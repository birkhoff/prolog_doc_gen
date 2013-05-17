:- prolog_flag(compiling,_,debugcode).
:- prolog_flag(source_info,_,on).
:- prolog_flag(profiling,_,on).

:- use_module(library(lists)).
:- use_module(library(terms)).
:- use_module(library(system)).
:- use_module(library(file_systems)).

:- use_module(escaper).

:- op(300, fy, ~~).

%:- include(prob_search_paths).


:- dynamic exports/3, imports/3, imports/1, predicates/7, dynamics/1, metas/1, in_module/1, in_clause/2, module_pos/2. 

in_module('user').
module_pos(1,1).

flatten(List,FlatList) :- flatten1(List,[],FlatList).
flatten1([],L,L) :- !.
flatten1([H|T],Tail,List) :- !, flatten1(H,FlatList,List), flatten1(T,Tail,FlatList).
flatten1(NonList,Tail,[NonList|Tail]).

write_exports :-
    exports(Module,Name,Arity),
    escaping_format('\n\t<export>\n\t\t<module>"~w"</module>\n\t\t<name>"~w"</name>\n\t\t<arity>~w</arity>\n\t</export>', [Module,Name,Arity]),
    fail.
write_exports.

write_import1 :-
    imports(Name),
    escaping_format('<name>"~w"</name>',[Name]),
    fail.
write_import1.
    
write_import3 :-
    imports(Module,Name,Arity),
    escaping_format('<module>"~w"</module>\n<name>"~w"</name>\n<arity>~w</arity>', [Module,Name,Arity]),
    fail.
write_import3.

write_predicates :-
    findall(pr(Name,Ar), predicates(Name,Ar,_,_,_,_,_), ListOfNames),
    remove_dups(ListOfNames,ListOfNames2),
    write_predicates(ListOfNames2).
write_predicates([]).
write_predicates([pr(Name,Ar)|Names]) :-
    write_predicates2(Name,Ar,[],[],[],[],0),
    write_predicates(Names).

bind_args(Args,VC,VCN) :-
    term_variables(Args,Variables),
    bind_args2(Variables,VC,VCN).
bind_args2([],X,X).
bind_args2([V|Vs],VC,VCN) :-
    number_codes(VC,CodesVC),
    append("v",CodesVC,Codes),
    atom_codes(V,Codes),
    VCNT is VC + 1,
    bind_args(Vs,VCNT,VCN).

is_dynamic(Name,Ar,'<dynamic>true</dynamic>') :- dynamics(Name/Ar), !.
is_dynamic(_Name,_Ar,'<dynamic>false</dynamic>').
is_meta(Name,Ar,'<meta>true</meta>') :- metas(Name/Ar), !.
is_meta(_Name,_Ar,'<meta>false</meta>').

write_predicates2(Name,Ar,Code,Calls,StartLines,EndLines,VC) :-
    retract(predicates(Name,Ar,Args1,Body1,Calls1,StartLine,EndLine)),
    bind_args(Args1,VC,VCN),
    bind_args(Body1,VCN,VCN2),
    NewCode = [ Args1, Body1|Code],
    append(Calls,Calls1,NewCalls),
    write_predicates2(Name,Ar,NewCode,NewCalls,[StartLine|StartLines],[EndLine|EndLines],VCN2).
write_predicates2(Name,Ar,Code,Calls,StartLines,EndLines,_VNC) :-
    is_dynamic(Name,Ar,Dynamic), is_meta(Name,Ar,Meta),
    escaping_format('\t<predicate>\n\t\t<name>"~w"</name>\n\t\t<arity>~w</arity>\n\t\t<code>~w</code>\n\t\t<startlines>~w</startlines>\n\t\t<endlines>~w</endlines>\n\t\t~w\n\t\t~w\n\t\t<calls>',[Name,Ar,Code,StartLines,EndLines,Dynamic,Meta]),
    write_calls(Calls),
    write('\n\t\t</calls>\n\t</predicate>\n'),nl.
	    
write_calls([]).
write_calls([call(Module,Name,Ar)|Calls]) :-
    escaping_format('\n\t\t\t<call>\n\t\t\t\t<module>"~w"</module>\n\t\t\t\t<name>"~w"</name>\n\t\t\t\t<arity>~w</arity>\n\t\t\t</call>', [Module,Name,Ar]),
    write_calls(Calls).

write_clj_representation :-
    update_calls_all_preds,
    write('<programm>'), nl,
    in_module(Module),
    module_pos(StartLine,EndLine),
    escaping_format('<module>"~w"</module>\n', [Module]),
    escaping_format('<module_startline>~w</module_startline>\n', [StartLine]),
    escaping_format('<module_endline>~w</module_endline>\n', [EndLine]),
    write('<exports>'), write_exports, write('</exports>'), nl,
    write('\n<predicates>\n\n'), write_predicates, write('</predicates>'), nl,
    write('<import_modules>'), write_import1, write('</import_modules>'), nl,
    write('<import_predicates>'), write_import3, write('</import_predicates>'), nl,
    write('</programm>').

update_calls_all_preds :-
    findall(pred(Name,Ar,Arguments,Body,Calls,Start,End),
	    predicates(Name,Ar,Arguments,Body,Calls,Start,End),
	    ListOfAssertedPreds),
    maplist(update_calls,ListOfAssertedPreds).

update_calls(pred(Name,Ar,Arguments,Body,Calls,Start,End)) :-
    maplist(update_call,Calls,UpdatedCalls),
    retract(predicates(Name,Ar,Arguments,Body,Calls,Start,End)),
    assert(predicates(Name,Ar,Arguments,Body,UpdatedCalls,Start,End)).

update_call(call(Module,Call,Arity),call(Module2,Call,Arity)) :-
    Module = nil
    -> update_module(Call,Arity,Module2)
    ;  Module2 = Module.

update_module('RECURSIVE_CALL',_A,X) :- !, in_module(X).
update_module(Call,Arity,Module2) :-
    in_module(X), functor(CallAndVar,Call,Arity),
    (predicate_property(X:CallAndVar,built_in) -> Module2 = built_in ;
     predicate_property(X:CallAndVar,imported_from(From)) -> Module2 = From ;
     predicates(Call,Arity,_,_,_,_,_) -> Module2 = X ;
     imports(ModuleI,Call,Arity) -> Module2 = ModuleI ;
     otherwise -> Module2 = foo_error).

layout_sub_term([],_,[]).
layout_sub_term([H|T],N,Res) :-
    (N=<1 -> Res=H ; N1 is N-1, layout_sub_term(T,N1,Res)).

analyze_body(X,_Layout,[call('built_in', 'call', 1)]) :- var(X), !.
analyze_body(\+(X),Layout,[call('built_in','not',1)|Calls]) :-
    !, analyze_body(X,Layout,Calls).
%analyze_body('~~'(X),Layout) :-
%    !, analyze_body(X,Layout).
analyze_body((A -> B),Layout,[call('built_in', '->' , 2)|Calls]) :-
    !, layout_sub_term(Layout,2,LayoutA),
    layout_sub_term(Layout,3,LayoutB),
    analyze_body(A,LayoutA,CallsA),
    analyze_body(B,LayoutB,CallsB),
    append(CallsA,CallsB,Calls).
analyze_body((A -> B ; C),Layout,[call('built_in', '->', 3)|Calls]) :-
    !, layout_sub_term(Layout,2,LayoutAB),
    layout_sub_term(LayoutAB,2,LayoutA),
    layout_sub_term(LayoutAB,3,LayoutB),
    layout_sub_term(Layout,3,LayoutC),
    analyze_body(A,LayoutA,CallsA),
    analyze_body(B,LayoutB,CallsB),
    analyze_body(C,LayoutC,CallsC),
    append(CallsA,CallsB,CallsT), append(CallsT,CallsC,Calls).
analyze_body(if(A,B,C),Layout,[call('built_in', 'if', 3)|Calls]) :-
    !, layout_sub_term(Layout,2,LayoutA),
    layout_sub_term(Layout,3,LayoutB),
    layout_sub_term(Layout,4,LayoutC),
    analyze_body(A,LayoutA,CallsA),
    analyze_body(B,LayoutB,CallsB),
    analyze_body(C,LayoutC,CallsC),
    append(CallsA,CallsB,CallsT), append(CallsT,CallsC,Calls).
analyze_body((A,B),Layout,Calls) :-
    !, layout_sub_term(Layout,2,LayoutA),
    layout_sub_term(Layout,3,LayoutB),
    analyze_body(A,LayoutA,CallsA),
    analyze_body(B,LayoutB,CallsB),
    append(CallsA,CallsB,Calls).
analyze_body((A;B),Layout,Calls) :-
    !, layout_sub_term(Layout,2,LayoutA),
    layout_sub_term(Layout,3,LayoutB),
    analyze_body(A,LayoutA,CallsA),
    analyze_body(B,LayoutB,CallsB),
    append(CallsA,CallsB,Calls).
analyze_body(M:X,_Layout,[call(M, FunOut, Ar)]) :-
    !, functor(X,Fun,Ar),
    (recursive_call(M,Fun,Ar) -> FunOut = 'RECURSIVE_CALL' ; FunOut = Fun).
analyze_body(X,_Layout,[call(nil, FunOut, Ar)]) :-
    !, functor(X,Fun,Ar),
    (recursive_call(_M,Fun,Ar) -> FunOut = 'RECURSIVE_CALL' ; FunOut = Fun).

recursive_call(Mod,Fun,Ar) :-
    in_module(Mod), in_clause(Fun,Ar).

assert_exports(Name,N/A) :-
    !, assert(exports(Name,N,A)).
assert_imports(Name,N/A) :-
    !, assert(imports(Name,N,A)).
assert_dynamics((X,Y)) :-
    !, assert(dynamics(X)), assert_dynamics(Y).
assert_dynamics(X) :-
    !, assert(dynamics(X)).
assert_metas((X,Y)) :-
    !, assert_metas(X), assert_metas(Y).
assert_metas(Term) :-
    !, functor(Term,Fun,Arg),
    (metas(Fun/Arg) -> true ; assert(metas(Fun/Arg))).

analyze((:- module(Name, ListOfExported)), _Layout, (:- module(Name,ListOfExported))) :-
    !, flatten(_Layout,[StartLine|FlatLayout]),
    (FlatLayout = [] -> EndLine = StartLine ; last(FlatLayout,EndLine)),
    retract(module_pos(_,_)), retract(in_module(_)),
    assert(in_module(Name)), assert(module_pos(StartLine,EndLine)),  maplist(assert_exports(Name),ListOfExported).
analyze((:- use_module(Name, ListOfImported)), _Layout, (:- true)) :-
    !, unwrap_module(Name,UnwrappedName),
    maplist(assert_imports(UnwrappedName),ListOfImported).
analyze((:- use_module(Name)), _Layout, (:- true)) :-
    !, unwrap_module(Name,UnwrappedName),
    assert(imports(UnwrappedName)).
analyze((:- dynamic(X)), _Layout, (:- dynamic(X))) :-
    !, assert_dynamics(X).
analyze((:- meta_predicate(X)), _Layout, (:- true)) :-
    !, assert_metas(X).
analyze((:- op(P,T,N)), _Layout, (:- op(P,T,N))) :- !.
analyze((:- _),_Layout,(:- true)) :- !.
analyze((?- X),_Layout,(?- X)) :- !.
analyze(end_of_file,_Layout,end_of_file) :- !.

analyze((Head :- Body), [LayoutHead | LayoutSub], (Head :- Body)) :-
    !, layout_sub_term([LayoutHead|LayoutSub],3,SubLay),
    analyze_body(Body,SubLay,Calls),
    functor(Head,Fun,Ar),
    Head =.. [Fun|Args],
    flatten([LayoutHead|LayoutSub],[StartLine|FlatLayout]),
    (FlatLayout = [] -> EndLine = StartLine ; last(FlatLayout,EndLine)),
    assert(predicates(Fun,Ar,Args,Body,Calls,StartLine,EndLine)),
    assert(in_clause(Fun,Ar)).
analyze(Fact, Layout, Fact) :-
    !, functor(Fact,Fun,Ar),
    Fact =.. [Fun|Args],
    flatten(Layout,[StartLine|FlatLayout]),
    (FlatLayout = [] -> EndLine = StartLine ; last(FlatLayout,EndLine)),
    assert(predicates(Fun,Ar,Args,'',[],StartLine,EndLine)).

user:term_expansion(Term1, Lay1, Tokens1, Term2, [], [codeq | Tokens1]) :-
    nonmember(codeq, Tokens1), % do not expand if already expanded
    analyze(Term1, Lay1, Term2),
    %write(Term1),nl,
    %write(Term2),nl,
    !.
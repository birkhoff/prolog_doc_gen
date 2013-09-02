:- prolog_flag(compiling,_,debugcode).
:- prolog_flag(source_info,_,on).
:- prolog_flag(profiling,_,on).

:- use_module(library(lists)).
:- use_module(library(terms)).
:- use_module(library(system)).
:- use_module(library(file_systems)).
:- use_module(library(codesio)).

:- use_module(escaper).
:- include(prob_search_paths).

:- op(300, fy, ~~).


:- dynamic exports/3, imports/3, imports/1, predicates/8, dynamics/1, metas/2, volatiles/1, multifiles/1,in_module/1, in_clause/2, module_pos/2, ops/3, blocking/2, modes/2, stream/1. 

in_module('user').
module_pos(1,1).

flatten(List,FlatList) :- flatten1(List,[],FlatList).
flatten1([],L,L) :- !.
flatten1([H|T],Tail,List) :- !, flatten1(H,FlatList,List), flatten1(T,Tail,FlatList).
flatten1(NonList,Tail,[NonList|Tail]).

write_exports :-
    exports(Module,Name,Arity),
	stream(Stream),
    escaping_format(Stream,'\n\t<export>\n\t\t<module>"~w"</module>\n\t\t<name>"~w"</name>\n\t\t<arity>~w</arity>\n\t</export>\n\n', [Module,Name,Arity]),
    fail.
write_exports.

write_import1 :-
    imports(Name),
	stream(Stream),
    escaping_format(Stream,'\n\t<name>"~w"</name>\n',[Name]),
    fail.
write_import1.
    
write_import3 :-
    imports(Module,Name,Arity),
	stream(Stream),
    escaping_format(Stream,'\n\t<import>\n\t\t<module>"~w"</module>\n\t\t<name>"~w"</name>\n\t\t<arity>~w</arity>\n\t</import>\n\n', [Module,Name,Arity]),
    fail.
write_import3.

write_ops3 :-
    ops(Prio,Ass,Name),
	stream(Stream),
    escaping_format(Stream,'\n\t<op>\n\t\t<priority>"~w"</priority>\n\t\t<ass>"~w"</ass>\n\t\t<name>~w</name>\n\t</op>\n\n', [Prio,Ass,Name]),
    fail.
write_ops3.

write_mode(Name/Ar):-
 	modes(Name/Ar, Args), 
	stream(Stream),
	escaping_format(Stream,'\n\t\t\t<mode>~w~w</mode>', [Name, Args]),
	fail.
write_mode(_Name/_Ar).

write_blocking(Name/Ar):-
 	blocking(Name/Ar, Args), 
	stream(Stream),
	escaping_format(Stream,'\n\t\t\t<blocking>~w~w</blocking>', [Name, Args]),
	fail.
write_blocking(_Name/_Ar).

write_multifiles :-
 	multifiles(Name/Ar), 
	stream(Stream),
	escaping_format(Stream,'\n\t<multifile>~w/~w</multifile>', [Name, Ar]),
	fail.
write_multifiles.

write_dynamics :-
 	dynamics(Name/Ar), 
	stream(Stream),
	escaping_format(Stream,'\n\t<dynamics>~w/~w</dynamics>', [Name, Ar]),
	fail.
write_dynamics.

write_metas(Name/Ar) :-
 	stream(Stream),
	metas(Name/Ar, Arg), 
	escaping_format(Stream,'~w~w\t', [Name, Arg]),
	fail.
write_metas(_Name/_Ar).

write_predicates([]).
write_predicates([pr(Name,Ar)|Names]) :-
    write_predicates2(_,Name,Ar,[],[],[],[],0),
    write_predicates(Names).

% write_predicates Module writes predicates of a given module

% write_predicates(Module) :-		
%    findall(pr(Name,Ar), predicates(Module,Name,Ar,_,_,_,_,_), ListOfNames),
%    remove_dups(ListOfNames,ListOfNames2),
%    write_predicates(ListOfNames2).

write_predicates :-
    findall(pr(Name,Ar), predicates(_,Name,Ar,_,_,_,_,_), ListOfNames),
    remove_dups(ListOfNames,ListOfNames2),
    write_predicates(ListOfNames2).


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
is_meta(Name,Ar,Return) :- metas(Name/Ar, _Args), format_to_codes('<meta>true</meta>', [], Codes), atom_codes(Return, Codes), !.
is_meta(_Name,_Ar,'<meta>false</meta>').

is_volatile(Name,Ar,'\n\t\t<volatile>true</volatile>') :- volatiles(Name/Ar), !.
is_volatile(_Name,_Ar,'\n\t\t<volatile>false</volatile>').

is_multifile(Name,Ar,'\n\t\t<multifile>true</multifile>') :- multifiles(Name/Ar), !.
is_multifile(_Name,_Ar,'\n\t\t<multifile>false</multifile>').


write_predicates2(Module,Name,Ar,Code,Calls,StartLines,EndLines,VC) :-
    retract(predicates(Module,Name,Ar,Args1,Body1,Calls1,StartLine,EndLine)),
    bind_args(Args1,VC,VCN),
    bind_args(Body1,VCN,VCN2),
    NewCode = [ Args1, Body1|Code],
    append(Calls,Calls1,NewCalls),
    write_predicates2(Module,Name,Ar,NewCode,NewCalls,[StartLine|StartLines],[EndLine|EndLines],VCN2).
write_predicates2(_Module,Name,Ar,_Code,Calls,StartLines,EndLines,_VNC) :-
    is_dynamic(Name,Ar,Dynamic), is_meta(Name,Ar,Meta), is_volatile(Name,Ar,Volatile), is_multifile(Name,Ar,Multifile),
    stream(Stream),
	escaping_format(Stream,'\t<predicate>\n\t\t<name>"~w"</name>\n\t\t<arity>~w</arity>\n\t\t<startlines>~w</startlines>\n\t\t<endlines>~w</endlines>\n\t\t~w\n\t\t~w ~w ~w\n\t\t<calls>',[Name,Ar,StartLines,EndLines,Dynamic,Meta, Volatile,Multifile]),
	setify(Calls, CallsNoDups),
	write_calls(CallsNoDups), write(Stream,'\n\t\t</calls>'),
	write(Stream, '\n\t\t<meta_args>'),
	write_metas(Name/Ar),
	write(Stream, '</meta_args>'),
	write(Stream,'\n\t\t<block>'),
	write_blocking(Name/Ar),
	write(Stream,'\n\t\t</block>'),
	write(Stream,'\n\t\t<modedeclaration>'),
	write_mode(Name/Ar),
	write(Stream,'\n\t\t</modedeclaration>'),
    write(Stream,'\n\t</predicate>\n'),nl.
	    
write_calls([]).
write_calls([call(Module,Name,Ar)|Calls]) :-
	escape_single_argument(Name, EscapedName),
    stream(Stream),
	escaping_format(Stream,'\n\t\t\t<call>\n\t\t\t\t<module>"~w"</module>\n\t\t\t\t<name>~w</name>\n\t\t\t\t<arity>~w</arity>\n\t\t\t</call>', [Module,EscapedName,Ar]),
    write_calls(Calls).

write_xml_representation :-
    update_calls_all_preds,
    stream(Stream),
	write(Stream,'<programm>'), nl,
    in_module(Module),
    module_pos(StartLine,EndLine),
    escaping_format(Stream,'<module>"~w"</module>\n\n', [Module]),
    escaping_format(Stream,'<module_startline>~w</module_startline>\n', [StartLine]),
    escaping_format(Stream,'<module_endline>~w</module_endline>\n', [EndLine]),
    write(Stream,'<exports>\n'), write_exports, write(Stream,'</exports>'), nl,
	write(Stream,'\n<multifiles>\n'), write_multifiles, write(Stream,'\n</multifiles>\n'),
	write(Stream,'\n<dynamic_predicates>\n'), write_dynamics, write(Stream,'\n</dynamic_predicates>\n'),
    write(Stream,'\n<predicates>\n\n'), write_predicates, write(Stream,'</predicates>'), nl,
    write(Stream,'<import_modules>'), write_import1, write(Stream,'</import_modules>'), nl,
    write(Stream,'<import_predicates>\n'), write_import3, write(Stream,'</import_predicates>'), nl,
	write(Stream,'\n<ops>\n'), write_ops3, write(Stream,'</ops>\n'), nl,
    write(Stream,'</programm>').

update_calls_all_preds :-
    findall(pred(Module,Name,Ar,Arguments,Body,Calls,Start,End),
	    predicates(Module,Name,Ar,Arguments,Body,Calls,Start,End),
	    ListOfAssertedPreds),
    maplist(update_calls,ListOfAssertedPreds).

update_calls(pred(Module,Name,Ar,Arguments,Body,Calls,Start,End)) :-
    maplist(update_call,Calls,UpdatedCalls),
    retract(predicates(Module,Name,Ar,Arguments,Body,Calls,Start,End)),
    assert(predicates(Module,Name,Ar,Arguments,Body,UpdatedCalls,Start,End)).

update_call(call(Module,Call,Arity),call(Module2,Call,Arity)) :-
    Module = nil
    -> update_module(Call,Arity,Module2)
    ;  Module2 = Module.


setify(L,R) :- setify(L,[],R).
setify([],A,A).
setify([H|T],A,R) :- (member(H,T) -> setify(T,A,R); setify(T,[H|A],R)).

update_module('RECURSIVE_CALL',_A,X) :- !, in_module(X).
update_module(Call,Arity,Module2) :-
    in_module(X), functor(CallAndVar,Call,Arity),
    (predicate_property(X:CallAndVar,built_in) -> Module2 = built_in ;
     predicate_property(X:CallAndVar,imported_from(From)) -> Module2 = From ;
     predicates(_,Call,Arity,_,_,_,_,_) -> Module2 = X ;
     imports(ModuleI,Call,Arity) -> Module2 = ModuleI ;
     dynamics(Call/Arity) -> Module2 = 'dynamic predicate';
     otherwise -> Module2 = foo_error).

layout_sub_term([],_,[]).
layout_sub_term([H|T],N,Res) :-
    (N=<1 -> Res=H ; N1 is N-1, layout_sub_term(T,N1,Res)).

analyze_body(X,_Layout,[call('built_in', 'call', 1)]) :- var(X), !.
analyze_body(_X:M,_Layout,[call('built_in', 'Use variable Module', 1)]) :- var(M), !.

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

analyze_body(when(A,B),Layout,[call('built_in', 'when', 2)|Calls]) :-
    !, layout_sub_term(Layout,2,LayoutA),
    layout_sub_term(Layout,3,LayoutB),
    analyze_body(A,LayoutA,CallsA),
    analyze_body(B,LayoutB,CallsB),
    append(CallsA,CallsB,Calls).

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
assert_imports(Name) :-
    !, assert(imports(Name)).
assert_dynamics((X,Y)) :-
    !, assert(dynamics(X)), assert_dynamics(Y).
assert_dynamics(X) :-
    !, assert(dynamics(X)).

assert_metas((X,Y)) :-
    !, assert_metas(X), assert_metas(Y).
assert_metas(Term) :-
    !, functor(Term,Fun,Ar),
	Term =..[_Fun|Args],
    (metas(Fun/Ar, Args) -> true ; assert(metas(Fun/Ar, Args))).


%% assert mode

assert_mode((X,Y)) :-
    !, assert_mode(X), assert_mode(Y).
assert_mode(Term) :-
    !, functor(Term,Fun,Ar),
	Term =..[_Fun|Args],
    (modes(Fun/Ar, Args) -> true ; assert(modes(Fun/Ar, Args))).

%assert block

assert_blocking((X,Y)) :-
    !, assert_blocking(X), assert_blocking(Y).
assert_blocking(Term) :-
    !, functor(Term,Fun,Ar),
	Term =..[_Fun|Args],
    (blocking(Fun/Ar, Args) -> true ; assert(blocking(Fun/Ar, Args))).

%assert volatile 

assert_volatile((X,Y)) :-
    !, assert(volatiles(X)), assert_volatile(Y).
assert_volatile(X) :-
    !, assert(volatiles(X)).

%assert multifile 

assert_multifile((X,Y)) :-
    !, assert(multifiles(X)), assert_multifile(Y).
assert_multifile(X) :-
    !, assert(multifiles(X)).

%% analyzing Prolog Code

analyze((:- module(Name, ListOfExported)), _Layout, (:- module(Name,ListOfExported))) :-
    !, flatten(_Layout,[StartLine|FlatLayout]),
    (FlatLayout = [] -> EndLine = StartLine ; last(FlatLayout,EndLine)),
    retract(module_pos(_,_)), retract(in_module(_)),
    assert(in_module(Name)), assert(module_pos(StartLine,EndLine)),  maplist(assert_exports(Name),ListOfExported).
analyze((:- use_module(Name, ListOfImported)), _Layout, (:- true)) :-
    !, unwrap_module(Name,UnwrappedName),
    maplist(assert_imports(UnwrappedName),ListOfImported).
analyze((:- use_module([H|T])), _Layout, (:- true)) :- 
    !, maplist(unwrap_module,[H|T],Names),
    maplist(assert_imports,Names).
analyze((:- use_module(Name)), _Layout, (:- true)) :- 
    !, unwrap_module(Name,UnwrappedName),
    assert(imports(UnwrappedName)).

analyze((:- dynamic(X)), _Layout, (:- dynamic(X))) :-
    !, assert_dynamics(X).
analyze((:- meta_predicate(X)), _Layout, (:- true)) :-
    !, assert_metas(X).

%blocking, operator declarations, volatile, multifile, 	mode

analyze((:- mode(X)), _Layout, (:- true)) :-
    !, assert_mode(X).
analyze((:- block(X)), _Layout, (:- true)) :-
    !, assert_blocking(X).
analyze((:- op(P,T,N)), _Layout, (:- op(P,T,N))) :- 
	assert( ops(P,T,N) ).
analyze((:- volatile(X)), _Layout, (:- volatile(X))) :-
    !, assert_volatile(X).
analyze((:- multifile(X)), _Layout, (:- multifile(X))) :-
    !, assert_multifile(X).
	
analyze((:- _),_Layout,(:- true)) :- !.
analyze((?- X),_Layout,(?- X)) :- !.
analyze(end_of_file,_Layout,end_of_file) :- !.



analyze((Head :- Body), [LayoutHead | LayoutSub], (Head :- Body)) :-
    !,layout_sub_term([LayoutHead|LayoutSub],3,SubLay),
    analyze_body(Body,SubLay,Calls),
    functor(Head,Fun,Ar),
    Head =.. [Fun|Args],
	(atom_codes(Fun, "-->") -> Args=[DCG_Name,_Rest], atom_concat(DCG_Name,-->,Name); Name=Fun),				% Analyze dcgs
    flatten([LayoutHead|LayoutSub],[StartLine|FlatLayout]),
    (FlatLayout = [] -> EndLine = StartLine ; last(FlatLayout,EndLine)),
    in_module(Module),
	assert(predicates(Module,Name,Ar,Args,Body,Calls,StartLine,EndLine)),
    assert(in_clause(Name,Ar)).


analyze(Fact, Layout, Fact) :-
    !, functor(Fact,Fun,Ar),
    Fact =.. [Fun|Args],
   	(atom_codes(Fun, "-->") -> Args=[DCG_Name,_Rest], atom_concat(DCG_Name,-->,Name); Name=Fun),				% Analyze dcgs
 	flatten(Layout,[StartLine|FlatLayout]),
    (FlatLayout = [] -> EndLine = StartLine ; last(FlatLayout,EndLine)),
	in_module(Module),
    assert(predicates(Module,Name,Ar,Args,'',[],StartLine,EndLine)).


analyze_file(FileName):-
	prolog_flag(redefine_warnings, _, off),
	on_exception(X,(use_module(FileName),
	write_xml_representation,told),
	(
		print('{:error \"'),print(X),print('\"}'),nl,halt(1))
	).


analyze_file(FileName, XMLFile):-
	prolog_flag(redefine_warnings, _, off),
	on_exception(X,
		(	
			use_module(FileName),
			open(XMLFile,write,Stream),
			assert(stream(Stream)),	
			write(Stream,'<?xml version="1.0" encoding="UTF-8"?>'),nl,	
			write_xml_representation,
			close(Stream),
			retract(stream(Stream)),
			halt(0)
		),
	(
		print('{:error \"'),print(X),print('\"}'),nl,halt(1))
	).
	

analyze_files([FileName|T], XMLFile):-
	prolog_flag(redefine_warnings, _, off),
	on_exception(X,
		(	
			use_module(FileName),
			open(XMLFile,write,Stream),
			assert(stream(Stream)),		
			write_xml_representation,
			close(Stream),
			retract(stream(Stream)),
			halt(0)
		),
	(
		print('{:error \"'),print(X),print('\"}'),nl,halt(1))
	).


user:term_expansion(Term1, Lay1, Tokens1, Term2, [], [codeq | Tokens1]) :-
    nonmember(codeq, Tokens1), % do not expand if already expanded
    analyze(Term1, Lay1, Term2),
    %write(S,Term1),nl,
    %write(S,Term2),nl,
    !.
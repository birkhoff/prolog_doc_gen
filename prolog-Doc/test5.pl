/** 
	@author swaghetti yolonese
	@date /John *Witulski
	@TimLippold  yolo
	swagelicious
	@descr <b>/* GPL</b>
	@mode merge(+,+,-)

*/

/* /*/

%! bla
%% blubb
:-module(test5, [foo/1]).

:- multifile foo/1.

:-meta_predicate foo(2), foo(1).

foo(Yam):-
	when(ground(Yam), print('1')).

bar:-
	% lala
	errorMessage(error_BParser_42, "expecting: '$', conjunction, single quotation, '(', ')', product, '**', plus, partial function, partial surjection, ',', minus, total function, total surjection, '->', '.', interval, dot par, '/', not belonging, non inclusion, strict non inclusion, not equal, '\\', intersection, '/|\\', element of, '::', ':=', ';', '<', overwrite relation, set relation, '<-', '<--', inclusion, strict inclusion, domain subtraction, less equal, equivalence, domain restriction, '=', implies, '>', partial injection, total injection, '>+>>', total bijection, direct product, greater equal, 'ABSTRACT_CONSTANTS', 'ABSTRACT_VARIABLES', 'ASSERTIONS', 'BE', 'CONCRETE_CONSTANTS', 'CONCRETE_VARIABLES', 'CONSTANTS', 'CONSTRAINTS', 'DEFINITIONS', 'DO', 'ELSE', 'ELSIF', 'END', 'EXTENDS', 'IMPORTS', 'IN', 'INCLUDES', initialisation, 'INVARIANT', 'LOCAL_OPERATIONS', 'OF', operations, 'OR', 'PROMOTES', 'PROPERTIES', 'SEES', 'SETS', 'THEN', 'USES', 'VALUES', 'VARIANT', 'VARIABLES', 'WHEN', 'WHERE', '[', ']', union, '\\|/', '^', 'mod', logical or, '}', '|', double vertical bar, maplet, range restriction, range subtraction, tilde, total relation, surjection relation, total surjection relation, EOF").
		

:- block merge(-,?,-), merge(?,-,-).
    merge([], Y, Y).
    merge(X, [], X).
    merge([H|X], [E|Y], [H|Z]) :- H @< E,  merge(X, [E|Y], Z).
    merge([H|X], [E|Y], [E|Z]) :- H @>= E, merge([H|X], Y, Z).



/** 
	@author Prolog documentation processor
	
	@date 2.1.3
	@descr
	This module processes structured comments and generates both formal
	mode declarations from them as well as documentation in the form of
	HTML or LaTeX.
*/

:- volatile is_not_visited/2.

is_not_visited( _ , [] ).
is_not_visited( H, [H|_T] ):-
	false.
is_not_visited( A, [H|T] ):-
	A \= H,
	is_not_visited(A, T),
	print(' \'More info at: http://www.stups.uni-duesseldorf.de/ProB/').

dcg_one --> '/\\/', dcg_two.
dcg_two --> "jap tum da//".

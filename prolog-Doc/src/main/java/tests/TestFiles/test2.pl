:-module(testFiles2, [foo/1]).

:- multifile foo/2, bar/0.

:-meta_predicate foo(:,+), foo(:,:), foo_bar(+,+).

foo(_Yam, _Tam).

bar:-
	% lala
	errorMessage(error_BParser_42, "expecting: '$', conjunction, single quotation, '(', ')', product, '**', plus, partial function, partial surjection, ',', minus, total function, total surjection, '->', '.', interval, dot par, '/', not belonging, non inclusion, strict non inclusion, not equal, '\\', intersection, '/|\\', element of, '::', ':=', ';', '<', overwrite relation, set relation, '<-', '<--', inclusion, strict inclusion, domain subtraction, less equal, equivalence, domain restriction, '=', implies, '>', partial injection, total injection, '>+>>', total bijection, direct product, greater equal, 'ABSTRACT_CONSTANTS', 'ABSTRACT_VARIABLES', 'ASSERTIONS', 'BE', 'CONCRETE_CONSTANTS', 'CONCRETE_VARIABLES', 'CONSTANTS', 'CONSTRAINTS', 'DEFINITIONS', 'DO', 'ELSE', 'ELSIF', 'END', 'EXTENDS', 'IMPORTS', 'IN', 'INCLUDES', initialisation, 'INVARIANT', 'LOCAL_OPERATIONS', 'OF', operations, 'OR', 'PROMOTES', 'PROPERTIES', 'SEES', 'SETS', 'THEN', 'USES', 'VALUES', 'VARIANT', 'VARIABLES', 'WHEN', 'WHERE', '[', ']', union, '\\|/', '^', 'mod', logical or, '}', '|', double vertical bar, maplet, range restriction, range subtraction, tilde, total relation, surjection relation, total surjection relation, EOF").
		

:- block merge(-,?,-), merge(?,-,-).
    merge([], Y, Y).
    merge(X, [], X).
    merge([H|X], [E|Y], [H|Z]) :- H @< E,  merge(X, [E|Y], Z).
    merge([H|X], [E|Y], [E|Z]) :- H @>= E, merge([H|X], Y, Z).

foo_bar(X,Y):-
		X = Y.
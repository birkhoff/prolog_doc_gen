:-module(test5, [foo/1]).

/** 
	@author swaghetti yolonese
	@date /John *Witulski
	@TimLippold  yolo
	swagelicious
	@descr /* GPL
	@mode merge(+,+,-)

*/

/* /*/

:- multifile foo/1.

foo(yam).

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
	is_not_visited(A, T).

dcg_one --> "/**", dcg_two.
dcg_two --> "//".

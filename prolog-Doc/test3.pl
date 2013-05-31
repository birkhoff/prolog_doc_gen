:-module(test3, [foo/1]).

/** 
	@author swaghetti yolonese
	@date /John *Witulski
	@TimLippold  yolo
	swagelicious
	@descr /* GPL

*/

/* /*/

foo(X):-
	X = 1.


/** 
	@author Prolog documentation processor
	
	@date 2.1.3
	@descr
	This module processes structured comments and generates both formal
	mode declarations from them as well as documentation in the form of
	HTML or LaTeX.
*/
is_not_visited( _ , [] ).
is_not_visited( H, [H|_T] ):-
	false.
is_not_visited( A, [H|T] ):-
	A \= H,
	is_not_visited(A, T).

dcg_one --> "/**", dcg_two.
dcg_two --> "//".
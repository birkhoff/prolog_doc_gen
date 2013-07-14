/** 
@author authorelement
	@date 1
	@descr descriptionElement

*/

%% @mode element(+,-,?)

element(E,[E|_T]).
element(E, [_H|T]):-
	element( E, T ).

/** @author authorVisited
	
	@date 2.1.3
	@descr descriptionVisited
*/

/*	@author authorVisited2 */
is_not_visited( _ , [] ).
is_not_visited( H, [H|_T] ):-
	false.

/*
	another description 
*/	
	
is_not_visited( A, [H|T] ):-
	A \= H,
	is_not_visited(A, T).
	
/** @author istrue
	@descr descrIsTrue
*/

istrue( true).

/** 
	@date 2.1.3
	@descr descr
	@author Prolog documentation processor
*/
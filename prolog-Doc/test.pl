/** 
@author Mike
	@date Today
	@descr  
	yami tammi
	tum da
	
	@timl yappa
	@jenny noppa
	@timl noppa

*/

%% @author Danny @descr dada

:-module(test, [foo/1]).

element(E,[E|_T]).
element(E, [_H|T]):-
	element( E, T ).

/* @author Tim
	**
	@date 2.1.3
	@descr
	
*/
is_not_visited( _ , [] ).
is_not_visited( H, [H|_T] ):-
	false.
is_not_visited( A, [H|T] ):-
	A \= H,
	is_not_visited(A, T).
	
/** @author Prolog documentation processor
	@descr descr
*/

istrue( '/').


check_boolean_expression(BExpr) :- %%covering all boolean expression with relational operators instead of '==' and '!='%
  relational_binary_op(BExpr,Arg1,Arg2,EX,EY,Call),!,
  evaluate_int_argument(Arg1,EX),
  evaluate_int_argument(Arg2,EY),
  Call.


foo(a).
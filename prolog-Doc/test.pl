/** 
@author Mike
	@date Today
	@descr  
	This module cotains several basic examples
*/

:-module(test, [foo/1]).

%% element(Element,List) if E in List then true

element(E,[E|_T]).
element(E, [_H|T]):-
	element( E, T ).
%! is_not_visited(Element,List) if E not in List then true

is_not_visited( _ , [] ).
is_not_visited( H, [H|_T] ):-
	false.
is_not_visited( A, [H|T] ):-
	A \= H,
	is_not_visited(A, T).
	
/** @author me
	@descr returns true
*/

istrue(true).


check_boolean_expression(BExpr) :- %%covering all boolean expression with relational operators instead of '==' and '!='%
  relational_binary_op(BExpr,Arg1,Arg2,EX,EY,Call),!,
  evaluate_int_argument(Arg1,EX),
  evaluate_int_argument(Arg2,EY),
  Call.


foo(a).
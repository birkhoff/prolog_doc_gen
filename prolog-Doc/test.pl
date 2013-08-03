/** 
@author spaghetti yolonese
	@date /Jan Wielemaker
	@TimLippold  yolo
	swagelicious
	@descr /* GPL 

*/


element(E,[E|_T]).
element(E, [_H|T]):-
	element( E, T ).

/* @author Prolog documentation processor
	**
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
	
/** @author Prolog documentation processor
	@descr descr
*/

istrue( '/').


check_boolean_expression(BExpr) :- %covering all boolean expression with relational operators instead of '==' and '!='%
  relational_binary_op(BExpr,Arg1,Arg2,EX,EY,Call),!,
  evaluate_int_argument(Arg1,EX),
  evaluate_int_argument(Arg2,EY),
  Call.

yolo(R):-
                      	'%'(cspm_arith_expr,cspm_arith_expr) ;
                              '*/'(cspm_arith_expr,cspm_arith_expr) ;
                              ''(cspm_arith_expr,cspm_arith_expr) ;
                              '/'(cspm_arith_expr,cspm_arith_expr) ;
                              '%'(cspm_arith_expr,cspm_arith_expr) ).

foo(a).
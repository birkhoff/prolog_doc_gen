package src.main.java;

import parser.*;

import lexer.*;
import node.*;
import java.io.*;
import java.util.LinkedList;
import java.lang.* ;



public class Compiler {
	
	public static String filename;
	
	public static void main(String args[]) throws ParserException, LexerException, InterruptedException
	{
	   try
	   {
		  filename = args[0];
		  File file = new File(filename);
		  PushbackReader reader = new PushbackReader(new FileReader(file)); 
		  Lexer lexer = new Lexer(reader); 
		  Parser parser = new Parser(lexer); 
		  Start tree = parser.parse();
		  ASTPrinter lala = new ASTPrinter();
		  tree.apply(lala);
		  //String code = "bipush 24\n";
		  	  
		  //System.out.println( checker.symbolTable.keySet() );
			
		  /*
		   *  [codeq_analyzer], tell('foo.clj'), prolog_flag(redefine_warnings, _, off),on_exception(X,(use_module('test.pl'),write_clj_representation,told, halt),(print('{:error \"'),print(X),print('\"}'),nl,halt(1))).
		   * 
		   */
	   }
		catch(IOException e)
		{
		 // do something 
		 System.out.println(e.getMessage());
		}

		try
		{
		  String cmd = "sicstus -l codeq_analyzer --goal \"tell('foo.xml'), prolog_flag(redefine_warnings, _, off),on_exception(X,(use_module('"+filename+"'),write_clj_representation,told, halt),(print('{:error \"'),print(X),print('\"}'),nl,halt(1))).\"" ;
		  
		  Process p = Runtime.getRuntime().exec(new String[] { 
                  "bash", "-c", 
                  cmd });
		  
		  //Process p = Runtime.getRuntime().exec(cmd);
		  p.waitFor();
		  
		  
		 }catch( Exception e)
		{
			System.out.println( e.getMessage() );
		}
	}
}
package src.main.java;

import parser.*;
import lexer.*;
import node.*;
import java.io.*;
import java.util.LinkedList;


public class Compiler {

	public static void main(String args[]) throws ParserException, LexerException
	{
	   try
	   {
		  String filename = args[0];
		  File file = new File(filename);
		  PushbackReader reader = new PushbackReader(new FileReader(file)); 
		  Lexer lexer = new Lexer(reader); 
		  Parser parser = new Parser(lexer); 
		  Start tree = parser.parse();
		  ASTPrinter lala = new ASTPrinter();
		  tree.apply(lala);
		  //String code = "bipush 24\n";
		  	  
		  //System.out.println( checker.symbolTable.keySet() );
		  
				
			
		}
		catch(IOException e)
		{
		 // do something 
		 System.out.println(e.getMessage());
		}
	}
}
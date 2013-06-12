package src.main.java;

import parser.*;

import lexer.*;
import node.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.lang.* ;



public class Compiler {
	
	//public static String filename;
	public static List<DocInformation> docInfos;
	public static List<Predicate> Predicates;
	public static Module Module;
	public static List<Module> Modules;
	
	public static void main(String args[]){
		Modules = new LinkedList<Module>();
		
		for(int i = 0; i<args.length; i++){
			try {
				Compiler(args[i]);
			} catch (ParserException e) {
				
				e.printStackTrace();
			} catch (LexerException e) {
				
				e.printStackTrace();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		HTML_Generator generator = new HTML_Generator();
		generator.generateDoc(Modules);
		
	}
	
	public static void Compiler(String nameOfFile) throws ParserException, LexerException, InterruptedException
	{
		
		
	   try
	   {
		  File file = new File(nameOfFile);
		  PushbackReader reader = new PushbackReader(new FileReader(file)); 
		  Lexer lexer = new Lexer(reader); 
		  Parser parser = new Parser(lexer); 
		  Start tree = parser.parse();
		  ASTPrinter lala = new ASTPrinter();
		  tree.apply(lala);
		 
		  DocParser docCollector = new DocParser();
		  tree.apply( docCollector);
		  
		  
		  debugDocOutput( docCollector.DocInfo );
		  docInfos = docCollector.DocInfo;
		  
		  /*
		   *  [codeq_analyzer], tell('foo.clj'), prolog_flag(redefine_warnings, _, off),on_exception(X,(use_module('test.pl'),write_clj_representation,told, halt),(print('{:error \"'),print(X),print('\"}'),nl,halt(1))).
		   */
	   }
		catch(IOException e)
		{
		 // catch no file found
		 System.out.println(e.getMessage());
		}

	 
		try {
			 String cmd = "sicstus -l codeq_analyzer --goal \"tell('foo.xml'), prolog_flag(redefine_warnings, _, off),on_exception(X,(use_module('"+nameOfFile+"'),write_clj_representation,told, halt),(print('{:error \"'),print(X),print('\"}'),nl,halt(1))).\"" ;
			  
			  Process p;
			p = Runtime.getRuntime().exec(new String[] { 
			       "bash", "-c", 
			       cmd });
			  //Process p = Runtime.getRuntime().exec(cmd);
			  p.waitFor();
		} catch (IOException e1) {
			
			System.out.println( e1.getMessage() );
		}
		 
		  
		try
		{
		  CodeqParser codeqParser = new CodeqParser(nameOfFile);
		  codeqParser.parseXML("foo.xml");
		  debugOutput(codeqParser.Predicates);
		  debugModuleOutput(codeqParser.Module);
		  Module = codeqParser.Module;
		  
		  Predicates = codeqParser.Predicates;
		  
		 }catch( Exception e)
		{
			System.out.println(e.getMessage() );
		}
		 
		  
		  InformationMerger merger = new InformationMerger();
		  merger.mergeModuleInformation(Predicates, docInfos);
		  debugMergedOutput(merger.MergedPredicates);
		  Module.setPredicates(merger.MergedPredicates);
		  Module.setPredicatesHashMap(merger.PredicatesHashMap);
		  Modules.add(Module);

		  		  
	}
	
	
	
	
	
	
	
	public static void debugOutput( List<Predicate> predicates){
		
		for(int i = 0; i<predicates.size(); i++){
			
			System.out.println("-------------");
			System.out.println(predicates.get(i).getName());
			System.out.println("\tDynmc: "+predicates.get(i).isDynamic());
			System.out.println("\tMeta: "+predicates.get(i).isMeta());

			List<Call> calls = predicates.get(i).getCallsNames();
			System.out.println("\tCalls:");
			for(int k = 0; k < calls.size(); k++){
				System.out.println("\t\tname: "+calls.get(k).getName());
				System.out.println("\t\tmodule: "+calls.get(k).getModule());
				System.out.println("\t\tarity: "+calls.get(k).getArity() + "\n");
			}
			System.out.println("--------\n");
		}
	}
	
	public static void debugDocOutput( List<DocInformation> info){
		
		System.out.println("_______ DocInfo ___________\n");
		for (int i = 0; i< info.size(); i++) {
			
			System.out.println("--------------");
			System.out.println( "Author: " + info.get(i).getAuthor());
			System.out.println( "Date: " + info.get(i).getDate());
			System.out.println( "Descr: " + info.get(i).getDescription());
			System.out.println( "Line: " + info.get(i).getLine());
			for(int k = 0; k < info.get(i).getAdditionalEntries().size(); k ++){
				System.out.println ( info.get(i).getAdditionalEntries().get(k).getIdentifier()+": "+info.get(i).getAdditionalEntries().get(k).getDescription());
			}
				System.out.println("----------\n");
		}
		System.out.println("\n______________________\n\n");
	}
	
	
	
	public static void debugMergedOutput( List<Predicate> predicates){
		
		System.out.println("\n\n_____________  Merged Predicates _________________\n");
		for(int i = 0; i<predicates.size(); i++){
					
					System.out.println("------Predicate------\n");
					System.out.println("Predicate: " + predicates.get(i).getName() + "\n");
					System.out.println( "\tModule: " + predicates.get(i).getModule());
					System.out.println( "\tAuthor: " + predicates.get(i).getAuthor());
					System.out.println( "\tDate: " + predicates.get(i).getDate());
					System.out.println( "\tDescr: " + predicates.get(i).getDescription());
	
		
					for(int k = 0; k < predicates.get(i).getAdditionalEntries().size(); k ++){
						System.out.println ( predicates.get(i).getAdditionalEntries().get(k).getIdentifier()+": "+predicates.get(i).getAdditionalEntries().get(k).getDescription());
					}
					
					System.out.println("\tDynmc: "+predicates.get(i).isDynamic());
					System.out.println("\tMeta: "+predicates.get(i).isMeta());
		
					System.out.println("\tCode:\n"+predicates.get(i).getCodeString());
						
					List<Call> calls = predicates.get(i).getCallsNames();
					System.out.println("\tCalls:");
					for(int k = 0; k < calls.size(); k++){
						System.out.println("\t\tname: "+calls.get(k).getName());
						System.out.println("\t\tmodule: "+calls.get(k).getModule());
						System.out.println("\t\tarity: "+calls.get(k).getArity() + "\n");
					}
					System.out.println("--------\n");
				}
		System.out.println("\n______________________________\n\n");
	}
	
	public static void debugModuleOutput( Module m){
		System.out.println("Module: "+ m.getName());
		System.out.println("Imports:");
		for (int i=0; i<m.getImports().size(); i++) {
			System.out.println("\tName:" + m.getImports().get(i).getName());
			System.out.println("\tModule:" + m.getImports().get(i).getModule());
			System.out.println("\tArity:" + m.getImports().get(i).getArity() + "\n");
		}
		
		System.out.println("\nExports: ");
		for (int i=0; i<m.getExports().size(); i++) {
			System.out.println("\tName:" + m.getExports().get(i).getName());
			System.out.println("\tModule:" + m.getExports().get(i).getModule());
			System.out.println("\tArity:" + m.getExports().get(i).getArity() + "\n");
		}
		System.out.println("\n");
	}
	
}
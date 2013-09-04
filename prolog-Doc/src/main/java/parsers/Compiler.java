package src.main.java.parsers;

import parser.*;

import lexer.*;
import node.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;




public class Compiler {
	
	public static List<DocInformation> docInfos;
	public static List<Predicate> Predicates;
	public static List <Predicate> AllPredicates;
	public static List <Predicate> AllUndocumented;
	public static List <Predicate> AllEmphasized;
	public static Module Module;
	public static List<Module> Modules;
	public static List<Module> AllEmphasizedModules;
	public static String loadingString= "";
	public static int NumberOfFiles;
	public static int CurrentFileNumber;
	public static boolean spdet_flag;
	public static boolean dir_flag;
	public static String destinationFolder = "Doc/";
	
	public static void main(String args[]){
		Modules = new LinkedList<Module>();
		AllPredicates = new LinkedList<Predicate>();
		AllUndocumented = new LinkedList<Predicate>();
		AllEmphasizedModules = new LinkedList<Module>();
		AllEmphasized = new LinkedList<Predicate>();
				
		setFlags(args);
		
		List<String> Files = new LinkedList<String>();
		if(dir_flag){
			Files = getFiles(args);
		}else{
			for (int i = 0; i < args.length; i++) {
				
				if(!args[i].startsWith("-"))Files.add(args[i]);
			}
		}

		startLoadingScreen(Files.size());
		
		for (int i = 0; i< Files.size(); i++) {

			try {
				loadingScreen(Files.size(), i, Files.get(i));
				analyze(Files.get(i));
			} catch (final Exception e) {
				System.out.println("Error in:"+  Files.get(i));
				e.printStackTrace();
			}
			
		}
		System.out.println("\n\nGenerating HTML Pages:");
		HTML_Generator generator = new HTML_Generator();
		generator.generateDoc(Modules, AllPredicates, AllUndocumented, AllEmphasized, AllEmphasizedModules, destinationFolder);
		System.out.println("\n");
	
	}
	
	public static void setFlags(String args[]){
	
		for(int i = 0; i < args.length; i++){
			if(args[i].contains("-spdet")) 	spdet_flag = true;
			if(args[i].contains("-dir"))	dir_flag = true;
			if(args[i].contains("-o")){
				destinationFolder = args[i].replaceAll("-[^=]*=", "").replaceAll("\"|\'", "");
				File f = new File(destinationFolder);
				if(!f.exists()) f.mkdirs();
			}
		}
	}
	
	public static void startLoadingScreen(int files){
		NumberOfFiles = files;
		CurrentFileNumber = 0;
		System.out.print("\nGenerating Sicstus Prolog Doc:\n");
	}
	
	public static void loadingScreen(int size, int current, String File){
		
		//for (int k = 0; k < loadingString.length(); k++){
			//System.out.print("\b");
			//System.out.print(" ");
			//System.out.print("\b");
		//}
		//loadingString = "\t";
		//for (int k = 0; k <= current; k++) loadingString += "#";
		current ++;
		loadingString = " ("+current+"/"+size+") Files analyzed   currently: "+File;
		System.out.println(loadingString);
	}
	
	public static List<String> getFiles(String args[]){
		
		List<String> returnFiles = new LinkedList<String>();
      
        for (int i = 1; i < args.length; i ++) {
			
        	if(!args[i].startsWith("-")){
	        	File current = new File(args[i]);
		        for ( File f : current.listFiles()) {
		            if (f.isDirectory()) {
		            	List<String> additionalFiles = getFiles(f.getPath());
		            	returnFiles.addAll(additionalFiles);
		            } else if (f.getName().endsWith(".pl")) {
		            	returnFiles.add(f.getPath());
		            	
		            }
		        }				
			}
        }
		return returnFiles;
	}
	
	public static List<String> getFiles(String dir){
		// writing to Pos 2 because the getFiles skips the first argument
		String args[] = new String[2];
		args[1] = dir;
		return getFiles(args);
	}
	
	public static void analyze(String nameOfFile) throws ParserException, LexerException, InterruptedException
	{	
	   try
	   {
		  File file = new File(nameOfFile);
		  PushbackReader reader = new PushbackReader(new FileReader(file)); 
		  Lexer lexer = new Lexer(reader); 
		  Parser parser = new Parser(lexer); 
		  Start tree = parser.parse();
		 // ASTPrinter printer = new ASTPrinter();
		 // tree.apply(printer);
		 
		  DocParser docCollector = new DocParser();
		  tree.apply( docCollector);
		  
		  //debugDocOutput( docCollector.DocInfo );
		  docInfos = docCollector.DocInfo;
		  
		  /*
		   *  [codeq_analyzer], tell('foo.clj'), prolog_flag(redefine_warnings, _, off),on_exception(X,(use_module('test.pl'),write_clj_representation,told, halt),(print('{:error \"'),print(X),print('\"}'),nl,halt(1))).
		   */
	   }
		catch(IOException e)
		{
		System.out.println("Error: "+ nameOfFile);
		 System.out.println(e.getMessage());
		}

	   
		try {
			String cmd[] = {"bash", "-c", 
						"sicstus -l codeq_analyzer_2 --goal \"analyze_file('"+nameOfFile+"','"+"foo.xml"+"').\""} ;
			
			ProcessBuilder pb = new ProcessBuilder(cmd);
			pb.redirectErrorStream(true);
			Process process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			//String line;
			process.getErrorStream().close();
			process.getOutputStream().close();
			while ((reader.readLine()) != null){   
				
			}
			process.waitFor();
			
		} catch (IOException e1) {
			System.out.println("Sicstus Process Failed in: " + nameOfFile);
			System.out.println( e1.getMessage() );
		}
		 
	
				  
		try
		{
		  CodeqParser codeqParser = new CodeqParser(nameOfFile);
		  codeqParser.parseXML("foo.xml");
		  //debugOutput(codeqParser.Predicates);
		  //debugModuleOutput(codeqParser.Module);
		  Module = codeqParser.Module;
		  if(spdet_flag){
			  SPDetParser spdet = new SPDetParser(nameOfFile);
			  Module.setSPDet(spdet.getSPDetHTML());
		  }
		  Predicates = codeqParser.Predicates;
		  AllPredicates.addAll(Predicates);
		  
		 }catch( Exception e)
		{
			System.out.println(e.getMessage() );
		}
		 
		  
		  InformationMerger merger = new InformationMerger();
		  merger.mergeModuleInformation(Predicates, docInfos);
		  //debugMergedOutput(merger.MergedPredicates);
		  Module.setPredicates(merger.MergedPredicates);
		  Module.setPredicatesHashMap(merger.PredicatesHashMap);
		  Modules.add(Module);
		  //File xml = new File("foo.xml");
		  //xml.delete();
		  AllUndocumented.addAll(merger.Undocumented);
		  if(merger.Emphasize.size()>0){
			  AllEmphasized.addAll(merger.Emphasize);
			  AllEmphasizedModules.add(Module);
		  }
		  		  
	}
	
	
	
	
	public static void debugOutput( List<Predicate> predicates){
		
		for(int i = 0; i<predicates.size(); i++){
			
			System.out.println("-------------");
			System.out.println(predicates.get(i).getName());
			System.out.println("\tDynmc: "+predicates.get(i).isDynamic());
			System.out.println("\tMeta: "+predicates.get(i).isMeta());
			System.out.println("\tStartLines: "+predicates.get(i).getStartLines()[0]);
			System.out.println("\tEndLines: "+predicates.get(i).getEndLines()[0]);
			System.out.println("\tMetaInformation: "+predicates.get(i).getMetaInformation());
			System.out.println("\tBlockings: "+predicates.get(i).getBlockingInformation());
			System.out.println("\tMode: "+predicates.get(i).getMode());
			
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
					System.out.println("\tBlockings: "+predicates.get(i).getBlockingInformation());
		
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

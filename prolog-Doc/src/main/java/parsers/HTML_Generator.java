package src.main.java.parsers;

import java.io.*;
import java.text.Collator;
import java.util.*;

public class HTML_Generator {

	private String code = "";
	private String moduleLinks ="";
	private String moduleLinksIndex = "";
	private List<Module> Modules;
	private HashMap<String, Boolean> ModuleNames;
	private String destination = "Doc/";
	
	public HTML_Generator(){
		
	}
	
	public void generateDoc( List<Module> modules, List<Predicate> AllPredicates, String destFolder){
		
		this.destination = destFolder;
		this.Modules = modules;
		ModuleNames = this.setModuleNamestoHash(Modules);
		this.setModuleLinks(modules);
		for(int i = 0; i < modules.size(); i++){
			try {
				this.generateSinglePage(modules.get(i));
			} catch (IOException e) {
				System.out.println("\n/t !!!!! /t ERROR GENERATING HTML /t !!!!!! \n");
				e.printStackTrace();
			}
		}
		
		try {
			this.generateModuleIndex();
			this.generatePredicateIndex(AllPredicates);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void generateDoc( List<Module> modules){
	
		this.Modules = modules;
		ModuleNames = this.setModuleNamestoHash(Modules);
		this.setModuleLinks(modules);
		for(int i = 0; i < modules.size(); i++){
			try {
				this.generateSinglePage(modules.get(i));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try {
			this.generateModuleIndex();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void generateModuleIndex() throws IOException{
		BufferedReader br;
		br = new BufferedReader(new FileReader("src/main/resources/Index.x"));
	
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null && !line.equalsIgnoreCase("null") ) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        code+= sb.toString();
	    } finally {
	        br.close();
	    }
	    
	    code += "#TOP";
	    code += "\" name=\""+"module_index"+"\">"+"Module Index"+"</a></h1>\n</div>";
	    code += "\n\n <div id=\"b3\" class=\"box\">\n<h3>Modules</h3>\n";
	    code += moduleLinksIndex;
	    code += "</div>";
	    
	    this.generateBottom();
	    
	    code += "\n</body>\n</html>";
	    this.writeToFile("ModuleIndex");
	    code = "";
	    
	}
	
	
	private void generatePredicateIndex(List<Predicate> AllPredicates) throws IOException{
		BufferedReader br;
		br = new BufferedReader(new FileReader("src/main/resources/Index.x"));
	
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null && !line.equalsIgnoreCase("null") ) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        code+= sb.toString();
	    } finally {
	        br.close();
	    }
	    
	    code += "#TOP";
	    code += "\" name=\""+"module_index"+"\">"+"Predicate Index"+"</a></h1>\n</div>";
	    code += "\n\n <div id=\"b3\" class=\"box\">\n<h3>Predicates</h3>\n";
	    code += this.getAllPredicates(AllPredicates);
	    code += "</div>";
	    
	    this.generateBottom();
	    
	    code += "\n</body>\n</html>";
	    this.writeToFile("PredicateIndex");
	    code = "";
	    
	}
	
	private String getAllPredicates(List<Predicate> AllPredicates){
		if (AllPredicates.size() > 0) {
			Collections.sort(AllPredicates, new Comparator<Predicate>() {
				@Override
				public int compare(final Predicate object1, final Predicate object2) {
					return object1.getName().compareTo(object2.getName());
				}
			} );
		}
		
		String PredicateLinks = "";
		char current = '.';
		for(int i = 0; i < AllPredicates.size(); i++){
			
			if(current != AllPredicates.get(i).getName().charAt(0)){
				current = AllPredicates.get(i).getName().charAt(0);
				PredicateLinks += "<h3>" + current + "</h3>";
			}
			Predicate p = AllPredicates.get(i);
			PredicateLinks += "<li><a href=\""+ p.getModule()+".html#"+ p.getName()+p.getArity()+"\">"+ p.getName()+"/"+p.getArity()+"</a></li>\n";
		}
		
		return PredicateLinks;
	}
	
	private void generateSinglePage(Module m) throws IOException{
		BufferedReader br;
			br = new BufferedReader(new FileReader("src/main/resources/beginning_to_Modules.x"));
		
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null && !line.equalsIgnoreCase("null") ) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        code+= sb.toString();
		    } finally {
		        br.close();
		    }
		    code += "#TOP";
		    code += "\" name=\""+m.getName()+"\">"+m.getFile()+"</a></h1>\n";
		    code +=	"<h3 align=\"right\" >" + m.getPath() + "</h3></div>"; 
		    code += "\n\n <div id=\"b2\" class=\"box\">\n<h3>Modules</h3>\n";
		    code += moduleLinks;
		    
		    code += "<h3>Predicates of "+m.getFile()+"</h3>\n";

		    for(int i = 0; i < m.getPredicates().size(); i++){
		    	code += "<li><a href=\"#"+ m.getPredicates().get(i).getName()+m.getPredicates().get(i).getArity()+"\">"+ m.getPredicates().get(i).getName()+"/"+ m.getPredicates().get(i).getArity() +"</a></li>\n";
		    }
		    code += "</div>\n";
		    
		    this.generateModuleInformation(m);
		    
		    code +=   "\n<div id=\"inner\">\n";
		    this.code += "<a class=\"anchor\" name=\"PREDICATES\">Predicates</a>\n";
		    code += "<h3>Predicates:</h3>";
		    for(int i = 0; i < m.getPredicates().size(); i++){
		    	code += "<li><a href=\"#"+ m.getPredicates().get(i).getName()+m.getPredicates().get(i).getArity()+"\">"+ m.getPredicates().get(i).getName()+"/"+ m.getPredicates().get(i).getArity() +"</a></li>\n";
		    }
		    
		    code += "</div><br><br>";
		    
		    this.generatePredicatesInformation(m);
		    
		    this.generateBottom();
		    
		    code += "\n</body>\n</html>";
		    this.writeToFile(m);
		    code = "";
		    
		    this.loadingScreen();
		    
	}
	
	public void loadingScreen(){
		for (int k = 0; k < Compiler.loadingString.length(); k++) System.out.print("\b");
		Compiler.loadingString = "\t";
		for (int k = 0; k <= Compiler.CurrentFileNumber; k++) Compiler.loadingString += "#";
		Compiler.CurrentFileNumber ++;
		Compiler.loadingString += " ("+Compiler.CurrentFileNumber+"/"+Compiler.NumberOfFiles+") Doc Pages generated";
		System.out.print(Compiler.loadingString);

	}
	
	private HashMap<String, Boolean> setModuleNamestoHash(List<Module> ms){
		
		HashMap<String, Boolean> returnMap = new HashMap<String, Boolean>();
		for(int i = 0; i<ms.size(); i++){
			returnMap.put(ms.get(i).getName(), true);
		}
		return returnMap;
	}
	
	private void generateBottom(){
		this.code += "\n<footer id=\"b5\" class=\"box\" >\n<h2 align=\"center\">Sicstus Prolog Doc</h2>";
		this.code += "<h4 align=\"right\">bachelor thesis Michael Birkhoff 2013<h/4>\n";
		this.code += "</footer>";
	}
	
	private void generatePredicatesInformation(Module m){
		
	    for(int i = 0; i < m.getPredicates().size(); i++){
	    	Predicate p = m.getPredicates().get(i);
	    	code += "\n<div id=\"inner\">\n";
			code +=	"<a class=\"anchor\" name=\""+p.getName()+p.getArity()+"\">"+p.getName()+"/" +p.getArity()+"</a>";
	    	code +=	"<h2><a name=\""+p.getName()+p.getArity()+"\">"+p.getName()+"/" +p.getArity()+"</a></h2>\n";
			if(p.getMode()!= null)	code+= "<h3 align=\"center\">Mode: &nbsp;&nbsp;"+p.getMode()+"</h3>\n";
			
			code += "<div id=\"codeblock\" class=\"box\">\n";
			code += "<p><font face=\"monospace\" size=\"4\" color=\"#efecde\">"+ p.getCodeString() + "</font></p>\n";
			code += "</div>\n";
			
			if(p.getAuthor() != null)	code += "<p>"+"Author"+": "+p.getAuthor()+"</p>\n";
			if(p.getDate() != null)	code += "<p>"+"Date"+": "+p.getDate()+"</p>\n";
			if(p.getDescription() != null)	code += "<p>"+"Description"+": "+p.getDescription()+"</p>\n";
			
			for(int k = 0; k < p.getAdditionalEntries().size(); k++){
				code += "<p>"+p.getAdditionalEntries().get(k).getIdentifier()+": "+p.getAdditionalEntries().get(k).getDescription()+"</p>\n";
			}
			code += "<p>"+"Arity"+": "+p.getArity()+"</p>\n";
			code += "<p>"+"Dynamic"+": "+p.isDynamic()+"</p>\n";
			if(p.isMeta()) code += "<p>"+"Meta"+": "+p.getMetaInformation()+"</p>\n";
			if(p.isMultiFile()) code += "<p>"+"Multifile"+": "+p.isMultiFile()+"</p>\n";
			if(p.getBlockingInformation() != null) code += "<p>"+"Blocking"+": "+p.getBlockingInformation()+"</p>\n";
			
			if(p.getCallsNames().size() > 0) code += "<p>"+"Calls:"+"</p>\n";
			
			if(p.getCallsNames().size() > 0) this.code += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"; id=\"table\">\n";
			for(int k = 0; k < p.getCallsNames().size(); k ++){
				Call call = p.getCallsNames().get(k);
				String callName = call.getName().replaceAll("\"", "");
				String callModule = call.getModule();
				int callArity = call.getArity();
				//code += "<div style=\"text-indent:30px;\">\n";
				//this.code+= "<tr>"; before alternatve
				if(k%2 == 0) 	this.code+= "<tr id=\"even\">\n";
				else			this.code+= "<tr id=\"odd\">\n";
				if(callModule.equalsIgnoreCase("built_in") ||  !ModuleNames.containsKey(callModule)){
					if(callArity > 0) 	code += "<td id=\"row\"><p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+callName+"&#47;"+callArity+"</td>\n"; 
					else				code += "<td id=\"row\"><p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+callName+"</td>\n"; 
					if(!callModule.equalsIgnoreCase("built_in") )	code += "<td id=\"row\">"+" \tModule: &nbsp;&nbsp;&nbsp;"+callModule+"</p></td>\n";
					else											code += "<td id=\"row\"></td>\n";
					
				}else{
					code += "<td id=\"row\"><p>Name:&nbsp;&nbsp;&nbsp;&nbsp; <a href=\""+callModule+".html#"+callName+callArity+"\">"+callName+"/"+callArity+"</a></td>\n";
					code += "<td id=\"row\">Module: &nbsp;&nbsp;&nbsp;<a href=\""+callModule+".html\">"+callModule+"</a></p></td>\n";
				}
				//System.out.println(p.getName());
				this.code+= "\n</tr>\n";
				//code += "</div>";
				//code += "<p>"+"Arity"+": \t"+callArity+"</p></div><br>"; unnecessary because arity is usually written as predicate/Arity but still here debuggig reasons
			}
			if(p.getCallsNames().size() > 0) this.code += "\n</table>\n";
			code += "</div><br><br>\n\n\n";
	    }

	    code += "</div>";
	}
	
	private void generateModuleInformation(Module m){
		   
		    this.code += "\n<div id=\"b3\" class=\"box\">\n<div id=\"inner\">\n";
		    this.code += "<a class=\"anchor\" name=\"MODULE_INFO\">Module Information</a>\n";
		    this.code += "<h2 align=\"center\">Module Information</h2><br>\n";
		    if(m.getSPDet()!= null)	this.code += "<h4>spdet:</h4>"+m.getSPDet() +"<br><br>\n";
		    
		    if(m.getMultiFile().size() > 0) this.generateMultifile(m);
		    
		    this.code += "<table  style=\"margin:auto;\"  width=80%>\n<tr>\n<th align=\"left\">Imports</th>\n<th align=\"left\">Exports</th>\n</tr>\n<tr>\n";
		    
		    this.generateImports(m);
		    this.generateExports(m);
		    
		    this.code += "\n</tr>\n</table>\n";
		    
		    this.code += "</div><br>\n";
		    
		    
	}
	
	private void generateMultifile(Module m){
		this.code += "<h4>Multifile Declarations: <h>"; 
		for(int i = 0; i < m.getMultiFile().size(); i++){
			this.code += "&nbsp;&nbsp;&nbsp;&nbsp;";
			this.code+= "<a href=\"#"+m.getMultiFile().get(i).replaceAll("(/)([0-9])*$", "$2")+"\">"+m.getMultiFile().get(i)+"</a>";			// regex $ end of line
		}
		this.code += " </h2>";
	}
	
	private void generateImports(Module m){
		 
		this.code += "<td valign=\"top\">";
		
		for(int i = 0; i < m.getImports().size(); i++){
		    Call imp = m.getImports().get(i);
			
			String importName = imp.getName().replaceAll("\"", "");
			String importModule = imp.getModule().replaceAll("\"", "");
			int importArity = imp.getArity();
		   
			if(!ModuleNames.containsKey(importModule)){
			
				this.code += "<p>"+ "Name: &nbsp;&nbsp;&nbsp;\t" + importName+"/"+ importArity +"</p>\n";
				this.code += "<p>"+"Module: &nbsp;&nbsp;&nbsp;"+" \t"+importModule+"</p>\n";
			}else{
			
				this.code += "<p>Name:&nbsp;&nbsp;&nbsp; <a href=\""+importModule+".html#"+importName+importArity+"\">"+importName+"/"+importArity+"</a></p>\n";
				this.code += "<p>Module: &nbsp;&nbsp;&nbsp;<a href=\""+importModule+".html\">"+importModule+"</a></p>\n";
			}
			this.code += "<br>";
		}
		this.code += "</td>";
	}
	
	private void generateExports(Module m){
		 
		this.code += "<td valign=\"top\">";
		
		for(int i = 0; i < m.getExports().size(); i++){
		    Call exp = m.getExports().get(i);
			
			String exportName = exp.getName();
			int exportArity = exp.getArity();
	
			this.code += "<p>Name:&nbsp;&nbsp;&nbsp; <a href=\"#"+exportName+exportArity+"\">"+exportName+"/"+exportArity+"</a></p>\n";
			this.code += "<br>";
		}
		this.code += "</td>";
	}
	
	private void writeToFile(Module m){
		BufferedWriter writer = null;
		try
		{
			if(!destination.endsWith("/")) destination += "/";
			writer = new BufferedWriter( new FileWriter(this.destination+m.getName().replaceAll("\"", "").replaceAll("\\.\\.", "-")+".html"));
			
			//writer.write(code);
			writer.write(this.prettyPrintCode());
		}
		catch ( IOException e)
		{
			System.out.println("!!\tERROR writing Module HTML File: " + m.getName());

		}
		finally
		{
			try
			{
				if ( writer != null)
					writer.close( );
			}
			catch ( IOException e)
			{
			}
	     }
		
	}
	
	private void writeToFile(String file){
		BufferedWriter writer = null;
		try
		{
			if(!destination.endsWith("/")) destination += "/";
			writer = new BufferedWriter( new FileWriter(this.destination+file+".html"));
			//System.out.println(this.destination+file+".html"); // debug
			//writer.write(code);
			writer.write(this.prettyPrintCode());
		}
		catch ( IOException e)
		{
			System.out.println("!!\tERROR writing HTML File: " + file);
		}
		finally
		{
			try
			{
				if ( writer != null)
					writer.close( );
			}
			catch ( IOException e)
			{
			}
	     }
		
	}
	
	private void setModuleLinks(List<Module> modules){
		if (modules.size() > 0) {
			Collections.sort(modules, new Comparator<Module>() {
				@Override
				public int compare(final Module object1, final Module object2) {
					return object1.getFile().compareTo(object2.getFile());
				}
			} );
		}
		
		char current = '.';
		for(int i = 0; i < modules.size(); i++){
			moduleLinks +=	"<li><a href=\""+ modules.get(i).getName()+".html\">"+ modules.get(i).getFile()+"</a></li>\n";
			
			if(current != modules.get(i).getFile().charAt(0)){
				current = modules.get(i).getFile().charAt(0);
				moduleLinksIndex += "<h3>" + current + "</h3>";
			}
			moduleLinksIndex += "<li><a href=\""+ modules.get(i).getName()+".html\">"+ modules.get(i).getFile()+"</a></li>\n";
		}
	}
	
	public String prettyPrintCode(){
		
		String returnCode = "";
		String splitCode[] = this.code.split("\n"); 
		int tabCounter = 0;
		//int div = 0;
		//int enddiv = 0;
		for(int i = 0; i < splitCode.length; i++){
			
			if(splitCode[i].contains("<"+'/'+"div"))		tabCounter = tabCounter-1;
			if(splitCode[i].contains("<"+'/'+"table"))		tabCounter = tabCounter-1;
			if(splitCode[i].contains("<"+'/'+"tr"))			tabCounter = tabCounter-1;
			
			if(tabCounter< 0) tabCounter = 0;
			for(int k = 0; k < tabCounter; k++){
				returnCode += "\t";
				
			}
			returnCode += splitCode[i] + "\n";
			
			if(splitCode[i].contains("<div"))	tabCounter++;
			if(splitCode[i].contains("<table"))			tabCounter++;
				if(splitCode[i].contains('<'+"tr")) 			tabCounter++;
			
			//System.out.println("div open: " + div + "div closed: " + enddiv);
			
		}
		
		return returnCode;
	}

}

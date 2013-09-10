package src.main.java.parsers;

import java.io.*;
import java.text.Collator;
import java.util.*;

public class HTML_Generator {

	private String code = "";
	private String moduleLinks ="";
	private String moduleLinksIndex = "";
	private String noModuleNameIndex = "";
	private String duplicateModules = "";
	private String moduleDifferNameIndex = "";
	private List<Module> Modules;
	private HashMap<String, Boolean> ModuleNames;
	private String destination = "Doc/";
	private boolean changeIndexDup;
	private boolean changeIndexNoMod;
	private boolean changeIndexDiff;
	
	public HTML_Generator(){
		
	}
	
	public void generateDoc( List<Module> modules, List<Predicate> AllPredicates, List<Predicate> AllUndocumented, List<Predicate> AllEmphasized, List<Module> AllEmphasizedModules, List<EmphasizeList> EmphasizeLists, String destFolder){
		

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
			this.generateCautionIndex();
			this.generatePredicateIndex(AllPredicates, "Predicate Index","PredicateIndex");
			this.generatePredicateIndex(AllUndocumented,"Undocumented Predicates","UndocumentedPredicateIndex");
			//this.generatePredicateIndex(AllEmphasized, "All Emphasized Predicates","EmphasizedPredicateIndex");
			this.generateEmphasizedPredicates(AllEmphasized,EmphasizeLists);
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
	
	public void generateEmphasizedPredicates(List<Predicate> AllEmphasized, List<EmphasizeList> EmphasizeLists) throws IOException{
		
		for(int i = 0; i<EmphasizeLists.size(); i++){
			String name = EmphasizeLists.get(i).getName();
			String link = name.replaceAll("( |/)", "_")+"Index";
			this.generatePredicateIndex(EmphasizeLists.get(i).getPredicates(), name, link);	
		}
		
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
	    code += "\" name=\""+"module_index"+"\">"+ "Emphasized Predicates" +"</a></h1>\n</div>";
	  
	    code += "\n\n <div id=\"b3\" class=\"box\">\n";
	    
	    code += "<div>";
	    	for(int i = 0; i<EmphasizeLists.size(); i++){
	    		String name = EmphasizeLists.get(i).getName();
	    		String link = name.replaceAll("( |/)", "_")+"Index";
	    		code += "<li><a href=\""+ link+".html\">"+name+"</a></li>\n";
	    			
	    	}
	    code += "</div>";
	    code += "<h3>Predicates</h3>\n";
	    code += this.getAllPredicates(AllEmphasized);
	    code += "</div>";
	    
	    this.generateBottom();
	    
	    code += "\n</body>\n</html>";
	    this.writeToFile("EmphasizedPredicateIndex");
	    code = "";
		
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
	
	
	private void generateCautionIndex() throws IOException{
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
	    code += "\n\n <div id=\"b3\" class=\"box\">\n\n";
	   
	    code += "<div>";
	    code += "<h3> No Modules defined</h3>";
	    code += noModuleNameIndex;
	    code += "</div>";
	    
	    code += "<div>";
	    code += "<h3>duplicate Modules</h3>";
	    code += duplicateModules;
	    code += "</div>";
	    
	    code += "<div>";
	    code += "<h3> Modules differ in File Name</h3>";
		code += moduleDifferNameIndex;
		code += "</div>";
		
	    code += "</div>";
	    
	    this.generateBottom();
	    
	    code += "\n</body>\n</html>";
	    this.writeToFile("CautionModuleIndex");
	    code = "";
	    
	}
	
	
	
	private void generatePredicateIndex(List<Predicate> AllPredicates, String Header,String Output) throws IOException{
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
	    code += "\" name=\""+"module_index"+"\">"+ Header +"</a></h1>\n</div>";
	    code += "\n\n <div id=\"b3\" class=\"box\">\n<h3>Predicates</h3>\n";
	    code += this.getAllPredicates(AllPredicates);
	    code += "</div>";
	    
	    this.generateBottom();
	    
	    code += "\n</body>\n</html>";
	    this.writeToFile(Output);
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
		    
		    this.generateSPDet(m);
		    code += "</div>";
		    
		    this.generateBottom();
		    
		    code += "\n</body>\n</html>";
		    this.writeToFile(m);
		    code = "";
		    
			this.loadingScreen();
	}
	
	public void generateSPDet(Module m){
		if(m.getSPDet()!= null)	this.code += "<div id=\"inner\">\n <a class=\"anchor\" name=\"SPDET\"> Determinacy Checker </a> \n <h3>Determinacy Checker: </h3>"+m.getSPDet() +"<br><br>\n</div>\n";
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
		this.code += "\n<footer id=\"b5\" class=\"box\" >\n<h2 align=\"center\">Prolog Documentation Generator for SICStus Prolog</h2>";
		this.code += "<h4 align=\"right\">Bachelor Thesis Michael Birkhoff 2013 	&nbsp;&nbsp;<h/4>\n";
		this.code += "</footer>";
	}
	
	private void generatePredicatesInformation(Module m){
		
	    for(int i = 0; i < m.getPredicates().size(); i++){
	    	Predicate p = m.getPredicates().get(i);
	    	String pName = p.getName() +p.getArity();
	    	code += "\n<div id=\"inner\">\n";
			code +=	"<a class=\"anchor\" name=\""+p.getName()+p.getArity()+"\">"+p.getName()+"/" +p.getArity()+"</a>";
	    	code +=	"<h2><a name=\""+p.getName()+p.getArity()+"\">"+p.getName()+"/" +p.getArity()+"</a></h2>\n";
			if(p.getMode()!= null)	code+= "<h3 align=\"right\">Mode: &nbsp;&nbsp;"+p.getMode()+"</h3>\n";
			
			
			if(p.getAuthor() != null)	code += "<p>"+"Author"+": "+p.getAuthor()+"</p>\n";
			if(p.getDate() != null)	code += "<p>"+"Date"+": "+p.getDate()+"</p>\n";
			if(p.getDescription() != null)	code += "<p>"+"Description"+": "+p.getDescription()+"</p>\n";
			
			for(int k = 0; k < p.getAdditionalEntries().size(); k++){
				code += "<p>"+p.getAdditionalEntries().get(k).getIdentifier()+": "+p.getAdditionalEntries().get(k).getDescription()+"</p>\n";
			}
			//code += "<p>"+"Arity"+": "+p.getArity()+"</p>\n";
			if(p.isDynamic())code += "<p>"+"Dynamic"+": "+p.isDynamic()+"</p>\n";
			if(p.isMeta()) code += "<p>"+"Meta"+": "+p.getMetaInformation()+"</p>\n";
			if(p.isMultiFile()) code += "<p>"+"Multifile"+": "+p.isMultiFile()+"</p>\n";
			if(p.getBlockingInformation() != null) code += "<p>"+"Block"+": "+p.getBlockingInformation()+"</p>\n";
			
			// codeblock
			code += "<input type='button' id=\"button\" value=\"Code\" onclick=\"javascript:hidecode('"+pName+"code"+"')\">\n";
			code += "<div id=\""+pName+"code"+"\"  style=\"display: none;\" >\n";			// hide code div
			code += "<div id=\"codeblock\" class=\"box\">\n";
			code += "<p><font face=\"monospace\" size=\"4\" color=\"#efecde\">"+ p.getCodeString() + "</font></p>\n";
			code += "</div>\n";
			code += "</div>\n";
			
			if(p.getCallsNames().size() > 0){ 
			
				code += "<input type='button' id=\"button\" value=\"Calls\" onclick=\"javascript:hidecode('"+pName+"calls"+"')\">\n";			//hide button
				code += "<div id=\""+pName+"calls"+"\" style=\"display: none;\" >\n";
				code += "<p>"+"Calls:"+"</p>\n";
			}
			
			if(p.getCallsNames().size() > 0) this.code += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"; id=\"table\">\n";
			for(int k = 0; k < p.getCallsNames().size(); k ++){
				Call call = p.getCallsNames().get(k);
				String callName = call.getName().replaceAll("\"", "");
				String callModule = call.getModule();
				String callModuleLink = call.getModuleLink();
				int callArity = call.getArity();
				//code += "<div style=\"text-indent:30px;\">\n";
				//this.code+= "<tr>"; before alternatve
				if(k%2 == 0) 	this.code+= "<tr id=\"even\">\n";
				else			this.code+= "<tr id=\"odd\">\n";
				if(callModule.equalsIgnoreCase("built_in") ||  !ModuleNames.containsKey(callModuleLink)){
					if(callArity > 0) 	code += "<td id=\"row\"><p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+callName+"&#47;"+callArity+"</td>\n"; 
					else				code += "<td id=\"row\"><p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+callName+"</td>\n"; 
					
					if(callModule.equalsIgnoreCase("dynamic predicate")){	
						code += "<td id=\"row\"> <a href=\"#MODULE_INFO\"> "+ callModule+" </a></td>\n";
					}else{
						if(!callModule.equalsIgnoreCase("built_in") ){			
							code += "<td id=\"row\">"+" \tModule: &nbsp;&nbsp;&nbsp;"+callModule+"</p></td>\n";
						}else{ 												
							code += "<td id=\"row\"></td>\n";														
						}
					}
				}else{
					code += "<td id=\"row\"><p>Name:&nbsp;&nbsp;&nbsp;&nbsp; <a href=\""+callModuleLink+".html#"+callName+callArity+"\">"+callName+"/"+callArity+"</a></td>\n";
					code += "<td id=\"row\">Module: &nbsp;&nbsp;&nbsp;<a href=\""+callModuleLink+".html\">"+callModule+"</a></p></td>\n";
				}
				//System.out.println(p.getName());
				this.code+= "\n</tr>\n";
				//code += "</div>";
				//code += "<p>"+"Arity"+": \t"+callArity+"</p></div><br>"; unnecessary because arity is usually written as predicate/Arity but still here debuggig reasons
			}
			if(p.getCallsNames().size() > 0) this.code += "\n</table>\n";
			if(p.getCallsNames().size() > 0) code += "</div>";	// end of div from hide button
			
			this.generateCalled(p);
			
			code += "</div><br><br>\n\n\n";
	    }

	}
	
	private void generateModuleInformation(Module m){
		   
		    this.code += "\n<div id=\"b3\" class=\"box\">\n<div id=\"inner\">\n";
		    this.code += "<a class=\"anchor\" name=\"MODULE_INFO\">Module Information</a>\n";
		    this.code += "<h2 align=\"center\">Module Information</h2><br>\n";
		    
		    if(m.getMultiFile().size() > 0) this.generateMultifile(m);
		    if(m.getDynamics().size() > 0) 	this.generateDynamics(m);
		    this.code += "<h4>"+m.getLines()+" Lines</h4>";
		    this.code += "<h4>"+m.getPredicates().size()+" Predicates</h4>";
		    if(m.getImportedModules().size() > 0)	this.importedModules(m);
		    this.code += "<h4>"+m.getExports().size()+" Exports</h4>";
		    if(m.getImportedModules().size()== 0) 
		    	this.code += "<h4>"+m.getImports().size()+" Imports</h4>";
		    else
		    	this.code += "<h4>"+m.getImports().size()+" specified Imports</h4>";
		    
		    if(m.getExports().size() != 0 || m.getImports().size() != 0){
		    	this.code += "<table  style=\"margin:auto;\"  width=80%>\n<tr>\n<th align=\"left\">Imports</th>\n<th align=\"left\">Exports</th>\n</tr>\n<tr>\n";
		    
		    	this.generateImports(m);
		    	this.generateExports(m);
		    
		    	this.code += "\n</tr>\n</table>\n";
		    }
		    this.code += "</div><br>\n";
		    
		    
	}
	
	private void importedModules(Module m){
		
		this.code += "<h4>Imported Modules: ";
		for(int i = 0; i < m.getImportedModules().size(); i++){
			
			String module = m.getImportedModules().get(i);
			this.code += "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ";
			
			if(ModuleNames.containsKey(module)) 	
				this.code +=  "<a href=\""+module+".html\">"+ module +"</a>" ;
			else
				this.code +=  module;
		}
		this.code += "</h4>";
	}
	
	private void generateDynamics(Module m){
		this.code += "<p><h4>Dynamic Predicates: "; 
		for(int i = 0; i < m.getDynamics().size(); i++){
			String current = m.getDynamics().get(i);
			this.code += "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ";
			this.code+= " <a href=\"#"+current.replaceAll("(/)([0-9])*$", "$2")+"\">"+current+"</a> ";											// regex $ end of line
		}
		this.code += "</p>";
	}
	
	private void generateMultifile(Module m){
		this.code += "<h4>Multifile Declarations: <h>"; 
		for(int i = 0; i < m.getMultiFile().size(); i++){
			this.code += "&nbsp;&nbsp;&nbsp;&nbsp; ";
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
		
		FileOutputStream fop = null;
		File file;
		
		String fileName = m.getLink();
		
		if(!destination.endsWith("/")) destination += "/";
 
		try {
 
			file = new File(this.destination+fileName+".html");
			fop = new FileOutputStream(file);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// get the content in bytes
			byte[] contentInBytes = code.getBytes();
 
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
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
			
			
			boolean newAlphabetIndex = false;
			if(current != modules.get(i).getFile().charAt(0)){
				current = modules.get(i).getFile().charAt(0);
				newAlphabetIndex = true;
			}
			
			
			this.setNormalModuleLinks(modules, i, current, newAlphabetIndex);
			this.setNoModuleNameLinks(modules, i, current, newAlphabetIndex);
			this.setDifferModuleNameLinks(modules, i, current, newAlphabetIndex);
			this.setDuplicateModuleNameLinks(modules, i, current, newAlphabetIndex);
		}
		
		
	}
	
	
	public void setNormalModuleLinks(List<Module> modules, int i, char current, boolean newAlphabet){
		moduleLinks +=	"<li><a href=\""+ modules.get(i).getLink()+".html\">"+ modules.get(i).getFile()+"</a></li>\n";
		
		if(newAlphabet){
			moduleLinksIndex += "<h3>" + current + "</h3>";
		}
		
		moduleLinksIndex += "<li><a href=\""+ modules.get(i).getLink() +".html\">"+ modules.get(i).getFile()+"</a></li>\n";
		
	}
	
	

	
	public void setNoModuleNameLinks(List<Module> modules, int i, char current, boolean newAlphabet){
		
		if(newAlphabet) changeIndexDup = true;
		
		if(modules.get(i).getName().endsWith(".pl")){
			
			if(changeIndexDup){
				noModuleNameIndex += "<h3>" + current + "</h3>";
				changeIndexDup = false;
			}
			noModuleNameIndex += "<li><a href=\""+ modules.get(i).getLink()+".html\">"+ modules.get(i).getFile()+"</a></li>\n";
		}
	}
	
	
	
	public void setDifferModuleNameLinks(List<Module> modules, int i, char current, boolean newAlphabet){
		
		if(newAlphabet) changeIndexNoMod = true;
		
		String modName = modules.get(i).getPathSuffix().substring(0, modules.get(i).getPathSuffix().length()-3);
		boolean differName = !modName.equalsIgnoreCase(modules.get(i).getFile());
		
		if(	(!modules.get(i).getFile().endsWith(".pl")) && differName){
			
			if(changeIndexNoMod){
				moduleDifferNameIndex += "<h3>" + current + "</h3>";
				changeIndexNoMod = false;
			}
			moduleDifferNameIndex += "<li><a href=\""+ modules.get(i).getLink()+".html\">"+ modules.get(i).getFile()+"</a></li>\n";
		}
	}
	
	public void setDuplicateModuleNameLinks(List<Module> modules, int i, char current, boolean newAlphabet){
		
		if(newAlphabet) changeIndexDiff = true;
		
		String previous = null;
		String next = null;
		if((i >0) )	previous = modules.get(i-1).getName();
		if(i < modules.size()-1) next = modules.get(i+1).getName();
		
		Boolean dup = false;
		if(previous != null && previous.equalsIgnoreCase(modules.get(i).getName()))	 	dup = true;
		if(next != null && next.equalsIgnoreCase(modules.get(i).getName()))				dup = true;
		
		if(dup){
			
			if(changeIndexDiff){
				duplicateModules += "<h3>" + current + "</h3>";
				changeIndexDiff = false;
			}
			duplicateModules += "<li><a href=\""+ modules.get(i).getLink()+".html\">"+ modules.get(i).getFile()+"</a></li>\n";
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

	
	
	private void generateCalled(Predicate p){
		
    	String pName = p.getName() +p.getArity();
		
		if(p.getCalled().size() > 0){ 
			
			
			code += "<input type='button' id=\"button\" value=\"Called\" onclick=\"javascript:hidecode('"+pName+"called"+"')\">\n";			//hide button
			code += "<div id=\""+pName+"called"+"\" style=\"display: none;\" >\n";
			code += "<p>"+"Called:"+"</p>\n";
		}
		
		
		Iterator<Call> it= p.getCalled().iterator();
		
		if(p.getCalled().size() > 0) this.code += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"; id=\"table\">\n";
		for(int k = 0; it.hasNext(); k ++){
			
			Call call = it.next();
			String callName = call.getName().replaceAll("\"", "");
			String callModule = call.getModule();
			String callModuleLink = call.getModuleLink();
			int callArity = call.getArity();
			//code += "<div style=\"text-indent:30px;\">\n";
			//this.code+= "<tr>"; before alternatve
			if(k%2 == 0) 	this.code+= "<tr id=\"even\">\n";
			else			this.code+= "<tr id=\"odd\">\n";
			if(callModule.equalsIgnoreCase("built_in") ||  !ModuleNames.containsKey(callModuleLink)){
				if(callArity > 0) 	code += "<td id=\"row\"><p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+callName+"&#47;"+callArity+"</td>\n"; 
				else				code += "<td id=\"row\"><p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+callName+"</td>\n"; 
				
				if(callModule.equalsIgnoreCase("dynamic predicate")){	
					code += "<td id=\"row\"> <a href=\"#MODULE_INFO\"> "+ callModule+" </a></td>\n";
				}else{
					if(!callModule.equalsIgnoreCase("built_in") ){			
						code += "<td id=\"row\">"+" \tModule: &nbsp;&nbsp;&nbsp;"+callModule+"</p></td>\n";
					}else{ 												
						code += "<td id=\"row\"></td>\n";														
					}
				}
			}else{
				code += "<td id=\"row\"><p>Name:&nbsp;&nbsp;&nbsp;&nbsp; <a href=\""+callModuleLink+".html#"+callName+callArity+"\">"+callName+"/"+callArity+"</a></td>\n";
				code += "<td id=\"row\">Module: &nbsp;&nbsp;&nbsp;<a href=\""+callModuleLink+".html\">"+callModule+"</a></p></td>\n";
			}
			//System.out.println(p.getName());
			this.code+= "\n</tr>\n";
			//code += "</div>";
			//code += "<p>"+"Arity"+": \t"+callArity+"</p></div><br>"; unnecessary because arity is usually written as predicate/Arity but still here debuggig reasons
		}
		if(p.getCalled().size() > 0) this.code += "\n</table>\n";
		if(p.getCalled().size() > 0) code += "</div>";	// end of div from hide button
	}
	
	
}

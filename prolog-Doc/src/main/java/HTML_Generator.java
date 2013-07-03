package src.main.java;

import java.io.*;
import java.util.*;

public class HTML_Generator {

	private String code = "";
	private String moduleLinks ="";
	private List<Module> Modules;
	private HashMap<String, Boolean> ModuleNames;
	public HTML_Generator(){
		
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
		    code += m.getName().replaceAll("\"", "")+".html";
		    code += "\" name=\""+m.getName().replaceAll("\"", "")+"\">"+m.getName().replaceAll("\"", "")+"</a></h1> \n	<br><br><br>\n  </div> \n <div id=\"b2\" class=\"box\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#ffffff;\">\n<h3>Modules</h3>\n";
		    code += moduleLinks;
		    
		    code += "<h3>Predicates of "+m.getName()+"</h3>\n";

		    for(int i = 0; i < m.getPredicates().size(); i++){
		    	code += "<h4><a href=\"#"+ m.getPredicates().get(i).getName().replaceAll("\"", "")+m.getPredicates().get(i).getArity()+"\">"+ m.getPredicates().get(i).getName().replaceAll("\"", "")+"/"+ m.getPredicates().get(i).getArity() +"</a></h4>\n";
		    }
		    code += "</div>\n";
		    
		    this.generateModuleInformation(m);
		    
		    code +=   "<div id=\"inner\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6;box-shadow: 4px 4px 18px #666;\">\n";
		    for(int i = 0; i < m.getPredicates().size(); i++){
		    	code += "<h4><a href=\"#"+ m.getPredicates().get(i).getName().replaceAll("\"", "")+m.getPredicates().get(i).getArity()+"\">"+ m.getPredicates().get(i).getName().replaceAll("\"", "")+"/"+ m.getPredicates().get(i).getArity() +"</a></h4>\n";
		    }
		    
		    code += "</div><br><br>";
		    
		    this.generatePredicatesInformation(m);
		    
		    this.generateBottom();
		    
		    code += "	</body>\n</html>";
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
		this.code += "<div id=\"b5\" class=\"box\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#ffffff;\"><h2 align=\"center\">Sicstus Prolog Doc</h2>";
		this.code += "<h4 align=\"right\">bachelor thesis Michael Birkhoff 2013<h/4>";
		this.code += "</div>";
	}
	
	private void generatePredicatesInformation(Module m){
		
	    for(int i = 0; i < m.getPredicates().size(); i++){
	    	Predicate p = m.getPredicates().get(i);
	    	code += "<div id=\"inner\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; box-shadow:4px 4px 18px #666;\">\n";
			code +=	"<h2><a name=\""+p.getName().replaceAll("\"", "")+p.getArity()+"\">"+p.getName().replaceAll("\"", "")+"/" +p.getArity()+"</a></h2>";
			if(p.getMode()!= null)	code+= "<h3 align=\"center\">Mode: &nbsp;&nbsp;"+p.getMode()+"</h3>\n";
			if(p.getAuthor() != null)	code += "<p>"+"Author"+": "+p.getAuthor()+"</p>";
			if(p.getDate() != null)	code += "<p>"+"Date"+": "+p.getDate()+"</p>";
			if(p.getDescription() != null)	code += "<p>"+"Description"+": "+p.getDescription()+"</p>";
			
			for(int k = 0; k < p.getAdditionalEntries().size(); k++){
				code += "<p>"+p.getAdditionalEntries().get(k).getIdentifier()+": "+p.getAdditionalEntries().get(k).getDescription()+"</p>\n";
			}
			code += "<p>"+"Arity"+": "+p.getArity()+"</p>\n";
			code += "<p>"+"Dynamic"+": "+p.isDynamic()+"</p>\n";
			code += "<p>"+"Meta"+": "+p.isMeta()+"</p>\n";
			
			if(p.getCallsNames().size() > 0) code += "<p>"+"Calls:"+"</p>\n";
			
			for(int k = 0; k < p.getCallsNames().size(); k ++){
				Call call = p.getCallsNames().get(k);
				String callName = call.getName().replaceAll("\"", "");
				String callModule = call.getModule().replaceAll("\"", "").replaceAll("/", "_");
				int callArity = call.getArity();
				code += "<div style=\"text-indent:30px;\">\n";
				
				if(callModule.equalsIgnoreCase("built_in") ||  !ModuleNames.containsKey(callModule)){
					if(callArity > 0) 	code += "<p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+callName+"&#47;"+callArity+"&nbsp;&nbsp;&nbsp;&nbsp;"; 
					else				code += "<p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+callName+"&nbsp;&nbsp;&nbsp;&nbsp;"; 
					if(!callModule.equalsIgnoreCase("built_in") ) code += "&nbsp;&nbsp;&nbsp;&nbsp;Module: &nbsp;&nbsp;&nbsp;"+" \t"+callModule+"</p>\n";
					
				}else{
					code += "<p>Name:&nbsp;&nbsp;&nbsp;&nbsp; <a href=\""+callModule+".html#"+callName+callArity+"\">"+callName+"/"+callArity+"</a>&nbsp;&nbsp;&nbsp;&nbsp;";
					code += "&nbsp;&nbsp;&nbsp;&nbsp;Module: &nbsp;&nbsp;&nbsp;<a href=\""+callModule+".html\">"+callModule+"</a></p>\n";
				}
				//System.out.println(p.getName());
				code += "</div><br>";
				//code += "<p>"+"Arity"+": \t"+callArity+"</p></div><br>"; unnecessary because arity is usually written as predicate/Arity but still here debuggig reasons
			}
			code += "<div class=\"box\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#fffffa;\">\n";
			code += "<p>"+ p.getCodeString() + "</p>\n";
			code += "</div>\n";
			code += "</div><br><br>\n\n\n";
	    }

	    code += "</div>";
	}
	
	private void generateModuleInformation(Module m){
		   
		    this.code += "  <div id=\"b3\" class=\"box\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#ffffff;\"><div style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#ffffff;box-shadow:4px 4px 18px #666;\">\n";
			  
		    this.code += "<h2 align=\"center\">Module Information</h2><br>";
		    
		    generateImports(m);
		    generateExports(m);
		    
		    this.code += "</div><br>";
		    
		    
	}
	
	private void generateImports(Module m){
		 
		if ( m.getImports().size()>0) this.code += "<h3>Imports</h3>";
		
		for(int i = 0; i < m.getImports().size(); i++){
		    Call imp = m.getImports().get(i);
			
			String importName = imp.getName().replaceAll("\"", "");
			String importModule = imp.getModule().replaceAll("\"", "");
			int importArity = imp.getArity();
		   
			if(!ModuleNames.containsKey(importModule)){
			
				this.code += "<h4>"+  importName.replaceAll("\"", "")+"/"+ importArity +"</h4>\n";
				this.code += "<p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+importName+"</p>\n";
			}else{
			
				this.code += "<p>Name:&nbsp;&nbsp;&nbsp; <a href=\""+importModule+".html#"+importName+importArity+"\">"+importName+"/"+importArity+"</a></p>\n";
				this.code += "<p>Module: &nbsp;&nbsp;&nbsp;<a href=\""+importModule+".html\">"+importModule+"</a></p>\n";
			}
			this.code += "<br>";
		}
		if ( m.getImports().size()>0) this.code += "<br>";
	}
	
	private void generateExports(Module m){
		 
		if ( m.getExports().size()>0) this.code += "<h3>Exports</h3>";
		
		for(int i = 0; i < m.getExports().size(); i++){
		    Call exp = m.getExports().get(i);
			
			String exportName = exp.getName().replaceAll("\"", "");
			int exportArity = exp.getArity();
	
			this.code += "<p>Name:&nbsp;&nbsp;&nbsp; <a href=\"#"+exportName+exportArity+"\">"+exportName+"/"+exportArity+"</a></p>\n";
			this.code += "<br>";
		}
		if ( m.getExports().size()>0) this.code += "<br>";
	}
	
	private void writeToFile(Module m){
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter( new FileWriter("Doc/"+m.getName().replaceAll("\"", "")+".html"));
			writer.write(code);

		}
		catch ( IOException e)
		{
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
		for(int i = 0; i < modules.size(); i++){
			moduleLinks +=	"<h4><a href=\""+ modules.get(i).getName().replaceAll("\"", "")+".html\">"+ modules.get(i).getName()+"</a></h4>";
		}
	}
}

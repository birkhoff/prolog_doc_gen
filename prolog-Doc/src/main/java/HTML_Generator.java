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
		    code += "\" name=\""+m.getName().replaceAll("\"", "")+"\">"+m.getName().replaceAll("\"", "")+"</a></h1> \n	<br><br><br>\n  </div> \n <div id=\"b2\" class=\"box\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:0px solid #a7bec6; background-color:#ffffff;\">\n<h3>Modules</h3>\n";
		    code += moduleLinks;
		    
		    code += "<h3>Predicates of "+m.getName()+"</h3>\n";

		    for(int i = 0; i < m.getPredicates().size(); i++){
		    	code += "<li><a href=\"#"+ m.getPredicates().get(i).getName().replaceAll("\"", "")+m.getPredicates().get(i).getArity()+"\">"+ m.getPredicates().get(i).getName().replaceAll("\"", "")+"/"+ m.getPredicates().get(i).getArity() +"</a></li>\n";
		    }
		    code += "</div>\n";
		    
		    this.generateModuleInformation(m);
		    
		    code +=   "<div id=\"inner\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6;box-shadow: 4px 4px 18px #666;\">\n";
		    code += "<h3>Predicates:</h3>";
		    for(int i = 0; i < m.getPredicates().size(); i++){
		    	code += "<li><a href=\"#"+ m.getPredicates().get(i).getName().replaceAll("\"", "")+m.getPredicates().get(i).getArity()+"\">"+ m.getPredicates().get(i).getName().replaceAll("\"", "")+"/"+ m.getPredicates().get(i).getArity() +"</a></li>\n";
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
			
			code += "<div class=\"box\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#fffffa;\">\n";
			code += "<p>"+ p.getCodeString() + "</p>\n";
			code += "</div>\n";
			
			if(p.getAuthor() != null)	code += "<p>"+"Author"+": "+p.getAuthor()+"</p>";
			if(p.getDate() != null)	code += "<p>"+"Date"+": "+p.getDate()+"</p>";
			if(p.getDescription() != null)	code += "<p>"+"Description"+": "+p.getDescription()+"</p>";
			
			for(int k = 0; k < p.getAdditionalEntries().size(); k++){
				code += "<p>"+p.getAdditionalEntries().get(k).getIdentifier()+": "+p.getAdditionalEntries().get(k).getDescription()+"</p>\n";
			}
			code += "<p>"+"Arity"+": "+p.getArity()+"</p>\n";
			code += "<p>"+"Dynamic"+": "+p.isDynamic()+"</p>\n";
			if(p.isMeta()) code += "<p>"+"Meta"+": "+p.getMetaInformation()+"</p>\n";
			if(p.getBlockingInformation() != null) code += "<p>"+"Blocking"+": "+p.getBlockingInformation()+"</p>\n";
			
			if(p.getCallsNames().size() > 0) code += "<p>"+"Calls:"+"</p>\n";
			
			if(p.getCallsNames().size() > 0) this.code += "<table border=\"1\"; width=80%>\n";
			for(int k = 0; k < p.getCallsNames().size(); k ++){
				Call call = p.getCallsNames().get(k);
				String callName = call.getName().replaceAll("\"", "");
				String callModule = call.getModule().replaceAll("\"", "").replaceAll("/", "_");
				int callArity = call.getArity();
				//code += "<div style=\"text-indent:30px;\">\n";
				this.code+= "<tr>";
				if(callModule.equalsIgnoreCase("built_in") ||  !ModuleNames.containsKey(callModule)){
					if(callArity > 0) 	code += "<td><p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+callName+"&#47;"+callArity+"</td>"; 
					else				code += "<td><p>"+"Name: &nbsp;&nbsp;&nbsp;"+" \t"+callName+"</td>"; 
					if(!callModule.equalsIgnoreCase("built_in") ) code += "<td>"+" \t"+callModule+"</p></td>\n";
					
				}else{
					code += "<td><p>Name:&nbsp;&nbsp;&nbsp;&nbsp; <a href=\""+callModule+".html#"+callName+callArity+"\">"+callName+"/"+callArity+"</a></td>";
					code += "<td>Module: &nbsp;&nbsp;&nbsp;<a href=\""+callModule+".html\">"+callModule+"</a></p></td>\n";
				}
				//System.out.println(p.getName());
				this.code+= "</tr>";
				//code += "</div>";
				//code += "<p>"+"Arity"+": \t"+callArity+"</p></div><br>"; unnecessary because arity is usually written as predicate/Arity but still here debuggig reasons
			}
			if(p.getCallsNames().size() > 0) this.code += "</table>\n";
			code += "</div><br><br>\n\n\n";
	    }

	    code += "</div>";
	}
	
	private void generateModuleInformation(Module m){
		   
		    this.code += "  <div id=\"b3\" class=\"box\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:0px solid #a7bec6; background-color:#ffffff;\"><div style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#ffffff;box-shadow:4px 4px 18px #666;\">\n";
			  
		    this.code += "<h2 align=\"center\">Module Information</h2><br>";
		    
		    this.code += "<table  width=100%>\n<tr>\n<th align=\"left\">Imports</th>\n<th align=\"left\">Exports</th>\n</tr>\n<tr>";
		    
		    generateImports(m);
		    generateExports(m);
		    
		    this.code += "</table>";
		    
		    this.code += "</div><br>";
		    
		    
	}
	
	private void generateImports(Module m){
		 
		this.code += "<td valign=\"top\">";
		
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
		this.code += "</td>";
	}
	
	private void generateExports(Module m){
		 
		this.code += "<td valign=\"top\">";
		
		for(int i = 0; i < m.getExports().size(); i++){
		    Call exp = m.getExports().get(i);
			
			String exportName = exp.getName().replaceAll("\"", "");
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
			moduleLinks +=	"<li><a href=\""+ modules.get(i).getName().replaceAll("\"", "")+".html\">"+ modules.get(i).getName()+"</a></li>";
		}
	}
}

package src.main.java;

import java.io.*;
import java.util.*;

public class HTML_Generator {

	private String code = "";
	private String moduleLinks ="";
	public HTML_Generator(){
		
	}
	
	public void generateDoc( List<Module> modules){
		
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
		    code += m.getName()+".html";
		    code += "\" name=\""+m.getName()+"\">"+m.getName()+"</a></h1> \n	<br><br><br>\n  </div> \n <div id=\"b2\" class=\"box\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#ffffff;\">\n<h3>Modules</h3>";
		    code += moduleLinks;
		    
		    code += "<h3>Predicates</h3>";

		    for(int i = 0; i < m.getPredicates().size(); i++){
		    	code += "<h4><a href=\"#"+ m.getPredicates().get(i).getName().replaceAll("\"", "")+"\">"+ m.getPredicates().get(i).getName() +"</a></h4>";
		    }
		    code += "</div>";
		    
		    code += "  <div id=\"b3\" class=\"box\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#ffffff;\"><div style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#ffffff;\">";
		    
		    for(int i = 0; i < m.getPredicates().size(); i++){
		    	code += "<h4><a href=\"#"+ m.getPredicates().get(i).getName().replaceAll("\"", "")+"\">"+ m.getPredicates().get(i).getName() +"</a></h4>";
		    }
		    
		    code += "</div><br><br>";
		    
		    for(int i = 0; i < m.getPredicates().size(); i++){
		    	Predicate p = m.getPredicates().get(i);
		    	code += "<div id=\"inner\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6;\">";
				code +=	"<h2><a name=\""+p.getName().replaceAll("\"", "")+"\">"+p.getName()+"</a></h2>";
				if(p.getAuthor() != null)	code += "<p>"+"Author"+": "+p.getAuthor()+"</p>";
				if(p.getDate() != null)	code += "<p>"+"Date"+": "+p.getDate()+"</p>";
				if(p.getDescription() != null)	code += "<p>"+"Description"+": "+p.getDescription()+"</p>";
				System.out.println(p.getAdditionalEntries().size());
				for(int k = 0; k < p.getAdditionalEntries().size(); k++){
					code += "<p>"+p.getAdditionalEntries().get(k).getIdentifier()+": "+p.getAdditionalEntries().get(k).getDescription()+"</p>";
				}
				code += "<p>"+"Arity"+": "+p.getArity()+"</p>";
				code += "<p>"+"Dynamic"+": "+p.isDynamic()+"</p>";
				code += "<p>"+"Meta"+": "+p.isMeta()+"</p>";
				
				if(p.getCallsNames().size() > 0) code += "<p>"+"Calls:"+"</p>";
				
				for(int k = 0; k < p.getCallsNames().size(); k ++){
					Call call = p.getCallsNames().get(k);
					String callName = call.getName();
					String callModule = call.getModule();
					int callArity = call.getArity();
					code += "<div style=\"text-indent:30px;\">";
					code += "<p>"+"Name"+": \t"+callName+"</p>";
					code += "<p>"+"Module"+": \t"+callModule+"</p>";
					code += "<p>"+"Arity"+": \t"+callArity+"</p></div><br><br>";
				}
				code += "<div class=\"box\" style=\"font-family:verdana;padding:40px;border-radius:10px;border:2px solid #a7bec6; background-color:#fffffa;\">";
				code += "<p>"+ p.getCodeString() + "</p>";
				code += "</div>";
				code += "</div><br><br>";
		     }
		    code += "</div>";
		    code += "	</body>\n</html>";
		    this.writeToFile(m);
		    
	}
	
	
	private void writeToFile(Module m){
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter( new FileWriter( m.getName()+".html"));
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
			moduleLinks +=	"<h4><a href=\""+ modules.get(i).getName()+".html\">"+ modules.get(i).getName()+"</a></h4>";
		}
	}
}

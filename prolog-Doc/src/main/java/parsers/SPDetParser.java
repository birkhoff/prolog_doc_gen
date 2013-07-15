package src.main.java.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SPDetParser {

	private String SPDetOutput;
	private String nameOfFile;
	private String SPDetHTML;
	
	public SPDetParser(String File) throws InterruptedException{
		this.nameOfFile = File;
		this.SPDetOutput = "";
		this.SPDetHTML = "";
		this.start_spdet();
	}

	public void start_spdet() throws InterruptedException{
		
		String cmd[] = {"bash", "-c", 
				"spdet " + nameOfFile} ;
	
	ProcessBuilder pb = new ProcessBuilder(cmd);
	pb.redirectErrorStream(true);
	Process process;
	try {
		process = pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		//String line;
		process.getErrorStream().close();
		process.getOutputStream().close();
		String line = "";
		
		for(int counter = 0; ((line = reader.readLine()) != null); counter = 1){
			
			if(counter >0)this.SPDetOutput 	+= line+"\n";
			if(counter >0)this.SPDetHTML 	+= this.parsePredicates(line)+"<br>\n";
		}
			
		process.waitFor();
		
	} catch (IOException e) {
		
		System.out.println("!!!\nError in spdet "+ nameOfFile +"\n"+e.getMessage());
	}
	
	}

	
	private String parsePredicates(String line){
		
		line = line.replaceAll("(:)((([a-z])([A-Z]|[a-z]|[0-9]|_|-)*/[0-9]*))","$1<a href=\"#\n$2\n\">$2</a>\n" );
		line = line.replaceAll("\n([^/]*)/([0-9]*)\n", "$1$2");
		return line;
	}
	
	public String getSPDetOutput() {
		return SPDetOutput;
	}


	public void setSPDetOutput(String sPDetOutput) {
		SPDetOutput = sPDetOutput;
	}

	public String getSPDetHTML() {
		return SPDetHTML;
	}

	public void setSPDetHTML(String sPDetHTML) {
		SPDetHTML = sPDetHTML;
	}
}

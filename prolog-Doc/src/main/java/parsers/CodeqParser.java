package src.main.java.parsers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class CodeqParser {

	
	public List<Predicate> Predicates;
	public Module Module;
	private String ParsedFile;
	private String NameOfFile;
	private String FileName;
	private String NameOfFileNoSlash;
	private List<String> File;
	List<Module>CalledModules;

	private static String varHighlight = "8ce8ff";
	private static String atomHighlight = "f36055";
	private static String stringHighlight = "6490ff";//"94FF8C";
	private static String implicationHighlight = "7ab6f9";
	private static String commentHighlight = "c4f1a3";
	
	public CodeqParser(){
		
		Predicates = new LinkedList<Predicate>();
	}
	
	public CodeqParser(List<Module>Called, String file){
		
		Predicates = new LinkedList<Predicate>();
		ParsedFile = file;
		CalledModules = Called;
		String split[] = file.split("\\/");
		this.FileName = split[split.length-1];
		this.NameOfFile = file;
		this.NameOfFileNoSlash = this.NameOfFile.replaceAll("/", "_").replaceAll("\\.\\.", "-").replaceAll("\"", "");
		
	}
	
	public CodeqParser(String file){
		
		Predicates = new LinkedList<Predicate>();
		ParsedFile = file;
		String split[] = file.split("\\/");
		this.FileName = split[split.length-1];
		this.NameOfFile = file;
		this.NameOfFileNoSlash = this.NameOfFile.replaceAll("/", "_").replaceAll("\\.\\.", "-").replaceAll("\"", "");
		
	}
	
	public void parseXML(String fileName){
		//System.out.println("SaveToArray");
		this.saveFileToArray();
		
		try {
					
			File stocks = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("predicate");

			
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
			
				if (node.getNodeType() == Node.ELEMENT_NODE) {
						
					Element element = (Element) node;
					
					Predicate predicate = new Predicate( this.getValue("name", element).replaceAll("\"", ""), Integer.parseInt( this.getValue("arity", element) ) );
					
					String moduleName = doc.getElementsByTagName("module").item(0).getChildNodes().item(0).getNodeValue().replaceAll("\"","");
					if(moduleName.equalsIgnoreCase("user")) moduleName = this.NameOfFileNoSlash;
					predicate.setModule( moduleName);
					predicate.setStartLines( this.getLineValue("startlines", element));
					predicate.setEndLines(this.getLineValue("endlines", element));

					
					predicate.setCodeString(this.getCode(predicate.getStartLines(), predicate.getEndLines()));
					
					Boolean dynamic = false;
					if( this.getValue("dynamic", element).toLowerCase().contains("true")) dynamic = true;
					predicate.setDynamic(dynamic);
					
					Boolean isMultifile = false;
					if( this.getValue("multifile", element).contains("true")) isMultifile = true;
					predicate.setMultiFile(isMultifile);
					
					Boolean meta = false;
					if( this.getValue("meta", element).toLowerCase().contains("true")){
						meta = true;
						this.setMetaInformation(predicate, element);
					}
					predicate.setMeta(meta);
					
					this.setEmphasizeInformation(predicate, element);
					this.setBlockingInformation(predicate, element);
					this.setModeInformation(predicate, element);
										
					
					NodeList calls = element.getElementsByTagName("call");
										
					for (int j = 0; j <calls.getLength(); j++) {
						
						Node call = calls.item(j);
						Element call_element = (Element) call;
						if (call.getNodeType() == Node.ELEMENT_NODE) {
							String callName = this.getValue("name", call_element);	//former CDataValue
							String callModule = this.getValue("module", call_element).replaceAll("\"", "").replaceAll("\\.\\.", "-");
							String callModuleLink = this.getValue("module", call_element).replaceAll("\"", "").replaceAll("\\.\\.", "-");;
							String callArity = this.getValue("arity", call_element);
							
							if (callModule.equalsIgnoreCase("user")) {
								callModuleLink	= this.NameOfFileNoSlash;
								callModule 		= this.FileName;
							}
							predicate.addCallNames(callName, callModule, callModuleLink,callArity);
							//this.updateCalled(predicate.getName(), predicate.getArity(), moduleName,callName, callArity,callModule,callModuleLink);
							if(this.CalledModules!=null){	//if constructor is called without the calledModule parameter.
								String displayName = moduleName;
								if(moduleName.endsWith(".pl"))  displayName = this.FileName;
								this.updateCalled(callName, callArity,callModuleLink, predicate.getName(), predicate.getArity(), moduleName, displayName);
							}
						}
						
					}
					Predicates.add(predicate);
				}
				
			}
			
			this.parseModuleInformation(doc);
			
		} catch (Exception ex) {
			System.out.println("filename: "+ NameOfFile);
			ex.printStackTrace();
		}
		
	}
	
private void updateCalled(String Call, String Arity, String ModuleName, String Called, int CalledArity, String CalledModuleLink, String CalledModule){
	

	if(!ModuleName.equalsIgnoreCase("built_in")){
	
		boolean addModule = true;
		for( int i = 0; i < this.CalledModules.size(); i++){
			
			Module currentModule = this.CalledModules.get(i);
			if(currentModule.getName().equalsIgnoreCase(ModuleName)){
				
				addModule = false;
				boolean addPredicate = true;
				
				for(int k = 0; k<currentModule.getPredicates().size(); k++){
					
					Predicate currentPredicate = currentModule.getPredicates().get(k);
					
					if(currentPredicate.getName().equalsIgnoreCase(Call) && currentPredicate.getArity()==Integer.parseInt(Arity)){	// checks if name and arity equals of the Module which is called
						
						addPredicate = false;
						currentPredicate.addCalled(Called, CalledModule, CalledModuleLink, Integer.toString(CalledArity));
						//System.out.println("Added:\t"+currentModule.getName()+": "+ currentPredicate.getName() +" Called by "+ CalledModuleLink+" "+Called+CalledArity);
					}
				}
				
				if(addPredicate){
					Predicate predicate = new Predicate(Call, Integer.parseInt(Arity));
					predicate.addCalled(Called, CalledModule, CalledModuleLink, Integer.toString(CalledArity));
					currentModule.getPredicates().add(predicate);
					
					//System.out.println("Added:\t"+currentModule.getName()+": "+ predicate.getName() +" Called by "+ CalledModuleLink+" "+Called+CalledArity);
				}
			}
		}
		if(addModule){
			
			Module module = new Module(ModuleName);
			
			Predicate predicate = new Predicate(Call, Integer.parseInt(Arity));
			predicate.addCalled(Called, CalledModule, CalledModuleLink, Integer.toString(CalledArity));
			List<Predicate> newPredicates = new LinkedList<Predicate>();
			newPredicates.add(predicate);
			module.setPredicates(newPredicates);
			
			//System.out.println("addCalled");
			
			CalledModules.add(module);
			//System.out.println("\t"+module.getName());
		}
	}
}
	
private void setMetaInformation(Predicate predicate, Element node){
	String MetaInformation = "";
	
		
	String current = this.getValue("meta_args", node);
	
	String List[] = current.split("\t");
	
	for(int i = 0; i < List.length; i++){
		MetaInformation += List[i].replaceAll("\\[", "(").replaceAll("\\]", ")") + " ";
	}
	
	predicate.setMetaInformation(MetaInformation);
}

private void setBlockingInformation(Predicate predicate, Element node){
	
	String BlockingInformation = null;
	
	NodeList blockings = node.getElementsByTagName("blocking");
		
	if(blockings.getLength()>0) BlockingInformation = "";
		
	for (int j = 0; j <blockings.getLength(); j++) {
		
		Node blocking = blockings.item(j);

		if (blocking.getNodeType() == Node.ELEMENT_NODE) {
			
			String currentBlocking = blocking.getFirstChild().getNodeValue();
			BlockingInformation += currentBlocking.replaceAll("\\[", "(").replaceAll("\\]", ")") + " ";
		}
		
	}
		
	predicate.setBlockingInformation(BlockingInformation);
}

private void setEmphasizeInformation(Predicate predicate, Element node){
	
	
	NodeList emphasize = node.getElementsByTagName("emphasize");
		
	if(emphasize.getLength()>0)
		predicate.setEmphasize(true);
	else
		predicate.setEmphasize(false);
		
	for (int j = 0; j <emphasize.getLength(); j++) {
		
		Node emph = emphasize.item(j);

		if (emph.getNodeType() == Node.ELEMENT_NODE) {
			predicate.addEmphasizeList(emph.getFirstChild().getNodeValue().replaceAll("\\[", "(").replaceAll("\\]", ")"));
		}
		
	}
}

private void setModeInformation(Predicate predicate, Element node){
	
	String ModeInformation = null;
	
	NodeList modes = node.getElementsByTagName("mode");
		
	if(modes.getLength()>0) ModeInformation = "";
		
	for (int j = 0; j <modes.getLength(); j++) {
		
		Node mode = modes.item(j);

		if (mode.getNodeType() == Node.ELEMENT_NODE) {
			
			String currentMode = mode.getFirstChild().getNodeValue();
			ModeInformation += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+currentMode.replaceAll("\\[", "(").replaceAll("\\]", ")") + " ";
		}
		
	}
		
	predicate.setMode(ModeInformation);
}

private void saveFileToArray(){
	try{
		
		this.File = new LinkedList<String>();
		this.File.add("Initialize");
		
		FileInputStream fstream = new FileInputStream(ParsedFile);
		// Get object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader buffread = new BufferedReader(new InputStreamReader(in));
		String line;
		//Read File Line By Line
		while ( (line = buffread.readLine()) != null) {
			
			  this.File.add(line);
		}
		//Close the input stream
		in.close();
	}catch (Exception e){//Catch exception if any
		System.err.println("Error: " + e.getMessage());
	}
	
	//for(int i = 0; i < File.size(); i++)
	//	System.out.println("\t"+i+": "+File.get(i));
}
	
private String getCode(int starts[], int ends[]){
	
	String returnCode = "";
	
	for (int k = starts.length-1; k >=0 ; k--) {
		
		for(int i = starts[k]; i <= ends[k]; i ++){
			
			if (i>=1 && i<= File.size()){
				if (starts[k] <= i && ends[k] >= i) {
					returnCode += 	"<br>";
					if (i != starts[k]) 	returnCode += "&nbsp;&nbsp;&nbsp;";	
					
					returnCode += this.highlightCode(File.get(i)) ;
				
					if (i == ends[k]){
						if (File.get(i).matches(".*\\.((%+.*)| |\t|\n|(/\\+.*))*"))
						returnCode += "<br>";
						else{
							for(int j = i+1; !File.get(j-1).matches(".*\\.((%+.*)| |\t|\n|/\\*.*)*") && j < File.size(); j++){
								returnCode += "<br>"+ this.highlightCode(File.get(j));
							}
							returnCode += "<br>";
						}
					}
				}
			}else{
				System.out.println("\n FATAL ERROR TRYING TO READ PREDICATE FROM LINE "+i +"WITH A FILE LENGTH OF "+File.size());
			}		
		}
	}
	returnCode = returnCode.replaceAll("\t", "\t&nbsp;&nbsp;&nbsp;");
	return returnCode;
}

private String highlightCode(String line){

	String current = " "+line;
	String stringsOfCurrent[] = current.split("\"");
	String returnString = "";
	
	for(int i = 0; i < stringsOfCurrent.length; i++){
		if(i % 2 == 0){
			String commentsOfCurrent[] = stringsOfCurrent[i].split("%");
			
			commentsOfCurrent[0] = commentsOfCurrent[0].replaceAll("<", "&lt;");
			commentsOfCurrent[0] = commentsOfCurrent[0].replaceAll(">", "&gt;");
			commentsOfCurrent[0] = commentsOfCurrent[0].replaceAll("(,| |\\)|\\(|\\||\t|\\]|\\[|:)((([a-z])([A-Z]|[a-z]|[0-9]|_|-)*)|(\'.*\'))","$1\n#<\n$2\n#>\n" );	// #< means atom_start and will be replaced by <Font> and #> as </Font> \n used as escaping character to ensure correct highlighting because of split on \n it is a neutral token
			commentsOfCurrent[0] = commentsOfCurrent[0].replaceAll("(,| |\\)|\\(|\\||\t|\\]|\\[|:)(((_|[A-Z])([A-Z]|[a-z]|[0-9]|_|-)*)|(\'.*\'))","$1\n##<\n$2\n##>\n" );	// ## stands for Var highlighting	
			commentsOfCurrent[0] = commentsOfCurrent[0].replaceAll(":-", "<FONT COLOR=\""+implicationHighlight+"\">:-</FONT COLOR>");
			commentsOfCurrent[0] = commentsOfCurrent[0].replaceAll("-->", "<FONT COLOR=\""+implicationHighlight+"\">--></FONT COLOR>");
			commentsOfCurrent[0] = commentsOfCurrent[0].replaceAll("->", "<FONT COLOR=\""+implicationHighlight+"\">-></FONT COLOR>");
			stringsOfCurrent[i] = commentsOfCurrent[0];
			
			for(int k = 1; k < commentsOfCurrent.length; k++){
				stringsOfCurrent[i] += "<FONT COLOR=\""+commentHighlight+"\">%"+commentsOfCurrent[k]+"</FONT COLOR>";
			}
		
		}else{
			
			stringsOfCurrent[i] = "<FONT COLOR=\""+stringHighlight+"\">\""+stringsOfCurrent[i]+"\"</FONT COLOR>";
		}
		returnString += stringsOfCurrent[i];
	}
	
	returnString = returnString.replaceAll("\n#<\n","<FONT COLOR=\""+atomHighlight+"\">" );
	returnString = returnString.replaceAll("\n#>\n","</FONT>" );
	
	returnString = returnString.replaceAll("\n##<\n","<FONT COLOR=\""+varHighlight+"\">" );
	returnString = returnString.replaceAll("\n##>\n","</FONT>" );
	
	
	return returnString;
}

private void parseModuleInformation(Document dc){
		
		String nameOfModule =  dc.getElementsByTagName("module").item(0).getChildNodes().item(0).getNodeValue().replace("\"", "");
		String fileName = nameOfModule;				// name of the file without path
		if(nameOfModule.equalsIgnoreCase("user")){

			nameOfModule = this.NameOfFileNoSlash;
			fileName = this.FileName;
		}
		
		String LineNumber =  dc.getElementsByTagName("module_startline").item(0).getChildNodes().item(0).getNodeValue().replace("\"", "");
		int LineDef = Integer.parseInt(LineNumber);
		
		Module = new Module(nameOfModule);
		NodeList importModules = dc.getElementsByTagName("imported_module");
		NodeList importNodes = dc.getElementsByTagName("import");
		NodeList exportNodes = dc.getElementsByTagName("export");
		NodeList multiFileNodes = dc.getElementsByTagName("multifile");
		NodeList dynamicNodes = dc.getElementsByTagName("dynamics");
		

		Module.setLineDef(LineDef);
		Module.setFile(fileName);
		Module.setLink(this.NameOfFileNoSlash);
		Module.setPathSuffix(FileName);
		Module.setPath(NameOfFile);
		Module.setLines(File.size());
		
		this.parseImportedModules(importModules);
		this.parseImports(importNodes);
		this.parseExports(exportNodes);
		this.parseMultiFile(multiFileNodes);
		this.parseDynamics(dynamicNodes);
			
	}
	
	private void parseMultiFile(NodeList nodes){
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			String multiFile = node.getFirstChild().getNodeValue();
			if(!(multiFile.contains("false") || multiFile.contains("true") ))	Module.addMultiFile(multiFile);
	
		}
	}
	
	private void parseDynamics(NodeList nodes){
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			String dynamic = node.getFirstChild().getNodeValue();
			Module.addDynamics(dynamic);
	
		}
	}

	
	private void parseImportedModules(NodeList nodes){
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
		
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element import_element = (Element) node;
				String module = this.getValue("name", import_element).replaceAll("\"", "");
				
				Module.addImportedModule(module);
								
			}
		}
		//System.out.println("Length:"+ Module.getImports().size());
	}
	
	private void parseImports(NodeList nodes){
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
		
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element import_element = (Element) node;
				String callName = this.getValue("name", import_element).replaceAll("\"", "");;
				
				String callModule = this.getValue("module", import_element).replaceAll("\"", "");
				String callArity = this.getValue("arity", import_element);
				Module.addImport(callName, callModule, callArity);
								
			}
		}
		//System.out.println("Length:"+ Module.getImports().size());
	}
	
	private void parseExports(NodeList nodes){
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
		
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element import_element = (Element) node;
				String callName = this.getValue("name", import_element).replaceAll("\"", "");
				String callModule = this.getValue("module", import_element);
				String callArity = this.getValue("arity", import_element);
				Module.addExport(callName, callModule, callArity);					
			}
		}
		//System.out.println("Length:"+ Module.getExports().size());
	}
	
	
	public String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
	
	/*
	public String getCDataValue(String tag, Element element) {
		
		 NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
		    String data;

		    for(int index = 0; index < list.getLength(); index++){
		        if(list.item(index) instanceof CharacterData){
		            CharacterData child = (CharacterData) list.item(index);
		            data = child.getData();

		            if(data != null && data.trim().length() > 0)
		                return child.getData();
		        }
		    }
		    return "";
            
		
	}*/
	
	
	public int[] getLineValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		
		String[] values= ((node.getNodeValue().replaceAll("\\[", "")).replaceAll("\\]", "")).split(",");
		int intArray[] = new int[values.length];
		for(int i = 0; i < values.length; i ++){
			intArray[i] = Integer.parseInt(values[i]);
		}
		return intArray;
		
	}
		
}

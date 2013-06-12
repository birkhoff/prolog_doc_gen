package src.main.java;

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
	
	public CodeqParser(){
		
		Predicates = new LinkedList<Predicate>();
	}
	
	public CodeqParser( String file){
		
		Predicates = new LinkedList<Predicate>();
		ParsedFile = file;
	}
	
	public void parseXML(String fileName){
		
		try {
			
			
			File stocks = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();
			
			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			
			NodeList nodes = doc.getElementsByTagName("predicate");
	
			
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
			
				if (node.getNodeType() == Node.ELEMENT_NODE) {
						
					Element element = (Element) node;
					
					Predicate predicate = new Predicate( this.getValue("name", element), Integer.parseInt( this.getValue("arity", element) ) );
					
					predicate.setModule( doc.getElementsByTagName("module").item(0).getChildNodes().item(0).getNodeValue());
					predicate.setStartLines( this.getLineValue("startlines", element));
					predicate.setEndLines(this.getLineValue("endlines", element));
					
					predicate.setCodeString(this.getCode(predicate.getStartLines(), predicate.getEndLines()));
					
					Boolean dynamic = false;
					if( this.getValue("dynamic", element).toLowerCase().contains("true")) dynamic = true;
					predicate.setDynamic(dynamic);
					
					Boolean meta = false;
					if( this.getValue("meta", element).toLowerCase().contains("true")) meta = true;
					predicate.setDynamic(meta);
					
					NodeList calls = element.getElementsByTagName("call");
										
					for (int j = 0; j <calls.getLength(); j++) {
						
						Node call = calls.item(j);
						Element call_element = (Element) call;
						if (call.getNodeType() == Node.ELEMENT_NODE) {
							String callName = this.getValue("name", call_element);
							String callModule = this.getValue("module", call_element);
							String callArity = this.getValue("arity", call_element);
							predicate.addCallNames(callName, callModule, callArity);
						
						}
						
					}
					Predicates.add(predicate);
				}
				
			}
			
			this.parseModuleInformation(doc);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
private String getCode(int starts[], int ends[]){
	
	String returnCode = "";
	
	try{
		  // Open the file that is the first 
		  // command line parameter
		  FileInputStream fstream = new FileInputStream(ParsedFile);
		  // Get the object of DataInputStream
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		  //Read File Line By Line
		  for (int i = 1; (strLine = br.readLine()) != null; i++)   {
		  // Print the content on the console
			  for (int k = 0; k < starts.length; k++) {
				  if(starts[k] <= i && ends[k] >= i ){
					  returnCode += 	"<br>"+strLine;
				  }
			  }
		  }
		  //Close the input stream
		  in.close();
		    }catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
		  }
		  
	
	
	return returnCode;
}
	
	private void parseModuleInformation(Document dc){
		
		String nameOfModule =  dc.getElementsByTagName("module").item(0).getChildNodes().item(0).getNodeValue();
		Module = new Module(nameOfModule);
		NodeList importNodes = dc.getElementsByTagName("import");
		NodeList exportNodes = dc.getElementsByTagName("export");
		
		this.parseImports(importNodes);
		this.parseExports(exportNodes);
			
	}
	
	private void parseImports(NodeList nodes){
		System.out.println("!!!!!!!\n: ");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
		
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element import_element = (Element) node;
				String callName = this.getValue("name", import_element);
				System.out.println("Name: "+callName);
				String callModule = this.getValue("module", import_element);
				System.out.println("callModule: "+ callModule);
				String callArity = this.getValue("arity", import_element);
				System.out.println("callArity: "+ callArity);
				Module.addImport(callName, callModule, callArity);
				System.out.println();
								
			}
		}
		System.out.println("Length:"+ Module.getImports().size());
	}
	
	private void parseExports(NodeList nodes){
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
		
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element import_element = (Element) node;
				String callName = this.getValue("name", import_element);
				String callModule = this.getValue("module", import_element);
				String callArity = this.getValue("arity", import_element);
				Module.addExport(callName, callModule, callArity);
								
			}
		}
		System.out.println("Length:"+ Module.getExports().size());
	}
	
	
	public String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
	
	
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

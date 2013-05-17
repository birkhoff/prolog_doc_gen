package src.main.java;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CodeqParser {

	public static void main(String args[]) {
		try {
	
			File stocks = new File(args[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();
			
			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			
			//NodeList nodes = doc.getChildNodes();
			NodeList nodes = doc.getElementsByTagName("predicate");
			
			System.out.println("==========================");
			
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
			
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					/*
					 * <arity>2</arity>
		<code>[[v2,[v3|v4]],element(v2,v4),[v0,[v0|v1]],]</code>
		<startlines>[12,11]</startlines>
		<endlines>[13,11]</endlines>
		<dynamic>false</dynamic>
		<meta>false</meta>
		<calls>
			<call>
				<module>"user"</module>
				<name>"element"</name>
				<arity>2</arity>
			</call>
		</calls>
	</predicate>
					 */
					
					Element element = (Element) node;
					System.out.println("-----Predicate------");
					
					System.out.println("name: " + getValue("name", element));
					System.out.println("arity: " + getValue("arity", element));
					System.out.println("code: " + getValue("code", element));
					System.out.println("startlines: " + getValue("startlines", element));
					System.out.println("endlines: " + getValue("endlines", element));
					System.out.println("dynamic: " + getValue("dynamic", element));
					System.out.println("meta: " + getValue("meta", element));
					
					
					NodeList calls = element.getElementsByTagName("call");
					if( calls.getLength()>0) System.out.println("calls:");
					
					for(int j = 0; j <calls.getLength(); j++){
						
						Node call = calls.item(j);
						Element call_element = (Element) call;
						if( call.getNodeType() == Node.ELEMENT_NODE ){
							
							System.out.println("\tname: " + getValue("name", call_element));
							System.out.println("\tarity: " + getValue("arity", call_element));
							System.out.println("\tmodule: " + getValue("module", call_element));
							
						}
						System.out.println();
					}
					
					
				}
				System.out.println("------------------");
				System.out.println( );
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
}

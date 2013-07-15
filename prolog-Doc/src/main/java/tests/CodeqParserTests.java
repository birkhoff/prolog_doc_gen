package src.main.java.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import src.main.java.parsers.*;

public class CodeqParserTests {

	@Test
	public void testSize() {
		  
		CodeqParser codeqParser = new CodeqParser("src/main/java/tests/TestFiles/test1.pl");
		codeqParser.parseXML("src/main/java/tests/TestFiles/test1.xml");
		assertTrue(codeqParser.Predicates.size() == 3);
	}
	
	@Test
	public void testPredicates() {
		  
		CodeqParser codeqParser = new CodeqParser("src/main/java/tests/TestFiles/test1.pl");
		codeqParser.parseXML("src/main/java/tests/TestFiles/test1.xml");
		List<Predicate> Predicates = codeqParser.Predicates;
		assertTrue(Predicates.get(0).getName().equalsIgnoreCase("\"element\""));
		assertTrue(Predicates.get(1).getName().equalsIgnoreCase("\"is_not_visited\""));
		assertTrue(Predicates.get(2).getName().equalsIgnoreCase("\"istrue\""));
	}


	@Test
	public void testLines() {
		  
		CodeqParser codeqParser = new CodeqParser("src/main/java/tests/TestFiles/test1.pl");
		codeqParser.parseXML("src/main/java/tests/TestFiles/test1.xml");
		List<Predicate> Predicates = codeqParser.Predicates;
		assertTrue(Predicates.get(0).getStartLines()[0]==11 && Predicates.get(0).getStartLines()[1]==10);
		assertTrue(Predicates.get(1).getStartLines()[1]==22 && Predicates.get(1).getStartLines()[2]==21 && Predicates.get(1).getEndLines()[1]==23 && Predicates.get(1).getEndLines()[0]==31);
		assertTrue(Predicates.get(1).getStartLines()[0]==29 && Predicates.get(1).getEndLines()[2]==21);
		assertTrue(Predicates.get(2).getStartLines()[0] == Predicates.get(2).getEndLines()[0]);
	}

	
	@Test
	public void testMeta() {
		  
		CodeqParser codeqParser = new CodeqParser("src/main/java/tests/TestFiles/test2.pl");
		codeqParser.parseXML("src/main/java/tests/TestFiles/test2.xml");
		List<Predicate> Predicates = codeqParser.Predicates;
		assertTrue(Predicates.get(2).getMetaInformation().contains("foo_bar(+,+)"));
		assertTrue(Predicates.get(1).getMetaInformation().contains("foo(:,:)"));

	}
	
	@Test
	public void testBlocking(){
		CodeqParser codeqParser = new CodeqParser("src/main/java/tests/TestFiles/test2.pl");
		codeqParser.parseXML("src/main/java/tests/TestFiles/test2.xml");
		List<Predicate> Predicates = codeqParser.Predicates;
		assertTrue(Predicates.get(3).getBlockingInformation().contains("merge(-,?,-)"));
	}
	
	@Test
	public void testMultiFile(){
		CodeqParser codeqParser = new CodeqParser("src/main/java/tests/TestFiles/test2.pl");
		codeqParser.parseXML("src/main/java/tests/TestFiles/test2.xml");
		Module m = codeqParser.Module;
		assertTrue(m.getMultiFile().get(0).contains("foo/2"));
		assertTrue(m.getMultiFile().get(1).contains("bar/0"));
	}
	
	
}

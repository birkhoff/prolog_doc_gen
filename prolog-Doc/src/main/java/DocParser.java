package src.main.java;

import java.util.*;

import node.*;
import analysis.*;

public class DocParser extends DepthFirstAdapter{
	
	public List<HashMap>	 Predicates;
	
	public List<DocInformation>	 DocInfo;
	
	private String currentEntry;
	private int currentPredicate;
	
	private String author = "Author";
	private String description = "Description";
	private String date = "Date";
	private String line = "Line";
	private String currentEntryKey;
	
	
	public DocParser(){
		Predicates = new LinkedList<HashMap>();
		DocInfo = new LinkedList<DocInformation>();
	}
	
	@Override
	public void caseAStartAst( AStartAst docs){
		
		int docs_size = docs.getAst().size();
		currentPredicate = 0;

		
		
		for(int i = 0; i < docs_size; i++){
			HashMap<String, String > EntriesTable = new HashMap<String, String>();
			Predicates.add(EntriesTable);
			
			DocInformation DocEntry = new DocInformation();
			DocInfo.add(DocEntry);
			
			docs.getAst().get(i).apply(this);
			currentPredicate ++;
		}
	}
	
	@Override
	public void caseAEntriesAst( AEntriesAst entries){
		
		int entries_size = entries.getAst().size();
		for(int i = 0; i < entries_size; i ++){
			
			entries.getAst().get(i).apply(this);
		}
	}
	
	@Override
	public void caseAAuthorAst( AAuthorAst entries){
		
		int entries_size = entries.getAst().size();
		String returnAuthor = "";
		for (int i = 0; i < entries_size; i ++) {
			
			String currentAuthor = entries.getAst().get(i).toString();
			//System.out.println( currentAuthor );
			returnAuthor += currentAuthor;
			entries.getAst().get(i).apply(this);
		}
		
		((HashMap) Predicates.get(currentPredicate)).put(author, returnAuthor);
		
		DocInfo.get(currentPredicate).setAuthor(returnAuthor);
		
	}
	
	@Override
	public void caseADateAst( ADateAst entries){
		
		int entries_size = entries.getAst().size();
		String returnDate = "";
		for (int i = 0; i < entries_size; i ++) {
			
			String currentDate = entries.getAst().get(i).toString();
			//System.out.println( currentDate );
			returnDate += currentDate;
			entries.getAst().get(i).apply(this);
		}
		
		((HashMap) Predicates.get(currentPredicate)).put(date, returnDate);
		DocInfo.get(currentPredicate).setDate(returnDate);
		
	}
	
	@Override
	public void caseADescrAst( ADescrAst entries){
		
		int entries_size = entries.getAst().size();
		String returnDescr = "";
		for (int i = 0; i < entries_size; i ++) {
			
			String currentDescr = entries.getAst().get(i).toString();
			//System.out.println( currentDescr );
			returnDescr += currentDescr;
			
			//Node entry = (Node) entries.getAst().get(i).clone();
			//System.out.println( ((Token) entry).getLine() );
			
			entries.getAst().get(i).apply(this);
			
		}
		
		((HashMap) Predicates.get(currentPredicate)).put(description, returnDescr);
		DocInfo.get(currentPredicate).setDescription(returnDescr);
		
		
	}
	
	@Override
	public void caseAAtDocAst(AAtDocAst entry){
		
		String entryKey = entry.getIdentifier().toString().replaceAll("@", "");
		String entryValue = entry.getDescription().toString();
		String returnDescr = "";
		for (int i = 0; i < entry.getDescription().size(); i ++) {
			
			String currentDescr = entry.getDescription().get(i).toString();
			returnDescr += currentDescr;
			entry.getDescription().get(i).apply(this);
			
		}
		DocInfo.get(currentPredicate).addAdditionalEntry(entryKey, returnDescr); 
		((HashMap) Predicates.get(currentPredicate)).put(entryKey, returnDescr);
	}
	
	
	
	@Override
	public void caseAStringBAst(AStringBAst token){
		
		((HashMap) Predicates.get(currentPredicate)).put(line, token.getStringDocStar().getLine());
		DocInfo.get(currentPredicate).setLine(token.getStringDocStar().getLine());
	}
}

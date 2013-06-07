package src.main.java;

import java.util.LinkedList;
import java.util.List;

public class InformationMerger {

	public List<Predicate> MergedPredicates;
	//public List<DocInformation> DocInfos;
	
	
	
	public InformationMerger(){
		
		this.MergedPredicates = new LinkedList<Predicate>();
	}
	
	public void mergeModuleInformation( List<Predicate> predicates, List<DocInformation> docs){
		
		for (int k = 0; k < docs.size(); k++) {
			int minValue = -1;
			Predicate attach = null; 
			DocInformation currentDoc = docs.get(k);
			
			for (int i= 0; i < predicates.size(); i++) {
				
				if (!predicates.get(i).isAttached() ){
					int currentMinSize = -1;
					int docLine = docs.get(k).getLine();
					int predicateLines[] = predicates.get(i).getStartLines();
					
					for (int j = 0; j < predicateLines.length ;j++) {
						int diff = Math.abs( predicateLines[j]-docLine );
						//if(j <= 0) currentMinSize = diff;
						if(currentMinSize > diff || currentMinSize == -1) currentMinSize = diff;
					}
					
					if (minValue > currentMinSize || minValue == -1) {
						minValue = currentMinSize;
						attach = predicates.get(i);
					}
				}
			}
			
			if(attach != null && currentDoc != null ){
				attach.setAttached(true);
				System.out.println("mergin " + attach.getName() + "with " + currentDoc.getAuthor());
				MergedPredicates.add( this.mergePredicate(attach, currentDoc) );
				System.out.println( attach.getName() );
			}
			
		}
		
	}
	
	public Predicate mergePredicate( Predicate predicate, DocInformation doc){
		
		Predicate merged = new Predicate(predicate.getName(), predicate.getArity());
		
		merged.setCalls(predicate.getCalls());
		merged.setCallsNames(predicate.getCallsNames());
		merged.setStartLines(predicate.getStartLines());
		merged.setEndLines(predicate.getEndLines());
		merged.setMeta(predicate.isMeta());
		merged.setDynamic(predicate.isDynamic());
		merged.setModule(predicate.getModule());
		
		merged.setDate(doc.getDate());
		merged.setAuthor(doc.getAuthor());
		merged.setDescription(doc.getDescription());
		merged.setAdditionalEntries(predicate.getAdditionalEntries());
				
		return merged;
	}
	
}

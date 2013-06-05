package src.main.java;

import java.util.LinkedList;
import java.util.List;

public class InformationMerger {

	//public List<Predicate> Predicates;
	//public List<DocInformation> DocInfos;
	
	
	public InformationMerger(){
		
	}
	
	public void mergeModuleInformation( List<Predicate> predicates, List<DocInformation> docs){
		
		for(int k = 0; k < docs.size(); k++){
			int minValue;
			Predicate attach = new Predicate(); 
			
			for (int i= 0; i < predicates.size(); i++) {

				int currentMinSize;
				int docLine = docs.get(k).getLine();
				int predicateLines[] = predicates.get(i).getStartLines();
				
				for(int j = 0; j < predicateLines.length ;j++){
					int diff = Math.abs( predicateLines[j]-docLine );
					if(j <= 0) currentMinSize = diff;
					//if(currentMinSize > diff) currentMinSize = diff;
				}
				
				if(i == 0){
					
				}
				System.out.println("search");
			}
		}
		
	}
	
	public Predicate mergePredicate( Predicate predicate, DocInformation doc){
		
		Predicate merged = predicate;
		
		/* !!!!!!!  to do !!!!!! */
		
		return merged;
	}
	
}

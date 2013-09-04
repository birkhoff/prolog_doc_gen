package src.main.java.parsers;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InformationMerger {

	public List<Predicate> MergedPredicates;
	public List<Predicate> Undocumented;
	public List<Predicate> Emphasize;
	//public List<DocInformation> DocInfos;
	public HashMap<String, Predicate> PredicatesHashMap;
	
	
	
	public InformationMerger(){
		
		this.Emphasize = new LinkedList<Predicate>();
		this.Undocumented = new LinkedList<Predicate>();
		this.MergedPredicates = new LinkedList<Predicate>();
		this.PredicatesHashMap = new HashMap<String, Predicate>();
	}
	
	public void mergeModuleInformation( List<Predicate> predicates, List<DocInformation> docs){
		
		for (int k = 0; k < docs.size(); k++) {
			int minValue = -1;
			Predicate attach = null; 
			DocInformation currentDoc = docs.get(k);
			
			for (int i= 0; i < predicates.size(); i++) {
				//System.out.println("\n\t\tPreciate: "+ predicates.get(i).getName());
				int currentMinSize = -1;
				int docLine = currentDoc.getLine();
				int predicateLines[] = predicates.get(i).getEndLines();
					
				for (int j = 0; j < predicateLines.length ;j++) {
					
					int diff = predicateLines[j]-docLine;
					if( diff >=0 && (currentMinSize > diff || currentMinSize == -1) ) {
						currentMinSize = diff;
					}
				}
				//System.out.println("\nMinValue: "+minValue +"  currentMin: " +currentMinSize);
				if ( currentMinSize >=0 && (minValue > currentMinSize || minValue == -1) ) {
					minValue = currentMinSize;
					attach = predicates.get(i);
				}
				
			}
			
			if(currentDoc != null && attach != null){
				if (attach.isAttached()){ 
					attach = this.mergeExistingPredicate(attach.getName(),attach.getArity(), currentDoc );
				}else{
					attach.setAttached(true);
					Predicate mergedPredicate = this.mergePredicate(attach, currentDoc);
					MergedPredicates.add( mergedPredicate );
					PredicatesHashMap.put(mergedPredicate.getName(), mergedPredicate);
				}
				//System.out.println("mergin " + attach.getName() + " with " + currentDoc.getAuthor());
			}
			
		}
		for(int i = 0; i < predicates.size(); i++){
			if(!predicates.get(i).isAttached()){
				MergedPredicates.add(predicates.get(i));
				Undocumented.add(predicates.get(i));
				PredicatesHashMap.put(predicates.get(i).getName(), predicates.get(i));
			}
			if(predicates.get(i).getEmphasize())	Emphasize.add(predicates.get(i));
		}
		if (MergedPredicates.size() > 0) {
			Collections.sort(MergedPredicates, new Comparator<Predicate>() {
				@Override
				public int compare(final Predicate object1, final Predicate object2) {
					return object1.getName().compareTo(object2.getName());
				}
			} );
		}
		
	}
	
	
	public Predicate mergeExistingPredicate(String name, int ari, DocInformation doc){
		
		Predicate p = null;
		for(int i=0; i<MergedPredicates.size(); i++){
			if( MergedPredicates.get(i).getName().equalsIgnoreCase(name)
				&& MergedPredicates.get(i).getArity() == ari ){
				
				p = MergedPredicates.get(i);
				if(doc.getMode()!= null) p.addMode(doc.getMode());

				if(doc.getDate()!= null) p.addDate(doc.getDate());
				
				if(doc.getAuthor()!= null) p.addAuthor(doc.getAuthor());
				
				if(doc.getDescription()!= null) p.addDescription(doc.getDescription());
				
				if(doc.getAdditionalEntries()!= null) p.addAdditionalEntries(doc.getAdditionalEntries());
			}
		}

		return p;
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
		merged.setCodeString(predicate.getCodeString());
		merged.setBlockingInformation(predicate.getBlockingInformation());
		merged.setMultiFile(predicate.isMultiFile());
		merged.setMetaInformation(predicate.getMetaInformation());
		merged.setMode(predicate.getMode());
		merged.setEmphasize(predicate.getEmphasize());
		
		if(doc.getMode() != null && predicate.getMode() == null) merged.setMode(doc.getMode());
		if(doc.getDate()!= null) merged.setDate(doc.getDate());
		if(doc.getAuthor()!= null) merged.setAuthor(doc.getAuthor());
		if(doc.getDescription()!= null) merged.setDescription(doc.getDescription());
		if(doc.getAdditionalEntries()!= null) merged.setAdditionalEntries(doc.getAdditionalEntries());
				
		return merged;
	}
	
}

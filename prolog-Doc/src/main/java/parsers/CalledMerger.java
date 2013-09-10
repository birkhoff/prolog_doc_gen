package src.main.java.parsers;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CalledMerger {
	
	public List<Module> Modules;
	public List<Module> CalledModules;
	private HashMap<String, Boolean> DoubleModule;

	
	
	
	public CalledMerger(List<Module> module, List<Module> called){
		
		this.Modules = module;
		this.CalledModules = called;
		DoubleModule = new HashMap<String, Boolean>();
		
		//System.out.println(CalledModules.size());

	}
	
	public void merge(){
		
		
		for(int i = 0; i<Modules.size(); i++){
			
	

			
			Module currentModule = Modules.get(i);
			
			String fileName = currentModule.getName();
			if(!DoubleModule.containsKey(fileName)) currentModule.setLink( currentModule.getName() );
			DoubleModule.put(currentModule.getName(), true);
			
			Module currentCalledModule = null;
			for(int j = 0; j<CalledModules.size();j++){
				if(currentModule.getName().equalsIgnoreCase(CalledModules.get(j).getName())){
					currentCalledModule = CalledModules.get(j);
					break;
				}
			}
			
			if(currentCalledModule != null){
				for(int k = 0; k<currentModule.getPredicates().size(); k++){
					
					Predicate currentPredicate = currentModule.getPredicates().get(k);
					
					for(int m = 0; m<currentCalledModule.getPredicates().size(); m++){
						Predicate currentCalledp = currentCalledModule.getPredicates().get(m);
						if(currentCalledp.getName().equalsIgnoreCase(currentPredicate.getName())){
							//System.out.println(currentCalledp.getCalled().size());
							currentPredicate.setCalled(currentCalledp.getCalled());
							break;
						}
					}
					
					
				
				}
			}
		}
	}

}

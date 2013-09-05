package src.main.java.parsers;

import java.util.LinkedList;
import java.util.List;

public class EmphasizeList {
	
	private List<Predicate> Predicates;
	private String Name;
	
	public EmphasizeList(String name){
		this.setName(name);
		Predicates = new LinkedList<Predicate>();
	}

	public List<Predicate> getPredicates() {
		return Predicates;
	}

	public void setPredicates(List<Predicate> predicates) {
		Predicates = predicates;
	}
	
	public void addPredicate(Predicate p){
		this.Predicates.add(p);
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}

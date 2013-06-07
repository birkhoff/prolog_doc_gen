package src.main.java;

import java.util.LinkedList;
import java.util.List;

public class Module {

	
	private String Name;
	private List<Predicate> Predicates;
	private List<Call> Exports;
	private List<Call> Imports;
	
	public Module(){
		
		setExports(new LinkedList<Call>());
		setImports(new LinkedList<Call>());
	}

	
	public Module(String name){
		
		this.Name = name;
		setExports(new LinkedList<Call>());
		setImports(new LinkedList<Call>());
	}
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public List<Predicate> getPredicates() {
		return Predicates;
	}

	public void setPredicates(List<Predicate> predicates) {
		Predicates = predicates;
	}

	public List<Call> getExports() {
		return Exports;
	}

	public void setExports(List<Call> exports) {
		Exports = exports;
	}

	public List<Call> getImports() {
		return Imports;
	}

	public void setImports(List<Call> imports) {
		Imports = imports;
	}
	
	public void addImport(Call call){
		this.Imports.add(call);
	}
	
	public void addExport(Call call){
		this.Exports.add(call);
	}
	
	public void addImport(String name, String module, String arity ){
		Call addCall = new Call(name, module, arity);
		this.Imports.add(addCall);
	}
	
	public void addExport(String name, String module, String arity ){
		Call addCall = new Call(name, module, arity);
		this.Exports.add(addCall);
	}
}

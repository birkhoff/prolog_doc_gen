package src.main.java.parsers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Module {

	
	private String Name;
	private String Path;
	private String File;
	private List<Predicate> Predicates;
	private List<Call> Exports;
	private List<Call> Imports;
	private List<String> Dynamics;
	private HashMap<String, Predicate> PredicatesHashMap;
	private List<String> MultiFile;
	private String SPDet;
	
	public Module(){
		
		this.Exports = new LinkedList<Call>();
		this.Imports = new LinkedList<Call>();
		this.MultiFile = new LinkedList<String>();
		this.Dynamics = new LinkedList<String>();
	}

	
	public Module(String name){
		
		this.Name = name;
		this.Exports = new LinkedList<Call>();
		this.Imports = new LinkedList<Call>();
		this.MultiFile = new LinkedList<String>();
		this.Dynamics = new LinkedList<String>();
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


	public HashMap<String, Predicate> getPredicatesHashMap() {
		return PredicatesHashMap;
	}


	public void setPredicatesHashMap(HashMap<String, Predicate> predicatesHashMap) {
		PredicatesHashMap = predicatesHashMap;
	}


	public List<String> getMultiFile() {
		return MultiFile;
	}


	public void setMultiFile(List<String> multiFile) {
		MultiFile = multiFile;
	}
	
	public void addMultiFile(String add){
		this.MultiFile.add(add);
	}


	public String getSPDet() {
		return SPDet;
	}


	public void setSPDet(String sPDet) {
		SPDet = sPDet;
	}


	public String getPath() {
		return Path;
	}


	public void setPath(String path) {
		Path = path;
	}


	public String getFile() {
		return File;
	}


	public void setFile(String file) {
		File = file;
	}


	public List<String> getDynamics() {
		return Dynamics;
	}


	public void setDynamics(List<String> dynamics) {
		Dynamics = dynamics;
	}
	
	public void addDynamics(String add){
		this.Dynamics.add(add);
	}
	
}

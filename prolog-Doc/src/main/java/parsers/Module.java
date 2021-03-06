package src.main.java.parsers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Module {

	
	private String Name;
	private String Path;
	private String File;
	private String PathSuffix;
	private List<Predicate> Predicates;
	private List<Call> Exports;
	private List<Call> Imports;
	private List<String> ImportedModules;
	private List<String> Dynamics;
	private HashMap<String, Predicate> PredicatesHashMap;
	private List<String> MultiFile;
	private String SPDet;
	private int Lines;
	private int LineDef;
	private String Link;

	private String Author;
	private String Date;
	private String Mode;
	private String Description;
	private List<AdditionalEntry> AdditionalEntries;
	
	public Module(){
		
		this.Exports = new LinkedList<Call>();
		this.Imports = new LinkedList<Call>();
		this.ImportedModules = new LinkedList<String>();
		this.MultiFile = new LinkedList<String>();
		this.Dynamics = new LinkedList<String>();
		this.AdditionalEntries = new LinkedList<AdditionalEntry>();
	}

	
	public Module(String name){
		
		this.Name = name;
		this.Exports = new LinkedList<Call>();
		this.Imports = new LinkedList<Call>();
		this.ImportedModules = new LinkedList<String>();
		this.MultiFile = new LinkedList<String>();
		this.Dynamics = new LinkedList<String>();
		this.AdditionalEntries = new LinkedList<AdditionalEntry>();
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


	public int getLines() {
		return Lines;
	}


	public void setLines(int lines) {
		Lines = lines;
	}


	public List<String> getImportedModules() {
		return ImportedModules;
	}


	public void setImportedModules(List<String> importedModules) {
		ImportedModules = importedModules;
	}
	
	public void addImportedModule(String module){
		this.ImportedModules.add(module);
	}


	public String getPathSuffix() {
		return PathSuffix;
	}


	public void setPathSuffix(String pathSuffix) {
		PathSuffix = pathSuffix;
	}


	public String getLink() {
		return Link;
	}


	public void setLink(String link) {
		Link = link;
	}


	public String getAuthor() {
		return Author;
	}


	public void setAuthor(String author) {
		Author = author;
	}


	public String getDate() {
		return Date;
	}


	public void setDate(String date) {
		Date = date;
	}


	public String getMode() {
		return Mode;
	}


	public void setMode(String mode) {
		Mode = mode;
	}


	public String getDescription() {
		return Description;
	}


	public void setDescription(String description) {
		Description = description;
	}


	public List<AdditionalEntry> getAdditionalEntries() {
		return AdditionalEntries;
	}


	public void setAdditionalEntries(List<AdditionalEntry> additionalEntries) {
		AdditionalEntries = additionalEntries;
	}
	
	public void addAdditionalEntry( String key, String entry){
		
		AdditionalEntry newEntry = new AdditionalEntry(key, entry);
		this.AdditionalEntries.add(newEntry);
	}

	
	public void addAdditionalEntries(List<AdditionalEntry> additionalEntries) {
		for(int k = 0; k < additionalEntries.size(); k++){
			boolean notFound = true;
			String key = additionalEntries.get(k).getIdentifier().replaceAll("( |\t)", "");
			String entry = additionalEntries.get(k).getDescription();
			for(int i = 0; i < this.AdditionalEntries.size() && notFound; i++){
				if(AdditionalEntries.get(i).getIdentifier().equalsIgnoreCase(key)){
					AdditionalEntries.get(i).addDescription(entry);
					notFound = false;
				}
			}
			if(notFound){
				AdditionalEntry newEntry = new AdditionalEntry(key, entry);
				this.AdditionalEntries.add(newEntry);
			}
		}
	}
	

	public int getLineDef() {
		return LineDef;
	}


	public void setLineDef(int lineDef) {
		LineDef = lineDef;
	}
	
}

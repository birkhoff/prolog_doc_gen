package src.main.java.parsers;

import java.util.*;

public class Predicate {

	private String Name;
	private String Author;
	private String Date;
	private String Mode;
	private String Description;
	private List<AdditionalEntry> AdditionalEntries;
	private boolean attached;	
	//private List<String> CallsNames;
	private List<Call> CallsNames;
	private List<Predicate> Calls;
	private boolean dynamic;
	private boolean meta;
	private int Arity;
	private String MetaInformation;
	private String BlockingInformation;
	private boolean Volatile;
	private boolean MultiFile;
	private int[] StartLines;
	private int[] EndLines;
	private String Module;
	private List<String> Code;
	private String CodeString;
	private Boolean Emphasize;
		
	
	public Predicate(){
		
		Code = new LinkedList<String>();
		Calls = new LinkedList<Predicate>();
		setCallsNames(new LinkedList<Call>());
		AdditionalEntries = new LinkedList<AdditionalEntry>();
		this.attached = false;
		
	}
	
	public Predicate(String name){
		
		Code = new LinkedList<String>();
		this.Name = name;
		Calls = new LinkedList<Predicate>();
		setCallsNames(new LinkedList<Call>());
		AdditionalEntries = new LinkedList<AdditionalEntry>();
		this.attached = false;

	}

	public Predicate( String name, int arity ){
		

		Code = new LinkedList<String>();
		this.Name = name;
		this.Arity = arity;
		Calls = new LinkedList<Predicate>();
		setCallsNames(new LinkedList<Call>());
		AdditionalEntries = new LinkedList<AdditionalEntry>();
		this.attached = false;
		
	}
	

	public void addAuthor(String author){
		if (this.Author!=null)
			this.setAuthor(this.getAuthor()+" "+author);
		else
			this.setAuthor(author);
	}
	
	public void addDate(String date){
		if (this.Date!=null)
			this.setDate(this.getDate()+" "+date);
		else
			this.setDate(date);
	}
	
	public void addMode(String mode){
		if (this.Mode!=null)
			this.setMode(this.getMode()+" "+mode);
		else
			this.setMode(mode);
	}
	
	public void addDescription(String descr){
		if (this.Description!=null)
			this.setDescription(this.getDescription()+" "+descr);
		else
			this.setDescription(descr);
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
	
	public void setName(String name) {
		Name = name;
	}


	public String getName() {
		return Name;
	}


	public void setAuthor(String author) {
		Author = author;
	}


	public String getAuthor() {
		return Author;
	}


	public void setDate(String date) {
		Date = date;
	}


	public String getDate() {
		return Date;
	}


	public void setDescription(String description) {
		Description = description;
	}


	public String getDescription() {
		return Description;
	}


	public void setAdditionalEntries(List<AdditionalEntry> additionalEntries) {
		AdditionalEntries = additionalEntries;
	}


	public List<AdditionalEntry> getAdditionalEntries() {
		return AdditionalEntries;
	}


	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}


	public boolean isDynamic() {
		return dynamic;
	}


	public void setMeta(boolean meta) {
		this.meta = meta;
	}


	public boolean isMeta() {
		return meta;
	}


	public void setArity(int arity) {
		Arity = arity;
	}


	public int getArity() {
		return Arity;
	}

	public void setStartLines(int[] startLines) {
		StartLines = startLines;
	}

	public int[] getStartLines() {
		return StartLines;
	}

	public void setEndLines(int[] endLines) {
		EndLines = endLines;
	}

	public int[] getEndLines() {
		return EndLines;
	}
	
	public void setCalls(List<Predicate> calls) {
		Calls = calls;
	}

	public List<Predicate> getCalls() {
		return Calls;
	}
	
	public void addCall(Predicate call){
		this.Calls.add(call);
	}

	public void setCallsNames(List<Call> callsNames) {
		CallsNames = callsNames;
	}

	public List<Call> getCallsNames() {
		return CallsNames;
	}
	
	public void addCallNames(String name, String module, String arity ){
		Call addCall = new Call(name, module, arity);
		this.CallsNames.add(addCall);
	}

	public void addCallNames(String name, String module, String moduleLink,String arity ){
		Call addCall = new Call(name, module, arity);
		addCall.setModuleLink(moduleLink);
		this.CallsNames.add(addCall);
	}
	
	public boolean isAttached() {
		return attached;
	}

	public void setAttached(boolean attached) {
		this.attached = attached;
	}

	public String getModule() {
		return Module;
	}

	public void setModule(String module) {
		Module = module;
	}

	public List<String> getCode() {
		return Code;
	}

	public void setCode(List<String> code) {
		Code = code;
	}
	
	public void addCode(String code) {
		
		this.Code.add(code);
	}

	public String getCodeString() {
		return CodeString;
	}

	public void setCodeString(String codeString) {
		CodeString = codeString;
	}

	public String getMode() {
		return Mode;
	}

	public void setMode(String mode) {
		Mode = mode;
	}

	public String getMetaInformation() {
		return MetaInformation;
	}

	public void setMetaInformation(String metaInformation) {
		MetaInformation = metaInformation;
	}

	public boolean isVolatile() {
		return Volatile;
	}

	public void setVolatile(boolean _volatile) {
		Volatile = _volatile;
	}

	public boolean isMultiFile() {
		return MultiFile;
	}

	public void setMultiFile(boolean multiFile) {
		MultiFile = multiFile;
	}

	public String getBlockingInformation() {
		return BlockingInformation;
	}

	public void setBlockingInformation(String blockingInformation) {
		BlockingInformation = blockingInformation;
	}

	public Boolean getEmphasize() {
		return Emphasize;
	}

	public void setEmphasize(Boolean emphasize) {
		Emphasize = emphasize;
	}

}

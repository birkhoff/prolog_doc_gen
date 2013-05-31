package src.main.java;

import java.util.*;

public class Predicate {

	private String Name;
	private String Author;
	private String Date;
	private String Description;
	private HashMap<String, String> AdditionalEntries;
	
	private List<String> CallsNames;
	private List<Predicate> Calls;
	private boolean dynamic;
	private boolean meta;
	private int Arity;
	private int[] StartLines;
	private int[] EndLines;
	
	
	public Predicate(String name){
		
		this.Name = name;
		Calls = new LinkedList<Predicate>();
		CallsNames = new LinkedList<String>();
		AdditionalEntries = new HashMap<String, String>();
	}

	public Predicate( String name, int arity ){
		
		this.Name = name;
		this.Arity = arity;
		Calls = new LinkedList<Predicate>();
		CallsNames = new LinkedList<String>();
		AdditionalEntries = new HashMap<String, String>();
	}
	

	public void putEntry( String Key, String Entry){
		
		this.AdditionalEntries.put(Key, Entry);
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


	public void setAdditionalEntries(HashMap<String, String> additionalEntries) {
		AdditionalEntries = additionalEntries;
	}


	public HashMap<String, String> getAdditionalEntries() {
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

	public void setCallsNames(List<String> callsNames) {
		CallsNames = callsNames;
	}

	public List<String> getCallsNames() {
		return CallsNames;
	}

	
	public void addNameOfCall(String call){
		this.CallsNames.add(call);
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


}

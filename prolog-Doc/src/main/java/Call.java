package src.main.java;


public class Call {

	/*
	<module>"test3"</module>
	<name>"foo"</name>
	<arity>1</arity>
	*/
	
	private String Module;
	private String Name;
	private int Arity;
	private boolean Built_in;
	private Predicate Predicate;
	
	public Call( String name, String module, int arity){
		
		this.setName(name);
		this.setModule(module);
		this.setArity(arity);
		this.setBuilt_in(false);
		if(module.contains("built_in")) this.setBuilt_in(true);
	}
	
	
	public Call( String name, String module, String arity){
		
		this.setName(name);
		this.setModule(module);
		int arielle = Integer.parseInt(arity);
		this.setArity(arielle);
		this.setBuilt_in(false);
		if(module.contains("built_in")) this.setBuilt_in(true);
	}

	public void setModule(String module) {
		Module = module;
	}

	public String getModule() {
		return Module;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getName() {
		return Name;
	}

	public void setArity(int arity) {
		Arity = arity;
	}

	public int getArity() {
		return Arity;
	}

	public void setBuilt_in(boolean built_in) {
		Built_in = built_in;
	}

	public boolean isBuilt_in() {
		return Built_in;
	}


	public Predicate getPredicate() {
		return Predicate;
	}


	public void setPredicate(Predicate predicate) {
		Predicate = predicate;
	}
}

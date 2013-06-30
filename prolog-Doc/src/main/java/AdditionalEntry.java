package src.main.java;

public class AdditionalEntry {

	private String Description;
	private String Identifier;
	
	public AdditionalEntry(String identifier, String description){
		
		this.Description = description;
		this.Identifier = identifier.replaceAll(" |\n|\t", "");			// cuts off unnecessary whitespaces
		
	}
	
	public void addDescription(String description) {
		this.Description += description;
	}

	
	public void setDescription(String description) {
		Description = description;
	}

	public String getDescription() {
		return Description;
	}

	public void setIdentifier(String identifier) {
		Identifier = identifier;
	}

	public String getIdentifier() {
		return Identifier;
	}
}

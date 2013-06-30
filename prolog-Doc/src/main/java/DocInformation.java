package src.main.java;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DocInformation {

		private String Author;
		private String Date;
		private String Description;
		private String Mode;
		private List<AdditionalEntry> AdditionalEntries;
		
		private int line;
		
		
		public DocInformation(){

			setAdditionalEntries(new LinkedList<AdditionalEntry>());
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


		public void setLine(int line) {
			this.line = line;
		}


		public int getLine() {
			return line;
		}


		public void setAdditionalEntries(List<AdditionalEntry> additionalEntries) {
			AdditionalEntries = additionalEntries;
		}


		public List<AdditionalEntry> getAdditionalEntries() {
			return AdditionalEntries;
		}
		
		
	/*	public void addAdditionalEntry(String key, String value ){
			AdditionalEntry newEntry = new AdditionalEntry(key, value);
			this.AdditionalEntries.add(newEntry);
		}*/

		public void addAdditionalEntry( String key, String entry){
			boolean notFound = true;
			key = key.replaceAll("( |\t)", "");
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

		public String getMode() {
			return Mode;
		}


		public void setMode(String mode) {
			Mode = mode;
		}

	
}

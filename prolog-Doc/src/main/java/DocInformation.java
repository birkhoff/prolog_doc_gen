package src.main.java;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DocInformation {

		private String Author;
		private String Date;
		private String Description;
		private HashMap<String, String> AdditionalEntries;
		
		private int line;
		
		
		public DocInformation(){
			

			setAdditionalEntries(new HashMap<String, String>());
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

		public void addAdditionalEntry(String key, String value ){
			this.AdditionalEntries.put(key, value);
		}

		public void setLine(int line) {
			this.line = line;
		}


		public int getLine() {
			return line;
		}
	
}

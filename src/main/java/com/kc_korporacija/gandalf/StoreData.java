package com.kc_korporacija.gandalf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StoreData {
	
		private String name,text,creationTime,username;
		
		public StoreData(){
		}
		
		public StoreData(String name, String text, String creationTime, String username){
			this.name = name;
			this.text = text;
			this.creationTime = creationTime;
			this.username = username;
		}
		
		public void saveToFile(){
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.name, true)))) {
				if(username==null){
					username = "no_username";
				}
			    out.println(this.username);
			    out.println(this.creationTime);
			    out.println(this.text);
			}catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		}
		
		public void loadFromFile(String fileName) throws IOException{
			BufferedReader br = null;
			br = new BufferedReader(new FileReader(fileName));
			
			this.name = fileName;
			this.username = br.readLine();
			this.creationTime = br.readLine();
			
			String line,text=""; 
			while((line = br.readLine()) != null){
				text += line;
			}	
			br.close();
			
			this.text = text;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getCreationTime() {
			return creationTime;
		}

		public void setCreationTime(String creationTime) {
			this.creationTime = creationTime;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		
		
	
}

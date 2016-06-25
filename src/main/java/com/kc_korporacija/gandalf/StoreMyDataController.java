package com.kc_korporacija.gandalf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StoreMyDataController {
	
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(value = "/storeMyData", method = RequestMethod.POST /*headers = {"Content-type=application/json"}*/)
	public void home(@RequestParam (value = "session", required = true) String session,
					@RequestParam (value = "username", required = false) String username,
					@RequestParam (value="data", required = true) String data) throws JsonProcessingException, IOException{
		
		System.out.println(session);
		System.out.println(username);	
		/*if(username==null)
			System.out.println("testing String");*/
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(data);
		
		String name = json.get("name").getTextValue(), text = json.get("text").getTextValue();
		
		System.out.println(name + " " + text);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);	
		String formattedDate = dateFormat.format(date);
		
		if(username == null){
			BufferedReader br = null;
			br = new BufferedReader(new FileReader("guestSessions.txt"));
			String line;
			while((line=br.readLine()) != null){
				String[] splited = line.split(" "); 
				String guest_session = splited[0];
				if(guest_session.equals(session) ){
					StoreData sd = new StoreData(name,text, formattedDate, username);
					sd.saveToFile();
					break;
				}
			}
			br.close();
		}
		else{
			
			BufferedReader br = null;
			br = new BufferedReader(new FileReader("userSessions.txt"));
			String line;
			while((line=br.readLine()) != null){
				System.out.println(line);
				String[] splited = line.split(" "); 
				String user_session = splited[0];
				String user_username = splited[6];
				if(user_session.equals(session) && user_username.equals(username) ){
					StoreData sd = new StoreData(name,text, formattedDate, username);
					sd.saveToFile();
					System.out.println("testing_saveToFile");
					break;
				}
			}
			br.close();
		}
		
		
	}
	
}

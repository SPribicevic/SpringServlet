package com.kc_korporacija.gandalf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetMyDataController {
	
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(value = "/getMyData",
					params = {"dataName","username","session"})
	@ResponseBody
	public StoreData getMyData(@RequestParam(value = "dataName") String dataName,
							   @RequestParam(value = "username") String username,
							   @RequestParam(value = "session") String session) throws IOException{
		
		BufferedReader br = null;
		br = new BufferedReader(new FileReader("userSessions.txt"));
		String line;
		while((line=br.readLine()) != null){
			//System.out.println(line);
			String[] splited = line.split(" "); 
			String user_session = splited[0];
			String user_username = splited[6];
			if(user_session.equals(session) && user_username.equals(username) ){
				br.close();
				
				StoreData sd = new StoreData();
				sd.loadFromFile(dataName);
				
				if(sd.getUsername().equals(username) || sd.getUsername().equals("no_username")){
					return sd;
				}
				
			}
		}
		
		return null;
		
	}

}

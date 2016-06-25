package com.kc_korporacija.gandalf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenerateUserSessionController {

	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(value = "/generateUserSession",
			params = {"user"})
	public String home(Model model, @RequestParam(value = "user") String user) throws IOException{
			
		if(isAlphaNumeric(user) == true){
			RandomString generateRandomUserSession = new RandomString(60);
			String userSession = generateRandomUserSession.nextString();
			model.addAttribute("userSession",userSession);
			SessionWriter sw = new SessionWriter();
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
			String formattedDate = dateFormat.format(date);
			sw.writeUser(userSession, formattedDate, user);
			String line;
			BufferedReader br = null;
			br = new BufferedReader(new FileReader("userSessions.txt"));
			while((line=br.readLine()) != null)
				System.out.println(line);
		}
		else{
			String error = "Provided username is not alphanumeric";
			model.addAttribute("userSession",error);
		}
			
		return "generateUserRequest";
	}
	
	private boolean isAlphaNumeric(String myString){
		String pattern = "^[a-zA-Z0-9]*$";
		return(myString.matches(pattern));
	}
	
}

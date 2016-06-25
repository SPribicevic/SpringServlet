package com.kc_korporacija.gandalf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class GenerateGuestSessionController {
	
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(value = "/generateGuestSession", method = RequestMethod.GET)
	public String home(Model model) throws IOException {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		String formattedDate = dateFormat.format(date);
		
		RandomString generateRandomGuestSession = new RandomString(60);
		
		String guestSession = generateRandomGuestSession.nextString();
		
		model.addAttribute("guestSession",guestSession);
		
		SessionWriter sw = new SessionWriter();
		
		sw.write(guestSession, formattedDate);
		
		String line;
		
		BufferedReader br = null;
		br = new BufferedReader(new FileReader("guestSessions.txt"));
		
		while((line=br.readLine()) != null)
			System.out.println(line);
		
		return "generateGuestRequest";
	}
	
}

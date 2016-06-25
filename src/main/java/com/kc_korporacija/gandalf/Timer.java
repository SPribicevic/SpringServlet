package com.kc_korporacija.gandalf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Timer {
	
	@Scheduled(fixedDelay = 45000)
    public void deleteOldGuestSessions() throws IOException
    {
        BufferedReader br = null;
        try {
			br = new BufferedReader(new FileReader("guestSessions.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String line;
        Vector<String> lines = new Vector<String>();
        
        while((line=br.readLine()) != null){
        	String[] splited = line.split(" ");
        	//System.out.println(splited[3]);
        	String[] time = splited[4].split("\\.");
        	int hours = Integer.parseInt(time[0]);
        	int minutes = Integer.parseInt(time[1]);
        	int seconds = Integer.parseInt(time[2]);
        	//System.out.println(time[0] + time[1] + time[2]);
        	//System.out.println("hours=" + hours + " minutes=" + minutes + " seconds=" + seconds);
        	
        	if(sessionStatus(hours, minutes, seconds, 1800) == true){
        		lines.add(line);
        		//System.out.println(line);
        	}
        }
        
        br.close();
        
        PrintWriter pw = new PrintWriter("guestSessions.txt");
        
        for(String temp : lines){
        	pw.println(temp);
        }
        
        pw.close();
    }
	
	@Scheduled(fixedDelay = 45000)
	public void deleteOldUserSession() throws NumberFormatException, IOException{
		
		BufferedReader br = null;
        try {
			br = new BufferedReader(new FileReader("userSessions.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String line;
        Vector<String> lines = new Vector<String>();
        
        while((line=br.readLine()) != null){
        	String[] splited = line.split(" ");
        	//System.out.println(splited[3]);
        	String[] time = splited[4].split("\\.");
        	int hours = Integer.parseInt(time[0]);
        	int minutes = Integer.parseInt(time[1]);
        	int seconds = Integer.parseInt(time[2]);
        	//System.out.println(time[0] + time[1] + time[2]);
        	//System.out.println("hours=" + hours + " minutes=" + minutes + " seconds=" + seconds);
        	
        	if(sessionStatus(hours, minutes, seconds, 7200) == true){
        		lines.add(line);
        		//System.out.println(line);
        	}
        }
        
        br.close();
        
        PrintWriter pw = new PrintWriter("userSessions.txt");
        
        for(String temp : lines){
        	pw.println(temp);
        }
        
        pw.close();
        
	}
	
	private boolean sessionStatus(int hours, int minutes, int seconds, int limit){
		
		int session_time = hours*3600 + minutes*60 + seconds;
		
		/*long time = System.currentTimeMillis();
		time /= 1000;
		int current_seconds = (int) (time%60);		
		time /= 60;
		int current_minutes = (int) (time % 60);	
		time /= 60;
		int current_hours = (int) (((time%60)+2)%24);*/
		
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date 
		int current_hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		int current_minutes = calendar.get(Calendar.MINUTE);
		int current_seconds = calendar.get(Calendar.SECOND);
		
		int current_time = current_hours*3600 + current_minutes*60 + current_seconds;
		
		//System.out.println(current_hours + " " + current_minutes);
		
		if(Math.abs(current_time-session_time) > limit){
			
			return false;
		}	
		return(true);
	}
	
}

package com.kc_korporacija.gandalf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class SessionWriter {
	
	public void write(String session, String time) throws FileNotFoundException, UnsupportedEncodingException
	{
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("guestSessions.txt", true)))) {
		    out.println(session+" "+time);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}
	
	public void writeUser(String session, String time, String user) throws FileNotFoundException, UnsupportedEncodingException
	{
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("userSessions.txt", true)))) {
		    out.println(session+" "+time+" "+user);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}
	
}

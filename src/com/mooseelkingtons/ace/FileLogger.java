package com.mooseelkingtons.ace;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.logging.*;

/**
 * Ripped from my other project, Däger.
 * 
 * @author Moose
 */
public class FileLogger {

	private String title = "Logger";
	private FileWriter out;
	private SimpleDateFormat day, time;
	
	public FileLogger(String title) {
		this.title = title;
		this.day = new SimpleDateFormat("dd-MM-yy");
		this.time = new SimpleDateFormat("HH:mm:ss");
		try {
			File f = new File("./logs/", title.toLowerCase()+"/");
			if(!f.exists())
				f.mkdirs();
			File d = new File(f, day.format(new Date())+".txt");
			if(!d.exists())
				d.createNewFile();
			out = new FileWriter(d, true);
			
			out.write("\r\n");
			out.write("BEGIN SESSION AT "+day.format(new Date())+ " "+time.format(new Date()));
			out.write("\r\n\r\n");
		} catch (IOException e) {
			log(Level.SEVERE, e);
		}
	}
	
	public void log(Level level, String msg) {
		try {
			String d = day.format(new Date());
			String t = time.format(new Date());
			String m = d+" "+t+" ["+title+"] ["+level.getName()+"]: "+msg;
			System.out.println(m);
			out.write(m);
			out.write("\r\n");
			
			out.flush();
		} catch(IOException e) {
			log(Level.SEVERE, e);
		}
	}
	
	public void log(Level level, Exception e) {
		try {
			String d = day.format(new Date());
			String t = time.format(new Date());
			String m = d+" "+t+" ["+title+"] ["+level.getName()+"]: "+e.getMessage();
			System.out.println(m);
			out.write(m);
			out.write("\r\n");
			
			String[] ap = new String[e.getStackTrace().length];
			for(int i = 0; i < ap.length; i++) {
				String st = e.getStackTrace()[i].toString();
				System.err.println(st);
				out.write(st);
				out.write("\r\n");
			}
			
			out.flush();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}

package com.mooseelkingtons.ace;

import java.util.logging.*;

import com.mooseelkingtons.ace.web.*;

public class Main {
	
	private static FileLogger internalLogger;
	
	public static void main(String[] args) {
		try {	
			internalLogger = new FileLogger("Services");
			
			internalLogger.log(Level.INFO, "Initializing Web Server");
			new Web();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static FileLogger getLogger() {
		return internalLogger;
	}
}

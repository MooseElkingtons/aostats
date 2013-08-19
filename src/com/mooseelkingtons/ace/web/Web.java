package com.mooseelkingtons.ace.web;

import java.awt.Image;
import java.net.*;
import java.util.*;
import java.util.logging.Level;

import com.mooseelkingtons.ace.Main;
import com.sun.net.httpserver.*;

public class Web {

	private HttpServer server;
	private int steamCount = 0, bnsCount = 0;
	private Image image;
		
	public Web() throws Exception {
		server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
		Main.getLogger().log(Level.INFO, "Webserver bound to port 8080");
		
		server.createContext("/", new ImageHandler(this));
		server.setExecutor(null);
		Main.getLogger().log(Level.INFO, "Starting Web Server");
		server.start();

		Timer timer = new Timer();
		timer.schedule(new UpdateTask(this), 0, (60 * 1000) * 10);
	}
	
	public int getSteamCount() {
		return steamCount;
	}
	
	public int getBnsCount() {
		return bnsCount;
	}
	
	public void setSteamCount(int count) {
		steamCount = count;
	}
	
	public void setBnsCount(int count) {
		bnsCount = count;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void update(Image image) {
		this.image = image;
	}
	
}

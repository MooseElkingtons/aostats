package com.mooseelkingtons.ace.web;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import com.sun.net.httpserver.*;

public class ImageHandler implements HttpHandler {

	private Web web;

	
	public ImageHandler(Web web) {
		this.web = web;
	}
	
	@Override
	public void handle(HttpExchange ex) throws IOException {
		Image image = web.getImage();
		Headers header = ex.getResponseHeaders();
		header.set("Content-Type", "image/png");
		ex.sendResponseHeaders(200, 0);
		
		OutputStream out = ex.getResponseBody();
		ImageIO.write((RenderedImage) image, "png", out);
		out.flush();
		out.close();
	}

}

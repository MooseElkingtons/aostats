package com.mooseelkingtons.ace.query;

import java.net.*;
import java.io.*;
import java.util.*;

import argo.jdom.*;

public class BnsQuery {
	
	public int query() throws Exception {
		URL url = new URL("http://services.buildandshoot.com/serverlist.php?" +
						  "version=075");
		InputStream in = url.openStream();
		int l = 0;
		byte[] buffer = new byte[1024];
		String json = "";
		while((l = in.read(buffer)) != -1) {
			json += new String(buffer, 0, l);
		}
		in.close();
		
		int playerAmt = 0;
		
		JsonRootNode root = new JdomParser().parse(json);
		List<JsonNode> array = root.getArrayNode();
		for(JsonNode child : array) {
			playerAmt += Integer.parseInt(child.getNumberValue("players_current"));
		}
		return playerAmt;
	}
}

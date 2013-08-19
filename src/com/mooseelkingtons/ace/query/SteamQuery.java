package com.mooseelkingtons.ace.query;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;

import com.github.koraktor.steamcondenser.exceptions.*;
import com.github.koraktor.steamcondenser.steam.servers.*;
import com.mooseelkingtons.ace.*;

public class SteamQuery {

	private int amt = 0;
	
	public int query() throws Exception {
		MasterServer master = new MasterServer(MasterServer.SOURCE_MASTER_SERVER);
		Vector<InetSocketAddress> servers = master.getServers(MasterServer.REGION_ALL,
				"\\gamedir\\aceofspades\\empty\\1");
		Main.getLogger().log(Level.INFO, "Querying "+servers.size()+" servers.");
		gamequery(servers);
		master.disconnect();
		return amt;
	}
	
	private void gamequery(Vector<InetSocketAddress> salloc) throws SteamCondenserException {
		for(InetSocketAddress addr : salloc) {
			try {
				SourceServer server = new SourceServer(addr.getAddress(), addr.getPort());
				int amt = new Byte((byte) server.getServerInfo().get("numberOfPlayers")).intValue();
				this.amt += amt;
				System.out.println("Player count: "+amt);
				server.disconnect();
			} catch(TimeoutException e) {
				
			}
		}
	} 
}

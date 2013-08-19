package com.mooseelkingtons.ace.web;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

import javax.imageio.*;

import com.mooseelkingtons.ace.*;
import com.mooseelkingtons.ace.query.*;

public class UpdateTask extends TimerTask {

	private Image image;
	private Image greenDuece, blueDuece, box;
	private Font spades, edo;
	private Web web;
	
	public UpdateTask(Web web) {
		this.web = web;
		try {
			greenDuece = ImageIO.read(new File("./png/075.png"));
			blueDuece  = ImageIO.read(new File("./png/100.png"));
			box        = ImageIO.read(new File("./png/box.png"));
			
			spades = Font.createFont(Font.TRUETYPE_FONT,
									 new FileInputStream(new File("./font/075.ttf")));
			edo    = Font.createFont(Font.TRUETYPE_FONT,
									 new FileInputStream(new File("./font/100.ttf")));
		} catch(Exception e) {
			Main.getLogger().log(Level.INFO, e);
		}
	}
	
	@Override
	public void run() {
		long start = System.currentTimeMillis();
		try {
			Main.getLogger().log(Level.INFO, "Updating playercount information");
			int pc = new SteamQuery().query();
			Main.getLogger().log(Level.INFO, "Obtained "+pc+" Players from 1.0.");
			int bc = new BnsQuery().query();
			Main.getLogger().log(Level.INFO, "Obtained "+bc+" Players from 0.75.");
			
			web.setSteamCount(pc);
			web.setBnsCount(bc);
			Main.getLogger().log(Level.INFO, "Finished updating data!");

			Main.getLogger().log(Level.INFO, "Updating Image");
			updateImage(bc, pc);
			web.update(image);
			Main.getLogger().log(Level.INFO, "Updated Image");
			
			Main.getLogger().log(Level.INFO, "Finished updating in "+
							((System.currentTimeMillis() - start) / 1000)+" seconds");
		} catch(Exception e) {
			Main.getLogger().log(Level.SEVERE,
					"There was an error while updating data :(");
			Main.getLogger().log(Level.SEVERE, e);
		}
	}
	
	private void updateImage(int bc, int pc) {
		image = new BufferedImage(350, 250, BufferedImage.TYPE_INT_ARGB);
		float jagPercent = ((float) pc / (((float) bc) + ((float) pc)) * 100.0f);
		float bnsPercent = ((float) bc / (((float) pc) + ((float) bc)) * 100.0f);
		Graphics2D g = ((BufferedImage) image).createGraphics();
		g.drawImage(greenDuece, 0, 0, null);
		Image blue = ((BufferedImage) blueDuece).getSubimage(0, 0,
					new Double(jagPercent).intValue(), 250);
		g.drawImage(blue, 0, 0, null);
		
		g.drawImage(box, 130, 50, null); // 0.75 box
		g.drawImage(box, 130, 150, null); // 1.0 box
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(96, 194, 48)); // green
		g.setFont(spades.deriveFont(28f));
		g.drawString("0.75", 130, 38);
		
		g.setColor(new Color(47, 117, 196)); // blue
		g.setFont(edo.deriveFont(28f));
		g.drawString("1.0", 130, 142);
		
		g.setFont(edo.deriveFont(24f));
		g.drawString(pc + " - "+Math.round(jagPercent)+"%", 138, 178);
		g.setColor(new Color(96, 194, 48)); // green
		g.drawString(bc + " - "+Math.round(bnsPercent)+"%", 138, 78);
	}
}

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import block.BlockType;
import save.WorldOptions;
import start.Game;

public class ActionBar {

	private Rectangle rect;
	private Font f;
	private Color c;
	private boolean colorAdapt;

	public ActionBar(Rectangle rect, Font f, Color c, boolean colorAdapt) {
		// If colorAdapt is true, then Color c is ignored
		// Clarification: this.c = c is not written
		this.rect = rect;
		this.f = f;
		if (!colorAdapt) {
			this.c = c;
		}
		this.colorAdapt = colorAdapt;
	}

	public void drawActionBar(Graphics2D g2d) {
		// Draws the action bar with the background only & calling other
		// Helper methods to do the other jobs
		if (!colorAdapt) {
			g2d.setColor(c);
		} else {
			g2d.setColor(WorldOptions.coordinates.getBiome(Game.b.chunk).getC().brighter());
		}

		g2d.fill(rect); // I'll try this for now

		drawStats(g2d);
		// drawHealth(g2d);
		drawInventory(g2d);
		// drawDebugging(g2d);
	}

	public void drawStats(Graphics2D g2d) {
		// Coordinates
		g2d.setColor(Color.BLACK);
		g2d.drawString("X: " + (-Game.b.x) + " Y: " + (-Game.b.y), rect.x + rect.width / 15F, rect.y + rect.height / 2F);
	}

	public void drawHealth(Graphics2D g2d) {
		// Hearts
		g2d.setColor(Color.RED);
		for (int i = 0; i < Game.player.health; i++) {
			g2d.fillArc(rect.x + rect.width / 2 + (rect.height * i), rect.y + rect.height / 15, rect.height, rect.height, 0, 360);
		}
	}

	public void drawInventory(Graphics2D g2d) {
		// 3 choices & drawCircle around them!
		for (int i = 0; i < Game.player.inventory.length; i++) {
			g2d.setColor(Game.player.inventory[i].getC());
			g2d.fill3DRect(rect.x + ((rect.width) / 2) + (35 * i), rect.y + (rect.height / 10),
					Game.player.inventory[i].getRect().width * BlockType.SIZE,
					Game.player.inventory[i].getRect().height * BlockType.SIZE, true);
			if (i == Game.player.getSelectedItem()) {
				g2d.setColor(Color.BLACK); // for now
				g2d.drawRect(rect.x + ((3 * rect.width) / 2), rect.y + (rect.height / 2),
						Game.player.inventory[i].getRect().width * BlockType.SIZE,
						Game.player.inventory[i].getRect().height * BlockType.SIZE);
			}
		}

	}

	public void drawDebugging(Graphics2D g2d) {
		// Optional to draw
	}
}

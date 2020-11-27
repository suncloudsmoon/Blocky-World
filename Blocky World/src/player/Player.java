package player;

import java.awt.Color;
import java.awt.Graphics2D;

import block.BlockType;

public class Player {

	private String name;

	private int x;
	private int y;

	public double speed = 500; // in pixels per second
	public double health = 3;

	public BlockType[] inventory = { BlockType.GRAY, BlockType.WOOD, BlockType.MAGMA };
	private int selectedItem = 0;

	public Player(String name, int x, int y, double speed) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed * 100;
	}

	public void drawPlayer(Graphics2D g) {
		g.setColor(Color.black);
		g.fill3DRect(x, y, 100, 100, true);

		g.setColor(Color.white);
		g.drawString(name, x + 50, y + 50);
	}
	
	public void checkCollision() {
		
	}

	public int getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

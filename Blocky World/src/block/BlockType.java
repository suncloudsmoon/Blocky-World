package block;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public enum BlockType {

	GRAY(null, Color.gray, new Rectangle(1, 1)), WOOD(null, new Color(140, 129, 129), new Rectangle(1, 1)),
	MAGMA(null, Color.red, new Rectangle(1, 1)), DOOR(null, new Color(104, 105, 81), new Rectangle(1, 5));

	public static final int SIZE = 30;

	private final BufferedImage img;
	private final Color c;
	private final Rectangle rect;

	BlockType(BufferedImage img, Color c, Rectangle rect) {
		this.c = c;
		this.img = img;
		this.rect = rect;
	}

	/**
	 * @return the img
	 */
	public BufferedImage getImg() {
		return img;
	}

	/**
	 * @return the c
	 */
	public Color getC() {
		return c;
	}

	/**
	 * @return the rect
	 */
	public Rectangle getRect() {
		return rect;
	}

}

package other;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	public final BufferedImage PLAYER_TOP, PLAYER_BOTTOM, PLAYER_LEFT, PLAYER_RIGHT;

	public Sprite() {
		PLAYER_TOP = read("Top Player.png");
		PLAYER_BOTTOM = read("Bottom Player.png");
		PLAYER_LEFT = read("Left Player.png");
		PLAYER_RIGHT = read("Right Player.png");
	}

	public BufferedImage read(String URL) {
		try {
			return ImageIO.read(getClass().getClassLoader().getResource(URL));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

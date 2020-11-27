package entity;

import java.awt.Point;

import save.WorldOptions;
import start.Game;

public class Collision {

	private Collision() {

	}

	// Set velocity to zero if collision happens
	// parameters are used to find the borders of the car
	// the parameters must be in grid
	public static boolean checkCollision(int x, int y, int w, int h) {
		if (WorldOptions.coordinates.containsKey(new Point(x, y))) {
			Game.car.setSpeedX(0);
			Game.car.setSpeedY(0);
			return true;
		} else if (WorldOptions.coordinates.containsKey(new Point(x + w, y))) {
			Game.car.setSpeedX(0);
			Game.car.setSpeedY(0);
			return true;
		} else if (WorldOptions.coordinates.containsKey(new Point(x, y + h))) {
			Game.car.setSpeedX(0);
			Game.car.setSpeedY(0);
			return true;
		} else if (WorldOptions.coordinates.containsKey(new Point(x + w, y + h))) {
			Game.car.setSpeedX(0);
			Game.car.setSpeedY(0);
			return true;
		}
		return false;
	}
}

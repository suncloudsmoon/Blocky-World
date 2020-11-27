package block;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import save.WorldOptions;
import start.Game;
import start.StartPoint;

public class CollisionDetection {
	
	// for now; change it later
	private static final int WIDTH = (9 * StartPoint.FRAME_WIDTH) / 22;
	private static final int HEIGHT = (9 * StartPoint.FRAME_HEIGHT) / 22;

	private CollisionDetection() {

	}

	public static boolean checkCollision() {
		HashMap<Point, BlockType> coordinates = WorldOptions.coordinates.getLocations(Game.b.chunk);
		
		Point use = new Point((-Game.b.x + (-Game.b.chunk.x * StartPoint.FRAME_WIDTH) + WIDTH) / BlockType.SIZE,
				(-Game.b.y + (-Game.b.chunk.y * StartPoint.FRAME_HEIGHT) + HEIGHT) / BlockType.SIZE);
		
		for (Map.Entry<Point, BlockType> a : coordinates.entrySet()) {
			Point check = a.getKey();
			if ((use.x <= check.x && use.x + 3 >= check.x) && (use.y <= check.y && use.y + 3 >= check.y)) {
				return true;
			}
		}
		return false;
	}
}

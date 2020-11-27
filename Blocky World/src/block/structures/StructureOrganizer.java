package block.structures;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import block.BlockType;
import save.WorldOptions;
import start.StartPoint;

public class StructureOrganizer {

	private static final int CUBE_SIZE = 6; // House Size: 6 x 6

	private StructureOrganizer() {

	}

	// this may return null becuase so suitable place has been found!
	// Invoke this method after all the procedural generation has been done!
	public static Map<Point, BlockType> addRandStructure(Structure s, Point chunk, Random rand) {
		if (s == Structure.HOUSE) {
			return addHouseStructure(chunk, rand);
		}
		return null;
	}

	public static Map<Point, BlockType> addHouseStructure(Point chunk, Random rand) {
		HashMap<Point, BlockType> house = new HashMap<>();
		
		int x = rand.nextInt((StartPoint.FRAME_WIDTH / BlockType.SIZE) - (CUBE_SIZE - 1));
		int y = rand.nextInt((StartPoint.FRAME_HEIGHT / BlockType.SIZE) - (CUBE_SIZE - 1));
		removeBlocks(chunk, new Point(x, y), new Point(x + CUBE_SIZE, y + CUBE_SIZE));

		///////// Full House //////////

		// Vertical slaps of the house
		house.put(new Point(x, y), BlockType.WOOD);
		house.put(new Point(x, y + CUBE_SIZE), BlockType.WOOD);
		
		house.put(new Point(x, y + 1), BlockType.DOOR);
		
		for (int i = 0; i < CUBE_SIZE; i++) {
			house.put(new Point(x + CUBE_SIZE, y + i), BlockType.WOOD);
		}

		// Horizontal slaps of the house
		for (int i = 1; i < CUBE_SIZE; i++) {
			house.put(new Point(x + i, y), BlockType.WOOD);
			house.put(new Point(x + i, y + CUBE_SIZE), BlockType.WOOD);
		}

		// Roof of the house
		for (int height = -1; height >= -CUBE_SIZE / 2; height--) {
			for (int width = -height; width <= CUBE_SIZE - (-height); width++) {
				house.put(new Point(x + width, y + height), BlockType.WOOD);
			}
		}

//		// Doors from block 2 height 2
//		for (int i = CUBE_SIZE - 1; i >= CUBE_SIZE / 2; i--) {
//			// Vertical slaps of the door
//			// Need ratios for door : house
//			house.put(new Point(x + CUBE_SIZE / 3, y + i), BlockType.WOOD); // Default Value
//			house.put(new Point(x + (2 * CUBE_SIZE / 3), y + i), BlockType.WOOD);
//		}
//
//		// Roof of the door
//		int avg = ((CUBE_SIZE / 3) + (2 * CUBE_SIZE / 3)) / 2;
//		house.put(new Point(x + avg, y + CUBE_SIZE / 2), BlockType.WOOD);

		return house;
	}

	// Remove blocks that interfere with the placement of the house
	public static void removeBlocks(Point chunk, Point p1, Point p2) {
		for (int i = p1.x; i <= p2.x; i++) {
			for (int j = p1.y; j <= p2.y; j++) {
				Point check = new Point(i, j);
				if (WorldOptions.coordinates.getLocations(chunk).containsKey(check)) {
					WorldOptions.coordinates.getLocations(chunk).remove(check);
				}

			}
		}
	}
}

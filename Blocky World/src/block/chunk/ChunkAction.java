package block.chunk;

import java.awt.Point;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import block.BlockType;
import block.structures.Structure;
import block.structures.StructureOrganizer;
import save.WorldOptions;
import start.StartPoint;

public class ChunkAction {

	// each block is only 50 pixels width & height

	private ChunkAction() {

	}

	public static void generateChunk(Point chunk, Random rand) {
		generateBlocks(chunk, rand);
		addStructures(chunk, rand);
	}

	private static void generateBlocks(Point p, Random rand) {
		// How many chunk locations there can be
		final int blockChunks = rand.nextInt(StartPoint.FRAME_WIDTH / (BlockType.SIZE * 2)); // Random Picking
		LinkedHashMap<Point, BlockType> block = WorldOptions.coordinates.getLocations(p);

		for (int i = 0; i < blockChunks; i++) {
			int locationX = rand.nextInt(StartPoint.FRAME_WIDTH / BlockType.SIZE);
			int locationY = rand.nextInt(StartPoint.FRAME_HEIGHT / BlockType.SIZE);

			if (!block.containsKey(new Point(locationX, locationY))) {
				for (int j = 0; j < Math.pow(blockChunks, 2); j++) {
					int option = rand.nextInt(9); // exclusive bound

					if (option == 0) {
						if (!block.containsKey(new Point(locationX + 1, locationY))) {
							block.put(new Point(++locationX, locationY), BlockType.GRAY);
						}
					} else if (option == 1) {
						if (!block.containsKey(new Point(locationX - 1, locationY))) {
							block.put(new Point(--locationX, locationY), BlockType.GRAY);
						}
					} else if (option == 2) {
						if (!block.containsKey(new Point(locationX, locationY + 1))) {
							block.put(new Point(locationX, ++locationY), BlockType.GRAY);
						}
					} else if (option == 3) {
						if (!block.containsKey(new Point(locationX, locationY - 1))) {
							block.put(new Point(locationX, --locationY), BlockType.GRAY);
						}
					} else if (option == 4) {
						if (!block.containsKey(new Point(locationX + 1, locationY + 1))) {
							block.put(new Point(++locationX, ++locationY), BlockType.GRAY);
						}
					} else if (option == 5) {
						if (!block.containsKey(new Point(locationX + 1, locationY - 1))) {
							block.put(new Point(++locationX, --locationY), BlockType.GRAY);
						}
					} else if (option == 6) {
						if (!block.containsKey(new Point(locationX - 1, locationY + 1))) {
							WorldOptions.coordinates.getLocations(p).put(new Point(--locationX, ++locationY),
									BlockType.GRAY);
						}
					} else if (option == 7) {
						if (!block.containsKey(new Point(locationX - 1, locationY - 1))) {
							block.put(new Point(--locationX, --locationY), BlockType.GRAY);
						}
					} else {
						block.put(new Point(locationX, locationY), BlockType.GRAY);
					}
				}
			}

		}

	}

	private static void addStructures(Point chunk, Random rand) {
		// Adding structures
		// specifically - a box for now
		final int chooser = rand.nextInt(4);
		for (int i = 0; i < chooser; i++) {
			Map<Point, BlockType> house = StructureOrganizer.addRandStructure(Structure.HOUSE, chunk, rand);
			for (Map.Entry<Point, BlockType> copy : house.entrySet()) {
				WorldOptions.coordinates.getLocations(chunk).put(copy.getKey(), copy.getValue());
			}
		}
	}

}

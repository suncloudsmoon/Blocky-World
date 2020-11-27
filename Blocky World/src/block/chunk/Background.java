package block.chunk;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedHashMap;

import biome.BiomeType;
import block.BlockType;
import other.Compute;
import save.WorldOptions;
import start.Game;
import start.StartPoint;

public class Background {

	public int x;
	public int y;

	// for now; divide by 2 later
	public Point chunk = new Point(-x / StartPoint.FRAME_WIDTH, -y / StartPoint.FRAME_HEIGHT);

	// this should be executed only once!
	public Background(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private void drawBackground(int x, int y, Color c, Graphics2D g) {
		g.setColor(c);
		g.fillRect(x, y, StartPoint.FRAME_WIDTH, StartPoint.FRAME_HEIGHT);
	}

	public void display(Graphics2D g) {
		calculate(g);
	}

	private void calculate(Graphics2D g) {
		chunk.x = -x / (StartPoint.FRAME_WIDTH / 2);
		chunk.y = -y / (StartPoint.FRAME_HEIGHT / 2);

		double chunkX = -x / (StartPoint.FRAME_WIDTH / 2D);
		double chunkY = -y / (StartPoint.FRAME_HEIGHT / 2D);

//		System.out.println("Chunk: " + chunkX);
//		System.out.println("ChunkX - 1: " + (chunkX - ((int) (chunkX) - 1)));
//		System.out.println("ChunkX + 1: " + (((int) (chunkX) + 1) - chunkX));

		// X level Rendering
		if (Math.abs(chunkX - ((int) (chunkX) - 1)) > Math.abs(((int) (chunkX) + 1) - chunkX)) {
			// use the load the chunk numX + 1 in addition to chunk numX
			if (!exists((int) (chunkX) + 1, (int) chunkY)) {
				generate((int) (chunkX) + 1, (int) chunkY);
			}
			render((int) (chunkX) + 1, (int) chunkY, g);

		} else if (Math.abs(chunkX - ((int) (chunkX) - 1)) < Math.abs(((int) (chunkX) + 1) - chunkX)) {
			if (!exists((int) (chunkX) - 1, (int) chunkY)) {
				generate((int) (chunkX) - 1, (int) chunkY);
			}
			render((int) (chunkX) - 1, (int) chunkY, g);
		}

		// Y level rendering
		if (Math.abs(chunkY - ((int) (chunkY) - 1)) > Math.abs(((int) (chunkY) + 1) - chunkY)) {
			// use the load the chunk numX + 1 in addition to chunk numX
			if (!exists((int) chunkX, (int) (chunkY) + 1)) {
				generate((int) chunkX, (int) (chunkY) + 1);
			}
			render((int) chunkX, (int) (chunkY) + 1, g);

		} else if (Math.abs(chunkY - ((int) (chunkY) - 1)) < Math.abs(((int) (chunkY) + 1) - chunkY)) {
			if (!exists((int) chunkX, (int) (chunkY) - 1)) {
				generate((int) chunkX, (int) (chunkY) - 1);
			}
			render((int) chunkX, (int) (chunkY) - 1, g);
		}

		if (!exists((int) chunkX, (int) chunkY)) {
			generate((int) chunkX, (int) chunkY);
		}
		render((int) chunkX, (int) chunkY, g);
	}

	public void render(int chunkX, int chunkY, Graphics2D g) {
		drawBackground(x + (chunkX * StartPoint.FRAME_WIDTH), y + (chunkY * StartPoint.FRAME_HEIGHT),
				WorldOptions.coordinates.getBiome(new Point(chunkX, chunkY)).getC(), g);
		Game.renderBlocks(new Point(chunkX, chunkY), g);
	}

	public void generate(int chunkX, int chunkY) {
		Point p = new Point(chunkX, chunkY);
		// System.out.println(BiomeType.values()[Compute.getRandValue(0,
		// BiomeType.values().length, Game.rand)]);
		WorldOptions.coordinates.put(p,
				BiomeType.values()[Compute.getRandValue(0, BiomeType.values().length, Game.rand)],
				new LinkedHashMap<Point, BlockType>());
		ChunkAction.generateChunk(p, Game.rand);
	}

	public static boolean exists(int chunkX, int chunkY) {
		return WorldOptions.coordinates.containsKey(new Point(chunkX, chunkY));
	}
}

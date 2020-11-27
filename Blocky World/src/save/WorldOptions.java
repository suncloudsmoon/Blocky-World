package save;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import biome.BiomeType;
import block.BlockType;
import block.chunk.ChunkMap;
import other.Achievements;

public class WorldOptions {

	public static final String FOLDER = "C:\\Blocky World\\", FULLPATH = "C:\\Blocky World\\Worlds\\";
	public static final String SETTINGS_FILENAME = "Settings.bin";
	public static final String LOGGER_DIRECTORY = FOLDER + "Logger.log";

	public static ChunkMap<Point, BiomeType, LinkedHashMap<Point, BlockType>> coordinates;
	private static long seed;

	private static String fileName;

	private WorldOptions() {
		
	}

	// I will do serialization
	public static void saveWorld(String path) throws FileNotFoundException, IOException {
		try (ObjectOutputStream object = new ObjectOutputStream(new FileOutputStream(path))) {
			object.writeLong(seed); // Writing seed to file
			object.writeObject(coordinates); // Writing ChunkMap instance to file
		}

	}

	@SuppressWarnings("unchecked")
	public static ChunkMap<Point, BiomeType, LinkedHashMap<Point, BlockType>> loadWorld(String path)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		try (ObjectInputStream object = new ObjectInputStream(new FileInputStream(path))) {
			seed = object.readLong();
			return (ChunkMap<Point, BiomeType, LinkedHashMap<Point, BlockType>>) object.readObject();
		}

	}

	public static void saveGameData(String path) throws FileNotFoundException, IOException {
		try (ObjectOutputStream object = new ObjectOutputStream(new FileOutputStream(path))) {
			if (Achievements.isEnabled()) {
				object.writeByte(Achievements.getLevel());
			} else {
				object.writeByte(-Achievements.getLevel());
			}

		}
	}

	public static boolean getGameData(String path) throws FileNotFoundException, IOException {
		if (checkFile(FOLDER + SETTINGS_FILENAME)) {
			try (ObjectInputStream object = new ObjectInputStream(new FileInputStream(path))) {
				// Reading the achievement setting
				byte level = object.readByte();
				Achievements.setEnabled(level > 0);
				Achievements.setLevel(level);
			}
			return true;
		} else {
			return false;
		}

	}

	public static boolean checkFile(String directory) {
		return new File(directory).exists();
	}

	public static long getSeed() {
		return seed;
	}

	public static void setSeed(long seed) {
		WorldOptions.seed = seed;
	}

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		WorldOptions.fileName = fileName;
	}

}

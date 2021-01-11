package save;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import biome.BiomeType;
import block.BlockType;
import block.chunk.ChunkMap;
import tilegame2d.storage.SimpleStorage;

public class WorldOptions {

	/**
	 * The main home folder located in <b>%localappdata%\Blocky World</b> or <b>UNIVERSAL_FOLDER</b> without "\\" at the end.
	 */
	public static String mainHome;
	
	public static final String UNIVERSAL_FOLDER = "C:\\Blocky World";
	public static final String WORLD_LOCATION = "\\Worlds";
	public static final String LOGGER_DIRECTORY = "\\Logger.log";
	public static final String SETTINGS_DATA = "\\Settings.json";
	public static final String GENERAL_DATA = "\\GameData.bin";

	public static ChunkMap<Point, BiomeType, LinkedHashMap<Point, BlockType>> coordinates;
	private static long seed;

	private static String fileName;

	private WorldOptions() {

	}

	// I will do serialization
	public static void saveWorld(String path) throws IOException {
		try (ObjectOutputStream object = new ObjectOutputStream(new FileOutputStream(path))) {
			object.writeLong(seed); // Writing seed to file
			object.writeObject(coordinates); // Writing ChunkMap instance to file
		}

	}

	@SuppressWarnings("unchecked")
	public static ChunkMap<Point, BiomeType, LinkedHashMap<Point, BlockType>> loadWorld(String path)
			throws ClassNotFoundException, IOException {
		try (ObjectInputStream object = new ObjectInputStream(new FileInputStream(path))) {
			seed = object.readLong();
			return (ChunkMap<Point, BiomeType, LinkedHashMap<Point, BlockType>>) object.readObject();
		}

	}

	public static void saveGameData(String data, String settings) throws IOException {
		// Storing the real data in binary
		try (ObjectOutputStream object = new ObjectOutputStream(new FileOutputStream(data))) {
			if (Achievements.isEnabled()) {
				object.writeByte(Achievements.getLevel());
			}

		}

		// Storing the settings data in a JSON format
		JSONObject store = new JSONObject();
		JSONObject achievements = new JSONObject();
		achievements.put("Enabled", Achievements.isEnabled());
		store.put("Achievements", achievements);
		SimpleStorage.saveData(store.toString(), settings);

	}

	public static boolean getGameData(String data, String settings) throws IOException {
		File d = new File(data);
		File s = new File(settings);
		if (d.exists() && s.exists()) {
			String settingsData = SimpleStorage.getData(settings);

			JSONObject read = new JSONObject(settingsData);
			JSONObject ach = (JSONObject) read.get("Achievements");

			boolean levelEnabled = (boolean) ach.get("Enabled");
			Achievements.setEnabled(levelEnabled);

			if (levelEnabled) {
				try (ObjectInputStream o = new ObjectInputStream(new FileInputStream(new File(data)))) {
					Achievements.setLevel(o.readByte());
				}
			}
			return true;
		} else {
			return false;
		}

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

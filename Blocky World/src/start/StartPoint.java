package start;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import mainmenu.MainMenu;
import save.WorldOptions;

/**
 * Where the game starts
 * 
 * @author Ganesha Ajjampura
 * @version 0.0.1
 */
public class StartPoint implements WindowListener {

	// TODO: make one (new point object & use it elsewhere without creating new
	// ones)

	/**
	 * The current version number of this game
	 */
	public static final String VERSION_NUM = "0.0.1";
	/**
	 * The current version type of this game
	 */
	public static final String VERSION_TYPE = "alpha";

	/**
	 * The frame width set to JFrame
	 */
	public static final int FRAME_WIDTH = 1000;
	/**
	 * The frame height set to JFrame
	 */
	public static final int FRAME_HEIGHT = 600;

	/**
	 * Logs error messages here
	 */
	public static Logger log;
	public static JFrame frame;

	/**
	 * The starting point of the program
	 */
	public StartPoint() {
		// Storage settings applied
		storageSetup();
		
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame = new JFrame("Blocky World");
		new MainMenu(frame);
		
		finalizeIt();

	}

	public static void main(String[] args) {
		new StartPoint();
	}

	private void storageSetup() {
		String storagePlace = System.getenv("LOCALAPPDATA");
		System.out.println("App Data Link: " + storagePlace); // for now, remove it later

		if (storagePlace != null) {
			WorldOptions.mainHome = storagePlace;
		} else {
			WorldOptions.mainHome = WorldOptions.UNIVERSAL_FOLDER;
		}

		new File(WorldOptions.mainHome + WorldOptions.WORLD_LOCATION).mkdirs();

		try {
			addLogger(WorldOptions.mainHome + WorldOptions.LOGGER_DIRECTORY);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			importGameData();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void finalizeIt() {
		if (System.getProperty("os.name").equals("Windows 10")) {
			frame.setSize(FRAME_WIDTH + 16, FRAME_HEIGHT + 7);
//			try {
//				Process s = Runtime.getRuntime().exec("SYSTEMINFO");
//				InputStream st = s.getInputStream();
//				st.transferTo(new FileOutputStream(new File("Hello.txt")));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} else {
			frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		}

		frame.addWindowListener(this);
		frame.setLocationByPlatform(true);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	public static void addLogger(String directory) throws IOException {
		log = Logger.getLogger("");
		FileHandler handler = new FileHandler(directory);
		SimpleFormatter formatter = new SimpleFormatter();

		handler.setFormatter(formatter);
		log.addHandler(handler);
	}

	public static boolean importGameData() throws IOException {
		// Checking if settings data exists && the whole game directory
		return WorldOptions.getGameData(WorldOptions.mainHome + WorldOptions.GENERAL_DATA,
				WorldOptions.mainHome + WorldOptions.SETTINGS_DATA);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			// First, save game data
			WorldOptions.saveGameData(WorldOptions.mainHome + WorldOptions.GENERAL_DATA,
					WorldOptions.mainHome + WorldOptions.SETTINGS_DATA);
			System.out.println("Settings Saved!");

			// Then, save world data if it exists!
			if (WorldOptions.coordinates != null) {
				WorldOptions.saveWorld(WorldOptions.mainHome + "\\" + WorldOptions.getFileName());
				System.out.println("World saved!");
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(0);

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}

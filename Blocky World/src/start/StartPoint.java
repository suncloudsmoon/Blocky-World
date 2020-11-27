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
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import mainmenu.MainMenu;
import save.WorldOptions;

public class StartPoint implements WindowListener {

	// TODO: make one (new point object & use it elsewhere without creating new
	// ones)

	public static final String VERSION_NUM = "1.0.1";
	public static final String VERSION_TYPE = "alpha";

	public static final int FRAME_WIDTH = 1000;
	public static final int FRAME_HEIGHT = 600;

	public static Logger log;
	public static JFrame frame;

	public StartPoint() {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create new directories
		new File(WorldOptions.FULLPATH).mkdirs();

		try {
			importGameData();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			addLogger(WorldOptions.LOGGER_DIRECTORY);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame = new JFrame("Blocky World");

		new MainMenu(frame);

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
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}

	public static void main(String[] args) {
		new StartPoint();
	}

	public static void test() {
		// System.out.println(Biome.GRASSLAND);
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
		return WorldOptions.getGameData(WorldOptions.FOLDER + WorldOptions.SETTINGS_FILENAME);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			// First, save game data
			WorldOptions.saveGameData(WorldOptions.FOLDER + "Settings.bin");
			System.out.println("Settings Saved!");

			// Then, save world data if it exists!
			if (WorldOptions.coordinates != null) {
				WorldOptions.saveWorld(WorldOptions.FULLPATH + WorldOptions.getFileName());
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

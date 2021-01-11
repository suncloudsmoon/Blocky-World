package save;

import javax.swing.JOptionPane;

import start.StartPoint;

public class Achievements {
	protected static final String[] ACHIEVEMENT_DESCRIPTION = { "Level 1:\nRace Around Your Blocky World" };
	/**
	 * Length of the String[] ACHIEVEMENTS.
	 */
	private static byte level = 1; // if the achievements are turned off, its -1

	private static boolean enabled = true;
	
	private Achievements() {
		
	}

	public static void showAchievement(byte level) {
		JOptionPane.showMessageDialog(StartPoint.frame, ACHIEVEMENT_DESCRIPTION[level], "Achievement Unlocked",
				JOptionPane.INFORMATION_MESSAGE);
		
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean enabled) {
		Achievements.enabled = enabled;
	}

	public static byte getLevel() {
		return level;
	}

	public static void setLevel(byte level) {
		Achievements.level = level;
	}

}

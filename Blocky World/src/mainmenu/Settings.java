package mainmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import save.Achievements;

public class Settings implements ActionListener {

	private static JPanel panel;

	private static JLabel settingsPage;
	
	private static JLabel achievementText;
	private static JButton enableAchievements;
	
	private static JButton goBack;

	public Settings(JPanel panel) {
		if (settingsPage == null) {
			Settings.panel = panel;
			setUp();
		} else {
			secondSetUp();
		}
		panel.revalidate();
		panel.repaint();
	}

	private void setUp() {
		settingsPage = new JLabel("Settings");
		settingsPage.setBounds((13 * panel.getWidth()) / 30, 0, 200, 100);
		settingsPage.setFont(MainMenu.HEADER);
		
		achievementText = new JLabel("Achievements:");
		achievementText.setBounds(20, 100, 80, 25);
		
		enableAchievements = new JButton();
		enableAchievements.setBounds(achievementText.getX() + achievementText.getWidth() + 10, achievementText.getY(), 65, 25);
		enableAchievements.addActionListener(this);
		if (Achievements.isEnabled()) {
			enableAchievements.setText("ON");
		} else {
			enableAchievements.setText("OFF");
		}
	
		goBack = new JButton("<== Go Back");
		goBack.setBounds(10, panel.getHeight() - 60, 100, 50);
		goBack.addActionListener(this);

		panel.add(settingsPage);
		panel.add(achievementText);
		panel.add(enableAchievements);
		panel.add(goBack);
	}

	private void secondSetUp() {
		goBack.setEnabled(true);
		
		panel.add(settingsPage);
		panel.add(achievementText);
		panel.add(enableAchievements);
		panel.add(goBack);
	}

	public void clear() {
		enableAchievements.setEnabled(false);
		goBack.setEnabled(false);
		panel.removeAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == goBack) {
			clear();
			new MainMenu(null);
		} else if (e.getSource() == enableAchievements) {
			if (Achievements.isEnabled()) {
				Achievements.setEnabled(false);
				enableAchievements.setText("OFF");
			} else {
				if (Achievements.getLevel() < 0) {
					Achievements.setLevel((byte) Math.abs(Achievements.getLevel()));
				}
				Achievements.setEnabled(true);
				enableAchievements.setText("ON");
			}
			
		}
		
	}
}

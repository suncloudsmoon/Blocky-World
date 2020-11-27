package mainmenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import start.StartPoint;

public class MainMenu implements ActionListener {

	private static JFrame frame;
	private static JPanel panel;

	private static JLabel versionInfo, gameTitle;

	// Three Buttons
	private static JButton play, help, settings;

	public static final Font HEADER = new Font("Heading", Font.BOLD, 40);

	public MainMenu(JFrame frame) {
		if (frame != null) {
			MainMenu.frame = frame;
			panel = new JPanel();
			panel.setBounds(0, 0, StartPoint.FRAME_WIDTH, StartPoint.FRAME_HEIGHT);
			panel.setLayout(null);

			setUp();
			frame.add(panel);
		} else {
			secondSetUp();
			panel.revalidate();
			panel.repaint();
		}

	}

	private void setUp() {
		panel.setBackground(Color.lightGray);
		
		versionInfo = new JLabel("Version: v" + StartPoint.VERSION_NUM + "-" + StartPoint.VERSION_TYPE);
		versionInfo.setBounds(10, 10, 150, 25);

		gameTitle = new JLabel("Blocky World");
		gameTitle.setFont(HEADER);
		gameTitle.setBounds((23 * panel.getWidth()) / 60, 0, 280, 100);

		play = new JButton("Play");
		play.setBounds((18 * panel.getWidth()) / 40, panel.getHeight() / 2 - 90, 100, 50);
		play.addActionListener(this);

		help = new JButton("Help");
		help.setBounds((18 * panel.getWidth()) / 40, panel.getHeight() / 2 - 30, 100, 50);
		help.addActionListener(this);

		settings = new JButton("Settings");
		settings.setBounds((18 * panel.getWidth()) / 40, panel.getHeight() / 2 + 30, 100, 50);
		settings.addActionListener(this);

		panel.add(versionInfo);
		panel.add(gameTitle);
		panel.add(play);
		panel.add(help);
		panel.add(settings);

	}

	private void secondSetUp() {
		play.setEnabled(true);
		help.setEnabled(true);
		settings.setEnabled(true);

		panel.add(versionInfo);
		panel.add(gameTitle);
		panel.add(play);
		panel.add(help);
		panel.add(settings);
	}

	private void clear() {
		play.setEnabled(false);
		help.setEnabled(false);
		settings.setEnabled(false);
		panel.removeAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == play) {
			clear();
			new Play(panel, frame);
		} else if (e.getSource() == help) {
			clear();
			new Help(panel);
		} else if (e.getSource() == settings) {
			clear();
			new Settings(panel);
		}

	}
}

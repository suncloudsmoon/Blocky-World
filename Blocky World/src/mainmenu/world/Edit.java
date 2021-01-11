package mainmenu.world;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainmenu.Play;
import save.WorldOptions;
import start.Game;

public class Edit implements ActionListener {

	private static JFrame frame;
	private static JPanel panel;

	private static JLabel pageInfo;

	private static JLabel name;
	private static JTextField enterName;

	private static JLabel seedIndicator;
	private static JTextField enterSeed;

	private JButton goBack;
	private JButton playWorld;

	public Edit(String fileName, String path, JPanel panel, JFrame frame) {
		Edit.panel = panel;
		Edit.frame = frame;
		WorldOptions.setFileName(fileName);
		setUp(fileName, path);
		panel.repaint();
	}

	private void setUp(String fileName, String path) {
		try {
			WorldOptions.loadWorld(path);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pageInfo = new JLabel("Edit");
		pageInfo.setBounds(panel.getWidth() / 3, 10, 250, 100);
		pageInfo.setFont(new Font("Heading", Font.BOLD, 40));

		name = new JLabel("Name:");
		name.setBounds(30, 100, 50, 25);

		enterName = new JTextField(fileName);
		enterName.setBounds(name.getX() + name.getWidth() + 30, name.getY(), 150, 25);

		seedIndicator = new JLabel("Seed:");
		seedIndicator.setBounds(name.getX(), name.getY() + name.getHeight() * 2, 50, 25);

		enterSeed = new JTextField(String.valueOf(WorldOptions.getSeed()));
		enterSeed.setBounds(seedIndicator.getX() + seedIndicator.getWidth() + 30, seedIndicator.getY(), 150, 25);

		goBack = new JButton("<== Back");
		goBack.setBounds(10, panel.getHeight() - 50, 100, 50);
		goBack.addActionListener(this);

		playWorld = new JButton("Play ==>");
		playWorld.setBounds((5 * panel.getWidth()) / 6, panel.getHeight() - 50, 100, 50);
		playWorld.addActionListener(this);

		panel.add(pageInfo);
		panel.add(name);
		panel.add(enterName);
		panel.add(seedIndicator);
		panel.add(enterSeed);
		panel.add(goBack);
		panel.add(playWorld);
	}

	public void clear() {
		enterName.setEnabled(false);
		enterSeed.setEnabled(false);
		playWorld.setEnabled(false);
		panel.removeAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playWorld) {
			if (!enterSeed.getText().isBlank() && !enterName.getText().isBlank()) {
				try {
					File oldFile = new File(WorldOptions.mainHome + "\\" + WorldOptions.getFileName() + ".bin");
					WorldOptions.setFileName(enterName.getText() + ".bin");
					oldFile.renameTo(new File(WorldOptions.mainHome + "\\" + WorldOptions.getFileName()));

					clear();
					panel.setEnabled(false);
					new Game(Long.parseLong(enterSeed.getText()), frame);
				} catch (NullPointerException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == goBack) {
			clear();
			new Play(panel, frame);
		}

	}
}

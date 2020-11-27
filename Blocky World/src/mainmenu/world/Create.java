package mainmenu.world;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainmenu.Play;
import save.WorldOptions;
import start.Game;

public class Create implements ActionListener {

	private JFrame frame;
	private JPanel panel;

	private JLabel pageInfo;

	private JLabel name;
	private JTextField enterName;

	private JLabel seedIndicator;
	private JTextField enterSeed;

	private JButton goBack;
	private JButton createWorld;

	public Create(JPanel panel, JFrame frame) {
		this.panel = panel;
		this.frame = frame;
		setUp();
		panel.repaint();
	}

	private void setUp() {

		pageInfo = new JLabel("Create World");
		pageInfo.setBounds(panel.getWidth() / 3, 10, 250, 100);
		pageInfo.setFont(new Font("Heading", Font.BOLD, 40));

		name = new JLabel("Name:");
		name.setBounds(30, 100, 50, 25);

		enterName = new JTextField();
		enterName.setBounds(name.getX() + name.getWidth() + 30, name.getY(), 150, 25);

		seedIndicator = new JLabel("Seed:");
		seedIndicator.setBounds(name.getX(), name.getY() + name.getHeight() * 2, 50, 25);

		enterSeed = new JTextField();
		enterSeed.setBounds(seedIndicator.getX() + seedIndicator.getWidth() + 30, seedIndicator.getY(), 150, 25);

		goBack = new JButton("<== Back");
		goBack.setBounds(10, panel.getHeight() - 50, 100, 50);
		goBack.addActionListener(this);

		createWorld = new JButton("Create ==>");
		createWorld.setBounds((5 * panel.getWidth()) / 6, panel.getHeight() - 50, 100, 50);
		createWorld.addActionListener(this);

		panel.add(pageInfo);
		panel.add(name);
		panel.add(enterName);
		panel.add(seedIndicator);
		panel.add(enterSeed);
		panel.add(goBack);
		panel.add(createWorld);
	}

	public void clear() {
		enterName.setEnabled(false);
		enterSeed.setEnabled(false);
		createWorld.setEnabled(false);
		panel.removeAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == createWorld) {
			if (!enterName.getText().isBlank()) {
				try {
					long seed;
					if (!enterSeed.getText().isBlank()) {
						seed = Long.parseLong(enterSeed.getText());
					} else {
						seed = (long) (Math.random() * Integer.MAX_VALUE);
					}
					clear();
					panel.setEnabled(false);
					frame.remove(panel);
					frame.revalidate();
					frame.repaint();
					WorldOptions.setFileName(enterName.getText() + ".bin");
					System.out.println("fileName=" + WorldOptions.getFileName());
					new Game(seed, frame);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(panel, "Unable to process input info:\n" + e1.getMessage(),
							"Input Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == goBack) {
			clear();
			new Play(panel, frame);
		}

	}
}

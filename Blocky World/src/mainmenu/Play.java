package mainmenu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import mainmenu.world.Create;
import mainmenu.world.Edit;
import save.WorldOptions;
import start.Game;

public class Play implements ActionListener {

	private static JFrame frame;
	private static JPanel panel;

	// List showing all the saved worlds!
	private static JList<String> list;
	private static JScrollPane scroller;

	private static JButton play, create, edit, goBack;

	public Play(JPanel panel, JFrame frame) {
		if (scroller == null) {
			Play.frame = frame;
			Play.panel = panel;
			setUp();
		} else {
			secondSetUp();
		}

		panel.revalidate();
		panel.repaint();
	}

	private void setUp() {
		// Setting up all the buttons
		play = new JButton("Play");
		play.setBounds((23 * panel.getWidth()) / 60, 20, 100, 50);
		play.addActionListener(this);

		create = new JButton("Create");
		create.setBounds((23 * panel.getWidth()) / 60 + 100, 20, 100, 50);
		create.addActionListener(this);

		edit = new JButton("Edit");
		edit.setBounds((23 * panel.getWidth()) / 60 + 50, 70, 100, 50);
		edit.addActionListener(this);

		// System.out.println("Path: " + WorldOptions.PATH);
		// System.out.println(new File(WorldOptions.PATH).mkdirs());

		if (new File(WorldOptions.mainHome + WorldOptions.WORLD_LOCATION).exists()) {
			list = new JList<String>(new File(WorldOptions.mainHome + WorldOptions.WORLD_LOCATION).list());
		} else {
			list = new JList<String>();
		}

		list.setLayoutOrientation(JList.VERTICAL);
		DefaultListCellRenderer center = (DefaultListCellRenderer) list.getCellRenderer();
		center.setHorizontalAlignment((int) JList.CENTER_ALIGNMENT);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		scroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(panel.getWidth() / 5, panel.getHeight() / 4, (22 * panel.getWidth()) / 40,
				(2 * panel.getHeight()) / 3);
		scroller.setBorder(new LineBorder(Color.black));

		goBack = new JButton("<== Go Back");
		goBack.setBounds(10, panel.getHeight() - 60, 100, 50);
		goBack.addActionListener(this);

		panel.add(play);
		panel.add(create);
		panel.add(edit);
		panel.add(scroller);
		panel.add(goBack);
	}

	private void secondSetUp() {
		play.setEnabled(true);
		create.setEnabled(true);
		edit.setEnabled(true);
		scroller.setEnabled(true);
		goBack.setEnabled(true);

		panel.add(play);
		panel.add(create);
		panel.add(edit);
		panel.add(scroller);
		panel.add(goBack);
	}

	public void clear() {
		play.setEnabled(false);
		create.setEnabled(false);
		edit.setEnabled(false);
		scroller.setEnabled(false);
		goBack.setEnabled(false);
		panel.removeAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == play) {
			if (!list.isSelectionEmpty()) {
				clear();
				panel.setEnabled(false);
				frame.remove(panel);

				WorldOptions.setFileName(list.getSelectedValue());
				new Game(WorldOptions.mainHome + WorldOptions.getFileName(), frame);
			}
		} else if (e.getSource() == create) {
			clear();
			new Create(panel, frame);
		} else if (e.getSource() == edit) {
			if (!list.isSelectionEmpty()) {
				clear();
				WorldOptions.setFileName(list.getSelectedValue());
				new Edit(list.getSelectedValue().substring(0, list.getSelectedValue().length() - 4),
						WorldOptions.mainHome + WorldOptions.getFileName(), panel, frame);
			}

		} else if (e.getSource() == goBack) {
			clear();
			new MainMenu(null);
		}

	}
}

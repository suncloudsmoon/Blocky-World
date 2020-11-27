package mainmenu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class Help implements ActionListener {
	
	private static JPanel panel;
	
	private static JLabel helpPage;
	private static JTextArea gameInfo; // this is non-editable
	private static JScrollPane scroller;
	private static JButton goBack;
	
	public Help(JPanel panel) {
		
		if (gameInfo == null) {
			Help.panel = panel;
			setUp();
		} else {
			secondSetUp();
		}
		
		panel.revalidate();
		panel.repaint();
	}
	
	private void setUp() {
		String data = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource("Help.txt").toURI())))) {
			String line;
			while ((line = reader.readLine()) != null) {
				data += line;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		helpPage = new JLabel("Help");
		helpPage.setBounds((13 * panel.getWidth()/30), 0, 100, 100);
		helpPage.setFont(MainMenu.HEADER);
		
		gameInfo = new JTextArea(data);
		gameInfo.setLineWrap(true);
		gameInfo.setWrapStyleWord(true);
		gameInfo.setEditable(false);
		
		scroller = new JScrollPane(gameInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(panel.getWidth()/4, helpPage.getY() + helpPage.getHeight(), panel.getWidth()/2, panel.getHeight()/2);
		scroller.setBorder(new LineBorder(Color.black));
		
		goBack = new JButton("<== Go Back");
		goBack.setBounds(10, panel.getHeight() - 60, 100, 50);
		goBack.addActionListener(this);
		
		panel.add(helpPage);
		panel.add(scroller);
		panel.add(goBack);
	}
	
	public void secondSetUp() {
		gameInfo.setEnabled(true);
		goBack.setEnabled(true);
		
		panel.add(helpPage);
		panel.add(scroller);
		panel.add(goBack);
	}
	
	public void clear() {
		gameInfo.setEnabled(false);
		goBack.setEnabled(false);
		panel.removeAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		clear();
		new MainMenu(null);
	}
}

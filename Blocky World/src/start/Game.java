package start;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import block.BlockType;
import block.CollisionDetection;
import block.chunk.Background;
import block.chunk.ChunkMap;
import entity.car.Car;
import gui.ActionBar;
import player.Player;
import save.WorldOptions;

public class Game extends JPanel implements MouseListener {

	private static final long serialVersionUID = 2920781633355292195L;

	private static JFrame frame; // change this to an instance
	public static Random rand;

	public static Car car;
	public static Player player;
	public static Background b;
	public static ActionBar a;

	private static int mouseX, mouseY;

	private static long fps;
	private static double delta;
	private static boolean running = true;

	private Action goUp, goDown, goLeft, goRight;

	// for creating new games
	public Game(long seed, JFrame frame) {
		// Initializing & Creating objects
		Game.frame = frame;
		// car = new Car("Speed", Mood.excited); // for now
		player = new Player("John", (9 * StartPoint.FRAME_WIDTH) / 22, (9 * StartPoint.FRAME_HEIGHT) / 22, 1);
		b = new Background(0, 0);

		WorldOptions.setSeed(seed);
		rand = new Random(seed);
		WorldOptions.coordinates = new ChunkMap<>();

		gameLoop();

		addMouseListener(this);
		frame.getContentPane().add(this);
		addKeyBindings();
		frame.revalidate();
		frame.repaint();
	}

	// for loading existing games
	public Game(String path, JFrame frame) {
		// Creating & Initializing objects
		Rectangle r = new Rectangle(0, 0, StartPoint.FRAME_WIDTH, 35); // for now its 200
		Font f = new Font("Text", Font.BOLD, 20);
		b = new Background(0, 0);
		a = new ActionBar(r, f, Color.WHITE, false);
		player = new Player("John", (9 * StartPoint.FRAME_WIDTH) / 22, (9 * StartPoint.FRAME_HEIGHT) / 22, 1);
		// Loading the existing
		try {
			WorldOptions.coordinates = WorldOptions.loadWorld(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rand = new Random(WorldOptions.getSeed());

		gameLoop();

		addMouseListener(this);
		frame.add(this);
		addKeyBindings();
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void paintComponent(Graphics gPrimitive) {
		super.paintComponent(gPrimitive);
		Graphics2D g = (Graphics2D) gPrimitive;

		renderChunk(g);
		// renderCar(g);
		renderPlayer(g);
		renderActionBar(g);

//		g.setColor(Color.black);
//		g.drawString("X: " + -b.x + " Y: " + -b.y, 10, 10); // player
//		g.drawString("MouseX: " + mouseX + " MouseY: " + mouseY, 70, 10);
//		g.drawString("FPS: " + fps + " FPS", 100, 10);

	}

	public void gameLoop() {

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (running) {
					double previousTime = System.nanoTime();

					// Code here
					// calculateRoute();
					// System.out.println(CollisionDetection.checkCollision());
					repaint();

					try {
						Thread.sleep(18);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					double currentTime = System.nanoTime();
					delta = (currentTime - previousTime) / (1e+9);
					fps = (long) (1 / delta);

				}
			}
		});
		t.start();

	}

	public void collisionDetection() {

	}

	public void renderChunk(Graphics2D g) {
		b.display(g);
	}

	public void renderPlayer(Graphics2D g) {
		player.drawPlayer(g);
	}

//	public void renderCar(Graphics2D g) {
//		car.drawCar(delta, g);
//	}

	public void renderActionBar(Graphics2D g2d) {
		a.drawActionBar(g2d);
	}

	public static void renderBlocks(Point chunk, Graphics2D g) {
		for (Map.Entry<Point, BlockType> block : WorldOptions.coordinates.getLocations(chunk).entrySet()) {
			if (block.getValue().getImg() == null) {
				g.setColor(block.getValue().getC());
				g.fill3DRect(b.x + (chunk.x * StartPoint.FRAME_WIDTH) + (block.getKey().x * BlockType.SIZE),
						b.y + (chunk.y * StartPoint.FRAME_HEIGHT) + (block.getKey().y * BlockType.SIZE),
						BlockType.SIZE * block.getValue().getRect().width,
						BlockType.SIZE * block.getValue().getRect().height, true);
			} else {
				g.drawImage(block.getValue().getImg(),
						b.x + (chunk.x * StartPoint.FRAME_WIDTH) + (block.getKey().x * BlockType.SIZE),
						b.y + (chunk.y * StartPoint.FRAME_HEIGHT) + (block.getKey().y * BlockType.SIZE), null);
			}
		}
	}

	public void calculateRoute() {
		if (!car.isMoving()) {
			System.out.println("Destination Changed!"); // change the destination
			car.setDestination();
		}

	}

	// Put this in CollisionDetection class!
	public boolean removeStructure(Point p) {
		LinkedHashMap<Point, BlockType> chunk = WorldOptions.coordinates.getLocations(b.chunk);

		int ansX = 0;
		int ansY = 0;

		for (Map.Entry<Point, BlockType> check : chunk.entrySet()) {
			int rectX = check.getKey().x;
			int rectY = check.getKey().y;
			int width = check.getValue().getRect().width;
			int height = check.getValue().getRect().height;
			if (((width > 1) || (height > 1))
					&& ((rectX <= p.x && rectX + width >= p.x) && (rectY <= p.y && rectY + height >= p.y))) {
				ansX = rectX;
				ansY = rectY;
				break;
			}
		}
		if (ansX != 0 || ansY != 0) {
			chunk.remove(new Point(ansX, ansY));
			return true;
		}
		return false;
	}

	public void addKeyBindings() {

		goUp = new GoUp();
		goDown = new GoDown();
		goLeft = new GoLeft();
		goRight = new GoRight();

		JRootPane rootOfAll = StartPoint.frame.getRootPane(); // for now

		rootOfAll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('w'), goUp);
		rootOfAll.getActionMap().put(goUp, goUp);

		rootOfAll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('s'), goDown);
		rootOfAll.getActionMap().put(goDown, goDown);

		rootOfAll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('a'), goLeft);
		rootOfAll.getActionMap().put(goLeft, goLeft);

		rootOfAll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('d'), goRight);
		rootOfAll.getActionMap().put(goRight, goRight);

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
//		mouseX = e.getX();
//		mouseY = e.getY();

		Point mouse = new Point((-b.x + (-b.chunk.x * StartPoint.FRAME_WIDTH) + e.getX()) / BlockType.SIZE,
				(-b.y + (-b.chunk.y * StartPoint.FRAME_HEIGHT) + e.getY()) / BlockType.SIZE);
		if (!removeStructure(mouse)) {
			if (WorldOptions.coordinates.getLocations(b.chunk).containsKey(mouse)) {
				// Remove blocks
				WorldOptions.coordinates.getLocations(b.chunk).remove(mouse);
			} else {
				// Place blocks
				WorldOptions.coordinates.getLocations(b.chunk).put(mouse, player.inventory[player.getSelectedItem()]);
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static class GoUp extends AbstractAction {

		private static final long serialVersionUID = -6858278947676316180L;

		@Override
		public void actionPerformed(ActionEvent e) {
			b.y += player.speed * delta;
			if (CollisionDetection.checkCollision()) {
				b.y -= player.speed * delta;
			}
		}

	}

	public static class GoDown extends AbstractAction {

		private static final long serialVersionUID = 3568086833232722124L;

		@Override
		public void actionPerformed(ActionEvent e) {
			b.y -= player.speed * delta;
			if (CollisionDetection.checkCollision()) {
				b.y += player.speed * delta;
			}

		}

	}

	public static class GoLeft extends AbstractAction {

		private static final long serialVersionUID = 5382616582561126041L;

		@Override
		public void actionPerformed(ActionEvent e) {
			b.x += player.speed * delta;
			if (CollisionDetection.checkCollision()) {
				b.x -= player.speed * delta;
			}

		}

	}

	public static class GoRight extends AbstractAction {

		private static final long serialVersionUID = 4867763915444162270L;

		@Override
		public void actionPerformed(ActionEvent e) {
			b.x -= player.speed * delta;
			if (CollisionDetection.checkCollision()) {
				b.x += player.speed * delta;
			}

		}

	}

}

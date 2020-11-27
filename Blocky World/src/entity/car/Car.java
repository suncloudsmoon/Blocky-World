package entity.car;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import entity.Entity;
import entity.Mood;
import start.StartPoint;

public class Car extends Entity {

	private double score;

	private double speedX, speedY;

	private int goalX, goalY;

	private boolean isRunning = true;

	private int x = 100, y = 100;

	public static final int WIDTH = 100, HEIGHT = 100;

	private Color c = Color.BLUE;

	public Car(String name, Mood mood) {
		super(name, mood, WIDTH, HEIGHT);
		score = mood.getScore();
	}

	public void drawCar(double delta, Graphics2D g) {
//		System.out.println("Delta: " + delta);
//		System.out.println("SpeedX: " + speedX);
//		System.out.println("SpeedY: " + speedY);

		g.setColor(c);
		g.fillRoundRect(x += speedX * 100 * delta, y += speedY * 100 * delta, WIDTH, HEIGHT, WIDTH / 5, WIDTH / 5);

	}

	// Manipulate the x and y coordinates
	public void setDestination() {
		move((int) (Math.random() * StartPoint.FRAME_WIDTH - 100), (int) (Math.random() * StartPoint.FRAME_HEIGHT - 100),
				1);
	}

	public boolean isMoving() {
		return !changeSpeed();
	}

	public void stopCar() {
		speedX = 0;
		speedY = 0;
	}

	public void learn(Point[] p) {
		// Learn only the borders of the blocks not the air
		// Serialize this too

		// If the landscape is too boring, subtract stuff from the score

	}

	/**
	 * @deprecated
	 */
	public int travelMood() {
		// Depends on the mood
		// For example, if the mood is happy, the car will want to move larger distances
		// If something exiting is found, change the mood.
		return 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getGoalX() {
		return goalX;
	}

	public void setGoalX(int goalX) {
		this.goalX = goalX;
	}

	public int getGoalY() {
		return goalY;
	}

	public void setGoalY(int goalY) {
		this.goalY = goalY;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}

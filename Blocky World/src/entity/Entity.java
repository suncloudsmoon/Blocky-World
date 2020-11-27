package entity;

public abstract class Entity {

	private String name;
	private Mood mood;

	private final int w, h;

	private int goalX, goalY;
	private double backlogSpeedX, backlogSpeedY;

	public Entity(String name, Mood mood, int w, int h) {
		this.name = name;
		this.mood = mood;
		this.w = w;
		this.h = h;
	}

	public void move(int goalX, int goalY, double speed) {
		System.out.printf("goalX: %d, goalY: %d\n", goalX, goalY);
		
		backlogSpeedX = 0;
		backlogSpeedY = 0;
		this.goalX = goalX;
		this.goalY = goalY;
		
		// for now
		if (!(getX() <= goalX && getX() + w >= goalX)) {
			if (goalX - getX() >= goalY - getY()) {
				if (goalX - getX() > 0) {
					setSpeedX(speed);
				} else {
					setSpeedX(-speed);
				}
			} else {
				backlogSpeedX = (goalX - getX() > 0) ? speed : -speed;
			}
		} else {
			setSpeedX(0);
		}

		if (!(getY() <= goalY && getY() + h >= goalY)) {
			if (goalY - getY() >= goalX - getX()) {
				if (goalY - getY() > 0) {
					setSpeedY(speed);
				} else {
					setSpeedY(-speed);
				}
			} else {
				backlogSpeedY = (goalY - getY() > 0) ? speed : -speed;
			}
		} else {
			setSpeedY(0);
		}

	}

	public boolean changeSpeed() {
		boolean xReached = getX() <= goalX && getX() + w >= goalX;
		boolean yReached = getY() <= goalY && getY() + h >= goalY;
		
		// System.out.printf("backlogSpeedX: %.1f, backlogSpeedY: %.1f\n", backlogSpeedX, backlogSpeedY);

		if (xReached && yReached) {
			setSpeedX(0);
			setSpeedY(0);
			return true;
		} else if (xReached) {
			setSpeedX(0);
			if (backlogSpeedY != 0) {
				setSpeedY(backlogSpeedY);
				backlogSpeedY = 0;
			}
		} else if (yReached) {
			setSpeedY(0);
			if (backlogSpeedX != 0) {
				setSpeedX(backlogSpeedX);
				backlogSpeedX = 0;
			}
		}
		
		return false;
	}

	public abstract int getX();

	public abstract int getY();

	public abstract void setX(int x);

	public abstract void setY(int y);

	public abstract double getSpeedX();

	public abstract double getSpeedY();

	public abstract void setSpeedX(double vx);

	public abstract void setSpeedY(double vy);

}

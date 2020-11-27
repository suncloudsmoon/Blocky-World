package entity;

public enum Mood {

	excited(1), happy(0.9), sad(0.6), angry(0.3); // On a scale of 0 to 100, like the percent

	private double score;

	Mood(double score) {
		this.score = score;
	}

	public double getScore() {
		return score;
	}
}

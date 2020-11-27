package other;

import java.util.Random;

public class Compute {

	// All the methods in this class should be public & static!

	private Compute() {

	}
	
	// All numbers should be positive in order for this to work
	// Inclusive to exclusive
	public static int getRandValue(int b, int e, Random rand) {
		return rand.nextInt(e-b) + b;
	}

}

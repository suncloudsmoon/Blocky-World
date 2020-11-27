package biome;

import java.awt.Color;

public enum BiomeType {

	GRASSLAND(new Color(43.5F/255, 54.1F/255, 16.9F/255)), 
	AQUATIC(new Color(61, 166, 227)),
	FOREST(new Color(169, 204, 172)),
	DESERT(new Color(199, 131, 42)),
	TUNDRA(new Color(90.2F/255, 92.9F/255, 90.2F/255));

	private Color c;

	BiomeType(Color c) {
		this.c = c;
	}

	public Color getC() {
		return c;
	}

}

package block.chunk;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class ChunkMap<A, B, C> implements Serializable {

	private static final long serialVersionUID = -5359730038253792585L;

	private LinkedHashMap<A, B> chunks;
	private LinkedHashMap<A, C> points;

	public ChunkMap() {
		chunks = new LinkedHashMap<>();
		points = new LinkedHashMap<>();
	}

	public B getBiome(A obj) {
		return chunks.get(obj);
	}

	public C getLocations(A obj) {
		return points.get(obj);
	}

	// A is the key
	public Object[] put(A chunk, B biome, C locations) {
		return new Object[] { chunks.put(chunk, biome), points.put(chunk, locations) };
	}

	public Object[] remove(A obj) {
		return new Object[] { chunks.remove(obj), points.remove(obj) };
	}

	public boolean containsKey(A obj) {
		return chunks.containsKey(obj) && points.containsKey(obj);
	}

	public B replaceBiome(A one, B sec) {
		return chunks.replace(one, sec);
	}

	public C replaceCoordinates(A one, C last) {
		return points.replace(one, last);
	}

	public void clear() {
		chunks.clear();
		points.clear();
	}

	public int size() {
		if (chunks.size() == points.size()) {
			return chunks.size();
		} else {
			throw new IllegalAccessError("There is a mismatch in size between ChunkMap A and ChunkMap B!");
		}
	}

	@Override
	public String toString() {
		return "ChunkMap [chunks=" + chunks + ", points=" + points + "]";
	}

}
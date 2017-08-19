/**
 * A helper for storing paths
 * which helps for the path finding sections
 * 
 * This is similar to a String in that it is 
 * immutable but new instances can be made with the
 * extendPath method.
 * 
 * Its main use is as to hold state and accumulate the path
 * when running getAllPathsFrom
 * @author drew
 *
 */
public class path {
	public final String name;
	public final int distance;
	// the bounds of the path (useful when constructing)
	public final int min;
	public final int max;
	public final int maxDist;
	public final int minDist;
	
	/**
	 * a path bound by a maximum distance
	 * @param start
	 * @param maxDist the distance that a path must be less than
	 * @return
	 */
	public static path pathDist(char start, int maxDist) {
		return new path(start, 1, Integer.MAX_VALUE, 0, maxDist);
	}
	
	/**
	 * a path bound by a minimum and maximum amount of stations
	 * @param start initial node
	 * @param min stations must be greater than or equal to
	 * @param max stations must be less than
	 * @return
	 */
	public static path pathBound(char start, int min, int max) {
		return new path(start, min, max, 0, Integer.MAX_VALUE);
	}
	private path(char start, int min, int max, int mind, int maxd) {
		name = ""+start;
		distance = 0;
		this.min = min;
		this.max = max;
		this.minDist = mind;
		this.maxDist = maxd;
	}
	
	private path(edge e, path p) {
		this.name = p.name.concat(""+e.getDestination());
		this.distance = p.distance + e.getDistance();
		this.min = p.min;
		this.max = p.max;
		this.minDist = p.minDist;
		this.maxDist = p.maxDist;
	}
	
	public path extendPath(edge e) {
		return new path(e, this);
	}

}

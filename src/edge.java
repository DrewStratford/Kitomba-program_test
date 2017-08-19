/**
 * simple class representing an edge between stations.
 * Essentially this is just a tuple containing the edges distance and destination.
 * @author drew
 *
 */
public class edge {
	final private int distance;
	final private char destination;
	
	public edge(int distance, char destination){
		this.distance = distance;
		this.destination = destination;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public char getDestination() {
		return destination;
	}
}
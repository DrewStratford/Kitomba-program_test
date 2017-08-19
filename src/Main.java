import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	final private Map<Character, station> stations = new HashMap<Character, station>();
	
	/**
	 * Adds an edge to the graph
	 * @param from
	 * @param to
	 * @param distance
	 */
	private void addRoute(char from, char to, int distance) {
		//need to create a station if from doesn't exist yet
		if(!stations.containsKey(from)) {
			stations.put(from, new station(from));
		}
		station s = stations.get(from);
		s.addEdge(distance, to);
	}
	
	/**
	 *  The distance of the path described by cs
	 *  as cs may be an invalid path we return an optional
	 * @param cs the path to take
	 * @return the distance that this path takes
	 */
	public Optional<Integer> getDistance(char... cs) {
		int distance = 0;
		// the station we look for paths from
		station s = null;
		
		//iterates through path looking for path from current station to next station
		for(char dest : cs) {
			//initiates the stations on the first iteration
			if(s == null) {
				s = stations.get(dest);
				if(s== null) return Optional.empty();
				continue;
			}
			
			Optional<edge> edge = s.getEdge((edge e) -> (e.getDestination() == dest));
			//returns nothing if edge not present
			if(!edge.isPresent()) return Optional.empty();
			
			distance += edge.get().getDistance();
			//updates the current station
			s = stations.get(dest);
			if(s== null) return Optional.empty();
		}
		return Optional.of(distance);	
	}
	

	public void printGetDistance(char... cs) {
		System.out.print("path ");
		for(char c : cs) {
			System.out.print(c);
		}
		
		Optional<Integer> i = this.getDistance(cs);
		if(i.isPresent()) {
			System.out.println(" "+ i.get());
		} else {
			System.out.println("NO SUCH ROUTE");
		}
	}

	
	public Stream<path> getAllPaths(char from, char to, int iter, path acc) {
		station st = stations.get(from);
		if(st == null)
			return Stream.empty();
		
		// pased max so return 
		if(iter > acc.max || acc.distance >= acc.maxDist)
			return Stream.empty();
		
		// the recursive call, uses flatmap to flatten results into one stream
		// getEdgesBy (const true) just streams all the stations edges
		Stream<path> out = st.getEdgesBy((edge e) -> true)
				.flatMap((edge e) -> 
						getAllPaths(e.getDestination(), to, iter + 1, acc.extendPath(e)));	
		
		// we may need to add the current acc if it satisfies conditions
		if(from == to && iter >= acc.min)
			out = Stream.concat(Stream.of(acc), out);
		
		return out;
	}
	
	public Optional<Integer> findMinimumDistancePath(char from, char to) {
		// Since this is the minumum path the max amount of stops is equal to the amount of stations
		// as their are no loops or repeat visits etc.
		int max = this.stations.size();
		Optional<Integer> b = this.getAllPaths(from, to, 0, path.pathBound(from, 1,max))
				.sorted((path p1, path p2) -> Integer.compare(p1.distance, p2.distance))
				.findFirst().map(p -> p.distance);	
		return b;
	}
	
	/**
	 * 
	 * @param from Start station
	 * @param to end station
	 * @param min the minimum amount of stations it must visit
	 * @param max the max amount of statement to visit
	 * @return
	 */
	public int countPathsInBounds(char from, char to, int min, int max) {
		return this.getAllPaths(from, to, 0, path.pathBound(from, min, max))
		.collect(Collectors.toList()).size();
	}
	
	public int countPathsUnderDistance(char from, char to, int max) {
		return this.getAllPaths(from, to, 0, path.pathDist(from,max))
				.collect(Collectors.toList()).size();
	}
	
	/*
	 * parses strings passed as args and constructs a graph of edges.
	 * This may throw an illegal argument exception if the arguments are in the wrong
	 * format
	 */
	public Main(String[] args) throws IllegalArgumentException{
		try {
			for(String s : args) {
				char from = s.charAt(0);
				char to   = s.charAt(1);
				int distance = Integer.parseInt(s.substring(2, s.length()));
			
				this.addRoute(from, to, distance);
			}
		} catch(IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("input is not in the correct format");
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("input is not in the correct format");
		}
		
	}

}

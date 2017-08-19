import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class representing a station
 * stores all nodes reachable by this node as well as
 * their distance
 * @author drew
 *
 */
public class station {
	final private char name;
	final private Set<edge> edges = new HashSet<edge>();
	
	public station(char name) {
		this.name = name;
	}
	
	public void addEdge(int dist, char dest) {
		edges.add(new edge(dist,dest));
	}
	
	
	/**
	 * 
	 * @param pred
	 * @return all edges that match the predicate
	 * 
	 */
	public Stream<edge> getEdgesBy(Predicate<? super edge> pred){
		return edges.stream().filter(pred);
	}
	
	
	/**
	 * 
	 * @param pred
	 * @return the first edge that matches the predicate as an optional, as the edge
	 * may not exist
	 * 
	 */
	public Optional<edge> getEdge(Predicate<? super edge> pred){
		return getEdgesBy(pred).findFirst();
	}
}
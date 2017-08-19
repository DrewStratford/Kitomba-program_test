import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class testing{
	
	String[] basic = {"AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"};
	
	/**
	 * checks that a stream matches its intended result
	 * 
	 * @param quality the quality by which to test the the stream eg. name or distance
	 * @param result the stream
	 * @param as list of expected results (in no necessary order)
	 * @return
	 */
	static <A> boolean testAllPaths(Function<path, A> quality, Stream<path> result, A... as) {
		Set<A> tSet = new HashSet<A>();
		Collections.addAll(tSet,as);
		
		Set<A> rSet = new HashSet<A>();
		result.forEach((path p) -> rSet.add(quality.apply(p)));
		
		return rSet.equals(tSet);
	}

	/*
	 * These are the tests laid out in the document
	 */
	@Test
	public void test1() {
		Main context = new Main(basic);
		Optional<Integer> a = Optional.of(9);
		assertEquals(context.getDistance('A','B','C'), a);
	}
	
	@Test
	public void test2() {
		Main context = new Main(basic);
		Optional<Integer> a = Optional.of(5);
		assertEquals(context.getDistance('A','D'), a);
	}
	@Test
	public void test3() {
		Main context = new Main(basic);
		Optional<Integer> a = Optional.of(13);
		assertEquals(context.getDistance('A','D', 'C'), a);
	}
	@Test
	public void test4() {
		Main context = new Main(basic);
		Optional<Integer> a = Optional.of(22);
		assertEquals(context.getDistance('A','E', 'B', 'C', 'D'), a);
	}
	@Test
	public void test5() {
		Main context = new Main(basic);
		Optional<Integer> a = Optional.empty();
		assertEquals(context.getDistance('A','E', 'D'), a);
	}
	
	@Test
	public void test6() {
		Main context = new Main(basic);
		int i = context.countPathsInBounds('C', 'C',1,3);
		assertEquals(i, 2);
	}
	
	@Test
	public void test7() {
		Main context = new Main(basic);
		int i = context.countPathsInBounds('A', 'C',4,4);
		assertEquals(i, 3);
	}
	@Test
	public void test8() {
		Main context = new Main(basic);

		Optional<Integer> a = Optional.of(9);
		Optional<Integer> b = context.findMinimumDistancePath('A', 'C');
		assertEquals(b, a);
	}
	
	@Test
	public void test9() {
		Main context = new Main(basic);

		Optional<Integer> a = Optional.of(9);
		Optional<Integer> b = context.findMinimumDistancePath('B', 'B');
		assertEquals(b, a);
	}
	
	@Test
	public void test10() {
		Main context = new Main(basic);
		int i = context.countPathsUnderDistance('C', 'C', 30);
		assertEquals(i, 7);
	}
	
	/*
	 * these tests ensure that the findAllPaths method finds the right paths
	 * as the above tests only check that it finds the right amount of paths.
	 */
	
	@Test
	public void test6Path() {
		Main context = new Main(basic);
		Stream<path> res =context.getAllPaths('C', 'C', 0, path.pathBound('C',1,3));
		assertTrue(testAllPaths(x -> x.name, res, "CDC", "CEBC"));
	}
	
	@Test
	public void test7Path() {
		Main context = new Main(basic);
		Stream<path> res =context.getAllPaths('A', 'C', 0, path.pathBound('A', 4,4));
		assertTrue(testAllPaths(x -> x.name, res, "ABCDC", "ADCDC", "ADEBC"));
	}
	
	@Test
	public void test10Path() {
		Main context = new Main(basic);
		Stream<path> res =context.getAllPaths('C', 'C', 0, path.pathDist('C',30));
		assertTrue(testAllPaths(x -> x.name, res,
				"CDC", "CEBC", "CEBCDC", "CDCEBC", "CDEBC", "CEBCEBC", "CEBCEBCEBC"));
	}
	
	/*
	 * Test some non existent nodes in paths.
	 * These should not throw errors and return Optional.empty().
	 */
	@Test
	public void nonExistentode1() {
		Main context = new Main(basic);
		Optional<Integer> a = Optional.empty();
		assertEquals(context.getDistance('!','B','C'), a);
	}
	@Test
	public void nonExistentNode2() {
		Main context = new Main(basic);
		Optional<Integer> a = Optional.empty();
		assertEquals(context.getDistance('A','!','C'), a);
	}
	
}

package Data;

import static org.junit.Assert.*;
import org.junit.Test;

public class TupleTest {
	@Test
	public void testGetters() {
		Tuple<String> testTuple = new Tuple("Facebook", "KeepUp");
		assertTrue(testTuple.getLabel().equals("Facebook"));
		assertTrue(testTuple.getPassword().equals("KeepUp"));
	}
	
	@Test
	public void testSetters() {
		Tuple<String> testTuple = new Tuple("Facebook", "KeepUp");
		testTuple.setLabel("Twitter");
		testTuple.setPassword("password");
		assertTrue(testTuple.getLabel().equals("Twitter"));
		assertTrue(testTuple.getPassword().equals("password"));
	}
	
	@Test
	public void testToString() {
		Tuple<String> testTuple = new Tuple("Facebook", "KeepUp");
		assertTrue(testTuple.toString().equals("KeepUp, Facebook"));
	}
}

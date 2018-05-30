import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class SongListTest {

	@Test
	public void testAddMap() {
		addMap();
	}
	
	@Test
	public void testGetValue() {
		SongList test = new SongList();
		Map<String ,String> songMapTest = test.songMap;
		songMapTest.put("Song", "Path");
		assertTrue(songMapTest.get("Song").equals("Path"));
	}


	@Test
	public void testClearAll() {
		SongList test = new SongList();
		Map<String ,String> songMapTest = test.songMap;
		songMapTest.put("Song", "Path");
		songMapTest.clear();
		assertTrue(songMapTest.isEmpty());
	}
	
	public void addMap() {
		SongList test = new SongList();
		Map<String ,String> songMapTest = test.songMap;
		songMapTest.put("Song", "Path");
		songMapTest.put("Song1", "Path1");
		assertTrue(songMapTest.get("Song").equals("Path"));
		assertTrue(songMapTest.get("Song1").equals("Path1"));
		
		
	}
	
	

}

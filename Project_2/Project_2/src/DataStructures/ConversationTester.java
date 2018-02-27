package DataStructures;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConversationTester {

	@Test
	public void testGameListContainer() {
		Conversation conversation = new Conversation("host", "name");
		assertTrue(conversation.getIP().equals("host"));
		assertTrue(conversation.getName().equals("name"));
		
		conversation.setIP("host2");
		conversation.setName("name2");
		assertTrue(conversation.getIP().equals("host2"));
		assertTrue(conversation.getName().equals("name2"));
	}
}
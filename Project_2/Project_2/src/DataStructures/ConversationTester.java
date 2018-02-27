package DataStructures;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConversationTester {

	@Test
	public void testGameListContainer() {
		Conversation conversation = new Conversation("host", "port", "name");
		assertTrue(conversation.getHost().equals("host"));
		assertTrue(conversation.getPort().equals("port"));
		assertTrue(conversation.getName().equals("name"));
		
		conversation.setHost("host2");
		conversation.setName("name2");
		conversation.setPort("port2");
		assertTrue(conversation.getHost().equals("host2"));
		assertTrue(conversation.getPort().equals("port2"));
		assertTrue(conversation.getName().equals("name2"));
	}
}
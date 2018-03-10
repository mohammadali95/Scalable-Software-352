package Network;

import java.net.*;
import java.util.ArrayList;

import DataStructures.Conversation;
import DataStructures.ConversationContainer;
import Threads.WaitingForConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.*;

public class Server {

	private ServerSocket server;

	public Server(ConversationContainer conversationContainer, ListView<String> messageList, Pane conversationViewer){
		
		try {
			server = new ServerSocket(22223);
			new WaitingForConnection(server, conversationContainer, conversationViewer).start();
		}catch(IOException i) {
			System.out.println(i);
		}
	}
}
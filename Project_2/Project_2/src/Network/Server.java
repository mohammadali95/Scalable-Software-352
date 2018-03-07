package Network;

import java.net.*;
import java.util.ArrayList;

import DataStructures.Conversation;
import Threads.WaitingForConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.*;

public class Server {

	private ServerSocket server;

	public Server(ListView<String> messageListView, ListView<String> conversationListView, ArrayList<Conversation> conversationArrayList, ObservableList<String> conversationObservableList){
		try {
			server = new ServerSocket(22223);
			new WaitingForConnection(server, messageListView, conversationListView, conversationArrayList, conversationObservableList).start();
		}catch(IOException i) {
			System.out.println(i);
		}
	}

	public String recieveMessage() {
		return "";
	}
}

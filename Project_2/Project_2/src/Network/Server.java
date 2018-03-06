package Network;

import java.net.*;

import Threads.WaitingForConnection;
import javafx.scene.control.ListView;

import java.io.*;

public class Server {

	private ServerSocket server;
	private Socket socket;
	private BufferedReader input;
	private Reader Conversation;
	private int port;

	public Server(int port, ListView<String> listview){
		try {
			server = new ServerSocket(port);
			new WaitingForConnection(server, listview).start();
		}catch(IOException i) {
			System.out.println(i);
		}
	}

	public String recieveMessage() {
		return "";
	}
}

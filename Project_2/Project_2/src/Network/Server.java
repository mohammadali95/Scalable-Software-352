package Network;

import java.net.*;

import Threads.WaitingForConnection;
import javafx.scene.control.ListView;

import java.io.*;

public class Server {

	private ServerSocket server;

	public Server(int port, ListView<String> listview){
		try {
			server = new ServerSocket(port);
			System.out.println("inside of server");
			new WaitingForConnection(server, listview).start();
		}catch(IOException i) {
			System.out.println(i);
		}
	}

	public String recieveMessage() {
		return "";
	}
}

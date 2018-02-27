package Network;

import java.net.*;
import java.io.*;

public class Server {
	
	private ServerSocket server;
	private Socket socket;
	private BufferedReader input;
	private Reader Conversation = new FileReader("");
	
	public Server(int port) throws IOException {
		try {
			server = new ServerSocket(port);
			WaitingForConnection waiting = new WaitingForConnection(server);
			waiting.run();
			socket = waiting.getConnectingSocket();
			input = new BufferedReader(Conversation);
		} catch(IOException i) {
			System.out.println(i);
		}
	}
	
	public String recieveMessage() {
		return "";
	}
}

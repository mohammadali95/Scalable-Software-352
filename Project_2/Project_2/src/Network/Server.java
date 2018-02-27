package Network;

import java.net.*;

import Threads.WaitingForConnection;

import java.io.*;

public class Server {
	
	private ServerSocket server;
	private Socket socket;
	private BufferedReader input;
	private Reader Conversation;
	private int port = 7654;
	
	public Server(int port){
		try {
			server = new ServerSocket(this.port);
			Reader Conversation = new FileReader("");
			WaitingForConnection waiting = new WaitingForConnection(server);
			waiting.run();
			input = new BufferedReader(Conversation);
		}catch(IOException i) {
			System.out.println(i);
		} 
	}
	
	public Reader getConversationFile() {
		return Conversation;
	}
	
	public String recieveMessage() {
		return "";
	}
}

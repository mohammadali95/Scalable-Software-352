package Network;

import java.net.*;

import Threads.WaitingForConnection;

import java.io.*;

public class Server {
	
	private ServerSocket server;
	private Socket socket;
	private BufferedReader input;
	private Reader Conversation;
	private int port;
	
	public Server(int port){
		try {
			server = new ServerSocket(port);
			new WaitingForConnection(server).start();
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

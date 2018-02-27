package Network;

import java.net.*;
import java.io.*;

public class Client {
	
	private Socket socket;
	private BufferedReader input;
	private BufferedWriter output;
	private Reader Conversation;
	private Writer WriteToConversation;
	
	public Client(String host, int port){
		try {
			socket = new Socket(host, port);
			input = new BufferedReader(Conversation);
			output = new BufferedWriter(WriteToConversation);
		} catch (UnknownHostException h) {
			System.out.println(h);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	

	public void sendMessage(BufferedWriter s) {
		this.output = s;
	}
}

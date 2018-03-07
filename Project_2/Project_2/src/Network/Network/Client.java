package Network;

import java.net.*;
import java.io.*;

public class Client {

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter write;

	public Client(String host, int port) {
		try {
			socket = new Socket(host, port);
			//reader = new BufferedReader(new InputStreamReader(input));
			write = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException h) {
			System.out.println(h);
		} catch (IOException e) {
			System.out.println(e);
		}
	}



	public void sendMessage(String s){
		
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	@Override
	public String toString() {
		return socket.getInetAddress().toString() + socket.getPort();
	}
}
package Network;

import java.net.*;
import java.io.*;

public class Client {

	private Socket socket;

	public Client(String host, int port) {
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException h) {
			System.out.println(h);
		} catch (IOException e) {
			System.out.println(e);
		}
	}



	public void sendMessage(String s) throws IOException {
		// When this is called the socket it is referencing does not exit
		// resulting in a NullPointerException :(
		byte[] bytes = s.getBytes();
		OutputStream os = socket.getOutputStream();
		os.write(bytes);
		os.flush();
		os.close();
	}
}

package Network;

import java.net.*;
import java.io.*;

public class Client {

	private Socket socket;

	public Client(String host, int port, String message) throws IOException {
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException h) {
			System.out.println(h);
		} catch (IOException e) {
			System.out.println(e);
		}
		byte[] bytes = message.getBytes();
		OutputStream os = socket.getOutputStream();
		os.write(bytes);
		os.flush();
		os.close();
		socket.close();
	}
}

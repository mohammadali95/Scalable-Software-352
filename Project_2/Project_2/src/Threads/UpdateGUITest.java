package Threads;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import javafx.scene.control.ListView;

public class UpdateGUITest {

	@Test
	public void test() throws IOException {		
		Socket socket = null;
		try {
			socket = new Socket("10.253.197.223", 22223);
		} catch (UnknownHostException h) {
			System.out.println(h);
		} catch (IOException e) {
			System.out.println(e);
		}
		String message = "hello";
		byte[] bytes = message.getBytes();
		OutputStream os = socket.getOutputStream();
		os.write(bytes);
		os.flush();
		os.close();
		
		ListView<String> listview = null;
		@SuppressWarnings("unused")
		UpdateGUI update = new UpdateGUI(socket, listview);
		socket.close();
	}

}

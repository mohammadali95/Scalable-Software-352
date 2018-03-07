package Threads;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import DataStructures.MessageReceiver;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class UpdateGUI {

	Socket socket;

	public UpdateGUI (Socket socket, ListView<String> listview) throws IOException {
		BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String messageStr = is.readLine();
		MessageReceiver receiver = new MessageReceiver(messageStr);
		ObservableList<String> messageList = receiver.toList();
		System.out.println(messageList.get(0));
		//listview.setItems(messageList);
	}

}

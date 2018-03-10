package Threads;

import java.net.*;

import DataStructures.ConversationContainer;
import Network.*;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.*;

public class WaitingForConnection extends Thread{
	ServerSocket s;
	Socket connectingSocket;
	ConversationContainer conversationContainer;
	Pane conversationViewer;

	public WaitingForConnection(ServerSocket s, ConversationContainer conversationContainer, Pane conversationViewer) {
		this.s = s;
		this.conversationContainer = conversationContainer;
		this.conversationViewer = conversationViewer;
	}

	@Override
	public void start() {
		run();
	}

	@Override
	public void run() {
		new Thread(() -> {try {
			connectingSocket = s.accept();
			new UpdateGUI(connectingSocket, conversationContainer, conversationViewer);
			// run update gui here??
			new WaitingForConnection(s, conversationContainer, conversationViewer).start();
		} catch(IOException e) {
			e.printStackTrace();
		}}).start();
	}

	public Socket getConnectingSocket() {
		return connectingSocket;
	}
}
// need to close ssockets at some point
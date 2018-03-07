package Threads;

import java.net.*;

import Network.*;
import javafx.scene.control.ListView;

import java.io.*;

public class WaitingForConnection extends Thread{
	ServerSocket s;
	SocketListener listener;
	Socket connectingSocket;
	ListView<String> listview;

	public WaitingForConnection(ServerSocket s, ListView<String> listview) {
		this.s = s;
		this.listview = listview;
	}

	@Override
	public void start() {
		run();
	}

	@Override
	public void run() {
		new Thread(() -> {try {
			System.out.println("inside of thread");
			connectingSocket = s.accept();
			new UpdateGUI(connectingSocket, listview);
			// run update gui here??
			new WaitingForConnection(s, listview).start();
		} catch(IOException e) {
			e.printStackTrace();
		}}).start();
	}

	public Socket getConnectingSocket() {
		return connectingSocket;
	}
}
// need to close ssockets at some point

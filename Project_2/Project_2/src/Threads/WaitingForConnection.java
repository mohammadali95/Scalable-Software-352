package Threads;

import java.net.*;

import Network.*;
import javafx.scene.control.ListView;

import java.io.*;

public class WaitingForConnection extends Thread{
	ServerSocket s;
	SocketListener listener;
	Socket connectingSocket;

	public WaitingForConnection(ServerSocket s, ListView<String> listview) {
		this.s = s;
	}

	@Override
	public void run() {
		new Thread(() -> {try {
			connectingSocket = s.accept();
			// run update gui here??
			System.out.println(connectingSocket.getInetAddress());
		} catch(IOException e) {
			e.printStackTrace();
		}}).start();
	}

	public Socket getConnectingSocket() {
		return connectingSocket;
	}
}
// need to close ssockets at some point

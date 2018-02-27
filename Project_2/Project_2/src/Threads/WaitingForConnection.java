package Threads;

import java.net.*;

import Network.*;

import java.io.*;

public class WaitingForConnection extends Thread{
	ServerSocket s;
	SocketListener listener;
	Socket connectingSocket;
	public WaitingForConnection(ServerSocket s) {
		this.s = s;
	}
	
	@Override
	public void run() {
		try {
			connectingSocket = s.accept();	
			new Thread(() -> {listener.receiveSocket(connectingSocket);}).start();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public Socket getConnectingSocket() {
		return connectingSocket;
	}
}
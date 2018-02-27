package Network;

import java.net.*;
import java.io.*;

public class WaitingForConnection extends Thread{
	ServerSocket s;
	Socket connectingSocket;
	public WaitingForConnection(ServerSocket s) {
		this.s = s;
	}
	
	@Override
	public void run() {
		try {
			connectingSocket = s.accept();	
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public Socket getConnectingSocket() {
		return connectingSocket;
	}
}
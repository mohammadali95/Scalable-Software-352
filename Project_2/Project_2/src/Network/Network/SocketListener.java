package Network;

import java.net.*;

public interface SocketListener {
	public void receiveSocket(Socket socket);
}
// To be able to tell another object here is a socket to listen to
// Tell acceptor here is an object to talk to when you need to
// bounce off of accept thread
// class to implement interface


// print writer to help with streams
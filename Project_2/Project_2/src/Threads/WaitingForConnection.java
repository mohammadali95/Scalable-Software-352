package Threads;

import java.net.*;
import java.util.ArrayList;

import DataStructures.Conversation;
import GUI.UpdateConversations;
import Network.*;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.*;

public class WaitingForConnection extends Thread{

	private ServerSocket s;
	private Socket connectingSocket;
	private ListView<String> messageListView;
	private ListView<String> conversationListView;
	private ArrayList<Conversation> conversationArrayList;
	private ObservableList<String> conversationObservableList;


	public WaitingForConnection(ServerSocket s, ListView<String> messageListView, ListView<String> conversationListView, ArrayList<Conversation> conversationArrayList, ObservableList<String> conversationObservableList) {
		this.s = s;
		this.messageListView = messageListView;
		this.conversationListView = conversationListView;
		this.conversationArrayList = conversationArrayList;
		this.conversationObservableList = conversationObservableList;
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
			new UpdateConversations(connectingSocket, conversationArrayList, conversationObservableList, conversationListView);
			new UpdateGUI(connectingSocket, conversationListView);
			new WaitingForConnection(s, messageListView, conversationListView, conversationArrayList, conversationObservableList).start();
		} catch(IOException e) {
			e.printStackTrace();
		}}).start();
	}

	public Socket getConnectingSocket() {
		return connectingSocket;
	}
}

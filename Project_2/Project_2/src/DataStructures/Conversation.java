package DataStructures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Conversation {

	private String ip;
	private String name;
	ObservableList<String> history = FXCollections.observableArrayList();

	public Conversation(String ip, String name) {
		this.ip = ip;
		this.name = name;
	}

	public String getIP() {return ip;}

	public String getName() {return name;}

	public ObservableList<String> getMessageHistory() {return history;}

	public void setIP(String ip) {this.ip = ip;}

	public void setName(String name) {this.name = name;}

	public void addNewMessage(String newMessage) {history.add(newMessage);}

}

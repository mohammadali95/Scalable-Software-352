package DataStructures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class Conversation {

	private ObservableList<String> list = FXCollections.observableArrayList();
	private ListView<String> listView = new ListView<String>();
	private String ip;
	private String name;

	public Conversation(String ip, String name) {
		this.ip = ip;
		this.name = name;
	}

	public String getIP() {return ip;}
	
	public String getName() {return name;}
	
	public ListView<String> getListView() {return listView;}
	
	public ObservableList<String> getList() {return list;}
	
	public void setIP(String ip) {this.ip = ip;}
	
	public void setName(String name) {this.name = name;}
	
	public void addMessage(String newMessage) {
			list.add(newMessage);
			listView.setItems(list);
		}
	
	public void addNewMessage(ObservableList<String> list) {
		addMessage(list.get(list.size() - 1));
	}

}
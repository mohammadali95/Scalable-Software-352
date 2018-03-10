package DataStructures;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ConversationContainer {

	private ArrayList<Conversation> arrayList = new ArrayList<Conversation>();
	private ObservableList<String> observableList = FXCollections.observableArrayList();
	private ListView<String> listView = new ListView<String>();

	public ConversationContainer(ArrayList<Conversation> arrayList, ObservableList<String> observableList,
			ListView<String> listView) {
		this.arrayList = arrayList;
		this.observableList = observableList;
		this.listView = listView;
	}

	public void Add(Conversation conversation) {
		if (containsIP(conversation.getName())) {

		} else {
			arrayList.add(conversation);
			observableList.add(conversation.getName());
			listView.setItems(observableList);
		}
	}

	public Conversation getChosenConversation() {
		int index = listView.getSelectionModel().getSelectedIndex();
		return arrayList.get(index);
	}

	public Conversation getIpsConversation(String ip, String name) {
		Conversation conversation = null;
		ip = ip.substring(1);
		System.out.println(ip);
		boolean found = false;
		for (int x = 0; x < arrayList.size(); x++) {
			System.out.println(arrayList.get(x).getIP());
			if (arrayList.get(x).getIP().contains(ip)) {
				conversation = arrayList.get(x);
				found = true;
			}
		}
		if (!found) {
			conversation = new Conversation(ip, name);
		}
		return conversation;
	}

	public Boolean containsIP(String name) {
		boolean result = false;
		for (int x = 0; x < observableList.size(); x++) {
			if (observableList.get(x).equals(name)) {
				result = true;
			}
		}
		return result;
	}

	public String getLastIP() {
		return arrayList.get(arrayList.size() - 1).getIP();
	}

	public String getLastName() {
		return arrayList.get(arrayList.size() - 1).getName();
	}
	
	public ListView<String> getListView() {
		return listView;
	};
}
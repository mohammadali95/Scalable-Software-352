package DataStructures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MessageReceiver {

	String messages;
	
	public MessageReceiver(String messages) {
		this.messages = messages;
	}
	
	public ObservableList<String> toList() {
		ObservableList<String> result = FXCollections.observableArrayList();
		String[] split = messages.split("///");
		for (int x = 0; x < split.length; x++) {
			result.add(split[x]);
		}
		return result;
	}

}

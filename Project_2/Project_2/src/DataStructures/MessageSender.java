package DataStructures;

import javafx.collections.ObservableList;

public class MessageSender {
	
	ObservableList<String> messages;

	public MessageSender(ObservableList<String> messages) {
		this.messages = messages;
	}
	
	@Override
	public String toString() {
		String result = "";
		for (int x = 0; x < messages.size(); x++) {
			if (x == 0) {
				result = messages.get(0);
			} else {
				result = result + "///" + messages.get(x);
			}
		}
		return result;
	}

}

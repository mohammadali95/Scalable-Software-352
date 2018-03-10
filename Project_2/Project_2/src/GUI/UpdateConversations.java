package GUI;

import java.net.Socket;
import java.util.ArrayList;

import DataStructures.Conversation;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateConversations {
	
	public Socket socket;
	public ArrayList<Conversation> arrayList;
	public ObservableList<String> observableList;
	public ListView<String> listView;
	public String ip;
	
	public UpdateConversations(Socket socket, ArrayList<Conversation> arrayList, ObservableList<String> observableList, ListView<String> listView) {
		this.socket = socket;
		this.arrayList = arrayList;
		this.observableList = observableList;
		this.listView = listView;
		
		this.ip = socket.getInetAddress().toString();
		System.out.println(ip);
		
		namePopup();
	}

	private void namePopup() {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		VBox layout = new VBox();
		
		TextField nameField = new TextField();
		nameField.setPromptText("name of user");
		
		Button saveButton = new Button();
		saveButton.setText("Save");
		saveButton.setOnAction((event) -> {
			String name = nameField.getText();
			
			Conversation conversation = new Conversation(ip, name);
			arrayList.add(conversation);
			
			observableList.add(name);
			listView.setItems(observableList);
		});
		
		layout.getChildren().addAll(nameField, saveButton);
		
	}

}

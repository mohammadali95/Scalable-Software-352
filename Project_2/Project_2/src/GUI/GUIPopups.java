package GUI;

import java.util.ArrayList;

import DataStructures.Conversation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIPopups {
	
	private ObservableList<String> conversationObservableList = FXCollections.observableArrayList();
	
	private ArrayList<Conversation> conversationArrayList = new ArrayList<Conversation>();
	
	private ListView<String> conversationList;
	
	public Pane conversationViewer;
	
	
	public GUIPopups(ObservableList<String> conversationObservableList, ArrayList<Conversation> conversationArrayList, ListView<String> conversationList, Pane conversationViewer) {
		this.conversationObservableList = conversationObservableList;
		this.conversationArrayList = conversationArrayList;
		this.conversationList = conversationList;
		this.conversationViewer = conversationViewer;
	}

	
	
	public void newConversation() {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		VBox layout = new VBox();

		HBox hostHbox = new HBox();
		
		Label hostLabel = new Label("Host: ");
		
		TextField hostTextField = new TextField();
		hostTextField.setPromptText("Enter the Host here.");
		hostTextField.setEditable(true);
		
		hostHbox.getChildren().addAll(hostLabel, hostTextField);
		
		HBox titleHbox = new HBox();
		
		Label titleLabel = new Label("Name: ");
		
		TextField nameTextField = new TextField();
		nameTextField.setPromptText("Enter the name here.");
		nameTextField.setEditable(true);
		
		titleHbox.getChildren().addAll(titleLabel, nameTextField);
		
		HBox buttonHbox = new HBox();
		Button sendButton = new Button();
		sendButton.setText("Send!");
		sendButton.setOnAction((event) -> {
			addToConversationList(hostTextField.getText(), "1231", nameTextField.getText());
			popup.close();
		});
		buttonHbox.getChildren().add(sendButton);
		
		layout.getChildren().addAll(hostHbox, titleHbox, buttonHbox);
		Scene popupscene = new Scene(layout, 250, 110);
		popup.setScene(popupscene);
		popup.show();
	}
	
	private void addToConversationList(String host, String port, String name) {
		Conversation newConvo = new Conversation(host, port, name);
		conversationArrayList.add(newConvo);
		conversationObservableList.add(newConvo.getName());
		conversationList.setItems(conversationObservableList);
	}
	
	public void toConversation() {
		int selectedIndex = conversationList.getSelectionModel().getSelectedIndex();
		Conversation selectedConversation = conversationArrayList.get(selectedIndex);
		
		VBox conversation = new VBox();
		
		Label introLabel = new Label(selectedConversation.getName());
		
		VBox messages = new VBox();
		
		Label example = new Label("hello");
		Label example1 = new Label("Hi");
		Label example2 = new Label("How are you?");
		
		messages.getChildren().addAll(example, example1, example2);
		
		HBox messageWriter = new HBox();
		
		TextField writeMessagesHere = new TextField();
		writeMessagesHere.setPromptText("Write a message here");
		writeMessagesHere.setEditable(true);
		Button addFile = new Button();
		addFile.setText("Add File");
		
		Button sendMessage = new Button();
		sendMessage.setText("Send!");
		
		messageWriter.getChildren().addAll(writeMessagesHere, addFile, sendMessage);
		
		conversation.getChildren().addAll(introLabel, messages, messageWriter);
		
		conversationViewer.getChildren().clear();
		
		conversationViewer.getChildren().add(conversation);
	}
	
	public ObservableList<String> getConversationObservableList() {return conversationObservableList;}
	
	public ArrayList<Conversation> getConversationArrayList() {return conversationArrayList;}
	
	public ListView<String> getConversationList() {return conversationList;}

	public Pane getConversationViewer() {return conversationViewer;}

}

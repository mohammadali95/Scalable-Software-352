package GUI;

import java.io.IOException;
import java.util.ArrayList;
import DataStructures.Conversation;
import DataStructures.MessageReceiver;
import DataStructures.MessageSender;
import Network.Client;
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
	private ListView<String> messageList;
	public Pane conversationViewer;
	public Client client;
	public String host;
	public int port = 22223;

	public GUIPopups(ObservableList<String> conversationObservableList, ArrayList<Conversation> conversationArrayList,
			ListView<String> conversationList, Pane conversationViewer, ListView<String> messageList) {
		this.conversationObservableList = conversationObservableList;
		this.conversationArrayList = conversationArrayList;
		this.conversationList = conversationList;
		this.conversationViewer = conversationViewer;
		this.messageList = messageList;
	}

	public void newConversation() {
		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		VBox layout = new VBox();
		HBox hostHbox = new HBox();
		Label hostLabel = new Label("Host: ");
		TextField ipTextField = new TextField();
		ipTextField.setPromptText("Enter the Host here.");
		ipTextField.setEditable(true);
		hostHbox.getChildren().addAll(hostLabel, ipTextField);
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
			addToConversationList(ipTextField.getText(), nameTextField.getText());
			sendConversationButton(ipTextField.getText());
			popup.close();
		});
		buttonHbox.getChildren().add(sendButton);
		layout.getChildren().addAll(hostHbox, titleHbox, buttonHbox);
		Scene popupscene = new Scene(layout, 250, 110);
		popup.setScene(popupscene);
		popup.show();
	}

	private void sendConversationButton(String string) {
		this.client = new Client(string, port);
		System.out.println(this.client);
	}

	private void sendMessageButton(String senderStr) {
		System.out.println(senderStr);
		System.out.println(this.client.toString());
		this.client.sendMessage(senderStr);
	}

	private void addToConversationList(String host, String name) {
		Conversation newConvo = new Conversation(host, name);
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
		messageList = updateMessageHistory(selectedConversation);
		messages.getChildren().clear();
		messages.getChildren().add(messageList);
		HBox messageWriter = new HBox();
		TextField writeMessagesHere = new TextField();
		writeMessagesHere.setPromptText("Write a message here");
		writeMessagesHere.setEditable(true);
		Button addFile = new Button();
		addFile.setText("Add File");
		Button sendMessage = new Button();
		sendMessage.setText("Send!");
		sendMessage.setOnAction((event) -> {
			selectedConversation.addNewMessage(writeMessagesHere.getText());
			writeMessagesHere.clear();
			ListView<String> addtoConversationHistory = updateMessageHistory(selectedConversation);
			messages.getChildren().clear();
			messages.getChildren().add(addtoConversationHistory);
			MessageSender sender = new MessageSender(selectedConversation.getMessageHistory());
			String senderStr = sender.toString();
			sendMessageButton(senderStr);
			// I think this is all the code I need now that the update GUI is inside of the
			// Server
		});
		messageWriter.getChildren().addAll(writeMessagesHere, addFile, sendMessage);
		conversation.getChildren().addAll(introLabel, messages, messageWriter);
		conversationViewer.getChildren().clear();
		conversationViewer.getChildren().add(conversation);
	}

	private ListView<String> updateMessageHistory(Conversation selectedConversation) {
		ObservableList<String> messageHistory = selectedConversation.getMessageHistory();
		ListView<String> listview = new ListView<String>();
		listview.setItems(messageHistory);
		return listview;
	}

	public ObservableList<String> getConversationObservableList() {
		return conversationObservableList;
	}

	public ArrayList<Conversation> getConversationArrayList() {
		return conversationArrayList;
	}

	public ListView<String> getConversationList() {
		return conversationList;
	}

	public Pane getConversationViewer() {
		return conversationViewer;
	}

}
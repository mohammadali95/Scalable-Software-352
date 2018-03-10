package Threads;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import DataStructures.Conversation;
import DataStructures.ConversationContainer;
import DataStructures.MessageReceiver;
import DataStructures.MessageSender;
import Files.AddImage;
import GUI.GUIPopups;
import Network.Client;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class UpdateGUI {

	Socket socket;
	ConversationContainer conversationContainer;
	Pane conversationViewer;

	public UpdateGUI (Socket socket, ConversationContainer conversationContainer, Pane conversationViewer) throws IOException {
		this.socket = socket;
		this.conversationContainer = conversationContainer;
		this.conversationViewer = conversationViewer;
		
		BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String messageStr = is.readLine();
		MessageReceiver receiver = new MessageReceiver(messageStr);
		ObservableList<String> messageList = receiver.toList();
		
		Conversation conversation = conversationContainer.getIpsConversation(socket.getInetAddress().toString(), "name");
		conversation.addNewMessage(messageList);
		
		Platform.runLater(() -> {
			GUIPopups popup = new GUIPopups(conversationContainer, conversationViewer, conversation.getListView());
			conversationContainer.Add(conversation);
			
			VBox conversationVbox = new VBox();
			Label introLabel = new Label(conversation.getName());
			VBox messages = new VBox();
			messages.getChildren().clear();
			messages.getChildren().add(conversation.getListView());
			HBox messageWriter = new HBox();
			TextField writeMessagesHere = new TextField();
			writeMessagesHere.setPromptText("Write a message here");
			writeMessagesHere.setEditable(true);
			Button addFile = new Button();
			addFile.setText("Add File");
			addFile.setOnAction((event) -> {
				
				new AddImage();
				
			});

			Button sendMessage = new Button();
			sendMessage.setText("Send!");
			sendMessage.setOnAction((event) -> {
				// This is where the magic happens

				conversation.addMessage(writeMessagesHere.getText());
				writeMessagesHere.clear();
				messages.getChildren().clear();
				messages.getChildren().add(conversation.getListView());

				MessageSender sender = new MessageSender(conversation.getList());
				String senderStr = sender.toString();

				try {
					Client client = new Client(conversation.getIP(), 22223, senderStr);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				// I think this is all the code I need now that the update GUI is inside of the Server
			});
			messageWriter.getChildren().addAll(writeMessagesHere, addFile, sendMessage);

			conversationVbox.getChildren().addAll(introLabel, messages, messageWriter);

			conversationViewer.getChildren().clear();

			conversationViewer.getChildren().add(conversationVbox);
		});
	}

}
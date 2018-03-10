package GUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import DataStructures.Conversation;
import DataStructures.ConversationContainer;
import DataStructures.MessageSender;
import Files.AddImage;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIPopups {
	
	private AddImage imageHolder = new AddImage();
	private ConversationContainer conversationContainer;

	private ListView<String> messageList = new ListView<String>();

	public Pane conversationViewer;

	public GUIPopups(ConversationContainer conversationContainer, Pane conversationViewer, ListView<String> messageList) {
		this.conversationContainer = conversationContainer;
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
			Conversation conversation = new Conversation(ipTextField.getText(), nameTextField.getText());
			conversationContainer.Add(conversation);
			popup.close();
		});

		buttonHbox.getChildren().add(sendButton);

		layout.getChildren().addAll(hostHbox, titleHbox, buttonHbox);
		Scene popupscene = new Scene(layout, 250, 110);
		popup.setScene(popupscene);
		popup.show();
	}

	private void sendFile(String ip, int port, byte[] message) {
		try {
			Client client = new Client(ip, port, message);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void sendMessageButton(String ip, int port, String message) {
		try {
			Client client = new Client(ip, port, message);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public File getDataFile1() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Select Image");
		return chooser.showOpenDialog(null);
		
		
	}
	
	
	private void uploadFile(Conversation selectedConversation, VBox messages) throws IOException  {
		File file = getDataFile1();
		String fil = (file.getCanonicalPath());
		Integer idx = fil.lastIndexOf('/');
		String filString = fil.substring(idx +1);
		System.out.println(filString);
		imageHolder.addMap(filString, fil);
		//Need to add filString to the Observable list here. 
		selectedConversation.addMessage(filString);

		messages.getChildren().clear();
		messages.getChildren().add(selectedConversation.getListView());
		
		MessageSender sender = new MessageSender(selectedConversation.getList());
		String senderStr = sender.toString();
		sendMessageButton(selectedConversation.getIP(), 22223, senderStr);
		byte[] bytearray = imageHolder.convertImage(filString);
		System.out.println(bytearray);
		sendFile(selectedConversation.getIP(), 22223, bytearray);

		}

	public void toConversation() {
		Conversation selectedConversation = conversationContainer.getChosenConversation();

		VBox conversation = new VBox();

		Label introLabel = new Label(selectedConversation.getName());

		VBox messages = new VBox();

		messages.getChildren().clear();
		messages.getChildren().add(messageList);

		HBox messageWriter = new HBox();

		TextField writeMessagesHere = new TextField();
		writeMessagesHere.setPromptText("Write a message here");
		writeMessagesHere.setEditable(true);
		// ADD Button for upload Image
		Button addFile = new Button();
		addFile.setText("Add File");
		
	
	
		
		
		addFile.setOnAction((event) -> {
			
			try {
				uploadFile(selectedConversation, messages);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});

		Button sendMessage = new Button();
		sendMessage.setText("Send!");
		sendMessage.setOnAction((event) -> {
			// This is where the magic happens

			selectedConversation.addMessage(writeMessagesHere.getText());
			writeMessagesHere.clear();
			messages.getChildren().clear();
			messages.getChildren().add(selectedConversation.getListView());

			MessageSender sender = new MessageSender(selectedConversation.getList());
			String senderStr = sender.toString();
			sendMessageButton(selectedConversation.getIP(), 22223, senderStr);
			
			// I think this is all the code I need now that the update GUI is inside of the Server
		});

		messageWriter.getChildren().addAll(writeMessagesHere, addFile, sendMessage);

		conversation.getChildren().addAll(introLabel, messages, messageWriter);

		conversationViewer.getChildren().clear();

		conversationViewer.getChildren().add(conversation);
	}
	
	
	public void recieveConversation(Conversation conversation) {
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
			conversationContainer.Add(conversation);

		
		buttonHbox.getChildren().add(sendButton);

		layout.getChildren().addAll(hostHbox, titleHbox, buttonHbox);
		Scene popupscene = new Scene(layout, 250, 110);
		popup.setScene(popupscene);
	}

	public Pane getConversationViewer() {return conversationViewer;}

}
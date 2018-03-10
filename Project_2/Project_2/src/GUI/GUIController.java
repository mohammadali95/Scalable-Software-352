package GUI;

import java.util.ArrayList;

import DataStructures.Conversation;
import DataStructures.ConversationContainer;
import Network.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class GUIController {

	private ArrayList<Conversation> conversationArrayList = new ArrayList<Conversation>();
	
	private ObservableList<String> conversationObservableList = FXCollections.observableArrayList();

	@FXML
	public ListView<String> conversationList = new ListView<String>();
	
	@FXML
	public ListView<String> messageList = new ListView<String>();
	
	@FXML
	public Pane conversationViewer;
	
	@FXML
	public void initialize() {
		ConversationContainer conversationContainer = new ConversationContainer(conversationArrayList, conversationObservableList, conversationList);
		Server server = new Server(conversationContainer, messageList, conversationViewer);
	}
	
	@FXML
	public void makeNewConversation() {
		ConversationContainer conversationContainer = new ConversationContainer(conversationArrayList, conversationObservableList, conversationList);
		GUIPopups popups = new GUIPopups(conversationContainer, conversationViewer, messageList);
		popups.newConversation();
	}

	@FXML
	private void gotoConversation() {
		ConversationContainer conversationContainer = new ConversationContainer(conversationArrayList, conversationObservableList, conversationList);
		GUIPopups popups = new GUIPopups(conversationContainer, conversationViewer, messageList);
		popups.toConversation();
	}
}
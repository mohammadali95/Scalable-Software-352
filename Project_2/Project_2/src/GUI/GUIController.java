package GUI;

import java.util.ArrayList;

import DataStructures.Conversation;
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

	ObservableList<String> conversationObservableList = FXCollections.observableArrayList();

	ArrayList<Conversation> conversationArrayList = new ArrayList<Conversation>();

	@FXML
	public ListView<String> messageListView = new ListView<String>();

	@FXML
	public ListView<String> conversationListView = new ListView<String>();

	@FXML
	public Button addConversation;

	@FXML
	public AnchorPane buttonPane;

	@FXML
	public AnchorPane listPane;


	@FXML
	public SplitPane splitpane;

	@FXML
	public Button gotoButton;

	@FXML
	public Pane conversationViewer;

	Server server = new Server(messageListView, conversationListView, conversationArrayList, conversationObservableList);

	@FXML
	public void makeNewConversation() {
		GUIPopups popups = new GUIPopups(conversationObservableList, conversationArrayList, conversationListView, conversationViewer, messageListView);
		popups.newConversation();
		conversationObservableList = popups.getConversationObservableList();
		conversationArrayList = popups.getConversationArrayList();
		conversationListView = popups.getConversationList();
	}

	@FXML
	private void gotoConversation() {
		GUIPopups popups = new GUIPopups(conversationObservableList, conversationArrayList, conversationListView, conversationViewer, messageListView);
		popups.toConversation();
		conversationArrayList = popups.getConversationArrayList();
		conversationListView = popups.getConversationList();
		conversationViewer = popups.getConversationViewer();

	}
}

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
	public ListView<String> messageList;

	Server server = new Server(22223, messageList);

	@FXML
	public Button addConversation;

	@FXML
	public AnchorPane buttonPane;

	@FXML
	public AnchorPane listPane;

	@FXML
	public ListView<String> conversationList;

	@FXML
	public SplitPane splitpane;

	@FXML
	public Button gotoButton;

	@FXML
	public Pane conversationViewer;

	@FXML
	public void makeNewConversation() {
		GUIPopups popups = new GUIPopups(conversationObservableList, conversationArrayList, conversationList, conversationViewer, messageList);
		popups.newConversation();
		conversationObservableList = popups.getConversationObservableList();
		conversationArrayList = popups.getConversationArrayList();
		conversationList = popups.getConversationList();
	}

	@FXML
	private void gotoConversation() {
		GUIPopups popups = new GUIPopups(conversationObservableList, conversationArrayList, conversationList, conversationViewer, messageList);
		popups.toConversation();
		conversationArrayList = popups.getConversationArrayList();
		conversationList = popups.getConversationList();
		conversationViewer = popups.getConversationViewer();

	}
}

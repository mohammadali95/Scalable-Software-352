import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.stage.FileChooser;

public class Controller {

@FXML
private Button uploadButton;

@FXML
private Button saveButton;

@FXML 
private Button playButton;

@FXML 
private Button puaseButton;


@FXML 
private Button cropButton;

@FXML 
private ListView<File> listSongs;
ObservableList<File> samples =  FXCollections.observableArrayList();

@FXML 
private ChoiceBox choiceButton;

@FXML
private void initialize() {
	choiceButton.setItems(FXCollections.observableArrayList(
		    "New Document", "Open ", "Save", "Save as"));

	
}



@FXML
private void uploadFile() {
	File file = getDataFile1();
	//if (checkType(file)) {
		samples.add(file);
	//}
	updateListView();
	
	
}
File getDataFile1() {
FileChooser chooser = new FileChooser();
chooser.setTitle("Select test data");
return chooser.showOpenDialog(null);
}



private void updateListView() {
	listSongs.setItems(samples);
	
}



private Boolean checkType(File file) {
	// TODO Auto-generated method stub
	return null;
}



private File getDataFile() {
	// TODO Auto-generated method stub
	return null;
}
}

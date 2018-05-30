import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sun.glass.ui.MenuBar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Controller {


	private SongList list = new SongList();
	private FXMLLoader fxmlLoader;

	

//private boolean click;
private String currSong = "Kar";

@FXML
MenuBar menu;

@FXML
MenuItem close;

@FXML
MenuItem about;

@FXML
private Button deleteButton;

@FXML
private Button clearButton;

@FXML
private Button stopButton;
	
@FXML
private Button uploadButton;

@FXML
private Button saveButton;

@FXML 
private Button playButton;

@FXML 
private Button pauseButton;

@FXML
private Button addButton;



@FXML
private ListView<String> playSongs;
ObservableList<String> pSongs =  FXCollections.observableArrayList();


@FXML 
private Button cropButton;

@FXML 
public ListView<String> listSongs;
ObservableList<String> samples =  FXCollections.observableArrayList();


@FXML
void close() {
		System.exit(0);
}

// Got help from Stack overflow
@FXML
void about() {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("MUSIC SAMPLER");
	alert.setHeaderText(
			"MAKE AND LISTEN TO THE BEST MUSIC YOU WANT!!!");
	alert.setContentText("Developed by Mohammad Ali" + "\n" + "Scalable Software");

	alert.showAndWait();

}

@FXML
private void uploadFile() throws IOException, LineUnavailableException, UnsupportedAudioFileException {

		File file = getDataFile();
		if (checkType(file)) {
		String fil = (file.getCanonicalPath());
		Integer idx = fil.lastIndexOf('/');
		String filString = fil.substring(idx +1);
		list.addMap(filString, fil);
		samples.add(filString);
		updateListView();
		}
	
		else {
			errShow("Please choose a Mp3 File");

		}
	
	
	
}
private File getDataFile() {
	FileChooser chooser = new FileChooser();
	chooser.setTitle("Select Song");
	return chooser.showOpenDialog(null);
	
	
}



private void updateListView() {
	listSongs.setItems(samples);
	
}


private Boolean checkType(File file) throws IOException {
	String fil = (file.getCanonicalPath());
	String [] split = fil.split("\\.");
	System.out.println(fil);
	
	System.out.println(split[1]);
	if (split[1].equals("mp3")) {
		return true;
	}
	else {
		return false;
	}
	}
// Should remove from my hashmap too which I don't do here. 
// I think I should have had two hashmaps. 
@FXML 
private void addToPlay() {
	if (listSongs.getSelectionModel().getSelectedItem()== null) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Whoa!");
		alert.setContentText("Gotta select a song to add!");
		alert.show();
	}
	else {
	String songToPlay = listSongs.getSelectionModel().getSelectedItem();
	final int songRm = listSongs.getSelectionModel().getSelectedIndex();
	pSongs.add(songToPlay);
	samples.remove(songRm);
	updateListView();
	playSongs.setItems(pSongs);
	}
}


	@FXML
	private void playSong() throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {
				if (playSongs.getSelectionModel().getSelectedItem() == null) {
					errShow("Please select a song to Play");
				}
				else {
				String songToPlay = playSongs.getSelectionModel().getSelectedItem();
				System.out.println("song:" + songToPlay);
				if (!songToPlay.equals(currSong)) {
					
					if (playButton.getText().equals("Play")) {
						currSong = songToPlay;
						//list.dispose();
						list.playSong(songToPlay);
						playButton.setText("Pause");
					}
				}
				else {
					if (playButton.getText().equals("Play")) {
						list.resume();
						playButton.setText("Pause");
					}
					else {
					playButton.setText("Play");
					list.pauseSound();
					}
				}
				Double length = list.getLength(songToPlay);
				}
}
	
	@FXML
	private void stop() {
		list.stop();
		playButton.setText("Play");
	}
	
	@FXML
	private void clearQueue() {
		list.clearAll(list.songMap);
		pSongs.clear();
		playSongs.setItems(pSongs);
		list.stop();
		playButton.setText("Play");
	}
	
	@FXML
	private void delete() {
		if ((listSongs.getSelectionModel().getSelectedItem() == null) && (playSongs.getSelectionModel().getSelectedItem() == null)  ) {
			errShow("Please select a song to Remove");
		}
		else if (listSongs.getSelectionModel().getSelectedItem() != null) {
		final String songToDelete = listSongs.getSelectionModel().getSelectedItem();
		final int indexToDelete = listSongs.getSelectionModel().getSelectedIndex();
		samples.remove(indexToDelete);
		updateListView();
		
		
	}
		else {
			
		final String songToDelete = playSongs.getSelectionModel().getSelectedItem();
		final int indexToDelete = playSongs.getSelectionModel().getSelectedIndex();
		if (songToDelete.equals(currSong)) {
		list.stop();
		pSongs.remove(indexToDelete);
		samples.add(songToDelete);
		playSongs.setItems(pSongs);
		playButton.setText("Play");
	}
		else {
			pSongs.remove(indexToDelete);
			samples.add(songToDelete);
			playSongs.setItems(pSongs);
		}
		}
		}
		
	
	@FXML
	private void crop() throws IOException {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("playFromGui.fxml"));     
		Parent root = (Parent)fxmlLoader.load();          
		PlayFromController controller = fxmlLoader.<PlayFromController>getController();
		controller.setSongList(list);
		Scene scene = new Scene(root); 
		stage.setScene(scene);    
		stage.showAndWait();
		playButton.setText("Pause");
	}
	
	public void errShow(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Whoa!");
		alert.setContentText(error);
		alert.show();
	}


}

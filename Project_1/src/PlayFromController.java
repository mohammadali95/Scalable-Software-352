import java.io.IOException;
import javafx.util.Duration;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PlayFromController {
	
	@FXML
	Button playButton;
	
	@FXML
	TextField time;
	
	@FXML private AnchorPane ap;

	private SongList list;
	private Controller cont;
	void initialize() {}
	
	void setSongList(SongList list){
		this.list = list;
		System.out.println(list);
	}
	
	
	
	@FXML
	private void playFromHere() throws IOException {
		String timeText = time.getText();
		System.out.println(timeText);
		String[] split = timeText.split(":");
		Double minutesInMilli = (double) (Integer.parseInt(split[0]) * 60000);
		Double secondsInMilli = (double) (Integer.parseInt(split[1]) * 1000);
		
		
		Duration lengthOfSong = new Duration(minutesInMilli + secondsInMilli);
		
		
		
		this.list.playFrom(lengthOfSong);

		Stage stage = (Stage) ap.getScene().getWindow();
		stage.close();
	}
	
	


}

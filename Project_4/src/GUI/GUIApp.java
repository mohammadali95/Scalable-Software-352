package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import SQL.Database;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class GUIApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Database.createDB("blooddonor");
			Database.readCommand("CREATE TABLE IF NOT EXISTS Donor (Name TEXT, Id TEXT, Bloodtype TEXT, Organ TEXT)");
			Database.readCommand("CREATE TABLE IF NOT EXISTS Patient (Name TEXT, Id TEXT, Bloodtype TEXT, Organ TEXT)");
			AnchorPane root = (AnchorPane)FXMLLoader.load(GUIApp.class.getResource("DonationGUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
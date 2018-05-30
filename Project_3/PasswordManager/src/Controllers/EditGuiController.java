package Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditGuiController {
	
	
	private static String newPassword;
	private static Boolean update;
	
	public static String getPass() {
        return EditGuiController.newPassword;
    }
	
	public static Boolean getUpdate() {
		return EditGuiController.update;
	}
	
	@FXML
	TextField password;
	
	@FXML
	TextField reenterPassword;
	
	@FXML
	Button saveButton;
	
	@FXML private AnchorPane ap;

	private PasswordTableController parent;
	
	private void initialize() {}
	
	public Boolean checkSame() {
		if (password.getText().equals(reenterPassword.getText())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean checkEmpty() {
		if (password.getText().equals("")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@FXML
	public void save() {
		if (checkEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Whoa!");
			alert.setContentText("Fields Empty!!");
			alert.show();
			update = false;
			 
			}
		else if (!checkSame()){
		
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Whoa!");
			alert.setContentText("Passwords not the same!");
			alert.show();
			password.clear();
			reenterPassword.clear();
			update = false;
		}
		
		else {
		
		update = true;
		newPassword = password.getText();
		Stage stage = (Stage) ap.getScene().getWindow();
		stage.close();
		}
			
		
				
			}
		
	
	
	

}

package GUI;

import People.*;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class GUIController {

	@FXML
	TextField addName;

	@FXML
	TextField addID;

	@FXML
	ComboBox<String> addOrgans;

	@FXML
	ComboBox<String> addBloodType;

	@FXML
	ComboBox<String> addPatientOrDonor;

	@FXML
	TextField searchName;

	@FXML
	TextField searchID;

	@FXML
	ComboBox<String> searchOrgans;

	@FXML
	ComboBox<String> searchBloodType;

	@FXML
	ComboBox<String> searchPatientOrDonor;

	@FXML
	TextField removeID;

	@FXML
	ComboBox<String> removePatientOrDonor;

	@FXML
	ListView<String> searchResults;

	@FXML
	ImageView joshPic;

	ObservableList<String> results = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		bloodTypeSetup(addBloodType);
		bloodTypeSetup(searchBloodType);
		organSetup(addOrgans);
		organSetup(searchOrgans);
		patientDonorSetup(addPatientOrDonor);
		patientDonorSetup(searchPatientOrDonor);
		patientDonorSetup(removePatientOrDonor);
	}

	@FXML
	public void addNewPatientOrDonor() {
		if(!checkName(addName.getText())) {
			Person.showAlert("The name should only contain alphanumeric characters.");
		}
		else if(!checkID(addID.getText())) {
			Person.showAlert("The ID should only contain numbers.");
		}
		else if(addPatientOrDonor.getValue() == null) {
			Person.showAlert("Please choose if this person is a Patient or a Donor.");
		}
		else {
			try {
				Person.add(addPatientOrDonor.getValue(), addName.getText(), addID.getText(), addBloodType.getValue(), addOrgans.getValue());
			} catch (SQLException e) {
				Person.showAlert("There has been an error in the program.");
			}
		}
		clearFields(addName, addID, addOrgans, addBloodType, addPatientOrDonor);
	}

	@FXML
	public void deletePatientOrDonor() {
		if(!checkID(removeID.getText())) {
			Person.showAlert("The ID should only contain numbers");
		}
		else if(removePatientOrDonor.getValue() == null) {
			Person.showAlert("Please choose if this person is a Patient or a Donor.");
		}
		else {
			try {
				Person.remove(removePatientOrDonor.getValue(), removeID.getText());
			} catch (SQLException e) {
				Person.showAlert("There has been an error in the program.");
			}
		}
		clearFields(null, removeID, null, null, removePatientOrDonor);
	}

	@FXML
	public void searchPatientDonor() {
		if(!searchName.getText().isEmpty() && !checkName(searchName.getText())) {
			Person.showAlert("The name should only contain alphanumeric characters.");
		}
		else if(!searchID.getText().isEmpty() && !checkID(searchID.getText())) {
			Person.showAlert("The ID should only contain numbers.");
		}
		else if(searchPatientOrDonor.getValue() == null) {
			Person.showAlert("Please choose if this person is a Patient or a Donor");
		}
		else {
			results.clear();
			try {
				results.addAll(Person.search(searchPatientOrDonor.getValue(), searchName.getText(), searchID.getText(), searchBloodType.getValue(), searchOrgans.getValue()));
				updateListView();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		clearFields(searchName, searchID, searchOrgans, searchBloodType, searchPatientOrDonor);
	}

	public void clearFields(TextField name, TextField id, ComboBox<String> available, ComboBox<String> bloodType, ComboBox<String> patientDonor) {
		if(name != null) {name.clear();}
		if(id != null) {id.clear();}
		if(available != null) {available.valueProperty().set(null);;}
		if(bloodType != null) {bloodType.valueProperty().set(null);}
		if(patientDonor != null) {patientDonor.valueProperty().set(null);}
	}

	public void bloodTypeSetup(ComboBox<String> bloodTypeBox) {
		bloodTypeBox.setItems(FXCollections.observableArrayList("AB+", "AB-", "A+", "A-", "B+", "B-", "O+", "O-"));
	}

	public void organSetup(ComboBox<String> organBox) {
		organBox.setItems(FXCollections.observableArrayList("Heart", "Kidney", "Liver", "Lung", "Pancreas", "Bone Marrow", "Tendon", "Skin", "Heart Valve", "Blood"));
	}

	public void patientDonorSetup(ComboBox<String> patientOrDonorBox) {
		patientOrDonorBox.setItems(FXCollections.observableArrayList("Patient", "Donor"));
	}

	public void updateListView() {
		searchResults.setItems(results);
	}

	//https://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters
	public Boolean checkName(String name) {
		return name.matches("[a-z A-Z]+");
	}

	public Boolean checkID(String id) {
		return id.matches("[0-9]+");
	}

	@FXML
	public void showJosh() {
		joshPic.setVisible(true);
	}
}
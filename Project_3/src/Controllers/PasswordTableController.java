package Controllers;
import Data.Database;
import OtherStuff.PasswordGenerator;
import Controllers.EditGuiController;
import Data.PasswordLabel;
import Data.Tuple;
import com.sun.prism.impl.Disposer;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PasswordTableController {
	
	private String hide = "**********";



	@FXML
	private Button popup;
	
	@FXML
	private Button addPasswordButton;

	@FXML
	private TableView<PasswordLabel> table;

	@FXML
	private TableColumn<PasswordLabel, String> label;

	@FXML
	private TableColumn <PasswordLabel, String> password;

	@FXML
	private TableColumn <PasswordLabel, Button> showButton;

	@FXML
	private TableColumn <PasswordLabel, Button> deleteButton;
	
	@FXML
	private Button generateButton;

	@FXML
	private TextField maxLength;

	@FXML
	private TextField labelField;

	@FXML
	private TextField passwordField;

	@FXML
	private Button showButt;
	
	private Database database;

	private PasswordGenerator generator;

	private ArrayList<Tuple<String>> data;
	
	private Map<String,String> passwordMap = new HashMap<String ,String>();

	private Map<String, Boolean> isHiddenMap = new HashMap<String,Boolean>();

	private static String plainTextMasterPassword;



	public static void setPlainTextMasterPassword(String password) {
		plainTextMasterPassword = password;
	}


	@FXML
	private void initialize() throws SQLException, Exception {
		Platform.runLater(() -> {

			generator = new PasswordGenerator();

			try {
				database = new Database("Passwords");
				database.setPlainTextMasterPassword(plainTextMasterPassword);
				instantiateTable();
				setupTable();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				BadNews.display("Internal error. ");
			} catch (Exception e) {
				e.printStackTrace();
				BadNews.display("Internal error. ");
			}

		});


	}

	private void instantiateTable() throws Exception {
		table.getItems().clear();
		data = database.getAllPasswords();
		
		for (Tuple t: data) {
			passwordMap.put((String)t.getLabel(), (String)t.getPassword());
			isHiddenMap.put((String)t.getLabel(), true);
			table.getItems().add(new PasswordLabel ((String)t.getLabel(), ((String)t.getPassword()), true));
		}
		

	}

	private void setupTable() {
		label.setCellValueFactory(cdf -> new SimpleStringProperty(cdf.getValue().getLabel()));
		password.setCellValueFactory(cdf -> new SimpleStringProperty(cdf.getValue().getPassword()));
		ContextMenu contextMenu = new ContextMenu();
		MenuItem edit = new MenuItem("Edit Password");
		contextMenu.getItems().add(edit);
		table.addEventHandler(MouseEvent.MOUSE_CLICKED,  new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent t) {
				if(t.getButton() == MouseButton.SECONDARY) {
					
					contextMenu.show(table, t.getScreenX(), t.getScreenY());
				}
			}
		});
		
		edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event)   {
				// TODO Auto-generated method stub
				PasswordLabel selectedColunm = table.getSelectionModel().getSelectedItem();
				String label = selectedColunm.getLabel();
			
			
				
				try {
					Stage stage = new Stage();
					Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Resources/editPassword.fxml"));
					Scene sceneLogin = new Scene(root);
					stage.setScene(sceneLogin);
					stage.showAndWait();
					
					//EditGuiController.getController(this.getClass());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				if (EditGuiController.getUpdate()) {
				String newPassword = EditGuiController.getPass();
				System.out.println("this" + newPassword);
				passwordMap.replace(label, newPassword);
				//passwordMap.clear();
				String password = selectedColunm.getPassword();
				Tuple<String> toEdit = new Tuple<String>(label, password);
				System.out.println(toEdit.toString());
				Tuple<String> updated = new Tuple<String>(label, newPassword);
				System.out.println(updated);
				try {
					database.editData(toEdit, updated);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				}
		});
		
		
		// https://gist.github.com/abhinayagarwal/9735744

		TableColumn show_col = new TableColumn<>();
		show_col.setMinWidth(100);

		table.getColumns().add(show_col);

		show_col.setCellValueFactory(
				(Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>) p -> new SimpleBooleanProperty(p.getValue() != null));
		//Adding the Button to the cell
		show_col.setCellFactory(
				(Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>) p -> new ShowButton());


		TableColumn copy_col = new TableColumn<>();
		copy_col.setMinWidth(150);
		table.getColumns().add(copy_col);


		copy_col.setCellValueFactory(
				(Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>) p -> new SimpleBooleanProperty(p.getValue() != null));
		//Adding the Button to the cell
		copy_col.setCellFactory(
				(Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>) p -> new CopyButton());



		TableColumn delete_col = new TableColumn<>();
		delete_col.setMinWidth(100);

		table.getColumns().add(delete_col);

		delete_col.setCellValueFactory(
				(Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>) p -> new SimpleBooleanProperty(p.getValue() != null));
		//Adding the Button to the cell
		delete_col.setCellFactory(
				(Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>) p -> new DeleteButton());

	}



	private void update() throws SQLException, Exception {
		table.getItems().clear();
		data = database.getAllPasswords();
		for (Tuple t: data) {
			PasswordLabel pl = new PasswordLabel((String) t.getLabel(), (String)t.getPassword(), isHiddenMap.get(t.getLabel()));
			table.getItems().add(new PasswordLabel(pl.getLabel(),pl.getPassword(),pl.isHidden()));
		}

	}

	@FXML
	private void generate() throws IOException {
		try {
			Integer.parseInt(maxLength.getText());
			generator.setMaxLength(Integer.valueOf(maxLength.getText()));
			passwordField.setText(generator.generate());
		} catch (NumberFormatException ex) {
			BadNews.display("Max length must be an integer greater than 3.");
		}
	}

	@FXML
	private void addPassword() throws SQLException, Exception {
		// TODO  make sure there is something in labelField & passwordField & that labelField is unique in this.data
		//        database.addPassword(new Tuple<String>());
		if (labelField.getText().isEmpty() || passwordField.getText().isEmpty()) {
			BadNews.display("Both label and password fields must be non-empty. ");
		} else {
			try {
				database.addPassword(new Tuple(labelField.getText(), passwordField.getText()));
				passwordMap.put(labelField.getText(), passwordField.getText());
				isHiddenMap.put(labelField.getText(), true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			labelField.clear();
			passwordField.clear();
			Platform.runLater(() -> {
				try {
					update();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}


	private class DeleteButton extends TableCell<Disposer.Record, Boolean> {
		final Button cellButton = new Button("Delete");
		DeleteButton() {

			//Action when the button is pressed
			cellButton.setOnAction(t -> {
                // get Selected Item
                PasswordLabel currentRow = (PasswordLabel) DeleteButton.this.getTableView().getItems().get(DeleteButton.this.getIndex());
                //remove selected item from the table list
                passwordMap.remove(currentRow.getLabel());
                Tuple<String> toDel = new Tuple<String>(currentRow.getLabel(), currentRow.getPassword());
                try {
                    database.deletePassword(toDel);
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
		}

		//Display button if the row is not empty
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(cellButton);
			} else {
				setGraphic(null);
			}
		}
	}
	private class ShowButton extends TableCell<Disposer.Record, Boolean> {
		final Button cellButton = new Button("Show/Hide");
		ShowButton() {

			//Action when the button is pressed
			cellButton.setOnAction(t -> {
                // get Selected Item
                PasswordLabel currentRow = (PasswordLabel) ShowButton.this.getTableView().getItems().get(ShowButton.this.getIndex());
                //remove selected item from the table list
                currentRow.toggleHidden();
                isHiddenMap.put(currentRow.getLabel(),currentRow.isHidden());
                try {
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
		}

		//Display button if the row is not empty
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(cellButton);
			} else {
				setGraphic(null);
			}
		}
	}

	private class CopyButton extends TableCell<Disposer.Record, Boolean> {
		final Button cellButton = new Button("Copy to Clipboard");
		CopyButton() {

			//Action when the button is pressed
			cellButton.setOnAction(t -> {
                PasswordLabel currentRow = (PasswordLabel) CopyButton.this.getTableView().getItems().get(CopyButton.this.getIndex());
                String password = passwordMap.get(currentRow.getLabel());

				Toolkit.getDefaultToolkit()
						.getSystemClipboard()
						.setContents(
								new StringSelection(password),
								null
						);
            });
		}

		//Display button if the row is not empty
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(cellButton);
			} else {
				setGraphic(null);
			}
		}
	}
}

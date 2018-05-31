package People;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import SQL.Database;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Person {
	public static void add(String table, String name, String id, String bloodtype, String organ) throws SQLException {
		Database.readCommand("SELECT * FROM " + table + " WHERE Id = '" + id + "'");
		ResultSet results = Database.getAndCloseResults();
		if (!results.next()) {
			Database.readCommand("INSERT INTO " + table + " VALUES ('" + name + "', '" + id + "', '" + bloodtype + "', '" + organ + "')");
		} else {
			showAlert("There is another " + table + " with this ID number.");
		}
	}

	public static void remove(String table, String id) throws SQLException {
		Database.readCommand("SELECT * FROM " + table + " WHERE Id = '" + id + "'");
		ResultSet results = Database.getResults();
		if (results.next()) {
			Database.readCommand("DELETE FROM " + table + " WHERE Id = '" + id + "'");
		} else {
			showAlert("There is no " + table + " with this ID number.");
		}
		Database.closeResults(results);
	}
	
	public static ArrayList<String> search(String table, String name, String id, String bloodType, String organ) throws SQLException {		
		ArrayList<String> outputResult = new ArrayList<String>();
		String command = "SELECT * FROM " + table + " WHERE";
		if (!name.equals("")) {
			command = command + " NAME = '" + name + "' AND";
		}
		if (!id.equals("")) {
			command = command + " Id = '" + id + "' AND";
		}
		if (bloodType != null) {
			command = command + " Bloodtype = '" + bloodType + "' AND";
		}
		if (organ != null) {
			command = command + " Organ = '" + organ + "'";
		}
		if (command.substring(command.length() - 6).equals(" WHERE")) {
			command = command.substring(0, command.length() - 6);
		}
		if (command.substring(command.length() - 4).equals(" AND")) {
			command = command.substring(0, command.length() - 4);
		}
		Database.readCommand(command);
		ResultSet results = Database.getResults();
		outputResult.add(formatString("Name", "ID", "BloodType", "Organ"));
		while (results.next()) {
			outputResult.add(formatString(results.getString("Name"), results.getString("Id"), results.getString("Bloodtype"), results.getString("Organ")));
		}
		Database.closeResults(results);
		return outputResult;
	}
	
	private static String formatString(String name, String id, String bloodType, String organ) throws SQLException {
		String formatted = name + "  -  " + id + "  -  " + bloodType + "  -  " + organ;
		return formatted;
	}
	
	public static void showAlert(String text) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Whoa!");
		alert.setContentText(text);
		alert.show();
	}
}


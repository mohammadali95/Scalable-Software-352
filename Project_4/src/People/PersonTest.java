package People;

import static org.junit.Assert.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import SQL.Database;

public class PersonTest {
	@Test
	public void addDonorTest() {
		try {
			addTest("Donor");
		} catch (ClassNotFoundException | SQLException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void addPatientTest() {
		try {
			addTest("Patient");
		} catch (ClassNotFoundException | SQLException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void removeDonorTest() {
		try {
			removeTest("Donor");
		} catch (ClassNotFoundException | SQLException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void removePatientTest() {
		try {
			removeTest("Patient");
		} catch (ClassNotFoundException | SQLException e) {
			Assert.fail();
		}
	}
	
	public void addTest(String table) throws ClassNotFoundException, SQLException {
		Database.createDB("test");
		Database.readCommand("DROP TABLE IF EXISTS " + table);
		Database.readCommand("CREATE TABLE " + table + " (Name TEXT, Id TEXT, Bloodtype TEXT, Organ TEXT)");
		Person.add(table, "ali", "001", "O+", "Heart");
		Database.readCommand("SELECT * FROM " + table);
		ResultSet results = Database.getResults();
		String name1 = results.getString("Name");
		String id = results.getString("Id");
		String type = results.getString("Bloodtype");
		String organ = results.getString("Organ");
		Database.closeResults(results);
		assertTrue(name1.equals("ali"));
		assertTrue(id.equals("001"));
		assertTrue(type.equals("O+"));
		assertTrue(organ.equals("Heart"));
	}

	public void removeTest(String table) throws ClassNotFoundException, SQLException {
		Database.createDB("test");
		Person.remove(table, "001");
		Database.readCommand("SELECT * FROM " + table);
		ResultSet results = Database.getResults();
		assertFalse(results.next());
		Database.closeResults(results);
	}
}

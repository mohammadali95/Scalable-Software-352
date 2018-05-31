package Data;

import Encryption.Encryption;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Calendar;

public class Database {
	private static Connection con;
	private static Statement stat;
	private static String plainTextMasterPassword;
	private static int lockOutTime = 3;

    public static String getMasterPassword() {
        return plainTextMasterPassword;
    }

    public static void setPlainTextMasterPassword(String password) {
        plainTextMasterPassword = password;
    }

	public Database(String name) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:" + name);
        stat = con.createStatement();
    }
	
    public void createTables() throws SQLException, ClassNotFoundException {
        stat.execute("CREATE TABLE IF NOT EXISTS Passwords (Label TEXT, Password TEXT)");
        stat.execute("CREATE TABLE IF NOT EXISTS Master (Password TEXT, Tries INTEGER, Next_Attempt TIMESTAMP)");
    }
    
    public void setMasterTable(String hashedMasterPassword) throws SQLException {
    	PreparedStatement add = con.prepareStatement("INSERT INTO Master VALUES (?, ?, ?)");
    	add.setString(1, hashedMasterPassword);
    	add.setInt(2,0);
    	Calendar calendar = Calendar.getInstance();
    	add.setTimestamp(3, new java.sql.Timestamp(calendar.getTimeInMillis()));
    	add.execute();
    }

    public void incAttemptedTries() throws SQLException {
        PreparedStatement update = con.prepareStatement("UPDATE Master SET Tries = ?");
        update.setInt(1, attemptedTries() + 1);
        update.execute();
    }
    
    public int attemptedTries() throws SQLException {
    	stat.execute("SELECT Tries FROM Master");
    	ResultSet results = stat.getResultSet();
    	int tries = results.getInt(1);
    	results.close();
    	return tries;
    }
    
    public void triesMaxed() throws SQLException {
    	PreparedStatement update = con.prepareStatement("UPDATE Master SET Tries = ?, Next_Attempt = ?");
    	update.setInt(1, 0);
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.MINUTE, lockOutTime);
    	update.setTimestamp(2, new java.sql.Timestamp(calendar.getTimeInMillis()));
    	update.execute();
    }

    public void resetTries() throws SQLException {
        PreparedStatement update = con.prepareStatement("UPDATE Master SET Tries = ?");
        update.setInt(1, 0);
        update.execute();
    }
    
    public static LocalDateTime nextLoginTime() throws SQLException {
    	stat.execute("SELECT Next_Attempt FROM Master");
    	ResultSet results = stat.getResultSet();
    	Timestamp date = results.getTimestamp(1);
    	results.close();
    	return date.toLocalDateTime();
    }

    public static String prettyPrintNextLoginTime() throws SQLException {
        return DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd ").format(nextLoginTime());
    }

    public static Boolean canLogin() throws SQLException {
        LocalDateTime next = nextLoginTime();
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        return next.isBefore(now.toLocalDateTime());
    }

    public static String getHashedMasterPassword() throws SQLException {
        stat.execute("SELECT Password TEXT FROM Master");
        ResultSet results = stat.getResultSet();
        return results.getString(1);
    }

    public static void addPassword(Tuple<String> labelAndPassword) throws Exception {
        Tuple<String> encrypted = Encryption.encryptTuple(labelAndPassword, plainTextMasterPassword);
        PreparedStatement search = con.prepareStatement("SELECT * FROM Passwords WHERE Label = ?");
        search.setString(1, encrypted.getLabel());
        ResultSet results = search.executeQuery();
        if (!results.next()) {
            PreparedStatement add = con.prepareStatement("INSERT INTO Passwords VALUES (?, ?)");
            add.setString(1, encrypted.getLabel());
            add.setString(2, encrypted.getPassword());
            add.execute();
        }
        results.close();
    }

    public static ArrayList<Tuple<String>> getAllPasswords() throws SQLException, Exception {
        ArrayList<Tuple<String>> passwords = new ArrayList<Tuple<String>>();
        stat.execute("SELECT * FROM Passwords");
        ResultSet results = stat.getResultSet();
        while (results.next()) {
            Tuple<String> encrypted = new Tuple(results.getString("Label"), results.getString("Password"));
            passwords.add(Encryption.decryptTuple(encrypted, plainTextMasterPassword));
        }
        return passwords;
    }
    
    public static ArrayList<Tuple<String>> searchPasswords(String label) throws Exception {
    	ArrayList<Tuple<String>> passwords = new ArrayList<Tuple<String>>();
    	PreparedStatement search = con.prepareStatement("SELECT * FROM Passwords WHERE Label = ?");
    	search.setString(1, label);
        ResultSet results = search.executeQuery();
        while (results.next()) {
            Tuple<String> encrypted = new Tuple(results.getString("Label"), results.getString("Password"));
        	passwords.add(Encryption.decryptTuple(encrypted, plainTextMasterPassword));
        }
        return passwords;
    }

    public static void editData(Tuple<String> oldLabelAndPassword, Tuple<String> newLabelAndPassword) throws Exception {
        oldLabelAndPassword = Encryption.encryptTuple(oldLabelAndPassword, plainTextMasterPassword);
        newLabelAndPassword = Encryption.encryptTuple(newLabelAndPassword, plainTextMasterPassword);
        PreparedStatement edit = con.prepareStatement("SELECT * FROM Passwords WHERE Label = ?");
        edit.setString(1, oldLabelAndPassword.getLabel());
        ResultSet results = edit.executeQuery();
        if (results.next()) {
            PreparedStatement update = con.prepareStatement("UPDATE Passwords SET Label = ?, Password = ? WHERE Label = ?");
            update.setString(1, newLabelAndPassword.getLabel());
            update.setString(2, newLabelAndPassword.getPassword());
            update.setString(3, oldLabelAndPassword.getLabel());
            update.execute();
        }
    }

    public static void deletePassword(Tuple<String> labelAndPassword) throws Exception {
        labelAndPassword = Encryption.encryptTuple(labelAndPassword, plainTextMasterPassword);
        PreparedStatement search = con.prepareStatement("SELECT * FROM Passwords WHERE Label = ?");
        search.setString(1, labelAndPassword.getLabel());
        ResultSet results = search.executeQuery();
        if (results.next()) {
            PreparedStatement delete = con.prepareStatement("DELETE FROM Passwords WHERE Label = ?");
            delete.setString(1, labelAndPassword.getLabel());
            delete.execute();
        }
        results.close();
    }
    
    public static void dropDatabase(String name) throws SQLException {
    	stat.execute("DROP DATABASE " + name);
    	stat.close();
    	con.close();
    }
}
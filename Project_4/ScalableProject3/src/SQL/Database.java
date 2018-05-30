package SQL;

import java.sql.*;

public class Database {
	private static Statement stat;
	private static Connection con;
	
	public Database() {}
	
    public static void createDB(String DBName) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        stat = con.createStatement();
    }
    
    public static void readCommand(String command) {
    	try {
			stat.execute(command);
		} catch (SQLException e) {
			System.out.println("Invalid argument");
		}
    }
    
    public static ResultSet getResults() throws SQLException {
    	return stat.getResultSet();
    }
    
    public static void closeResults(ResultSet results) throws SQLException {
    	results.close();
    }
    
    public static ResultSet getAndCloseResults() throws SQLException {
    	ResultSet results = stat.getResultSet();
    	results.close();
    	return results;
    }
    
    public static void printAll(String table) throws SQLException {
    	readCommand("SELECT * FROM " + table);
    	ResultSet results = stat.getResultSet();
    	while (results.next()) {
    		String name1 = results.getString("Name");
    		String id = results.getString("Id");
    		String type = results.getString("Bloodtype");
    		String organ = results.getString("Organ");
    		System.out.println(name1);
    		System.out.println(id);
    		System.out.println(type);
    		System.out.println(organ);
    	}
    	results.close();
    }
}
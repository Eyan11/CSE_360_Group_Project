package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p> DatabaseManager. </p>
 * 
 * <p> Description: Starts and closes connection to database.</p>
 * 
 * <p> Source: Lynn Robert Carter from FirstDatabase project, DatabaseHelper class, 
 * 				available at: https://canvas.asu.edu/courses/193728/files/92728837?module_item_id=14758007
 * 
 * @author Eyan Martucci
 * 
 *  
 */

public class DatabaseManager {

	// JDBC driver name and database URL 
	private static final String JDBC_DRIVER = "org.h2.Driver";   
	private static final String DB_URL = "jdbc:h2:~/GroupProjectDatabase"; 

	//  Database credentials 
	private static final String USER = "sa"; 
	private static final String PASS = ""; 
	
	// Reusable variables to communicate with database
	private static String query = "";
	private static Connection connection = null;
	private static Statement statement = null; 
	private static ResultSet resultSet = null;
	
	/**********
	 * Starts the connection to the H2 database.
	 */
	public static void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection Successful!");
			statement = connection.createStatement(); 
			
			AccountDatabase.setConnection(connection, statement);	// Let account class access database
			ArticleDatabase.setConnection(connection, statement);	// Let article class access database
			
			// Wipe all data in accounts table
			AccountDatabase.createTable();
			AccountDatabase.deleteTable();
			AccountDatabase.createTable();
			
			// Wipe all data in articles table
			ArticleDatabase.createTable();
			ArticleDatabase.deleteTable();
			ArticleDatabase.createTable();
			
			// Perform tests on both accounts and articles tables
			//AccountDatabaseTesting.performTestEvaluations();
			//ArticleDatabaseTesting.performTestEvaluations();
		} 
		// Connection failed
		catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}
	
	
	/**********
	 * Closes the connection to the H2 database.
	 */
	public static void closeConnection() {
		// Close statement
		try { 
			if(statement!=null) 
				statement.close(); 
		} 
		catch(SQLException se2) { 
			se2.printStackTrace();
		} 
		
		// Close connection
		try { 
			if(connection!=null) 
				connection.close(); 
			System.out.println("CLOSING CONNECTION TO DATABASE");
		} 
		catch(SQLException se){ 
			se.printStackTrace(); 
		} 
	}
}

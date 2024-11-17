package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p> DatabaseManager. </p>
 * 
 * <p> Description: Starts and closes connection to database.</p>
 * 
 * <p> Source: Lynn Robert Carter from FirstDatabase project, DatabaseHelper class, 
 * 				available at: https://canvas.asu.edu/courses/193728/files/92728837?module_item_id=14758007 </p>
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		10/26/2024 Phase 2 implementation and documentation
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
	private static Connection connection = null;
	private static Statement statement = null; 
	
	/**********
	 * Starts the connection to the H2 database.
	 */
	public static void connectToDatabase() {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection Successful!");
			statement = connection.createStatement(); 
			
			// Let each class access database
			AccountDatabase.setConnection(connection, statement);
			ArticleDatabase.setConnection(connection, statement);
			HelpMessageDatabase.setConnection(connection, statement);
			GroupDatabase.setConnection(connection, statement);
			
			// Wipe all data in accounts table
			AccountDatabase.createTable();
			AccountDatabase.deleteAllAccounts();
			
			// Wipe all data in articles table
			ArticleDatabase.createTable();
			ArticleDatabase.deleteAllArticles();
			
			// Wipe all data in help_messages table
			HelpMessageDatabase.createTable();
			HelpMessageDatabase.deleteAllMessages();
			
			// Wipe all data in groups table
			GroupDatabase.createTable();
			GroupDatabase.deleteAllGroups();
			
			// Perform tests on both accounts and articles tables
			//AccountDatabaseTesting.performTestEvaluations();
			//ArticleDatabaseTesting.performTestEvaluations();
		} 
		// Connection failed
		catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException in DatabaseManager.connectToDatabase \n\n");
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
		catch(SQLException e) {
			System.err.println("SQLException in DatabaseManager.connectToDatabase \n\n");
			e.printStackTrace();
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
			System.err.println("\nStatement not closed, error occured, "
					+ "you may need to delete the 'GroupProjectDatabase.mv and .trace file\n");
			se2.printStackTrace();
		} 
		
		// Close connection
		try { 
			if(connection!=null) 
				connection.close(); 
			System.out.println("CLOSING CONNECTION TO DATABASE");
		} 
		catch(SQLException se){ 
			System.err.println("\nConnection to database not closed, error occured, "
					+ "you may need to delete the 'GroupProjectDatabase.mv and .trace file\n");
			se.printStackTrace(); 
		} 
	}
}

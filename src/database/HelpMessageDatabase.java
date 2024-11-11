package database;

import java.sql.*; // For SQL related objects

/**
 * <p> HelpMessageDatabase. </p>
 * 
 * <p> Description: Manages the Help Message Table in the H2 database.</p>
 * 
 * <p> Source: Lynn Robert Carter from FirstDatabase project, DatabaseHelper class, 
 * 				available at: https://canvas.asu.edu/courses/193728/files/92728837?module_item_id=14758007 </p>
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		TODO
 *  
 */

public class HelpMessageDatabase {

	// To communicate with database
	private static String query = "";
	private static Connection connection = null;
	private static Statement statement = null; 
	private static ResultSet resultSet = null;
	
	
	/**********
	 * Sets connection and statement variables so this class can access the database
	 */
	public static void setConnection(Connection _connection, Statement _statement) {
		connection = _connection;
		statement = _statement;
	}
	
	
	/**********
	 * Creates a table called help_messages and initializes columns.
	 */
	public static void createTable() {
		query = "CREATE TABLE IF NOT EXISTS help_messages ("
				+ "id INT AUTO_INCREMENT PRIMARY KEY,"
				+ "timestamp TIMESTAMP,"	// TIMESTAMP is when message was placed (Format: YYYY-MM-DD HH:MI:SS)
				+ "message VARCHAR(300))";
		try {
			statement.execute(query);
			System.out.println("'help_messages' table created if it did not already exist");
		}
		catch(SQLException e) {
			System.err.println("SQLException in HelpMessageDatabase.createTable \n\n");
			e.printStackTrace();
		}
	}
	
	
	/**********
	 * Deletes the entire help_messages table in database
	 */
	public static void deleteTable() {
		query = "DROP TABLE help_messages";	// delete help_messages table
		try {
			statement.execute(query);		// execute query
			System.out.println("'help_messages' table deleted");
		}
		catch(SQLException e) {
			System.err.println("SQLException in HelpMessageDatabase.deleteTable \n\n");
			e.printStackTrace();
		}
	}
	
	
	/**********
	 * Deletes all rows in help_messages database
	 */
	public static void deleteAllMessages() {
		query = "DELETE FROM help_messages";	// delete all help_messages in table
		try {
			statement.execute(query);			// execute query
			System.out.println("All messages in 'help_messages' table deleted");
		}
		catch(SQLException e) {
			System.err.println("SQLException in HelpMessagesDatabase.deleteAllMessages \n\n");
			e.printStackTrace();
		}
	}

	
	/**********************************************************************************************

	 Public Methods To Get Database Information
	
	**********************************************************************************************/
	
	
	/**********
	 * Checks if there is at least one row of data in help_messages table.
	 */
	public static boolean isTableEmpty() {
		// Counts total number of rows in help_messages table
		query = "SELECT COUNT(*) AS count FROM help_messages";
		
		try {
			resultSet = statement.executeQuery(query);
			
			// if there is a next row, return the number of rows 
			if (resultSet.next()) {
				return resultSet.getInt("count") == 0;
			}
			return false;	// if no rows
		}
		catch(SQLException e) {
			System.err.println("SQLException in HelpMessagesDatabase.isTableEmpty \n\n");
			e.printStackTrace();
		}
		return false;	// for error
	}
	
	
	/**********
	 * Returns the message and timestamp for every row in help_messages table as a String
	 * in format of "timestamp1,message1|timestamp2,message2|..."
	 */
	public static String getAllHelpMessages() {
		
		// Prevent getting articles if empty
		if(isTableEmpty()) {
			System.err.println("Cannot get all help messages because table is empty!");
			return "";
		}
		
		// Select all rows from table
		query = "SELECT * FROM help_messages"; 
		String returnString = "";
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query); 	// Execute query
	
			// While the next row exists, check next row
			while(resultSet.next()) { 
				// Get current article info
				returnString += resultSet.getTimestamp("timestamp").toString() + ",";
				returnString += resultSet.getString("message") + "|";
			}
		}
		catch(SQLException e) {
			System.err.println("SQLException in HelpMessagesDatabase.getAllHelpMessages \n\n");
			e.printStackTrace();
		}
		return returnString;	// for error
	}
	
	
	/**********************************************************************************************

	 Public Methods To Set Database Information
	
	**********************************************************************************************/
	
	
	/**********
	 * Creates a generic message and stores in help_messages table, returns if successful or not.
	 */
	public static boolean createGenericMessage(String group) {
		
		// Prevent sending message if group doesn't exist
		/*	TODO once group database is created
		if(doesGroupExist(group)) {
			System.err.println("Cannot send generic message because group: " + group + " doesn't exist!");
			return false;
		}
		*/
		
		// Insert a new row into database and fill in the following column values
		query = "INSERT INTO help_messages (timestamp, message) "
				+ "VALUES (?, ?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			
			// Set the placeholder ? variables
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(2, "More help articles regarding the " + group + " group are requested.");
			pstmt.executeUpdate();		// Execute query
			return true;
		}
		catch(SQLException e) {
			System.err.println("SQLException in HelpMessages.createGenericMessage \n\n");
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**********
	 * Creates a specific message and stores in help_messages table, returns if successful or not.
	 */
	public static boolean createSpecificMessage(String message) {
		
		// Insert a new row into database and fill in the following column values
		query = "INSERT INTO help_messages (timestamp, message) "
				+ "VALUES (?, ?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			
			// Set the placeholder ? variables
			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(2, message);
			pstmt.executeUpdate();		// Execute query
			return true;
		}
		catch(SQLException e) {
			System.err.println("SQLException in HelpMessages.createGenericMessage \n\n");
			e.printStackTrace();
		}
		return false;
	}
}

package application;

import java.sql.*;

public class AccountDatabase {
	
	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/firstDatabase"; 

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 

	private Connection connection = null;
	private Statement statement = null; 
	private ResultSet resultSet = null;


	/**********************************************************************************************

	Private Methods
	
	**********************************************************************************************/
	
	/**********
	 * Creates a table called accounts and initializes columns
	 * From Instructor's DatabaseHelper class
	 */
	private void createTables() throws SQLException {
		String userTable = "CREATE TABLE IF NOT EXISTS accounts ("
				+ "username VARCHAR(50) PRIMARY KEY UNIQUE, "
				+ "password VARCHAR(50), "		// VARCHAR(#) is a string with a max of # characters
				+ "email VARCHAR(50) UNIQUE, "
				+ "first_name VARCHAR(50),"
				+ "middle_name VARCHAR(50),"
				+ "last_name VARCHAR(50),"
				+ "preferred_name VARCHAR(50),"
				+ "display_name VARCHAR(50),"
				+ "is_key BIT," 					// BIT is used for boolean, 0 is false, 1 is true
				+ "is_student BIT,"
				+ "is_instructor BIT,"
				+ "is_admin BIT,"
				+ "expiration TIMESTAMP)";			// TIMESTAMP is an exact date and time (when key expires)
		statement.execute(userTable);
	}
	
	
	/**********
	 * TODO To test if users have been added to database
	 * From Instructor's DatabaseHelper class
	 */
	private void printUsers() throws SQLException{
		// get entire accounts table
		String sql = "SELECT * FROM accounts"; 
		statement = connection.createStatement();
		resultSet = statement.executeQuery(sql); 
		
		System.out.println(); // empty line before printing all users

		// while the row exists
		while(resultSet.next()) { 
			// Retrieve by column name 
			String user = resultSet.getString("username"); 

			// Display values 
			System.out.println("ID: " + user); 
		} 
	}
	
	
	/**********************************************************************************************

	Public Methods
	
	**********************************************************************************************/
	
	/**********
	 * This method starts the connection to the SQL database when the program is started
	 * From Instructor's DatabaseHelper class
	 */
	public void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection Successful!");
			statement = connection.createStatement(); 
			createTables();  // Create the necessary tables if they don't exist
			
			// testing
			//register("Tommy", "1234");
			//register("Billy", "1112");
			//printUsers();
			
		// Connection failed, driver not found
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}
	
	/**********
	 * This method closes the connection to the SQL database when the program is closed
	 * From Instructor's DatabaseHelper class
	 */
	public void closeConnection() {
		// Close statement
		try{ 
			if(statement!=null) statement.close(); 
		} catch(SQLException se2) { 
			se2.printStackTrace();
		} 
		// Close connection
		try { 
			if(connection!=null) connection.close(); 
		} catch(SQLException se){ 
			se.printStackTrace(); 
		} 
	}
	
	/**********
	 * Checks if there exists a row of data in accounts database
	 * Returns true when there are no rows in the accounts database
	 * From Instructor's DatabaseHelper class
	 */
	public boolean isDatabaseEmpty() throws SQLException {
		// Counts total number of rows in accounts database
		String query = "SELECT COUNT(*) AS count FROM accounts";
		resultSet = statement.executeQuery(query);
		
		// if there is a next row, return the number of rows 
		if (resultSet.next()) {
			return resultSet.getInt("count") == 0;
		}
		return true;
	}
	
	public boolean doesLoginExist(String user, String pass) {
		// TODO
		return true;
	}
	
	public boolean doesKeyExist(String key) {
		// TODO
		return true;
	}
	
	public boolean doesUsernameExist(String user) {
		// TODO
		return false;
	}
	
	public boolean doesEmailExist(String email) {
		// TODO
		return false;
	}
	
	public void CreateAccount(String user, String pass) {
		// TODO
	}
	
	public void UpdateAccount(String email, String firstName, String middleName, String lastName, String prefName) {
		// TODO
	}
	
	
	
	// TODO put in main function before launch(args); to start and close database
	// Not putting in main right now to avoid giving merge conflicts
	/*
	import java.sql.SQLException;
	
	// Reference to database
	AccountDatabase database = new AccountDatabase();
	
	// Start database connection
	try {
		database.connectToDatabase();
	}
	catch (SQLException e) {
		System.err.println("Database error: " + e.getMessage());
		e.printStackTrace();
	}
	// Close database connection when software is closed
	finally {
		System.out.println("Closing Database Connection");
		database.closeConnection();
	}
	*/
	
	
	
	
	
	
	
	
	
	/**********
	 * TODO Adds a username and password to database
	 * From Instructor's DatabaseHelper class
	 */
	/*
	public void register(String username, String password) throws SQLException {
		String insertUser = "INSERT INTO accounts (username, password) VALUES (?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
		}
	}
	*/
	
	
}

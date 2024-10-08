package application;

import java.sql.*;

public class AccountDatabase {
	
	// JDBC driver name and database URL 
	private final String JDBC_DRIVER = "org.h2.Driver";   
	private final String DB_URL = "jdbc:h2:~/accountDatabase"; 

	//  Database credentials 
	private final String USER = "sa"; 
	private final String PASS = ""; 

	// Reusable variables to execute queries
	private static String query = "";
	private static Connection connection = null;
	private static Statement statement = null; 
	private static ResultSet resultSet = null;
	
	
	/**********************************************************************************************

	Public Methods For Database Connection
	
	**********************************************************************************************/
	
	
	/**********
	 * Starts the connection to the SQL database DELETE working
	 */
	public void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection Successful!");
			statement = connection.createStatement(); 
			
			createTables();  // Create accounts database if it doesn't exist on local machine
			System.out.println("Created Tables if none existed"); // DELETE working
		} 
		// Connection failed
		catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}
	
	
	/**********
	 * Closes the connection to the SQL database when the program is closed DELETE working
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
	
	
	/**********************************************************************************************

	Public Getter Methods To Check Database Values
	
	**********************************************************************************************/
	
	
	/**********
	 * Checks if there is at least one row of data in accounts database
	 */
	public static boolean isDatabaseEmpty() throws SQLException {
		// Counts total number of rows in accounts database
		query = "SELECT COUNT(*) AS count FROM accounts";
		resultSet = statement.executeQuery(query);
		
		// if there is a next row, return the number of rows 
		if (resultSet.next()) {
			return resultSet.getInt("count") == 0;
		}
		return true;
	}
	
	
	/**********
	 * Checks if the given username and password exist in the same row in accounts database
	 */
	public static boolean doesLoginExist(String user, String pass) throws SQLException {
		// Query gets all data in accounts database where username and password equal placeholder variable ?
		query = "SELECT * FROM accounts WHERE username = ? AND password = ?";
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			pstmt.setString(1, user);				// First ? = user
			pstmt.setString(2, pass);				// Second ? = pass
			
			resultSet = pstmt.executeQuery();		// ResultSet is now positioned before first row
			return resultSet.next();				// Returns if first row exists or not			
		}
	}
	
	
	/**********
	 * Checks if the given key exists and is_key is true in database DELETE untested changes
	 */
	public static boolean doesKeyExist(String key) throws SQLException {
		// Query gets all data in accounts database where is_key and password equal placeholder variable ?
		query = "SELECT * FROM accounts WHERE is_key = true AND password = ?";
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			pstmt.setString(1, key);				// Second ? = key
			
			resultSet = pstmt.executeQuery();		// ResultSet is now positioned before first row
			return resultSet.next();				// Returns if first row exists or not			
		}
	}
	
	
	/**********
	 * Checks if the given username exists in the accounts database
	 */
	public static boolean doesUsernameExist(String user) {
		// Query finds total number of rows in accounts database where username is equal to placeholder variable ?
	    query = "SELECT COUNT(*) FROM accounts WHERE username = ?";
	    // Prepare the previous query to be executed
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, user);				    // Set the ? placeholder variable in query to user
	        resultSet = pstmt.executeQuery();			// ResultSet is now positioned before first row
	        
	        if (resultSet.next()) {						// Move resultSet cursor to first row
	            return resultSet.getInt(1) > 0;			// If returned at least 1 row, user exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();						// print trace of exception
	    }
	    return false; 									// If an error occurs, assume user doesn't exist
	}
	
	
	/**********
	 * Checks if the given email exists in the accounts database
	 */
	public static boolean doesEmailExist(String email) {
		// Query finds total number of rows in accounts database where email is equal to placeholder variable ?
	    query = "SELECT COUNT(*) FROM accounts WHERE email = ?";
	    // Prepare the previous query to be executed
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, email);				    // Set the ? placeholder variable in query to email
	        resultSet = pstmt.executeQuery();			// ResultSet is now positioned before first row
	        
	        if (resultSet.next()) {						// Move resultSet cursor to first row
	            return resultSet.getInt(1) > 0;			// If returned at least 1 row, email exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();						// print trace of exception
	    }
	    return false; 									// If an error occurs, assume user doesn't exist
	}
	
	
	/**********
	 * Checks if a given user has the student role
	 */
	public static boolean isStudentRole(String user) {
		// Query finds total number of rows in accounts database 
		// where username is equal to placeholder variable ? and is_student is true
	    query = "SELECT COUNT(*) FROM accounts WHERE username = ? AND is_student = true";
	    // Prepare the previous query to be executed
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, user);				    // Set the ? placeholder variable in query to user
	        resultSet = pstmt.executeQuery();			// ResultSet is now positioned before first row
	        
	        if (resultSet.next()) {						// Move resultSet cursor to first row
	            return resultSet.getInt(1) > 0;			// If returned at least 1 row, user has student role
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();						// print trace of exception
	    }
	    return false; 									// If an error occurs, assume user doesn't exist
	}
	
	
	/**********
	 * Checks if a given user has the instructor role
	 */
	public static boolean isInstructorRole(String user) {
		// Query finds total number of rows in accounts database 
		// where username is equal to placeholder variable ? and is_instructor is true
	    query = "SELECT COUNT(*) FROM accounts WHERE username = ? AND is_instructor = true";
	    // Prepare the previous query to be executed
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, user);				    // Set the ? placeholder variable in query to user
	        resultSet = pstmt.executeQuery();			// ResultSet is now positioned before first row
	        
	        if (resultSet.next()) {						// Move resultSet cursor to first row
	            return resultSet.getInt(1) > 0;			// If returned at least 1 row, user has instructor role
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();						// print trace of exception
	    }
	    return false; 									// If an error occurs, assume user doesn't exist
	}
	
	
	/**********
	 * Checks if a given user has the admin role
	 */
	public static boolean isAdminRole(String user) {
		// Query finds total number of rows in accounts database 
		// where username is equal to placeholder variable ? and is_admin is true
	    query = "SELECT COUNT(*) FROM accounts WHERE username = ? AND is_admin = true";
	    // Prepare the previous query to be executed
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, user);				    // Set the ? placeholder variable in query to user
	        resultSet = pstmt.executeQuery();			// ResultSet is now positioned before first row
	        
	        if (resultSet.next()) {						// Move resultSet cursor to first row
	            return resultSet.getInt(1) > 0;			// If returned at least 1 row, user has admin role
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();						// print trace of exception
	    }
	    return false; 									// If an error occurs, assume user doesn't exist
	}
	
	
	/**********
	 * Checks if a given user has updated their account information
	 */
	public static boolean isAccountUpdated(String user) {
		// Query finds total number of rows in accounts database 
		// where username is equal to placeholder variable ? and is_account_updated is true
	    query = "SELECT COUNT(*) FROM accounts WHERE username = ? AND is_account_updated = true";
	    // Prepare the previous query to be executed
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, user);				// Set the ? placeholder variable in query to user
	        resultSet = pstmt.executeQuery();		// ResultSet is now positioned before first row
	        
	        if (resultSet.next()) {					// Move resultSet cursor to first row
	            return resultSet.getInt(1) > 0;		// If returned at least 1 row, user has updated account info
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();					// print trace of exception
	    }
	    return false; 								// If an error occurs, assume user has NOT updated account info
	}
	
	
	
	
	/**********************************************************************************************

	Public Setter Methods To Update or Add Accounts to Database
	
	**********************************************************************************************/
	
	
	/**********
	 * Creates the first account in accounts database with provided username and password.
	 * If there is already an account, it will exit the method and print an error message. DELETE working but test new stuff
	 */
	public static void createFirstAccount(String user, String pass) throws SQLException {
		
		// Prevents using method when not the first account in database
		if(!isDatabaseEmpty()) {
			System.err.println("Can't create first account, database is NOT empty");
		}
		// Prevents very long usernames
		if(user.length() > 50) {
			System.err.println("Cannot create account username is over 50 characters");
			return;
		}
		// Prevents very long passwords
		if(pass.length() > 50) {
			System.err.println("Cannot create account password is over 50 characters");
			return;
		}
		
		
		// Query inserts placeholder variables ? for username and password columns into a new row in accounts database
		query = "INSERT INTO accounts (username, password, is_student, is_instructor, is_admin, is_key, is_account_updated) VALUES (?, ?, ?, ?, ?, ?, ?)";
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			// Set the placeholder ? variables
			pstmt.setString(1, user);
			pstmt.setString(2, pass);
			pstmt.setInt(3, 0);					// is_student = false
			pstmt.setInt(4, 0);					// is_instructor = false
			pstmt.setInt(5, 1);					// is_admin = true
			pstmt.setInt(6, 0);					// is_key = false
			pstmt.setInt(6, 0);					// is_account_updated = false
			pstmt.executeUpdate();				// Execute query
		}
		
		// Print results
		if(doesLoginExist(user, pass))
			System.out.println("user and pass successfully inserted into accounts database!");
		else 
			System.err.println("user and pass failed to be inserted into database");
	}

	
	/**********
	 * Creates the first account in accounts database with provided username and password.
	 * If there is already an account, it will exit the method and print an error message. DELETE working
	 */
	public static void createAccountWithKey(String user, String pass, String key) throws SQLException {
		
		// Prevents duplicate usernames
		if(doesUsernameExist(user)) {
			System.err.println("Cannot create account because username already exists");
			return;
		}
		// Prevents very long usernames
		if(user.length() > 50) {
			System.err.println("Cannot create account username is over 50 characters");
			return;
		}
		// Prevents very long passwords
		if(pass.length() > 50) {
			System.err.println("Cannot create account password is over 50 characters");
			return;
		}
		
		
		// Query inserts placeholder variables ? into accounts database where the password is a key and matches key param
		String query = "UPDATE accounts "
				+ "SET username = ?, password = ?, is_key = false, is_account_updated = false "
				+ "WHERE is_key = true AND password = ?";
		
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			// Set the placeholder ? variables
			pstmt.setString(1, user);
			pstmt.setString(2, pass);
			pstmt.setString(3, key);
			pstmt.executeUpdate();				// Execute query
		}
		
		// Print results
		if(doesLoginExist(user, pass))
			System.out.println("user and pass successfully inserted into accounts database!");
		else 
			System.err.println("user and pass failed to be inserted into database");
	}

	
	/**********
	 * Updates accounts database column values in an existing row where the username matches the given user String.
	 * If username already exists, it will exit the method and print an error message. DELETE working
	 */
	public static void updateAccountInformation(String user, String email, String firstName, String middleName, 
			String lastName, String prefName) throws SQLException{
		
		// Checks if username doesn't exist
		if(!doesUsernameExist(user)) {
			System.err.println("Cannot update account, username does not exist in accounts database");
			return;
		}
		// Checks if email exists
		if(doesEmailExist(email)) {
			System.err.println("Cannot update account, email already exists in database");
			return;
		}
		// Prevents very long emails
		if(email.length() > 50) {
			System.err.println("Cannot update account, email is over 50 characters");
			return;
		}
		// Prevents very long first names
		if(firstName.length() > 50) {
			System.err.println("Cannot update account, first name is over 50 characters");
			return;
		}
		// Prevents very long middle names
		if(middleName.length() > 50) {
			System.err.println("Cannot update account, middle name is over 50 characters");
			return;
		}
		// Prevents very long last names
		if(lastName.length() > 50) {
			System.err.println("Cannot update account, last name is over 50 characters");
			return;
		}
		// Prevents very long preferred names
		if(prefName != null && prefName.length() > 50) {
			System.err.println("Cannot update account, preferred name is over 50 characters");
			return;
		}
		
		String query;
		// Set query WITHOUT preferred name
		if(prefName == null || prefName == "") {
			query = "UPDATE accounts "			// update accounts database
					+ "SET email = ?, first_name = ?, middle_name = ?, last_name = ?, display_name = ?, is_account_updated = true "
					+ "WHERE username = ?";					// Update info where username column = placeholder variable ?
		}
		// Set query WITH preferred name
		else {
			query = "UPDATE accounts "			// update accounts database
					+ "SET email = ?, first_name = ?, middle_name = ?, last_name = ?, preferred_name = ?, display_name = ?, is_account_updated = true "
					+ "WHERE username = ?";					// Update info where username column = placeholder variable ?
		}
		
		
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			// Set the placeholder ? variables
			pstmt.setString(1, email);
			pstmt.setString(2, firstName);
			pstmt.setString(3, middleName);
			pstmt.setString(4, lastName);
			
			// Set placeholder variables WITHOUT preferred name
			if(prefName == null || prefName == "") {
				pstmt.setString(5, firstName);			// Use first name as display name and leave preferred name empty
				pstmt.setString(6, user);				// Update the above info at the row where username = user
			}
			// Set placeholder variables WITH preferred name
			else {										// If preferred name is given:
				pstmt.setString(5, prefName);			// Set preferred name column
				pstmt.setString(6, prefName);			// Use preferred name as display name
				pstmt.setString(7, user);				// Update the above info at the row where username = user
			}
			
			pstmt.executeUpdate();						// execute query
		}
		System.out.println("Successfully updated " + user + "'s account!");
	}

	
	/**********************************************************************************************

	Private Methods
	
	**********************************************************************************************/
	
	/**********
	 * Creates a table called accounts and initializes columns
	 * From Instructor's DatabaseHelper class DELETE working
	 */
	private void createTables() throws SQLException {
		query = "CREATE TABLE IF NOT EXISTS accounts ("
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
				+ "is_account_updated BIT,"
				+ "expiration TIMESTAMP)";			// TIMESTAMP is an exact date and time (when key expires)
		statement.execute(query);
	}
	
	
	// DELETE for testing
	private void createFullAccountTester(String user, String pass, String email, 
			boolean isStudent, boolean isInstructor, boolean isAdmin, boolean isKey) throws SQLException {
		
		// Prevents duplicate usernames
		if(doesUsernameExist(user)) {
			System.err.println("Cannot create account because username already exists");
			return;
		}
		// Prevents duplicate emails
		if(doesEmailExist(email)) {
			System.err.println("Cannot create account because email already exists");
			return;
		}
		// Prevents very long usernames
		if(user.length() > 50) {
			System.err.println("Cannot create account username is over 50 characters");
			return;
		}
		// Prevents very long passwords
		if(pass.length() > 50) {
			System.err.println("Cannot create account password is over 50 characters");
			return;
		}
		// Prevents very long emails
		if(email.length() > 50) {
			System.err.println("Cannot create account email is over 50 characters");
			return;
		}
			
		
		
		query = "INSERT INTO accounts (username, password, email, is_student, is_instructor, is_admin, is_key) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, user);
			pstmt.setString(2, pass);
			pstmt.setString(3, email);
			
			if(isStudent) pstmt.setInt(4, 1);
			else pstmt.setInt(4, 0);
			
			if(isInstructor) pstmt.setInt(5, 1);
			else pstmt.setInt(5, 0);
			
			if(isAdmin) pstmt.setInt(6, 1);
			else pstmt.setInt(6, 0);
			
			if(isKey) pstmt.setInt(7, 1);
			else pstmt.setInt(7, 0);
			
			pstmt.executeUpdate();
		}
	}
	
	
	// DELETE for testing
	private void printAccounts() throws SQLException{
		// get entire accounts table
		query = "SELECT * FROM accounts"; 
		statement = connection.createStatement();
		resultSet = statement.executeQuery(query); 
		
		System.out.println("Printing Accounts: ");

		// while the row exists
		while(resultSet.next()) { 
			// Retrieve by column name 
			String user = resultSet.getString("username"); 
			String pass = resultSet.getString("password"); 
			String email = resultSet.getString("email"); 
			String fName = resultSet.getString("first_name");
			String mName = resultSet.getString("middle_name");
			String lName = resultSet.getString("last_name");
			String pName = resultSet.getString("preferred_name");
			String dName = resultSet.getString("display_name");
			int isKey = resultSet.getInt("is_key"); 

			// Display values 
			System.out.println("Account: ");
			System.out.println("Username: " + user); 
			System.out.println("Password: " + pass); 
			System.out.println("Email: " + email); 
			System.out.println("First Name: " + fName); 
			System.out.println("Middle Name: " + mName); 
			System.out.println("Last Name: " + lName); 
			System.out.println("Preferred Name: " + pName); 
			System.out.println("Display Name: " + dName); 
			System.out.println("isKey: " + isKey); 
		} 
	}
	
}

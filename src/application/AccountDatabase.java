package application;

import java.sql.*; // For SQL related objects
import java.util.Random; // For random key generator

/**
 * <p> AccountDatabase. </p>
 * 
 * <p> Description: Manages the H2 database which stores all account information.</p>
 * 
 * <p> Source: Lynn Rober Carter from FirstDatabase project, DatabaseHelper class, 
 * 				available at: https://canvas.asu.edu/courses/193728/files/92728837?module_item_id=14758007
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		10/9/2024 Phase 1 implementation and documentation
 *  
 */

public class AccountDatabase {
	
	// JDBC driver name and database URL 
	private static final String JDBC_DRIVER = "org.h2.Driver";   
	private static final String DB_URL = "jdbc:h2:~/accountDatabase"; 

	//  Database credentials 
	private static final String USER = "sa"; 
	private static final String PASS = ""; 

	// Reusable variables to communicate with database
	private static String query = "";
	private static Connection connection = null;
	private static Statement statement = null; 
	private static ResultSet resultSet = null;
	
	// For random key generator
	private final String KEY_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_#$@";
	private final int KEY_LENGTH = 15;
	private Random random;
	
	
	/**********************************************************************************************

	Private Methods For Database Connection
	
	**********************************************************************************************/
	
	
	/**********
	 * Starts the connection to the H2 database.
	 */
	public static void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection Successful!");
			statement = connection.createStatement(); 
			
			createTable();  // Create accounts database if it doesn't exist on local machine
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
	
	
	/**********************************************************************************************

	Public Getter Methods To Check Database Values
	
	**********************************************************************************************/
	
	
	/**********
	 * Checks if there is at least one row of data in database.
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
	 * Checks if the given username and password exist in the same row in the database.
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
	 * Checks if the given key exists and is_key is true in the database.
	 */
	public static boolean doesKeyExist(String key) throws SQLException {
		// Query gets all data in accounts database where is_key and password equal placeholder variable ?
		query = "SELECT * FROM accounts WHERE is_key = true AND password = ?";
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			pstmt.setString(1, key);				// password = key
			
			resultSet = pstmt.executeQuery();		// ResultSet is now positioned before first row
			return resultSet.next();				// Returns if first row exists or not			
		}
	}
	
	
	/**********
	 * Checks if a given username exists in the database.
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
	 * Checks if a given email exists in the accounts database.
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
	 * Checks if a given username has the student role.
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
	 * Checks if a given username has the instructor role
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
	 * Checks if a given username has the admin role.
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
	 * Checks if a given username has updated their account information.
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
	 * If there is already an account, it will exit the method and print an error message.
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
			System.out.println("First user and pass successfully inserted into accounts database!");
		else 
			System.err.println("First user and pass failed to be inserted into database");
	}

	
	/**********
	 * Updates account username and password to the account corresponding to the given key in database.
	 * If key doesn't exist in database, or username already exists, it will exit with error message.
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
		
		
		// Query inserts placeholder variables ? into accounts database 
		// where the password is a key and matches key param
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
			System.out.println("New user and pass successfully inserted into accounts database!");
		else 
			System.err.println("New user and pass failed to be inserted into database");
	}

	
	/**********
	 * Updates additional account information to the account corresponding to the given username in database.
	 * If username doesn't exist, or email already exists, it will exit and print an error message.
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
					+ "WHERE username = ?";		// Update info where username column = placeholder variable ?
		}
		// Set query WITH preferred name
		else {
			query = "UPDATE accounts "			// update accounts database
					+ "SET email = ?, first_name = ?, middle_name = ?, last_name = ?, preferred_name = ?, display_name = ?, is_account_updated = true "
					+ "WHERE username = ?";		// Update info where username column = placeholder variable ?
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
		System.out.println("Successfully updated " + user + "'s account");
	}
	
	
	/**********
	 * Adds a random unique key and user roles into database, then returns the key.
	 */
	public String inviteUser(boolean isStudent, boolean isInstructor, boolean isAdmin) throws SQLException{
		
		// generate random unique key
		String key = generateKey();
		
		// Query inserts placeholder variables ? for below columns into a new row in accounts database
		query = "INSERT INTO accounts (password, is_student, is_instructor, is_admin, is_key, is_account_updated, expiration) VALUES (?, ?, ?, ?, ?, ?, ?)";
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			// Set the placeholder ? variables
			pstmt.setString(1, key);				// password = key
			pstmt.setInt(2, isStudent ? 1 : 0);		// is_student = isStudents
			pstmt.setInt(3, isInstructor ? 1 : 0);	// is_instructor = isInstructor
			pstmt.setInt(4, isAdmin ? 1 : 0);		// is_admin = isAdmin
			pstmt.setInt(5, 1);						// is_key = true
			pstmt.setInt(6, 0);						// is_account_updated = false
			// expiration = current Day:Month:Year Hour:Minute:Second
			pstmt.setTimestamp(6, new java.sql.Timestamp(new java.util.Date().getTime()));
			pstmt.executeUpdate();					// Execute query
		}
		
		// Print results
		if(doesKeyExist(key)) {
			System.out.println("Key and roles successfully added to database!");
			return key;
		}
		else  {
			System.err.println("Key and roles failed to be added to database");
			return "";
		}
	}

	
	/**********************************************************************************************

	Private Methods
	
	**********************************************************************************************/
	
	/**********
	 * Creates a table called accounts and initializes columns.
	 */
	private static void createTable() throws SQLException {
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
		System.out.println("'accounts' table created if it did not already exist");
	}
	
	
	/**********
	 * Deletes the entire accounts table in database
	 */
	private void deleteTable() throws SQLException {
		query = "DROP TABLE accounts";		// delete accounts table
		statement.execute(query);			// execute query
		System.out.print("'accounts' table deleted");
	}
	
	
	/**********
	 * Generates a random key with a-z, A-z, and 0-9 characters of length 15
	 */
	private String generateKey() throws SQLException{
		String newKey = "";
		random = new Random();
		int randValue;
		
		// Loop while the newKey is empty or already exists in database to prevent duplicate keys
		while(newKey == "" || doesKeyExist(newKey)) {
			
			newKey = "";	// Reset key
			
			// Loop length is length of key
			for(int i = 0; i < KEY_LENGTH; i++) {
				
				// generates random int from 0 to length of KEY_CHARS - 1
				randValue = random.nextInt(KEY_CHARS.length());
				// add the character at randValue location in string to the newKey
				newKey += KEY_CHARS.substring(randValue, randValue + 1);
			}
		}
		
		return newKey;	// successfully generated unique key
	}
	
	
	/**********
	 * Deletes the entire accounts table in database
	 */
	private void testDatabase() throws SQLException {
		deleteTable();
		createTable();
		createFirstAccount("First user", "123");	// test first account creation
		createFirstAccount("Second account", "123");
		
		//createAccountTest("Bob", "123", "Bob@gmail.com", true, false, false, false);
	}
	
	
	/**********
	 * Creates a new account in account database for testing purposes.
	 */
	private void createAccountTest(String user, String pass, String email, 
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
			
		// Query creates a new row in database with provided parameters
		query = "INSERT INTO accounts (username, password, email, is_student, is_instructor, is_admin, is_key) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, user);
			pstmt.setString(2, pass);
			pstmt.setString(3, email);
			
			if(isStudent) pstmt.setInt(4, 1);		// is_student = true
			else pstmt.setInt(4, 0);				// is_student = false
			
			if(isInstructor) pstmt.setInt(5, 1);	// is_instructor = true
			else pstmt.setInt(5, 0);				// is_instructor = false
			
			if(isAdmin) pstmt.setInt(6, 1);			// is_admin = true
			else pstmt.setInt(6, 0);				// is_admin = false
			
			if(isKey) pstmt.setInt(7, 1);			// is_key = true
			else pstmt.setInt(7, 0);				// is_key = false
			
			pstmt.executeUpdate();					// execute query
		}
		System.out.println("Created test account for '" + user + "'");
	}
	
	
	/**********
	 * Prints all account information for all accounts in account database for testing purposes.
	 */
	private void printAccountsTest() throws SQLException{
		// get entire accounts table
		query = "SELECT * FROM accounts"; 
		statement = connection.createStatement();
		resultSet = statement.executeQuery(query); 
		
		System.out.println("----------------------------------------------------");
		System.out.println("Printing All Accounts: \n");
		System.out.println("----------------------------------------------------");

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
			System.out.println("----------------------------------------------------\n");
		} 
	}

}

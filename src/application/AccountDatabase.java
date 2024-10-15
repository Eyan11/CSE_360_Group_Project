package application;

import java.sql.*; // For SQL related objects
import java.util.Random; // For random key generator
import java.util.concurrent.TimeUnit; // For time used in expiration column

/**
 * <p> AccountDatabase. </p>
 * 
 * <p> Description: Manages the H2 database which stores all account information.</p>
 * 
 * <p> Source: Lynn Robert Carter from FirstDatabase project, DatabaseHelper class, 
 * 				available at: https://canvas.asu.edu/courses/193728/files/92728837?module_item_id=14758007
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		10/09/2024 Phase 1 implementation and documentation
 * @version 1.10		10/15/2024 Updated to include all functionality of phase 1
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
	private static final String KEY_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_#$@";
	private static final int KEY_LENGTH = 15;
	private static Random random;
	
	
	/**********************************************************************************************

	Public Methods For Database Connection
	
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
			
			// Wipe all stored database rows on local machine and create a new accounts table
			createTable();
			deleteTable();
			createTable();
			
			// Perform tests on all methods in this class
			DatabaseTestingAutomation.performTestEvaluations();
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

	Public Getter Methods To Check/Return Database Values
	
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
		query = "SELECT * FROM accounts WHERE username = ? AND password = ? AND is_key = false";
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
	
	
	/**********
	 * Checks if current time is past the expiration time for a given key
	 * Returns true if expired OR key/expiration timestamp does not exist in account database
	 */
	public static boolean isKeyExpired(String key) throws SQLException{
		// Prevents using method if key does NOT exist
		if(!doesKeyExist(key)) {
			System.err.println("Cannot check if key is expired because key does not exist in account database");
			return true;
		}
		
		// Query gets all data in accounts database where is_key is true and password equals placeholder variable ?
		query = "SELECT expiration FROM accounts WHERE is_key = true AND password = ?";
		
		Timestamp key_expiration = null;
		// Get current timestamp
		Timestamp cur_time = new Timestamp(System.currentTimeMillis());
		
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setString(1, key);				// password = key
			
			resultSet = pstmt.executeQuery();		// ResultSet is now positioned before first row
			if(resultSet.next()) {					// If first row exists			
				key_expiration = resultSet.getTimestamp(1);	// Get expiration date of first row
				
				// Prevent checking timestamp of null value
				if(key_expiration == null) {
					System.err.println("Can't check if key is expired because expiration timestamp is null");
					return true;
				}
			}
		}
		
		// Return results
		if(key_expiration.after(cur_time))
			return false;	// key is NOT expired
		else
			return true;	// key is expired
	}
	
	
	/**********
	 * Returns the expiration date of a given key
	 */
	public static String getKeyExpiration(String key) throws SQLException {
		// Prevents using method if key does NOT exist
		if(!doesKeyExist(key)) {
			System.err.println("Cannot get expiration date because key does not exist in account database");
			return "";
		}
		
		// Query gets all data in accounts database where is_key is true and password equals placeholder variable ?
		query = "SELECT expiration FROM accounts WHERE is_key = true AND password = ?";
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			pstmt.setString(1, key);				// password = key
			
			resultSet = pstmt.executeQuery();		// ResultSet is now positioned before first row
			if(resultSet.next()) {					// If first row exists			
				return resultSet.getString(1);		// Return expiration date of first row
			}
		}
		return "";									// If error occurred, assume query failed
	}
	
	
	/**********
	 * Returns the username and display name of every account
	 * in format of "username1|display_name1,username2|display_name2,..."
	 */
	public static String getAllAccountNames() throws SQLException {
		
		// Counts total number of rows in accounts database
		query = "SELECT username, display_name FROM accounts";
		resultSet = statement.executeQuery(query);
		
		// To build a string containing all accounts username and display name
		String returnString = "";
		
		// Loop through every row
		while (resultSet.next()) {
			// Get username and separate with |
			returnString += resultSet.getString("username") + "|";
			
			// Get display name if NOT null
			if(resultSet.getString("display_name") != null)
				returnString += resultSet.getString("display_name");
			
			// Separate each account with ,
			returnString += ",";
		}
		
		// Return in the format of "username1|display_name1,username2|display_name2,..."
		return returnString;
	}
	
	/**********************************************************************************************

	Public Setter Methods To Update or Add Accounts to Database
	
	**********************************************************************************************/
	
	
	/**********
	 * Creates the first account in accounts database with provided username and password.
	 * If there is already an account, it will exit the method and print an error message.
	 */
	public static boolean createFirstAccount(String user, String pass) throws SQLException {
		
		// Prevents using method when not the first account in database
		if(!isDatabaseEmpty()) {
			System.err.println("Can't create first account, database is NOT empty");
			return false;
		}
		// Prevents very long usernames
		if(user.length() > 50) {
			System.err.println("Cannot create account username is over 50 characters");
			return false;
		}
		// Prevents very long passwords
		if(pass.length() > 50) {
			System.err.println("Cannot create account password is over 50 characters");
			return false;
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
			pstmt.setInt(7, 0);					// is_account_updated = false
			pstmt.executeUpdate();				// Execute query
		}
		
		// Print results
		if(doesLoginExist(user, pass)) {
			System.out.println("First user and pass successfully inserted into accounts database!");
			return true;
		}
		else {
			System.err.println("First user and pass failed to be inserted into database");
			return false;
		}
	}

	
	/**********
	 * Updates account username and password to the account corresponding to the given key in database.
	 * If key doesn't exist in database, or username already exists, it will exit with error message.
	 */
	public static boolean createAccountWithKey(String user, String pass, String key) throws SQLException {
		
		// Prevents duplicate usernames
		if(doesUsernameExist(user)) {
			System.err.println("Cannot create account because username already exists");
			return false;
		}
		// Prevents very long usernames
		if(user.length() > 50) {
			System.err.println("Cannot create account username is over 50 characters");
			return false;
		}
		// Prevents very long passwords
		if(pass.length() > 50) {
			System.err.println("Cannot create account password is over 50 characters");
			return false;
		}
		
		
		// Query inserts placeholder variables ? into accounts database 
		// where the password is a key and matches key param
		String query = "UPDATE accounts "
				+ "SET username = ?, password = ?, is_key = false, is_account_updated = false, expiration = ? "
				+ "WHERE is_key = true AND password = ?";
		
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			// Set the placeholder ? variables
			pstmt.setString(1, user);
			pstmt.setString(2, pass);
			pstmt.setTimestamp(3, null);		// Remove expiration time stamp
			pstmt.setString(4, key);
			pstmt.executeUpdate();				// Execute query
		}
		
		// Print results
		if(doesLoginExist(user, pass)) {
			System.out.println("New user and pass successfully inserted into accounts database!");
			return true;
		}
		else {
			System.err.println("New user and pass failed to be inserted into database");
			return false;
		}
	}

	
	/**********
	 * Updates additional account information to the account corresponding to the given username in database.
	 * If username doesn't exist, or email already exists, it will exit and print an error message.
	 */
	public static boolean updateAccountInformation(String user, String email, String firstName, String middleName, 
			String lastName, String prefName) throws SQLException{
		
		// Checks if username doesn't exist
		if(!doesUsernameExist(user)) {
			System.err.println("Cannot update account, username does not exist in accounts database");
			return false;
		}
		// Checks if email exists
		if(doesEmailExist(email)) {
			System.err.println("Cannot update account, email already exists in database");
			return false;
		}
		// Prevents very long emails
		if(email.length() > 50) {
			System.err.println("Cannot update account, email is over 50 characters");
			return false;
		}
		// Prevents very long first names
		if(firstName.length() > 50) {
			System.err.println("Cannot update account, first name is over 50 characters");
			return false;
		}
		// Prevents very long middle names
		if(middleName.length() > 50) {
			System.err.println("Cannot update account, middle name is over 50 characters");
			return false;
		}
		// Prevents very long last names
		if(lastName.length() > 50) {
			System.err.println("Cannot update account, last name is over 50 characters");
			return false;
		}
		// Prevents very long preferred names
		if(prefName != null && prefName.length() > 50) {
			System.err.println("Cannot update account, preferred name is over 50 characters");
			return false;
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
			System.out.println("Successfully updated " + user + "'s account");
			return true;
		}
	}
	
	
	/**********
	 * Adds a random unique key and user roles into database, then returns the key.
	 * Returns an empty string when invite failed.
	 */
	public static String inviteUser(boolean isStudent, boolean isInstructor, boolean isAdmin) throws SQLException{
		
		// Prevent an account from having no roles
		if(!isStudent && !isInstructor && !isAdmin) {
			System.err.println("Cannot invite user because new user must have at least 1 role");
			return "";
		}
		
		// Prevent an account from having BOTH student and instructor roles
		if(isStudent && isInstructor) {
			System.err.println("Cannot invite user because new user cannot have BOTH student and instructor role");
			return "";
		}
		
		// generate random unique key
		String key = generateKey();
		
		// Query inserts placeholder variables ? for below columns into a new row in accounts database
		query = "INSERT INTO accounts (username, password, is_student, is_instructor, "
				+ "is_admin, is_key, is_account_updated, expiration) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			// Set the placeholder ? variables
			pstmt.setString(1, key);				// username = key (since username can't be null and has to be unique)
			pstmt.setString(2, key);				// password = key
			pstmt.setInt(3, isStudent ? 1 : 0);		// is_student = isStudents
			pstmt.setInt(4, isInstructor ? 1 : 0);	// is_instructor = isInstructor
			pstmt.setInt(5, isAdmin ? 1 : 0);		// is_admin = isAdmin
			pstmt.setInt(6, 1);						// is_key = true
			pstmt.setInt(7, 0);						// is_account_updated = false
			// expiration = current time + 10 min in format: YYYY-MM-DD HH:MI:SS
			pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)));
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


	/**********
	 * Replaces a user's password with a one-time key, then returns the key
	 * Returns an empty string when reset failed
	 */
	public static String resetUser(String user) throws SQLException {
		// Checks if username doesn't exist
		if(!doesUsernameExist(user)) {
			System.err.println("Cannot reset account, username does not exist in accounts database");
			return "";
		}
		
		
		// generate random unique key
		String key = generateKey();
		
		// Query inserts placeholder variables ? into accounts database 
		// where the username matches user parameter
		String query = "UPDATE accounts "
				+ "SET password = ?, is_key = true, expiration = ? "
				+ "WHERE username = ?";
		
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			// Set the placeholder ? variables
			pstmt.setString(1, key);			// set password = key
			// expiration = current time + 10 min in format: YYYY-MM-DD HH:MI:SS
			pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)));
			pstmt.setString(3, user);			// update account where username = user
			pstmt.executeUpdate();				// Execute query
		}
		
		// Print results
		if(doesKeyExist(key)) {
			System.out.println("User successfully reset in accounts database!");
			return key;
		}
		else {
			System.err.println("User failed to be reset in accounts database");
			return "";
		}
	}
	
	
	/**********
	 * Replaces a user's password with given password
	 */
	public static boolean resetPassword(String key, String pass) throws SQLException {
		// Checks if key doesn't exist
		if(!doesKeyExist(key)) {
			System.err.println("Cannot reset password, key does not exist in accounts database");
			return false;
		}
	
		
		// Query inserts placeholder variables ? into accounts database 
		// where the password matches key parameter
		String query = "UPDATE accounts "
				+ "SET password = ?, is_key = false, expiration = null "
				+ "WHERE password = ?";
		
		// Prepare the previous query to be executed
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			
			// Set the placeholder ? variables
			pstmt.setString(1, pass);			// Set password = pass
			pstmt.setString(2, key);			// Update account where password = key
			pstmt.executeUpdate();				// Execute query
		}
		
		// Print results
		if(!doesKeyExist(key)) {
			System.out.println("Successfully reset password in accounts database!");
			return true;
		}
		else {
			System.err.println("Failed to reset password in accounts database");
			return false;
		}
	}
	
	
	/**********
	 * Removes a user's account from database
	 */
	public static boolean deleteUser(String user) throws SQLException {
		// Checks if username doesn't exist
		if(!doesUsernameExist(user)) {
			System.err.println("Cannot delete account, username does not exist in accounts database");
			return false;
		}

		
		// Query inserts placeholder variables ? into accounts database 
		// where the username matches user parameter
		String query = "DELETE FROM accounts WHERE username = ?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		// Set the placeholder ? variables
		pstmt.setString(1, user);	// Delete account where username = user
		pstmt.executeUpdate();		// Execute query
		
		// Print results
		if(!doesUsernameExist(user)) {
			System.out.println("Successfully deleted account");
			return true;
		}
		else {
			System.err.println("Failed to delete account");
			return false;
		}	
	}
	
	
	/**********************************************************************************************

	 Public Methods To Create/Delete 'accounts' Table and Print All Accounts
	
	**********************************************************************************************/
	
	
	
	/**********
	 * Creates a table called accounts and initializes columns.
	 */
	public static void createTable() throws SQLException {
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
				+ "expiration TIMESTAMP)";			// TIMESTAMP is when key expires (Format: YYYY-MM-DD HH:MI:SS)
		statement.execute(query);
		System.out.println("'accounts' table created if it did not already exist");
	}
	
	
	/**********
	 * Deletes the entire accounts table in database
	 */
	public static void deleteTable() throws SQLException {
		query = "DROP TABLE accounts";		// delete accounts table
		statement.execute(query);			// execute query
		System.out.println("'accounts' table deleted");
	}
	
	/**********
	 * Prints all account information for all accounts in account database for testing purposes.
	 */
	public static void printAccountsToConsole() throws SQLException{
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
			int isStudent = resultSet.getInt("is_student");
			int isInstructor = resultSet.getInt("is_instructor");
			int isAdmin = resultSet.getInt("is_admin");
			int isAccountUpdated = resultSet.getInt("is_account_updated");
			Timestamp expiration = resultSet.getTimestamp("expiration");

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
			System.out.println("isStudent: " + isStudent); 
			System.out.println("isInstructor: " + isInstructor); 
			System.out.println("isAdmin: " + isAdmin); 
			System.out.println("isAccountUpdated: " + isAccountUpdated); 
			System.out.println("key expiration: " + expiration); 
			System.out.println("----------------------------------------------------\n");
		} 
	}
	
	
	/**********************************************************************************************

	 Private Helper Methods
	
	**********************************************************************************************/
	
	
	/**********
	 * Generates a random key with a-z, A-z, and 0-9 characters of length 15
	 */
	private static String generateKey() throws SQLException{
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
}

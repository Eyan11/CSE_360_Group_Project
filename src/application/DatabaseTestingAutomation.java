package application;

import java.sql.*; // For SQL related objects

public class DatabaseTestingAutomation {

	
	// Reusable variables to communicate with database
	private static String query = "";
	private static Connection connection = null;
	private static Statement statement = null; 
	private static ResultSet resultSet = null;
	
	private boolean actualResult;
	/** Total number of test cases that had matching expectations and results */
	private static int numPassed = 0;
	/** Total number of test cases that had different expectations and results */
	private static int numFailed = 0;
	
	private String[] keyArr = new String[3];
	private int i = 0;
	private String key = "";
	
	
	/**********
	 * Starts the testing automatotion
	 */
	private void performTestEvaluations() throws SQLException {
		// Reset accounts table
		AccountDatabase.deleteTable();
		AccountDatabase.createTable();
		
		// *** Test createFirstAccount() ***********************************
		testCreateFirstAccount("First user", "123", true);
		testCreateFirstAccount("Second account", "123", false);
		// *****************************************************************
		
		// *** Test inviteUser() *******************************************
		testInviteUser(true, false, false, true);
		testInviteUser(false, false, true, true);
		testInviteUser(false, true, true, true);
		testInviteUser(false, false, false, false);
		testInviteUser(true, true, false, false);
		testInviteUser(true, true, true, false);
		// *****************************************************************
		
		// *** Test doesKeyExist() *****************************************
		testDoesKeyExist(keyArr[0], true);
		testDoesKeyExist(keyArr[1], true);
		testDoesKeyExist("", false);
		testDoesKeyExist("crmc4324c7y23m4c087234SEKFJ", false);
		// *****************************************************************
		
		// *** Test createAccountWithKey() *********************************
		testCreateAccountWithKey("Name1", "123", keyArr[0], true);
		testCreateAccountWithKey("Name2", "123", keyArr[1], true);
		testCreateAccountWithKey("NotAdded", "123", keyArr[1], true);
		// *****************************************************************
		
		
		// Print Results
		System.out.println("-----------------------------------------------------------");
		System.out.println("\n\n\n Restults: ");
		System.out.println("Number of tests that passed: " + numPassed);
		System.out.println("Number of tests that failed" + numFailed);
	}
	
	
	/**********************************************************************************************

	Private Helper Methods To Test All Functionalities of AccountDatabase Class
	
	**********************************************************************************************/
	
	
	/**********
	 * Tests the functionality of the createFirstAccount() method in AccountDatabase class.
	 */
	private void testCreateFirstAccount(String user, String pass, boolean expectedResult) throws SQLException{
		
		// Attempt to create first account and get result
		actualResult = AccountDatabase.createFirstAccount(user, pass);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("createFirstAccount() passed!");
		}
		else {
			numFailed++;
			System.out.println("createFirstAccount() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the createAccountWithKey() method in AccountDatabase class.
	 */
	private void testCreateAccountWithKey(String user, String pass, String key, boolean expectedResult) throws SQLException{
		
		// Attempt to create account with a key and get result
		actualResult = AccountDatabase.createAccountWithKey(user, pass, key);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("createAccountWithKey() passed!");
		}
		else {
			numFailed++;
			System.out.println("createAccountWithKey() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the updateAccountInformation() method in AccountDatabase class.
	 */
	private void testUpdateAccountInformation(String user, String email, String firstName, String middleName, 
			String lastName, String prefName, boolean expectedResult) throws SQLException{
		
		// Attempt to update account information
		actualResult = AccountDatabase.updateAccountInformation(user, email, firstName, middleName, lastName, prefName);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("testUpdateAccountInformation() passed!");
		}
		else {
			numFailed++;
			System.out.println("testUpdateAccountInformation() failed!");
		}
	}
	

	/**********
	 * Tests the functionality of the inviteUser() method in AccountDatabase class.
	 */
	private void testInviteUser(boolean isStudent, boolean isInstructor, boolean isAdmin, boolean expectedResult) throws SQLException{
		
		// Attempt to invite user and use return key
		key = AccountDatabase.inviteUser(isStudent, isInstructor, isAdmin);
		
		// If no key then invite failed
		if(key == "")
			actualResult = false;
		// If key then invite succeeded
		else
			actualResult = true;
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("inviteUser() passed!");
			// Store key for future test cases
			keyArr[i] = key;
			i++;
		}
		else {
			numFailed++;
			System.out.println("inviteUser() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the isDatabaseEmpty() method in AccountDatabase class.
	 */
	private void testIsDatabaseEmpty(boolean expectedResult) throws SQLException{
		
		// Check if database is empty and return result
		actualResult = AccountDatabase.isDatabaseEmpty();
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("isDatabaseEmpty() passed!");
		}
		else {
			numFailed++;
			System.out.println("isDatabaseEmpty() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the doesLoginExist() method in AccountDatabase class.
	 */
	private void testDoesLoginExist(String user, String pass, boolean expectedResult) throws SQLException{
		
		// Check if login exists and return result
		actualResult = AccountDatabase.doesLoginExist(user, pass);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("doesLoginExist() passed!");
		}
		else {
			numFailed++;
			System.out.println("doesLoginExist() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the doesKeyExist() method in AccountDatabase class.
	 */
	private void testDoesKeyExist(String key, boolean expectedResult) throws SQLException{
		
		// Check if key exists and return result
		actualResult = AccountDatabase.doesKeyExist(key);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("doesKeyExist() passed!");
		}
		else {
			numFailed++;
			System.out.println("doesKeyExist() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the doesUsernameExist() method in AccountDatabase class.
	 */
	private void testDoesUsernameExist(String user, boolean expectedResult) throws SQLException{
		
		// Check if username exists and return result
		actualResult = AccountDatabase.doesUsernameExist(user);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("doesUsernameExist() passed!");
		}
		else {
			numFailed++;
			System.out.println("doesUsernameExist() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the doesEmailExist() method in AccountDatabase class.
	 */
	private void testDoesEmailExist(String email, boolean expectedResult) throws SQLException{
		
		// Check if email exists and return result
		actualResult = AccountDatabase.doesEmailExist(email);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("doesEmailExist() passed!");
		}
		else {
			numFailed++;
			System.out.println("doesEmailExist() failed!");
		}
	}
	

	/**********
	 * Tests the functionality of the isStudentRole() method in AccountDatabase class.
	 */
	private void testIsStudentRole(String user, boolean expectedResult) throws SQLException{
		
		// Check user has student role and return result
		actualResult = AccountDatabase.isStudentRole(user);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("isStudentRole() passed!");
		}
		else {
			numFailed++;
			System.out.println("isStudentRole() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the isInstructorRole() method in AccountDatabase class.
	 */
	private void testIsInstructorRole(String user, boolean expectedResult) throws SQLException{
		
		// Check user has instructor role and return result
		actualResult = AccountDatabase.isInstructorRole(user);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("isInstructorRole() passed!");
		}
		else {
			numFailed++;
			System.out.println("isInstructorRole() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the isAdmin() method in AccountDatabase class.
	 */
	private void testIsAdmin(String user, boolean expectedResult) throws SQLException{
		
		// Check user has admin role and return result
		actualResult = AccountDatabase.isAdminRole(user);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("isAdminRole() passed!");
		}
		else {
			numFailed++;
			System.out.println("isAdminRole() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the isAccountUpdated() method in AccountDatabase class.
	 */
	private void testIsAccountUpdated(String user, boolean expectedResult) throws SQLException{
		
		// Check account has updated information and return result
		actualResult = AccountDatabase.isAccountUpdated(user);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("isAccountUpdated() passed!");
		}
		else {
			numFailed++;
			System.out.println("isAccountUpdated() failed!");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**********
	 * Creates a new account in account database for testing purposes.
	 */
	private void createAccountTest(String user, String pass, String email, 
			boolean isStudent, boolean isInstructor, boolean isAdmin, boolean isKey) throws SQLException {
		
		// Prevents duplicate usernames
		if(AccountDatabase.doesUsernameExist(user)) {
			System.err.println("Cannot create account because username already exists");
			return;
		}
		// Prevents duplicate emails
		if(AccountDatabase.doesEmailExist(email)) {
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

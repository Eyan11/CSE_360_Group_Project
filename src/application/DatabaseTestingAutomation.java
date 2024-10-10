package application;

import java.sql.*; // For SQL related objects

/**
 * <p> DatabaseTestingAutomation. </p>
 * 
 * <p> Description: Tests all functionalities of the AccountDatabase class.</p>
 * 
 * <p> Source: Lynn Robert Carter from PasswordEvaluatorTestbedWithGUI project, 
 * 				PasswordEvaluationTestingAutomation class, 
 * 				available at: https://canvas.asu.edu/courses/193728/assignments/5505672?module_item_id=14493167
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		10/9/2024 Phase 1 implementation and documentation
 *  
 */

public class DatabaseTestingAutomation {

	
	// Reusable variables to communicate with database
	private static String query = "";
	private static Connection connection = null;
	private static Statement statement = null; 
	private static ResultSet resultSet = null;
	
	// Temporary variable to store return value
	private static boolean actualResult;
	// Total number of test cases that had matching expectations and results
	private static int numPassed = 0;
	// Total number of test cases that had different expectations and results
	private static int numFailed = 0;
	
	// To store one-time keys in order to create accounts with them
	private static String[] keyArr = new String[3];
	private static int i = 0;
	private static String key = "";
	
	
	/**********
	 * Starts the testing automation
	 */
	public static void performTestEvaluations() throws SQLException {

		// Wipe all stored database rows on local machine and create a new accounts table
		AccountDatabase.createTable();
		AccountDatabase.deleteTable();
		AccountDatabase.createTable();
		
		// *** Test isDatabaseEmpty() **************************************
		testIsDatabaseEmpty(true);
		// *****************************************************************
		
		// *** Test createFirstAccount() ***********************************
		testCreateFirstAccount("Name1", "pass1", true);
		testCreateFirstAccount("Ignore1", "123", false); // Can't create first account if account already exists
		// *****************************************************************
		
		// *** Test isDatabaseEmpty() **************************************
		testIsDatabaseEmpty(false);
		// *****************************************************************
		
		// *** Test inviteUser() *******************************************
		testInviteUser(true, false, false, true); // this is username = Name2
		testInviteUser(false, true, true, true); // this is username = Name3
		testInviteUser(false, false, false, false); // No roles
		testInviteUser(true, true, false, false); // BOTH student and Instructor roles
		// *****************************************************************
		
		// *** Test doesKeyExist() *****************************************
		testDoesKeyExist(keyArr[0], true);
		testDoesKeyExist(keyArr[1], true);
		testDoesKeyExist("", false); // Wrong key
		testDoesKeyExist("crmc4324c7y23m4c087234SEKFJ", false); // Wrong key
		// *****************************************************************
		
		// *** Test createAccountWithKey() *********************************
		testCreateAccountWithKey("Name2", "pass2", keyArr[0], true);
		testCreateAccountWithKey("Name2", "123", keyArr[1], false); // Duplicate username
		testCreateAccountWithKey("Name3", "pass3", keyArr[1], true);
		testCreateAccountWithKey("Ignore", "123", "", false); // Wrong key
		testCreateAccountWithKey("Ignore", "123", "12310871241241KJSDFHSF", false); // Wrong key
		// *****************************************************************
		
		// *** Test updateAccountInformation() ****************************
		testUpdateAccountInformation("Name1", "email1", "fname", "mname", "lname", "pname", true);
		testUpdateAccountInformation("Ignore", "email1", "fname", "mname", "lname", "pname", false); // Username doesn't exist
		testUpdateAccountInformation("Name2", "email1", "fname", "mname", "lname", "pname", false); // Duplicate email
		testUpdateAccountInformation("Name2", "email2", "fname", "mname", "lname", "", true);
		// *****************************************************************
		
		// **** Test doesLoginExist() **************************************
		testDoesLoginExist("Name1", "pass1", true);
		testDoesLoginExist("Name2", "pass2", true);
		testDoesLoginExist("Ignore", "pass1", false); // Username is wrong
		testDoesLoginExist("Name3", "Ignore", false); // Password is wrong
		// *****************************************************************
		
		// *** Test doesUsernameExist() ************************************
		testDoesUsernameExist("Name1", true);
		testDoesUsernameExist("Name3", true);
		testDoesUsernameExist("Ignore", false); // Wrong username
		// *****************************************************************
		
		// **** Test doesEmailExist() **************************************
		testDoesEmailExist("email2", true);
		testDoesEmailExist("email3", true);
		testDoesEmailExist("Ignore", false); // Wrong email
		// *****************************************************************
		
		// *** Test isStudentRole() ****************************************
		testIsStudentRole("Name1", false); // Doesn't have student role
		testIsStudentRole("Name2", true);
		testIsStudentRole("Ignore", false); // Wrong username
		// *****************************************************************
		
		// *** Test isInstructorRole() *************************************
		testIsInstructorRole("Name1", false); // Doesn't have instructor role
		testIsInstructorRole("Name2", true);
		testIsInstructorRole("Ignore", false); // Wrong username
		// *****************************************************************
		
		// *** Test isAdminRole() ******************************************
		testIsAdminRole("Name1", true);
		testIsAdminRole("Name2", false); // Doesn't have admin role
		testIsAdminRole("Ignore", false); // Wrong username
		// *****************************************************************
		
		// *** Test isAccountUpdated() *************************************
		testIsAccountUpdated("Name1", true);
		testIsAccountUpdated("Name3", false);  // Account not updated
		testIsAccountUpdated("Ignore", false); // Wrong username
		// *****************************************************************
		
		// Print all accounts in database (not an actual test case, just to to check data in database)
		testPrintAccounts();
		
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
	private static void testCreateFirstAccount(String user, String pass, boolean expectedResult) throws SQLException{
		
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
	private static void testCreateAccountWithKey(String user, String pass, String key, boolean expectedResult) throws SQLException{
		
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
	private static void testUpdateAccountInformation(String user, String email, String firstName, String middleName, 
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
	private static void testInviteUser(boolean isStudent, boolean isInstructor, boolean isAdmin, boolean expectedResult) throws SQLException{
		
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
	private static void testIsDatabaseEmpty(boolean expectedResult) throws SQLException{
		
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
	private static void testDoesLoginExist(String user, String pass, boolean expectedResult) throws SQLException{
		
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
	private static void testDoesKeyExist(String key, boolean expectedResult) throws SQLException{
		
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
	private static void testDoesUsernameExist(String user, boolean expectedResult) throws SQLException{
		
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
	private static void testDoesEmailExist(String email, boolean expectedResult) throws SQLException{
		
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
	private static void testIsStudentRole(String user, boolean expectedResult) throws SQLException{
		
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
	private static void testIsInstructorRole(String user, boolean expectedResult) throws SQLException{
		
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
	 * Tests the functionality of the isAdminRole() method in AccountDatabase class.
	 */
	private static void testIsAdminRole(String user, boolean expectedResult) throws SQLException{
		
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
	private static void testIsAccountUpdated(String user, boolean expectedResult) throws SQLException{
		
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
	 * Prints all account information for all accounts in account database for testing purposes.
	 */
	private static void testPrintAccounts() throws SQLException{
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

package database;

import java.sql.*; // For SQL related objects

/**
 * <p> AccountDatabaseTesting. </p>
 * 
 * <p> Description: Tests all functionalities of the AccountDatabase class.</p>
 * 
 * <p> Source: Lynn Robert Carter from PasswordEvaluatorTestbedWithGUI project, 
 * 				PasswordEvaluationTestingAutomation class, 
 * 				available at: https://canvas.asu.edu/courses/193728/assignments/5505672?module_item_id=14493167
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		10/09/2024 Phase 1 implementation and documentation
 * @version 1.10		10/15/2024 Updated to test additional methods in AccountDatabase version 1.10
 *  
 */

public class AccountDatabaseTesting {

	
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
	private static String[] keyArr = new String[5];
	private static int i = 0;
	private static String returnString = "";
	
	
	/**********
	 * Starts the testing automation that test 59 test cases
	 */
	public static void performTestEvaluations() throws SQLException {
		
		// Wipe all stored database rows on local machine and create a new accounts table
		//AccountDatabase.createTable();
		//AccountDatabase.deleteTable();
		//AccountDatabase.createTable();
		
		// *** Test isDatabaseEmpty() **************************************
		testIsDatabaseEmpty(true);
		// *****************************************************************
		
		// *** Test getAllAccountNames() **********************************
		testGetAllAccountNames(false);	// No accounts exist
		// *****************************************************************
		
		// *** Test createFirstAccount() ***********************************
		testCreateFirstAccount("Name1", "pass1", true);
		testCreateFirstAccount("Ignore1", "123", false); // Can't create first account if account already exists
		// *****************************************************************
		
		// *** Test isDatabaseEmpty() **************************************
		testIsDatabaseEmpty(false);
		// *****************************************************************
		
		// *** Test inviteUser() *******************************************
		testInviteUser(true, false, false, true); // this is username = Name2 and keyArr[0]
		testInviteUser(false, true, true, true); // this is username = Name3 and keyArr[1]
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
		testDoesEmailExist("email1", true);
		testDoesEmailExist("Ignore", false); // Wrong email
		// *****************************************************************
		
		// *** Test isStudentRole() ****************************************
		testIsStudentRole("Name1", false); // Doesn't have student role
		testIsStudentRole("Name2", true);
		testIsStudentRole("Ignore", false); // Wrong username
		// *****************************************************************
		
		// *** Test isInstructorRole() *************************************
		testIsInstructorRole("Name1", false); // Doesn't have instructor role
		testIsInstructorRole("Name3", true);
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
		
		// *** Test resetUser() ********************************************
		//AccountDatabase.printAccountsToConsole();
		testResetUser("Name1", true);	// keyArr[2]
		testResetUser("Ignore", false);	// Username doesn't exist
		testResetUser("Name1", true);	// Allowed to reset user again (keyArr[3])
		// *****************************************************************
		
		// *** Test deleteUser() *******************************************
		testDeleteUser("Ignore", false);	// Username doesn't exist
		testDeleteUser("Name2", true);
		testDeleteUser("Name2", false);		// Username doesn't exist since just deleted it
		// *****************************************************************
		
		// *** Test isKeyExpired() *****************************************
		testIsKeyExpired(keyArr[3], false);	// Key exists but is not expired yet
		testIsKeyExpired("Ignore", true);	// Key doesn't exist (returns true when test fails)
		// *****************************************************************
		
		// *** Test getKeyExpiration() *************************************
		testGetKeyExpiration(keyArr[3], true);
		testGetKeyExpiration("Ignore", false);	// Key doesn't exist
		testGetKeyExpiration(keyArr[2], false);	// Key doesn't exist
		// *****************************************************************
		
		// *** Test resetPassword() ****************************************
		testResetPassword(keyArr[3], "new pass", true);
		testResetPassword(keyArr[3], "Ignore", false);	// Account doesn't have a key because it password was just reset
		testResetPassword("Ignore", "Ignore", false); // Key doesn't exist
		// *****************************************************************
		
		// *** Test getAllAccountNames() **********************************
		testGetAllAccountNames(true);
		// *****************************************************************
		
		
		// Print all accounts in database (not an actual test case, just to to check data in database)
		AccountDatabase.printAccountsToConsole();
		
		// Print Results
		System.out.println("-----------------------------------------------------------");
		System.out.println("\n\nRestults: ");
		System.out.println("Number of tests that passed: " + numPassed);
		System.out.println("Number of tests that failed: " + numFailed);
		
		// Reset accounts table for GUI usage
		AccountDatabase.deleteTable();
		AccountDatabase.createTable();
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
		returnString = AccountDatabase.inviteUser(isStudent, isInstructor, isAdmin);
		
		// If no key then invite failed
		if(returnString == "")
			actualResult = false;
		// If key then invite succeeded
		else {
			actualResult = true;
			// Store key for future test cases
			keyArr[i] = returnString;
			i++;
		}
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("inviteUser() passed!");
		}
		else {
			numFailed++;
			System.out.println("inviteUser() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the resetUser() method in AccountDatabase class.
	 */
	private static void testResetUser(String user, boolean expectedResult) throws SQLException{
		
		// Attempt to reset user and use return key
		returnString = AccountDatabase.resetUser(user);
		
		// If no key then reset failed
		if(returnString == "")
			actualResult = false;
		// If key then reset succeeded
		else {
			actualResult = true;
			// Store key for future test cases
			keyArr[i] = returnString;
			i++;
		}
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("resetUser() passed!");
		}
		else {
			numFailed++;
			System.out.println("resetUser() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the resetPassword() method in AccountDatabase class.
	 */
	private static void testResetPassword(String key, String pass, boolean expectedResult) throws SQLException{
		
		// Attempt to reset password and return result
		actualResult = AccountDatabase.resetPassword(key, pass);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("resetPassword() passed!");
		}
		else {
			numFailed++;
			System.out.println("resetPassword() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the deleteUser() method in AccountDatabase class.
	 */
	private static void testDeleteUser(String user, boolean expectedResult) throws SQLException{
		
		// Attempt to delete user and return result
		actualResult = AccountDatabase.deleteUser(user);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("deleteUser() passed!");
		}
		else {
			numFailed++;
			System.out.println("deleteUser() failed!");
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
	 * Tests the functionality of the isKeyExpired() method in AccountDatabase class.
	 * Also returns true when test fails
	 */
	private static void testIsKeyExpired(String key, boolean expectedResult) throws SQLException{
		
		// Check if current time stamp is past key expiration time stamp
		actualResult = AccountDatabase.isKeyExpired(key);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("isKeyExpired() passed!");
		}
		else {
			numFailed++;
			System.out.println("isKeyExpired() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the getKeyExpiration() method in AccountDatabase class.
	 */
	private static void testGetKeyExpiration(String key, boolean expectedResult) throws SQLException{
		
		// Return key expiration time stamp
		returnString = AccountDatabase.getKeyExpiration(key);
		
		// If no expiration time stamp
		if(returnString == "" || returnString == null)
			actualResult = false;
		// If method returned an expiration time stamp
		else
			actualResult = true;
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("getKeyExpiration() passed!");
		}
		else {
			numFailed++;
			System.out.println("getKeyExpiration() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the getAllAccountNames() method in AccountDatabase class.
	 */
	private static void testGetAllAccountNames(boolean expectedResult) throws SQLException{
		
		// Return usernames and display names of all accounts
		returnString = AccountDatabase.getAllAccountNames();
		
		// If empty string then no accounts or test failed
		if(returnString == "" || returnString == null)
			actualResult = false;
		// If string is NOT empty
		else
			actualResult = true;
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("getAllAccountNames() passed!");
		}
		else {
			numFailed++;
			System.out.println("getAllAccountNames() failed!");
		}
	}
}


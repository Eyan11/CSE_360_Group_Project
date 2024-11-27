package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import database.AccountDatabase;	// To test AccountDatabase methods
import database.DatabaseManager;	// TO start and close database connection

class AccountDatabaseTesting {

	/**********************************************************************************************

	Test AccountDatabase Getter Methods
	
	**********************************************************************************************/
	/**********
	 * Opens connection to database before all testing starts and initializes non-group database
	 */
	@BeforeAll
	private static void initialization() {
		DatabaseManager.connectToDatabase();	// Start database
	}
	
	/**********
	 * Closes connection to database when all testing is finished
	 */
	@AfterAll
	public static void cleanup() {
		DatabaseManager.closeConnection();
	}
	
	@Test
	void testIsTableEmpty() {
        AccountDatabase.deleteAllAccounts();
        
        // TEST 1 - no accounts exist in database should return true
        assertTrue(AccountDatabase.isTableEmpty(), "Test 1");

        // TEST 2 - table no longer empty after a user is added should return false
        AccountDatabase.createFirstAccount("username", "password");
        assertFalse(AccountDatabase.isTableEmpty(), "Test 2");

        // TEST 3 - remove all account and test if the table is empty after that should return true
        AccountDatabase.deleteAllAccounts();
        assertTrue(AccountDatabase.isTableEmpty(), "Test 3");
	}
	
	@Test
	void testDoesLoginExist() {
        AccountDatabase.deleteAllAccounts();

        // TEST 1 - Account Database is empty
        assertFalse(AccountDatabase.doesLoginExist("jim", "123"), "Test 1");

        // TEST 2 - Account Does Not Exist (Password is incorrect + user name is correct)
        AccountDatabase.createFirstAccount("jim", "123"); // Create an account 'jim' w/ password '123'
        assertFalse(AccountDatabase.doesLoginExist("jim", "12"), "Test 2");

        // TEST 3 - Account Does Not Exist (User name is Incorrect + password is incorrect)
        assertFalse(AccountDatabase.doesLoginExist("jimmy", "123"), "Test 3");

        // TEST 4 - Account Does exist
        assertTrue(AccountDatabase.doesLoginExist("jim", "123"), "Test 4");
	}
	
	@Test
	void testDoesKeyExist() {
        AccountDatabase.deleteAllAccounts();

        // TEST 1 - Account Database is empty
        assertFalse(AccountDatabase.doesKeyExist("123"), "Test 1");

        // TEST 2 - Key is incorrect
        String key = AccountDatabase.inviteUser(false, false, true);
        String incorrectKey = key + "f";
        assertFalse(AccountDatabase.doesKeyExist(incorrectKey), "Test 2");

        // TEST 3 - Key is correct
        assertTrue(AccountDatabase.doesKeyExist(key), "Test 3");
	}
	
	@Test
	void testDoesUsernameExist() {
        AccountDatabase.deleteAllAccounts();

        // TEST 1 - Account Database is empty
        assertFalse(AccountDatabase.doesUsernameExist("jim"), "Test 1");

        // TEST 2 - User name does not exist
        AccountDatabase.createFirstAccount("jim", "123"); // Create an account 'jim' w/ password '123'
        assertFalse(AccountDatabase.doesUsernameExist("jimmy"), "Test 2");

        // TEST 3 - User name does exist
        assertTrue(AccountDatabase.doesUsernameExist("jim"), "Test 3");
	}
	
	@Test
	void testDoesEmailExist() {
        AccountDatabase.deleteAllAccounts();
        
        // TEST 1 -  adding a user with email and see if its in database , it should be
        AccountDatabase.createFirstAccount("cadonisawesome", "password");
        AccountDatabase.updateAccountInformation("cadonisawesome", "cadon@email.edu", "firstname", "middlename", "lastname", "Preferred");
        assertTrue(AccountDatabase.doesEmailExist("cadon@email.edu"), "Test 1 ");

        // TEST 2 - check if a non existent email exists (it shouldn't)
        assertFalse(AccountDatabase.doesEmailExist("fake_email@gmail.com"), "Test 2");
	}
	
	@Test
	void testIsStudentRole() {
		AccountDatabase.deleteAllAccounts();

		//TEST 1 - Add student role and test if student
		String key = AccountDatabase.inviteUser(true, false, false);
	    AccountDatabase.createAccountWithKey("userS", "pass", key);
	    assertTrue(AccountDatabase.isStudentRole("userS"), "Test 1");
	
        //TEST 2 - Testing if Not a student
		key = AccountDatabase.inviteUser(false, true, false);
	    AccountDatabase.createAccountWithKey("userI", "pass", key);
        assertFalse(AccountDatabase.isStudentRole("userI"), "Test 2");

        //TEST 3 - Testing user with Student and Admin Role
        key = AccountDatabase.inviteUser(true, false, true);
        AccountDatabase.createAccountWithKey("userSA", "pass", key);
        assertTrue(AccountDatabase.isStudentRole("userSA"), "Test 3");
	}
	
	@Test
	void testIsInstructorRole() {
		AccountDatabase.deleteAllAccounts();

		//TEST 1 - Add Instructor role and test if Instructor
		String key = AccountDatabase.inviteUser(false, true, false);
	    AccountDatabase.createAccountWithKey("userI", "pass", key);
	    assertTrue(AccountDatabase.isInstructorRole("userI"), "Test 1");
	
        //TEST 2 - Testing if not an Instructor
		key = AccountDatabase.inviteUser(true, false, false);
	    AccountDatabase.createAccountWithKey("userS", "pass", key);
        assertFalse(AccountDatabase.isInstructorRole("userS"), "Test 2");

        //TEST 3 - Testing user with Admin and Instructor Role
        key = AccountDatabase.inviteUser(false, true, true);
        AccountDatabase.createAccountWithKey("userAI", "pass", key);
        assertTrue(AccountDatabase.isInstructorRole("userAI"), "Test 3");
	}
	
	@Test
	void testIsAdminRole() {
		AccountDatabase.deleteAllAccounts();

		//TEST 1 - Add admin role and test if admin
        AccountDatabase.createFirstAccount("userA", "pass");
        assertTrue(AccountDatabase.isAdminRole("userA"), "Test 1"); 
	
        //TEST 2 - Test if Not an Admin
        String key = AccountDatabase.inviteUser(true, false, false);
	    AccountDatabase.createAccountWithKey("userS", "pass", key);
        assertFalse(AccountDatabase.isAdminRole("userS"), "Test 2");

        //TEST 3
        key = AccountDatabase.inviteUser(false, true, true);
        AccountDatabase.createAccountWithKey("userAI", "pass", key);
        assertTrue(AccountDatabase.isAdminRole("userAI"), "Test 3");
	}
	
	@Test
	void testIsAccountUpdated() {
        AccountDatabase.deleteAllAccounts();
        
        // Test 1 - create account and test BEFORE update (no update yet, should be FALSE)
        AccountDatabase.createFirstAccount("userA", "pass");
        assertFalse(AccountDatabase.isAccountUpdated("userA"), "Test 1"); 
        
        // Test 2 - test account AFTER update (account updated, should be TRUE)
        AccountDatabase.updateAccountInformation("userA", "email", "firstName", "middleName", "lastName", "prefName");
        assertTrue(AccountDatabase.isAccountUpdated("userA"), "Test 2"); 
        
        // Test 3 - create another account, userB, then check for update (no update yet, should be FALSE)
        String key2 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userB", "pass", key2);
        assertFalse(AccountDatabase.isAccountUpdated("userB"), "Test 3"); 
        
        // Test 4 - test account AFTER update (account updated, should be TRUE)
        AccountDatabase.updateAccountInformation("userB", "email2", "firstName2", "middleName2", "lastName2", "prefName2");
        assertTrue(AccountDatabase.isAccountUpdated("userB"), "Test 4"); 
	}
	
	@Test
	void testIsKeyExpired() {
		AccountDatabase.deleteAllAccounts();
		assertTrue(true);	// TODO
	}
	
	@Test
	void testGetExpiration() {
		AccountDatabase.deleteAllAccounts();
		assertTrue(true);	// TODO
	}
	
	@Test
	void testGetAllAccounts() {
		AccountDatabase.deleteAllAccounts();
		assertTrue(true);	// TODO
	}
	
	/**********************************************************************************************

	Test AccountDatabase Setter Methods
	
	**********************************************************************************************/
	
	@Test
	void testCreateFirstAccount() {
        AccountDatabase.deleteAllAccounts();
        
        // Test 1 - test if account is created (not created yet, should be FALSE)
        assertTrue(AccountDatabase.createFirstAccount("userA", "passA"), "Test 1"); 
        
        // Test 2 - create account and test BEFORE update (no update yet, should be FALSE)
        assertFalse(AccountDatabase.createFirstAccount("userB", "passB"), "Test 2"); 
        
        // Test 3 - create account and test BEFORE update (no update yet, should be FALSE)
        AccountDatabase.deleteAllAccounts(); // Needed for tests 3 and 4, as we are testing first account parameters, not the first account itself
        assertFalse(AccountDatabase.createFirstAccount(
        		"userCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC", "passC"), "Test 3"); // Testing user length

        // Test 3 - create account and test BEFORE update (no update yet, should be FALSE)
        AccountDatabase.deleteAllAccounts(); // Needed for tests 3 and 4, as we are testing first account parameters, not the first account itself
        assertFalse(AccountDatabase.createFirstAccount(
        		"userD", "passDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"), "Test 4"); // Testing pass length
	}
	
	@Test
	void testCreateAccountWithKey() {
		AccountDatabase.deleteAllAccounts();
		assertTrue(true);	// TODO
	}
	
	@Test
	void testUpdateAccountInformation() {
        AccountDatabase.deleteAllAccounts();
        
        // Test 1 - All input is correct (should return TRUE)
        AccountDatabase.createFirstAccount("userA", "passA"); 
        assertTrue(AccountDatabase.updateAccountInformation(
        		"userA", "email", "firstName", "middleName", "lastName", "prefName"), "Test 1");
        
        // Test 2 - Test method with username that doesn't exist (and all other unique info), should return FALSE
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userB", "email2", "firstName2", "middleName2", "lastName2", "prefName2"), "Test 2");
        
        // Test 3 - Test method with email that already exists (should return FALSE)
        String key = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userC", "passC", key);
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userC", "email", "firstName3", "middleName3", "lastName3", "prefName3"), "Test 3");
                
        // Test 4 - Create account and test method with email > 50 characters (should return FALSE)
        String key2 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userD", "passD", key2);
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userD", "email4444444444444444444444444444444444444444444444", "firstName4", 
                "middleName4", "lastName4", "prefName4"), "Test 4");
        
        // Test 5 - Create account and test method with firstName > 50 characters (should return FALSE)
        String key3 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userE", "passE", key3); 
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userE", "email5", "firstName555555555555555555555555555555555555555555", "middleName5", 
                "lastName5", "prefName5"), "Test 5");
        
        // Test 6 - Create account and test method with lastName > 50 characters (should return FALSE)
        String key4 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userF", "passF", key4); 
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userF", "email6", "firstName6", "middleName66666666666666666666666666666666666666666", 
                "lastName6", "prefName6"), "Test 6");
        
        // Test 7 - Create account and test method with middleName > 50 characters (should return FALSE)
        String key5 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userG", "passG", key5); 
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userG", "email7", "firstName7", "middleName7", 
                "lastName7777777777777777777777777777777777777777777", "prefName7"), "Test 7");
        
        // Test 8 - Create account and test method with prefName > 50 characters (should return FALSE)
        String key6 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userH", "passH", key6); 
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userH", "email8", "firstName8", "middleName8", 
                "lastName8", "prefName8888888888888888888888888888888888888888888"), "Test 8");
        
        // Test 9 - All input is correct, but prefName is null (should return TRUE)
        String key7 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userI", "passI", key7);
        assertTrue(AccountDatabase.updateAccountInformation(
        		"userI", "email9", "firstName9", "middleName9", "lastName9", null), "Test 9");
	}
	
	@Test
	void testUpdateUserRoles() {
		AccountDatabase.deleteAllAccounts();
		assertTrue(true);	// TODO
	}
	
	@Test
	void testInviteUser() {
		AccountDatabase.deleteAllAccounts();
		assertTrue(true);	// TODO
	}
	
	@Test
	void testResetUser() {
		AccountDatabase.deleteAllAccounts();
		assertTrue(true);	// TODO
	}
	
	@Test
	void testResetPassword() {
		AccountDatabase.deleteAllAccounts();
		assertTrue(true);	// TODO
	}
	
	@Test
	void testDeleteUser() {
		AccountDatabase.deleteAllAccounts();
		assertTrue(true);	// TODO
	}
	
}

package testing;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import database.AccountDatabase;	// To test AccountDatabase methods
import database.DatabaseManager;	// TO start and close database connection

/**
 * <p> AccountDatabaseTesting. </p>
 * 
 * <p> Description: Uses JUnit test cases to test all public methods in the AccountDatabase class. </p>
 * 
 * @author Eyan Martucci, Julio Salazar, Cadon Duong, Sriram Nesan, and Evan Espinosa
 * 
 * @version 1.00		12/3/2024 Phase 4 implementation and documentation
 *  
 */

class AccountDatabaseTesting {
	
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
	
	
	/**********************************************************************************************

	Test AccountDatabase Getter Methods
	
	**********************************************************************************************/
	
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

		// TEST 1 - Add student role and test if student
		String key = AccountDatabase.inviteUser(true, false, false);
	    AccountDatabase.createAccountWithKey("userS", "pass", key);
	    assertTrue(AccountDatabase.isStudentRole("userS"), "Test 1");
	
        // TEST 2 - Testing if Not a student
		key = AccountDatabase.inviteUser(false, true, false);
	    AccountDatabase.createAccountWithKey("userI", "pass", key);
        assertFalse(AccountDatabase.isStudentRole("userI"), "Test 2");

        // TEST 3 - Testing user with Student and Admin Role
        key = AccountDatabase.inviteUser(true, false, true);
        AccountDatabase.createAccountWithKey("userSA", "pass", key);
        assertTrue(AccountDatabase.isStudentRole("userSA"), "Test 3");
	}
	
	@Test
	void testIsInstructorRole() {
		AccountDatabase.deleteAllAccounts();

		// TEST 1 - Add Instructor role and test if Instructor
		String key = AccountDatabase.inviteUser(false, true, false);
	    AccountDatabase.createAccountWithKey("userI", "pass", key);
	    assertTrue(AccountDatabase.isInstructorRole("userI"), "Test 1");
	
        // TEST 2 - Testing if not an Instructor
		key = AccountDatabase.inviteUser(true, false, false);
	    AccountDatabase.createAccountWithKey("userS", "pass", key);
        assertFalse(AccountDatabase.isInstructorRole("userS"), "Test 2");

        // TEST 3 - Testing user with Admin and Instructor Role
        key = AccountDatabase.inviteUser(false, true, true);
        AccountDatabase.createAccountWithKey("userAI", "pass", key);
        assertTrue(AccountDatabase.isInstructorRole("userAI"), "Test 3");
	}
	
	@Test
	void testIsAdminRole() {
		AccountDatabase.deleteAllAccounts();

		// TEST 1 - Add admin role and test if admin
        AccountDatabase.createFirstAccount("userA", "pass");
        assertTrue(AccountDatabase.isAdminRole("userA"), "Test 1"); 
	
        // TEST 2 - Test if Not an Admin
        String key = AccountDatabase.inviteUser(true, false, false);
	    AccountDatabase.createAccountWithKey("userS", "pass", key);
        assertFalse(AccountDatabase.isAdminRole("userS"), "Test 2");

        // TEST 3
        key = AccountDatabase.inviteUser(false, true, true);
        AccountDatabase.createAccountWithKey("userAI", "pass", key);
        assertTrue(AccountDatabase.isAdminRole("userAI"), "Test 3");
	}
	
	@Test
	void testIsAccountUpdated() {
        AccountDatabase.deleteAllAccounts();
        
        // TEST 1 - create account and test BEFORE update (no update yet, should be FALSE)
        AccountDatabase.createFirstAccount("userA", "pass");
        assertFalse(AccountDatabase.isAccountUpdated("userA"), "Test 1"); 
        
        // TEST 2 - test account AFTER update (account updated, should be TRUE)
        AccountDatabase.updateAccountInformation("userA", "email", "firstName", "middleName", "lastName", "prefName");
        assertTrue(AccountDatabase.isAccountUpdated("userA"), "Test 2"); 
        
        // TEST 3 - create another account, userB, then check for update (no update yet, should be FALSE)
        String key2 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userB", "pass", key2);
        assertFalse(AccountDatabase.isAccountUpdated("userB"), "Test 3"); 
        
        // TEST 4 - test account AFTER update (account updated, should be TRUE)
        AccountDatabase.updateAccountInformation("userB", "email2", "firstName2", "middleName2", "lastName2", "prefName2");
        assertTrue(AccountDatabase.isAccountUpdated("userB"), "Test 4"); 
	}
	
	@Test
	void testIsKeyExpired() {
		AccountDatabase.deleteAllAccounts();
		
		// TEST 1 - inviting a student
		String key = AccountDatabase.inviteUser(true, false, false);
		assertFalse(AccountDatabase.isKeyExpired(key), "Test 1");
		
		// TEST 2 - inviting an instructor
		key = AccountDatabase.inviteUser(false, true, false);
		assertFalse(AccountDatabase.isKeyExpired(key), "Test 2");
		
		// TEST 3 - inviting an admin
		key = AccountDatabase.inviteUser(false, false, true);
		assertFalse(AccountDatabase.isKeyExpired(key), "Test 3");
		
		// TEST 4 - empty key (assumes key is expired if error occurs)
		assertTrue(AccountDatabase.isKeyExpired(""), "Test 4");
		
		// TEST 5 - key is too long (assumes key is expired if error occurs)
		assertTrue(AccountDatabase.isKeyExpired("123456789012345 Too Long"), "Test 5");
		
		// TEST 6 - key doesn't exist (returns true if fails to authenticate key)
		assertTrue(AccountDatabase.isKeyExpired("KeyDoesNotExist"), "Test 6");
		
		// TEST 7 - key is removed since account is created (returns true if fails to authenticate key)
		AccountDatabase.createAccountWithKey("user", "pass", key);
		assertTrue(AccountDatabase.isKeyExpired(key), "Test 7");
		// Can't test if key actually expires since it would take 10 minutes
	}
	
	@Test
	void testGetKeyExpiration() {
		AccountDatabase.deleteAllAccounts();
		
		// NOTE: for all tests I am using substring 0 - 16 to cut off the seconds in the timestamp, 
		// because it takes more than one millisecond to run the code.
		// So it is only checking the format of yyyy-mm-dd hh:mm 
		
		// TEST 1 - inviting a student
		String key = AccountDatabase.inviteUser(true, false, false);
		String expirationString = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)).toString();
		assertEquals(expirationString.substring(0, 16), 
				AccountDatabase.getKeyExpiration(key).substring(0, 16), "Test 1");
		
		// TEST 2 - inviting an instructor
		key = AccountDatabase.inviteUser(false, true, false);
		expirationString = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)).toString();
		assertEquals(expirationString.substring(0, 16), 
				AccountDatabase.getKeyExpiration(key).substring(0, 16), "Test 2");
		
		// TEST 3 - key is empty
		assertEquals("", AccountDatabase.getKeyExpiration(""), "Test 3");
		
		// TEST 4 - key is too long
		assertEquals("", AccountDatabase.getKeyExpiration("123456789012345 Too Long"), "Test 4");
		
		// TEST 5 - key doesn't exist
		assertEquals("", AccountDatabase.getKeyExpiration("KeyDoesNotExist"), "Test 5");
		
		// TEST 6 - key is removed since account is created
		AccountDatabase.createAccountWithKey("user", "pass", key);
		assertEquals("", AccountDatabase.getKeyExpiration(key), "Test 6");
	}
	
	@Test
	void testGetAllAccounts() {
		AccountDatabase.deleteAllAccounts();
		
		// TEST 1 - no accounts
		assertEquals("", AccountDatabase.getAllAccounts(), "Test 1");
		
		// TEST 2 - first account (admin)
		AccountDatabase.createFirstAccount("user1", "pass");
		AccountDatabase.updateAccountInformation("user1", "email1", "Fname1", "Mname1", "Lname1", "Pname1");
		String returnString = "user1,Pname1,0,0,1";
		assertEquals(returnString, AccountDatabase.getAllAccounts(), "Test 2");
		
		// TEST 3 - second account (instructor)
		String key = AccountDatabase.inviteUser(false, true, false);
		AccountDatabase.createAccountWithKey("user2", "pass", key);
		AccountDatabase.updateAccountInformation("user2", "email2", "Fname2", "Mname2", "Lname2", "Pname2");
		returnString = "user1,Pname1,0,0,1|\nuser2,Pname2,0,1,0";
		assertEquals(returnString, AccountDatabase.getAllAccounts(), "Test 3");
		
		// TEST 4 - third account (student, no preferred name)
		key = AccountDatabase.inviteUser(true, false, false);
		AccountDatabase.createAccountWithKey("user3", "pass", key);
		AccountDatabase.updateAccountInformation("user3", "email3", "Fname3", "Mname3", "Lname3", "");
		returnString = "user1,Pname1,0,0,1|\nuser2,Pname2,0,1,0|\nuser3,Fname3,1,0,0";
		assertEquals(returnString, AccountDatabase.getAllAccounts(), "Test 4");
		
		// TEST 5 - delete first account
		AccountDatabase.deleteUser("user1");
		returnString = "user2,Pname2,0,1,0|\nuser3,Fname3,1,0,0";
		assertEquals(returnString, AccountDatabase.getAllAccounts(), "Test 5");
		
		// TEST 6 - edit second account
		AccountDatabase.updateUserRoles("user2", true, false, true);
		returnString = "user2,Pname2,1,0,1|\nuser3,Fname3,1,0,0";
		assertEquals(returnString, AccountDatabase.getAllAccounts(), "Test 6");
	}
	
	
	/**********************************************************************************************

	Test AccountDatabase Setter Methods
	
	**********************************************************************************************/
	
	@Test
	void testCreateFirstAccount() {
        AccountDatabase.deleteAllAccounts();
        
        // TEST 1 - test if account is created (not created yet, should be FALSE)
        assertTrue(AccountDatabase.createFirstAccount("userA", "passA"), "Test 1"); 
        
        // TEST 2 - create account and test BEFORE update (no update yet, should be FALSE)
        assertFalse(AccountDatabase.createFirstAccount("userB", "passB"), "Test 2"); 
        
        // TEST 3 - create account and test BEFORE update (no update yet, should be FALSE)
        AccountDatabase.deleteAllAccounts(); // Needed for tests 3 and 4, as we are testing first account parameters, not the first account itself
        assertFalse(AccountDatabase.createFirstAccount(
        		"userCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC", "passC"), "Test 3"); // Testing user length

        // TEST 3 - create account and test BEFORE update (no update yet, should be FALSE)
        AccountDatabase.deleteAllAccounts(); // Needed for tests 3 and 4, as we are testing first account parameters, not the first account itself
        assertFalse(AccountDatabase.createFirstAccount(
        		"userD", "passDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"), "Test 4"); // Testing pass length
	}
	
	@Test
	void testCreateAccountWithKey() {
		AccountDatabase.deleteAllAccounts();
		
		// TEST 1 - create first account (admin)
		String key = AccountDatabase.inviteUser(false, false, true);
		assertTrue(AccountDatabase.createAccountWithKey("user1", "pass", key));
		
		// TEST 2 - create second account (instructor)
		key = AccountDatabase.inviteUser(false, true, false);
		assertTrue(AccountDatabase.createAccountWithKey("user2", "pass", key));
		
		// TEST 3 - create third account (student)
		key = AccountDatabase.inviteUser(true, false, false);
		assertTrue(AccountDatabase.createAccountWithKey("user3", "pass", key));
		
		// TEST 4 - duplicate username
		key = AccountDatabase.inviteUser(true, false, false);
		assertFalse(AccountDatabase.createAccountWithKey("user1", "pass", key));
		
		// TEST 5 - empty username
		assertFalse(AccountDatabase.createAccountWithKey("", "pass", key));
		
		// TEST 6 - password is too long
		assertFalse(AccountDatabase.createAccountWithKey("user5", 
				"11111111112222222222333333333344444444445555555555 Too Long", key));
	}
	
	@Test
	void testUpdateAccountInformation() {
        AccountDatabase.deleteAllAccounts();
        
        // TEST 1 - All input is correct (should return TRUE)
        AccountDatabase.createFirstAccount("userA", "passA"); 
        assertTrue(AccountDatabase.updateAccountInformation(
        		"userA", "email", "firstName", "middleName", "lastName", "prefName"), "Test 1");
        
        // TEST 2 - Test method with username that doesn't exist (and all other unique info), should return FALSE
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userB", "email2", "firstName2", "middleName2", "lastName2", "prefName2"), "Test 2");
        
        // TEST 3 - Test method with email that already exists (should return FALSE)
        String key = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userC", "passC", key);
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userC", "email", "firstName3", "middleName3", "lastName3", "prefName3"), "Test 3");
                
        // TEST 4 - Create account and test method with email > 50 characters (should return FALSE)
        String key2 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userD", "passD", key2);
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userD", "email4444444444444444444444444444444444444444444444", "firstName4", 
                "middleName4", "lastName4", "prefName4"), "Test 4");
        
        // TEST 5 - Create account and test method with firstName > 50 characters (should return FALSE)
        String key3 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userE", "passE", key3); 
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userE", "email5", "firstName555555555555555555555555555555555555555555", "middleName5", 
                "lastName5", "prefName5"), "Test 5");
        
        // TEST 6 - Create account and test method with lastName > 50 characters (should return FALSE)
        String key4 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userF", "passF", key4); 
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userF", "email6", "firstName6", "middleName66666666666666666666666666666666666666666", 
                "lastName6", "prefName6"), "Test 6");
        
        // TEST 7 - Create account and test method with middleName > 50 characters (should return FALSE)
        String key5 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userG", "passG", key5); 
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userG", "email7", "firstName7", "middleName7", 
                "lastName7777777777777777777777777777777777777777777", "prefName7"), "Test 7");
        
        // TEST 8 - Create account and test method with prefName > 50 characters (should return FALSE)
        String key6 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userH", "passH", key6); 
        assertFalse(AccountDatabase.updateAccountInformation(
        		"userH", "email8", "firstName8", "middleName8", 
                "lastName8", "prefName8888888888888888888888888888888888888888888"), "Test 8");
        
        // TEST 9 - All input is correct, but prefName is null (should return TRUE)
        String key7 = AccountDatabase.inviteUser(false, true, false);     // Invite and add instructor user
        AccountDatabase.createAccountWithKey("userI", "passI", key7);
        assertTrue(AccountDatabase.updateAccountInformation(
        		"userI", "email9", "firstName9", "middleName9", "lastName9", null), "Test 9");
	}
	
	@Test
	void testUpdateUserRoles() {
		AccountDatabase.deleteAllAccounts();
		
		// TEST 1 - don't make any changes
		AccountDatabase.createFirstAccount("user1", "pass");		// create user1 account
		assertTrue(AccountDatabase.updateUserRoles("user1", false, false, true), "Test 1");
		
		// TEST 2 - switch to instructor
		assertTrue(AccountDatabase.updateUserRoles("user1", false, true, false), "Test 2");
		
		// TEST 3 - switch to student and admin
		assertTrue(AccountDatabase.updateUserRoles("user1", true, false, true), "Test 3");
		
		// TEST 4 - empty username
		assertFalse(AccountDatabase.updateUserRoles("", false, false, true), "Test 4");
		
		// TEST 5 - username too long
		assertFalse(AccountDatabase.updateUserRoles(
				"11111111112222222222333333333344444444445555555555 Too Long", false, false, true), "Test 5");
		
		// TEST 6 - username not found
		assertFalse(AccountDatabase.updateUserRoles("Not found", false, false, true), "Test 6");
		
		// TEST 7 - can't have no roles
		assertFalse(AccountDatabase.updateUserRoles("user1", false, false, false), "Test 7");
		
		// TEST 8 - can't have both student and instructor roles
		assertFalse(AccountDatabase.updateUserRoles("user1", true, true, false), "Test 8");
	}
	
	@Test
	void testInviteUser() {
		AccountDatabase.deleteAllAccounts();
		
		// inviteUser() cannot be directly tested because it returns a randomized key.
		// Instead testCreateAccountWithKey() is the best way to test it because it takes in the key as input, 
		//  and only returns true if key was in database. But this method will test when incorrect input is given.
		
		// TEST 1 - can't have no roles
		assertEquals("", AccountDatabase.inviteUser(false, false, false), "Test 1");
		
		// TEST 2 - can't be a student and instructor
		assertEquals("", AccountDatabase.inviteUser(true, true, false), "Test 2");
	}
	
	@Test
	void testResetUser() {
		AccountDatabase.deleteAllAccounts();
		
		// resetUser() cannot be directly tested because it returns a randomized key.
		// Instead testResetPassword() is the best way to test it because it takes in the key as input, 
		//  and only returns true if key was in database. But this method will test when incorrect input is given.
		
		// TEST 1 - empty username
		assertEquals("", AccountDatabase.resetUser(""), "Test 1");
		
		// TEST 2 - username is too long
		assertEquals("", AccountDatabase.resetUser(
				"11111111112222222222333333333344444444445555555555 Too Long"), "Test 2");
		
		// TEST 3 - username doesn't exist
		AccountDatabase.createFirstAccount("user", "pass");
		assertEquals("", AccountDatabase.resetUser("User Doesn't Exist"), "Test 3");
	}
	
	@Test
	void testResetPassword() {
		AccountDatabase.deleteAllAccounts();
		
		// TEST 1 - reset first account
		AccountDatabase.createFirstAccount("user1", "pass");					// create user1 account
		String key = AccountDatabase.resetUser("user1");						// reset user1 password
		assertTrue(AccountDatabase.resetPassword(key, "new pass"), "Test 1");
		
		// TEST 2 - reset second account
		key = AccountDatabase.inviteUser(true, false, false);					
		AccountDatabase.createAccountWithKey("user2", "pass", key);				// create user2 account
		key = AccountDatabase.resetUser("user2");								// reset user2 password
		assertTrue(AccountDatabase.resetPassword(key, "new pass"), "Test 2");
		
		// TEST 3 - key is empty
		key = AccountDatabase.resetUser("user2");								// reset user2 password
		assertFalse(AccountDatabase.resetPassword("", "new pass"), "Test 3");
		
		// TEST 4 - password is too long
		assertFalse(AccountDatabase.resetPassword(key, 
				"11111111112222222222333333333344444444445555555555 Too Long"), "Test 4");
		
		// TEST 5 - key doesn't exist
		assertFalse(AccountDatabase.resetPassword("KeyDoesNotExist", "new pass"), "Test 5");
	}
	
	@Test
	void testDeleteUser() {
		AccountDatabase.deleteAllAccounts();
		
		// TEST 1 - delete first user
		AccountDatabase.createFirstAccount("user1", "pass");				// create user1 account
        String key = AccountDatabase.inviteUser(false, true, false);
        AccountDatabase.createAccountWithKey("user2", "pass", key);			// create user2 account
        key = AccountDatabase.inviteUser(true, false, false);
        AccountDatabase.createAccountWithKey("user3", "pass", key);			// create user3 account
        assertTrue(AccountDatabase.deleteUser("user1"), "Test 1");
        
        // TEST 2 - delete second user
        assertTrue(AccountDatabase.deleteUser("user2"), "Test 2");
        
        // TEST 3 - username is empty
        assertFalse(AccountDatabase.deleteUser(""), "Test 3");
        
        // TEST 4 - username is too long
        assertFalse(AccountDatabase.deleteUser(
        		"11111111112222222222333333333344444444445555555555 Too Long"), "Test 4");
        
        // TEST 5 - username doesn't exist
        assertFalse(AccountDatabase.deleteUser("Name Doesn't Exist"), "Test 5");
	}
	
}

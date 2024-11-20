package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import database.AccountDatabase;		// To create accounts for testing
import database.DatabaseManager;		// To connect and close connection to H2 database
import database.GroupDatabase;
import database.HelpMessageDatabase;	// To test HelpMessageDatabase methods
import database.LoginTracker;
import java.text.SimpleDateFormat; 
import java.util.Date; 

/**
 * <p> HelpMessageDatabaseTesting. </p>
 * 
 * <p> Description: Uses JUnit test cases to test all public methods in the HelpMessageDatabase class.</p>
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		11/19/2024 Phase 3 implementation and documentation
 *  
 */

class HelpMessageDatabaseTesting {

	
	/**********
	 * Opens connection to database before all testing starts and initializes non-group database
	 */
	@BeforeAll
	private static void initialization() {
		DatabaseManager.connectToDatabase();	// Start database
		
		// Add admin user
		AccountDatabase.createFirstAccount("userA", "pass");
		
		// Inite and add instructor user
		String key = AccountDatabase.inviteUser(false, true, false);
		AccountDatabase.createAccountWithKey("userI", "pass", key);
		
		// Invite and add student user
		key = AccountDatabase.inviteUser(true, false, false);
		AccountDatabase.createAccountWithKey("userS", "pass", key);
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
		HelpMessageDatabase.deleteAllMessages();	// Wipe table
		
		// TEST 1
		assertTrue(HelpMessageDatabase.isTableEmpty(), "Test 1");

		// TEST 2
		GroupDatabase.createGroup("group1", "userS", false);
		HelpMessageDatabase.createGenericMessage("group1");	// Fill table
		assertFalse(HelpMessageDatabase.isTableEmpty(), "Test 2");
		
		// TEST 3
		HelpMessageDatabase.deleteAllMessages();	// Wipe table
		assertTrue(HelpMessageDatabase.isTableEmpty(), "Test 3");
	}
	
	
	@Test
	void testGetAllHelpMessages() {
		HelpMessageDatabase.deleteAllMessages();	// Wipe table
		LoginTracker.logout();
		
		// TEST 1 - Empty table
		assertEquals("", HelpMessageDatabase.getAllHelpMessages(), "Test 1");
		
		// TEST 2 - 1 group
		GroupDatabase.createGroup("group1", "userA", false);		// Create a general group 'group1'
		GroupDatabase.addUserToGroup("userS", "group1", true);		// Add userS to group1
		LoginTracker.login("userS");								// Login as userS
		HelpMessageDatabase.createGenericMessage("group1");			// Create generic message with group1
		assertEquals("2024-11-19\nMore help articles regarding the "
				+ "group1 group are requested.\n\n", 
				HelpMessageDatabase.getAllHelpMessages(), "Test 2");
			
		// TEST 3 - 2 groups
		GroupDatabase.createGroup("group2", "userA", true);			// Create special group 'group2'
		GroupDatabase.addUserToGroup("userS", "group2", true);		// Add userS to group2
		HelpMessageDatabase.createGenericMessage("group2");			// Create generic message with group2
		assertEquals("2024-11-19\nMore help articles regarding the "
				+ "group1 group are requested.\n\n"
				+ "2024-11-19\nMore help articles regarding the "
				+ "group2 group are requested.\n\n", 
				HelpMessageDatabase.getAllHelpMessages(), "Test 3");
	}
	
	//System.out.println("\n\nGet All Group Info: " + GroupDatabase.getAllGroupInfo());
	//System.out.println("\n\n\n TEST 1: \n\n\n\n");
	@Test
	void testCreateGenericMessage() {
		HelpMessageDatabase.deleteAllMessages();	// Wipe table
		LoginTracker.logout();
		
		// Test 1 - Create generic message with group1
		GroupDatabase.createGroup("group1", "userA", false);		// Create a general group 'group1'
		GroupDatabase.addUserToGroup("userS", "group1", true);		// Add userS to group1
		LoginTracker.login("userS");								// Login as userS
		assertTrue(HelpMessageDatabase.createGenericMessage("group1"), "Test 1");
		
		// Test 2 - Create generic message with group2
		GroupDatabase.createGroup("group2", "userA", true);			// Create special group 'group2'
		GroupDatabase.addUserToGroup("userS", "group2", true);		// Add userS to group2
		assertTrue(HelpMessageDatabase.createGenericMessage("group2"), "Test 2");
		
		// Test 3 - Group name doesn't exist
		assertFalse(HelpMessageDatabase.createGenericMessage("Ignore"), "Test 3");
		
		// Test 4 - Logged in user isn't in group1
		LoginTracker.login("userI");
		assertFalse(HelpMessageDatabase.createGenericMessage("group1"), "Test 4");
		
		// Test 5 - Nobody is logged in
		LoginTracker.logout();
		assertFalse(HelpMessageDatabase.createGenericMessage("Ignore"), "Test 5");
	}
	
	
	@Test
	void testCreateSpecific() {
		HelpMessageDatabase.deleteAllMessages();	// Wipe table
		LoginTracker.logout();
		
		// Test 1 - Create specific message 1
		LoginTracker.login("userS");								// Login as userS
		assertTrue(HelpMessageDatabase.createSpecificMessage("This is my first message"), "Test 1");
		
		// Test 2 - Create specific message 2
		assertTrue(HelpMessageDatabase.createSpecificMessage("This is my second message"), "Test 2");
		
		// Test 3 - Empty message
		assertFalse(HelpMessageDatabase.createGenericMessage(""), "Test 3");
		
		// Test 4 - Message too long (over 300 char)
		assertFalse(HelpMessageDatabase.createGenericMessage(""
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "Too Long"), "Test 4");
		
		// Test 5 - Nobody is logged in
		LoginTracker.logout();
		assertFalse(HelpMessageDatabase.createGenericMessage("Ignore"), "Test 5");
	}

}

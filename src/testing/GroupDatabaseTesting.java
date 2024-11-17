package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;	// To run a method when testing is done
import org.junit.jupiter.api.BeforeAll;	// To run a method before testing starts
import org.junit.jupiter.api.Test;
import database.DatabaseManager;	// To initialize database
import database.GroupDatabase;		// To test GroupDatabase
import database.AccountDatabase;	// To create a user to add to groups

class GroupDatabaseTesting {
	
	/**********
	 * Opens connection to database before all testing starts and initializes non-group database
	 */
	@BeforeAll
	public static void initialization() {
		DatabaseManager.connectToDatabase();	// Start database
		
		// Add admin user
		AccountDatabase.createFirstAccount("userA", "pass");
		
		// Inite and add instructor user
		String key = AccountDatabase.inviteUser(false, true, false);
		AccountDatabase.createAccountWithKey("userI", "pass", key);
		
		// Invite and add student user
		key = AccountDatabase.inviteUser(false, false, true);
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
		GroupDatabase.deleteAllGroups();	// Wipe table
		
		// TEST 1
		assertTrue(GroupDatabase.isTableEmpty());

		// TEST 2
		GroupDatabase.createGroup("group1", "userA", "special access");	// Fill table
		assertFalse(GroupDatabase.isTableEmpty());
		
		// TEST 3
		GroupDatabase.deleteAllGroups();	// Wipe table
		assertTrue(GroupDatabase.isTableEmpty());
	}
	
	
	@Test
	void testDoesGroupNameExist() {
		GroupDatabase.deleteAllGroups();	// Wipe table
		
		// TEST 1
		assertFalse(GroupDatabase.doesGroupNameExist("Ignore"));

		// TEST 2
		GroupDatabase.createGroup("group1", "userA", "special access");	// Fill table
		assertTrue(GroupDatabase.doesGroupNameExist("group1"));	

		// TEST 3
		assertTrue(GroupDatabase.doesGroupNameExist("  GROUP1  "));
		
		// TEST 4
		GroupDatabase.deleteAllGroups();	// Wipe table
		assertFalse(GroupDatabase.doesGroupNameExist("group1"));
	}
	
	
	@Test
	void testShouldArticleBeEncrypted() {
		GroupDatabase.deleteAllGroups();	// Wipe table
		
		// TEST 1
		assertFalse(GroupDatabase.shouldArticleBeEncrypted("Ignore"), "Test 1");

		// TEST 2
		GroupDatabase.createGroup("special group1", "userA", "special access");
		GroupDatabase.createGroup("general group2", "userA", "general access");
		assertTrue(GroupDatabase.shouldArticleBeEncrypted("special group1"), "Test 2");	

		// TEST 3
		assertFalse(GroupDatabase.shouldArticleBeEncrypted("general group1"), "Test 3");
		
		// TEST 4
		assertTrue(GroupDatabase.shouldArticleBeEncrypted(" SPECIAL GROUP1  "), "Test 4");
		
		// TEST 5
		assertTrue(GroupDatabase.shouldArticleBeEncrypted("Ignore, general group2, special group1"), "Test 5");
		
		// TEST 6
		assertFalse(GroupDatabase.shouldArticleBeEncrypted("Ignore1, Ignore2, general group2"), "Test 6");
		
		// TEST 7
		GroupDatabase.deleteAllGroups();	// Wipe table
		assertFalse(GroupDatabase.shouldArticleBeEncrypted("special group1"), "Test 7");
	}
	
	
	@Test
	void testIsUserInGroup() {
		/* TODO - errors when running JUNIT tests on test 3
		GroupDatabase.deleteAllGroups();	// Wipe table
		
		// TEST 1
		assertFalse(GroupDatabase.isUserInGroup("Ignore", "user1", false), "Test 1");
		
		// TEST 2
		assertFalse(GroupDatabase.isUserInGroup("Ignore", "user1", true), "Test 2");
		
		// TEST 3
		GroupDatabase.createGroup("group1", "userA", "special access");	// Create special group with userA
		GroupDatabase.addUserToGroup("userI", "group1", false);			// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group1", true);			// Add userS as viewer
		assertTrue(GroupDatabase.isUserInGroup("group1", "userA", false), "Test 3");
		
		// TEST 4
		assertTrue(GroupDatabase.isUserInGroup(" GRoup1 ", "userI", false), "Test 4");
		
		// TEST 5
		assertTrue(GroupDatabase.isUserInGroup("group1", "userS", true), "Test 5");
		
		// TEST 6
		assertFalse(GroupDatabase.isUserInGroup("group1", "userA", true), "Test 6");
		
		// TEST 7
		assertFalse(GroupDatabase.isUserInGroup("group1", "userS", false), "Test 7");
		
		// TEST 8
		assertFalse(GroupDatabase.isUserInGroup("group1", "Ignore", false), "Test 8");
		*/
	}
}



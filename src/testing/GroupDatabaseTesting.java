package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;	// To run a method when testing is done
import org.junit.jupiter.api.BeforeAll;	// To run a method before testing starts
import org.junit.jupiter.api.Test;
import database.DatabaseManager;	// To initialize database
import database.GroupDatabase;		// To test GroupDatabase
import database.LoginTracker;
import database.AccountDatabase;	// To create a user to add to groups
import database.ArticleDatabase;

/**
 * <p> GroupDatabaseTesting. </p>
 * 
 * <p> Description: Uses JUnit test cases to test all public methods in the GroupDatabase class.</p>
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		11/18/2024 Phase 3 implementation and documentation
 *  
 */

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
		GroupDatabase.deleteAllGroups();	// Wipe table
		
		// TEST 1
		assertTrue(GroupDatabase.isTableEmpty());

		// TEST 2
		GroupDatabase.createGroup("group1", "userA", true);	// Fill table
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
		GroupDatabase.createGroup("group1", "userA", true);	// Fill table
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
		LoginTracker.logout();
		
		// TEST 1
		assertFalse(GroupDatabase.shouldArticleBeEncrypted("Ignore"), "Test 1");

		// TEST 2
		GroupDatabase.createGroup("special group1", "userA", true);
		GroupDatabase.createGroup("general group2", "userA", false);
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
		GroupDatabase.deleteAllGroups();	// Wipe table
		LoginTracker.logout();
		
		// TEST 1 - empty table
		assertFalse(GroupDatabase.isUserInGroup("Ignore", "user1", false), "Test 1");
		
		// TEST 2
		GroupDatabase.createGroup("group1", "userA", true);			// Create special group with userA
		GroupDatabase.addUserToGroup("userI", "group1", false);		// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group1", true);		// Add userS as viewer
		assertTrue(GroupDatabase.isUserInGroup("group1", "userA", false), "Test 2");
		
		// TEST 3
		assertTrue(GroupDatabase.isUserInGroup(" GRoup1 ", "userI", false), "Test 3");
		
		// TEST 4
		assertTrue(GroupDatabase.isUserInGroup("group1", "userS", true), "Test 4");
		
		// TEST 5
		assertFalse(GroupDatabase.isUserInGroup("group1", "userA", true), "Test 5");
		
		// TEST 6
		assertFalse(GroupDatabase.isUserInGroup("group1", "userS", false), "Test 6");
		
		// TEST 7
		assertFalse(GroupDatabase.isUserInGroup("group1", "Ignore", false), "Test 7");
	}
	
	
	@Test
	void testHasRightsForAllGroups() {
		GroupDatabase.deleteAllGroups();	// Wipe table
		LoginTracker.logout();

		// TEST 1 - empty table
		assertFalse(GroupDatabase.hasRightsForAllGroups("Ignore", "user1", false), "Test 1");
		
		// TEST 2 - admin rights (1 group)
		GroupDatabase.createGroup("group1", "userA", false);		// Create general group with userA
		GroupDatabase.addUserToGroup("userI", "group1", false);		// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group1", true);		// Add userS as viewer
		GroupDatabase.createGroup("group2", "userA", true);			// Create special group with userA
		GroupDatabase.addUserToGroup("userI", "group2", false);		// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group2", true);		// Add userS as viewer
		GroupDatabase.createGroup("group3", "userA", true);			// Create special group with userA
		assertTrue(GroupDatabase.hasRightsForAllGroups("group1", "userA", true), "Test 2");
		
		// TEST 3 - viewing rights (1 group)
		assertTrue(GroupDatabase.hasRightsForAllGroups("group1", "userS", true), "Test 3");
		
		// TEST 4 - admin rights (2 groups)
		assertTrue(GroupDatabase.hasRightsForAllGroups("group1, group2", "userI", false), "Test 4");
		
		// TEST 5 - viewing rights (2 groups)
		assertTrue(GroupDatabase.hasRightsForAllGroups("group2 , Group1", "userS", true), "Test 5");
		
		// TEST 6 - no viewing rights (3 groups)
		assertFalse(GroupDatabase.hasRightsForAllGroups("group1,group2, group3", "userS", true), "Test 6");
		
		// TEST 7 - admin rights (3 groups)
		assertTrue(GroupDatabase.hasRightsForAllGroups("group1, group2, group3", "userA", true), "Test 7");
	}

	
	@Test
	void testGetGroupAdminsOrViewers() {
		GroupDatabase.deleteAllGroups();	// Wipe table
		LoginTracker.logout();

		// TEST 1 - empty table
		assertEquals("", GroupDatabase.getGroupAdminsOrViewers("Ignore", false), "Test 1");
		
		// TEST 2 - get viewers
		GroupDatabase.createGroup("group1", "userA", false);		// Create general group with userA
		GroupDatabase.addUserToGroup("userI", "group1", false);		// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group1", true);		// Add userS as viewer
		assertEquals("userS", GroupDatabase.getGroupAdminsOrViewers("GROUP1 ", true), "Test 2");
		
		// TEST 3 - get admins
		assertEquals("userA, userI", GroupDatabase.getGroupAdminsOrViewers("GROUP1 ", false), "Test 3");		
	}

	
	@Test
	void testGetAllAuthorizedGroupNames() {
		GroupDatabase.deleteAllGroups();	// Wipe table
		LoginTracker.logout();

		// TEST 1 - empty table
		assertEquals("", GroupDatabase.getAllAuthorizedGroupNames(false), "Test 1");
		
		// TEST 2 - get groups with admin rights for userA
		GroupDatabase.createGroup("group1", "userA", false);		// Create general group with userA
		GroupDatabase.addUserToGroup("userI", "group1", false);		// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group1", true);		// Add userS as viewer
		GroupDatabase.createGroup("group2", "userA", true);			// Create special group with userA
		GroupDatabase.addUserToGroup("userI", "group2", false);		// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group2", true);		// Add userS as viewer
		LoginTracker.login("userA");								// Login as UserA
		assertEquals("group1+group2", GroupDatabase.getAllAuthorizedGroupNames(false), "Test 2");
		
		// TEST 3 - get groups with viewer rights for userA (admins also have viewer rights)
		assertEquals("group1+group2", GroupDatabase.getAllAuthorizedGroupNames(true), "Test 3");	
		
		// TEST 4 - get groups with viewer rights for userS
		LoginTracker.login("userS");	// Login as UserS
		assertEquals("group1+group2", GroupDatabase.getAllAuthorizedGroupNames(true), "Test 4");	
		
		// TEST 5 - get groups with admin rights for userS
		assertEquals("", GroupDatabase.getAllAuthorizedGroupNames(false), "Test 5");	
	}
	
	
	@Test
	void testGetAllGroupInfo() {
		GroupDatabase.deleteAllGroups();	// Wipe table
		LoginTracker.logout();

		// TEST 1 - empty table
		assertEquals("", GroupDatabase.getAllGroupInfo(), "Test 1");
		
		// TEST 2 - get all group info for userA
		GroupDatabase.createGroup("group1", "userA", false);		// Create general group with userA
		GroupDatabase.addUserToGroup("userI", "group1", false);		// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group1", true);		// Add userS as viewer
		GroupDatabase.createGroup("group2", "userA", true);			// Create special group with userA
		GroupDatabase.addUserToGroup("userI", "group2", false);		// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group2", true);		// Add userS as viewer
		LoginTracker.login("userA");								// Login as userA (admin)
		LoginTracker.selectAdminRole();								// Use admin role
		assertEquals("Group: group1\nType: general access"
				+ "\nAdmins: userA, userI\nViewers: userS\n\nGroup: group2\nType: special access"
				+ "\nAdmins: userA, userI\nViewers: userS\n\n", GroupDatabase.getAllGroupInfo(), "Test 2");
		
		// TEST 3 - get all group info for userI (instructor can only see students)
		LoginTracker.login("userI");								// Login as userI (instructor)
		LoginTracker.selectInstructorRole();						// Use instructor role
		assertEquals("Group: group1\nType: general access"
				+ "\nViewers: userS\n\nGroup: group2\nType: special access"
				+ "\nViewers: userS\n\n", GroupDatabase.getAllGroupInfo(), "Test 3");

		// TEST 4 - get all group info for userS (student can't access this info)
		LoginTracker.login("userS");								// Login as users (student)
		LoginTracker.selectStudentRole();							// Use student role
		assertEquals("", GroupDatabase.getAllGroupInfo(), "Test 4");
	}
	
	
	@Test
	void testCreateGroup() {
		GroupDatabase.deleteAllGroups();	// Wipe table
		LoginTracker.logout();
		
		// TEST 1 - Create special access group
		assertTrue(GroupDatabase.createGroup("group1", "userA", true), "Test 1");
		
		// TEST 2 - Create general access group
		assertTrue(GroupDatabase.createGroup("group2", "userA", false), "Test 2");
		
		// TEST 3 - name is 0 characters characters
		assertFalse(GroupDatabase.createGroup("", "userA", false), "Test 3");
		
		// TEST 4 - name is over 50 characters
		assertFalse(GroupDatabase.createGroup("0000000000111111111122222222223333333333444444444450", "userA", false), "Test 4");
		
		// TEST 5 - Duplicate group name
		assertFalse(GroupDatabase.createGroup("group1", "userA", true), "Test 5");
		
		// TEST 6 - User doesn't exist
		assertFalse(GroupDatabase.createGroup("Ignore", "Ignore User", false), "Test 6");
		
		// TEST 7 - Can't add student as group admin
		assertFalse(GroupDatabase.createGroup("Ignore", "userS", false), "Test 7");
	}

	
	@Test
	void testAddUserToGroup() {
		GroupDatabase.deleteAllGroups();	// Wipe table
		LoginTracker.logout();
		
		// TEST 1 - Add userI to group1 as group viewer (which gets converted to group admin since first instructor)
		GroupDatabase.createGroup("group1", "userA", false);
		assertTrue(GroupDatabase.addUserToGroup("userI", "group1", true), "Test 1");
		
		// TEST 3 - Add userS to group1 as group viewer
		assertTrue(GroupDatabase.addUserToGroup("userS", "group1", true), "Test 3");
		
		// TEST 4 - Switch userI to group viewer
		assertTrue(GroupDatabase.addUserToGroup("userI", "group1", true), "Test 4");
		
		// TEST 5 - Switch userI back to group admin
		assertTrue(GroupDatabase.addUserToGroup("userI", "group1", false), "Test 5");
		
		// TEST 6 - Username doesn't exist
		assertFalse(GroupDatabase.addUserToGroup("Ignore", "group1", false), "Test 6");
		
		// TEST 7 - Can't add student as group admin
		assertFalse(GroupDatabase.addUserToGroup("userS", "group1", false), "Test 7");
		
		// TEST 8 - Group name doesn't exist
		assertFalse(GroupDatabase.addUserToGroup("userS", "Ignore", false), "Test 8");
		
		// TEST 9 - Instructors cannot add non students
		LoginTracker.login("userI");
		LoginTracker.selectInstructorRole();
		assertFalse(GroupDatabase.addUserToGroup("userI", "Ignore", true), "Test 9");
		
		// TEST 10 - Cannot add duplicate users
		LoginTracker.logout();
		assertFalse(GroupDatabase.addUserToGroup("userS", "group1", true), "Test 10");
	}
	
	
	@Test
	void testRemoveUserFromGroup() {
		GroupDatabase.deleteAllGroups();	// Wipe table
		LoginTracker.logout();
		
		// TEST 1 - Remove student
		GroupDatabase.createGroup("group1", "userA", false);		// Create general group with userA
		GroupDatabase.addUserToGroup("userI", "Group1", false);		// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group1", true);		// Add userS as viewer
		assertTrue(GroupDatabase.removeUserFromGroup("userS", "group1", true), "Test 1");
		
		System.out.println("\n\n\nTESTEST\n\n\n");
		
		// TEST 2 - Remove instructor
		assertTrue(GroupDatabase.removeUserFromGroup("userI", "group1", false), "Test 2");
		
		// TEST 3 - Can't remove last group admin
		assertFalse(GroupDatabase.removeUserFromGroup("userA", "group1", false), "Test 3");
		
		// TEST 4 - User doesn't exist
		GroupDatabase.addUserToGroup("userI", "Group1", false);		// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "group1", true);		// Add userS as viewer
		assertFalse(GroupDatabase.removeUserFromGroup("Ignore", "group1", false), "Test 4");
		
		// TEST 5 - Group name doesn't exist
		assertFalse(GroupDatabase.removeUserFromGroup("userI", "Ignore", false), "Test 5");
		
		// TEST 6 - Can't user from a list they are not in
		assertFalse(GroupDatabase.removeUserFromGroup("userS", "group1", false), "Test 6");
		
		// TEST 7 - Instructors can't remove non-students
		LoginTracker.login("userI");
		assertFalse(GroupDatabase.removeUserFromGroup("userI", "group1", false), "Test 7");
	}
}

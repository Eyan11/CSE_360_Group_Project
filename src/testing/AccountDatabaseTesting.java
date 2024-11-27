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
	private void testIsTableEmpty() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testDoesLoginExist() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testDoesKeyExist() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testDoesUsernameExist() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testDoesEmailExist() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testIsStudentRole() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testIsInstructorRole() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testIsAdminRole() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testIsAccountUpdated() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testIsKeyExpired() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testGetExpiration() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testGetAllAccounts() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	/**********************************************************************************************

	Test AccountDatabase Setter Methods
	
	**********************************************************************************************/
	
	@Test
	private void testCreateFirstAccount() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testCreateAccountWithKey() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testUpdateAccountInformation() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testUpdateUserRoles() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testInviteUser() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testResetUser() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testResetPassword() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testDeleteUser() {
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
}

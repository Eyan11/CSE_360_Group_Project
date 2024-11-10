package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import database.AccountDatabase;	// To test AccountDatabase methods
import database.DatabaseManager;	// TO start and close database connection

class AccountDatabaseTesting {

	/**********************************************************************************************

	Test AccountDatabase Getter Methods
	
	**********************************************************************************************/
	
	@Test
	private void testIsTableEmpty() {
		DatabaseManager.connectToDatabase();
		AccountDatabase.createTable();
		AccountDatabase.deleteAllAccounts();
		fail("Not yet implemented");
	}
	
	@Test
	private void testDoesLoginExist() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testDoesKeyExist() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testDoesUsernameExist() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testDoesEmailExist() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testIsStudentRole() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testIsInstructorRole() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testIsAdminRole() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testIsAccountUpdated() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testIsKeyExpired() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testGetExpiration() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testGetAllAccounts() {
		fail("Not yet implemented");
	}
	
	/**********************************************************************************************

	Test AccountDatabase Setter Methods
	
	**********************************************************************************************/
	
	@Test
	private void testCreateFirstAccount() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testCreateAccountWithKey() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testUpdateAccountInformation() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testUpdateUserRoles() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testInviteUser() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testResetUser() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testResetPassword() {
		fail("Not yet implemented");
	}
	
	@Test
	private void testDeleteUser() {
		fail("Not yet implemented");
	}
	
}

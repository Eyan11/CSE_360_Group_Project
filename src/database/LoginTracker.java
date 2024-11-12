package database;

/**
 * <p> LoginTracker. </p>
 * 
 * <p> Description: Stores basic information of currently logged in user.</p>
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		TODO
 *  
 */

public class LoginTracker {
	
	// Private variables to store logged in user info
	private boolean isLoggedIn = false;
	private String username = "";
	private String currentRole = "";	// If user has multiple roles
	
	
	/**********************************************************************************************

	Public Setter Methods
	
	**********************************************************************************************/
	
	
	/**********
	 * Gets and stores information about the logged in user from AccountDatabase
	 */
	public boolean login(String user) {
		
		// return false if given username doesn't exist
		if(!AccountDatabase.doesUsernameExist(user))
			return false;
		
		// Store all user info and return true
		isLoggedIn = true;
		username = user;
		return true;
	}
	
	/**********
	 * Clears the information about the logged in user
	 */
	public void logout() {
		// Clear all user info
		isLoggedIn = false;
		username = "";
		currentRole = "";
	}
	
	/**********
	 * Sets current role as student when user selects student role at select role page
	 */
	public void selectStudentRole() {
		currentRole = "student";
	}
	
	/**********
	 * Sets current role as instructor when user selects student role at select role page
	 */
	public void selectInstructorRole() {
		currentRole = "instructor";
	}
	
	/**********
	 * Sets current role as admin when user selects student role at select role page
	 */
	public void selectAdminRole() {
		currentRole = "admin";
	}
	
	
	/**********************************************************************************************

	Public Getter Methods
	
	**********************************************************************************************/
	
	/**********
	 * Returns true if a user is logged in
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	/**********
	 * Returns the username of the logged in user or null if nobody is logged in
	 */
	public String getUsername() {
		return username;
	}
	
	/**********
	 * Returns true if the username of the logged in user is using the student role
	 */
	public boolean usingStudentRole() {
		return currentRole.equals("student");
	}
	
	/**********
	 * Returns true if the username of the logged in user is using the instructor role
	 */
	public boolean usingInstructorRole() {
		return currentRole.equals("instructor");
	}
	
	/**********
	 * Returns true if the username of the logged in user is using the admin role
	 */
	public boolean usingAdminRole() {
		return currentRole.equals("admin");
	}
	
	/**********
	 * Returns the student role status of logged in user and always returns false if nobody is logged in
	 */
	public boolean isStudent() {
		return AccountDatabase.isStudentRole(username);
	}
	
	/**********
	 * Returns the instructor role status of logged in user and always returns false if nobody is logged in
	 */
	public boolean isInstructor() {
		return AccountDatabase.isInstructorRole(username);
	}
	
	/**********
	 * Returns the admin role status of logged in user and always returns false if nobody is logged in
	 */
	public boolean isAdmin() {
		return AccountDatabase.isAdminRole(username);
	}
	
	/**********
	 * Returns the updated account info status of logged in user and always returns false if nobody is logged in
	 */
	public boolean isAccountUpdate() {
		return AccountDatabase.isAccountUpdated(username);
	}
}

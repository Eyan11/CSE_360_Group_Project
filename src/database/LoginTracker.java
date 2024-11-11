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
	private String username = null;
	private boolean isStudent = false;
	private boolean isInstructor = false;
	private boolean isAdmin = false;
	private boolean isAccountUpdated = false;
	
	
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
		isStudent = AccountDatabase.isStudentRole(username);
		isInstructor = AccountDatabase.isInstructorRole(username);
		isAdmin = AccountDatabase.isAdminRole(username);
		isAccountUpdated = AccountDatabase.isAccountUpdated(username);
		return true;
	}
	
	
	/**********
	 * Clears the information about the logged in user
	 */
	public void logout() {
		// Clear all user info
		isLoggedIn = false;
		username = null;
		isStudent = false;
		isInstructor = false;
		isAdmin = false;
		isAccountUpdated = false;
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
	 * Returns the student role status of logged in user and always returns false if nobody is logged in
	 */
	public boolean isStudent() {
		return isStudent;
	}
	
	/**********
	 * Returns the instructor role status of logged in user and always returns false if nobody is logged in
	 */
	public boolean isInstructor() {
		return isInstructor;
	}
	
	/**********
	 * Returns the admin role status of logged in user and always returns false if nobody is logged in
	 */
	public boolean isAdmin() {
		return isAdmin;
	}
	
	/**********
	 * Returns the updated account info status of logged in user and always returns false if nobody is logged in
	 */
	public boolean isAccountUpdate() {
		return isAccountUpdated;
	}
}

package application;
// I will add thorough documentation in future
// this class will be used as an object for AccountDatabase, which will store many Account objects
public class Account {
	
	private String user;
	private String pass;
	private String email;
	private String firstName;
	private String middleName;
	private String lastName;
	private String prefName;
	private String displayName;
	
	private boolean isKey;
	private boolean isStudent;
	private boolean isInstructor;
	private boolean isAdmin;
	
	private float expirationTime;
	
	// Constructor, used when creating first account
	public Account(String username, String password) {
		user = username;
		pass = password;
		isAdmin = true;
		
		firstName = "";
		middleName = "";
		lastName = "";
		prefName = "";
		displayName = "";
		
		isKey = false;
		isStudent = false;
		isInstructor = false;
		
		expirationTime = 0.0f;
	}
	
	// constructor, used when inviting a user
	public Account(boolean _isStudent, boolean _isInstructor, boolean _isAdmin, String key) {

		pass = "key";
		
		isKey = true;
		isStudent = _isStudent;
		isInstructor = _isInstructor;
		isAdmin = _isAdmin;
		
		expirationTime = 600f; // 10 minutes
		
		user = "";
		firstName = "";
		middleName = "";
		lastName = "";
		prefName = "";
		displayName = "";
	}

}

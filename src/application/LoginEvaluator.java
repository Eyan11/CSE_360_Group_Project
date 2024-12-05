package application;

import database.AccountDatabase;	// To use account database in different package

/*******
 * <p> LoginEvaluator Class </p>
 * 
 * <p> Description: Determines whether login/key information is valid </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/9/2024 Phase 1 Implementation and Documentation
 * 
 */


/**
 * Class utilized to determine credentials from login or key information
 * called inside of LoginGUI.java mainly 
 */

public class LoginEvaluator
{
	/*
	 * Variable Declarations
	 * store values from inputed parameters
	 */
	
	private static String userInput;
	private static String passwordInput;
	private static String keyInput;
	
	/**
	 * Methods
	 */
	
	/**
	 * checks if user name and password can both be found in database
	 * @param user name
	 * @param password
	 * @return
	 */
	
	public static boolean PasswordChecker(String username, String password)
	{
		//stores inputed parameter
		userInput = username;
		passwordInput = password;
		
		//check database to see if login information exists and corresponds to one another
		if(AccountDatabase.doesLoginExist(userInput, passwordInput)) // check if login exists
		{
			return true; // login DOES exist
		}
		//login does NOT exist
		return false;
	}
	
	/**
	 * checks if one-time key inputed by user is valid
	 * @param one-time key
	 * @return
	 */
	 
	public static boolean OneTimeKeyChecker(String onetimekey)
	{
		//stores inputed parameter
		keyInput = onetimekey;
		
		//check database to see if one-time key exists
		if(AccountDatabase.doesKeyExist(keyInput)) // check if key exists
		{
			return true; // key DOES exist
		}
		//key does NOT exist
		return false;
	}
	
	/**
	 * LOGIN BUTTON Methods
	 */
	
	/**
	 * IF first time login, send user to UpdateAccountInformationGUI
	 * @param user name
	 * @return
	 */
	
	public static boolean firstTimeLogin(String username)
	{
		//stores inputed parameter
		userInput = username;
		
		//check database to see if user needs to update account info
		if(!AccountDatabase.isAccountUpdated(userInput))
		{	
			return true; // users first time logging in
		}
		//returning user
		return false;
	}
	
	/**
	 * IF administrator logins AND has updated account info, send user to AdminHomeGUI
	 * @param user name
	 * @return
	 */
	
	public static boolean adminLogin(String username)
	{
		//stores inputed parameter
		userInput = username;
			
		//check database to see if user is returning AND is administrator
		if(AccountDatabase.isAccountUpdated(userInput) && AccountDatabase.isAdminRole(userInput))
		{	
			return true; // user is only an administrator
		}
		//users only role is administrator
		return false;
	}
	
	/**
	 * IF user has two roles (administrator and Student/Instructor), send user to SelectRoleGUI
	 * @param user name
	 * @return
	 */
	
	public static boolean multipleRoles(String username)
	{
		//stores inputed parameter
		userInput = username;
		
		//check database to see if user has multiple roles (one of them being administrator)
		if(AccountDatabase.isAdminRole(userInput) && (AccountDatabase.isStudentRole(userInput) || AccountDatabase.isInstructorRole(userInput)))
		{	
			return true; // user has multiple roles besides administrator
		}
		//user only has one role
		return false;
	}
	
	/**
	 * IF user is Student with updated account info, send user to StudentHomeGUI
	 * @param user name
	 * @return
	 */
	
	public static boolean studentRole(String username)
	{
		//stores inputed parameter
		userInput = username;
		
		//check database to see if user is either a student or instructor
		if(AccountDatabase.isAccountUpdated(userInput) && (AccountDatabase.isStudentRole(userInput)))
		{	
			return true; // user is a student or instructor
		}
		//user is neither a student or instructor
		return false;
	}
	
	/**
	 * IF user is Instructor with updated account info, send user to InstructorHomeGUI
	 * @param user name
	 * @return
	 */
	
	public static boolean instructorRole(String username)
	{
		//stores inputed parameter
		userInput = username;
		
		//check database to see if user is either a student or instructor
		if(AccountDatabase.isAccountUpdated(userInput) && (AccountDatabase.isInstructorRole(userInput)))
		{	
			return true; // user is a student or instructor
		}
		//user is neither a student or instructor
		return false;
	}
	
	/**
	 * IF new user, send user to CreateAccountGUI
	 * @param key
	 * @return
	 */
	
	public static boolean accountCreation(String key)
	{
		//stores inputed parameter
		keyInput = key;
		
		// Checks if key is being utilized for resetting password or account creation
		if(AccountDatabase.isInviteKey(key))
		{	
			// Key is being utilized for account creation
			return true;
		}
		// Key is being utilized for resetting password
		return false;
	}
}




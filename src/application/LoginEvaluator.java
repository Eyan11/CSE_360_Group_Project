package application;

import java.sql.SQLException;	// To catch errors for database

//helper class utilized inside in the LoginGUI class
public class LoginEvaluator
{
	/*
	 * Variables
	 */
	
	public static String userInput;
	public static String passwordInput;
	public static String keyInput;
	
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
		
		try {
			//access AccountDatabase class to find user name and corresponding password
			if(AccountDatabase.doesLoginExist(userInput, passwordInput)) // check if login exists
			{
				return true; // login DOES exist
			}
			//login does NOT exist
			return false;
		}
		catch(SQLException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
			return false;
		}
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
		
		try {
			//access AccountDatabase class to find one-time key
			if(AccountDatabase.doesKeyExist(keyInput)) // check if key exists
			{
				return true; // key DOES exist
			}
			//key does NOT exist
			return false;
		}
		catch(SQLException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * LOGIN BUTTON Methods
	 */
	
	//IF first time login, send user to UpdateAccountInformationGUI
	public static boolean firstTimeLogin(String username)
	{
		//stores inputed parameter
		userInput = username;
		
		//try {		
		//checks if user needs to update account info
		if(!AccountDatabase.isAccountUpdated(userInput))
		{	
			return true; // users first time logging in
		}
		//returning user
		return false;
		//}
		/*
		catch(SQLException a) {
			System.err.println("JDBC Driver not found: " + a.getMessage());
			return false;
		}
		*/
	}
	
	//IF administrator logins AND has updated account info, send user to AdminHomeGUI
	public static boolean adminLogin(String username)
	{
		//stores inputed parameter
		userInput = username;
		
		//try {
			
			//checks if user is returning AND is administrator
			if(AccountDatabase.isAccountUpdated(userInput) && AccountDatabase.isAdminRole(userInput))
			{	
				return true;
			}
			//users only role is administrator
			return false;
		//}
		/*
		catch(SQLException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
			return false;
		}
		*/
	}
	
	//IF user has two roles (administrator and Student/Instructor), send user to SelectRoleGUI
	public static boolean multipleRoles(String username)
	{
		//stores inputed parameter
		userInput = username;
		
		//try {	
		//check is user has multiple roles (one of them being administrator)
		if(AccountDatabase.isAdminRole(userInput) && (AccountDatabase.isStudentRole(userInput) || AccountDatabase.isInstructorRole(userInput)))
		{	
			return true;
		}
		//user only has one role
		return false;
		/*
		}
		catch(SQLException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
			return false;
		}
		*/
	}
	
	//IF user is Student/Instructor with updated account info, send user to StudentInstructorHomeGUI
	public static boolean studentInstructorRole(String username)
	{
		//stores inputed parameter
		userInput = username;
		
		//try {
		//check is user is either a student or instructor
		if(AccountDatabase.isAccountUpdated(userInput) && (AccountDatabase.isStudentRole(userInput) || AccountDatabase.isInstructorRole(userInput)))
		{	
			return true;
		}
		//user is neither a student or instructor
		return false;
		//}
			/*
		catch(SQLException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
			return false;
		}
		*/
	}
	
	/**
	 * VERIFY BUTTON Methods
	 * NEED TO FINISH for Phase2
	 * Assume for now every account is a new account when trying to verify a key
	 */
	
	//IF new user, send user to CreateAccountGUI
	public static boolean accountCreation(String key)
	{
		//stores inputed parameter
		keyInput = key;
		
		/*
		try {		
			//
			if(!AccountDatabase.isAccountUpdated(...))
			{	
				return true;
			}
			//
			return false;
		}
		catch(SQLException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
			return false;
		}
		*/
		
		return false; // TEMPORARY return value
	}
	
	//IF existing user, send user to ResetPasswordGUI
	//ResetPasswordGUI will not be implemented in Phase 1
}

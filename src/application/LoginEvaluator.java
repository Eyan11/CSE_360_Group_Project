package application;

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
	 * checks if username and password can both be found in database
	 * @param username
	 * @param password
	 * @return
	 */
	
	public static boolean PasswordChecker(String username, String password)
	{
		//stores inputed parameter
		userInput = username;
		passwordInput = password;
		
		//TEMPORARY return value
		return false;
		
		//access AccountDatabase class to find username and corresponding password
		//IF both exist and match, return true
		//ELSE return false
	}
	
	/**
	 * checks if one-time key inputed by user is valid
	 * @param onetimekey
	 * @return
	 */
	
	public static boolean OneTimeKeyChecker(String onetimekey)
	{
		//stores inputed parameter
		keyInput = onetimekey;
		
		//TEMPORARY return value
		return false;
		
		//access AccountDatabase class to find one-time key
		//IF one-time key exists, return true
		//ELSE return false
	}
}
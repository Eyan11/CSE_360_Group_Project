package database;

import java.sql.*; // For SQL related objects

/**
 * <p> ArticleDatabaseTesting. </p>
 * 
 * <p> Description: Tests all functionalities of the ArticleDatabase class.</p>
 * 
 * <p> Source: Lynn Robert Carter from PasswordEvaluatorTestbedWithGUI project, 
 * 				PasswordEvaluationTestingAutomation class, 
 * 				available at: https://canvas.asu.edu/courses/193728/assignments/5505672?module_item_id=14493167
 * 
 * @author Eyan Martucci
 * 
 * @version TODO
 *  
 */

public class ArticleDatabaseTesting {
	
	// Temporary variable to store return value
	private static boolean actualResult;
	// Total number of test cases that had matching expectations and results
	private static int numPassed = 0;
	// Total number of test cases that had different expectations and results
	private static int numFailed = 0;
	
	
	/**********
	 * Starts the testing automation that test 59 test cases
	 */
	public static void performTestEvaluations() throws SQLException {
		
		// Wipe all stored database rows on local machine and create a new accounts table
		ArticleDatabase.createTable();
		ArticleDatabase.deleteTable();
		ArticleDatabase.createTable();
		
		// *** Test methodName() **************************************

		
		// Print all accounts in database (not an actual test case, just to to check data in database)
		ArticleDatabase.printAllArticles();
		
		// Print Results
		System.out.println("-----------------------------------------------------------");
		System.out.println("\n\nRestults: ");
		System.out.println("Number of tests that passed: " + numPassed);
		System.out.println("Number of tests that failed: " + numFailed);
		
		// Reset accounts table for GUI usage
		ArticleDatabase.deleteTable();
		ArticleDatabase.createTable();
	}
	
	
	/**********************************************************************************************

	Private Helper Methods To Test All Functionalities of ArticleDatabase Class
	
	**********************************************************************************************/
	
}


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
	private static String actualResultString;
	// Total number of test cases that had matching expectations and results
	private static int numPassed = 0;
	// Total number of test cases that had different expectations and results
	private static int numFailed = 0;
	
	
	/**********
	 * Starts the testing automation for ArticleDatabase methods
	 */
	public static void performTestEvaluations() throws SQLException {
		
		// Wipe all stored database rows on local machine and create a new accounts table
		//ArticleDatabase.createTable();
		//ArticleDatabase.deleteTable();
		//ArticleDatabase.createTable();
		
		// *** Test isTableEmpty() ***************************************
		testIsTableEmpty(true);
		// ***************************************************************
		
		// *** Test createArticles() *************************************
		testCreateArticle("header1", "title1", "description1", "keywords1", "group1&group4", 
				"body1", "references1", true);	// id = 1
		testCreateArticle("header1", "Ignore", "Ignore", "Ignore", "Ignore", "Ignore", "Ignore", false);				// Duplicate header
		testCreateArticle("header2", "title2", "description2", "keywords2", "group2", "body2", "references2", true);	// id = 2
		testCreateArticle("header3", "title3", "description3", "keywords3", "group3", "body3", "references3", true);	// id = 3
		testCreateArticle("111111111122222222223333333333444444444455555555556666666666", "Ignore", "Ignore", "Ignore", 
				"Ignore", "Ignore", "Ignore", false);	// header over 50 characters
		testCreateArticle("header4", "title4", "description4", "keywords4", "group4", "body4", "references4", true);	// id = 4
		// ***************************************************************
		
		// *** Test getAllArticles() *************************************
		testGetAllArticles("1,header1,title1|2,header2,title2|3,header3,title3|4,header4,title4|");
		// ***************************************************************
	
		// *** Test deleteArticle() **************************************
		testDeleteArticle(3, true);		// id = 3 deleted
		testDeleteArticle(5, false);	// id doesn't exist
		testDeleteArticle(3, false);	// id doesn't exist since deleted
		testDeleteArticle(2, true);		// id = 2 deleted
		// ***************************************************************
		
		// *** Test isTableEmpty() ***************************************
		testIsTableEmpty(false);
		// ***************************************************************
		
		// *** Test doesArticleIDExist() *********************************
		testDoesArticleIDExist(1, true);
		testDoesArticleIDExist(2, false);	// id doesn't exist since deleted
		testDoesArticleIDExist(3, false);	// id doesn't exist since deleted
		testDoesArticleIDExist(4, true);
		// ***************************************************************
		
		// *** Test doesArticleHeaderExist() *****************************
		testDoesArticleHeaderExist("header1", true);
		testDoesArticleHeaderExist("header2", false);	// header doesn't exist since deleted
		testDoesArticleHeaderExist("header3", false);	// header doesn't exist since deleted
		testDoesArticleHeaderExist("header4", true);
		// ***************************************************************
		
		// *** Test getAllArticles() *************************************
		testGetAllArticles("1,header1,title1|4,header4,title4|");
		// ***************************************************************
		
		// *** Test getArticleByID() *************************************
		testGetArticleByID(1, "1,header1,title1,description1,keywords1,group1&group4,body1,references1");
		testGetArticleByID(2, "");	// id doesn't exist since deleted
		testGetArticleByID(3, "");	// id doesn't exist since deleted
		testGetArticleByID(4, "4,header4,title4,description4,keywords4,group4,body4,references4");
		// ***************************************************************
		
		// *** Test getArticlesByGroups***********************************
		testGetArticlesByGroups("group1", "1,header1,title1|");
		testGetArticlesByGroups("group4", "1,header1,title1|4,header4,title4|");
		testGetArticlesByGroups("group1,group4", "1,header1,title1|4,header4,title4|");	// group1 or group4
		testGetArticlesByGroups("group1&group4", "1,header1,title1|");					// group1 and group 4
		testGetArticlesByGroups("", "1,header1,title1|4,header4,title4|");				// Empty groups means get all articles
		testGetArticlesByGroups("group2", "");											// group doesn't exist since deleted
		// ***************************************************************
		
		// *** Test editArticle() ****************************************
		testEditArticle(1, "new header1", "new title1", "new description1", "new keywords1", 
				"new group1 & new group4", "new body1", "new references1", true);
		testEditArticle(2, "Ignore", "Ignore", "Ignore", "Ignore", "Ignore", "Ignore", "Ignore", false);	// id doesn't exist since deleted
		testEditArticle(3, "Ignore", "Ignore", "Ignore", "Ignore", "Ignore", "Ignore", "Ignore", false);	// id doesn't exist since deleted
		testEditArticle(4, "new header1", "Ignore", "Ignore", "Ignore", "Ignore", "Ignore", "Ignore", false);	// duplicate header
		// ***************************************************************
		
		
		// Print all articles in database (not an actual test case, just to to check data in database)
		System.out.println("Finished Testing, Printing ALl Articles: \n\n" + ArticleDatabase.getAllArticles());
		
		// Print Results
		System.out.println("-----------------------------------------------------------");
		System.out.println("\n\nRestults: ");
		System.out.println("Number of tests that passed: " + numPassed);
		System.out.println("Number of tests that failed: " + numFailed);
		
		// Reset accounts table for GUI usage
		//ArticleDatabase.deleteTable();
		//ArticleDatabase.createTable();
	}
	
	
	/**********************************************************************************************

	Private Helper Methods To Test Setter Methods in ArticleDatabase class.
	
	**********************************************************************************************/
	
	
	/**********
	 * Tests the functionality of the createArticle() method in ArticleDatabase class.
	 */
	private static void testCreateArticle(String header, String title, String description, String keywords, 
			String groups, String body, String references, boolean expectedResult) throws SQLException {
		
		// Create article and return result
		actualResult = ArticleDatabase.createArticle(header, title, description, keywords, groups, body, references);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("createArticle() passed!");
		}
		else {
			numFailed++;
			System.out.println("createArticle() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the deleteArticle() method in ArticleDatabase class.
	 */
	private static void testDeleteArticle(int id, boolean expectedResult) throws SQLException{
		
		// Delete article and return result
		actualResult = ArticleDatabase.deleteArticle(id);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("deleteArticle() passed!");
		}
		else {
			numFailed++;
			System.out.println("deleteArticle() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the editArticle() method in ArticleDatabase class.
	 */
	private static void testEditArticle(int id, String header, String title, String description, String keywords, 
			String groups, String body, String references, boolean expectedResult) throws SQLException{
		
		// Edit article and return result
		actualResult = ArticleDatabase.editArticle(id, header, title, description, keywords, groups, body, references);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("editArticle() passed!");
		}
		else {
			numFailed++;
			System.out.println("editArticle() failed!");
		}
	}
	
	
	/**********************************************************************************************

	Private Helper Methods To Test Getter Methods in ArticleDatabase class.
	
	**********************************************************************************************/
	
	
	/**********
	 * Tests the functionality of the isTableEmpty() method in ArticleDatabase class.
	 */
	private static void testIsTableEmpty(boolean expectedResult) throws SQLException{
		
		// Check if articles table is empty and return result
		actualResult = ArticleDatabase.isTableEmpty();
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("isTableEmpty() passed!");
		}
		else {
			numFailed++;
			System.out.println("isTableEmpty() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the doesArticleIDExist() method in ArticleDatabase class.
	 */
	private static void testDoesArticleIDExist(int id, boolean expectedResult) throws SQLException{
		
		// Check if article id exists and return result
		actualResult = ArticleDatabase.doesArticleIDExist(id);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("doesArticleIDExist() passed!");
		}
		else {
			numFailed++;
			System.out.println("doesArticleIDExist() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the doesArticleHeaderExist() method in ArticleDatabase class.
	 */
	private static void testDoesArticleHeaderExist(String header, boolean expectedResult) throws SQLException{
		
		// Check if article header exists and return result
		actualResult = ArticleDatabase.doesArticleHeaderExist(header);
		
		// Return if test passed or failed and track
		if(actualResult == expectedResult) {
			numPassed++;
			System.out.println("doesArticleHeaderExist() passed!");
		}
		else {
			numFailed++;
			System.out.println("doesArticleHeaderExist() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the getAllArticles() method in ArticleDatabase class.
	 */
	private static void testGetAllArticles(String expectedString) throws SQLException{
		
		// Get header and title of all articles
		actualResultString = ArticleDatabase.getAllArticles();
				
		// Return if test passed or failed and track
		if(actualResultString.equals(expectedString)) {
			numPassed++;
			System.out.println("getAllArticles() passed!");
		}
		else {
			numFailed++;
			System.out.println("getAllArticles() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the getArticleByID() method in ArticleDatabase class.
	 */
	private static void testGetArticleByID(int id, String expectedString) throws SQLException {
		
		// Get id, header, and title of article with matching id
		actualResultString = ArticleDatabase.getArticleByID(id);
		
		// Return if test passed or failed and track
		if(actualResultString.equals(expectedString)) {
			numPassed++;
			System.out.println("getArticleByID() passed!");
		}
		else {
			numFailed++;
			System.out.println("getArticleByID() failed!");
		}
	}
	
	
	/**********
	 * Tests the functionality of the getArticlesByGroups() method in ArticleDatabase class.
	 */
	private static void testGetArticlesByGroups(String groups, String expectedString) throws SQLException {
		
		// Get id, header, and title of all articles with matching groups
		actualResultString = ArticleDatabase.getArticlesByGroups(groups);
		
		// Return if test passed or failed and track
		if(actualResultString.equals(expectedString)) {
			numPassed++;
			System.out.println("getArticlesByGroups() passed!");
		}
		else {
			numFailed++;
			System.out.println("getArticlesByGroups() failed!");
		}
	}
}


package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import database.AccountDatabase;
import database.ArticleDatabase;	// To test ArticleDatabase methods
import database.DatabaseManager;
import database.GroupDatabase;
import database.LoginTracker;

/**
 * <p> ArticleDatabaseTesting. </p>
 * 
 * <p> Description: Uses JUnit test cases to test all public methods in the ArticleDatabase class.</p>
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		11/19/2024 Phase 3 implementation and documentation
 *  
 */

class ArticleDatabaseTesting {

	
	/**********
	 * Opens connection to database before all testing starts and initializes non-group database
	 */
	@BeforeAll
	public static void initialization() {
		DatabaseManager.connectToDatabase();	// Start database
		
		// Add admin user
		AccountDatabase.createFirstAccount("userA", "pass");
		
		// Inite and add instructor user
		String key = AccountDatabase.inviteUser(false, true, false);
		AccountDatabase.createAccountWithKey("userI", "pass", key);
		
		// Invite and add student user
		key = AccountDatabase.inviteUser(true, false, false);
		AccountDatabase.createAccountWithKey("userS", "pass", key);
		
		GroupDatabase.createGroup("groupS", "userA", true);		// Create special access group 'groupS'
		GroupDatabase.addUserToGroup("userI", "groupS", false);	// Add userI as admin
		GroupDatabase.createGroup("groupG", "userA", false);	// Create general access group 'groupG'
		GroupDatabase.addUserToGroup("userI", "groupG", false);	// Add userI as admin
	}
	
	
	/**********
	 * Closes connection to database and wipes the created rows in tables when all testing is finished
	 */
	@AfterAll
	public static void cleanup() {
		// Wipe all rows in groups and accounts table
		GroupDatabase.deleteAllGroups();
		AccountDatabase.deleteAllAccounts();
		
		DatabaseManager.closeConnection();
	}
	
	
	/**********************************************************************************************

	Test ArticleDatabase Getter Methods
	
	**********************************************************************************************/
	
	
	@Test
	void testIsTableEmpty() {
		ArticleDatabase.deleteAllArticles();
		
		// Test 1
		assertTrue(ArticleDatabase.isTableEmpty(), "Test 1");
		
		// Test 2
		ArticleDatabase.createArticle("h1", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		assertFalse(ArticleDatabase.isTableEmpty(), "Test 2");
		
		// Test 2
		ArticleDatabase.deleteArticle(ArticleDatabase.getArticleID("h1"));
		assertTrue(ArticleDatabase.isTableEmpty(), "Test 3");
	}
	
	
	@Test
	void testDoesArticleIDExist() {
		ArticleDatabase.deleteAllArticles();
		
		// Test 1 - article 1
		ArticleDatabase.createArticle("h1", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		assertTrue(ArticleDatabase.doesArticleIDExist(ArticleDatabase.getArticleID("h1")), "Test 1");
		
		// Test 2 - article 2
		ArticleDatabase.createArticle("h2", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		assertTrue(ArticleDatabase.doesArticleIDExist(ArticleDatabase.getArticleID("h2")), "Test 2");
		
		// Test 3 - id doesn't exist
		assertFalse(ArticleDatabase.doesArticleIDExist(-1), "Test 3");
	}

	
	@Test
	void testDoesArticleHeaderExist() {
		ArticleDatabase.deleteAllArticles();
		
		// Test 1 - article 1
		ArticleDatabase.createArticle("h1", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		assertTrue(ArticleDatabase.doesArticleHeaderExist("h1"), "Test 1");
		
		// Test 2 - article 2
		ArticleDatabase.createArticle("h2", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		assertTrue(ArticleDatabase.doesArticleHeaderExist("h2"), "Test 2");
		
		// Test 3 - header doesn't exist
		assertFalse(ArticleDatabase.doesArticleHeaderExist("Ignore"), "Test 3");
	}
	
	
	@Test
	void testGetArticleID() {
		ArticleDatabase.deleteAllArticles();
		LoginTracker.logout();
		
		// Test 1 - article 1
		LoginTracker.login("userI");
		ArticleDatabase.createArticle("h1", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		String allArticles = ArticleDatabase.getAllArticles();	// Get all articles
		int id = Integer.parseInt(allArticles.substring(0, allArticles.indexOf("+")));	// Extract id from all articles
		assertEquals(id, ArticleDatabase.getArticleID("h1"), "Test 1");
		
		// Test 2 - article 2
		ArticleDatabase.deleteAllArticles();	// Wipe database so getAllArticles only returns 1 article
		ArticleDatabase.createArticle("h2", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		allArticles = ArticleDatabase.getAllArticles();	// Get all articles
		id = Integer.parseInt(allArticles.substring(0, allArticles.indexOf("+")));	// Extract id from all articles
		assertEquals(id, ArticleDatabase.getArticleID("h2"), "Test 2");

		// Test 3 - header doesn't exist
		assertEquals(-1, ArticleDatabase.getArticleID("Ignore"), "Test 3");
	}
	
	
	@Test
	void testGetAllArticles() {
		ArticleDatabase.deleteAllArticles();
		LoginTracker.logout();
		
		// Test 1 - Empty table
		LoginTracker.login("userA");
		assertEquals("", ArticleDatabase.getAllArticles(), "Test 1");
		
		// Test 2 - article 1
		ArticleDatabase.createArticle("h1", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		String allArticles = ArticleDatabase.getArticleID("h1") + "+h1+t+groupG|";	// Create all articles string with 1st articles ingo
		assertEquals(allArticles, ArticleDatabase.getAllArticles(), "Test 2");
		
		// Test 3 - article 2
		ArticleDatabase.createArticle("h2", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		allArticles += ArticleDatabase.getArticleID("h2") + "+h2+t+groupG|";		// Add article 2 to string
		assertEquals(allArticles, ArticleDatabase.getAllArticles(), "Test 3");
		
		// Test 4 - Nobody logged in
		LoginTracker.logout();
		assertEquals("", ArticleDatabase.getAllArticles(), "Test 4");
	}
	
	
	@Test
	void testGetArticleByID() {
		ArticleDatabase.deleteAllArticles();
		LoginTracker.logout();
		
		// Test 1 - article 1
		LoginTracker.login("userI");
		ArticleDatabase.createArticle("h1", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		String articleInfo = ArticleDatabase.getArticleID("h1") + "+h1+t+a+d+k+beginner+groupG+b+r";
		System.out.println("\n\n\n All Articles" + ArticleDatabase.getAllArticles() + "\n\n\n");
		assertEquals(articleInfo, ArticleDatabase.getArticleByID(ArticleDatabase.getArticleID("h1")), "Test 1");
		
		// Test 2 - article 2
		ArticleDatabase.createArticle("h2", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		articleInfo = ArticleDatabase.getArticleID("h2") + "+h2+t+a+d+k+beginner+groupG+b+r";
		assertEquals(articleInfo, ArticleDatabase.getArticleByID(ArticleDatabase.getArticleID("h2")), "Test 2");
		
		// Test 3 - ID doesn't exist
		assertEquals("", ArticleDatabase.getArticleByID(-1), "Test 3");
	}
	
	/*
	@Test
	void testSearchByContents() {
		fail("Not yet implemented");
	}
	*/
	
	/**********************************************************************************************

	Test ArticleDatabase Setter Methods
	
	**********************************************************************************************/
	/*
	@Test
	void testCreateArticle() {
		fail("Not yet implemented");
	}
	
	@Test
	void testDeleteArticle() {
		fail("Not yet implemented");
	}
	
	@Test
	void testEditArticle() {
		fail("Not yet implemented");
	}
	*/
	/**********************************************************************************************

	Test ArticleDatabase Backup/Restore Methods
	
	**********************************************************************************************/
	/*
	@Test
	void testBackupArticles() {
		fail("Not yet implemented");
	}
	
	@Test
	void testRestoreByOverriding() {
		fail("Not yet implemented");
	}
	
	@Test
	void testRestoreByMerging() {
		fail("Not yet implemented");
	}
	*/
}

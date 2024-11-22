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
		GroupDatabase.addUserToGroup("userS", "groupS", true);	// Add userS as viewer
		
		GroupDatabase.createGroup("groupG", "userA", false);	// Create general access group 'groupG'
		GroupDatabase.addUserToGroup("userI", "groupG", false);	// Add userI as admin
		GroupDatabase.addUserToGroup("userS", "groupG", true);	// Add userS as viewer
		
		GroupDatabase.createGroup("groupX", "userA", true);		// Create special access group 'groupX'
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
		
		// Test 1 - instructor general group article
		LoginTracker.login("userI");
		ArticleDatabase.createArticle("h1", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		String articleInfo = ArticleDatabase.getArticleID("h1") + "+h1+t+a+d+k+beginner+groupG+b+r";
		assertEquals(articleInfo, ArticleDatabase.getArticleByID(ArticleDatabase.getArticleID("h1")), "Test 1");
		
		// Test 2 - instructor special group article
		ArticleDatabase.createArticle("h2", "t", "a", "d", "k", "beginner", "groupS", "b", "r");
		articleInfo = ArticleDatabase.getArticleID("h2") + "+h2+t+a+d+k+beginner+groupS+b+r";
		assertEquals(articleInfo, ArticleDatabase.getArticleByID(ArticleDatabase.getArticleID("h2")), "Test 2");
		
		// Test 3 - admin general group article
		LoginTracker.login("userA");
		ArticleDatabase.createArticle("h3", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		articleInfo = ArticleDatabase.getArticleID("h3") + "+h3+t+a+d+k+beginner+groupG+r";
		assertEquals(articleInfo, ArticleDatabase.getArticleByID(ArticleDatabase.getArticleID("h3")), "Test 3");
		
		// Test 4 - admin special group article
		ArticleDatabase.createArticle("h4", "t", "a", "d", "k", "beginner", "groupS", "b", "r");
		articleInfo = ArticleDatabase.getArticleID("h4") + "+h4+t+a+d+k+beginner+groupS+r";
		assertEquals(articleInfo, ArticleDatabase.getArticleByID(ArticleDatabase.getArticleID("h4")), "Test 4");
		
		// Test 5 - ID doesn't exist
		assertEquals("", ArticleDatabase.getArticleByID(-1), "Test 5");
	}
	
	
	@Test
	void testSearchByContents() {
		ArticleDatabase.deleteAllArticles();
		LoginTracker.logout();
		
		// TEST 1 - Empty
		assertEquals("", ArticleDatabase.searchByContents("all", "all", "all"), "Test 1");

		// TEST 2 - One article
		LoginTracker.login("userI");
		ArticleDatabase.createArticle("h1", "t1", "a1", "d1", "k1", "beginner", "groupG", "b1", "r1");
		String searchResult = "Groups: groupG\nContent Levels: 1 beginner\n\n"
				 + 	"Sequence Number: 1\nTitle: t1\nAuthor: a1\nDescription: d1\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("all", "all", "all"), "Test 2");
		
		// TEST 3 - Two articles
		ArticleDatabase.createArticle("h2", "t2", "a2", "d2", "k2", "intermediate", "groupS", "b2", "r2");
		searchResult = "Groups: groupG, groupS\nContent Levels: 1 beginner, 1 intermediate\n\n"
				 + 	"Sequence Number: 1\nTitle: t1\nAuthor: a1\nDescription: d1\n\n"
				 + 	"Sequence Number: 2\nTitle: t2\nAuthor: a2\nDescription: d2\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("all", "all", "all"), "Test 3");
		
		// TEST 4 - Three articles
		ArticleDatabase.createArticle("h3", "t3", "a3", "d3", "k3", "advanced", "groupG", "b3", "r3");
		searchResult = "Groups: groupG, groupS\nContent Levels: 1 beginner, 1 intermediate, 1 advanced\n\n"
				 + 	"Sequence Number: 1\nTitle: t1\nAuthor: a1\nDescription: d1\n\n"
				 + 	"Sequence Number: 2\nTitle: t2\nAuthor: a2\nDescription: d2\n\n"
				 + 	"Sequence Number: 3\nTitle: t3\nAuthor: a3\nDescription: d3\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("all", "all", "all"), "Test 4");
		
		// TEST 5 - Don't return 4th article because userI doesn't have access to groupX
		LoginTracker.login("userA");
		ArticleDatabase.createArticle("h4", "t4", "a4", "d4", "k4", "expert", "groupX", "b4", "r4");
		LoginTracker.login("userI");
		searchResult = "Groups: groupG, groupS\nContent Levels: 1 beginner, 1 intermediate, 1 advanced\n\n"
				 + 	"Sequence Number: 1\nTitle: t1\nAuthor: a1\nDescription: d1\n\n"
				 + 	"Sequence Number: 2\nTitle: t2\nAuthor: a2\nDescription: d2\n\n"
				 + 	"Sequence Number: 3\nTitle: t3\nAuthor: a3\nDescription: d3\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("all", "all", "all"), "Test 5");
		
		// TEST 6 - Return all 4 articles because userA has access to all groups
		LoginTracker.login("userA");
		searchResult = "Groups: groupG, groupS, groupX\nContent Levels: 1 beginner, 1 intermediate, 1 advanced, 1 expert\n\n"
				 + 	"Sequence Number: 1\nTitle: t1\nAuthor: a1\nDescription: d1\n\n"
				 + 	"Sequence Number: 2\nTitle: t2\nAuthor: a2\nDescription: d2\n\n"
				 + 	"Sequence Number: 3\nTitle: t3\nAuthor: a3\nDescription: d3\n\n"
				 + 	"Sequence Number: 4\nTitle: t4\nAuthor: a4\nDescription: d4\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("", "", ""), "Test 6");	// "" = "all"
		
		// TEST 7 - Filter by groups
		LoginTracker.login("userI");
		searchResult = "Groups: groupG\nContent Levels: 1 beginner, 1 advanced\n\n"
				 + 	"Sequence Number: 1\nTitle: t1\nAuthor: a1\nDescription: d1\n\n"
				 + 	"Sequence Number: 2\nTitle: t3\nAuthor: a3\nDescription: d3\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("groupG", "all", "all"), "Test 7");
		
		// TEST 8 - Filter by level
		searchResult = "Groups: groupG\nContent Levels: 1 advanced\n\n"
				 + 	"Sequence Number: 1\nTitle: t3\nAuthor: a3\nDescription: d3\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("all", "advanced", "all"), "Test 8");
		
		// TEST 9 - Filter by contents (title)
		searchResult = "Groups: groupG\nContent Levels: 1 beginner\n\n"
				 + 	"Sequence Number: 1\nTitle: t1\nAuthor: a1\nDescription: d1\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("all", "all", "t1"), "Test 9");
		
		// TEST 10 - Filter by contents (author)
		searchResult = "Groups: groupS\nContent Levels: 1 intermediate\n\n"
				+ 	"Sequence Number: 1\nTitle: t2\nAuthor: a2\nDescription: d2\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("all", "all", "a2"), "Test 10");
		
		// TEST 11 - Filter by contents (description)
		searchResult = "Groups: groupG\nContent Levels: 1 advanced\n\n"
				+ 	"Sequence Number: 1\nTitle: t3\nAuthor: a3\nDescription: d3\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("all", "all", "d3"), "Test 11");
		
		// TEST 12 - Use All 3 Filters
		searchResult = "Groups: groupG\nContent Levels: 1 beginner\n\n"
				+ 	"Sequence Number: 1\nTitle: t1\nAuthor: a1\nDescription: d1\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("groupG", "beginner", "a1"), "Test 12");
		
		// TEST 13 - Filter by Multiple Groups
		LoginTracker.login("userA");	// Use admin just to show it doesn't return all groups
		searchResult = "Groups: groupG, groupS\nContent Levels: 1 beginner, 1 intermediate, 1 advanced\n\n"
				 + 	"Sequence Number: 1\nTitle: t1\nAuthor: a1\nDescription: d1\n\n"
				 + 	"Sequence Number: 2\nTitle: t2\nAuthor: a2\nDescription: d2\n\n"
				 + 	"Sequence Number: 3\nTitle: t3\nAuthor: a3\nDescription: d3\n\n";
		assertEquals(searchResult, ArticleDatabase.searchByContents("groupG, groupS", "all", "all"), "Test 13");
		
		// TEST 14 - Search parameters are too long
		assertEquals("", ArticleDatabase.searchByContents(""
				+ "11111111112222222222333333333344444444445555555555 Too Long", "all", "all"), "Test 14");
	}
	
	
	/**********************************************************************************************

	Test ArticleDatabase Setter Methods
	
	**********************************************************************************************/
	
	
	@Test
	void testCreateArticle() {
		ArticleDatabase.deleteAllArticles();
		LoginTracker.logout();
		
		// TEST 1 - create first article
		assertTrue(ArticleDatabase.createArticle("h1", "t", "a", "d", "k", "beginner", "groupG", "b", "r"), "Test 1");
		
		// TEST 2 - create second article
		LoginTracker.login("userI");
		assertTrue(ArticleDatabase.createArticle("h2", "t", "a", "d", "k", "intermediate", "groupS", "b", "r"), "Test 2");
		
		// TEST 3 - duplicate header
		assertFalse(ArticleDatabase.createArticle("h2", "t", "a", "d", "k", "intermediate", "groupS", "b", "r"), "Test 3");
		
		// TEST 4 - invalid character (no + or | symbol)
		assertFalse(ArticleDatabase.createArticle("h9", "t", "a+a", "d", "k", "intermediate", "groupG", "b", "r"), "Test 4");
		
		// TEST 5 - Keywords are over 50 characters
		assertFalse(ArticleDatabase.createArticle("h9", "t", "a", "d", 
				"111111111122222222223333333334444444444 Too Long Over 50 characters"
				, "beginner", "groupG", "b", "r"), "Test 5");
		
		// TEST 6 - References are over 100 characters
		assertFalse(ArticleDatabase.createArticle("h9", "t", "a", "d", "k", "intermediate", "groupS", "b", 
				"111111111122222222223333333333444444444455555555556666666666"
				+ "777777777788888888889999999999 Over 100 characters"), "Test 6");
		
		// TEST 7 - Body is over 500 characters
		assertFalse(ArticleDatabase.createArticle("h9", "t", "a", "d", "k", "intermediate", "groupG", 
				"1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "Too Long, over 500 characters", 
				"r"), "Test 7");
		
		// TEST 8 - userI is not a group admin of that special access group
		assertFalse(ArticleDatabase.createArticle("h9", "t", "a", "d", "k", "intermediate", "groupX", "b", "r"), "Test 8");
		
		// TEST 9 - group doesn't exist
		assertFalse(ArticleDatabase.createArticle("h9", "t", "a", "d", "k", "intermediate", "Ignore", "b", "r"), "Test 9");
		
		// TEST 10 - Content level isn't beginner, intermediate, advanced, or expert
		assertFalse(ArticleDatabase.createArticle("h9", "t", "a", "d", "k", "Ignore", "GroupG", "b", "r"), "Test 10");
	}
	
	@Test
	void testDeleteArticle() {
		ArticleDatabase.deleteAllArticles();
		LoginTracker.logout();
		
		// Test 1 - delete first article
		LoginTracker.login("userA");		// Use admin to create articles
		ArticleDatabase.createArticle("h1", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		ArticleDatabase.createArticle("h2", "t", "a", "d", "k", "advanced", "groupS", "b", "r");
		ArticleDatabase.createArticle("h3", "t", "a", "d", "k", "expert", "groupX", "b", "r");
		LoginTracker.login("userI");		// Use instructor to delete articles
		assertTrue(ArticleDatabase.deleteArticle(ArticleDatabase.getArticleID("h1")), "Test 1");
		
		// Test 2 - delete second article
		assertTrue(ArticleDatabase.deleteArticle(ArticleDatabase.getArticleID("h2")), "Test 2");
		
		// Test 3 - article id doesn't exist
		assertFalse(ArticleDatabase.deleteArticle(-1), "Test 3");
		
		// Test 4 - userI is not a group admin of groupX
		assertFalse(ArticleDatabase.deleteArticle(ArticleDatabase.getArticleID("h3")), "Test 4");
	}
	
	
	@Test
	void testEditArticle() {
		ArticleDatabase.deleteAllArticles();
		LoginTracker.logout();
		
		// TEST 1 - edit first article
		LoginTracker.login("userA");		// Use admin to create articles
		ArticleDatabase.createArticle("h1", "t", "a", "d", "k", "beginner", "groupG", "b", "r");
		ArticleDatabase.createArticle("h2", "t", "a", "d", "k", "advanced", "groupS", "b", "r");
		ArticleDatabase.createArticle("h3", "t", "a", "d", "k", "expert", "groupX", "b", "r");
		LoginTracker.login("userI");		// Use instructor to delete articles
		int id = ArticleDatabase.getArticleID("h1");
		assertTrue(ArticleDatabase.editArticle(id, "h1", "t1", "a1", "d1", "k1", "intermediate", "groupG", "b1", "r1"), "Test 1");
		
		// TEST 2 - edit second article
		id = ArticleDatabase.getArticleID("h2");
		assertTrue(ArticleDatabase.editArticle(id, "h2", "t2", "a2", "d2", "k2", "beginner", "groupS", "b2", "r2"), "Test 2");
		
		// TEST 3 - id doesn't exist
		assertFalse(ArticleDatabase.editArticle(-1, "h9", "t", "a", "d", "k", "advanced", "groupS", "b", "r"), "Test 3");
		
		// TEST 4 - duplicate header
		assertFalse(ArticleDatabase.editArticle(id, "h1", "t", "a", "d", "k", "intermediate", "groupS", "b", "r"), "Test 4");
		
		// Test 5 - not logged in as admin or instructor
		LoginTracker.login("userS");
		assertFalse(ArticleDatabase.editArticle(id, "h2", "t", "a", "d", "k", "expert", "groupS", "new b", "r"), "Test 5");
		
		// TEST 6 - invalid character (no + or | symbol)
		LoginTracker.login("userI");
		assertFalse(ArticleDatabase.editArticle(id, "h9", "t", "a+a", "d", "k", "expert", "groupG", "b", "r"), "Test 6");
		
		// TEST 7 - userI is not a group admin of old groups (groupX)
		id = ArticleDatabase.getArticleID("h3");
		assertFalse(ArticleDatabase.editArticle(id, "h9", "t", "a", "d", "k", "advanced", "groupG", "b", "r"), "Test 7");
		
		// TEST 8 - userI is not a group admin of new groups (groupX)
		id = ArticleDatabase.getArticleID("h2");
		assertFalse(ArticleDatabase.editArticle(id, "h9", "t", "a", "d", "k", "advanced", "groupX", "b", "r"), "Test 8");
		
		// TEST 9 - content level is not beginner, intermediate, advanced, or expert
		assertFalse(ArticleDatabase.editArticle(id, "h9", "t", "a", "d", "k", "Ignore", "groupG", "b", "r"), "Test 9");
		
		// TEST 10 - Keywords are over 50 characters
		assertFalse(ArticleDatabase.editArticle(id, "h9", "t", "a", "d", 
				"111111111122222222223333333334444444444 Too Long Over 50 characters"
				, "beginner", "groupG", "b", "r"), "Test 10");
		
		// TEST 11 - References are over 100 characters
		assertFalse(ArticleDatabase.editArticle(id, "h9", "t", "a", "d", "k", "intermediate", "groupS", "b", 
				"111111111122222222223333333333444444444455555555556666666666"
				+ "777777777788888888889999999999 Over 100 characters"), "Test 11");
		
		// TEST 12 - Body is over 500 characters
		assertFalse(ArticleDatabase.editArticle(id, "h9", "t", "a", "d", "k", "intermediate", "groupG", 
				"1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000"
				+ "Too Long, over 500 characters", 
				"r"), "Test 12");
		
		// TEST 13 - group doesn't exist
		assertFalse(ArticleDatabase.editArticle(id, "h9", "t", "a", "d", "k", "intermediate", "Ignore", "b", "r"), "Test 13");
		
		// TEST 14 - admin can edit article, but not the body
		LoginTracker.login("userA");
		assertTrue(ArticleDatabase.editArticle(id, "h9", "t", "a", "d", "k", "beginner", "groupG", "This will not be edited", "r"), "Test 14");
	}
	
	/**********************************************************************************************

	Test ArticleDatabase Backup/Restore Methods
	
	**********************************************************************************************/
	
	
	@Test
	void testBackupArticles() {
		ArticleDatabase.deleteAllArticles();
		LoginTracker.logout();
		
		// TEST 1 - Backup article 1
		LoginTracker.login("userI");
		ArticleDatabase.createArticle("h1", "t1", "a1", "d1", "k1", "beginner", "groupG", "b1", "r1");
		assertTrue(ArticleDatabase.backupArticles("backup1", "all"), "Test 1");
		
		// TEST 2 - Backup articles 1 and 2
		ArticleDatabase.createArticle("h2", "t2", "a2", "d2", "k2", "intermediate", "groupS", "b2", "r2");
		assertTrue(ArticleDatabase.backupArticles("backup1", "all"), "Test 2");
		
		// TEST 3 - Backup articles 1, 2, and 3
		ArticleDatabase.createArticle("h3", "t3", "a3", "d3", "k3", "advanced", "groupG", "b2", "r2");
		assertTrue(ArticleDatabase.backupArticles("backup1", "all"), "Test 3");
		
		// TEST 4 - Backup articles with groupG
		assertTrue(ArticleDatabase.backupArticles("backup1", "groupG"), "Test 4");
		
		// TEST 5 - Backup articles with groupS
		assertTrue(ArticleDatabase.backupArticles("backup1", "groupS"), "Test 5");
		
		// TEST 6 - Backup articles with groupS AND groupG
		assertTrue(ArticleDatabase.backupArticles("backup1", "groupS, groupG"), "Test 6");
		
		// TEST 7 - Backup no articles because userI does not have admin rights for groupX
		LoginTracker.login("userA");
		ArticleDatabase.createArticle("h4", "t4", "a4", "d4", "k4", "expert", "groupX", "b4", "r4");
		LoginTracker.login("userI");
		assertTrue(ArticleDatabase.backupArticles("backup1", "groupX"), "Test 7");
		
		// TEST 8 - Groups filter is over 50 characters
		assertFalse(ArticleDatabase.backupArticles("backup1", 
				"111111111122222222233333333334444444445555555555 Too Long"), "Test 8");
		
		// Test 9 - Empty file path
		assertFalse(ArticleDatabase.backupArticles("", "all"), "Test 9");
		
		// Test 10 - File path is too long
		assertFalse(ArticleDatabase.backupArticles(""
				+ "111111111122222222233333333334444444445555555555"
				+ "111111111122222222233333333334444444445555555555 Too Long", "all"), "Test 10");
	}
	
	
	@Test
	void testRestoreByOverriding() {
		ArticleDatabase.deleteAllArticles();
		LoginTracker.logout();
		
		// TEST 1 - Restore no articles
		LoginTracker.login("userI");
		ArticleDatabase.backupArticles("backup2", "all");
		assertTrue(ArticleDatabase.restoreByOverriding("backup2"), "Test 1");
		
		// TEST 2 - Restore 1 article
		ArticleDatabase.createArticle("h1", "t1", "a1", "d1", "k1", "beginner", "groupG", "b1", "r1");
		ArticleDatabase.backupArticles("backup2", "all");
		assertTrue(ArticleDatabase.restoreByOverriding("backup2"), "Test 2");
		
		// TEST 3 - Restore 2 articles
		ArticleDatabase.createArticle("h2", "t2", "a2", "d2", "k2", "intermediate", "groupS", "b2", "r2");
		ArticleDatabase.backupArticles("backup2", "all");
		assertTrue(ArticleDatabase.restoreByOverriding("backup2"), "Test 3");
		
		// TEST 4 - Restore 3 articles
		ArticleDatabase.createArticle("h3", "t3", "a3", "d3", "k3", "advanced", "groupG", "b3", "r3");
		ArticleDatabase.backupArticles("backup2", "all");
		assertTrue(ArticleDatabase.restoreByOverriding("backup2"), "Test 4");
		
		// TEST 5 - Empty file path
		assertFalse(ArticleDatabase.restoreByOverriding(""), "Test 5");
		
		// TEST 6 - File path over 100 characters
		assertFalse(ArticleDatabase.restoreByOverriding(""
				+ "111111111122222222233333333334444444445555555555"
				+ "111111111122222222233333333334444444445555555555 Too Long"), "Test 6");
		
	}
	
	
	@Test
	void testRestoreByMerging() {
		ArticleDatabase.deleteAllArticles();
		LoginTracker.logout();
		
		// TEST 1 - Restore no articles
		ArticleDatabase.backupArticles("backup3", "all");
		assertTrue(ArticleDatabase.restoreByMerging("backup3"), "Test 1");
		
		// TEST 2 - Restore 1 article
		LoginTracker.login("userI");
		ArticleDatabase.createArticle("h1", "t1", "a1", "d1", "k1", "beginner", "groupG", "b1", "r1");
		ArticleDatabase.backupArticles("backup3", "all");
		assertTrue(ArticleDatabase.restoreByMerging("backup3"), "Test 2");
		
		// TEST 3 - Restore 2 articles
		ArticleDatabase.createArticle("h2", "t2", "a2", "d2", "k2", "intermediate", "groupS", "b2", "r2");
		ArticleDatabase.backupArticles("backup3", "all");
		assertTrue(ArticleDatabase.restoreByMerging("backup3"), "Test 3");
		
		// TEST 4 - Restore 3 articles
		ArticleDatabase.createArticle("h3", "t3", "a3", "d3", "k3", "advanced", "groupG", "b3", "r3");
		ArticleDatabase.backupArticles("backup3", "all");
		assertTrue(ArticleDatabase.restoreByMerging("backup3"), "Test 4");
		
		// TEST 5 - Empty file path
		assertFalse(ArticleDatabase.restoreByMerging(""), "Test 5");
		
		// TEST 6 - File path over 100 characters
		assertFalse(ArticleDatabase.restoreByMerging(""
				+ "111111111122222222233333333334444444445555555555"
				+ "111111111122222222233333333334444444445555555555 Too Long"), "Test 6");
		
		// TEST 7 - Delete all before merging
		ArticleDatabase.deleteAllArticles();
		assertTrue(ArticleDatabase.restoreByMerging("backup3"), "Test 7");
		
		// TEST 7 - Don't add duplicate header
		ArticleDatabase.deleteAllArticles();
		ArticleDatabase.createArticle("h1", "t4", "a4", "d4", "k4", "expert", "groupG", "b4", "r4");
		assertTrue(ArticleDatabase.restoreByMerging("backup3"), "Test 7");
	}
	
}

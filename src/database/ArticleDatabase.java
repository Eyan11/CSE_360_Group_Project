package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*; // For SQL related objects
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * <p> ArticleDatabase. </p>
 * 
 * <p> Description: Manages the Articles Table in the H2 database.</p>
 * 
 * <p> Source: Lynn Robert Carter from FirstDatabase project, DatabaseHelper class, 
 * 				available at: https://canvas.asu.edu/courses/193728/files/92728837?module_item_id=14758007 
 * 
 *     Source: Lynn Robert Carter from FirstDatabaseWithEncryption project, DatabaseHelper class, 
 * 				available at: https://canvas.asu.edu/courses/193728/assignments/5505684
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		10/26/2024 Phase 2 implementation and documentation
 * @version 2.00		11/20/2024 Phase 3 implementation and documentation
 *  
 */

public class ArticleDatabase {
	
	// Reusable variables to communicate with database
	private static String query = "";
	private static Connection connection = null;
	private static Statement statement = null; 
	private static ResultSet resultSet = null;
	private static EncryptionHelper encryptionClass = null;
	
	
	/**********
	 * Sets connection and statement variables so this class can access the database
	 */
	public static void setConnection(Connection _connection, Statement _statement) {
		connection = _connection;
		statement = _statement;
		try {
			encryptionClass = new EncryptionHelper();
		}
		catch(Exception e) {
			System.err.println("Exception in ArticleDatabase.setConnection \n\n");
			e.printStackTrace();
		}
	}
	

	/**********
	 * Creates a table called articles and initializes columns.
	 */
	public static void createTable() {
		// Create article table in database
		query = "CREATE TABLE IF NOT EXISTS articles ("
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "header VARCHAR(50) UNIQUE, "
				+ "title VARCHAR(50), "
				+ "author VARCHAR(50), "
				+ "description VARCHAR(100), "
				+ "keywords VARCHAR(50), "
				+ "level VARCHAR(20), "
				+ "groups VARCHAR(50), "
				+ "body VARCHAR(500), "
				+ "references VARCHAR(100))";
		try {
			statement.execute(query);
			System.out.println("'articles' table created if it did not already exist");
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.createTable \n\n");
			e.printStackTrace();
		}
	}
	
	
	/**********
	 * Deletes the entire articles table in database
	 */
	public static void deleteTable() {
		query = "DROP TABLE articles";		// delete database
		try {
			statement.execute(query);			// execute query
			System.out.println("'articles' table deleted");
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.deleteTable \n\n");
			e.printStackTrace();
		}
	}
	
	
	/**********
	 * Deletes all rows in articles database
	 */
	public static void deleteAllArticles() {
		query = "DELETE FROM articles";		// delete all articles in table
		try {
			statement.execute(query);			// execute query
			System.out.println("All accounts in 'articles' table deleted");
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.deleteAllArticles \n\n");
			e.printStackTrace();
		}
	}

	
	/**********************************************************************************************

	 Public Methods To Get Database Information
	
	**********************************************************************************************/


	/**********
	 * Checks if there is at least one row of data in table.
	 */
	public static boolean isTableEmpty() {
		query = "SELECT COUNT(*) AS count FROM articles";
		try {
			resultSet = statement.executeQuery(query);
			
			// While the next row exists, check next row
			if (resultSet.next()) {
				return resultSet.getInt("count") == 0;
			}
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.isTableEmpty \n\n");
			e.printStackTrace();
		}
		return true;	// for error
	}
	
	
	/**********
	 * Checks if an article has the given id number
	 */
	public static boolean doesArticleIDExist(int id) {
		
	    try {
			// Select all rows from database where id = placeholder variable ?
		    query = "SELECT COUNT(*) FROM articles WHERE id = ?";
		    PreparedStatement pstmt = connection.prepareStatement(query);
		        
	        pstmt.setInt(1, id);	// id = id
	        resultSet = pstmt.executeQuery();
	        
	        // If the next row exists
	        if (resultSet.next()) {
	            // Return true if 1 or more articles have a matching id
	            return resultSet.getInt(1) > 0;
	        }
	    }
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.doesArticleIDExist \n\n");
			e.printStackTrace();
		}
	    return false; // If an error occurs, assume article doesn't exist
	}
	
	
	/**********
	 * Checks if an article has the given header
	 */
	public static boolean doesArticleHeaderExist(String header) {
		
	    try {
			// Select all rows from database where header = placeholder variable ?
		    query = "SELECT COUNT(*) FROM articles WHERE header = ?";
		    PreparedStatement pstmt = connection.prepareStatement(query);
		    
		    // Set placeholder ? variable to header
	        pstmt.setString(1, header);
	        resultSet = pstmt.executeQuery();
	        
	        // If the next row exists
	        if (resultSet.next()) {
	            // Return true if 1 or more articles have a matching header
	            return resultSet.getInt(1) > 0;
	        }
	    }
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.doesArticleHeaderExist \n\n");
			e.printStackTrace();
		}
	    return false; // If an error occurs, assume header doesn't exist
	}
	
	
	/**********
	 * Returns the id of the article with the matching header.
	 * Returns -1 if an error occurs.
	 */
	public static int getArticleID(String header) {
		
		// Prevent getting articles if empty
		if(isTableEmpty()) {
			System.err.println("Cannot get article id because database is empty!");
			return -1;
		}
		
		try {
			// Select all rows from database where header = placeholder variable ?
		    query = "SELECT * FROM articles WHERE header = ?";
		    PreparedStatement pstmt = connection.prepareStatement(query);
		    
		    // Set placeholder ? variable to header
	        pstmt.setString(1, header);
	        resultSet = pstmt.executeQuery();
	        
	        // If the next row exists, return it's id
	        if (resultSet.next())
	            return resultSet.getInt("id");
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.getAllArticles \n\n");
			e.printStackTrace();
		}
		return -1;		// For error
	}
	
	
	/**********
	 * Returns the id, header, and title for every article in table as a String for ModifyArticlesGUI.
	 * Format is "id1+header1+title1+group1|id2+header2+title2+group2|...".
	 */
	public static String getAllArticles() {
		
		// Prevent getting articles if empty
		if(isTableEmpty()) {
			System.err.println("Cannot get all articles because database is empty!");
			return "";
		}
		// Prevent getting an article that does not exist
		if(!LoginTracker.isLoggedIn()) {
			System.err.println("Cannot get all articles because nobody is logged in on LoginTracker!");
			return "";
		}
		
		// Select all rows from database
		query = "SELECT * FROM articles"; 
		String returnString = "";
		String groups = "";
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query); 	// Execute query
	
			// While the next row exists, check next row
			while(resultSet.next()) { 
				
				groups = resultSet.getString("groups");
				
				// If logged in user doesn't have viewing rights for all groups in article, skip it
				if(!GroupDatabase.hasRightsForAllGroups(groups, LoginTracker.getUsername(), true)) {
					System.out.println("Not getting article because logged in user does not have viewing rights for all groups: " + groups);
					continue;
				}
				
				// Get current article info
				returnString += resultSet.getInt("id") + "+"; 
				returnString += resultSet.getString("header") + "+";
				returnString += resultSet.getString("title") + "+";
				returnString += groups + "|";
			}
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.getAllArticles \n\n");
			e.printStackTrace();
		}
		return returnString;	// for error
	}
	
	
	/**********
	 * Returns the all information about an article given its id number.
	 * in format of "id+header+title+author+description+keywords+contentLevel+groups+body+references"
	 */
	public static String getArticleByID(int id) {
		
		// Prevent getting an article that does not exist
		if(!doesArticleIDExist(id)) {
			System.err.println("Cannot get article id: " + id + " because it is not found in database!");
			return "";
		}
		
	    String returnString = "";
	    String groups = "";
	    
	    try {
			// Select the row from database where id = placeholder variable ?
		    query = "SELECT * FROM articles WHERE id = ?";
		    PreparedStatement pstmt = connection.prepareStatement(query);
		    
	        pstmt.setInt(1, id);				// id = id
	        resultSet = pstmt.executeQuery();	// Execute query
	        
	        // While next row exists, check next row
	        if (resultSet.next()) {
	        	
				groups = resultSet.getString("groups");
				
				// If logged in user doesn't have viewing rights for all groups in article, don't return article
				if(!GroupDatabase.hasRightsForAllGroups(groups, LoginTracker.getUsername(), true)) {
					System.out.println("Not getting article because logged in user does not have viewing rights for all groups: " + groups);
					return "";
				}
				
	        	// Get article info
	        	returnString += id + "+";
	        	returnString += resultSet.getString("header") + "+"; 
	        	returnString += resultSet.getString("title") + "+";
	        	returnString += resultSet.getString("author") + "+";
	        	returnString += resultSet.getString("description") + "+"; 
	        	returnString += resultSet.getString("keywords") + "+";
	        	returnString += resultSet.getString("level") + "+";
	        	returnString += groups + "+";
	        	        		
	        	
	        	// Only print body if user is not an admin
	        	if(!LoginTracker.usingAdminRole()) {
	        		// If article body is encrypted
		        	if(GroupDatabase.shouldArticleBeEncrypted(resultSet.getString("groups"))) {
			        	// Decrypt body using title as IV and add it to return string
						char[] decryptedBody = encryptionClass.toCharArray(encryptionClass.decrypt(
								Base64.getDecoder().decode(resultSet.getString("body")), 
								encryptionClass.getInitializationVector(resultSet.getString("title").toCharArray())));
						returnString += encryptionClass.convertToString(decryptedBody) + "+";
		        	}
		        	// If article body is not encrypted, return as it is stored in database
		        	else
		        		returnString += resultSet.getString("body") + "+";
	        	}
	        	
	        	returnString += resultSet.getString("references"); 
	        }
	    }
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.getArticleByID \n\n");
			e.printStackTrace();
		}
		catch(Exception e) {
			System.err.println("Exception in ArticleDatabase.getArticleByID \n\n");
			e.printStackTrace();
		}
        return returnString;
	}
	
	
	/**********
	 * Returns the sequence number, title, author, and description as String for all matching articles
	 * 	in format of "Groups: text1, text2|Content Levels: 1 beginner, 3 advanced|
	 * 	Sequence Number: 1\nTitle: text\nAuthor: text\nDescription: text\n\n..."
	 */
	public static String searchByContents(String groupFilter, String levelFilter, String searchContents) {
		// Prevent searching if no articles exist
		if(ArticleDatabase.isTableEmpty()) {
			System.err.println("Can't search by contents becase table is empty!");
			return "";
		}
		// Prevent filters from being too long
		if(groupFilter.length() > 50 || levelFilter.length() > 50 || searchContents.length() > 100) {
			System.err.println("Can't search by contents because the search parameters too long!");
			return "";
		}
		
		String returnString = "";
		try {
			// Get a result set of all matching articles
			resultSet = craftResultSetToSearchArticles(groupFilter, levelFilter, searchContents);
			
			// Temporary variables for collecting data
			String returnGroups = "Groups: ";
			String returnLevels = "Content Levels: ";
			String tempGroups = "";
			String[] groupsArr = new String[10];
			int numBeg = 0;
			int numInt = 0;
			int numAdv = 0;
			int numExp = 0;
			int seq = 1;	// stores sequence number
			
			
			// While the next row exists, check next row
			while(resultSet.next()) { 
				
				tempGroups = resultSet.getString("groups");		// Get all article groups
				groupsArr = tempGroups.split(" & ");			// Separate all groups into array
				
				// If user does NOT have authorization for all groups in article
				if(!GroupDatabase.hasRightsForAllGroups(tempGroups, LoginTracker.getUsername(), true)) {
					System.out.println("Not getting article because logged in user does not have viewing rights for all groups: " + tempGroups);
					continue;		// Skip article
				}
				
				// Get current article info
				returnString += "Sequence Number: " + seq + "\n";
				returnString += "Title: " + resultSet.getString("title") + "\n";
				returnString += "Author: " + resultSet.getString("author") + "\n";
				returnString += "Description: " + resultSet.getString("description") + "\n\n";
				
				// Get content level of article
				switch(resultSet.getString("level").toLowerCase()) {
					case "beginner":
						numBeg++;
						break;
					case "intermediate":
						numInt++;
						break;
					case "advanced":
						numAdv++;
						break;
					case "expert":
						numExp++;
						break;
					default:
						System.err.println("Content level not returned in ArticleDatabase.searchByContents sequence number: " + seq);
						break;
				}
				
				// Loop through each group
				for(String group : groupsArr) 
					if(!returnGroups.contains(group))	// If group isn't already in list
						returnGroups += group + ", ";	// Add group to list
				
				seq++;	// Increment sequence number
			}
	
			
			// If at least one group added, remove the last ", " in groups string
			if (returnGroups.length() > 8)
				returnGroups = returnGroups.substring(0, returnGroups.length() - 2);

			
			// Build content levels string
			if(numBeg > 0)
				returnLevels += numBeg + " beginner, ";
			if(numInt > 0)
				returnLevels += numInt + " intermediate, ";
			if(numAdv > 0)
				returnLevels += numAdv + " advanced, ";
			if(numExp > 0)
				returnLevels += numExp + " expert, ";
			
			// If at least 1 article is returned, remove the last ", " from returnLevels
			if(numBeg + numInt + numAdv + numExp > 0)
				returnLevels = returnLevels.substring(0, returnLevels.length() - 2);	
			
			// Combine all return strings into one
			returnString = returnGroups + "\n" + returnLevels + "\n\n" + returnString;
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.searchByContents \n\n");
			e.printStackTrace();
		}
		return returnString;
	}
	
	
	/**********************************************************************************************

	 Public Methods To Set Database Information
	
	**********************************************************************************************/
	
	
	/**********
	 * Creates a new article and stores in database, returns if successful or not.
	 * Note: if an article has multiple groups or keywords, separate with " & ", example: "group1 & group2 & group3".
	 * Note: all fields cannot have the "+" or "|" symbol in them because it is used to separate data when returning articles
	 */
	public static boolean createArticle(String header, String title, String author, String description, 
			String keywords, String level, String groups, String body, String references) {
		
		// Prevent printing an article that does not exist
		if(doesArticleHeaderExist(header)) {
			System.err.println("Cannot create article because header: " + header + " already exists in database!");
			return false;
		}
		// Prevent "+" or "|" symbol in any field since it is used to separate article info
		if(containsInvalidCharacter(header + title + author + description + keywords + level + groups + body + references)) {
			System.err.println("Cannot create article because a field contains a '+' or '|' symbol");
			return false;
		}

		level = level.toLowerCase();
		// Prevents level from being anything other than "beginner", "intermediate", "advanced", or "expert"
		if(!level.equals("beginner") && !level.equals("intermediate") && !level.equals("advanced") && !level.equals("expert")) {
			System.err.println("Cannot create article since content level is not 'beginner', 'intermediate', 'advanced, or 'expert");
			return false;
		}
		// Prevents very long header, title, author, keywords, or groups
		if(header.length() > 50 || title.length() > 50 || author.length() > 50 || 
				keywords.length() > 50 || groups.length() > 50) {
			System.err.println("Cannot create article since header, title, keywords, or groups are over 50 characters");
			return false;
		}
		// Prevents very long description or references
		if(description.length() > 100 || references.length() > 100) {
			System.err.println("Cannot create article since description or references are over 100 characters");
			return false;
		}
		// Prevents very long body
		if(body.length() > 500) {
			System.err.println("Cannot create article since body is over 500 characters");
			return false;
		}
		// Prevent logged in user from assigning a group to an article that they are not a group admin of
		if(!GroupDatabase.hasRightsForAllGroups(groups, LoginTracker.getUsername(), false)) {
			System.err.println("Can't create article because user is not a group admin of all groups: " + groups);
			return false;
		}
	
		try {
			// Insert a new row into database and fill in the following column values
			query = "INSERT INTO articles (header, title, author, description, keywords, level, groups, body, references) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			
			// If at least one group is of special access type, article must be encrypted
			if(GroupDatabase.shouldArticleBeEncrypted(groups)) {
				// Encrypt body using title as IV
				body = Base64.getEncoder().encodeToString(
						encryptionClass.encrypt(body.getBytes(), 
						encryptionClass.getInitializationVector(title.toCharArray())));
				System.out.println("Encrypted the body of new article");
			}
			
			
			// Set the placeholder ? variables
			pstmt.setString(1, header);
			pstmt.setString(2, title);
			pstmt.setString(3, author);
			pstmt.setString(4, description);
			pstmt.setString(5, keywords);
			pstmt.setString(6, level);
			pstmt.setString(7, groups);
			pstmt.setString(8, body);	// May or may not be encrypted
			pstmt.setString(9, references);
			pstmt.executeUpdate();		// Execute query
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.createArticle \n\n");
			e.printStackTrace();
		}
		catch(Exception e) {
			System.err.println("Exception in ArticleDatabase.createArticle \n\n");
			e.printStackTrace();
		}
		
		// Print and return result
		if(doesArticleHeaderExist(header)) {
			System.out.println("Article successfully created!");
			return true;
		}
		else {
			System.err.println("Article not created, an error occured!");
			return false;
		}
	}
	
	
	/**********
	 * Deletes the article that matches the given id parameter
	 */
	public static boolean deleteArticle(int id) {
		
		// Prevent deleting an article that does not exist
		if(!doesArticleIDExist(id)) {
			System.err.println("Cannot delete article id: " + id + " because it is not found in database!");
			return false;
		}
		// Prevent logged in user from deleting an article where they are not a group admin of all groups
		if(!GroupDatabase.hasRightsForAllGroups(getArticleGroups(id), LoginTracker.getUsername(), false)) {
			System.err.println("Can't delete article because user is not a group admin of all groups in article id: " + id);
			return false;
		}
		
		try {
			// Deletes the row from database where id = placeholder variable ?
			query = "DELETE FROM articles WHERE id = ?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			
			pstmt.setInt(1, id);		// id = id
			pstmt.executeUpdate();		// Execute query
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.deleteArticle \n\n");
			e.printStackTrace();
		}
		
		// Print and return result
		if(doesArticleIDExist(id)) {
			System.err.println("Article not deleted, an error occured!");
			return false;
		}
		else {
			System.out.println("Article successfully deleted!");
			return true;
		}
	}
	
	
	/**********
	 * Edits the article that matches the given id parameter
	 */
	public static boolean editArticle(int id, String header, String title, String author, String description, 
			String keywords, String level, String groups, String body, String references) {
		
		// Prevent editing an article that does not exist
		if(!doesArticleIDExist(id)) {
			System.err.println("Cannot edit article id: " + id + " because it is not found in database!");
			return false;
		}
		// Prevent editing an article if header is updated and it already exists
		if(!getArticleHeader(id).equals(header) && doesArticleHeaderExist(header)) {
			System.err.println("Cannot edit article because header: " + header + " already exists in database!");
			return false;
		}
		// Prevent editing article if you are not logged in as either an instructor or admin
		if(!LoginTracker.usingInstructorRole() && !LoginTracker.usingAdminRole()) {
			System.err.println("Cannot edit article because user is not logged in as instructor or admin in LoginTracker!");
			return false;
		}
		// Prevent "+" or "|" symbol in any field since it is used to separate article info
		if(containsInvalidCharacter(header + title + author + description + keywords + level + groups + body + references)) {
			System.err.println("Cannot edit article because a field contains a '+' or '|' symbol");
			return false;
		}
		// Prevent logged in user from editing an article where they are not a group admin of all groups (use old groups)
		if(!GroupDatabase.hasRightsForAllGroups(getArticleGroups(id), LoginTracker.getUsername(), false)) {
			System.err.println("Can't edit article because user is not a group admin of all previous groups in article id: " + id);
			return false;
		}
		// Prevent logged in user from editing an article where they are not a group admin of all groups (use new groups)
		if(!GroupDatabase.hasRightsForAllGroups(groups, LoginTracker.getUsername(), false)) {
			System.err.println("Can't delete article because user is not a group admin of all new groups in article id: " + id);
			return false;
		}
		
		level = level.toLowerCase();
		// Prevents level from being anything other than "beginner", "intermediate", "advanced", or "expert"
		if(!level.equals("beginner") && !level.equals("intermediate") && !level.equals("advanced") && !level.equals("expert")) {
			System.err.println("Cannot edit article since content level is not 'beginner', 'intermediate', 'advanced, or 'expert");
			return false;
		}
		// Prevents very long header, title, author, keywords, or groups
		if(header.length() > 50 || title.length() > 50 || author.length() > 50 || 
				keywords.length() > 50 || groups.length() > 50) {
			System.err.println("Cannot edit article since header, title, keywords, or groups are over 50 characters");
			return false;
		}
		// Prevents very long description or references
		if(description.length() > 100 || references.length() > 100) {
			System.err.println("Cannot edit article since description or references are over 100 characters");
			return false;
		}
		// Prevents very long body
		if(body.length() > 500) {
			System.err.println("Cannot edit article since body is over 500 characters");
			return false;
		}
		

		try {
			
			// If logged in as instructor
			if(LoginTracker.usingInstructorRole()) {
				// Update all columns in articles column where id matches placeholder variable ? (including body)
				query = "UPDATE articles "
						+ "SET header = ?, title = ?, author = ?, description = ?, keywords = ?, "
						+ "level = ?, groups = ?, body = ?, references = ? WHERE id = ?";
			}
			// If logged in as admin
			else {
				// Update all columns in articles column where id matches placeholder variable ? (not including body)
				query = "UPDATE articles "
						+ "SET header = ?, title = ?, author = ?, description = ?, keywords = ?, "
						+ "level = ?, groups = ?, references = ? WHERE id = ?";
			}
			// Prepare the previous query to be executed
			PreparedStatement pstmt = connection.prepareStatement(query);
				
			// Set the placeholder ? variables
			pstmt.setString(1, header);
			pstmt.setString(2, title);
			pstmt.setString(3, author);
			pstmt.setString(4, description);
			pstmt.setString(5, keywords);
			pstmt.setString(6, level);
			pstmt.setString(7, groups);
			
			// If logged in as instructor, edit body and the rest of article
			if(LoginTracker.usingInstructorRole()) {
				// If at least one group is of special access type, article must be encrypted
				if(GroupDatabase.shouldArticleBeEncrypted(groups)) {
					
					// Encrypt body using title as IV
					String encryptedBody = Base64.getEncoder().encodeToString(
							encryptionClass.encrypt(body.getBytes(), 
							encryptionClass.getInitializationVector(title.toCharArray())));
					
					// Use encrypted body as article body
					pstmt.setString(8, encryptedBody);
					System.out.println("Encrypted the body of the edited article");
				}
				// Don't encrypt body
				else
					pstmt.setString(8, body);
				
				pstmt.setString(9, references);
				pstmt.setInt(10, id);
				
				pstmt.executeUpdate();	// execute query
			}
			
			// If logged in as admin, skip body and edit the rest of article
			else {
				pstmt.setString(8, references);
				pstmt.setInt(9, id);
				
				pstmt.executeUpdate();	// execute query
			}
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.editArticle \n\n");
			e.printStackTrace();
		}
		catch(Exception e) {
			System.err.println("Exception in ArticleDatabase.editArticle \n\n");
			e.printStackTrace();
		}
		
		String articleContents = getArticleByID(id);

		// Print and return result
		// Instructors can see full article
		if(LoginTracker.usingInstructorRole() && 
				articleContents.equals(id + "+" + header + "+" + title + "+" + author + "+" + description + 
				"+" + keywords + "+" + level + "+" + groups + "+" + body + "+" + references)) {
			
			System.out.println("Successfully edited article id: " + id);
			return true;
		}
		// Admins don't see article body
		else if(LoginTracker.usingAdminRole() && 
				articleContents.equals(id + "+" + header + "+" + title + "+" + author + "+" + description + 
				"+" + keywords + "+" + level + "+" + groups + "+" + references)) {
			
			System.out.println("Successfully edited article id: " + id);
			return true;
		}
		else {
			System.err.println("Failed to edit article id: " + id);
			return false;
		}
	}
	
	
	/**********************************************************************************************

	 Public Methods To Backup and Restore Database
	
	**********************************************************************************************/
	
	
	/**********
	 * Prints all article table contents to a user specified file
	 */
	public static boolean backupArticles(String filePath, String groups) {
		// Prevent long group filter
		if(groups.length() > 50) {
			System.err.println("Can't backup articles because groups filter is over 50 characters!");
			return false;
		}
		// Prevent empty or long file path
		else if(filePath.length() <= 0 || filePath.length() > 100) {
			System.err.println("Can't backup articles because file path must be between 1 and 100 charactes!");
			return false;
		}
		
		BufferedWriter writer = null;
		try {
			// write to file
			writer = new BufferedWriter(new FileWriter(filePath));

			// Returns result set of all articles with matching groups
			resultSet = craftResultSetToGetArticlesByGroups(groups);
			String resultGroups;
	
			// While the next row exists, check next row
			while(resultSet.next()) { 
				
				resultGroups = resultSet.getString("groups");
				
				// If logged in user doesn't have admin rights for all groups in article, skip it
				if(!GroupDatabase.hasRightsForAllGroups(resultGroups, LoginTracker.getUsername(), false)) {
					System.out.println("Skipping backup for article because logged in user does not have viewing rights for all groups: " + resultGroups);
					continue;
				}
				
	        	// Write all article info into file
				writer.write("\n" + resultSet.getInt("id") + "\n");
				writer.write(resultSet.getString("header") + "\n"); 
				writer.write(resultSet.getString("title") + "\n"); 
				writer.write(resultSet.getString("author") + "\n"); 
				writer.write(resultSet.getString("description") + "\n"); 
				writer.write(resultSet.getString("keywords") + "\n"); 
				writer.write(resultSet.getString("level") + "\n"); 
				writer.write(resultGroups + "\n"); 
				writer.write(resultSet.getString("body") + "\n"); 
				writer.write(resultSet.getString("references") + "\n"); 
				
				// Print result
				System.out.println("Article id: " + resultSet.getInt("id") + " backed up to: " + filePath);
			} 
			writer.close();		// Stop writing to file
			return true;
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.backupArticles \n\n");
			e.printStackTrace();
		}
		catch(IOException e) {
			System.err.println("IOException in ArticleDatabase.backupArticles \n\n");
			System.err.println("File path: " + filePath + " is not found!");
			e.printStackTrace();
		}
		return false;	// for errors
	}
	
	
	/**********
	 * Gathers backup articles info from file and replaces the current table with restored table
	 */
	public static boolean restoreByOverriding(String filePath) {
		// Prevent empty or long file path
		if(filePath.length() <= 0 || filePath.length() > 100) {
			System.err.println("Can't restore by overriding articles because file path must be between 1 and 100 charactes!");
			return false;
		}
		
		BufferedReader reader = null;
		boolean returnValue = true;
		try {
			// Read from file
			reader = new BufferedReader(new FileReader(filePath));
		
			// Temporary strings to collect file contents
			String idString, header, title, author, description, keywords, level, groups, body, references = null;
			
			// Wipe the articles table
			deleteAllArticles();
			
			// While the next line isn't empty
			while((reader.readLine()) != null) {
				// Get article info from file
				idString = reader.readLine();
				int id = Integer.parseInt(idString);
				header = reader.readLine();
				title = reader.readLine();
				author = reader.readLine();
				description = reader.readLine();
				keywords = reader.readLine();
				level = reader.readLine();
				groups = reader.readLine();
				body = reader.readLine();
				references = reader.readLine();
				
				// Insert a new row into database and fill in the following column values
				query = "INSERT INTO articles (id, header, title, author, description, "
						+ "keywords, level, groups, body, references) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt = connection.prepareStatement(query);
					
				// Set the placeholder ? variables
				pstmt.setInt(1, id);
				pstmt.setString(2, header);
				pstmt.setString(3, title);
				pstmt.setString(4, author);
				pstmt.setString(5, description);
				pstmt.setString(6, keywords);
				pstmt.setString(7, level);
				pstmt.setString(8, groups);
				pstmt.setString(9, body);
				pstmt.setString(10, references);
				pstmt.executeUpdate();		// Execute query
				
				// Print result to console
				if(doesArticleIDExist(id))
					System.out.println("Article id: " + idString + " successfully restored from " + filePath);
				else {
					System.err.println("Article id: " + idString + " failed to be restored from " + filePath);
					returnValue = false;
				}
			}
			reader.close();		// Stop reading from file
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.restoreByOverriding \n\n");
			e.printStackTrace();
			returnValue = false;
		}
		catch(IOException e) {
			System.err.println("IOException in ArticleDatabase.restoreByOverriding \n\n");
			System.err.println("File path: " + filePath + " is not found!");
			e.printStackTrace();
			returnValue = false;
		}
		return returnValue;		// False if any article wasn't added or exception occurred
	}
	
	
	/**********
	 * Gathers backup articles info from file and replaces the current table with restored table
	 */
	public static boolean restoreByMerging(String filePath) {
		// Prevent empty or long file path
		if(filePath.length() <= 0 || filePath.length() > 100) {
			System.err.println("Can't restore by merging articles because file path must be between 1 and 100 charactes!");
			return false;
		}
		
		BufferedReader reader = null;
		boolean returnValue = true;
		try {
			// Read from file
			reader = new BufferedReader(new FileReader(filePath));
		
			// Temporary strings to collect file contents
			String idString, header, author, title, description, keywords, level, groups, body, references = null;
			int id = 0;
			
			// While the next line isn't empty
			while((reader.readLine()) != null) {
				// Get article info from file
				idString = reader.readLine();
				id = Integer.parseInt(idString);	// change id from string to int
				header = reader.readLine();
				title = reader.readLine();
				author = reader.readLine();
				description = reader.readLine();
				keywords = reader.readLine();
				level = reader.readLine();
				groups = reader.readLine();
				body = reader.readLine();
				references = reader.readLine();
				
				// Skip adding article if it already exists
				if(doesArticleIDExist(id)) {
					System.out.println("Article id: " + idString + " not added to article table because id already exists");
					continue;
				}
				// Prevent adding duplicate header
				else if(doesArticleHeaderExist(header)) {
					System.out.println("Article id: " + idString + "not added to article table because header: " + header + "already exists");
					continue;
				}
				
				// Insert a new row into database and fill in the following column values
				query = "INSERT INTO articles (id, header, title, author, description, "
						+ "keywords, level, groups, body, references) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt = connection.prepareStatement(query);
					
				// Set the placeholder ? variables
				pstmt.setInt(1, id);
				pstmt.setString(2, header);
				pstmt.setString(3, title);
				pstmt.setString(4, author);
				pstmt.setString(5, description);
				pstmt.setString(6, keywords);
				pstmt.setString(7, level);
				pstmt.setString(8, groups);
				pstmt.setString(9, body);
				pstmt.setString(10, references);
				pstmt.executeUpdate();		// Execute query
				
				// Print result to console
				if(doesArticleIDExist(id))
					System.out.println("Article id: " + idString + " successfully restored from " + filePath);
				else {
					System.err.println("Article id: " + idString + " failed to be restored from " + filePath);
					returnValue = false;
				}
			}
			reader.close();		// Stop reading from file
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.restoreByMerging \n\n");
			e.printStackTrace();
			returnValue = false;
		}
		catch(IOException e) {
			System.err.println("IOException in ArticleDatabase.restoreByMerging \n\n");
			System.err.println("File path: " + filePath + " is not found!");
			e.printStackTrace();
			returnValue = false;
		}
		return returnValue;		// False if any article wasn't added or exception occurred
	}
	
	
	/**********************************************************************************************

	 Private Helper Method
	
	**********************************************************************************************/
	
	
	/**********
	 * Returns the result set containing all rows with matching groups
	 * Note: groups should be separated by a comma for OR operation and an ampersand for AND operation
	 * Note: if groups = empty string or whitespace then return all articles.
	 */
	private static ResultSet craftResultSetToGetArticlesByGroups(String groups) throws SQLException {
		
		groups.toLowerCase();	// All groups are lowercase
		groups.trim();			// Remove excess whitespace
		
		// If searching for all queries
		if(groups.equals("all") || groups.equals("")) {
			query = "SELECT * FROM articles";			// Get all articles
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			return statement.executeQuery(query); 		// Return result set
		}
		// Else filter the search for the requested groups
		
		String[] groupsArr = groups.split(",");		// Separate all groups into an array
		query = "SELECT * FROM articles WHERE";		// Craft beginning of query
		
		// For all groups in array
		for(int i = 0; i < groupsArr.length; i++) {
			groupsArr[i] = groupsArr[i].trim();		// Remove excess whitespace
			query += " groups LIKE ? OR";			// Add a filter to the query
		}
		
		query = query.substring(0, query.length() - 3);		// Remove the last " OR" of query
    	// Allow statement to be scrollable so result set pointer can be reset to beginning
	    PreparedStatement pstmt = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    
	    // For all groups, set the placeholder ? to the group
	    for(int i = 0; i < groupsArr.length; i++) {
	        pstmt.setString(i + 1, "%" + groupsArr[i] + "%");
	    }
	    
        return pstmt.executeQuery();	// Return result set
	}
	
	
	/**********
	 * Returns the result set with matching groups, content level, and search contents.
	 * It does NOT take into account the logged in user's group access
	 * Note: groups should be separated by a comma for OR operation and an ampersand for AND operation
	 * Note: if groups, level filter, or search contents = "" or "all" then ignore that filter and return all
	 */
	private static ResultSet craftResultSetToSearchArticles(String groupFilter, String levelFilter, 
			String searchContents) throws SQLException {
		
		// All groups are lowercase
		groupFilter.toLowerCase();
		levelFilter.toLowerCase();
		// Remove excess whitespace
		groupFilter.trim();
		levelFilter.trim();
		searchContents.trim();
		
		
		// *** Sort Groups ************************************************************************
		String[] groupsArr = groupFilter.split(",");	// Separate all groups into an array
		
		if(groupFilter.equals("all") || groupFilter.equals(""))
			query = "SELECT * FROM articles WHERE ( TRUE )";	// initialize query and move onto level filter
		// Else filter by groups
		else {
			query = "SELECT * FROM articles WHERE (";		// Craft beginning of query
			
			// For all groups in array
			for(int i = 0; i < groupsArr.length; i++) {
				groupsArr[i] = groupsArr[i].trim();			// Remove excess whitespace
				query += " groups LIKE ? OR";				// Add a filter to the query
			}
			
			query = query.substring(0, query.length() - 3);		// Remove the last " OR" of query
			query += " )";	// Add closing parenthesis around group query filter
		}
		// ****************************************************************************************
		
		
		// *** Sort Level *****************************************************************
		if(levelFilter.equals("all") || levelFilter.equals(""))
			query += " AND";						// Ignore levels filter
		else
			query += " AND ( level = ? ) AND";		// Filter by levels
		// ****************************************************************************************
		
		
		// *** Sort By Contents *******************************************************************
		if(searchContents.equals("all") || searchContents.equals(""))
			query += " ( TRUE )";					// Ignore filter by contents
		else										// Search for content in title, author, or description
			query += " ( title LIKE ? OR author LIKE ? OR description LIKE ? )";
		// ****************************************************************************************
		
		
		// *** Enter Values Into Query ************************************************************
		PreparedStatement pstmt = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		int index = 1;	// Used for pstmt index when setting strings
		
		// If filtering by group
		if(!groupFilter.equals("all") && !groupFilter.equals("")) {
			
			// Set placeholder ? for all groups in groupsArr
			for(String group : groupsArr) {
				pstmt.setString(index, "%" + group + "%");
				index++;
			}
		}

		// If filtering by level
		if(!levelFilter.equals("all") && !levelFilter.equals("")) {
			pstmt.setString(index, levelFilter);
			index++;
		}
		
		// If filtering by contents
		if(!searchContents.equals("all") && !searchContents.equals("")) {
			// Set last 3 filters for title, author, and description
			pstmt.setString(index, "%" + searchContents + "%");
			pstmt.setString(index + 1, "%" + searchContents + "%");
			pstmt.setString(index + 2, "%" + searchContents + "%");
		}
		// ****************************************************************************************
		
		return pstmt.executeQuery();	// Return result set
	}
	
	
	/**********
	 * Returns true if input string contains a "+" or "|" character
	 */
	private static boolean containsInvalidCharacter(String inputString) {
		// Prevent input from containing "+" symbol
		if(inputString.contains("+"))
			return true;
		// Prevent input from containing "|" symbol
		else if(inputString.contains("|"))
			return true;
		// No invalid characters in input string
		else
			return false;
	}
	
	
	/**********
	 * Returns all groups in article.
	 */
	private static String getArticleGroups(int id) {
		
	    try {
			// Select all rows from database where id = placeholder variable ?
			query = "SELECT * FROM articles WHERE id = ?";
		    PreparedStatement pstmt = connection.prepareStatement(query);
		    
	        pstmt.setInt(1, id);	// id = id
	        resultSet = pstmt.executeQuery();
	        
	        // If the next row exists
	        if (resultSet.next()) {
	            // Return the groups column of article
	            return resultSet.getString("groups");
	        }
	    }
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.doesArticleIDExist \n\n");
			e.printStackTrace();
		}
	    return "";	// No id or error
	}
	
	
	/**********
	 * Returns the header of the article with the matching id.
	 * Returns empty string if no header exists.
	 */
	public static String getArticleHeader(int id) {
		
		try {
			// Select all rows from database where id = placeholder variable ?
		    query = "SELECT * FROM articles WHERE id = ?";
		    PreparedStatement pstmt = connection.prepareStatement(query);
		    
		    // Set placeholder ? variable to header
	        pstmt.setInt(1, id);
	        resultSet = pstmt.executeQuery();
	        
	        // If the next row exists, return it's header
	        if (resultSet.next())
	            return resultSet.getString("header");
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.getAllArticles \n\n");
			e.printStackTrace();
		}
		return "";		// No id or error
	}
}

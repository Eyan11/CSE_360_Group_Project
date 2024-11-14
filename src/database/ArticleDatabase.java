package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*; // For SQL related objects

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
 * @version TODO
 *  
 */

public class ArticleDatabase {
	
	// Reusable variables to communicate with database
	private static String query = "";
	private static Connection connection = null;
	private static Statement statement = null; 
	private static ResultSet resultSet = null;
	
	
	/**********
	 * Sets connection and statement variables so this class can access the database
	 */
	public static void setConnection(Connection _connection, Statement _statement) {
		connection = _connection;
		statement = _statement;
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
		
		// Select all rows from database where id = placeholder variable ?
	    query = "SELECT COUNT(*) FROM articles WHERE id = ?";
	    try {
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
	 * Checks if an article has the given title
	 */
	public static boolean doesArticleHeaderExist(String header) {
		
		// Select all rows from database where header = placeholder variable ?
	    String query = "SELECT COUNT(*) FROM articles WHERE header = ?";
	    try {
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
	    return false; // If an error occurs, assume title doesn't exist
	}
	
	
	/**********
	 * Returns the id, header, and title for every article in table as a String
	 * in format of "id1+header1+title1+group1|id2+header2+title2+group2|..."
	 */
	public static String getAllArticles() {
		
		// Prevent getting articles if empty
		if(isTableEmpty()) {
			System.err.println("Cannot get all articles because database is empty!");
			return "";
		}
		
		// Select all rows from database
		query = "SELECT * FROM articles"; 
		String returnString = "";
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query); 	// Execute query
	
			// While the next row exists, check next row
			while(resultSet.next()) { 
				// Get current article info
				returnString += resultSet.getInt("id") + "+"; 
				returnString += resultSet.getString("header") + "+";
				returnString += resultSet.getString("title") + "+";
				returnString += resultSet.getString("groups") + "|";
			}
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.getAllArticles \n\n");
			e.printStackTrace();
		}
		return returnString;	// for error
	}
	
	
	/**********
	 * Returns the all information about an article given its id number
	 * in format of "id+header+title+author+description+keywords+content level+groups+body+references"
	 */
	public static String getArticleByID(int id) {
		
		// Prevent getting an article that does not exist
		if(!doesArticleIDExist(id)) {
			System.err.println("Cannot get article id: " + id + " because it is not found in database!");
			return "";
		}
		
		// Select the row from database where id = placeholder variable ?
	    query = "SELECT * FROM articles WHERE id = ?";
	    String returnString = "";
	    
	    try {
		    PreparedStatement pstmt = connection.prepareStatement(query);
		        
	        pstmt.setInt(1, id);				// id = id
	        resultSet = pstmt.executeQuery();	// Execute query
	        
	        // While next row exists, check next row
	        if (resultSet.next()) {
	        	// Get article info
	        	returnString += id + ",";
	        	returnString += resultSet.getString("header") + "+"; 
	        	returnString += resultSet.getString("title") + "+";
	        	returnString += resultSet.getString("author") + "+";
	        	returnString += resultSet.getString("description") + "+"; 
	        	returnString += resultSet.getString("keywords") + "+";
	        	returnString += resultSet.getString("level") + "+";
	        	returnString += resultSet.getString("groups") + "+";
	        	returnString += resultSet.getString("body") + "+";
	        	returnString += resultSet.getString("references"); 
	        }
	    }
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.getArticleByID \n\n");
			e.printStackTrace();
		}
        return returnString;
	}
	
	
	/**********
	 * Returns the sequence number, title, author, and description as String for all matching articles
	 * 	in format of "Groups: group1, group2|Content Levels: 1 beginner, 3 advanced|
	 * 	1+title1+author1+description1|\n2+title2+author2+description2|\n...".
	 */
	public static String searchByContents(String groupFilter, String levelFilter, String searchContents) {
		
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

			
			int i = 1;	// stores sequence number
			
			// While the next row exists, check next row
			while(resultSet.next()) { 
				// Get current article info
				returnString += i + ","; 	// sequence number
				returnString += resultSet.getString("title") + "+";
				returnString += resultSet.getString("author") + "+";
				returnString += resultSet.getString("description") + "|";
				returnString += "\n"; // adds new line for each article
				
				
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
						System.err.println("Content level not returned in ArticleDatabase.searchByContents sequence number: " + i);
						break;
				}
				
				
				// Get groups in article
				tempGroups = resultSet.getString("groups");
				groupsArr = tempGroups.split(" & ");
				
				// Loop through each group
				for(int j = 0; i < groupsArr.length; j++) {
					
					// If group is not already in list, add it to list
					if(!returnGroups.contains(groupsArr[j]))
						returnGroups += groupsArr[j] + ", ";
				}
			}
	
			
			// If non-empty, remove the last ", " in groups string
			if (returnGroups.length() > 0)
				returnGroups = returnGroups.substring(0, returnGroups.length() - 1);
			
			// Build content levels string
			if(numBeg > 0)
				returnLevels += numBeg + " beginner";
			if(numInt > 0)
				returnLevels += ", " + numInt + " intermediate";
			if(numAdv > 0)
				returnLevels += ", " + numAdv + " advanced";
			if(numExp > 0)
				returnLevels += ", " + numExp + " expert";
			
			// If non-empty, remove the last "|\n" in return string
			if (returnString.length() > 0)
				returnString = returnString.substring(0, returnString.length() - 2);
			
			// Combine all return strings into one
			returnString = returnGroups + "|" + returnLevels + "|" + returnString;
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
		
		
		// Insert a new row into database and fill in the following column values
		query = "INSERT INTO articles (header, title, author, description, keywords, level, groups, body, references) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
				
			// Set the placeholder ? variables
			pstmt.setString(1, header);
			pstmt.setString(2, title);
			pstmt.setString(3, author);
			pstmt.setString(4, description);
			pstmt.setString(5, keywords);
			pstmt.setString(6, level);
			pstmt.setString(7, groups);
			pstmt.setString(8, body);
			pstmt.setString(9, references);
			pstmt.executeUpdate();		// Execute query
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.createArticle \n\n");
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
		
		// Deletes the row from database where id = placeholder variable ?
		query = "DELETE FROM articles WHERE id = ?";
		
		try {
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
		// Prevent editing an article that does not exist
		if(doesArticleHeaderExist(header)) {
			System.err.println("Cannot edit article because header: " + header + " already exists in database!");
			return false;
		}
		// Prevent "+" or "|" symbol in any field since it is used to separate article info
		if(containsInvalidCharacter(header + title + author + description + keywords + level + groups + body + references)) {
			System.err.println("Cannot edit article because a field contains a '+' or '|' symbol");
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
		
		
		// Update all columns in articles column where id matches placeholder variable ?
		query = "UPDATE articles "
				+ "SET header = ?, title = ?, author = ?, description = ?, keywords = ?, "
				+ "level = ?, groups = ?, body = ?, references = ? WHERE id = ?";
		try {
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
			pstmt.setString(8, body);
			pstmt.setString(9, references);
			pstmt.setInt(8, id);
			
			pstmt.executeUpdate();	// execute query
		}
		catch(SQLException e) {
			System.err.println("SQLException in ArticleDatabase.editArticle \n\n");
			e.printStackTrace();
		}

		// Print and return result
		if(getArticleByID(id).equals(id + "+" + header + "+" + title + "+" + author + "+" + description + 
				"+" + keywords + "+" + level + "+" + groups + "+" + body + "+" + references)) {
			
			System.out.println("Successfully edited article id: " + id);
			return true;
		}
		else {
			System.out.println("Failed to edit article id: " + id);
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
		
		BufferedWriter writer = null;
		try {
			// write to file
			writer = new BufferedWriter(new FileWriter(filePath));

			// Returns result set of all articles with matching groups
			resultSet = craftResultSetToGetArticlesByGroups(groups);
	
			// While the next row exists, check next row
			while(resultSet.next()) { 
	        	// Write all article info into file
				writer.write("\n" + resultSet.getInt("id") + "\n");
				writer.write(resultSet.getString("header") + "\n"); 
				writer.write(resultSet.getString("title") + "\n"); 
				writer.write(resultSet.getString("author") + "\n"); 
				writer.write(resultSet.getString("description") + "\n"); 
				writer.write(resultSet.getString("keywords") + "\n"); 
				writer.write(resultSet.getString("level") + "\n"); 
				writer.write(resultSet.getString("groups") + "\n"); 
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
		
		BufferedReader reader = null;
		boolean returnValue = true;
		try {
			// Read from file
			reader = new BufferedReader(new FileReader(filePath));
		
			// Temporary strings to collect file contents
			String idString, header, title, author, description, keywords, level, groups, body, references = null;
			
			// Wipe the articles table and start a new one
			deleteTable();
			createTable();
			
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
				pstmt.setString(8, body);
				pstmt.setString(9, references);
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
				pstmt.setString(3, author);
				pstmt.setString(4, description);
				pstmt.setString(5, keywords);
				pstmt.setString(3, level);
				pstmt.setString(6, groups);
				pstmt.setString(7, body);
				pstmt.setString(8, references);
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
	
	
	/********** TODO, update documentation and method
	 * Returns the result set containing all rows with matching groups
	 * groups parameter is in format of "group1,group2,group3&group4"
	 * Note: if groups = empty string or whitespace then return all articles, 
	 * 	Assumes groups parameter always contains contains a non-whitespace between every ",".
	 */
	private static ResultSet craftResultSetToGetArticlesByGroups(String groups) throws SQLException {
		
		// Get all articles, then filter through them later
		query = "SELECT * FROM articles"; 
		statement = connection.createStatement();
		
		
		// If filtering by groups (not empty or "all")
		if(!groups.trim().isEmpty() || !groups.toLowerCase().equals("all")) {
			
			// Check all articles
			while(resultSet.next()) { 
				
				// If the groups do not exist in article
				if(!resultSet.getString("groups").contains(groups))
					resultSet.deleteRow();	// Filter out article
			}
		}
		
		// TODO once I finish group database, I will make sure currently logged in user is a 
		//	group admin of all of the groups in that article
		// Check all articles with matching groups
		/*
		while(resultSet.next()) { 
			
			// If the groups do not exist in article
			if(!resultSet.getString("groups").contains(groups))
				resultSet.deleteRow();	// Filter out article
		}
		*/
		return resultSet;
	}
	
	
	/**********
	 * Returns the result set with matching groups, content level, and search contents
	 * 	for all articles that the user has permission to view.
	 */
	private static ResultSet craftResultSetToSearchArticles(String groupFilter, String levelFilter, 
			String searchContents) throws SQLException {
		
		resultSet = craftResultSetToGetArticlesByGroups(groupFilter);
		levelFilter = levelFilter.toLowerCase();
		
		// If filtering by level (not empty or "all")
		if(!levelFilter.trim().isEmpty() || !levelFilter.equals("all")) {
			
			// Check all matching groups
			while(resultSet.next()) { 
				
				// If the level filter does NOT match the article level
				if(!resultSet.getString("level").equals(levelFilter))
					resultSet.deleteRow();	// Filter out article
			}
		}
		
		
		// if filtering by search contents (not empty)
		if(!levelFilter.trim().isEmpty()) {
			
			// Check all matching groups
			while(resultSet.next()) { 
				
				// If search contents are NOT in title, author, or description
				if(!resultSet.getString("title").contains(searchContents) || 
					!resultSet.getString("author").contains(searchContents) ||
					!resultSet.getString("description").contains(searchContents)) {
							
					resultSet.deleteRow();	// Filter out article
				}
			}
		}
		return resultSet;	// Returned filtered search as result set
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
}

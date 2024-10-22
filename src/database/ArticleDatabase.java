package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*; // For SQL related objects

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
	public static void createTable() throws SQLException {
		// Create article table in database
		query = "CREATE TABLE IF NOT EXISTS articles ("
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "header VARCHAR(50) UNIQUE, "
				+ "title VARCHAR(50), "
				+ "description VARCHAR(100), "
				+ "keywords VARCHAR(50), "
				+ "group VARCHAR(50), "		// TODO: maybe change to array
				+ "body VARCHAR(500), "
				+ "references VARCHAR(100))";
		statement.execute(query);
	}
	
	
	/**********
	 * Deletes the entire articles table in database
	 */
	public static void deleteTable() throws SQLException {
		query = "DROP TABLE articles";		// delete database
		statement.execute(query);						// execute query
	}

	
	
	/**********************************************************************************************

	 Public Methods To Check Database Information
	
	**********************************************************************************************/


	/**********
	 * Checks if there is at least one row of data in database.
	 */
	public static boolean isDatabaseEmpty() throws SQLException {
		query = "SELECT COUNT(*) AS count FROM articles";
		resultSet = statement.executeQuery(query);
		
		// While the next row exists, check next row
		if (resultSet.next()) {
			return resultSet.getInt("count") == 0;
		}
		return true;
	}
	
	
	/**********
	 * Checks if an article has the given id number
	 */
	public static boolean doesArticleIDExist(int id) throws SQLException {
		
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
	    
	    return false; // If an error occurs, assume article doesn't exist
	}
	
	
	/**********
	 * Checks if an article has the given title
	 */
	public static boolean doesArticleHeaderExist(String header) throws SQLException {
		
		// Select all rows from database where header = placeholder variable ?
	    String query = "SELECT COUNT(*) FROM articles WHERE header = ?";
	    PreparedStatement pstmt = connection.prepareStatement(query);
	        
        pstmt.setString(1, header);	// title = title
        resultSet = pstmt.executeQuery();
        
        // If the next row exists
        if (resultSet.next()) {
            // Return true if 1 or more articles have a matching title
            return resultSet.getInt(1) > 0;
        }

	    return false; // If an error occurs, assume title doesn't exist
	}
	
	
	/**********************************************************************************************

	 Public Methods To Modify Database
	
	**********************************************************************************************/
	
	
	/**********
	 * Creates a new article and stores in database, returns if successful or not.
	 */
	public static void createArticle(String header, String title, String description, String keywords, 
			String group, String body, String references) throws SQLException, Exception {
		
		// Prevent printing an article that does not exist
		if(doesArticleHeaderExist(header)) {
			System.err.println("Cannot create article because header: " + title + " already exists in database!");
			return;
		}
		
		// Insert a new row into database and fill in the following column values
		query = "INSERT INTO articles (header, title, description, keywords, group, body, references) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = connection.prepareStatement(query);
			
		// Set the placeholder ? variables
		pstmt.setString(1, header);
		pstmt.setString(2, title);
		pstmt.setString(3, description);
		pstmt.setString(4, keywords);
		pstmt.setString(5, group);
		pstmt.setString(6, body);
		pstmt.setString(7, references);
		pstmt.executeUpdate();		// Execute query
		
		// Print result
		if(!doesArticleHeaderExist(title))
			System.err.println("Article not created, an error occured!");
		else
			System.out.println("Article successfully created!");
	}
	
	
	/**********
	 * Deletes the article that matches the given id parameter
	 */
	public static void deleteArticle(int id) throws SQLException {
		
		// Prevent deleting an article that does not exist
		if(!doesArticleIDExist(id)) {
			System.err.println("Cannot delete article id: " + id + " because it is not found in database!");
			return;
		}
		
		// Deletes the row from database where id = placeholder variable ?
		query = "DELETE FROM articles WHERE id = ?";
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(1, id);		// id = id
		pstmt.executeUpdate();		// Execute query
		
		// Print result
		if(doesArticleIDExist(id))
			System.err.println("Article not deleted, an error occured!");
		else
			System.out.println("Article successfully deleted!");
	}
	
	
	/**********************************************************************************************

	 Public Methods To Print Database Information
	
	**********************************************************************************************/
	

	/**********
	 * Prints the id, title, and authors for every article in database as a String
	 */
	public static void printAllArticles() throws SQLException {
		
		// Prevent printing with an empty database
		if(isDatabaseEmpty()) {
			System.err.println("Cannot print all articles because database is empty!");
			return;
		}
		
		// Select all rows from database
		query = "SELECT * FROM articles"; 
		statement = connection.createStatement();
		resultSet = statement.executeQuery(query); 	// Execute query

		// While the next row exists, check next row
		while(resultSet.next()) { 
			// Get current article info
			int id  = resultSet.getInt("id"); 
			String header = resultSet.getString("header"); 		// TODO: update depending on what we want to display
			String title = resultSet.getString("title");  		// TODO: update depending on what we want to display

			//  Print current article info
			System.out.print("ID: " + id); 
			System.out.print(", Header: " + header); 
			System.out.println(", Title: " + title); 
		} 
	}
	
	
	/**********
	 * Prints the all information about an article given its id number
	 */
	public static void printArticle(int id) throws SQLException, Exception {
		
		// Prevent printing an article that does not exist
		if(!doesArticleIDExist(id)) {
			System.err.println("Cannot print article id: " + id + " because it is not found in database!");
			return;
		}
		
		// Select the row from database where id = placeholder variable ?
	    query = "SELECT * FROM articles WHERE id = ?";
	    
	    PreparedStatement pstmt = connection.prepareStatement(query);
	        
        pstmt.setInt(1, id);				// id = id
        resultSet = pstmt.executeQuery();	// Execute query
        
        // While next row exists, check next row
        if (resultSet.next()) {
        	// Get article info
			String header = resultSet.getString("header"); 
			String title = resultSet.getString("title"); 
			String description = resultSet.getString("description"); 
			String keywords = resultSet.getString("keywords"); 
			String group = resultSet.getString("group"); 
			String body = resultSet.getString("body"); 
			String references = resultSet.getString("references"); 

			// Print article info
			System.out.println("ID: " + id); 
			System.out.println("Header: " + header); 
			System.out.println("Title: " + title); 
			System.out.println("Description: " + description); 
			System.out.println("Keywords: " + keywords);
			System.out.println("Group: \n" + group);
			System.out.println("Body: \n" + body); 
			System.out.println("References: \n" + references); 
        }
	}
	
	
	/**********************************************************************************************

	 Public Methods To Backup and Restore Database
	
	**********************************************************************************************/
	
	
	/**********
	 * Prints all database contents to a user specified file
	 */
	public static void backupDatabase(String filePath) throws IOException, SQLException, Exception {
		
		BufferedWriter writer = null;
		try {
			// write to file
			writer = new BufferedWriter(new FileWriter(filePath));
		}
		catch(IOException e) {
			// File not found, print error message
			System.err.println("File path: " + filePath + " is not found!");
			return;
		}
		
		// Select all rows from database
		query = "SELECT * FROM articles"; 
		statement = connection.createStatement();
		resultSet = statement.executeQuery(query); 	// Execute query

		// While the next row exists, check next row
		while(resultSet.next()) { 
        	// Write all article info into file
			writer.write("\n" + resultSet.getInt("id") + "\n");
			writer.write(resultSet.getString("header") + "\n"); 
			writer.write(resultSet.getString("title") + "\n"); 
			writer.write(resultSet.getString("description") + "\n"); 
			writer.write(resultSet.getString("keywords") + "\n"); 
			writer.write(resultSet.getString("group") + "\n"); 
			writer.write(resultSet.getString("body") + "\n"); 
			writer.write(resultSet.getString("references") + "\n"); 
			
			// Print the backed up article to console
			System.out.println("Article id: " + resultSet.getInt("id") + " backed up to: " + filePath);
		} 
		
		writer.close();		// Stop writing to file
	}
	
	
	/**********
	 * Gathers backup database info from file and replaces the current table with restored table
	 */
	public static void replaceDatabaseWithBackup(String filePath) throws IOException, SQLException {
		
		BufferedReader reader = null;
		try {
			// Read from file
			reader = new BufferedReader(new FileReader(filePath));
		}
		catch(IOException e) {
			// File not found, print error message
			System.err.println("File path: " + filePath + " is not found!");
			return;
		}
		
		// Temporary strings to collect file contents
		String idString, header, title, description, keywords, group, body, references = null;
		
		deleteTable();		// Delete current database
		createTable();		// Start a new empty database
		
		// While the next line isn't empty
		while((reader.readLine()) != null) {
			// Get article info from file
			idString = reader.readLine();
			header = reader.readLine();
			title = reader.readLine();
			description = reader.readLine();
			keywords = reader.readLine();
			group = reader.readLine();
			body = reader.readLine();
			references = reader.readLine();
			
			// Insert a new row into database and fill in the following column values
			query = "INSERT INTO articles (id, header, title, description, keywords, group, body, references) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
				
			// Set the placeholder ? variables
			int id = Integer.parseInt(idString);
			pstmt.setInt(1, id);
			pstmt.setString(2, header);
			pstmt.setString(3, title);
			pstmt.setString(4, description);
			pstmt.setString(5, keywords);
			pstmt.setString(6, group);
			pstmt.setString(7, body);
			pstmt.setString(8, references);
			pstmt.executeUpdate();		// Execute query
			
			// Print result to console
			if(doesArticleIDExist(id))
				System.out.println("Article id: " + idString + " successfully restored from " + filePath);
			else
				System.err.println("Article id: " + idString + " failed to be restored from " + filePath);
		}
			
		reader.close();		// Stop reading from file
	}
	
}

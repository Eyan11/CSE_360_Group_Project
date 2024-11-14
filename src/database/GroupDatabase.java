package database;

import java.sql.*; // For SQL related objects

/**
 * <p> GroupDatabase. </p>
 * 
 * <p> Description: Manages the Groups Table in the H2 database.</p>
 * 
 * <p> Source: Lynn Robert Carter from FirstDatabase project, DatabaseHelper class, 
 * 				available at: https://canvas.asu.edu/courses/193728/files/92728837?module_item_id=14758007 </p>
 * 
 * @author Eyan Martucci
 * 
 * @version 1.00		TODO
 *  
 */

public class GroupDatabase {

	// To communicate with database
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
	 * Creates a table called help_messages and initializes columns.
	 */
	public static void createTable() {
		query = "CREATE TABLE IF NOT EXISTS groups ("
				+ "name VARCHAR(50) UNIQUE PRIMARY KEY,"
				+ "type VARCHAR(50),"
				+ "admins VARCHAR(500),"
				+ "viewers VARCHAR(500))";
		try {
			statement.execute(query);
			System.out.println("'groups' table created if it did not already exist");
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.createTable \n\n");
			e.printStackTrace();
		}
	}
	
	
	/**********
	 * Deletes the entire groups table in database
	 */
	public static void deleteTable() {
		query = "DROP TABLE groups";	// delete groups table
		try {
			statement.execute(query);	// execute query
			System.out.println("'groups' table deleted");
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.deleteTable \n\n");
			e.printStackTrace();
		}
	}
	
	
	/**********
	 * Deletes all rows in groups database
	 */
	public static void deleteAllGroups() {
		query = "DELETE FROM help_messages";	// Clear the groups table
		try {
			statement.execute(query);			// execute query
			System.out.println("All groups in 'groups' table deleted");
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.deleteAllGroups \n\n");
			e.printStackTrace();
		}
	}

	
	/**********************************************************************************************

	 Public Methods To Get Database Information
	
	**********************************************************************************************/
	
	
	/**********
	 * Checks if there is at least one row of data in groups table.
	 */
	public static boolean isTableEmpty() {
		// Counts total number of rows in groups table
		query = "SELECT COUNT(*) AS count FROM groups";
		
		try {
			resultSet = statement.executeQuery(query);
			
			// if there is a next row, return true
			if (resultSet.next()) {
				return resultSet.getInt("count") == 0;
			}
			return false;	// if no rows
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.isTableEmpty \n\n");
			e.printStackTrace();
		}
		return false;	// for error
	}
	
	
	/**********
	 * Returns true if the given group name already exists in the groups table
	 */
	public static boolean doesGroupNameExist(String groupName) {
		
		// Group names are all lowercase
		groupName = groupName.toLowerCase();
		
		// Select all rows from database where name = placeholder variable ?
	    query = "SELECT COUNT(*) FROM groups WHERE name = ?";
	    try {
		    PreparedStatement pstmt = connection.prepareStatement(query);
		    
	        pstmt.setString(1, groupName);	// Set placeholder variable ? as groupName
	        resultSet = pstmt.executeQuery();
	        
	        // If the next row exists
	        if (resultSet.next()) {
	            // Return true if 1 or more articles have a matching group name
	            return resultSet.getInt(1) > 0;
	        }
	    }
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.doesGroupNameExist \n\n");
			e.printStackTrace();
		}
	    return false; // If an error occurs, assume group name doesn't exist
	}
	
	
	/**********
	 * Returns a String that differs depending on the role of the logged in user.
	 * Note: admins can see admins, instructors, and students while instructors can only see students.
	 */
	public static String getAllGroupInfo() {
		
		// Prevent getting articles if empty
		if(isTableEmpty()) {
			System.err.println("Cannot get all groups because groups table is empty!");
			return "";
		}
		
		try {
			// Search for groups where current user is in admins list
			query = "SELECT * FROM groups WHERE admins LIKE ?";
			PreparedStatement pstmt = connection.prepareStatement(query);
	
			pstmt.setString(1, "%" + LoginTracker.getUsername() + "%");	// Set the placeholder ? variable
			resultSet = pstmt.executeQuery();	// Return result set of query
		
			// Build and return string for an admin
			if(LoginTracker.usingAdminRole())
				return buildGroupsStringForAdmin(resultSet);
			// Build and return string for an instructor
			else if(LoginTracker.usingInstructorRole())
				return buildGroupsStringForInstructor(resultSet);
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.getAllGroupInfo \n\n");
			e.printStackTrace();
		}
		return "";	// for error
	}
	

	/**********************************************************************************************

	 Public Methods To Set Database Information
	
	**********************************************************************************************/
	
	
	/**********
	 * Creates a new group in groups table with the given info and returns true if successful.
	 */
	public static boolean createGroup(String groupName, String firstAdmin, String groupType) {
		
		// Convert to lowercase to avoid case sensitive issues
		groupName = groupName.toLowerCase();
		groupType = groupType.toLowerCase();
		
		// Prevent group name from being empty or over 50 characters
		if(groupName.length() <= 0 || groupName.length() > 50) {
			System.err.println("Cannot create group because group name: " + groupName + " is not between 1 and 50 characters!");
			return false;
		}
		// Prevent duplicate group names
		if(doesGroupNameExist(groupName)) {
			System.err.println("Cannot create group because group name: " + groupName + " already exists in database!");
			return false;
		}
		// Prevent adding a user that doesn't exist
		if(AccountDatabase.doesUsernameExist(firstAdmin)) {
			System.err.println("Cannot create group because username: " + firstAdmin + " does not exists in database!");
			return false;
		}
		// Prevent adding a student as a group admin
		if(AccountDatabase.isStudentRole(firstAdmin)) {
			System.err.println("Cannot create group because username: " + firstAdmin + " cannot be a student!");
			return false;
		}
		// Prevent a group type that isn't "general access" or "special access"
		if(!groupType.equals("general access") && !groupType.equals("special access")) {
			System.err.println("Cannot create group because group type: " + groupType + " is not 'general access' or 'special acces'!");
			return false;
		}
		
	
		try {
			// Insert a new row into table and fill in the following column values
			query = "INSERT INTO groups (name, type, admins, viewers) "
					+ "VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
				
			// Set the placeholder ? variables
			pstmt.setString(1, groupName);
			pstmt.setString(2, groupType);
			pstmt.setString(3, firstAdmin);
			pstmt.setString(4, "");
			pstmt.executeUpdate();		// Execute query
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.createGroup \n\n");
			e.printStackTrace();
		}
		
		// Print and return result
		if(doesGroupNameExist(groupName)) {
			System.out.println("Group successfully created!");
			return true;
		}
		else {
			System.err.println("Group not created, an error occured!");
			return false;
		}
	}
	
	
	/**********************************************************************************************

	 Private Helper Methods
	
	**********************************************************************************************/
	
	
	/**********
	 * Returns a String containing the group name, type, admins, and viewers for every 
	 * 	row in groups table where the user is an admin of that group. 
	 * String is in format of "Group: group_name1\nType: Special Access Group\nAdmins: 
	 * 	admin1, admin2\nViewers: viewer1, viewer2\n\nGroup: group_name2\nType: General Access Group\n...".
	 */
	private static String buildGroupsStringForAdmin(ResultSet rs) {
		String returnString = "";
		try {
			// While the next row exists, check next row
			while(rs.next()) { 
				// Get current all group info and format it
				returnString += "Group: " + rs.getString("name") + "\n";
				returnString += "Type: " + rs.getString("type") + "\n";
				returnString += "Admins: " + rs.getString("admins") + "\n";
				returnString += "Viewers: " + rs.getString("viewers") + "\n\n";
			}
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.buildGroupsStringForAdmin \n\n");
			e.printStackTrace();
		}
		return returnString;
	}
	
	
	/**********
	 * Returns a String containing the group name, type, and viewers for every 
	 * 	row in groups table where the user is an admin of that group. 
	 * String is in format of "Group: group_name1\nType: Special Access Group\n 
	 * 	Viewers: viewer1, viewer2\n\nGroup: group_name2\nType: General Access Group\n...".
	 */
	private static String buildGroupsStringForInstructor(ResultSet rs) {
		String returnString = "";
		String viewersString = "";
		String[] viewersArr = new String[30];
		try {
			// While the next row exists, check next row
			while(rs.next()) { 
				
				// Get current all group info and format it
				returnString += "Group: " + rs.getString("name") + "\n";
				returnString += "Type: " + rs.getString("type") + "\n";
				
				// Put each viewer into a different element in array
				viewersArr = rs.getString("viewers").split(", ");
				
				viewersString = "Viewers: ";
				
				// for each viewer in viewer array
				for(String viewer : viewersArr) {
					// Only add students viewers to viewers string
					if(viewer != "" && AccountDatabase.isStudentRole(viewer))
						viewersString += viewer + ", ";
					
					viewer = "";	// reset viewer in array
				}
				
				// If at least 1 viewer in viewers string, remove the last ", " in viewers string
				if (viewersString.length() > 9)
					viewersString = viewersString.substring(0, viewersString.length() - 1);
				
				// Assemble string
				returnString += viewersString + "\n\n";
			}
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.buildGroupsStringForInstructor \n\n");
			e.printStackTrace();
		}
		return returnString;
	}
}

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
	 * Sets connection and statement variables so this class can access the database.
	 */
	public static void setConnection(Connection _connection, Statement _statement) {
		connection = _connection;
		statement = _statement;
	}
	
	
	/**********
	 * Creates a table called groups and initializes columns.
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
	 * Deletes the entire groups table in database.
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
	 * Deletes all rows in groups database.
	 */
	public static void deleteAllGroups() {
		query = "DELETE FROM groups";		// Clear the groups table
		try {
			statement.execute(query);		// execute query
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
	 * Returns true if the given group name already exists in the groups table.
	 */
	public static boolean doesGroupNameExist(String groupName) {
		
		groupName = groupName.toLowerCase();	// Group names are all lowercase
		groupName = groupName.trim();	// Group names don't have excess whitespace
		
	    try {
			// Select all rows from database where name = placeholder variable ?
		    query = "SELECT COUNT(*) FROM groups WHERE name = ?";
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
	 * Returns true if at least one group in group list is of the special access type
	 */
	public static boolean shouldArticleBeEncrypted(String groupList) {
		groupList = groupList.toLowerCase();	// Group names are all lowercase
		
		try {
			// Select all special access groups
			query = "SELECT * FROM groups WHERE type = 'special access'";
			resultSet = statement.executeQuery(query);
			
			// Separate all groups into array
			String[] groupsArr = groupList.split(",");
			
			// Remove excess whitespace in all groups
			for(int i = 0; i < groupsArr.length; i++)
				groupsArr[i] = groupsArr[i].trim();

			
			String nameInDatabase;
			while(resultSet.next()) {
				// Get current group name
				nameInDatabase = resultSet.getString("name");
				
				// Loop through all groups in array
				for(String group : groupsArr) {
					// If one of the special access groups is in group list
					if(nameInDatabase.equals(group))
						return true;	// You have to encrypt this article because it belongs to a special access group
				}
			}
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.shouldArticleBeEncrypted \n\n");
			e.printStackTrace();
		}
		// If no groups matched, you do not have to encrypt this article because it only has general access groups
		return false;
	}
	
	
	/**********
	 * Returns true if the user belongs to the group admins or viewers list of the specified group.
	 */
	public static boolean isUserInGroup(String groupName, String user, boolean isViewer) {
		// Adjust group name to avoid error's
		groupName = groupName.toLowerCase();
		groupName = groupName.trim();
		
		try {
			// Select all rows from database where name = placeholder variable ?
		    query = "SELECT * FROM groups WHERE name = ?";
		    PreparedStatement pstmt = connection.prepareStatement(query);
		    
	        pstmt.setString(1, groupName);	// Set placeholder variable ? as groupName
	        resultSet = pstmt.executeQuery();
	        
	        // If user is in group viewers list, return true
	        if(isViewer && resultSet.getString("viewers").contains(user))
	        	return true;
	        // If user is in group admins list, return true
	        else if(!isViewer && resultSet.getString("admins").contains(user))
	        	return true;
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.isUserInGroup \n\n");
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**********
	 * Returns true if the user belongs to the group admins/viewers lists for all groups in group list
	 */
	public static boolean isUserInAllGroupsInList(String groupList, String user, boolean isViewer) {
		
		// Separate groups into an array
		String[] groupsArr = groupList.split(", ");
		
		// Check all groups in group list
		for(String group : groupsArr) {
			group = group.trim();	// Trim whitespace
			
			// If the user is not in the group admin/viewers list
			if(!isUserInGroup(group, user, isViewer)) {
				System.out.println("User: " + user + " is not in group: " + group + " \n\n");
				return false;
			}
		}
		return true;	// User is in all groups in group list
	}
	
	
	/**********
	 * Returns all group admins or viewers for the provided group name.
	 */
	public static String getGroupAdminsOrViewers(String groupName, boolean getViewers) {
		
		// Prevent getting admins if group name doesn't exist
		if(doesGroupNameExist(groupName)) {
			System.err.println("Cannot get group admins because group name: " + groupName + " doesn't exist!");
			return "";
		}
		
		try {
			// Select all rows from database where name = placeholder variable ?
		    query = "SELECT COUNT(*) FROM groups WHERE name = ?";
		    PreparedStatement pstmt = connection.prepareStatement(query);
		    
	        pstmt.setString(1, groupName);	// Set placeholder variable ? as groupName
	        resultSet = pstmt.executeQuery();
	        
	        if(getViewers)
	        	return resultSet.getString("viewers");
	        else
	        	return resultSet.getString("admins");
		}
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.getGroupAdminsOrViewers \n\n");
			e.printStackTrace();
		}
		return "";	// for error
	}
	
	
	/**********
	 * Returns a String for ModifyGroupAccessGUI which displays all groups that the user is an admin 
	 * 	along with their type, viewers list , and admins list for users with admins account role.
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
		if(!AccountDatabase.doesUsernameExist(firstAdmin)) {
			System.err.println("Cannot create group because username: " + firstAdmin + " does not exists in database!");
			return false;
		}
		// Prevent adding a user with only the student role as a group admin
		if(!AccountDatabase.isAdminRole(firstAdmin) && !AccountDatabase.isInstructorRole(firstAdmin)) {
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
	
	
	/**********
	 * Adds a user to a group as a group admin or group viewer and returns true if the user was added.
	 * If the user is in the opposite group role, then they are switched to the requested role because a user
	 * 	cannot be in both the group admins and viewers list.
	 */
	public static boolean addUserToGroup(String user, String groupName, boolean isViewer) {
		
		groupName = groupName.toLowerCase();	// Convert to all lowercase
		groupName = groupName.trim();			// Remove excess whitespace
		
		
		// Prevent adding a user that doesn't exist
		if(!AccountDatabase.doesUsernameExist(user)) {
			System.err.println("Cannot add to group because username: " + user + " does not exists in database!");
			return false;
		}
		// Prevent adding a user with only the student role as a group admin
		if(!isViewer && !AccountDatabase.isAdminRole(user) && !AccountDatabase.isInstructorRole(user)) {
			System.err.println("Cannot add username: " + user + " as a group admin because they are a student!");
			return false;
		}
		// Prevent adding a user to a group that doesn't exist
		if(!doesGroupNameExist(groupName)) {
			System.err.println("Cannot add to group because group name: " + groupName + " doesn't exist!");
			return false;
		}
		// Prevent instructor from adding admins or instructors
		if(LoginTracker.usingInstructorRole() && (AccountDatabase.isAdminRole(user) || AccountDatabase.isInstructorRole(user))) {
			System.err.println("Cannot add user: " + user + " to group because the user is an admin or instructor and the "
					+ "currently logged in user is an instructor (they need to be an admin to do this)!");
			return false;
		}
		// Prevent duplicate users in list (covers both admin and viewers list)
		if(isUserInGroup(groupName, user, isViewer)) {
    		System.err.println("Cannot add user: " + user + " to group: " + groupName +
    				"because they are already in that group list!");
    		return false;
		}
		
    	// Get list of admins and viewers for the given group name
    	String groupAdmins = getGroupAdminsOrViewers(groupName, false);
    	String groupViewers = getGroupAdminsOrViewers(groupName, true);
    	
    	// If adding as group viewer
    	if(isViewer) {
    		
    		// If user is admin, remove from admin list and return false if removal failed, otherwise continue
    		if(!removeUserFromGroup(user, groupName, false)) {
        		System.err.println("Cannot add user: " + user + " to group: " + groupName + " as group viewer "
        				+ "because it failed to be removed from group admins list!");
        		return false;
    		}
    		
    		// Add user to empty group viewers list
    		if(groupViewers.equals(""))
    			groupViewers = user;
    		// Add user to the end of group viewers list
    		else
    			groupViewers += ", " + user;
    	}
    	// If adding as group admin
    	else {
        	
        	// Prevent user from being in both group admins and viewers list
        	if(groupViewers.contains(user))
        		groupViewers = removeNameFromList(groupViewers, user);
        	
        	// Add user to end of admins list (admins list can never be empty)
        	groupAdmins += ", " + user;
        }
	    	
	    try {
    		// Update the matching group name with the updated admins and viewers list
    		query = "UPDATE groups SET admins = ?, viewers = ? WHERE name = ?";
			// Prepare the previous query to be executed
			PreparedStatement pstmt = connection.prepareStatement(query);
				
			// Set the placeholder ? variables
			pstmt.setString(1, groupAdmins);
			pstmt.setString(2, groupViewers);
			pstmt.setString(3, groupName);
			pstmt.executeUpdate();	// execute query
			return true;
	    }
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.addUserToGroup \n\n");
			e.printStackTrace();
		}
	    
	    // Check and print result
		if(isUserInGroup(groupName, user, isViewer)) {
    		System.out.println("User: " + user + " successfully added to group: " + groupName);
    		return true;
		}
		else {
    		System.err.println("User: " + user + " failed to be added to group: " + groupName);
    		return false;
		}
	}
	
	
	/**********
	 * Removes a user from a right the group admins or viewers list and returns true if they were removed.
	 */
	public static boolean removeUserFromGroup(String user, String groupName, boolean isViewer) {
		
		// Convert to lowercase to avoid case sensitive issues
		groupName = groupName.toLowerCase();
		
		// Prevent removing a user that doesn't exist
		if(!AccountDatabase.doesUsernameExist(user)) {
			System.err.println("Cannot remove from group because username: " + user + " does not exists in database!");
			return false;
		}
		// Prevent removing a user to a group that doesn't exist
		if(!doesGroupNameExist(groupName)) {
			System.err.println("Cannot remove from group because group name: " + groupName + " doesn't exist!");
			return false;
		}
		// Prevent removing users from a list they are not already in
		if(!isUserInGroup(groupName, user, isViewer)) {
    		System.err.println("Cannot remove user: " + user + " from group: " + groupName +
    				"because they are not in that group list!");
    		return false;
		}
		// Prevent instructor from removing admins or instructors
		if(LoginTracker.usingInstructorRole() && (AccountDatabase.isAdminRole(user) || AccountDatabase.isInstructorRole(user))) {
			System.err.println("Cannot remove user: " + user + " from group because the user is an admin or instructor and the "
					+ "currently logged in user is an instructor (they need to be an admin to do this)!");
			return false;
		}
		
    	// Get list of admins and viewers for the given group name
    	String groupAdmins = getGroupAdminsOrViewers(groupName, false);
    	String groupViewers = getGroupAdminsOrViewers(groupName, true);
    	
    	// Prevent removing the only admin from admins list
    	if(!isViewer && atLeastOneAdminAfterRemoval(groupAdmins, user)) {
			System.err.println("Cannot remove from admins list since user: " + user + " is the only user "
					+ "with account admin role in the list! There must be at least one admin in group admins list.");
			return false;
    	}
    	
    	// Remove from viewers list string
    	if(isViewer)
    		groupViewers = removeNameFromList(groupViewers, user);
    	// Remove from admins list string
    	else
    		groupAdmins = removeNameFromList(groupAdmins, user);
		
	    try {
    		// Update the matching group name with the updated admins and viewers list
    		query = "UPDATE groups SET admins = ?, viewers = ? WHERE name = ?";
			// Prepare the previous query to be executed
			PreparedStatement pstmt = connection.prepareStatement(query);
				
			// Set the placeholder ? variables
			pstmt.setString(1, groupAdmins);
			pstmt.setString(2, groupViewers);
			pstmt.setString(3, groupName);
			pstmt.executeUpdate();	// execute query
			return true;
	    }
		catch(SQLException e) {
			System.err.println("SQLException in GroupDatabase.removeUserFromGroup \n\n");
			e.printStackTrace();
		}

	    // Check and print result
		if(!isUserInGroup(groupName, user, isViewer)) {
    		System.out.println("User: " + user + " successfully removed from group: " + groupName);
    		return true;
		}
		else {
    		System.err.println("User: " + user + " failed to be removed from group: " + groupName);
    		return false;
		}
	}
	
	
	/**********************************************************************************************

	 Private Helper Methods
	
	**********************************************************************************************/
	
	
	/**********
	 * Returns true if there is at least one admin in the given admins list besides for the given user.
	 */
	private static boolean atLeastOneAdminAfterRemoval(String adminsList, String user) {
		String[] adminsArr = adminsList.split(", ");
		
		// Make sure there is at least one other user in group admins list with admin account role
		for(String groupAdmin : adminsArr) {

			// if an admin is in the list and it is not the user being remove, return true
			if(AccountDatabase.isAdminRole(user) && !groupAdmin.equals(user))
				return true;
		}
		return false;
	}
	
	
	/**********
	 * Removes a name from a string list of names while keeping the order and format of the list correct.
	 * Returns strings in format of "Name1" or "Name1, Name2, Name3".
	 */
	private static String removeNameFromList(String list, String name) {
		
		// If list contains 0 or 1 names, return an empty list
		if(!list.contains(","))
			return "";
		// If name is not the last in list, remove it and keep format
		else if(list.contains(name + ", "))
			return list.replace(name + ", ", "");
		// If name is the last name in list, remove it and keep format
		else if(list.contains(", " + name))
			return list.replace(", " + name, "");
		
		// Error since list is in wrong format
		System.err.println("Cannot remove name from the list: " + list + " because list is in the wrong format!"
				+ " Returning original list.");
		return list;
	}
	
	
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

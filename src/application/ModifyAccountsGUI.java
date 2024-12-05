package application;

import database.AccountDatabase;
import database.LoginTracker;
import javafx.scene.control.Label; // For Label object
import javafx.scene.control.TextField; // For TextField object
import javafx.scene.control.Button; // For Button object
import javafx.scene.control.CheckBox; // For CheckBox object
import javafx.geometry.Pos; // For Position object (vector2 coordinate used to describe position)
import javafx.scene.paint.Color; // To set color of UI elements
import javafx.scene.layout.Pane; // For Pane object

import javafx.event.ActionEvent; // For ActionEvent object
import javafx.event.EventHandler; // For EventHandler object
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.sql.*;

/*******
 * <p> ModifyAccountGUI Class </p>
 * 
 * <p> Description: Interface that allows user to reset, delete, and role changes </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/24/2024 Phase 1 Implementation and Documentation
 * 			1.50 10/25/2024 Finalized GUI Display
 * 			1.75 10/28/2024 Documentation and added Full Button Functionality
 * 			2.00 11/18/2024 Phase 3 Implementation and Documentation
 * 			2.25 11/20/2024 Finalization + Documentation
 */

public class ModifyAccountsGUI
{
	/*
	 * Variable Declarations
	 */

	// The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	// Stores user name inputed by user
	private String userInput = "";
	
	// Default roles assigned to each user name prior to changes
	private boolean addStudentRole = false;
	private boolean addInstructorRole = false;
	private boolean addAdminRole = false;
	
	// Displays interface information
	private Label allAccountsLabel = new Label("All Accounts");
	private Label usernameLabel = new Label("Enter Username: ");
	
	// Text Field for user input
	private TextField userText = new TextField();
	
	// Buttons used for navigating interface and user modifications
	private Button homeButton = new Button("Home");
	private Button resetButton = new Button("Reset");
	private Button deleteButton = new Button("Delete");
	private Button applyRoleButton = new Button("Apply Role Changes?");
	
	// Check Boxes used specifically for role changes
	private CheckBox studentCheckBox = new CheckBox("Student");
	private CheckBox instructorCheckBox = new CheckBox("Instructor");
	private CheckBox adminCheckBox = new CheckBox("Admin");
	
	// Declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param theRoot
	 */
	
	public ModifyAccountsGUI(Pane theRoot) throws SQLException
	{	
		// Utilizes the SetUpElements class for Labels, TextFields, Buttons, and Check Boxes
		setupUI = new SetupUIElements();
		
		// Non-Interactive text that will appear on interface
		Label accounInfoLabel = new Label(AccountDatabase.getAllAccounts());
		
		/*
		 * Label Creations
		 */
		
		// Label that displays "All Accounts"
		setupUI.SetupLabelUI(allAccountsLabel, "Arial", 28, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 40, Color.BLACK);
		
		// Label that asks user for a user name
		setupUI.SetupLabelUI(usernameLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 310, Color.BLACK);
		
		// Label that displays all account information from database
		setupUI.SetupLabelUI(accounInfoLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 100, Color.BLACK);
		
		/*
		 * TextField Creation
		 */
		
		// Text Field that takes user name input from user
		setupUI.SetupTextFieldUI(userText, "Arial", 14, 125, 20,
				Pos.BASELINE_LEFT, 10, 325, true);
		
		/*
		 * Button Creations
		 */
		
		// Button that returns user to previous interface
		setupUI.SetupButtonUI(homeButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		
		// Button that rests inputed username's password
		setupUI.SetupButtonUI(resetButton, "Arial", 11, 100, 20, 
        		Pos.CENTER, 150, 325, false, Color.BLACK);
		
		// Button that deletes inputed username's account
		setupUI.SetupButtonUI(deleteButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 260, 325, false, Color.BLACK);
		
		// Button that saves role changes from check boxes
		setupUI.SetupButtonUI(applyRoleButton, "Arial", 11, 130, 20,
        		Pos.CENTER, 365, 325, false, Color.BLACK);
		
		/*
		 * CheckBox Creations
		 */
		
		// Check Box that manages student role for user name inputed by user
		setupUI.SetupCheckBoxUI(studentCheckBox, "Arial", 11, 100, 20,
        		Pos.CENTER, 375, 350, false, Color.BLACK);
		
		// Check Box that manages instructor role for user name inputed by user
		setupUI.SetupCheckBoxUI(instructorCheckBox, "Arial", 11, 100, 20,
        		Pos.CENTER, 375, 375, false, Color.BLACK);
		
		// Check Box that manages administrator role for user name inputed by user
		setupUI.SetupCheckBoxUI(adminCheckBox, "Arial", 11, 100, 20,
        		Pos.CENTER, 375, 400, false, Color.BLACK);
		
		
		// Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(allAccountsLabel, usernameLabel, accounInfoLabel, userText, homeButton,
				resetButton, deleteButton, studentCheckBox, instructorCheckBox, adminCheckBox, applyRoleButton);
		
		/*
		 * Button + CheckBox Functionality
		 */
		
		/*****
		 * Home Button
		 */
		
		homeButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{		
				// Checks if user is logged in as an admin if user has multiple roles
				if(LoginTracker.usingAdminRole())
				{
					theRoot.getChildren().clear();  // clear the current root
					
					// Send user to previous interface
					Pane newRoot = new Pane(); // create new root
					AdminHome adminHome = new AdminHome(newRoot); // call previous interface
					Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
				    Stage currentStage = (Stage) theRoot.getScene().getWindow();
				    currentStage.setScene(newScene); // sets scene
				}
				// Checks if user is logged in as instructor if user has multiple roles
				else if(LoginTracker.usingInstructorRole())
				{
					theRoot.getChildren().clear();  // clear the current root
					
					// Send user to previous interface
					Pane newRoot = new Pane(); // create new root
					InstructorHomeGUI instructorHome = new InstructorHomeGUI(newRoot); // call previous interface
					Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
				    Stage currentStage = (Stage) theRoot.getScene().getWindow();
				    currentStage.setScene(newScene); // sets scene
				}
			}
		});
		
		/*****
		 * Reset Button
		 */
		
		resetButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				if(!LoginTracker.usingAdminRole())
				{
					// Collect user name input from user for modification
					userInput = userText.getText();
					// Make sure inputed user name from user exists in database
					if(AccountDatabase.doesUsernameExist(userInput))
					{
						theRoot.getChildren().clear();  // clear the current root
						
						// Send user to reset password interface
						Pane newRoot = new Pane();
						ResetAccountGUI resetAccount = new ResetAccountGUI(newRoot, userInput);
						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // create new scene
					    Stage currentStage = (Stage) theRoot.getScene().getWindow();
					    currentStage.setScene(newScene); // set scene
					}
				} else
				{
					System.out.println("Admins cannot reset");
				}
			}
		});
		
		/*****
		 * Delete Button
		 */
		
		deleteButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				// Collect user name from user for input
				userInput = userText.getText();
				// Make sure inputed user name from user exists in database
				if(AccountDatabase.doesUsernameExist(userInput)) 
				{
					theRoot.getChildren().clear();  // Clear the current root
					
					// Send user to delete account interface
					Pane newRoot = new Pane();
					DeleteConfirmationGUI resetAccount = new DeleteConfirmationGUI(newRoot, userInput);
					Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
				    Stage currentStage = (Stage) theRoot.getScene().getWindow();
				    currentStage.setScene(newScene); // set scene
				}
			}
		});
		
		/*****
		 * Student CheckBox
		 */
		
		studentCheckBox.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{	
				// Collect user name from user for input
				userInput = userText.getText();
				// Make sure inputed user name from user exists in database
				if(AccountDatabase.doesUsernameExist(userInput))
				{
					// If user is not a student, student role will be added
					if(!addStudentRole)
					{
						addStudentRole = true;
					}
					// If user is a student, student role will be removed
					else
					{
						addStudentRole = false;
					}
				}
			}
		});
		
		/*****
		 * Instructor CheckBox
		 */
		
		instructorCheckBox.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{				
				// Collect user name from user for input
				userInput = userText.getText();
				// Make sure inputed user name from user exists in database
				if(AccountDatabase.doesUsernameExist(userInput))
				{
					// If user is not a instructor, instructor role will be added
					if(!addInstructorRole)
					{
						addInstructorRole = true;
					}
					// If user is a instructor, instructor role will be removed
					else
					{
						addInstructorRole = false;
					}
				}
			}
		});
		
		/*****
		 * Administrator CheckBox
		 */
		
		adminCheckBox.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				// Collect user name from user for input
				userInput = userText.getText();
				// Make sure inputed user name from user exists in database
				if(AccountDatabase.doesUsernameExist(userInput))
				{
					// If user is not a administrator, administrator role will be added
					if(!addAdminRole)
					{
						addAdminRole = true;
					}
					// If user is a administrator, administrator role will be removed
					else
					{
						addAdminRole = false;
					}
				}
			}
		});
		
		
		/*****
		 * Apply Role Changes Button
		 */
		
		applyRoleButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				if(!LoginTracker.usingAdminRole())
				{
					// Collect user name from user for input
					userInput = userText.getText();
					
					// Make sure inputed user name from user exists in database
					if(AccountDatabase.doesUsernameExist(userInput))
					{
						// Updates role changes for user name inputed from user
						AccountDatabase.updateUserRoles(userInput, addStudentRole, addInstructorRole, addAdminRole);
					}
				} else {
					System.out.println("Admins cannot edit");
				}
			}
		});

	}
}


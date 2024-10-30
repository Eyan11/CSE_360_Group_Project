package application;

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

import database.AccountDatabase;

import java.sql.*;

/*******
 * <p> ModifyAccountGUI Class </p>
 * 
 * <p> Description: Interface that allows reset, delete, and role changes </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/24/2024 Phase 1 Implementation and Documentation
 * 			1.50 10/25/2024 Finalized GUI Display
 * 			1.75 10/28/2024 Documentation
 * 			2.00 TBD (waiting  on ResetAccountGUI + DeleteConfirmationGUI)
 */

public class ModifyAccountsGUI
{
	/*
	 * Variable Declarations
	 */

	//The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	//stores user name inputed by user
	private String userInput = "";
	
	//default roles assigned to each user name prior to changes
	private boolean addStudentRole = false;
	private boolean addInstructorRole = false;
	private boolean addAdminRole = false;
	
	//displays interface information
	private Label allAccountsLabel = new Label("All Accounts");
	private Label usernameLabel = new Label("Enter Username: ");
	
	//text field for user input
	private TextField userText = new TextField();
	
	//buttons used for navigating interface and user modifications
	private Button homeButton = new Button("Home");
	private Button resetButton = new Button("Reset");
	private Button deleteButton = new Button("Delete");
	private Button applyRoleButton = new Button("Apply Role Changes?");
	
	//check boxes used specifically for role changes
	private CheckBox studentButton = new CheckBox("Student");
	private CheckBox instructorButton = new CheckBox("Instructor");
	private CheckBox adminButton = new CheckBox("Admin");
	
	//declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param theRoot
	 */
	
	public ModifyAccountsGUI(Pane theRoot) throws SQLException
	{	
		//non-interactive text that will appear on interface
		Label accounInfoLabel = new Label(AccountDatabase.getAllAccounts());
		
		//utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		/*
		 * Label Creations
		 */
		
		//Label that displays "All Accounts"
		setupUI.SetupLabelUI(allAccountsLabel, "Arial", 28, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 40, Color.BLACK);
		
		//Label that asks user for a user name
		setupUI.SetupLabelUI(usernameLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 310, Color.BLACK);
		
		//Label that displays all account information from database
		setupUI.SetupLabelUI(accounInfoLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 60, Color.BLACK);
		
		/*
		 * TextField Creation
		 */
		
		//Text Field that takes user name input from user
		setupUI.SetupTextFieldUI(userText, "Arial", 14, 125, 20,
				Pos.BASELINE_LEFT, 10, 325, true);
		
		/*
		 * Button Creations
		 */
		
		//Button that returns user to previous interface
		setupUI.SetupButtonUI(homeButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		//Button that rests inputed username's password
		setupUI.SetupButtonUI(resetButton, "Arial", 11, 100, 20, 
        		Pos.CENTER, 150, 325, false, Color.BLACK);
		//Button that deletes inputed username's account
		setupUI.SetupButtonUI(deleteButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 260, 325, false, Color.BLACK);
		//Button that saves role changes from check boxes
		setupUI.SetupButtonUI(applyRoleButton, "Arial", 11, 130, 20,
        		Pos.CENTER, 365, 325, false, Color.BLACK);
		
		/*
		 * CheckBox Creations
		 */
		
		//Check Box that manages student role for user name inputed by user
		setupUI.SetupCheckBoxUI(studentButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 375, 350, false, Color.BLACK);
		//Check Box that manages instructor role for user name inputed by user
		setupUI.SetupCheckBoxUI(instructorButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 375, 375, false, Color.BLACK);
		//Check Box that manages administrator role for user name inputed by user
		setupUI.SetupCheckBoxUI(adminButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 375, 400, false, Color.BLACK);
		
		
		//Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(allAccountsLabel, usernameLabel, accounInfoLabel, userText, homeButton,
				resetButton, deleteButton, studentButton, instructorButton, adminButton, applyRoleButton);
		
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
				theRoot.getChildren().clear();  // Clear the current root
				//send user to previous interface
				Pane newRoot = new Pane(); // create new root
				AdminHome adminHome = new AdminHome(newRoot); // call previous interface
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
			    Stage currentStage = (Stage) theRoot.getScene().getWindow();
			    currentStage.setScene(newScene); // sets scene
			}
		});
		
		/*****
		 * Reset Button
		 */
		
		resetButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//collect user name input from user for modification
				userInput = userText.getText();
				//make sure inputed user name from user exists in database
				if(AccountDatabase.doesUsernameExist(userInput))
				{
					theRoot.getChildren().clear();  // Clear the current root
					//send user to reset password interface
					Pane newRoot = new Pane();
					ResetAccountGUI resetAccount = new ResetAccountGUI(newRoot, userInput);
					Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // create new scene
				    Stage currentStage = (Stage) theRoot.getScene().getWindow();
				    currentStage.setScene(newScene); // set scene
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
				//collect user name from user for input
				userInput = userText.getText();
				//make sure inputed user name from user exists in database
				if(AccountDatabase.doesUsernameExist(userInput)) 
				{
					theRoot.getChildren().clear();  // Clear the current root
					//send user to delete account interface
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
		
		studentButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{	
				//collect user name from user for input
				userInput = userText.getText();
				//make sure inputed user name from user exists in database
				if(AccountDatabase.doesUsernameExist(userInput))
				{
					//if user is not a student, student role will be added
					if(!addStudentRole)
					{
						addStudentRole = true;
					}
					//if user is a student, student role will be removed
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
		
		instructorButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{				
				//collect user name from user for input
				userInput = userText.getText();
				//make sure inputed user name from user exists in database
				if(AccountDatabase.doesUsernameExist(userInput))
				{
					//if user is not a instructor, instructor role will be added
					if(!addInstructorRole)
					{
						addInstructorRole = true;
					}
					//if user is a instructor, instructor role will be removed
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
		
		adminButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//collect user name from user for input
				userInput = userText.getText();
				//make sure inputed user name from user exists in database
				if(AccountDatabase.doesUsernameExist(userInput))
				{
					//if user is not a administrator, administrator role will be added
					if(!addAdminRole)
					{
						addAdminRole = true;
					}
					//if user is a administrator, administrator role will be removed
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
				////collect user name from user for input
				userInput = userText.getText();
				
				try {
					//make sure inputed user name from user exists in database
					if(AccountDatabase.doesUsernameExist(userInput))
					{
						//updates role changes for user name inputed from user
						AccountDatabase.updateUserRoles(userInput, addStudentRole, addInstructorRole, addAdminRole);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}


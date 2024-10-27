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
 * <p> Description:  </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/24/2024 Phase 1 Implementation and Documentation
 * 			1.50 10/25/2024 Finalized GUI Display
 * 
 */

public class ModifyAccountsGUI
{
	/*
	 * Variable Declarations
	 */

	//The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	//
	private String userInput = "";
	
	//
	private boolean changeStudentRole = false;
	private boolean changeInstructorRole = false;
	private boolean changeAdminRole = false;
	
	//
	private Label allAccountsLabel = new Label("All Accounts");
	private Label usernameLabel = new Label("Enter Username: ");
	
	//text field for user input
	private TextField userText = new TextField();
	
	//
	private Button homeButton = new Button("Home");
	private Button resetButton = new Button("Reset");
	private Button deleteButton = new Button("Delete");
	private CheckBox studentButton = new CheckBox("Student");
	private CheckBox instructorButton = new CheckBox("Instructor");
	private CheckBox adminButton = new CheckBox("Admin");
	
	private Button applyRoleButton = new Button("Apply Role Changes?");
	
	//declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param userPane
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
		
		//
		setupUI.SetupLabelUI(allAccountsLabel, "Arial", 28, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 40, Color.BLACK);
		
		setupUI.SetupLabelUI(usernameLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 310, Color.BLACK);
		
		//
		setupUI.SetupLabelUI(accounInfoLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 60, Color.BLACK);
		
		/*
		 * TextField Creations 
		 */
		
		//
		setupUI.SetupTextFieldUI(userText, "Arial", 14, 125, 20,
				Pos.BASELINE_LEFT, 10, 325, true);
		
		/*
		 * Button Creations
		 * FOR JULIO: Figure Out Button Dimensions
		 */
		
		//Button that ...
		setupUI.SetupButtonUI(homeButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		
		//Button that ...
		setupUI.SetupButtonUI(resetButton, "Arial", 11, 100, 20, 
        		Pos.CENTER, 150, 325, false, Color.BLACK);
		//Button that ...
		setupUI.SetupButtonUI(deleteButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 260, 325, false, Color.BLACK);
		
		//Button that ...
		setupUI.SetupCheckBoxUI(studentButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 375, 350, false, Color.BLACK);
		//Button that ...
		setupUI.SetupCheckBoxUI(instructorButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 375, 375, false, Color.BLACK);
		
		//Button that ...
		setupUI.SetupCheckBoxUI(adminButton, "Arial", 11, 100, 20,
        		Pos.CENTER, 375, 400, false, Color.BLACK);
		
		//Button that ...
		setupUI.SetupButtonUI(applyRoleButton, "Arial", 11, 130, 20,
        		Pos.CENTER, 365, 325, false, Color.BLACK);
		
		
		//Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(allAccountsLabel, usernameLabel, accounInfoLabel, userText, homeButton,
				resetButton, deleteButton, studentButton, instructorButton, adminButton, applyRoleButton);
		
		/*
		 * Button Functionality
		 */
		
		/*****
		 * Home Button
		 */
		
		homeButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{						
				theRoot.getChildren().clear();  // Clear the current root
				
				Pane newRoot = new Pane(); // 
				AdminHome adminHome = new AdminHome(theRoot); //
				
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
			    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
			    currentStage.setScene(newScene); // 
			}
		});
		
		/*****
		 * Reset Button
		 */
		
		resetButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//
				userInput = userText.getText();
				
				//
				if(AccountDatabase.doesUsernameExist(userInput)) // 
				{
					theRoot.getChildren().clear();  // Clear the current root
					
					Pane newRoot = new Pane();
					//ResetAccountGUI resetAccount = new ResetAccountGUI(theRoot, userInput);
					
					Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
				    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
				    currentStage.setScene(newScene); // 
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
				//
				userInput = userText.getText();
				
				//
				if(AccountDatabase.doesUsernameExist(userInput)) // 
				{
					theRoot.getChildren().clear();  // Clear the current root
					
					Pane newRoot = new Pane();
					//DeleteConfirmationGUI resetAccount = new DeleteConfirmationGUI(theRoot, userInput);
					
					Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
				    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
				    currentStage.setScene(newScene); // 
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
				System.out.println("Student Checked!"); //
				
				//
				userInput = userText.getText();
				
				//
				if(AccountDatabase.doesUsernameExist(userInput)) // 
				{
					
					if(AccountDatabase.isStudentRole(userInput)) {}
					//
					else
					{
						if(!changeInstructorRole)
						{
							changeStudentRole = true;
						}
						else
						{
							changeStudentRole = false;
						}
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
				System.out.println("Instructor Checked!"); //
				
				//
				userInput = userText.getText();
				
				//
				if(AccountDatabase.doesUsernameExist(userInput)) // 
				{
					
					if(AccountDatabase.isInstructorRole(userInput)) {}
					//
					else
					{
						if(!changeInstructorRole)
						{
							changeInstructorRole = true;
						}
						else
						{
							changeInstructorRole = false;
						}
					}
				}
			}
		});
		
		/*****
		 * Admin CheckBox
		 */
		
		adminButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				System.out.println("Admin Checked!"); //
				
				//
				userInput = userText.getText();
				
				//
				if(AccountDatabase.doesUsernameExist(userInput)) // 
				{
					
					if(AccountDatabase.isAdminRole(userInput)) {}
					//
					else
					{
						if(!changeInstructorRole)
						{
							changeAdminRole = true;
						}
						else
						{
							changeAdminRole = false;
						}
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
				//
				userInput = userText.getText();
				
				try {
					AccountDatabase.updateUserRoles(userInput, changeStudentRole, changeInstructorRole, changeAdminRole);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}

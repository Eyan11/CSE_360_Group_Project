package application;

import javafx.scene.control.Label; // For Label object
import javafx.scene.control.TextField; // For TextField object
import javafx.scene.control.Button; // For Button object
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
	private Label usernameLabel = new Label("Enter Username You Would Like to Modify: ");
	
	//text field for user input
	private TextField userText = new TextField();
	
	//
	private Button homeButton = new Button("Home");
	private Button resetButton = new Button("Reset");
	private Button deleteButton = new Button("Delete");
	private Button studentButton = new Button("Student");
	private Button instructorButton = new Button("Instructor");
	private Button adminButton = new Button("Admin");
	
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
		setupUI.SetupLabelUI(allAccountsLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 40, Color.BLACK);
		
		setupUI.SetupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 230, Color.BLACK);
		
		//
		setupUI.SetupLabelUI(accounInfoLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
		
		/*
		 * TextField Creations 
		 */
		
		//
		setupUI.SetupTextFieldUI(userText, "Arial", 14, WINDOW_WIDTH-10,
				Pos.BASELINE_LEFT, 10, 250, true);
		
		/*
		 * Button Creations
		 * FOR JULIO: Figure Out Button Dimensions
		 */
		
		//Button that ...
		setupUI.SetupButtonUI(homeButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		
		//Button that ...
		setupUI.SetupButtonUI(resetButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 275, false, Color.BLACK);
		//Button that ...
		setupUI.SetupButtonUI(deleteButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 300, false, Color.BLACK);
		
		//Button that ...
		setupUI.SetupButtonUI(studentButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 325, false, Color.BLACK);
		//Button that ...
		setupUI.SetupButtonUI(instructorButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 350, false, Color.BLACK);
		
		//Button that ...
		setupUI.SetupButtonUI(adminButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 375, false, Color.BLACK);
		
		//Button that ...
		setupUI.SetupButtonUI(applyRoleButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, false, Color.BLACK);
		
		
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
		 * Student Button
		 */
		
		studentButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//
				userInput = userText.getText();
				
				//
				if(AccountDatabase.doesUsernameExist(userInput)) // 
				{
					
					if(AccountDatabase.isStudentRole(userInput)) {}
					//
					else
					{
						changeStudentRole = true;
					}
				}
			}
		});
		
		/*****
		 * Instructor Button
		 */
		
		instructorButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//
				userInput = userText.getText();
				
				//
				if(AccountDatabase.doesUsernameExist(userInput)) // 
				{
					
					if(AccountDatabase.isInstructorRole(userInput)) {}
					//
					else
					{
						changeInstructorRole = true;
					}
				}
			}
		});
		
		/*****
		 * Admin Button
		 */
		
		adminButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//
				userInput = userText.getText();
				
				//
				if(AccountDatabase.doesUsernameExist(userInput)) // 
				{
					
					if(AccountDatabase.isAdminRole(userInput)) {}
					//
					else
					{
						changeAdminRole = true;
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

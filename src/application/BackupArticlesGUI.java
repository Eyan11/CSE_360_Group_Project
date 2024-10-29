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

import java.io.IOException;

//import database.AccountDatabase;

import java.sql.*;

import database.ArticleDatabase;

/*******
 * <p> BackupArticlesGUI Class </p>
 * 
 * <p> Description: User Interface for Backing-Up Articles </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/26/2024 Phase 2 Implementation and Documentation
 * 			1.25 10/28/2024 Added Button Functionality (waiting on ManageArticlesGUI)
 */

public class BackupArticlesGUI
{
	/*
	 * Variable Declarations
	 */

	//The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	//store inputed file path and group from user
	private String filePathInput = "";
	private String groupInput = "";
	
	//displays instructions for user input
	private Label filePathLabel = new Label("File Path: ");
	private Label instructionLabel = new Label("(leave empty for all groups)");
	private Label groupLabel = new Label("Groups: ");
	
	//text fields for user input
	private TextField filePathText = new TextField();
	private TextField groupText = new TextField();
	
	//buttons used to navigate interface and backing-up
	private Button backButton = new Button("<-");
	private Button backupButton = new Button("Backup");
	
	//declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param theRoot
	 */
	
	public BackupArticlesGUI(Pane theRoot) throws SQLException
	{	
		//utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		/*
		 * Label Creations
		 */
		
		//Label asking user for file path
		setupUI.SetupLabelUI(filePathLabel, "Arial", 36, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
		
		//Label letting user result of leaving group field left unfilled
		setupUI.SetupLabelUI(instructionLabel, "Arial", 18, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 175, 63, Color.BLACK);
		
		//Label asking user for group(s) they would like to back-up
		setupUI.SetupLabelUI(groupLabel, "Arial", 36, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 200, Color.BLACK);
		
		/*
		 * TextField Creations 
		 */
		
		//Text field that stores user's file path for back-up
		setupUI.SetupTextFieldUI(filePathText, "Arial", 18, 400, 40,
				Pos.BASELINE_LEFT, 10, 110, true);
		
		//Text field that stores user's group(s) for back-up
		setupUI.SetupTextFieldUI(groupText, "Arial", 18, 400, 40,
				Pos.BASELINE_LEFT, 10, 260, true);
		
		/*
		 * Button Creations
		 */
		
		//Button that returns user to previous interface
		setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		
		//Button that backs-up articles specified by user by group(s)
		setupUI.SetupButtonUI(backupButton, "Arial", 18, 200, 50,
        		Pos.CENTER, 150, 325, false, Color.BLACK);
		
		
		//Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(filePathLabel, instructionLabel, groupLabel, 
				filePathText, groupText, backButton, backupButton);
		
		/*
		 * Button Functionality
		 */
		
		/*****
		 * Back Button
		 */
		
		backButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{						
				//returns user back to previous page
				theRoot.getChildren().clear();  // Clear the current root
				//create new pane for next interface
				Pane newRoot = new Pane();
				//ManageArticlesGUI manageArticles = new ManageArticlesGUI(theRoot); // Returns user to previous interface
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
			    Stage currentStage = (Stage) theRoot.getScene().getWindow();
			    currentStage.setScene(newScene); // sets new scene
			}
		});
		
		/*****
		 * Backup Button
		 */
		
		backupButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{	
				//retrieves file path from user
				filePathInput = filePathText.getText();
				//retrieves group(s) from user
				groupInput = groupText.getText();
				
				//try and catch block needed for exception handling
				try {
					//Back-up Articles
					if(ArticleDatabase.backupArticles(filePathInput, groupInput))
					{
						//returns user back to previous page
						theRoot.getChildren().clear();  // Clear the current root
						//create new pane for next interface
						Pane newRoot = new Pane();
						//ManageArticlesGUI manageArticles = new ManageArticlesGUI(theRoot); // Returns user to previous interface
						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
					    Stage currentStage = (Stage) theRoot.getScene().getWindow();
					    currentStage.setScene(newScene); // sets new scene
					}
					else // Back-Up Failed
					{
						//print to console letting user know back-up failed
						System.out.println("Backup Failed!");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}

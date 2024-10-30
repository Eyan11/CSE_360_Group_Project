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
 * <p> Description: User Interface for Article Restoration </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/26/2024 Phase 2 Implementation and Documentation
 * 			1.25 10/28/2024 Added Button Functionality (waiting on ManageArticlesGUI)
 */

public class RestoreArticlesGUI
{
	/*
	 * Variable Declarations
	 */

	//the width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	//will store inputed file path for restoration
	private String filePathInput = "";
	
	//asks user to input a file path
	private Label filePathLabel = new Label("File Path: ");
	
	//text field for user file path
	private TextField filePathText = new TextField();
	
	//buttons used to navigate interface and restoration
	private Button backButton = new Button("<-");
	private Button overrideButton = new Button("Restore by Overriding");
	private Button mergeButton = new Button("Restore by Merging");
	
	//declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param theRoot
	 */
	
	public RestoreArticlesGUI(Pane theRoot) throws SQLException
	{	
		//utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		/*
		 * Label Creations
		 */
		
		//Label asking user for File Path
		setupUI.SetupLabelUI(filePathLabel, "Arial", 36, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
		
		/*
		 * TextField Creations 
		 */
		
		//Text Field storing file path
		setupUI.SetupTextFieldUI(filePathText, "Arial", 18, 475, 40,
				Pos.BASELINE_LEFT, 10, 110, true);
		
		/*
		 * Button Creations
		 */
		
		//Button that returns user back to previous page
		setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		
		//Button that restores articles with overriding versus merging
		setupUI.SetupButtonUI(overrideButton, "Arial", 24, 400, 100,
        		Pos.CENTER, 50, 175, false, Color.BLACK);
		
		//Button that restores articles with merging versus overriding
		setupUI.SetupButtonUI(mergeButton, "Arial", 24, 400, 100,
        		Pos.CENTER, 50, 300, false, Color.BLACK);
		
		
		//Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(filePathLabel, filePathText, backButton, overrideButton, mergeButton);
		
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
		 * Override Button
		 */
		
		overrideButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{	
				//retrieves file path from user
				filePathInput = filePathText.getText();
				
				//try and catch block needed for exception handling
				try {
					//Restore Articles
					if(ArticleDatabase.restoreByOverriding(filePathInput))
					{
						//returns user to previous page
						theRoot.getChildren().clear();  // Clear the current root
						//create new pane for next interface
						Pane newRoot = new Pane(); // 
						//ManageArticlesGUI manageArticles = new ManageArticlesGUI(theRoot); // Returns user to previous interface
						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
					    Stage currentStage = (Stage) theRoot.getScene().getWindow();
					    currentStage.setScene(newScene); // sets new scene
					}
					else // Restore Failed
					{
						//print to console letting user know restoration failed
						System.out.println("Restore by Overriding Failed!");
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
		
		/*****
		 * Merge Button
		 */
		
		mergeButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//retrieves file path from user
				filePathInput = filePathText.getText();
				
				//try and catch block needed for exception handling
				try {
					//Restore Articles
					if(ArticleDatabase.restoreByMerging(filePathInput))
					{
						//returns user to previous page
						theRoot.getChildren().clear();  // Clear the current root
						//create new pane for next interface
						Pane newRoot = new Pane(); // 
						//ManageArticlesGUI manageArticles = new ManageArticlesGUI(theRoot); // Returns user to previous interface
						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // Creates new scene
					    Stage currentStage = (Stage) theRoot.getScene().getWindow();
					    currentStage.setScene(newScene); // Sets new scene
					}
					else // Restore Failed
					{
						//print to console letting user know merging failed
						System.out.println("Restore by Merging Failed!");
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
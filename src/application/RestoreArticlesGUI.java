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

//import database.AccountDatabase;

import java.sql.*;

/*******
 * <p> BackupArticlesGUI Class </p>
 * 
 * <p> Description: ... </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/26/2024 Phase 2 Implementation and Documentation
 * 
 */

public class RestoreArticlesGUI
{
	/*
	 * Variable Declarations
	 */

	//The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	//
	private String filePathInput = "";
	
	//
	private Label filePathLabel = new Label("File Path: ");
	
	//text field for user input
	private TextField filePathText = new TextField();
	
	//
	private Button backButton = new Button("<-");
	private Button overrideButton = new Button("Restore by Overriding");
	private Button mergeButton = new Button("Restore by Merging");
	
	//declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param userPane
	 */
	
	public RestoreArticlesGUI(Pane theRoot) throws SQLException
	{	
		//utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		/*
		 * Label Creations
		 */
		
		//
		setupUI.SetupLabelUI(filePathLabel, "Arial", 36, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
		
		/*
		 * TextField Creations 
		 */
		
		//
		setupUI.SetupTextFieldUI(filePathText, "Arial", 18, 475, 40,
				Pos.BASELINE_LEFT, 10, 110, true);
		
		/*
		 * Button Creations
		 */
		
		//Button that ...
		setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		
		//Button that ...
		setupUI.SetupButtonUI(overrideButton, "Arial", 24, 400, 100,
        		Pos.CENTER, 50, 175, false, Color.BLACK);
		
		//Button that ...
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
				/*
				 * 
				 */
				
				theRoot.getChildren().clear();  // Clear the current root
				
				Pane newRoot = new Pane(); // 
				//ManageArticlesGUI manageArticles = new ManageArticlesGUI(theRoot); //
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
			    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
			    currentStage.setScene(newScene); // 
			}
		});
		
		/*****
		 * Override Button
		 */
		
		overrideButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{	
				/*
				 * 
				 */
				
				theRoot.getChildren().clear();  // Clear the current root
				
				Pane newRoot = new Pane(); // 
				//ManageArticlesGUI manageArticles = new ManageArticlesGUI(theRoot); //
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
			    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
			    currentStage.setScene(newScene); // 
			}
		});
		
		/*****
		 * Merge Button
		 */
		
		mergeButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{	
				/*
				 * 
				 */
				
				theRoot.getChildren().clear();  // Clear the current root
				
				Pane newRoot = new Pane(); // 
				//ManageArticlesGUI manageArticles = new ManageArticlesGUI(theRoot); //
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
			    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
			    currentStage.setScene(newScene); // 
			}
		});
	}
}

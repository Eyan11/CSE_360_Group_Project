package application;

import database.ArticleDatabase;

import javafx.scene.control.Label; // For Label object
import javafx.scene.control.Button; // For Button object
import javafx.geometry.Pos; // For Position object (vector2 coordinate used to describe position)
import javafx.scene.paint.Color; // To set color of UI elements
import javafx.scene.layout.Pane; // For Pane object

import javafx.event.ActionEvent; // For ActionEvent object
import javafx.event.EventHandler; // For EventHandler object
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.sql.*;

/*******
 * <p> ListByGroupGUI Class </p>
 * 
 * <p> Description: Interface that displays articles by Group (helper class) </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/28/2024 Phase 2 Implementation and Documentation
 * 			1.50 10/29/2024 Added Interface Dimensions
 * 			2.00 11/18/2024 Phase 3 Implementation and Documentation
 * 			2.25 11/20/2024 Finalization + Documentation
 * 
 */

public class ListByGroupGUI
{
	/*
	 * Variable Declarations
	 */

	// The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	// Stores inputed parameter from user
	private String group = "";
	private String contentLevel = "";
	private String articleContents = "";
	
	// Button to return to previous interface
	private Button backButton = new Button("<-");
	
	// Declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI + User Group + Content Level + Article Contents
	 * @param theRoot
	 */
	
	public ListByGroupGUI(Pane theRoot, String group, String contentLevel, String articleContents) throws SQLException
	{	
		// Utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();		
		
		// Initialize Strings with inputed parameter
		this.group = group;
		this.contentLevel = contentLevel;
		this.articleContents = articleContents;
		
		/*****
		 * ERROR FROM ARTICLE DATABASE
		 */
		
		// Grab articles from database that are related to user parameters
		String formattedInput = ArticleDatabase.searchByContents(this.group, this.contentLevel, this.articleContents);
		// Create a label with limited article(s) information
		Label displayArticles = new Label(formattedInput);
		
		/*
		 * Label Creations
		 */
		
		// Label that displays article(s) related to user parameters
		setupUI.SetupLabelUI(displayArticles, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 100, Color.BLACK);
		
		/*
		 * Button Creation
		 */
		
		// Button that returns user to previous interface
		setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		
		// Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(displayArticles, backButton);
		
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
				// Returns user back to previous page
				theRoot.getChildren().clear();  // Clear the current root
				
				// Create new pane for next interface
				Pane newRoot = new Pane();
				// Try and Catch Block for exception handling
				try {
					ListArticlesGUI listArticles = new ListArticlesGUI(newRoot); // ListArticlesGUI class
				} catch (SQLException e) {
				} // Returns user to previous interface
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
			    Stage currentStage = (Stage) theRoot.getScene().getWindow();
			    currentStage.setScene(newScene); // sets new scene
			}
		});
	}
}


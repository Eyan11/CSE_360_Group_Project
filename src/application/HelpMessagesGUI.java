package application;

import database.HelpMessageDatabase;
import database.LoginTracker;

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
 * <p> HelpMessagesGUI Class </p>
 * 
 * <p> Description:  </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 11/18/2024 Phase 3 Design and Implementation
 */

public class HelpMessagesGUI
{
	/*
	 * Variable Declarations
	 */

	// The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	// Buttons used for navigating interface and listing articles
	private Button backButton = new Button("<-");
	
	// Declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI + User ID
	 * @param theRoot
	 */
	
	public HelpMessagesGUI(Pane theRoot) throws SQLException
	{	
		// Grab article with the specified ID
		String allHelpMessages = HelpMessageDatabase.getAllHelpMessages();
	    
	    /*****
	     * Label Declaration
	     */
        
		Label helpMessages = new Label(allHelpMessages);
		
		// Utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		/*
		 * Label Creations
		 */
		
		// Labels that display...
		setupUI.SetupLabelUI(helpMessages, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
		
		/*
		 * Button Creation
		 */
		
		// Button that returns user to previous interface
		setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		
		// Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(backButton, helpMessages);
		
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
				if(LoginTracker.isInstructor()) {
					// Returns user back to previous page
					theRoot.getChildren().clear();  // Clear the current root
					// Create new pane for next interface
					Pane newRoot = new Pane();
					InstructorHomeGUI iHome = new InstructorHomeGUI(newRoot); // ListArticlesGUI class
					Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
				    Stage currentStage = (Stage) theRoot.getScene().getWindow();
				    currentStage.setScene(newScene); // sets new scene
				} else {
					// Returns user back to previous page
					theRoot.getChildren().clear();  // Clear the current root
					// Create new pane for next interface
					Pane newRoot = new Pane();
					AdminHome aHome = new AdminHome(newRoot); // AdminHomeGUI class
					Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
				    Stage currentStage = (Stage) theRoot.getScene().getWindow();
				    currentStage.setScene(newScene); // sets new scene
				}
			}
		});
	}
}


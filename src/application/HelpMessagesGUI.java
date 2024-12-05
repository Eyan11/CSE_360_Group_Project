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
 * <p> Description: Allows administrators and instructors to view student special or general help requests </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 11/18/2024 Phase 3 Design and Implementation
 * 			1.50 11/20/2024 Documentation + Finalization
 */

public class HelpMessagesGUI
{
	/*
	 * Variable Declarations
	 */

	// The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	// Button used for returning to previous interface
	private Button backButton = new Button("<-");
	
	// Declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param theRoot
	 */
	
	public HelpMessagesGUI(Pane theRoot) throws SQLException
	{	
		// Utilizes the SetUpElements class for the Label and Button
		setupUI = new SetupUIElements();
		
		// Grab article with the specified ID
		String allHelpMessages = HelpMessageDatabase.getAllHelpMessages();
	    
	    /*****
	     * Label Declaration
	     */
        
		// Label that displays student help requests
		Label helpMessages = new Label(allHelpMessages);
		
		/*
		 * Label Creation
		 */
		
		// Label that displays student help requests
		setupUI.SetupLabelUI(helpMessages, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 30, Color.BLACK);
		
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
	}
}




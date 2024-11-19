package application;

import database.GroupDatabase;

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

import java.sql.*;

/*******
 * <p> CreateGroupGUI Class </p>
 * 
 * <p> Description: ... </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 11/18/2024 Phase 3 Implementation and Documentation
 */

public class CreateGroupGUI
{
	/*
	 * Variable Declarations
	 */

	// The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	// Stores...
	private String groupName = "";
	private String firstAdmin = "";
	
	// Labels for ...
	private Label groupLabel = new Label("Group Name: ");
	private Label adminLabel = new Label("First Admin: ");
	private Label typeLabel = new Label("Group Type: ");
	
	// Text Field for ...
	private TextField groupText = new TextField();
	private TextField adminText = new TextField();
	
	// Buttons for navigating interface and ...
	private Button backButton = new Button("<-");
	private Button generalButton = new Button("General Access");
	private Button specialButton = new Button("Special Access");
	
	// Declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param theRoot
	 */
	
	public CreateGroupGUI(Pane theRoot) throws SQLException
	{	
		// Utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		/*
		 * Label Creations
		 */
		
		// Label that...
		setupUI.SetupLabelUI(groupLabel, "Arial", 36, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
		
		// Label that...
		setupUI.SetupLabelUI(adminLabel, "Arial", 36, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 125, Color.BLACK);
		
		// Label that...
		setupUI.SetupLabelUI(typeLabel, "Arial", 36, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 200, Color.BLACK);
		
		/*
		 * TextField Creations 
		 */
		
		// Text Field that...
		setupUI.SetupTextFieldUI(adminText, "Arial", 18, 400, 40,
				Pos.BASELINE_LEFT, 250, 50, true);
		
		// Text Field that...
		setupUI.SetupTextFieldUI(groupText, "Arial", 18, 400, 40,
				Pos.BASELINE_LEFT, 250, 125, true);
		
		/*
		 * Button Creations
		 */
		
		// Button that returns user to previous interface
		setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		// Button that ...
		setupUI.SetupButtonUI(generalButton, "Arial", 18, 175, 50,
        		Pos.CENTER, 35, 325, false, Color.BLACK);
		// Button that ...
		setupUI.SetupButtonUI(specialButton, "Arial", 18, 175, 50,
        		Pos.CENTER, 250, 325, false, Color.BLACK);
		
		
		// Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(groupLabel, adminLabel, typeLabel, adminText,
				groupText, backButton, generalButton, specialButton);
		
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
				ManageArticlesGUI manageArticles = new ManageArticlesGUI(newRoot); // Returns user to previous interface
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
			    Stage currentStage = (Stage) theRoot.getScene().getWindow();
			    currentStage.setScene(newScene); // sets new scene
			}
		});
		
		/*****
		 * ...
		 */
		
		generalButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{	
				// ...
				firstAdmin = adminText.getText();
				groupName = groupText.getText();
				
				GroupDatabase.createGroup(groupName, firstAdmin, "general access");
				
				// Returns user back to previous page
				theRoot.getChildren().clear();  // Clear the current root
				// Create new pane for next interface
				Pane newRoot = new Pane();
				ManageArticlesGUI manageArticles = new ManageArticlesGUI(newRoot); // Returns user to previous interface
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
			    Stage currentStage = (Stage) theRoot.getScene().getWindow();
			    currentStage.setScene(newScene); // sets new scene
			}
		});
		
		/*****
		 * ...
		 */
		
		specialButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{	
				// ...
				firstAdmin = adminText.getText();
				groupName = groupText.getText();
				
				GroupDatabase.createGroup(groupName, firstAdmin, "special access");
				
				// Returns user back to previous page
				theRoot.getChildren().clear();  // Clear the current root
				// Create new pane for next interface
				Pane newRoot = new Pane();
				ManageArticlesGUI manageArticles = new ManageArticlesGUI(newRoot); // Returns user to previous interface
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
			    Stage currentStage = (Stage) theRoot.getScene().getWindow();
			    currentStage.setScene(newScene); // sets new scene
			}
		});
	}
}

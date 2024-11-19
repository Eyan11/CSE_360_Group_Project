package application;

import database.ArticleDatabase;

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
 * <p> ListArticlesGUI Class </p>
 * 
 * <p> Description: Interface that allows article display by ID or Group </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/28/2024 Phase 2 Implementation and Documentation
 * 			2.00 11/18/2024 Phase 3 Re-haul (Added Three Filters)
 */

public class ListArticlesGUI
{
	/*
	 * Variable Declarations
	 */

	// The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	// String that...
	private String groupFilter = "";
	private String contentLvlFilter = "";
	private String articleContentsFilter = "";
	
	
	// Labels that displays interface instructions
	private Label groupLabel = new Label("Group Filter: ");
	private Label contentLvlLabel = new Label("Content Level Filter: ");
	private Label articleLabel = new Label("Search Article Contents of ID/Sequence Number: ");
	
	// Text Fields for user input (ID or Group(s)
	private TextField groupText = new TextField();
	private TextField contentLvlText = new TextField();
	private TextField articleText = new TextField();
	
	// Buttons used for navigating interface and listing articles
	private Button backButton = new Button("<-");
	private Button listIdButton = new Button("Search ID/Sequence Number");
	private Button listContentsButton = new Button("Search Contents");
	
	// Declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param theRoot
	 */
	
	public ListArticlesGUI(Pane theRoot) throws SQLException
	{	
		// Utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		/*
		 * Label Creations
		 */
		
		// Label that...
		setupUI.SetupLabelUI(groupLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
		// Label that...
		setupUI.SetupLabelUI(contentLvlLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 120, Color.BLACK);
		// Label that...
		setupUI.SetupLabelUI(articleLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 190, Color.BLACK);
		
		/*
		 * TextField Creations 
		 */
		
		// Text Field that..
		setupUI.SetupTextFieldUI(groupText, "Arial", 18, 400, 40,
				Pos.BASELINE_LEFT, 10, 80, true);
		
		// Text Field that...
		setupUI.SetupTextFieldUI(contentLvlText, "Arial", 18, 400, 40,
				Pos.BASELINE_LEFT, 10, 80, true);
				
		// Text Field that...
		setupUI.SetupTextFieldUI(articleText, "Arial", 18, 400, 40,
				Pos.BASELINE_LEFT, 10, 80, true);
		
		/*
		 * Button Creations
		 */
		
		// Button that returns user to previous interface
		setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		// Button that takes user to page with Article information specified by the ID
		setupUI.SetupButtonUI(listIdButton, "Arial", 18, 175, 50,
        		Pos.CENTER, 35, 325, false, Color.BLACK);
		// Button that takes user to page with Article information specified by the Group(s)
		setupUI.SetupButtonUI(listContentsButton, "Arial", 18, 175, 50,
        		Pos.CENTER, 250, 325, false, Color.BLACK);
		
		
		// Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(groupLabel, contentLvlLabel, articleLabel, groupText,
				contentLvlText, articleText, backButton, listIdButton, listContentsButton);
		
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
		 * List by ID Button
		 */
		
		listIdButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{	
				// Retrieves ID from user and converts it into a integer
				articleContentsFilter = articleText.getText();
				int integerInput = Integer.parseInt(articleContentsFilter);

				// Make sure inputed Article ID from user is valid
				if(ArticleDatabase.doesArticleIDExist(integerInput))
				{
					//Send user to ListByIdGUI class
					theRoot.getChildren().clear();  // Clear the current root
					
					// Create new pane for next interface
					Pane newRoot = new Pane();
					try {
						ListByIdGUI listID = new ListByIdGUI(newRoot, integerInput); // ListByIdGUI helper class
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
				    Stage currentStage = (Stage) theRoot.getScene().getWindow();
				    currentStage.setScene(newScene); // sets new scene
				}
				else
				{
					// inputed user Article ID is invalid
					System.out.println("Article ID Does Not Exist!");
				}
			}
		});
		
		/*****
		 * List ...
		 */
		
		listContentsButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{	
				// Retrieves...
				groupFilter = groupText.getText();
				contentLvlFilter = contentLvlText.getText();
				articleContentsFilter = articleText.getText();
				
				theRoot.getChildren().clear();  // Clear the current root
				// Create new pane for next interface
				Pane newRoot = new Pane();
				// Try and Catch block for exception handling
				try {
					// ListByGroup helper class
					ListByGroupGUI listGroup = new ListByGroupGUI(newRoot, groupFilter, contentLvlFilter, articleContentsFilter);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
			    Stage currentStage = (Stage) theRoot.getScene().getWindow();
			    currentStage.setScene(newScene); // sets new scene
			}
		});
	}
}

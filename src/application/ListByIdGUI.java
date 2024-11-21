package application;

import database.ArticleDatabase;
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
 * <p> ListByIdGUI Class </p>
 * 
 * <p> Description: Interface that displays articles by ID (helper class) </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/28/2024 Phase 2 Implementation and Documentation
 * 			1.50 10/29/2024 Added Interface Dimensions
 * 			2.00 11/18/2024 Phase 3 Implementation and Documentation
 * 			2.25 11/20/2024 Finalization + Documentation
 */

public class ListByIdGUI
{
	/*
	 * Variable Declarations
	 */

	// The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	// Stores user ID as an integer
	private int userID;
	
	// Stores Article Information
	private String id = "";
	private String header = "";
	private String title = "";
	private String author = "";
	private String description = "";
	private String keywords = "";
	private String level = "";
	private String groups = "";
    private String body = "";
    private String references = "";
	
	// Displays interface information
	private Label articleLabel = new Label("Article Information: ");
	
	// Buttons used for navigating interface and listing articles
	private Button backButton = new Button("<-");
	
	// Declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI + User ID
	 * @param theRoot
	 */
	
	public ListByIdGUI(Pane theRoot, int userID) throws SQLException
	{	
		// Utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		// Initialize user ID with inputed parameter
		this.userID = userID;
		
		// Grab article with the specified ID
		String unformattedInput = ArticleDatabase.getArticleByID(this.userID);
	    // Split the input by commas
	    String[] formattedInput = unformattedInput.split("\\+");

        // Parse and store each piece of data
        id = formattedInput[0];
        header = formattedInput[1];
        title = formattedInput[2];
        author = formattedInput[3];
        description = formattedInput[4];
        keywords = formattedInput[5];
        level = formattedInput[6];
        groups = formattedInput[7];
        if(LoginTracker.usingAdminRole())
        {
        	references = formattedInput[8];
        }
        else
        {
        	body = formattedInput[8];
        	references = formattedInput[9];
        }
	    
	    /*****
	     * Label Declaration with Article Data
	     */
		
		Label idLabel = new Label("ID: " + id);
		Label headerLabel = new Label("Header: " + header);
		Label titleLabel = new Label("Title: " + title);
		Label authorLabel = new Label("Author: " + author);
		Label descriptionLabel = new Label("Description: " + description);
		Label keywordsLabel = new Label("Keywords: " + keywords);
		Label levelLabel = new Label("Content Level: " + level);
		Label groupsLabel = new Label("Group(s): " + groups);
		Label bodyLabel = new Label("Body: " + body);
		Label referencesLabel = new Label("References: " + references);
		
		/*
		 * Label Creations
		 */
		
		// Label that displays interface information
		setupUI.SetupLabelUI(articleLabel, "Arial", 36, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
		
		// Labels that display Article ID Information
		setupUI.SetupLabelUI(idLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 100, Color.BLACK);
		setupUI.SetupLabelUI(headerLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 125, Color.BLACK);
		setupUI.SetupLabelUI(titleLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 150, Color.BLACK);
		setupUI.SetupLabelUI(authorLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 175, Color.BLACK);
		setupUI.SetupLabelUI(descriptionLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 200, Color.BLACK);
		setupUI.SetupLabelUI(keywordsLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 225, Color.BLACK);
		setupUI.SetupLabelUI(levelLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 250, Color.BLACK);
		setupUI.SetupLabelUI(groupsLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 275, Color.BLACK);
		setupUI.SetupLabelUI(bodyLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 300, Color.BLACK);
		setupUI.SetupLabelUI(referencesLabel, "Arial", 11, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 325, Color.BLACK);
		
		/*
		 * Button Creation
		 */
		
		// Button that returns user to previous interface
		setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		
		// Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(articleLabel, idLabel, headerLabel, titleLabel, authorLabel, descriptionLabel, 
				keywordsLabel, levelLabel, groupsLabel, bodyLabel, referencesLabel, backButton);
		
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
				try {
					ListArticlesGUI listArticle = new ListArticlesGUI(newRoot); // ListArticlesGUI class
				} catch (SQLException e) {
				} // Returns user to previous interface
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
			    Stage currentStage = (Stage) theRoot.getScene().getWindow();
			    currentStage.setScene(newScene); // sets new scene
			}
		});
	}
}


package application;

import javafx.scene.control.Label; // For Label object
import javafx.scene.control.TextField; // For TextField object
import javafx.scene.control.Button; // For Button object
import javafx.scene.control.CheckBox; // For CheckBox object
import javafx.geometry.Pos; // For Position object (vector2 coordinate used to describe position)
import javafx.scene.paint.Color; // To set color of UI elements

import javafx.scene.layout.Pane; // For Pane object
import javafx.event.ActionEvent; // For ActionEvent object
import javafx.event.EventHandler; // For EventHandler object

import javafx.stage.Stage;
import javafx.scene.Scene;

import database.ArticleDatabase;

import java.io.IOException;
import java.sql.*;

/*******
 * <p> ListByGroupGUI Class </p>
 * 
 * <p> Description: Interface that displays articles by Group (helper class) </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/28/2024 Phase 2 Implementation and Documentation
 */

public class ListByGroupGUI
{
	/*
	 * Variable Declarations
	 */

	//The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	//stores Group or Id
	private String userInput = "";
	
	//displays interface information
	private Label userLabel = new Label("Enter Id or Group(s): ");
	
	//text field for user input (II or Group(s)
	private TextField userText = new TextField();
	
	//buttons used for navigating interface and listing articles
	private Button backButton = new Button("<-");
	private Button listIdButton = new Button("List by ID");
	private Button listGroupButton = new Button("List by Group(s)");
	
	//declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param theRoot
	 */
	
	public ListByGroupGUI(Pane theRoot, String group) throws SQLException
	{	
		this.userInput = group;
		Label groupLabel = new Label(ArticleDatabase.getArticlesByGroups(userInput));
		
		//utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		/*
		 * Label Creations
		 */
		
		//Label that...
		setupUI.SetupLabelUI(userLabel, "Arial", 36, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
		
		/*
		 * TextField Creations 
		 */
		
		//Text field that..
		setupUI.SetupTextFieldUI(userText, "Arial", 18, 400, 40,
				Pos.BASELINE_LEFT, 10, 110, true);
		
		/*
		 * Button Creations
		 */
		
		//Button that returns user to previous interface
		setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
		//...
		setupUI.SetupButtonUI(listIdButton, "Arial", 18, 150, 50,
        		Pos.CENTER, 50, 325, false, Color.BLACK);
		//...
		setupUI.SetupButtonUI(listGroupButton, "Arial", 18, 150, 50,
        		Pos.CENTER, 200, 325, false, Color.BLACK);
		
		
		//Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(userLabel, userText, backButton, 
				listIdButton, listGroupButton);
		
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
	}
}

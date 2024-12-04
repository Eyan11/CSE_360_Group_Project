package application;

import java.sql.SQLException;

import database.LoginTracker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos; // For positioning UI elements
import javafx.scene.Scene;
import javafx.scene.control.Button; // For Button object
import javafx.scene.layout.Pane; // Changed from StackPane to Pane
import javafx.scene.layout.VBox; // For vertical layout
import javafx.scene.paint.Color; // For setting color of UI elements
import javafx.scene.text.Font; // For setting font of text elements
import javafx.scene.text.Text; // For displaying text in the UI
import javafx.stage.Stage;

/**
 * <p> StudentHomePage. </p>
 * 
 * <p> Description: A JavaFX class responsible for displaying the Student home page GUI, 
 * and providing functionality for various student actions and logout.</p>
 * 
 * @author Sriram Nesan
 * 
 * @version 1.01        11/19/2024 Updated with missing buttons
 *  
 */

public class StudentHomeGUI {

    /*
     * Variable Declarations
     */
    
    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;
    
    // The quit button that terminates the execution of this application
    Button buttonQuit = new Button("X");
    
    // Declaration of SetupUIElements Object
    public SetupUIElements setupUI;

    // Constructor for setting up the Student Home GUI
    public StudentHomeGUI(Pane theRoot) { 
        
        // Instantiate SetupUIElements
        setupUI = new SetupUIElements();

        // Create "Student Home" title
        Text homeText = new Text("Student Home");
        homeText.setFont(new Font("Arial", 32));  
        homeText.setFill(Color.BLACK);  
        homeText.setLayoutX((WINDOW_WIDTH - homeText.getLayoutBounds().getWidth()) / 2); // Center horizontally
        homeText.setLayoutY(30);  // Set a fixed vertical position

        // Create Buttons for Student actions
        Button genericMessageButton = new Button("Generic Message");
        Button specificMessageButton = new Button("Specific Message");
        Button articleSearchButton = new Button("Article Search");
        Button logoutButton = new Button("Logout");
        Button quitButton = new Button("Quit");
        
        // Quit Button (Top-Right Corner)
        setupUI.SetupButtonUI(buttonQuit, "Arial", 11, 25, 20, 
        		Pos.BASELINE_LEFT, 465, 10, false, Color.RED);

        // Set up the buttons using SetupUIElements
        setupUI.SetupButtonUI(genericMessageButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(specificMessageButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(articleSearchButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(logoutButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(quitButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);

        // Position the buttons in a vertical layout
        VBox vbox = new VBox(20, genericMessageButton, specificMessageButton, articleSearchButton, logoutButton, quitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX((WINDOW_WIDTH - 200) / 2); // Center align based on button width
        vbox.setLayoutY(80); // Set a suitable vertical position

        // Add title and buttons to the root
        theRoot.getChildren().addAll(homeText, buttonQuit, vbox);

        // Handle button functionalities
        handleGenericMessage(genericMessageButton, theRoot);
        handleSpecificMessage(specificMessageButton, theRoot);
        handleArticleSearch(articleSearchButton, theRoot);
        handleLogout(logoutButton, theRoot);
        handleQuit(quitButton);
        
        buttonQuit.setOnAction(new EventHandler<>()
    	{
    		public void handle(ActionEvent event) 
    		{						
    			System.exit(0);
    		}
    	});
    }

    private void handleGenericMessage(Button genericMessageButton, Pane theRoot) {
        genericMessageButton.setOnAction(event -> {
            theRoot.getChildren().clear();  // Clear the current root

            Pane newRoot = new Pane();
            SendGenericMessageGUI generic = new SendGenericMessageGUI(newRoot);  // Create a new instance of LoginGUI
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
          Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }

    private void handleSpecificMessage(Button specificMessageButton, Pane theRoot) {
        specificMessageButton.setOnAction(event -> {
            theRoot.getChildren().clear();  // Clear the current root

            Pane newRoot = new Pane();
            SendSpecificMessageGUI specific= new SendSpecificMessageGUI(newRoot);  // Create a new instance of LoginGUI
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }


    private void handleArticleSearch(Button articleSearchButton, Pane theRoot) {
        articleSearchButton.setOnAction(event -> {
            // Clear the current root
            theRoot.getChildren().clear();

            // Create a new pane for the next interface
            Pane newRoot = new Pane();

            // Try to navigate to ListArticlesGUI
            try {
				ListArticlesGUI listArticles = new ListArticlesGUI(newRoot);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Navigate to ListArticlesGUI

            // Set up the new scene
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene); // Switch to the new scene
        });
    }


    private void handleLogout(Button logoutButton, Pane theRoot) {
        logoutButton.setOnAction(event -> {
            LoginTracker.logout();
            
            theRoot.getChildren().clear();  // Clear the current root
            
            
            Pane newRoot = new Pane();
            LoginGUI loginPage = new LoginGUI(newRoot);  // Create a new instance of LoginGUI
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }

    private void handleQuit(Button quitButton) {
        quitButton.setOnAction(event -> {
            // Close the application
            System.exit(0);
        });
    }
}

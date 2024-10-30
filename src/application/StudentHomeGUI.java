package application;

import javafx.geometry.Pos; // For positioning UI elements
import javafx.scene.Scene;
import javafx.scene.control.Button; // For Button object
import javafx.scene.layout.Pane; // Changed from StackPane to Pane
import javafx.scene.paint.Color; // For setting color of UI elements
import javafx.scene.text.Font; // For setting font of text elements
import javafx.scene.text.Text; // For displaying text in the UI
import javafx.stage.Stage;

/**
 * <p> StudentInstructorHomePage. </p>
 * 
 * <p> Description: A JavaFX class responsible for displaying the Student/Instructor home page GUI, 
 * and providing logout functionality for the user.</p>
 * 
 * @author Sriram Nesan
 * 
 * @version 1.00		10/9/2024 Phase 1 implementation and documentation
 *  
 */

public class StudentHomeGUI {

    /*
     * Variable Declarations
     */
    
    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;
    
    
    // Declaration of SetupUIElements Object
    public SetupUIElements setupUI;

    // Constructor for setting up the Student/Instructor Home GUI
    public StudentHomeGUI(Pane theRoot) {  // Changed StackPane to Pane
        
        // Instantiate SetupUIElements
        setupUI = new SetupUIElements();

        // Create "Home" text
        Text homeText = new Text("Home");
        homeText.setFont(new Font("Arial", 32));  
        homeText.setFill(Color.BLACK);  
        homeText.setLayoutX((WINDOW_WIDTH - homeText.getLayoutBounds().getWidth()) / 2); // Center horizontally
        homeText.setLayoutY(50);  // Set a fixed vertical position

        // Create the Logout Button
        Button logoutButton = new Button("Logout");
        setupUI.SetupButtonUI(logoutButton, "Arial", 14, 200, Pos.CENTER, 0, 50, false, Color.BLACK);

        //set layout positions for the logout button
        logoutButton.setLayoutX(150);  // Center horizontally
        logoutButton.setLayoutY(100);  // Position below the title

        // Add elements
        theRoot.getChildren().addAll(homeText, logoutButton);

        handleLogout(logoutButton, theRoot);  // Handle logout
    }

    private void handleLogout(Button logoutButton, Pane theRoot) {
        // Event handler for the logout button
        logoutButton.setOnAction(event -> {
            // LoginGUI
        	theRoot.getChildren().clear();  // Clear
        	
        	Pane newRoot = new Pane();
        	LoginGUI loginPage = new LoginGUI(theRoot);  // Create a new instance of LoginGUI
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
		    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
		    currentStage.setScene(newScene); //
        });
    }
}
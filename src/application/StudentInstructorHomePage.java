package application;

import javafx.geometry.Pos; // For positioning UI elements
import javafx.scene.control.Button; // For Button object
import javafx.scene.layout.Pane; // Changed from StackPane to Pane
import javafx.scene.paint.Color; // For setting color of UI elements
import javafx.scene.text.Font; // For setting font of text elements
import javafx.scene.text.Text; // For displaying text in the UI

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

public class StudentInstructorHomePage {

    /*
     * Variable Declarations
     */
    
    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;
    
    
    // Declaration of SetupUIElements Object
    public SetupUIElements setupUI;

    // Constructor for setting up the Student/Instructor Home GUI
    public StudentInstructorHomePage(Pane root) {  // Changed StackPane to Pane
        
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
        root.getChildren().addAll(homeText, logoutButton);

        handleLogout(logoutButton, root);  // Handle logout
    }

    private void handleLogout(Button logoutButton, Pane root) {
        // Event handler for the logout button
        logoutButton.setOnAction(event -> {
            // LoginGUI
            root.getChildren().clear();  // Clear
            LoginGUI loginPage = new LoginGUI(root);  // Create a new instance of LoginGUI
        });
    }
}

package application;

import javafx.geometry.Pos; // For positioning UI elements
import javafx.scene.control.Button; // For Button object
import javafx.scene.layout.StackPane; // For layout that allows UI elements to be stacked
import javafx.scene.layout.VBox; // For layout that arranges UI elements vertically
import javafx.scene.paint.Color; // For setting color of UI elements
import javafx.scene.text.Font; // For setting font of text elements
import javafx.scene.text.Text; // For displaying text in the UI


/**
 * <p> StudentInstructorHomePage. </p>
 * 
 * <p> Description: A JavaFX class responsible for displaying the Student/Instructor home page GUI, 
 * and providing logout functionality for the user.</p>
 * 
 * <p> Source: Lynn Rober Carter from InClassDocumentationProject1 project, UserInterface class, 
 * 				available at: https://canvas.asu.edu/courses/193728/files/93600828?module_item_id=14807672 
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
	
	
	//declaration of SetupUIElements Object
    public SetupUIElements setupUI;

    // Constructor for setting up the Student/Instructor Home GUI
    public StudentInstructorHomePage(StackPane root) {
    	


        // Instantiate SetupUIElements
        setupUI = new SetupUIElements();

        // Create "Home" text
        Text homeText = new Text("Home");
        homeText.setFont(new Font("Arial", 32));  
        homeText.setFill(Color.BLACK);  

        // Create the Logout Button
        Button logoutButton = new Button("Logout");
        setupUI.SetupButtonUI(logoutButton, "Arial", 14, 200, Pos.CENTER, 0, 50, false, Color.BLACK);

        // VBox Layout for the "Home" text
        VBox topVBox = new VBox();
        topVBox.setAlignment(Pos.TOP_CENTER); // Align at the top, centered horizontally
        topVBox.setSpacing(20);
        topVBox.getChildren().add(homeText);

        // StackPane Layout for the "Logout" button
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER); // Center the button
        stackPane.getChildren().add(logoutButton);

        // Combine the top VBox and the StackPane
        root.getChildren().addAll(topVBox, stackPane);
    }
}

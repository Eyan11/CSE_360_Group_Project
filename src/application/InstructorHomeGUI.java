package application;

import javafx.geometry.Pos; // For positioning UI elements
import javafx.scene.control.Button; // For Button object
import javafx.scene.layout.Pane; // Change from StackPane to Pane
import javafx.scene.layout.VBox; // For layout that arranges UI elements vertically
import javafx.scene.paint.Color; // For setting color of UI elements
import javafx.scene.text.Font; // For setting font of text elements
import javafx.scene.text.Text; // For displaying text in the UI

/**
 * <p> AdminHome. </p>
 * 
 * <p> Description: A JavaFX class responsible for displaying the admin home GUI and providing buttons 
 * for admin-related actions such as managing accounts and articles.</p>
 * 
 * @version 1.01        10/29/2024 Updated layout based on design image
 *  
 */

public class InstructorHomeGUI {

    /*
     * Variable Declarations
     */
    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;

    // Declaration of SetupUIElements Object
    public SetupUIElements setupUI;

    // Constructor for setting up the InstructorHome GUI
    public InstructorHomeGUI(Pane root) {
        // Instantiate
        setupUI = new SetupUIElements();

        // Create "Instructor Home" title
        Text title = new Text("Instructor Home");
        title.setFont(new Font("Arial", 32));  // Set font
        title.setFill(Color.BLACK);  // Set the text color
        title.setLayoutX((WINDOW_WIDTH - title.getLayoutBounds().getWidth()) / 2);
        title.setLayoutY(30);  // Set vertical position for title

        Button manageArticlesButton = new Button("Manage Articles");
        Button logoutButton = new Button("Logout");

        // Set up the buttons using SetupUIElements
        setupUI.SetupButtonUI(manageArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(logoutButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);

        // Position the buttons in a vertical layout
        VBox vbox = new VBox(20, manageArticlesButton, logoutButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX((WINDOW_WIDTH - 200) / 2); // Center align based on button width
        vbox.setLayoutY(100);

        // Add both title and VBox layout to root
        root.getChildren().addAll(title, vbox);

        // Handle logout functionality
        handleLogout(logoutButton, root);
    }

    private void handleLogout(Button logoutButton, Pane root) {
        // Event handler for the logout button
        logoutButton.setOnAction(event -> {
            // LoginGUI
            root.getChildren().clear();  // Clear the current root
            LoginGUI loginPage = new LoginGUI(root);  // Create a new instance of LoginGUI
        });
    }
}

package application;

import java.sql.SQLException;

import database.LoginTracker;
import javafx.geometry.Pos; // For positioning UI elements
import javafx.scene.Scene;
import javafx.scene.control.Button; // For Button object
import javafx.scene.layout.Pane; // Change from StackPane to Pane
import javafx.scene.layout.VBox; // For layout that arranges UI elements vertically
import javafx.scene.paint.Color; // For setting color of UI elements
import javafx.scene.text.Font; // For setting font of text elements
import javafx.scene.text.Text; // For displaying text in the UI
import javafx.stage.Stage;

/**
 * <p> InstructorHomeGUI. </p>
 * 
 * <p> Description: A JavaFX class responsible for displaying the Instructor home GUI and providing buttons 
 * for managing accounts, articles, help messages, and logging out.</p>
 * 
 * @version 1.02        11/19/2024 Added missing buttons based on design image
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
    public InstructorHomeGUI(Pane theRoot) {
        // Instantiate
        setupUI = new SetupUIElements();

        // Create "Instructor Home" title
        Text title = new Text("Instructor Home");
        title.setFont(new Font("Arial", 32));  // Set font
        title.setFill(Color.BLACK);  // Set the text color
        title.setLayoutX((WINDOW_WIDTH - title.getLayoutBounds().getWidth()) / 2);
        title.setLayoutY(30);  // Set vertical position for title

        // Buttons for Instructor actions
        Button manageAccountsButton = new Button("Manage Accounts");
        Button manageArticlesButton = new Button("Manage Articles");
        Button helpMessagesButton = new Button("Help Messages");
        Button logoutButton = new Button("Logout");

        // Set up the buttons using SetupUIElements
        setupUI.SetupButtonUI(manageAccountsButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(manageArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(helpMessagesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(logoutButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);

        // Position the buttons in a vertical layout
        VBox vbox = new VBox(20, manageAccountsButton, manageArticlesButton, helpMessagesButton, logoutButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX((WINDOW_WIDTH - 200) / 2); // Center align based on button width
        vbox.setLayoutY(100);

        // Add both title and VBox layout to root
        theRoot.getChildren().addAll(title, vbox);

        // Handle button functionality
        ManageAccounts(manageAccountsButton, theRoot);
        ManageArticles(manageArticlesButton, theRoot);
        ManageHelpMessages(helpMessagesButton, theRoot);
        Logout(logoutButton, theRoot);
    }

    private void Logout(Button logoutButton, Pane theRoot) {
        // Event handler for the logout button
        logoutButton.setOnAction(event -> {
        	
            LoginTracker.logout();
            
            // Navigate to LoginGUI
            theRoot.getChildren().clear();  // Clear the current root
            
            Pane newRoot = new Pane();
            LoginGUI loginPage = new LoginGUI(newRoot);  // Create a new instance of LoginGUI
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene); // sets new scene
        });
    }

    private void ManageArticles(Button manageArticlesButton, Pane theRoot) {
        manageArticlesButton.setOnAction(event -> {
            theRoot.getChildren().clear();
            
            Pane newRoot = new Pane();
            ManageArticlesGUI manageArticles = new ManageArticlesGUI(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene); // sets new scene
        });
    }

    private void ManageAccounts(Button manageAccountsButton, Pane theRoot) {
        manageAccountsButton.setOnAction(event -> {
            theRoot.getChildren().clear();

            Pane newRoot = new Pane();
            ManageAccountsGUI manageAccounts = new ManageAccountsGUI(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene); // sets new scene
        });
    }

    private void ManageHelpMessages(Button helpMessagesButton, Pane theRoot) {
        helpMessagesButton.setOnAction(event -> {
            theRoot.getChildren().clear();

            Pane newRoot = new Pane();
            try {
				HelpMessagesGUI manageHelpMessages = new HelpMessagesGUI(newRoot);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene); // sets new scene
        });
    }
}

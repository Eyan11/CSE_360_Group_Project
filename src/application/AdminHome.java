package application;

import javafx.geometry.Pos; // For positioning UI elements
import javafx.scene.control.Button; // For Button object
import javafx.scene.layout.StackPane; // For layout that allows UI elements to be stacked
import javafx.scene.layout.VBox; // For layout that arranges UI elements vertically
import javafx.scene.paint.Color; // For setting color of UI elements
import javafx.scene.text.Font; // For setting font of text elements
import javafx.scene.text.Text; // For displaying text in the UI


/**
 * <p> AdminHome. </p>
 * 
 * <p> Description: A JavaFX class responsible for displaying the admin home GUI and providing buttons 
 * for admin-related actions such as inviting users, resetting accounts, and more.</p>
 * 
 * <p> Source: Lynn Rober Carter from InClassDocumentationProject1 project, UserInterface class, 
 * 				available at: https://canvas.asu.edu/courses/193728/files/93600828?module_item_id=14807672 
 * 
 * @author Sriram Nesan
 * 
 * @version 1.00		10/9/2024 Phase 1 implementation and documentation
 *  
 */


public class AdminHome {
	
	/*
	 * Variable Declarations
	 */
	
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	
	//declaration of SetupUIElements Object
    public SetupUIElements setupUI;

    // Constructor for setting up the AdminHome GUI
    public AdminHome(StackPane root) {
        // Instantiate
        setupUI = new SetupUIElements();

        // Create "Admin Home"
        Text title = new Text("Admin Home");
        title.setFont(new Font("Arial", 32)); // font size and font
        title.setFill(Color.BLACK);  // Set the text color

        // Buttons for Admin actions
        Button inviteUserButton = new Button("Invite User");
        Button resetAccountButton = new Button("Reset an Account");
        Button deleteAccountButton = new Button("Delete an Account");
        Button listUsersButton = new Button("List Users");
        Button manageRolesButton = new Button("Add/Remove Roles");
        Button logoutButton = new Button("Logout");

        // Set up the buttons using SetupUIElements
        setupUI.SetupButtonUI(inviteUserButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(resetAccountButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(deleteAccountButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(listUsersButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(manageRolesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(logoutButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);

        // VBox Layout for the title
        VBox topVBox = new VBox();
        topVBox.setAlignment(Pos.TOP_CENTER); // align
        topVBox.getChildren().add(title);

        // centered vertically and horizontally
        VBox buttonVBox = new VBox(10); //10 spacing
        buttonVBox.setAlignment(Pos.CENTER); // Center 
        buttonVBox.getChildren().addAll(inviteUserButton, resetAccountButton, deleteAccountButton, listUsersButton, manageRolesButton, logoutButton);

        // Add both title and button layout to root
        root.getChildren().addAll(topVBox, buttonVBox); // Add both
    }
}

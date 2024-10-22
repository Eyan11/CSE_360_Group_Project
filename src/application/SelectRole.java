package application;

import javafx.geometry.Pos; // For positioning UI elements
import javafx.scene.control.Button; // For Button object
import javafx.scene.layout.Pane; // Changed from StackPane to Pane
import javafx.scene.layout.VBox; // For layout that arranges UI elements vertically
import javafx.scene.paint.Color; // For setting color of UI elements
import javafx.scene.text.Font; // For setting font of text elements
import javafx.scene.text.Text; // For displaying text in the UI
import database.AccountDatabase;	// To use account database in different package


/**
 * <p> SelectRole. </p>
 * 
 * <p> Description: A JavaFX class responsible for displaying the role selection GUI and enabling or 
 * disabling buttons based on the user's role, using data from the AccountDatabase.</p>
 * 
 * @author Sriram Nesan
 * 
 * @version 1.00		10/9/2024 Phase 1 implementation and documentation
 *  
 */


public class SelectRole {

    /*
     * Variable Declarations
     */
    
    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;

    // Declaration of SetupUIElements Object
    private SetupUIElements setupUI = new SetupUIElements();

    // Constructor that takes in the username to determine user roles
    public SelectRole(Pane root, String username) {  // Changed StackPane to Pane
        // Create "Select Role" text
        Text title = new Text("Select Role");
        title.setFont(new Font("Arial", 32));  // Set font
        title.setFill(Color.BLACK);  // Set the text color

        // Create Role Buttons
        Button studentButton = new Button("Student");
        Button instructorButton = new Button("Instructor");
        Button adminButton = new Button("Admin");

        // Set up the buttons
        setupUI.SetupButtonUI(studentButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(instructorButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(adminButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);

        // Disable buttons based on the user's role by calling the role-checking methods
        setButtonAccess(studentButton, instructorButton, adminButton, username);

        // VBox Layout
        VBox vbox = new VBox(10); // Spacing of 10
        vbox.setAlignment(Pos.CENTER); // Center layout
        vbox.getChildren().addAll(title, studentButton, instructorButton, adminButton);

        // Add all elements to the root Pane
        root.getChildren().add(vbox);  // Pane now instead of StackPane
        
        handleRoleSelection(studentButton, instructorButton, adminButton, username, root);  // Update method call
    }

    private void handleRoleSelection(Button studentButton, Button instructorButton, Button adminButton, String username, Pane root) {  // Changed StackPane to Pane
        // Event handler for the student button
        studentButton.setOnAction(event -> {
            // Simulate navigating to the Student/Instructor HomePage
            StudentInstructorHomePage studentPage = new StudentInstructorHomePage(root);
        });

        // Event handler for the instructor button
        instructorButton.setOnAction(event -> {
            // Student/Instructor HomePage
            StudentInstructorHomePage instructorPage = new StudentInstructorHomePage(root);
        });

        // Event handler for the admin button
        adminButton.setOnAction(event -> {
            // Simulate navigating to the AdminHome page
            AdminHome adminPage = new AdminHome(root);
        });
    }

    // Logic to enable/disable buttons based on the user role
    private void setButtonAccess(Button studentBtn, Button instructorBtn, Button adminBtn, String username) {
        // Check the user's roles using the AccountDatabase methods
        boolean isStudent = AccountDatabase.isStudentRole(username);
        boolean isInstructor = AccountDatabase.isInstructorRole(username);
        boolean isAdmin = AccountDatabase.isAdminRole(username);

        // Enable or disable buttons based on the user's role
        if (!isStudent) {
            studentBtn.setDisable(true);
        }
        if (!isInstructor) {
            instructorBtn.setDisable(true);
        }
        if (!isAdmin) {
            adminBtn.setDisable(true);
        }
    }
}

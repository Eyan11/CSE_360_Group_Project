package application;

import javafx.geometry.Pos; // For positioning UI elements
import javafx.scene.Scene;
import javafx.scene.control.Button; // For Button object
import javafx.scene.layout.Pane; // Changed from StackPane to Pane
import javafx.scene.layout.VBox; // For layout that arranges UI elements vertically
import javafx.scene.paint.Color; // For setting color of UI elements
import javafx.scene.text.Font; // For setting font of text elements
import javafx.scene.text.Text; // For displaying text in the UI
import javafx.stage.Stage;
import database.LoginTracker;

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
    // Constructor that takes in the username to determine user roles
    public SelectRole(Pane theRoot, String username) {
        // Log in the user using LoginTracker
        if (!LoginTracker.login(username)) {
            // If login fails (e.g., username doesn't exist), show an error or exit
            System.out.println("Error: Invalid username. Unable to proceed.");
            
            theRoot.getChildren().clear();  // Clear the current root
            Pane newRoot = new Pane();
            LoginGUI loginPage = new LoginGUI(newRoot);  // Create a new instance of LoginGUI
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        }

        // Create "Select Role" text
        Text title = new Text("Select Role");
        title.setFont(new Font("Arial", 32));  
        title.setFill(Color.BLACK);  
        title.setLayoutX((WINDOW_WIDTH - title.getLayoutBounds().getWidth()) / 2); // Center horizontally
        title.setLayoutY(50);  // Set a fixed vertical position

        // Create Role Buttons
        Button studentButton = new Button("Student");
        Button instructorButton = new Button("Instructor");
        Button adminButton = new Button("Admin");

        // Set up the buttons
        setupUI.SetupButtonUI(studentButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(instructorButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(adminButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);

        // Disable buttons based on the user's role
        setButtonAccess(studentButton, instructorButton, adminButton);

        // VBox Layout
        VBox vbox = new VBox(20, studentButton, instructorButton, adminButton); // Spacing of 10
        vbox.setAlignment(Pos.CENTER); // Center layout
        vbox.setLayoutX((WINDOW_WIDTH - 200) / 2); // Center align based on button width
        vbox.setLayoutY(80); // Set a suitable vertical position
        
        //vbox.getChildren().addAll(title, studentButton, instructorButton, adminButton);

        // Add all elements to the root Pane
        theRoot.getChildren().addAll(title, vbox);

        // Handle role selection
        handleRoleSelection(studentButton, instructorButton, adminButton, username, theRoot);
    }

    private void handleRoleSelection(Button studentButton, Button instructorButton, Button adminButton, String username, Pane theRoot) {  // Changed StackPane to Pane
        // Event handler for the student button
        studentButton.setOnAction(event -> {
        	// Update selected role
        	LoginTracker.selectStudentRole();
        	
            // Simulate navigating to the Student/Instructor HomePage
        	theRoot.getChildren().clear();
        	
        	Pane newRoot = new Pane();
            StudentHomeGUI studentPage = new StudentHomeGUI(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
		    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
		    currentStage.setScene(newScene); //
        });

        // Event handler for the instructor button
        instructorButton.setOnAction(event -> {
        	// Update selected role
        	LoginTracker.selectInstructorRole();
        	
            // Student/Instructor HomePage
        	theRoot.getChildren().clear();
        	
        	Pane newRoot = new Pane();
            InstructorHomeGUI instructorPage = new InstructorHomeGUI(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
		    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
		    currentStage.setScene(newScene); //
        });

        // Event handler for the admin button
        adminButton.setOnAction(event -> {
        	// Update selected role
        	LoginTracker.selectAdminRole();
        	
            // Simulate navigating to the AdminHome page
        	theRoot.getChildren().clear();
        	
        	Pane newRoot = new Pane();
            AdminHome adminPage = new AdminHome(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
		    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
		    currentStage.setScene(newScene); //
        });
    }

    // Logic to enable/disable buttons based on the user role
    // Logic to enable/disable buttons based on the logged-in user's role
    private void setButtonAccess(Button studentBtn, Button instructorBtn, Button adminBtn) {
        // Use LoginTracker to check roles
        boolean isStudent = LoginTracker.isStudent();
        boolean isInstructor = LoginTracker.isInstructor();
        boolean isAdmin = LoginTracker.isAdmin();

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


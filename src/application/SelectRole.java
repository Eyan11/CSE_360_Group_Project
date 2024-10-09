package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SelectRole {

    private SetupUIElements setupUI = new SetupUIElements();

    // Constructor that takes in the username to determine user roles
    public SelectRole(StackPane root, String username) {
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

        // Add all elements to the root StackPane
        root.setAlignment(Pos.CENTER);  // Center everything in the StackPane
        root.getChildren().add(vbox);
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

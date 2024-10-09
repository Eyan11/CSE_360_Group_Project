package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectRole {

    // previous logic: already have determined user role
    private String userRole = "Student"; // hard-coded for now
    
    // Instantiate 
    public SetupUIElements setupUI = new SetupUIElements();

    // Constructor
    public SelectRole(Pane root) {
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

        // disable buttons based on the user's role
        setButtonAccess(studentButton, instructorButton, adminButton);

        // VBox Layout
        VBox vbox = new VBox(10); // Spacing of 10
        vbox.setAlignment(Pos.CENTER); //center
        vbox.getChildren().addAll(title, studentButton, instructorButton, adminButton);

        // Add all elements to the root Pane
        root.getChildren().add(vbox);
    }

    // Logic to enable/disable buttons based on the user role
    private void setButtonAccess(Button studentBtn, Button instructorBtn, Button adminBtn) {
        switch (userRole) {
            case "Student":
                instructorBtn.setDisable(true); // Disable "Instructor" button
                adminBtn.setDisable(true); // Disable "Admin" button
                break;
            case "Instructor":
                adminBtn.setDisable(true); // Disable "Admin" button
                break;
            case "Admin":
                // No action needed, all buttons are enabled for Admin
                break;
        }
    }
}


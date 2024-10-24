package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ResetPassword {

    // Constructor 
    public ResetPassword(Pane root) {
        // Create the title "Reset Password"
        Text title = new Text("Reset Password");
        title.setFont(new Font("Arial", 32));
        title.setFill(Color.BLACK);
        title.setLayoutX(100);
        title.setLayoutY(50);

        // Create Labels and PasswordFields
        Label newPasswordLabel = new Label("New Password:");
        newPasswordLabel.setLayoutX(50);
        newPasswordLabel.setLayoutY(120);

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setLayoutX(200);
        newPasswordField.setLayoutY(120);

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setLayoutX(50);
        confirmPasswordLabel.setLayoutY(170);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setLayoutX(200);
        confirmPasswordField.setLayoutY(170);

        // Error message label (initially hidden)
        Label errorMessage = new Label();
        errorMessage.setTextFill(Color.RED);
        errorMessage.setLayoutX(150);
        errorMessage.setLayoutY(220);
        errorMessage.setVisible(false);

        // Reset button
        Button resetButton = new Button("Reset");
        resetButton.setPrefWidth(100);
        resetButton.setLayoutX(200);
        resetButton.setLayoutY(250);

        // Set up button action
        resetButton.setOnAction(event -> {
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Check if passwords match
            if (newPassword.equals(confirmPassword)) {
                // Clear fields and hide error message if passwords match
                newPasswordField.clear();
                confirmPasswordField.clear();
                errorMessage.setVisible(false);
            } else {
                // Show error message if passwords don't match
                errorMessage.setText("Passwords do not match!");
                errorMessage.setVisible(true);
            }
        });

        // Add all elements
        root.getChildren().addAll(title, newPasswordLabel, newPasswordField, confirmPasswordLabel, confirmPasswordField, errorMessage, resetButton);
    }
}

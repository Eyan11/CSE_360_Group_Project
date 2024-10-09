package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectRole extends Application {

    // previous logic: already have determined user role
    private String userRole = "Student"; // hard-coded for now
    // we will replace this with the logic to see if the user is using the accountdatabase
    
    // Instantiate 
    public SetupUIElements setupUI = new SetupUIElements();

    @Override
    public void start(Stage primaryStage) {
    	// Create "Select Role" text
    	Text title = new Text("Select Role");
    	title.setFont(new Font("Arial", 32));  // Set font
    	title.setFill(Color.BLACK);  // Set the text
    	
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

        // Scene
        Scene scene = new Scene(vbox, 300, 200);

        // Primary Stage
        primaryStage.setTitle("Select Role");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Set button access (enable/disable) based on the user's role
     */
    private void setButtonAccess(Button studentBtn, Button instructorBtn, Button adminBtn) {
        switch (userRole) {
            case "Student":
                // A student can only access "Student" options
                instructorBtn.setDisable(true); // Disable "Instructor" button
                adminBtn.setDisable(true); // Disable "Admin" button
                break;
            case "Instructor":
                // An instructor can access "Instructor" and "Student" options,not "Admin"
                adminBtn.setDisable(true); // Disable "Admin" button
                break;
            case "Admin":
                // No action
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

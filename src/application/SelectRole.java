package application;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectRole {

    //previous logic: already have determined user role
    private String userRole = "Student"; //hard-coded for now, could be "Instructor" or "Admin"

    public void start(Stage primaryStage) {
        // Title Text
        Text title = new Text("Select Role");

        // Role Buttons
        Button studentButton = new Button("Student");
        Button instructorButton = new Button("Instructor");
        Button adminButton = new Button("Admin");

        // Call the method to disable buttons based on the user's role
        setButtonAccess(studentButton, instructorButton, adminButton);

        // VBox Layout
        VBox vbox = new VBox(10); // Spacing of 10 between elements
        vbox.getChildren().addAll(title, studentButton, instructorButton, adminButton);

        // Scene
        Scene scene = new Scene(vbox, 300, 200); 

        // Primary Stage
        primaryStage.setTitle("Select Role");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * button access (enable/disable) based on the user's role
     */
    private void setButtonAccess(Button studentBtn, Button instructorBtn, Button adminBtn) {
        switch (userRole) {
            case "Student":
                // A student can only access "Student" options
                instructorBtn.setDisable(true); // Disable "Instructor" button
                adminBtn.setDisable(true); // Disable "Admin" button
                break;
            case "Instructor":
                // An instructor can access "Instructor" and "Student" options, but not "Admin"
                adminBtn.setDisable(true); // Disable "Admin" button
                break;
            case "Admin":
                // No action needed since all buttons are enabled by default
                break;
          /*  default:
                studentBtn.setDisable(true);
                instructorBtn.setDisable(true);
                adminBtn.setDisable(true);
                System.out.println("Invalid role: access denied");
                */
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
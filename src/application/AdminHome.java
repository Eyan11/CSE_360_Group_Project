package application;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminHome{

    public void start(Stage primaryStage) {
        // Title Text
        Text title = new Text("Admin Home");

        // Buttons for Admin actions
        Button inviteUserButton = new Button("Invite User");
        Button resetAccountButton = new Button("Reset an Account");
        Button deleteAccountButton = new Button("Delete an Account");
        Button listUsersButton = new Button("List Users");
        Button manageRolesButton = new Button("Add/Remove Roles");
        Button logoutButton = new Button("Logout");

        // Event handling for each button (currently just prints action)
        inviteUserButton.setOnAction(e -> System.out.println("Invite User clicked"));
        resetAccountButton.setOnAction(e -> System.out.println("Reset Account clicked"));
        deleteAccountButton.setOnAction(e -> System.out.println("Delete Account clicked"));
        listUsersButton.setOnAction(e -> System.out.println("List Users clicked"));
        manageRolesButton.setOnAction(e -> System.out.println("Manage Roles clicked"));
        logoutButton.setOnAction(e -> {
            System.out.println("Logging out...");
            primaryStage.close();  // Close window or navigate back to login
        });

        // VBox Layout
        VBox vbox = new VBox(10); 
        vbox.getChildren().addAll(title, inviteUserButton, resetAccountButton, deleteAccountButton, listUsersButton, manageRolesButton, logoutButton);

        // Scene
        Scene scene = new Scene(vbox, 300, 300); 

        // Primary Stage
        primaryStage.setTitle("Admin Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

	private static void launch(String[] args) {
		// TODO Auto-generated method stub
		
	}
}

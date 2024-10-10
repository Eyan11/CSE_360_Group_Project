package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminHome {
	
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;

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

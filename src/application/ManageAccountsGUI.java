package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ManageAccountsGUI {

    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;
    public SetupUIElements setupUI;

    public ManageAccountsGUI(Pane root) {
        setupUI = new SetupUIElements();

        Text title = new Text("Manage Accounts");
        title.setFont(new Font("Arial", 24));
        title.setFill(Color.BLACK);
        title.setLayoutX((WINDOW_WIDTH - title.getLayoutBounds().getWidth()) / 2);
        title.setLayoutY(50);

        Button backButton = new Button("Back");
        Button modifyAccountsButton = new Button("Modify Accounts");
        Button inviteUserButton = new Button("Invite User");

        setupUI.SetupButtonUI(backButton, "Arial", 12, 80, Pos.TOP_LEFT, 10, 10, false, Color.BLACK);
        setupUI.SetupButtonUI(modifyAccountsButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(inviteUserButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);

        backButton.setPrefWidth(80);  // Set smaller width
        backButton.setPrefHeight(30); // Set smaller height
        backButton.setLayoutX(10);    // Set x position close to the left edge
        backButton.setLayoutY(10);
        modifyAccountsButton.setLayoutX(150);
        modifyAccountsButton.setLayoutY(100);
        inviteUserButton.setLayoutX(150);
        inviteUserButton.setLayoutY(150);

        root.getChildren().addAll(title, backButton, modifyAccountsButton, inviteUserButton);

        handleBack(backButton, root);
    }

    private void handleBack(Button backButton, Pane root) {
        backButton.setOnAction(event -> {
            root.getChildren().clear();
            AdminHome adminHome = new AdminHome(root); 
       });
    }
}


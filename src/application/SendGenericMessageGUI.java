package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import database.GroupDatabase;
import database.HelpMessageDatabase;

public class SendGenericMessageGUI {
    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 300;

    private Label sceneLabel = new Label("Send Generic Message");
    private Label groupLabel = new Label("Group:");
    private ComboBox<String> groupDropdown = new ComboBox<>();
    private Button backButton = new Button("Back");
    private Button sendMessageButton = new Button("Send Message");
    private Label statusLabel = new Label();

    public SendGenericMessageGUI(Pane genericMessagePane) {
        // Set up UI elements
        setupLabelUI(sceneLabel, "Arial", 24, WINDOW_WIDTH, Pos.CENTER, 0, 10, Color.BLACK);
        setupLabelUI(groupLabel, "Arial", 16, WINDOW_WIDTH, Pos.BASELINE_LEFT, 10, 100, Color.BLACK);
        setupDropdownUI(groupDropdown, WINDOW_WIDTH / 2 - 100, 100, 200);

        setupButtonUI(backButton, "Arial", 14, 80, Pos.BASELINE_LEFT, 10, 50, Color.GREEN);
        setupButtonUI(sendMessageButton, "Arial", 14, 120, Pos.CENTER, WINDOW_WIDTH / 2 - 60, 180, Color.GREEN);
        setupLabelUI(statusLabel, "Arial", 14, WINDOW_WIDTH, Pos.CENTER, 0, 230, Color.RED);

        genericMessagePane.getChildren().addAll(sceneLabel, groupLabel, groupDropdown, backButton, sendMessageButton, statusLabel);

        // Populate the dropdown with group names
        populateGroupDropdown();

        // Back Button Logic
        backButton.setOnAction(event -> {
            genericMessagePane.getChildren().clear(); // Clear the current root

            // Navigate back to ManageHelpMessagesGUI
            Pane newRoot = new Pane();
            StudentHomeGUI manageHelpMessagesGUI = new StudentHomeGUI(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
            Stage currentStage = (Stage) genericMessagePane.getScene().getWindow();
            currentStage.setScene(newScene);
        });

        // Send Message Button Logic
        sendMessageButton.setOnAction(event -> handleSendGenericMessage());
    }

    private void populateGroupDropdown() {
        try {
            // Fetch authorized group names from GroupDatabase
            String groupNames = GroupDatabase.getAllAuthorizedGroupNames(true); // Assuming true for viewer access
            if (!groupNames.isEmpty()) {
                // Split group names by "+" and add to dropdown
                String[] groups = groupNames.split("\\+");
                groupDropdown.getItems().addAll(groups);
            } else {
                statusLabel.setText("No groups available to select.");
            }
        } catch (Exception e) {
            statusLabel.setText("Error fetching group names.");
            e.printStackTrace();
        }
    }

    private void handleSendGenericMessage() {
        String selectedGroup = groupDropdown.getValue();

        if (selectedGroup == null || selectedGroup.isEmpty()) {
            statusLabel.setText("Please select a group.");
            return;
        }

        boolean success = HelpMessageDatabase.createGenericMessage(selectedGroup);

        if (success) {
            statusLabel.setText("Generic message sent successfully!");
            statusLabel.setTextFill(Color.GREEN);
        } else {
            statusLabel.setText("Failed to send generic message.");
            statusLabel.setTextFill(Color.RED);
        }
    }

    private void setupLabelUI(Label label, String font, double fontSize, double minWidth, Pos pos, double x, double y, Color color) {
        label.setFont(Font.font(font, fontSize));
        label.setMinWidth(minWidth);
        label.setAlignment(pos);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setTextFill(color);
    }

    private void setupButtonUI(Button button, String font, double fontSize, double width, Pos pos, double x, double y, Color color) {
        button.setFont(Font.font(font, fontSize));
        button.setMinWidth(width);
        button.setAlignment(pos);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setTextFill(color);
    }

    private void setupDropdownUI(ComboBox<String> dropdown, double x, double y, double width) {
        dropdown.setLayoutX(x);
        dropdown.setLayoutY(y);
        dropdown.setPrefWidth(width);
    }
}

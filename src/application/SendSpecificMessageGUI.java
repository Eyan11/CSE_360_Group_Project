package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import database.HelpMessageDatabase;

public class SendSpecificMessageGUI {
    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 300;

    private Label sceneLabel = new Label("Send Specific Message");
    private Label messageLabel = new Label("Message:");
    private TextArea messageTextArea = new TextArea();
    private Button backButton = new Button("Back");
    private Button sendMessageButton = new Button("Send Message");

    public SendSpecificMessageGUI(Pane messagePane) {
        setupLabelUI(sceneLabel, "Arial", 24, WINDOW_WIDTH, Pos.CENTER, 0, 10, Color.BLACK);
        
        setupButtonUI(backButton, "Arial", 14, 80, Pos.BASELINE_LEFT, 10, 50, Color.GREEN);

        setupLabelUI(messageLabel, "Arial", 16, WINDOW_WIDTH, Pos.BASELINE_LEFT, 10, 100, Color.BLACK);

        messageTextArea.setLayoutX(10);
        messageTextArea.setLayoutY(130);
        messageTextArea.setPrefWidth(WINDOW_WIDTH - 20);
        messageTextArea.setPrefHeight(60);
        messageTextArea.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

        setupButtonUI(sendMessageButton, "Arial", 14, 120, Pos.CENTER, WINDOW_WIDTH / 2 - 60, 210, Color.GREEN);

        messagePane.getChildren().addAll(sceneLabel, backButton, messageLabel, messageTextArea, sendMessageButton);

        // Button logic
        backButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
                messagePane.getChildren().clear(); // clear the current root
                
                // Navigate to the student home 
                Pane newRoot = new Pane(); // create new root
                StudentHomeGUI studenthome = new StudentHomeGUI(newRoot); // call previous interface
                Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // create new scene
                Stage currentStage = (Stage) messagePane.getScene().getWindow();
                currentStage.setScene(newScene); // set new scene
            }
        });

        sendMessageButton.setOnAction(event -> handleSendMessage());
    }

    private void handleSendMessage() {
        String message = messageTextArea.getText();
        if (message.isEmpty()) {
            sceneLabel.setText("Message cannot be empty!");
            sceneLabel.setTextFill(Color.RED);
        } else {
            boolean success = HelpMessageDatabase.createSpecificMessage(message); // Store in database
            if (success) {
                sceneLabel.setText("Message sent and saved successfully!");
                sceneLabel.setTextFill(Color.GREEN);
                messageTextArea.clear(); // Clear the text area after sending
            } else {
                sceneLabel.setText("Failed to save the message.");
                sceneLabel.setTextFill(Color.RED);
            }
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
}

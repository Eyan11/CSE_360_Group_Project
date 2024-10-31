package application;

import database.ArticleDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.SQLException;
//ALL NOTES CRITICAL FOR OTHER DEVELOPERS READING THE CODE AND CONNECTING IT TO THEIR OWN ARE PREFACED BY "DEVELOPER NOTE: "
//However, it is still highly recommended that you read ALL comments throughout the program before doing anything 
//(especially before changing anything!)

//DEVELOPER NOTE: Additional imports may be necessary for this program to work with the rest of phase 2.

/**
* <p> SetUpUIElements. </p>
* 
* <p> Description: A JavaFX helper class to initialize the properties of UI elements.</p>
* 
* <p> Source: Lynn Robert Carter from InClassDocumentationProject1 project, UserInterface class, 
* 				available at: https://canvas.asu.edu/courses/193728/files/93600828?module_item_id=14807672 
* 
* @author Cadon Duong 
* 
* @version 1.00		10/30/2024 Phase 2 implementation and documentation
*  
*/
public class ModifyArticlesGUI {
    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;
    private Pane pane;
    private Label sceneLabel = new Label("Modify Articles");
    private Button backButton = new Button("Back");

    private Label articlesLabel = new Label("All Articles");
    private Label idHeader = new Label("ID:");
    private Label headerHeader = new Label("Header:");
    private Label titleHeader = new Label("Title:");
    private Label groupHeader = new Label("Group:");

    private Label idLabel = new Label("ID:");
    private Label errorLabel = new Label("");  // Displays error on invalid input

    private TextField idTextField = new TextField();
    private Button editButton = new Button("Edit");
    private Button deleteButton = new Button("Delete");

    public ModifyArticlesGUI(Pane pane) {
        setupLabelUI(sceneLabel, "Arial", 24, WINDOW_WIDTH, Pos.CENTER, 0, 10, Color.BLACK);
        setupButtonUI(backButton, "Arial", 14, 80, Pos.BASELINE_LEFT, 10, 50, Color.GREEN);
        setupLabelUI(articlesLabel, "Arial", 18, WINDOW_WIDTH, Pos.CENTER, 0, 90, Color.BLACK);

        // Set up table headers
        setupLabelUI(idHeader, "Arial", 14, 50, Pos.CENTER_LEFT, 40, 130, Color.BLACK);
        setupLabelUI(headerHeader, "Arial", 14, 100, Pos.CENTER_LEFT, 90, 130, Color.BLACK);
        setupLabelUI(titleHeader, "Arial", 14, 100, Pos.CENTER_LEFT, 220, 130, Color.BLACK);
        setupLabelUI(groupHeader, "Arial", 14, 100, Pos.CENTER_LEFT, 350, 130, Color.BLACK);

        // Display article data rows
        displayArticleRows(pane);

        // Error message label
        setupLabelUI(errorLabel, "Arial", 14, WINDOW_WIDTH - 20, Pos.CENTER, 10, 280, Color.RED);

        // Set up ID label and input field
        setupLabelUI(idLabel, "Arial", 14, 50, Pos.CENTER_LEFT, 40, 300, Color.BLACK);
        setupTextFieldUI(idTextField, "Arial", 14, 80, Pos.CENTER_LEFT, 90, 320);

        // Set up edit and delete buttons
        setupButtonUI(editButton, "Arial", 14, 80, Pos.CENTER, 150, 320, Color.GREEN);
        setupButtonUI(deleteButton, "Arial", 14, 80, Pos.CENTER, 250, 320, Color.GREEN);

        // Button actions
        editButton.setOnAction(event -> handleEdit());
        deleteButton.setOnAction(event -> handleDelete());

        // Add components to the pane
        pane.getChildren().addAll(sceneLabel, backButton, articlesLabel, idHeader, headerHeader, titleHeader, groupHeader, idLabel, errorLabel, idTextField, editButton, deleteButton);

        // Handle the Back button
        backButton.setOnAction(event -> handleBack(pane));
    }

    // Method to retrieve and display articles in rows
    private void displayArticleRows(Pane pane) {
        try {
            String allArticles = ArticleDatabase.getAllArticles(); //allArticles accessing all the articles currently in database
            if (allArticles.isEmpty()) { // if there are no articles in the database then 
                Label noDataLabel = new Label("No articles found."); // it will say none found 
                setupLabelUI(noDataLabel, "Arial", 14, WINDOW_WIDTH, Pos.CENTER, 0, 160, Color.GRAY);
                pane.getChildren().add(noDataLabel); // display it on pane 
                return;
            }

            String[] articleEntries = allArticles.split("\\|");  // Each article separated by "|"
            int yOffset = 160;

            for (String entry : articleEntries) {
                String[] articleData = entry.split(",");
                if (articleData.length >= 4) {  // ID, Header, Title, Group fields are present
                    String displayText = articleData[0] + "          " + articleData[1] + "       " + articleData[2] + "       " + articleData[3];
                    Label articleRow = new Label(displayText);
                    setupLabelUI(articleRow, "Arial", 14, WINDOW_WIDTH - 20, Pos.CENTER_LEFT, 40, yOffset, Color.GRAY);
                    pane.getChildren().add(articleRow);
                    yOffset += 30;
                }
            }
        } catch (SQLException e) {
            errorLabel.setText("Error loading articles.");
            e.printStackTrace();
        }
    }

    // Method to handle edit action
    private void handleEdit() {
    	String temp = "deleteme";
        if (idTextField.getText().isEmpty()) { // if nothing is in the id text box
            errorLabel.setText("Error: Please enter an article ID.");
        } else {
            int id = Integer.parseInt(idTextField.getText()); //id is set to id entered
            try {
				if (ArticleDatabase.doesArticleIDExist(id)) { 
	                pane.getChildren().clear();  // Clear the current pane
	                new EditArticleGUI(pane, temp, id);
				} else {
				    errorLabel.setText("Error: Article ID not found.");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    // Method to handle delete action
    private void handleDelete() {
        if (idTextField.getText().isEmpty()) {
            errorLabel.setText("Error: Please enter an article ID.");
        } else {
            int id = Integer.parseInt(idTextField.getText());
            try {
				if (ArticleDatabase.deleteArticle(id)) {
				    errorLabel.setTextFill(Color.GREEN);
				    errorLabel.setText("Article ID " + id + " deleted successfully.");
				    idTextField.clear();
				    pane.getChildren().clear();
				    displayArticleRows(pane); // Refresh display after deletion
				} else {
				    errorLabel.setTextFill(Color.RED);
				    errorLabel.setText("Error: Failed to delete Article ID " + id + ".");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    // Method to handle back action and navigate to ManageArticlesGUI
    private void handleBack(Pane pane) {
        pane.getChildren().clear();
        Pane newRoot = new Pane();
        new ManageArticlesGUI(newRoot);
        Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        Stage currentStage = (Stage) pane.getScene().getWindow();
        currentStage.setScene(newScene);
    }

    // Helper method to set up label UI
    private void setupLabelUI(Label label, String font, double fontSize, double minWidth, Pos pos, double x, double y, Color color) {
        label.setFont(Font.font(font, fontSize));
        label.setMinWidth(minWidth);
        label.setAlignment(pos);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setTextFill(color);
    }

    // Helper method to set up button UI
    private void setupButtonUI(Button button, String font, double fontSize, double width, Pos pos, double x, double y, Color color) {
        button.setFont(Font.font(font, fontSize));
        button.setMinWidth(width);
        button.setAlignment(pos);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setTextFill(color);
    }

    // Helper method to set up text field UI
    private void setupTextFieldUI(TextField textField, String font, double fontSize, double width, Pos pos, double x, double y) {
        textField.setFont(Font.font(font, fontSize));
        textField.setMinWidth(width);
        textField.setAlignment(pos);
        textField.setLayoutX(x);
        textField.setLayoutY(y);
    }
}

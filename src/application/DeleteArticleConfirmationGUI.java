package application;
import java.sql.SQLException;

import database.ArticleDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
* @version 1.00		10/29/2024 Phase 2 implementation and documentation
*  
*/
public class DeleteArticleConfirmationGUI {
    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;

    private Label sceneLabel = new Label("Delete Confirmation");
    private Label messageLabel;// implementing logic for id later 
    private Button yesButton = new Button("Yes"); // will add logic to remove article from database
    private Button noButton = new Button("No"); // return back to previous modifyarticle page 
    private Label errorLabel = new Label("Error");
    
    /*
     * Constructor
     */
    
    public DeleteArticleConfirmationGUI(Pane pane, int id ) {
    	System.out.println("Delete 3");
    	
    	messageLabel = new Label("Are you sure you want to delete the article? ID: " + id +"?"); 
        // sets up the label for the title 
        setupLabelUI(sceneLabel, "Arial", 24, WINDOW_WIDTH, Pos.CENTER, 0, 10, Color.BLACK);

        // the confirmation label setup
        setupLabelUI(messageLabel, "Arial", 16, WINDOW_WIDTH - 20, Pos.CENTER, 10, 100, Color.BLACK);
        setupLabelUI(errorLabel, "Arial", 16, WINDOW_WIDTH - 20, Pos.CENTER, 10, 100, Color.RED);

        // setup for yes and no button
        setupButtonUI(yesButton, "Arial", 14, 80, Pos.CENTER, 150, 250, Color.GREEN);
        setupButtonUI(noButton, "Arial", 14, 80, Pos.CENTER, 270, 250, Color.GREEN);

        // put all the labels and buttons needed onto the pane 
        pane.getChildren().addAll(sceneLabel, messageLabel, yesButton, noButton);
        yesButton.setOnAction(event -> yesButtonFunction(pane, id));
        noButton.setOnAction(event -> noButtonFunction(pane));
    }
    
    /*
     * yesButtonFunction
     */
    
    private void yesButtonFunction(Pane pane, int id) {
    	boolean isdeleted = true; //new

		isdeleted = ArticleDatabase.deleteArticle(id);
		
    	if (!isdeleted) { 
    		errorLabel.setText("failed to delete article with id" + id);
    	}
    	else { // if deleted switch scene back to previous
    		pane.getChildren().clear();
    		Pane newRoot = new Pane();
    		new ModifyArticlesGUI(newRoot);
    		Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
    		Stage currentStage = (Stage) pane.getScene().getWindow();
    		currentStage.setScene(newScene);
    	}
    	
    }
    
    
    private void noButtonFunction(Pane pane) {
        // Clear the current pane and navigate back to ModifyArticlesGUI without deleting anything
        pane.getChildren().clear();
        Pane newRoot = new Pane();
        new ModifyArticlesGUI(newRoot);

        // Set up the new scene and replace current scene
        Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        Stage currentStage = (Stage) pane.getScene().getWindow();
        currentStage.setScene(newScene);
    }
//label ui
    
    
    private void setupLabelUI(Label label, String font, double fontSize, double minWidth, Pos pos, double x, double y, Color color) {
        label.setFont(Font.font(font, fontSize));
        label.setMinWidth(minWidth);
        label.setAlignment(pos);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setTextFill(color);
    }
//button ui
    
    
    private void setupButtonUI(Button button, String font, double fontSize, double width, Pos pos, double x, double y, Color color) {
        button.setFont(Font.font(font, fontSize));
        button.setMinWidth(width);
        button.setAlignment(pos);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setTextFill(color);
    }
}

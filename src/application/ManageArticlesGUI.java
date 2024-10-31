package application;

import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ManageArticlesGUI {

    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;
    public SetupUIElements setupUI;

    public ManageArticlesGUI(Pane theRoot) {
        setupUI = new SetupUIElements();

        Text title = new Text("Manage Articles");
        title.setFont(new Font("Arial", 24));
        title.setFill(Color.BLACK);
        title.setLayoutX((WINDOW_WIDTH - title.getLayoutBounds().getWidth()) / 2);
        title.setLayoutY(50);

        Button backButton = new Button("Back");
        Button modifyArticlesButton = new Button("Modify Articles");
        Button createArticleButton = new Button("Create Article");
        Button backupArticlesButton = new Button("Backup Articles");
        Button restoreArticlesButton = new Button("Restore Articles");
        Button listArticlesButton = new Button("List Articles");

        setupUI.SetupButtonUI(backButton, "Arial", 12, 80, Pos.TOP_LEFT, 10, 10, false, Color.BLACK);
        setupUI.SetupButtonUI(modifyArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(createArticleButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(backupArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(restoreArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(listArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);

        
        backButton.setPrefWidth(80);  // Set smaller width
        backButton.setPrefHeight(30); // Set smaller height
        backButton.setLayoutX(10);    // Set x position close to the left edge
        backButton.setLayoutY(10);
        modifyArticlesButton.setLayoutX(150);
        modifyArticlesButton.setLayoutY(100);
        createArticleButton.setLayoutX(150);
        createArticleButton.setLayoutY(150);
        backupArticlesButton.setLayoutX(150);
        backupArticlesButton.setLayoutY(200);
        restoreArticlesButton.setLayoutX(150);
        restoreArticlesButton.setLayoutY(250);
        listArticlesButton.setLayoutX(150);
        listArticlesButton.setLayoutY(300);

        theRoot.getChildren().addAll(title, backButton, modifyArticlesButton, createArticleButton, backupArticlesButton, restoreArticlesButton, listArticlesButton);

        handleBack(backButton, theRoot);
        modifyArticle(modifyArticlesButton, theRoot);
        createArticle(createArticleButton, theRoot);
        backupArticles(backupArticlesButton, theRoot);
        restoreArticles(restoreArticlesButton, theRoot);
        listArticles(listArticlesButton, theRoot);
    }

    private void handleBack(Button backButton, Pane theRoot) {
        backButton.setOnAction(event -> {
        	theRoot.getChildren().clear(); // clear old root
            
            Pane newRoot = new Pane();
            AdminHome adminHome = new AdminHome(newRoot); 
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
		    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
		    currentStage.setScene(newScene); //
        });
    }
    
    private void modifyArticle(Button modifyArticlesButton, Pane theRoot) {
    modifyArticlesButton.setOnAction(event -> {
    	theRoot.getChildren().clear(); // clear old root
        
        Pane newRoot = new Pane();
        ModifyArticlesGUI modArt = new ModifyArticlesGUI(newRoot); // Navigates to ModifyArticlesGUI
        Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
	    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
	    currentStage.setScene(newScene); //
    	});
    }
    
   
    private void createArticle(Button createArticleButton, Pane theRoot) {
    createArticleButton.setOnAction(event -> {
    	theRoot.getChildren().clear(); // clear old root
        
        Pane newRoot = new Pane();
        CreateArticle creArt = new CreateArticle(newRoot); // Navigates to ModifyArticlesGUI
        Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
	    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
	    currentStage.setScene(newScene); //
    
    	});
    }
    
    private void backupArticles(Button backupArticlesButton, Pane theRoot) {
    backupArticlesButton.setOnAction(event -> {
    	theRoot.getChildren().clear();
    	Pane newRoot = new Pane();
    	
    	try {
			BackupArticlesGUI backArt = new BackupArticlesGUI(newRoot);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
	    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
	    currentStage.setScene(newScene); // 
	    
    	});
    }
    
    private void restoreArticles(Button restoreArticlesButton, Pane theRoot) {
    restoreArticlesButton.setOnAction(event -> {
    	theRoot.getChildren().clear();
    	Pane newRoot = new Pane();
    	
    	try {
			RestoreArticlesGUI backArt = new RestoreArticlesGUI(newRoot);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
	    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
	    currentStage.setScene(newScene); // 
	    
    	});
    }
    
    private void listArticles(Button listArticlesButton, Pane theRoot) {
    listArticlesButton.setOnAction(event -> {
    	theRoot.getChildren().clear();
    	Pane newRoot = new Pane();
    	
            try {
            	ListArticlesGUI listArt = new ListArticlesGUI(newRoot); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Navigates to ListArticlesGUI
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
		    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
		    currentStage.setScene(newScene); //
		    
    	});
    }
}
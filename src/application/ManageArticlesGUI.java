package application;

import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * <p> ManageArticlesGUI. </p>
 * 
 * <p> Description: A JavaFX class responsible for displaying options to manage articles.</p>
 * 
 * @author Sriram Nesan
 * 
 * @version 1.01        11/19/2024 Updated layout with missing buttons based on design image
 *  
 */

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
        title.setLayoutY(20);

        // Create buttons for article management actions
        Button backButton = new Button("Back");
        Button articleSearchButton = new Button("Article Search");
        Button modifyArticlesButton = new Button("Modify Articles");
        Button createArticleButton = new Button("Create Article");
        Button backupArticlesButton = new Button("Backup Articles");
        Button restoreArticlesButton = new Button("Restore Articles");
        Button modifyGroupAccessButton = new Button("Modify Group Access");
        Button createGroupButton = new Button("Create Group");

        // Set up the buttons using SetupUIElements
        setupUI.SetupButtonUI(backButton, "Arial", 12, 80, Pos.TOP_LEFT, 10, 10, false, Color.BLACK);
        setupUI.SetupButtonUI(articleSearchButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(modifyArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(createArticleButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(backupArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(restoreArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(modifyGroupAccessButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(createGroupButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);

        // Position buttons in a vertical layout
        VBox vbox = new VBox(10, articleSearchButton, modifyArticlesButton, createArticleButton, 
                             backupArticlesButton, restoreArticlesButton, modifyGroupAccessButton, createGroupButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX((WINDOW_WIDTH - 200) / 2); // Center align based on button width
        vbox.setLayoutY(60);

        // Add elements to the root
        theRoot.getChildren().addAll(title, backButton, vbox);

        // Handle button actions
        handleBack(backButton, theRoot);
        articleSearch(articleSearchButton, theRoot);
        modifyArticle(modifyArticlesButton, theRoot);
        createArticle(createArticleButton, theRoot);
        backupArticles(backupArticlesButton, theRoot);
        restoreArticles(restoreArticlesButton, theRoot);
        modifyGroupAccess(modifyGroupAccessButton, theRoot);
        createGroup(createGroupButton, theRoot);
    }

    private void handleBack(Button backButton, Pane theRoot) {
        backButton.setOnAction(event -> {
            theRoot.getChildren().clear(); // Clear the root
            
            Pane newRoot = new Pane();
            AdminHome adminHome = new AdminHome(newRoot); 
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }

    private void articleSearch(Button articleSearchButton, Pane theRoot) {
        articleSearchButton.setOnAction(event -> {
            theRoot.getChildren().clear();

            Pane newRoot = new Pane();
 //           ArticleSearchGUI articleSearch = new ArticleSearchGUI(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }

    private void modifyArticle(Button modifyArticlesButton, Pane theRoot) {
        modifyArticlesButton.setOnAction(event -> {
            theRoot.getChildren().clear();

            Pane newRoot = new Pane();
            ModifyArticlesGUI modArt = new ModifyArticlesGUI(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }

    private void createArticle(Button createArticleButton, Pane theRoot) {
        createArticleButton.setOnAction(event -> {
            theRoot.getChildren().clear();

            Pane newRoot = new Pane();
            CreateArticle creArt = new CreateArticle(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }

    private void backupArticles(Button backupArticlesButton, Pane theRoot) {
        backupArticlesButton.setOnAction(event -> {
            theRoot.getChildren().clear();
            Pane newRoot = new Pane();

            try {
                BackupArticlesGUI backArt = new BackupArticlesGUI(newRoot);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }

    private void restoreArticles(Button restoreArticlesButton, Pane theRoot) {
        restoreArticlesButton.setOnAction(event -> {
            theRoot.getChildren().clear();
            Pane newRoot = new Pane();

            try {
                RestoreArticlesGUI restoreArt = new RestoreArticlesGUI(newRoot);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }

    private void modifyGroupAccess(Button modifyGroupAccessButton, Pane theRoot) {
        modifyGroupAccessButton.setOnAction(event -> {
            theRoot.getChildren().clear();

            Pane newRoot = new Pane();
   //         ModifyGroupAccessGUI modGroupAccess = new ModifyGroupAccessGUI(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }

    private void createGroup(Button createGroupButton, Pane theRoot) {
        createGroupButton.setOnAction(event -> {
            theRoot.getChildren().clear();

            Pane newRoot = new Pane();
 //           CreateGroupGUI createGroup = new CreateGroupGUI(newRoot);
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
            Stage currentStage = (Stage) theRoot.getScene().getWindow();
            currentStage.setScene(newScene);
        });
    }
}

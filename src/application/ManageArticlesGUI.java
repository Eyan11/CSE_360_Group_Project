package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ManageArticlesGUI {

    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;
    public SetupUIElements setupUI;

    public ManageArticlesGUI(Pane root) {
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

        setupUI.SetupButtonUI(backButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(modifyArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(createArticleButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(backupArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(restoreArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(listArticlesButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);

        backButton.setLayoutX(20);
        backButton.setLayoutY(20);
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

        root.getChildren().addAll(title, backButton, modifyArticlesButton, createArticleButton, backupArticlesButton, restoreArticlesButton, listArticlesButton);

   //     handleBack(backButton, root);
    }

 /*   private void handleBack(Button backButton, Pane root) {
        backButton.setOnAction(event -> {
            root.getChildren().clear();
            AdminHome adminHome = new AdminHome(root); */
     //   });
    }


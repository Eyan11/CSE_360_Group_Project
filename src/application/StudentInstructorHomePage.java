package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StudentInstructorHomePage extends Application {

    public SetupUIElements setupUI;

    @Override
    public void start(Stage primaryStage) {
        // Instantiate SetupUIElements
        setupUI = new SetupUIElements();

        // Create "Home"
        Text homeText = new Text("Home");
        homeText.setFont(new Font("Arial", 32));  
        homeText.setFill(Color.BLACK);  

        // Create the Logout Button and center
        Button logoutButton = new Button("Logout");
        setupUI.SetupButtonUI(logoutButton, "Arial", 14, 200, Pos.CENTER, 0, 50, false, Color.BLACK);

        // VBox Layout for the "Home"
        VBox topVBox = new VBox();
        topVBox.setAlignment(Pos.TOP_CENTER); // Align at the top, centered horizontally
        topVBox.setSpacing(20);
        topVBox.getChildren().add(homeText);

        // StackPane Layout "Logout" button
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER); // center
        stackPane.getChildren().add(logoutButton);

        // combine the top VBox and the StackPane
        StackPane mainLayout = new StackPane();
        mainLayout.getChildren().addAll(topVBox, stackPane);

        // Scene
        Scene scene = new Scene(mainLayout, 400, 300);

        // Primary Stage
        primaryStage.setTitle("Student/Instructor Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

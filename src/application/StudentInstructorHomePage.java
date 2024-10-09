package application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StudentInstructorHomePage{

    public void start(Stage primaryStage) {
        // Title Text
        Text title = new Text("Home");

        // Logout Button
        Button logoutButton = new Button("Logout");

        // VBox Layout
        VBox vbox = new VBox(20); 
        vbox.getChildren().addAll(title, logoutButton);

        // Scene
        Scene scene = new Scene(vbox, 300, 200); 

        // Primary Stage
        primaryStage.setTitle("Student/Instructor Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



























package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StudentInstructorHomePage {
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;

    public SetupUIElements setupUI;

    // Constructor for setting up the Student/Instructor Home GUI
    public StudentInstructorHomePage(StackPane root) {
    	


        // Instantiate SetupUIElements
        setupUI = new SetupUIElements();

        // Create "Home" text
        Text homeText = new Text("Home");
        homeText.setFont(new Font("Arial", 32));  
        homeText.setFill(Color.BLACK);  

        // Create the Logout Button
        Button logoutButton = new Button("Logout");
        setupUI.SetupButtonUI(logoutButton, "Arial", 14, 200, Pos.CENTER, 0, 50, false, Color.BLACK);

        // VBox Layout for the "Home" text
        VBox topVBox = new VBox();
        topVBox.setAlignment(Pos.TOP_CENTER); // Align at the top, centered horizontally
        topVBox.setSpacing(20);
        topVBox.getChildren().add(homeText);

        // StackPane Layout for the "Logout" button
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER); // Center the button
        stackPane.getChildren().add(logoutButton);

        // Combine the top VBox and the StackPane
        root.getChildren().addAll(topVBox, stackPane);
    }
}

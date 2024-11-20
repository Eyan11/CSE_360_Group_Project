package application;

import database.AccountDatabase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//ALL NOTES CRITICAL FOR OTHER DEVELOPERS READING THE CODE AND CONNECTING IT TO THEIR OWN ARE PREFACED BY "DEVELOPER NOTE: "
//However, it is still highly recommended that you read ALL comments throughout the program before doing anything 
//(especially before changing anything!)

//DEVELOPER NOTE: Additional imports may be necessary for this program to work with the rest of Phase 1

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
* @version 1.00		10/28/2024 Phase 1 implementation and documentation
*  
*/

public class InviteUserGUI {
    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;

    private Label sceneLabel = new Label("Invite User");
    private Label errorLabel = new Label("");
    private CheckBox studentCheckBox = new CheckBox("Student");
    private CheckBox instructorCheckBox = new CheckBox("Instructor");
    private CheckBox adminCheckBox = new CheckBox("Admin");
    private Button homeButton = new Button("<-");
    private Button inviteButton = new Button("Invite");
    
    // Declaration of SetupUIElements Object
 	public SetupUIElements setupUI;

    public InviteUserGUI(Pane invitePane) {
    	// Utilizes the SetUpElements class for the Label and Button
    	setupUI = new SetupUIElements();
    	
        setupLabelUI(sceneLabel, "Arial", 36, WINDOW_WIDTH, Pos.CENTER, 0, 10, Color.BLACK);
        
        // Button that returns user to previous interface
 		setupUI.SetupButtonUI(homeButton, "Arial", 11, 50, 20,
         		Pos.CENTER, 10, 10, false, Color.BLACK);
        
        // creating the student instructor and admin check boxes, and gr
        setupCheckBoxUI(studentCheckBox, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 100, Color.PURPLE);
        setupCheckBoxUI(instructorCheckBox, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 130, Color.PURPLE);
        setupCheckBoxUI(adminCheckBox, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 160, Color.PURPLE);
        
        // Invite Button
        setupButtonUI(inviteButton, "Arial", 14, 120, Pos.CENTER, WINDOW_WIDTH / 2 - 60, 200, Color.BLACK);
        
        // Error Label or Success Message
        setupLabelUI(errorLabel, "Arial", 14, WINDOW_WIDTH - 20, Pos.CENTER, 10, 250, Color.RED);
        
        invitePane.getChildren().addAll(sceneLabel, homeButton, studentCheckBox, instructorCheckBox, adminCheckBox, inviteButton, errorLabel);

        // Button logic
        inviteButton.setOnAction(event -> handleInvite());
        
		homeButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{						
				invitePane.getChildren().clear();  // clear the current root
				
				// Send user to previous interface
				Pane newRoot = new Pane(); // create new root
				ManageAccountsGUI manageAccount = new ManageAccountsGUI(newRoot); // call previous interface
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
			    Stage currentStage = (Stage) invitePane.getScene().getWindow();
			    currentStage.setScene(newScene); // sets scene
			}
		});
    }

    private void handleInvite() { //error message if no boxes are selected
        if (!studentCheckBox.isSelected() && !instructorCheckBox.isSelected() && !adminCheckBox.isSelected()) {
            errorLabel.setText("Please select at least one role.");
        } else {
            // Assuming generateOneTimeKey() is implemented elsewhere to generate a unique key
            String oneTimeKey = generateOneTimeKey();
            errorLabel.setText("One-time key: " + oneTimeKey);
            errorLabel.setTextFill(Color.GREEN); // successfully selected more than one box
        }
    }

    private String generateOneTimeKey() {
        boolean isStudent = studentCheckBox.isSelected();
        boolean isInstructor = instructorCheckBox.isSelected();
        boolean isAdmin = adminCheckBox.isSelected();

        return AccountDatabase.inviteUser(isStudent, isInstructor, isAdmin);
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

    private void setupCheckBoxUI(CheckBox checkBox, String font, double fontSize, double width, Pos pos, double x, double y, Color color) {
        checkBox.setFont(Font.font(font, fontSize));
        checkBox.setMinWidth(width);
        checkBox.setLayoutX(x);
        checkBox.setLayoutY(y);
        checkBox.setTextFill(color);
    }

}


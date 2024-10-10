package application;

import java.lang.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextFlow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
//import passwordEvaluationTestbed.PasswordEvaluationGUITestbed;

// ALL NOTES CRITICAL FOR OTHER DEVELOPERS READING THE CODE AND CONNECTING IT TO THEIR OWN ARE PREFACED BY "DEVELOPER NOTE: "
// However, it is still highly recommended that you read ALL comments throughout the program before doing anything 
// (especially before changing anything!)

// DEVELOPER NOTE: Additional imports may be necessary for this program to work with the rest of phase 1

//public class UpdateAccountInformationGUI extends Application { // Use this class header if you want to test with start()
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
 * @version 1.00		10/9/2024 Phase 1 implementation and documentation
 *  
 */
public class CreateAccountInformationGUI {
	
	
	/**
	 * Variable declaration
	 */
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;
	
	/** String inputs */
	private String usernameInput;
	private String passwordInput;
	private String confirmInput;
	
	
	/** Text to appear as a part of the window (text field indicators, etc. */
	private Label sceneLabel = new Label("Enter User Info Window");
	private Label usernameLabel = new Label("Enter username here");
	private Label passwordLabel = new Label("Enter password here");
	private Label confirmLabel = new Label("Re-type password to confirm");
	private Label errorLabel = new Label("Please fill in the required entries (see red)");

	/** Text fields for user input */
	private TextField usernameText = new TextField();
	private TextField passwordText = new TextField();
	private TextField confirmText = new TextField();
	
	/** Constructors
	 */
	
	CreateAccountInformationGUI(Pane userPane) {
		                
       // NOTE: Not needed, see updateStage.setTitle() ^
        // Label the Scene with the name of the testbed, centered at the top of the pane
		setupLabelUI(sceneLabel, "Arial", 24, WINDOW_WIDTH, 
				Pos.CENTER, 0, 10, Color.GREEN);
		// Label the username input field with a title just above it, left aligned
		setupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 100, Color.GREEN);
		// Label the password input field with a title just above it, left aligned
		setupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 200, Color.GREEN);
		// Label the confirm input field with a title just above it, left aligned
		setupLabelUI(confirmLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 300, Color.GREEN);
		
		// Establish the text input operand field and when anything changes in the user inputs,
		// the code will process the entire input to ensure that it is valid or an error.
		setupTextUI(usernameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 125, true);
		setupTextUI(passwordText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 225, true);
		setupTextUI(confirmText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 325, true);
		
		// Establish the button which will be used to check and send new user info
		// to the respective methods required to update the user info currently in the database
        Button createButton = new Button("Create Account");
        setupButtonUI(createButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, Color.GREEN);
        
        // Sends all previously established settings for the pane to the scene for setup
        userPane.getChildren().addAll(usernameLabel, usernameText, passwordLabel, passwordText, confirmLabel, confirmText, sceneLabel, createButton);
        
        // Establishes the button logic for each press
        // DEVELOPER NOTE: Button logic does not refresh or continue after VALID input. If this ever becomes an issue, let Evan know and 
        //                 he will add functionality for repeated valid input.
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	
            	
	            	// Retrieve TextField input
            	String usernameString = usernameText.getText();
            	String passwordString = passwordText.getText();
            	String confirmString = confirmText.getText();
	
	                // Do error check, if no errors, update info. If errors, output error message above update button and below info input.
	            	// Repeat process for each button push
	            	//boolean pass = ErrorMessage(emailString, firstString, middleString, lastString);
	            	boolean pass = ErrorMessage(usernameString, passwordString, confirmString);
	            	// If there are any unfilled entries, alter text box and output message indicating that entries are incomplete
	            	// and highlight all necessary entry boxes
	            	if(pass == false) {
	            		userPane.getChildren().add(errorLabel);

	            		setupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-20, 
                		Pos.CENTER, 10, 430, Color.RED);
	            		
	            		setupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-20, 
	                    		Pos.CENTER, 10, 430, Color.RED);
	            		
	            		setupLabelUI(confirmLabel, "Arial", 14, WINDOW_WIDTH-20, 
	                    		Pos.CENTER, 10, 430, Color.RED);
	            		setupButtonUI(createButton, "Arial", 14, WINDOW_WIDTH-20, 
	                    		Pos.CENTER, 10, 430, Color.RED);
	            	}
	            	
	            	// If all necessary entries are filled, reset scene formatting and send info to next step!
	            	// DEVELOPER NOTE: Please let Cadon know what steps need to be incorporated so I can add whatever is necessary to pass 
	            	// 				   onto then next part. Thank you.
	            	else {
	            		
	            		//Eliminate error indicator
	            		setupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-20, 
	                    		Pos.CENTER, 10, 430, Color.RED);
	    	            		
	    	            setupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-20, 
	    	                    Pos.CENTER, 10, 430, Color.RED);
	    	            		
	    	            setupLabelUI(confirmLabel, "Arial", 14, WINDOW_WIDTH-20, 
	    	                    Pos.CENTER, 10, 430, Color.RED);
	    	            setupButtonUI(createButton, "Arial", 14, WINDOW_WIDTH-20, 
	    	                    Pos.CENTER, 10, 430, Color.RED);
	            		userPane.getChildren().remove(errorLabel);
	            		
	            		// DEVELOPER NOTE: Critical step
	            		// Pass info onto the next part!
	            		
	            		//DEVELOPER NOTE: Critical step
	            		// If preferred name box is filled in (i.e. not empty)
	            		if(passwordString.equals(confirmString)) {
	            			userPane.getChildren().clear();  // Clear the current root
	            			LoginGUI login = new LoginGUI(userPane);
	            		} else {
	            			errorLabel.setText("passwords dont match");
	            			setupLabelUI(errorLabel, "Arial", 14,WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10, 300, Color.RED);
	            			userPane.getChildren().add(errorLabel);
	            			// Replace first name in user display menu with preferred name
	            			// someName.somePlace() == preferredString; // Something like this (I think)
	            		}
	            	}
            }
        });
	}
	
	

	private void setupLabelUI(Label l, String font, double fontSize, double minWidth, Pos pos, double x, double y, Color color){
		l.setFont(Font.font(font, fontSize));
		l.setMinWidth(minWidth);
		l.setAlignment(pos);
		l.setLayoutX(x);
		l.setLayoutY(y);
		l.setTextFill(color);
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}	
	
	/**********
	 * Private local method to initialize the standard fields for a button
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y, Color color){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setMaxWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
		b.setTextFill(color);
	}	
	
	
	// DEVELOPER NOTE: main left for any future interest in testing UpdateAccountInfoGUI using the start() method (see below ErrorMessage)
	/*public static void main(String[] args) {
		launch(args);
	}*/
	
	/**********
	 * Private local method to check for valid text field input for all text fields
	 */
	// Checks all necessary entries for not being empty. If any are empty, returns false to button function for error display.
	// Otherwise, if all necessary entries are filled, returns true and sends to button function for pushing info to the next step!
	// (Also resets scene if previous entry was an error)
	private boolean ErrorMessage(String username, String password, String confirm) {
		
		boolean filled; // Checks of all necessary entries are filled. Starts as false (by default). If any parameters are not filled, stays false.
						// Otherwise, returns as true!
		/*if( (email == "") || (first == "") || (middle == "") || (last == "") ) {
			// Show error above button saying "All necessary text boxes must be filled! (see red)
			// Change relevant titles to red (gonna have to break if statements up)	 
		
		}*/
		// If any entry is empty, filled = false
		if(username == "") {
			filled = false;
		}
		if(password == "") {
			filled = false;
		}
		if(confirm == "") {
			filled = false;
		}

		// Else, filled = true
		else {
			filled = true;
		}
		
		// Return filled!
		return filled;
	}
	
	public void userInfo() {
		
	}
	
	public void userInfo(String username, String password, String confirm){
		
		// Constructor variable names are different from constructor input names to prevent variable shadowing
		
	}
	
}

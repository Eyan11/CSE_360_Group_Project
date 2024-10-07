package application;

import java.lang.*;

//import javafx.scene.control.Label; // For Label obj

public class LoginGUI
{
	/**
	// Variables

	// The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	// will store login input for evaluation
	String userInput = "";
	String passwordInput = "";
	String keyInput = "";
	
	// non-interactive text that will appear on login interface
	Label returningUserLabel = new Label("Returning User?");
	Label usernameLabel = new Label("Username:");
	Label passwordLabel = new Label("Password:");
	Label newUserLabel = new Label("New User?");
	Label keyLabel = new Label("One-Time Key:");
	
	// text field for user input
	TextField usernameText = new TextField();
	TextField passwordText = new TextField();
	TextField keyText = new TextField();
	
	// interactive button for user to login or verify key
	Button loginButton = new Button("Login");
	Button verifyKeyButton = new Button("Verify Key");
	
	 // Constructor w/ Parameter
	 //@param userPane
	
	LoginGUI(Pane theRoot)
	{
		Stage updateStage = new Stage();
		updateStage.setTitle("Login Page");
		
		//utilizes the SetUpElements class for Labels, TextFields, and Buttons
		//public SetupUIElements setupUI = new SetupUIElements;
		
		//Label Declarations
		 
		
		// Label asking if user is a returning user, left aligned
		setupLabelUI(returningUserLabel, "Arial", 28, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 25, Color.BLACK);
				
		// Label asking for user name input, left aligned
		setupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 100, Color.BLACK);
				
		// Label asking for password input, left aligned
		setupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 175, Color.BLACK);
				
		// Label asking if user has a one-time key, left aligned
		setupLabelUI(newUserLabel, "Arial", 28, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 250, Color.BLACK);
				
		// Label asking for one-time key input, left aligned
		setupLabelUI(keyLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 325, Color.BLACK);
		
		//TextField Declarations
		
		// TextField that will take user name from user
		setupTextUI(usernameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 125, true);
		// TextField that will take password from user
		setupTextUI(passwordText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 200, true);
		// TextField that will take key from user
		setupTextUI(keyText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 350, true);
		
		//Button Declarations
		//FOR ME: figure out dimensions for login button
		
		// Button that should be pressed when key is inputed in keyText TextField
		setupButtonUI(verifyKeyButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, Color.BLACK);
		
		// Button that should be pressed when password is inputed passwordText TextField
		//setupButtonUI(loginButton, "Arial", 14, WINDOW_WIDTH-20, 
        		//Pos.CENTER, 10, 400, Color.BLACK);
		
		// Sends all previously established settings for the pane to the scene for setup
		//theRoot.getChildren().addAll(returningUserLabel, usernameLabel, passwordLabel,
				//newUserLabel, keyLabel, usernameText, passwordText, keyText, loginButton,
				//verifyKeyButton);
		
		theRoot.getChildren().addAll(returningUserLabel, usernameLabel, passwordLabel,
				newUserLabel, keyLabel, usernameText, passwordText, keyText, loginButton,
				verifyKeyButton); 

		Scene userScene = new Scene(theRoot, 800, 500);
		userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		updateStage.setScene(userScene);
		updateStage.show();
	}
	*/
}
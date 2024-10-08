package application;

import javafx.scene.control.Label; // For Label obj
import javafx.scene.control.TextField; // For TextField obj
import javafx.scene.control.Button; // For Button obj
//import javafx.scene.control.CheckBox; // For CheckBox obj
//import javafx.scene.text.Font; // To set font and font size
import javafx.geometry.Pos; // For Pos obj (vector2 coordinate used to describe position)
import javafx.scene.paint.Color; // To set color of UI elements

//import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LoginGUI
{
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
	
	public SetupUIElements setupUI;
	
	 // Constructor w/ Parameter
	 //@param userPane
	
	public LoginGUI(Pane theRoot)
	{
		Stage updateStage = new Stage();
		updateStage.setTitle("Login Page");
		
		//utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		//Label Declarations
		
		// Label asking if user is a returning user, left aligned
		setupUI.SetupLabelUI(returningUserLabel, "Arial", 28, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 25, Color.BLACK);
				
		// Label asking for user name input, left aligned
		setupUI.SetupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 100, Color.BLACK);
				
		// Label asking for password input, left aligned
		setupUI.SetupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 175, Color.BLACK);
				
		// Label asking if user has a one-time key, left aligned
		setupUI.SetupLabelUI(newUserLabel, "Arial", 28, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 250, Color.BLACK);
				
		// Label asking for one-time key input, left aligned
		setupUI.SetupLabelUI(keyLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 325, Color.BLACK);
		
		//TextField Declarations
		
		// TextField that will take user name from user
		setupUI.SetupTextFieldUI(usernameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 125, true);
		// TextField that will take password from user
		setupUI.SetupTextFieldUI(passwordText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 200, true);
		// TextField that will take key from user
		setupUI.SetupTextFieldUI(keyText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 350, true);
		
		//Button Declarations
		//FOR ME: figure out dimensions for login button
		
		// Button that should be pressed when key is inputed in keyText TextField
		setupUI.SetupButtonUI(verifyKeyButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, false, Color.BLACK);
		
		// Button that should be pressed when password is inputed passwordText TextField
		//setupButtonUI(loginButton, "Arial", 14, WINDOW_WIDTH-20, 
        		//Pos.CENTER, 10, 400, false, Color.BLACK);
		
		// Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(returningUserLabel, usernameLabel, passwordLabel,
				newUserLabel, keyLabel, usernameText, passwordText, keyText, loginButton,
				verifyKeyButton); 

		Scene userScene = new Scene(theRoot, 800, 500);
		userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		updateStage.setScene(userScene);
		updateStage.show();
		
		//button logic for the ont-time key verification
		verifyKeyButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//grabs input from one-time key text field
				keyInput = keyText.getText();
				
				// utilize one time key checker in LoginEvaluator class
				// check if one time key is in databases
				if(LoginEvaluator.OneTimeKeyChecker(keyInput))
				{
					//keep key label black or change to black if user inputed incorrect info earlier
					setupUI.SetupLabelUI(keyLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 325, Color.BLACK);
					
					//WAITING on AccountDatabase Class
					//IF new user, send user to CreateAccountGUI
					//IF existing user, send user to ResetPasswordGUI
					//ResetPasswordGUI will not be implemented in Phase 1
				}
				else // ERROR MESSAGE
				{
					setupUI.SetupLabelUI(keyLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 325, Color.RED);
				}
			}
		});
		
		//button logic for the password verification
		loginButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//grabs input from username and password text field
				userInput = usernameText.getText();
				passwordInput = passwordText.getText();
				
				//utilize the password checker method in LoginEvaluator class
				//check if userInput and passwordInput is in database
				if(LoginEvaluator.PasswordChecker(userInput, passwordInput))
				{
					//keep the username and password label black or change to black if user inputed info earlier
					setupUI.SetupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 100, Color.BLACK);
					setupUI.SetupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 175, Color.BLACK);
					
					//WAITING on AccountDatabase Class
					//IF first time login, send user to UpdateAccountInformationGUI
					//IF admin logins AND has updated account info, send user to AdminHomeGUI
					//IF user has two roles (admin or Student/Instructor), send user to SelectRoleGUI
					//IF user is Student/Instructor with updated account info, send user to StudentInstructotHomeGUI
				}
				else // ERROR MESSAGE
				{
					// change color of username and password label to red, indicating an error message
					setupUI.SetupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 100, Color.RED);
					setupUI.SetupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 175, Color.RED);
				}
			}
		});
	}
}
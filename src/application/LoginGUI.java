package application;

import javafx.scene.control.Label; // For Label object
import javafx.scene.control.TextField; // For TextField object
import javafx.scene.control.Button; // For Button object
import javafx.geometry.Pos; // For Position object (vector2 coordinate used to describe position)
import javafx.scene.paint.Color; // To set color of UI elements

//import javafx.stage.Stage;
//import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LoginGUI
{
	/*
	 * Variable Declaration
	 */

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
	
	 /**
	  * Constructor w/ Parameter for GUI
	  * @param userPane
	  */
	
	public LoginGUI(Pane theRoot)
	{
		
		/*
		Stage updateStage = new Stage();
		updateStage.setTitle("Login Page");
		*/
		
		//utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		/*
		 * Label Declarations
		 */
		
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
		
		/*
		 * TextField Declarations 
		 */
		
		// TextField that will take user name from user
		setupUI.SetupTextFieldUI(usernameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 125, true);
		// TextField that will take password from user
		setupUI.SetupTextFieldUI(passwordText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 200, true);
		// TextField that will take key from user
		setupUI.SetupTextFieldUI(keyText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 350, true);
		
		/*
		 * Button Declarations
		 * FOR ME: figure out dimensions for login button
		 */
		
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

		/*
		Scene userScene = new Scene(theRoot, 800, 500);
		userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		updateStage.setScene(userScene);
		updateStage.show();
		*/
		
		/*
		 * Button Functionality
		 */
		
		// button logic for password verification button
		loginButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//grabs input from user name and password text field
				userInput = usernameText.getText();
				passwordInput = passwordText.getText();
						
				//utilize the password checker method in LoginEvaluator class
				//check if userInput and passwordInput is in database
				if(LoginEvaluator.PasswordChecker(userInput, passwordInput))
				{
					//keep the user name and password label black or change to black if user inputed info earlier
					setupUI.SetupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 100, Color.BLACK);
					setupUI.SetupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 175, Color.BLACK);
							
					//IF first time login, send user to UpdateAccountInformationGUI
					if(LoginEvaluator.firstTimeLogin(userInput))
					{
						//UpdateAccountInformationGUI createAccount = new UpdateAccountInformationGUI(theRoot);
					}
					//IF administrator logins AND has updated account info, send user to AdminHomeGUI
					else if(LoginEvaluator.adminLogin(userInput))
					{
						//AdminHome adminHome = new AdminHome();
					}
					//IF user has two roles (administrator and Student/Instructor), send user to SelectRoleGUI
					else if(LoginEvaluator.multipleRoles(userInput))
					{
						//SelectRole selectRole = new SelectRole(theRoot, userInput);
					}
					//IF user is Student/Instructor with updated account info, send user to StudentInstructorHomeGUI
					else if(LoginEvaluator.studentInstructorRole(userInput))
					{
						//StudentInstructorHomePage shHome = new StudentInstructorHomePage(theRoot, userInput);
					}
					else
					{
						System.err.println("PASSWORD CHECKER ERROR");
					}
				}
				else // ERROR MESSAGE
				{
					// change color of user name and password label to red, indicating an error message
					setupUI.SetupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 100, Color.RED);
					setupUI.SetupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 175, Color.RED);
				}
			}
		});
		
		//button logic for the one-time key verification button
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
					
					//IF new user, send user to CreateAccountGUI
					if(!LoginEvaluator.accountCreation(keyInput))
					{
						//CreateAccountInformationGUI createAccount = new CreateAccountInformationGUI(theRoot);
					}
					/*
					else if(LoginEvaluator.resetPassword(keyInput))
					{
						
					}
					//IF existing user, send user to ResetPasswordGUI
					//ResetPasswordGUI will not be implemented in Phase 1
					*/
					else
					{
						System.err.println("KEY CHECKER ERROR");
					}
				}
				else // ERROR MESSAGE
				{
					setupUI.SetupLabelUI(keyLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 325, Color.RED);
				}
			}
		});
	}
}

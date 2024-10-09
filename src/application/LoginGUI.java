package application;

import javafx.scene.control.Label; // For Label object
import javafx.scene.control.TextField; // For TextField object
import javafx.scene.control.Button; // For Button object
import javafx.geometry.Pos; // For Position object (vector2 coordinate used to describe position)
import javafx.scene.paint.Color; // To set color of UI elements

import javafx.scene.layout.Pane; // For Pane object
import javafx.event.ActionEvent; // For ActionEvent object
import javafx.event.EventHandler; // For EventHandler object

/*******
 * <p> LoginGUI Class </p>
 * 
 * <p> Description: Login User Interface which accepts user name, password, and one-time key </p>
 * 
 * @author Julio Salazar
 * 
 * @version 1.00 10/9/2024 Phase 1 Implementation and Documentation
 * 
 */

public class LoginGUI
{
	/*
	 * Variable Declarations
	 */

	//The width/height of the pop-up window for the user interface
	public final static double WINDOW_WIDTH = 500;
	public final static double WINDOW_HEIGHT = 430;
	
	//will store login input for evaluation
	private String userInput = "";
	private String passwordInput = "";
	private String keyInput = "";
	
	//non-interactive text that will appear on login interface
	private Label returningUserLabel = new Label("Returning User?");
	private Label usernameLabel = new Label("Username:");
	private Label passwordLabel = new Label("Password:");
	private Label newUserLabel = new Label("New User?");
	private Label keyLabel = new Label("One-Time Key:");
	
	//private Label loginErrorMessage = new Label("Oops! Incorrect Password or Username, Please Try Again");
	//private Label keyErrorMessage = new Label("Oops! Incorrect One-Time Key, Please Try Again");
	
	// text field for user input
	private TextField usernameText = new TextField();
	private TextField passwordText = new TextField();
	private TextField keyText = new TextField();
	
	//interactive button for user to login or verify key
	private Button loginButton = new Button("Login");
	private Button verifyKeyButton = new Button("Verify Key");
	
	//declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	/**
	 * Constructor w/ Parameter for GUI
	 * @param userPane
	 */
	
	public LoginGUI(Pane theRoot)
	{	
		//utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		/*
		 * Label Creations
		 */
		
		//Label asking if user is a returning user, left aligned
		setupUI.SetupLabelUI(returningUserLabel, "Arial", 28, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 10, Color.BLACK);
				
		//Label asking for user name input, left aligned
		setupUI.SetupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
				
		//Label asking for password input, left aligned
		setupUI.SetupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 115, Color.BLACK);
				
		//Label asking if user has a one-time key, left aligned
		setupUI.SetupLabelUI(newUserLabel, "Arial", 28, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 250, Color.BLACK);
				
		//Label asking for one-time key input, left aligned
		setupUI.SetupLabelUI(keyLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 290, Color.BLACK);
		
		/*
		setupUI.SetupLabelUI(loginErrorMessage, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 175, Color.RED);
		setupUI.SetupLabelUI(keyErrorMessage, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 355, Color.RED);
		*/
		
		/*
		 * TextField Creations 
		 */
		
		//TextField that will take user name from user
		setupUI.SetupTextFieldUI(usernameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 75, true);
		//TextField that will take password from user
		setupUI.SetupTextFieldUI(passwordText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 135, true);
		//TextField that will take key from user
		setupUI.SetupTextFieldUI(keyText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 315, true);
		
		/*
		 * Button Creations
		 * FOR JULIO: 
		 * figure out dimensions for login button
		 */
		
		//Button that should be pressed when password is inputed passwordText TextField
		setupUI.SetupButtonUI(loginButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 185, false, Color.BLACK);
		
		//Button that should be pressed when key is inputed in keyText TextField
		setupUI.SetupButtonUI(verifyKeyButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 365, false, Color.BLACK);
		
		//Sends all previously established settings for the pane to the scene for setup
		theRoot.getChildren().addAll(returningUserLabel, usernameLabel, passwordLabel,
				newUserLabel, keyLabel, usernameText, passwordText, keyText, loginButton,
				verifyKeyButton);
		
		/*
		 * Button Functionality
		 */
		
		//button logic for password verification button
		loginButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{
				//grabs input from user name and password text field
				userInput = usernameText.getText();
				passwordInput = passwordText.getText();
						
				//utilize the password checker method in LoginEvaluator class
				if(LoginEvaluator.PasswordChecker(userInput, passwordInput)) // check if userInput and passwordInput is in database
				{
					//keep the user name and password label black or change to black if user inputed information earlier
					setupUI.SetupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
					setupUI.SetupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 115, Color.BLACK);
							
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
				else // ERROR MESSAGE / Error Indication
				{
					//change color of user name and password label to red, indicating an error
					setupUI.SetupLabelUI(usernameLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 50, Color.RED);
					setupUI.SetupLabelUI(passwordLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 115, Color.RED);
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
				
				//utilize one time key checker in LoginEvaluator class
				if(LoginEvaluator.OneTimeKeyChecker(keyInput)) // check if one time key is in databases
				{
					//keep key label black or change to black if user inputed incorrect info earlier
					setupUI.SetupLabelUI(keyLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 290, Color.BLACK);
					
					//IF new user, send user to CreateAccountGUI
					if(!LoginEvaluator.accountCreation(keyInput))
					{
						//CreateAccountInformationGUI createAccount = new CreateAccountInformationGUI(theRoot);
					}
					/*
					 * IF existing user, send user to ResetPasswordGUI
					 * ResetPasswordGUI will not be implemented in Phase 1
					else if(LoginEvaluator.resetPassword(keyInput))
					{
						
					}
					*/
					else // key doesn't fit either scenario
					{
						System.err.println("KEY CHECKER ERROR");
					}
				}
				else // ERROR MESSAGE / Error Indication
				{
					//change color of key label to red, indicating an error
					setupUI.SetupLabelUI(keyLabel, "Arial", 14, WINDOW_WIDTH-10, 
							Pos.BASELINE_LEFT, 10, 290, Color.RED);
				}
			}
		});
	}
}

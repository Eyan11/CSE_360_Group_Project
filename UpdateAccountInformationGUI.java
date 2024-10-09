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
public class UpdateAccountInformationGUI {
	
	
	/**
	 * Variable declaration
	 */
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;
	
	/** String inputs */
	private String user; // Passed in from previous step
	private String emailInput;
	private String firstNameInput;
	private String middleNameInput;
	private String lastNameInput;
	private String prefNameInput;
	
	/** Text to appear as a part of the window (text field indicators, etc. */
	private Label sceneLabel = new Label("Update User Info Window");
	private Label emailLabel = new Label("Enter email here");
	private Label firstName = new Label("Enter first name here");
	private Label middleName = new Label("Enter middle name here");
	private Label lastName = new Label("Enter last name here");
	private Label prefName = new Label("Enter preferred name here");
	private Label errorLabel = new Label("Please fill in the required entries (see red)");

	/** Text fields for user input */
	private TextField emailText = new TextField();
	private TextField firstNameText = new TextField();
	private TextField middleNameText = new TextField();
	private TextField lastNameText = new TextField();
	private TextField prefNameText = new TextField();
	
	//private Button updateButton = new Button("Button test");	
	//updateButton.setText("Update");
	
	
	/** Constructors
	 */
	
	UpdateAccountInformationGUI(Pane userPane, String user) { // user passed in from previous step
		Stage updateStage = new Stage();
		updateStage.setTitle("User Info Update");
        
        
        //setupUI = new SetupUIElements();
        
        // NOTE: Not needed, see updateStage.setTitle() ^
        // Label the Scene with the name of the testbed, centered at the top of the pane
		//setupLabelUI(sceneLabel, "Arial", 24, WINDOW_WIDTH, 
				//Pos.CENTER, 0, 10, Color.GREEN);
		
		// Label the email input field with a title just above it, left aligned
		setupLabelUI(emailLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 25, Color.GREEN);
		
		// Label the first name input field with a title just above it, left aligned
		setupLabelUI(firstName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 100, Color.GREEN);
		
		// Label the middle name input field with a title just above it, left aligned
		setupLabelUI(middleName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 175, Color.GREEN);
		
		// Label the last name input field with a title just above it, left aligned
		setupLabelUI(lastName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 250, Color.GREEN);
		
		// Label the preferred name input field with a title just above it, left aligned
		setupLabelUI(prefName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 325, Color.GREEN);
		
		// Establish the text input operand field and when anything changes in the user inputs,
		// the code will process the entire input to ensure that it is valid or an error.
		setupTextUI(emailText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 50, true);
		
		setupTextUI(firstNameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 125, true);
		
		setupTextUI(middleNameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 200, true);
		
		setupTextUI(lastNameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 275, true);
		
		setupTextUI(prefNameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 350, true);
		
		// Establish the button which will be used to check and send new user info
		// to the respective methods required to update the user info currently in the database
        Button updateButton = new Button("Button test");
        setupButtonUI(updateButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, Color.GREEN);
        
        // Sends all previously established settings for the pane to the scene for setup
        userPane.getChildren().addAll(emailLabel, emailText, firstName, firstNameText, middleName, middleNameText, lastName, lastNameText,
        							  prefName, prefNameText, updateButton); 
        
        Scene userScene = new Scene(userPane, 800, 500);
        userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        updateStage.setScene(userScene);
        updateStage.show();
        
        // Establishes the button logic for each press
        // DEVELOPER NOTE: Button logic does not refresh or continue after VALID input. If this ever becomes an issue, let Evan know and 
        //                 he will add functionality for repeated valid input.
        updateButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	
            	
	            	// Retrieve TextField input
	            	String emailString = emailText.getText();
	            	String firstString = firstNameText.getText();
	            	String lastString = lastNameText.getText();
	            	String middleString = middleNameText.getText();
	            	String preferredString = prefNameText.getText();
	
	                // Do error check, if no errors, update info. If errors, output error message above update button and below info input.
	            	// Repeat process for each button push
	            	boolean pass = ErrorMessage(emailString, firstString, middleString, lastString);
	            	
	            	// If there are any unfilled entries, alter text box and output message indicating that entries are incomplete
	            	// and highlight all necessary entry boxes
	            	if(pass == false) {
	            		userPane.getChildren().add(errorLabel);
	            		setupButtonUI(updateButton, "Arial", 14, WINDOW_WIDTH-20, 
	                    		Pos.CENTER, 10, 430, Color.RED);
	            		setupLabelUI(errorLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 400, Color.RED);
	            		
	            		setupLabelUI(emailLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 25, Color.RED);
	            		
	            		setupLabelUI(firstName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 100, Color.RED);
	            		
	            		setupLabelUI(middleName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 175, Color.RED);
	            		
	            		setupLabelUI(lastName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 250, Color.RED);
	
	            	}
	            	
	            	// If all necessary entries are filled, reset scene formatting and send info to next step!
	            	// DEVELOPER NOTE: Please let Evan know what steps need to be incorporated so I can add whatever is necessary to pass 
	            	// 				   onto then next part. Thank you.
	            	else {
	            		
	            		//Eliminate error indicator
	            		setupButtonUI(updateButton, "Arial", 14, WINDOW_WIDTH-20, 
	            				Pos.CENTER, 10, 400, Color.GREEN);
	            		// Reset color values to green
	            		setupLabelUI(emailLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 25, Color.GREEN);
	            		
	            		setupLabelUI(firstName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 100, Color.GREEN);
	            		
	            		setupLabelUI(middleName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 175, Color.GREEN);
	            		
	            		setupLabelUI(lastName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 250, Color.GREEN);
	            		userPane.getChildren().remove(errorLabel);
	            		
	            		// DEVELOPER NOTE: Critical step
	            		// Pass info onto the next part!
	            		AccountDatabase userAccount = new AccountDatabase(); // Object made from AccountDatabase.java (the next part)
		            	userAccount.updateAccountInformation(user, emailString, firstString, middleString, lastString, preferredString);

	            		
	            		//DEVELOPER NOTE: Critical step
	            		// If preferred name box is filled in (i.e. not empty)
	            		if(preferredString != "") {
	            			// Replace first name in user display menu with preferred name
	            			// someName.somePlace() == preferredString; // Something like this (I think)
	            		}
	            	}
            }
        });
	}
	
	
	/*public void userInfo() {
		
	}*/
	
	
	/*public void userInfo(String email, String firstName, String middleName, String lastName, String prefName){
		
		// Constructor variable names are different from constructor input names to prevent variable shadowing
		emailInput = email;
		firstNameInput = firstName;
		middleNameInput = middleName;
		lastNameInput = lastName;
		prefNameInput = prefName;
		
	}*/
	
	/**
	 * Methods
	 */
	
	/**********
	 * Private local method to initialize the standard fields for a label
	 */
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
	private boolean ErrorMessage(String email, String first, String middle, String lasts) {
		
		boolean filled; // Checks of all necessary entries are filled. Starts as false (by default). If any parameters are not filled, stays false.
						// Otherwise, returns as true!
		/*if( (email == "") || (first == "") || (middle == "") || (last == "") ) {
			// Show error above button saying "All necessary text boxes must be filled! (see red)
			// Change relevant titles to red (gonna have to break if statements up)	 
		
		}*/
		// If any entry is empty, filled = false
		if(email == "") {
			filled = false;
		}
		if(email == "") {
			filled = false;
		}
		if(email == "") {
			filled = false;
		}
		if(email == "") {
			filled = false;
		}
		// Else, filled = true
		else {
			filled = true;
		}
		
		// Return filled!
		return filled;
	}
	
	
	
	
	
	
	// DEVELOPER NOTE: start method left here in case future changes want to be made, they can easily be tested here and through
	// defining the function of main simply as "launch(args)". NOTE: You might have to comment out UpdateAccountInfoGUI in order to test using this.
	
	/*@Override
	public void start(Stage updateStage) throws Exception {
    	
        updateStage.setTitle("User Info Update");
        
        Pane userPane = new Pane();
        
        //setupUI = new SetupUIElements();
        
        // NOTE: Not needed, see updateStage.setTitle() ^
        // Label the Scene with the name of the testbed, centered at the top of the pane
		//setupLabelUI(sceneLabel, "Arial", 24, WINDOW_WIDTH, 
				//Pos.CENTER, 0, 10, Color.GREEN);
		
		// Label the email input field with a title just above it, left aligned
		setupLabelUI(emailLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 25, Color.GREEN);
		
		// Label the first name input field with a title just above it, left aligned
		setupLabelUI(firstName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 100, Color.GREEN);
		
		// Label the middle name input field with a title just above it, left aligned
		setupLabelUI(middleName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 175, Color.GREEN);
		
		// Label the last name input field with a title just above it, left aligned
		setupLabelUI(lastName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 250, Color.GREEN);
		
		// Label the preferred name input field with a title just above it, left aligned
		setupLabelUI(prefName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 325, Color.GREEN);
		
		// Establish the text input operand field and when anything changes in the user inputs,
		// the code will process the entire input to ensure that it is valid or an error.
		setupTextUI(emailText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 50, true);
		
		setupTextUI(firstNameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 125, true);
		
		setupTextUI(middleNameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 200, true);
		
		setupTextUI(lastNameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 275, true);
		
		setupTextUI(prefNameText, "Arial", 18, WINDOW_WIDTH-20,
				Pos.BASELINE_LEFT, 10, 350, true);
		
		// Establish the button which will be used to check and send new user info
		// to the respective methods required to update the user info currently in the database
        Button updateButton = new Button("Button test");
        setupButtonUI(updateButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, Color.GREEN);
        
        // Sends all previously established settings for the pane to the scene for setup
        userPane.getChildren().addAll(emailLabel, emailText, firstName, firstNameText, middleName, middleNameText, lastName, lastNameText,
        							  prefName, prefNameText, updateButton); 
        
        Scene userScene = new Scene(userPane, 800, 500);
        userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        updateStage.setScene(userScene);
        updateStage.show();
        
        // Establishes the button logic for each press
        // DEVELOPER NOTE: Button logic does not refresh or continue after VALID input. If this ever becomes an issue, let Evan know and 
        //                 he will add functionality for repeated valid input.
        updateButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	
            	
	            	// Retrieve TextField input
	            	String emailString = emailText.getText();
	            	String firstString = firstNameText.getText();
	            	String lastString = lastNameText.getText();
	            	String middleString = middleNameText.getText();
	            	String preferredString = prefNameText.getText();
	
	                // Do error check, if no errors, update info. If errors, output error message above update button and below info input.
	            	// Repeat process for each button push
	            	boolean pass = ErrorMessage(emailString, firstString, middleString, lastString);
	            	
	            	// If there are any unfilled entries, alter text box and output message indicating that entries are incomplete
	            	// and highlight all necessary entry boxes
	            	if(pass == false) {
	            		userPane.getChildren().add(errorLabel);
	            		setupButtonUI(updateButton, "Arial", 14, WINDOW_WIDTH-20, 
	                    		Pos.CENTER, 10, 430, Color.RED);
	            		setupLabelUI(errorLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 400, Color.RED);
	            		
	            		setupLabelUI(emailLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 25, Color.RED);
	            		
	            		setupLabelUI(firstName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 100, Color.RED);
	            		
	            		setupLabelUI(middleName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 175, Color.RED);
	            		
	            		setupLabelUI(lastName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 250, Color.RED);
	
	            	}
	            	
	            	// If all necessary entries are filled, reset scene formatting and send info to next step!
	            	// DEVELOPER NOTE: Please let Evan know what steps need to be incorporated so I can add whatever is necessary to pass 
	            	// 				   onto then next part. Thank you.
	            	else {
	            		
	            		//Eliminate error indicator
	            		setupButtonUI(updateButton, "Arial", 14, WINDOW_WIDTH-20, 
	            				Pos.CENTER, 10, 400, Color.GREEN);
	            		// Reset color values to green
	            		setupLabelUI(emailLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 25, Color.GREEN);
	            		
	            		setupLabelUI(firstName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 100, Color.GREEN);
	            		
	            		setupLabelUI(middleName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 175, Color.GREEN);
	            		
	            		setupLabelUI(lastName, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 250, Color.GREEN);
	            		userPane.getChildren().remove(errorLabel);
	            		
	            		// DEVELOPER NOTE: Critical step
	            		// Pass info onto the next part!
	            		
	            		//DEVELOPER NOTE: Critical step
	            		// If preferred name box is filled in (i.e. not empty)
	            		if(preferredString != "") {
	            			// Replace first name in user display menu with preferred name
	            			// someName.somePlace() == preferredString; // Something like this (I think)
	            		}
	            	}
            }
        });
        
    }*/
	
	
}

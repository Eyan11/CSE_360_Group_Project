package application;

import database.AccountDatabase;	// To use account database in different package

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;


/**
 * <p> UpdateAccountInformationGUI Class </p>
 * 
 * <p> Description: The Java/FX-based user interface for updating user account information .
 * Developed for Professor Lynn Robert Carter's CSE 360 Group Project (current version: Phase 4).</p>
 * 
 * <p> Copyright: Evan Espinosa © 2024 </p>
 * 
 * @author Evan Espinosa
 * 
 * @version 1.00		2024-12-04 The JavaFX-based GUI for the implementation of the user's update account information page
 *  
 */

// ALL NOTES CRITICAL FOR OTHER DEVELOPERS READING THE CODE AND CONNECTING IT TO THEIR OWN ARE PREFACED BY "DEVELOPER NOTE: "
// However, it is still highly recommended that you read ALL comments throughout the program before doing anything 
// (especially before changing anything!)
public class UpdateAccountInformationGUI {
	/**
	 * Variable declaration
	 */
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;
	
	/** String inputs */
	private String emailString;
    private String firstString;
    private String lastString;
    private String middleString;
    private String preferredString;
	
	/** Text to appear as a part of the window (text field indicators, etc. */
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
	
	
	/** Constructors
	 */
	
	/** Constructor for setting up the user's GUI for the update account info page
	 */
	
	
	UpdateAccountInformationGUI(Pane theRoot, String user) { // user passed in from previous step
		Stage updateStage = new Stage();
		updateStage.setTitle("User Info Update");
        
       
		// Label the email input field with a title just above it, left aligned
		setupLabelUI(emailLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 25, Color.BLACK);
		
		// Label the first name input field with a title just above it, left aligned
		setupLabelUI(firstName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 100, Color.BLACK);
		
		// Label the middle name input field with a title just above it, left aligned
		setupLabelUI(middleName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 175, Color.BLACK);
		
		// Label the last name input field with a title just above it, left aligned
		setupLabelUI(lastName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 250, Color.BLACK);
		
		// Label the preferred name input field with a title just above it, left aligned
		setupLabelUI(prefName, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 325, Color.BLACK);
		
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
        Button updateButton = new Button("Update Account Information");
        setupButtonUI(updateButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, Color.BLACK);
        
        // Sends all previously established settings for the pane to the scene for setup
        theRoot.getChildren().addAll(emailLabel, emailText, firstName, firstNameText, middleName, middleNameText, lastName, lastNameText,
        							  prefName, prefNameText, updateButton);
        
        // Establishes the button logic for each press
        updateButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	// Retrieve TextField input
            	emailString = emailText.getText();
            	firstString = firstNameText.getText();
            	lastString = lastNameText.getText();
            	middleString = middleNameText.getText();
            	preferredString = prefNameText.getText();

                // Do error check, if no errors, update info. If errors, output error message above update button and below info input.
            	// Repeat process for each button push
            	boolean pass = ErrorMessage(emailString, firstString, middleString, lastString);
            	
            	// If there are any unfilled entries, alter text box and output message indicating that entries are incomplete
            	// and highlight all necessary entry boxes
            	if(pass == false) {
            		theRoot.getChildren().add(errorLabel);
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
            	else if (!AccountDatabase.doesEmailExist(user)) {
            		// Reset color values to green
            		setupButtonUI(updateButton, "Arial", 14, WINDOW_WIDTH-20, 
            				Pos.CENTER, 10, 400, Color.BLACK);
            		// Reset color values to green
            		setupLabelUI(emailLabel, "Arial", 14, WINDOW_WIDTH-10, 
            				Pos.BASELINE_LEFT, 10, 25, Color.BLACK);
            		
            		setupLabelUI(firstName, "Arial", 14, WINDOW_WIDTH-10, 
            				Pos.BASELINE_LEFT, 10, 100, Color.BLACK);
            		
            		setupLabelUI(middleName, "Arial", 14, WINDOW_WIDTH-10, 
            				Pos.BASELINE_LEFT, 10, 175, Color.BLACK);
            		
            		setupLabelUI(lastName, "Arial", 14, WINDOW_WIDTH-10, 
            				Pos.BASELINE_LEFT, 10, 250, Color.BLACK);
            		//Eliminate error indicator
            		theRoot.getChildren().remove(errorLabel);
            		
            		// DEVELOPER NOTE: Critical step v1
            		// Pass info onto the next part!
        			AccountDatabase.updateAccountInformation(user, emailString, firstString, middleString, lastString, preferredString);
        			
        			/**
        			 * Transitions to different home pages
        			 */
        			
        			if(LoginEvaluator.multipleRoles(user)) // check is user is admin + (Student or Instructor)
        			{
        				theRoot.getChildren().clear();  // Clear the current root
        				Pane newRoot = new Pane();
        				
        				//Method used for setting up for next part
						SelectRole selectRole = new SelectRole(newRoot, user);
						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
					    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
					    currentStage.setScene(newScene); // 
        			}
        			else if(LoginEvaluator.studentRole(user)) // user is student or instructor
        			{
        				theRoot.getChildren().clear();  // Clear the current root
        				Pane newRoot = new Pane();
        				
        				//Method used for setting up next part
						StudentHomeGUI sHome = new StudentHomeGUI(newRoot);
						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
					    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
					    currentStage.setScene(newScene); // 
        			}
        			else if(LoginEvaluator.instructorRole(user)) // user is student or instructor
        			{
        				theRoot.getChildren().clear();  // Clear the current root
        				Pane newRoot = new Pane();
        				
        				//Method used for setting up for next part
						InstructorHomeGUI iHome = new InstructorHomeGUI(newRoot);
						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
					    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
					    currentStage.setScene(newScene); // 
        			}
        			else if(LoginEvaluator.adminLogin(user)) // check if user is an admin
        			{
        				theRoot.getChildren().clear();  // Clear the current root
        				Pane newRoot = new Pane();
        				
						AdminHome adminHome = new AdminHome(newRoot);
						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
					    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
					    currentStage.setScene(newScene); // 
        			}
        		} else {
            		setupLabelUI(emailLabel, "Arial", 14, WINDOW_WIDTH-10, 
            				Pos.BASELINE_LEFT, 10, 25, Color.RED);
            		
            		setupLabelUI(firstName, "Arial", 14, WINDOW_WIDTH-10, 
            				Pos.BASELINE_LEFT, 10, 100, Color.RED);
            		
            		setupLabelUI(middleName, "Arial", 14, WINDOW_WIDTH-10, 
            				Pos.BASELINE_LEFT, 10, 175, Color.RED);
            		
            		setupLabelUI(lastName, "Arial", 14, WINDOW_WIDTH-10, 
            				Pos.BASELINE_LEFT, 10, 250, Color.RED);
            		
            		System.out.println("Email already exists!");
        		}
        	}
    	});
	}
	
	/**
	 * Methods
	 */
	
	/**********
	 * Private local method to check for valid text field input for all text fields (returns F if all necessary fields are NOT filled, T otherwise)
	 */
	// Checks all necessary entries for not being empty. If any are empty, returns false to button function for error display.
	// Otherwise, if all necessary entries are filled, returns true and sends to button function for pushing info to the next step!
	// (Also resets scene if previous entry was an error)
	private boolean ErrorMessage(String email, String first, String middle, String lasts) {
		
		boolean filled; // Checks of all necessary entries are filled. Starts as false (by default). If any parameters are not filled, stays false.
						// Otherwise, returns as true!
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
}


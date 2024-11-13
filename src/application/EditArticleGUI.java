package application;

import database.*;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * <p> EditArticleGUI Class </p>
 * 
 * <p> Description: The Java/FX-based user interface for the editing of an article 
 * in Lynn Robert Carter's CSE 360 Group Project (current version: Phase 2).</p>
 * 
 * <p> Copyright: Evan Espinosa © 2024 </p>
 * 
 * @author Evan Espinosa
 * 
 * @version 1.00		2024-09-10 The JavaFX-based GUI for the implementation of the user's update account information page
 *  
 */

public class EditArticleGUI {
		/**
		 * Variable declaration
		 */
		
		/** The width of the pop-up window for the user interface */
		public final static double WINDOW_WIDTH = 500;
		/** The height of the pop-up window for the user interface */
		public final static double WINDOW_HEIGHT = 600;
		
		/** String inputs */
		private String user; // Passed in from previous step
		// articleID imported in constructor
		private String headerInput;
		private String titleInput;
		private String descriptionInput;
		private String keywordsInput;
		private String groupsInput;
		private String bodyInput;
		private String referencesInput;

		/** Text to appear as a part of the window (text field indicators, etc. */
		private Label sceneLabel = new Label("Create Articles");
		private Label articleID = new Label("ID:");
		private Label headerLabel = new Label("Header:");
		private Label titleLabel = new Label("Title:");
		private Label descriptionLabel = new Label("Description:");
		private Label keywordsLabel = new Label("Keywords:");
		private Label groupsLabel = new Label("Groups:");
		private Label bodyLabel = new Label("Body:");
		private Label referencesLabel = new Label("References:");
		private Label errorLabel = new Label("Please fill in the required entries (see red)");
		
		/** Text fields for user input */
		// Either do ID = (function for ID import) and then set it later, or something else
		private TextField IDText = new TextField();
		private TextField headerText = new TextField();
		private TextField titleText = new TextField();
		private TextField descriptionText = new TextField();
		private TextField keywordsText = new TextField();
		private TextField groupsText = new TextField();
		private TextField bodyText = new TextField();
		private TextField referencesText = new TextField();
		private TextField errorText = new TextField();
		
		//
		public SetupUIElements setupUI = new SetupUIElements();
		
		/** Constructor for setting up the user's GUI for the update account info page
		 */
		
		// TODO: fix constructor, doesn’t need user
		EditArticleGUI(Pane userPane, int articleID) { // user passed in from previous step
	        
	        // Label the Scene with the name of the testbed, centered at the top of the pane
			//setupLabelUI(sceneLabel, "Arial", 24, WINDOW_WIDTH, 
					//Pos.CENTER, 0, 10, Color.GREEN);
			
			// Label the email input field with a title just above it, left aligned
			setupLabelUI(sceneLabel, "Arial", 14, WINDOW_WIDTH-10, 
					Pos.BASELINE_LEFT, 10, 25, Color.GREEN);
						
			// Label the first name input field with a title just above it, left aligned
			setupLabelUI(headerLabel, "Arial", 14, WINDOW_WIDTH-10, 
					Pos.BASELINE_LEFT, 10, 35, Color.GREEN);
			
			// Label the middle name input field with a title just above it, left aligned
			setupLabelUI(titleLabel, "Arial", 14, WINDOW_WIDTH-10, 
					Pos.BASELINE_LEFT, 10, 105, Color.GREEN);
			
			// Label the last name input field with a title just above it, left aligned
			setupLabelUI(descriptionLabel, "Arial", 14, WINDOW_WIDTH-10, 
					Pos.BASELINE_LEFT, 10, 175, Color.GREEN);
			
			// Label the preferred name input field with a title just above it, left aligned
			setupLabelUI(keywordsLabel, "Arial", 14, WINDOW_WIDTH-10, 
					Pos.BASELINE_LEFT, 10, 245, Color.GREEN);
			
			setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
					Pos.BASELINE_LEFT, 10, 315, Color.GREEN);
			
			setupLabelUI(bodyLabel, "Arial", 14, WINDOW_WIDTH-10, 
					Pos.BASELINE_LEFT, 10, 385, Color.GREEN);
			
			setupLabelUI(referencesLabel, "Arial", 14, WINDOW_WIDTH-10, 
					Pos.BASELINE_LEFT, 10, 455, Color.GREEN);
			
			// Establish the text input operand field and when anything changes in the user inputs,
			// the code will process the entire input to ensure that it is valid or an error.
			setupTextUI(headerText, "Arial", 18, WINDOW_WIDTH-20,
					Pos.BASELINE_LEFT, 10, 60, true);
			
			setupTextUI(titleText, "Arial", 18, WINDOW_WIDTH-20,
					Pos.BASELINE_LEFT, 10, 130, true);
			
			setupTextUI(descriptionText, "Arial", 18, WINDOW_WIDTH-20,
					Pos.BASELINE_LEFT, 10, 200, true);
			
			setupTextUI(keywordsText, "Arial", 18, WINDOW_WIDTH-20,
					Pos.BASELINE_LEFT, 10, 270, true);
			
			setupTextUI(groupsText, "Arial", 18, WINDOW_WIDTH-20,
					Pos.BASELINE_LEFT, 10, 340, true);
			
			setupTextUI(bodyText, "Arial", 18, WINDOW_WIDTH-20,
					Pos.BASELINE_LEFT, 10, 410, true);
			
			setupTextUI(referencesText, "Arial", 18, WINDOW_WIDTH-20,
					Pos.BASELINE_LEFT, 10, 480, true);
			
			setupTextUI(errorText, "Arial", 18, WINDOW_WIDTH-20,
					Pos.BASELINE_LEFT, 10, 505, true);
			
			// Establish the button which will be used to check and send new user info
			// to the respective methods required to update the article info currently in the database
			Button editButton = new Button("Edit");
	        setupButtonUI(editButton, "Arial", 14, WINDOW_WIDTH-20, 
	        		Pos.CENTER, 10, 545, Color.GREEN);
	        
	        Button backButton = new Button("<-");
	        setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
	        		Pos.CENTER, 10, 10, false, Color.BLACK);
	        
	        // Sends all previously established settings for the pane to the scene for setup
	        userPane.getChildren().addAll(headerLabel, headerText, titleLabel, titleText, descriptionLabel, descriptionText, keywordsLabel, keywordsText,
	        		groupsLabel, groupsText, bodyLabel, bodyText, referencesLabel, referencesText, editButton, backButton); 
	        
	        /*Scene userScene = new Scene(userPane, 800, 500);
	        userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        updateStage.setScene(userScene);
	        updateStage.show();*/
	        
	        // Establishes the button logic for each press
	        // DEVELOPER NOTE: Button logic does not refresh or continue after VALID input. If this ever becomes an issue, let Evan know and 
	        //                 he will add functionality for repeated valid input.
	        editButton.setOnAction(new EventHandler<>() {
	            public void handle(ActionEvent event) {
	            	
		            	// Retrieve TextField input
		            	String headerString = headerText.getText();
		            	String titleString = titleText.getText();
		            	String descriptionString = descriptionText.getText();
		            	String keywordsString = keywordsText.getText();
		            	String groupsString = groupsText.getText();
		            	String bodyString = bodyText.getText();
		            	String referencesString = referencesText.getText();

		
		                // Do error check, if no errors, update info. If errors, output error message above update button and below info input.
		            	// Repeat process for each button push
		            	boolean pass = ErrorMessage(headerString, titleString, descriptionString, keywordsString, 
		            			groupsString, bodyString, referencesString);
		            	
		            	// If there are any unfilled entries, alter text box and output message indicating that entries are incomplete
		            	// and highlight all necessary entry boxes
		            	if(pass == false) {
		            		userPane.getChildren().add(errorLabel);
		
		            		setupButtonUI(editButton, "Arial", 14, WINDOW_WIDTH-20, 
		                    		Pos.CENTER, 10, 430, Color.RED);
		            		setupLabelUI(errorLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 400, Color.RED);
		            		
		            		setupLabelUI(headerLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 10, Color.RED);

		            		setupLabelUI(titleLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 25, Color.RED);
		            		
		            		setupLabelUI(descriptionLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 100, Color.RED);
		            		
		            		setupLabelUI(keywordsLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 175, Color.RED);
		            		
		            		setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 250, Color.RED);
		            		setupLabelUI(bodyLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 250, Color.RED);
		            		setupLabelUI(referencesLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 250, Color.RED);
		
		            	}
		            	
		            	// If all necessary entries are filled, reset scene formatting and send info to next step!
		            	// DEVELOPER NOTE: Please let Evan know what steps need to be incorporated so I can add whatever is necessary to pass 
		            	// 				   onto then next part. Thank you.
		            	else {
		            		//Eliminate error indicator
		            		setupButtonUI(editButton, "Arial", 14, WINDOW_WIDTH-20, 
		                    		Pos.CENTER, 10, 430, Color.GREEN);
		            		setupLabelUI(errorLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 400, Color.GREEN);
		            		
		            		setupLabelUI(titleLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 25, Color.GREEN);

		            		
		            		setupLabelUI(titleLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 25, Color.GREEN);
		            		
		            		setupLabelUI(descriptionLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 100, Color.GREEN);
		            		
		            		setupLabelUI(keywordsLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 175, Color.GREEN);
		            		
		            		setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 250, Color.GREEN);
		            		setupLabelUI(bodyLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 250, Color.GREEN);
		            		setupLabelUI(referencesLabel, "Arial", 14, WINDOW_WIDTH-10, 
		            				Pos.BASELINE_LEFT, 10, 250, Color.GREEN);
		            		userPane.getChildren().remove(errorLabel);
		            		
		            		// DEVELOPER NOTE: Critical step v1
		            		// Pass info onto the next part!
		            		/* TODO - add author and content level arguments
	            			ArticleDatabase.editArticle(articleID, headerString, titleString, descriptionString, keywordsString, 
	            					groupsString, bodyString, referencesString);
	            			*/
		            		
	            			/**
	            			 * Transitions to different home pages
	            			 */
	            			
	            			userPane.getChildren().clear();  // Clear the current root
	            			
	            			Pane newRoot = new Pane();
	            			ManageArticlesGUI manageArticlesGUI = new ManageArticlesGUI(newRoot); 
	            			
	            			Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
						    Stage currentStage = (Stage) userPane.getScene().getWindow();
						    currentStage.setScene(newScene); // sets new scene
	            			
    						
    						/*Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
    		                updateStage.setScene(newScene);
    		                updateStage.show();*/
	            			
	            			/*if(LoginEvaluator.adminLogin(user)) // check if user is an admin
	            			{
	            				userPane.getChildren().clear();  // Clear the current root
	    						AdminHome adminHome = new AdminHome(newRoot);
	    						
	    						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
	    		                updateStage.setScene(newScene);
	    		                updateStage.show();
	            			}
	            			else if(LoginEvaluator.multipleRoles(user)) // check is user is admin + (Student or Instructor)
	            			{
	            				userPane.getChildren().clear();  // Clear the current root
	    						SelectRole selectRole = new SelectRole(newRoot, user);
	    						
	    						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
	    		                updateStage.setScene(newScene);
	    		                updateStage.show();
	            			}
	            			else if(LoginEvaluator.studentInstructorRole(user)) // user is student or instructor
	            			{
	            				userPane.getChildren().clear();  // Clear the current root
	    						SelectRole selectRole = new SelectRole(newRoot, user);
	    						
	    						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
	    		                updateStage.setScene(newScene);
	    		                updateStage.show();
	            			}*/
		            		
		            		//DEVELOPER NOTE: Critical step v2 (Experimental implementation idea, keeping for future reference in the coming phases)
		            		//                (maybe not needed?)
		            		// If preferred name box is filled in (i.e. not empty)
		            		//if(preferredString != "") {
		            			// Replace first name in user display menu with preferred name
		            			// someName.somePlace() == preferredString; // Something like this (I think)
		            		//}
		            	}
	            }
	        });
		}
		
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
		
		/**********
		 * Private local method to check for valid text field input for all text fields (returns F if all necessary fields are NOT filled, T otherwise)
		 */
		// Checks all necessary entries for not being empty. If any are empty, returns false to button function for error display.
		// Otherwise, if all necessary entries are filled, returns true and sends to button function for pushing info to the next step!
		// (Also resets scene if previous entry was an error)
		private boolean ErrorMessage(String headerString, String titleString, String descriptionString, String keywordsString, 
				String groupsString, String bodyString, String referencesString) {
			
			boolean filled; // Checks of all necessary entries are filled. Starts as false (by default). If any parameters are not filled, stays false.
							// Otherwise, returns as true!
			/*if( (email == "") || (first == "") || (middle == "") || (last == "") ) {
				// Show error above button saying "All necessary text boxes must be filled! (see red)
				// Change relevant titles to red (gonna have to break if statements up)	 
			
			}*/
			// If any entry is empty, filled = false
			if(headerString == "") {
				filled = false;
			}
			if(titleString == "") {
				filled = false;
			}
			if(descriptionString == "") {
				filled = false;
			}
			if(keywordsString == "") {
				filled = false;
			}
			if(groupsString == "") {
				filled = false;
			}
			if(bodyString == "") {
				filled = false;
			}
			if(referencesString == "") {
				filled = false;
			}
			// Else, filled = true
			return filled = true;

		}
	}

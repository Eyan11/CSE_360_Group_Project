package application;

import database.ArticleDatabase;
import database.GroupDatabase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane; 
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * <p> CreateArticleGUI Class </p>
 * 
 * <p> Description: The Java/FX-based user interface for the creation of an article 
 * in Lynn Robert Carter's CSE 360 Group Project (current version: Phase 2).</p>
 * 
 * <p> Copyright: Evan Espinosa © 2024 </p>
 * 
 * @author Evan Espinosa
 * 
 * @version 1.00 2024-09-10 The JavaFX-based GUI for the implementation of the user's update account information page
 *  
 */

public class CreateArticle {
	
	 /**
	 * Variable declaration
	 */
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;
	
	/** String inputs */
	private String headerString;
	private String titleString;
	private String authorString;
	private String descriptionString;
	private String keywordsString;
	private String bodyString;
	private String referencesString;

	/** Text to appear as a part of the window (text field indicators, etc. */
	//private Label sceneLabel = new Label("Create Article");
	private Label headerLabel = new Label("Header:");
	private Label titleLabel = new Label("Title:");
	private Label authorLabel = new Label("Author:");
	private Label descriptionLabel = new Label("Description:");
	private Label keywordsLabel = new Label("Keywords:");
	private Label groupsLabel = new Label("Groups:");
	private Label contentLabel = new Label("Content level:");
	private Label bodyLabel = new Label("Body:");
	private Label referencesLabel = new Label("References:");
	private Label errorLabel = new Label("Please fill in the required entries (see red)");
	private Label errorLabel2 = new Label("Please select a group (see red)");
	private Label errorLabel3 = new Label("Please select a difficulty (see red)");



	
	/** Text fields for user input */
	private TextField headerText = new TextField();
	private TextField titleText = new TextField();
	private TextField descriptionText = new TextField();
	private TextField keywordsText = new TextField();
	private TextField authorText = new TextField();
	private TextField bodyText = new TextField();
	private TextField referencesText = new TextField();
	//private TextField errorText = new TextField();
	
	// Declaration of SetupUIElements Object
	public SetupUIElements setupUI = new SetupUIElements();
	
	/** Constructors
	 */
	
	/** Constructor for setting up the user's GUI for the update account info page
	 */
	
	CreateArticle(Pane userPane) { // user passed in from previous step

		// Utilizes the SetUpElements class for the Label and Button
    	setupUI = new SetupUIElements();

    	String returnGroups = "";
    	
    	String groupString = GroupDatabase.getAllAuthorizedGroupNames(false);
    	String[] GroupsArr = groupString.split("\\+");
		
		// Label the email input field with a title just above it, left aligned
		setupLabelUI(errorLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 500, Color.RED);
		
		// Label the email input field with a title just above it, left aligned
		setupLabelUI(errorLabel2, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 500, Color.RED);
		
		// Label the email input field with a title just above it, left aligned
		setupLabelUI(errorLabel2, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 500, Color.RED);
				
		// Label the first name input field with a title just above it, left aligned
		setupLabelUI(headerLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
		
		// Label the middle name input field with a title just above it, left aligned
		setupLabelUI(titleLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 90, Color.BLACK);
		
		// Label the middle name input field with a title just above it, left aligned
		setupLabelUI(authorLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 130, Color.BLACK);
		
		// Label the last name input field with a title just above it, left aligned
		setupLabelUI(descriptionLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 170, Color.BLACK);
		
		// Label the preferred name input field with a title just above it, left aligned
		setupLabelUI(keywordsLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 210, Color.BLACK);
		
		setupLabelUI(contentLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 230, Color.BLACK);
		
		setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 275, Color.BLACK);
		
		//setupLabelUI(groupLabel, "Arial", 14, WINDOW_WIDTH-10, 
				//.Pos.BASELINE_LEFT, 10, 350, Color.BLACK);
		
		setupLabelUI(bodyLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 335, Color.BLACK);
		
		setupLabelUI(referencesLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 10, 375, Color.BLACK);
		
		// Establish the text input operand field and when anything changes in the user inputs,
		// the code will process the entire input to ensure that it is valid or an error.
		setupUI.SetupTextFieldUI(headerText, "Arial", 18, 400, 10,
				Pos.BASELINE_LEFT, 65, 35, true);
		
		setupUI.SetupTextFieldUI(titleText, "Arial", 18, 400, 10,
				Pos.BASELINE_LEFT, 55, 75, true);
		
		setupUI.SetupTextFieldUI(authorText, "Arial", 18, 400, 10,
				Pos.BASELINE_LEFT, 65, 115, true);
		
		setupUI.SetupTextFieldUI(descriptionText, "Arial", 18, 400, 10,
				Pos.BASELINE_LEFT, 85, 155, true);
		
		setupUI.SetupTextFieldUI(keywordsText, "Arial", 18, 400, 10,
				Pos.BASELINE_LEFT, 80, 195, true);
		
		setupUI.SetupTextFieldUI(bodyText, "Arial", 18, 400, 10,
				Pos.BASELINE_LEFT, 55, 335, true);
		
		setupUI.SetupTextFieldUI(referencesText, "Arial", 18, 400, 10,
				Pos.BASELINE_LEFT, 90, 375, true);
		
		ComboBox difficultyBox = new ComboBox();
	        difficultyBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");      
	        difficultyBox.setLayoutX(20);
	        difficultyBox.setLayoutY(250);
	        
	        
		ComboBox groupBox = new ComboBox<>();
        	groupBox.getItems().addAll(GroupsArr);
        	groupBox.setLayoutX(20);
        	groupBox.setLayoutY(295);

		
		// Establish the button which will be used to check and send new user info
		// to the respective methods required to update the user info currently in the database
        Button createButton = new Button("Create");
        setupButtonUI(createButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 420, Color.BLACK);
        
        Button backButton = new Button("Back");
        setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 10, 10, false, Color.BLACK);
        
        /*
        Button addButton = new Button("Add");
        setupUI.SetupButtonUI(addButton, "Arial", 11, 50, 20,
        		Pos.CENTER, 110, 295, false, Color.BLACK);
        
        Button removeButton = new Button("Remove");
        setupUI.SetupButtonUI(removeButton, "Arial", 11, 80, 20,
        		Pos.CENTER, 160, 295, false, Color.BLACK);
        */
        
        // Sends all previously established parameters for the pane to the scene for setup
        userPane.getChildren().addAll(headerLabel, headerText, titleLabel, titleText, authorLabel, 
        		authorText, descriptionLabel, descriptionText, keywordsLabel, keywordsText,
        		groupsLabel, contentLabel, bodyLabel, bodyText, referencesLabel, 
        		referencesText, groupBox, difficultyBox, createButton, backButton); 
        
        // Don't need this -- error (Only need scene when you're going to a different page)
        //Scene userScene = new Scene(userPane, 800, 500);
        //userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        //updateStage.setScene(userScene);
        //updateStage.show();
        
        // Establishes the button logic for each press
        // DEVELOPER NOTE: Button logic does not refresh or continue after VALID input. If this ever becomes an issue, let Evan know and 
        //                 he will add functionality for repeated valid input.
        
        /*
        addButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
	            	// Retrieve TextField input
	            	headerString = headerText.getText();
	            	titleString = titleText.getText();
	            	authorString = authorText.getText();
	            	descriptionString = descriptionText.getText();
	            	keywordsString = keywordsText.getText();
	            	bodyString = bodyText.getText();
	            	referencesString = referencesText.getText();
	            	
	            	// Retrieve selected role (admin, user) from the role combobox, turn it into string
					String difficultyString = (String) difficultyBox.getSelectionModel().getSelectedItem();
				    
					// Retrieve selected group (any amount of any strings) from the group combobox, turn it into string 
					String selectedGroup = (String) groupBox.getSelectionModel().getSelectedItem();
	            	
				// Check for group selection 
	            if(selectedGroup == null ) {
	            	// Select a group dummy
	            	userPane.getChildren().remove(errorLabel);
	            	userPane.getChildren().remove(errorLabel3);
	            	userPane.getChildren().add(errorLabel2);

	            	// Change stuff to red
            		
            		setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
            				Pos.BASELINE_LEFT, 10, 275, Color.RED);
	            }
	            else {
	            	// Remove error indicators
	            	userPane.getChildren().remove(errorLabel2);
	            	
	            	setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
            				Pos.BASELINE_LEFT, 10, 275, Color.GREEN);
	            	
	            	// Check for difficulty selection

	            	if(difficultyString == null) {
	            		// Select a difficulty dummy
		            	userPane.getChildren().add(errorLabel3);
		            	// Change stuff to red
	            		setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 275, Color.RED);
	            	}
	            	else {
	            		// Remove error indicators
	            		userPane.getChildren().remove(errorLabel);
	            		userPane.getChildren().add(errorLabel2);
	            		userPane.getChildren().add(errorLabel3);
	            		setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 275, Color.RED);
	            		
	            		if(returnGroups == "") {
	            			returnGroups = returnGroups + selectedGroup;
	            		}
	            		else {
	            			returnGroups = returnGroups + selectedGroup + " & ";
	            		}
	            	}
	            }
            }
        });
        */
        
        /*
        // Julio, remember what I said about this method (those who knows :skull:)
        removeButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	
	            	// Retrieve TextField input
	            	headerString = headerText.getText();
	            	titleString = titleText.getText();
	            	authorString = authorText.getText();
	            	descriptionString = descriptionText.getText();
	            	keywordsString = keywordsText.getText();
	            	//groupsString = groupsText.getText();
	            	bodyString = bodyText.getText();
	            	referencesString = referencesText.getText();
	            	
	            	// Retrieve selected group (any amount of any strings) from the group combobox, turn it into string 
					String selectedGroup = (String) groupBox.getSelectionModel().getSelectedItem();
	            	
	            	
	            	if(returnGroups == "") {
            			returnGroups = returnGroups + selectedGroup;
            		}
            		else {
            			returnGroups = returnGroups + selectedGroup + " & ";
            		}
	
            }  
        });
        */
        
        
        createButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
	            	// Retrieve TextField input
	            	headerString = headerText.getText();
	            	titleString = titleText.getText();
	            	authorString = authorText.getText();
	            	descriptionString = descriptionText.getText();
	            	keywordsString = keywordsText.getText();
	            	bodyString = bodyText.getText();
	            	referencesString = referencesText.getText();
	
	                // Do error check, if no errors, update info. If errors, output error message above update button and below info input.
	            	// Repeat process for each button push
	            	boolean pass = ErrorMessage(headerString, titleString, authorString, descriptionString, keywordsString, 
	            			bodyString, referencesString);
	            	
	            	// If there are any unfilled entries, alter text box and output message indicating that entries are incomplete
	            	// and highlight all necessary entry boxes
	            	if(pass == false) {
	            		
	            		userPane.getChildren().remove(errorLabel2);
	            		userPane.getChildren().remove(errorLabel3);
	            		userPane.getChildren().add(errorLabel);

	            		setupLabelUI(headerLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 50, Color.RED);
	            		setupLabelUI(titleLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 90, Color.RED);
	            		setupLabelUI(authorLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 130, Color.RED);
	            		setupLabelUI(descriptionLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 170, Color.RED);
	            		setupLabelUI(keywordsLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 210, Color.RED);
	            		setupLabelUI(contentLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 230, Color.RED);
	            		setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 275, Color.RED);
	            		setupLabelUI(bodyLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 335, Color.RED);
	            		setupLabelUI(referencesLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 375, Color.RED);
	            	}
	            	
	            	// If all necessary entries are filled, reset scene formatting and send info to next step!
	            	// DEVELOPER NOTE: Please let Evan know what steps need to be incorporated so I can add whatever is necessary to pass 
	            	// 				   onto then next part. Thank you.
	            	else {
	            		//Eliminate error indicator
	            		userPane.getChildren().remove(errorLabel);
	            		
	            		setupLabelUI(headerLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
	            		setupLabelUI(titleLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 90, Color.BLACK);
	            		setupLabelUI(authorLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 130, Color.BLACK);
	            		setupLabelUI(descriptionLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 170, Color.BLACK);
	            		setupLabelUI(keywordsLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 210, Color.BLACK);
	            		setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 280, Color.BLACK);
	            		setupLabelUI(bodyLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 320, Color.BLACK);
	            		setupLabelUI(referencesLabel, "Arial", 14, WINDOW_WIDTH-10, 
	            				Pos.BASELINE_LEFT, 10, 360, Color.BLACK);
	            		
	            		// Retrieve selected role (admin, user) from the role combobox, turn it into string
						String difficultyString = (String) difficultyBox.getSelectionModel().getSelectedItem();
						
						String groupsString = (String) groupBox.getSelectionModel().getSelectedItem();
	            		
	            		// DEVELOPER NOTE: Critical step v1
	            		// Pass info onto the next part!
	            		/*	TODO - add author and content level arguments (see below)
            			ArticleDatabase.createArticle(headerString, titleString, descriptionString, keywordsString, 
            					groupsString, bodyString, referencesString);
            			*/
						
						// Julio and Eyan, slight error here. Thanks again for the decoding help
	            		ArticleDatabase.createArticle(headerString, titleString, authorString, descriptionString, 
	            					keywordsString, difficultyString, groupsString, bodyString, referencesString);
            			
            			/**
            			 * Transitions to different home pages
            			 */
            			userPane.getChildren().clear();  // Clear the current root
            			
            			Pane newRoot = new Pane();
            			// Load next step
            			ManageArticlesGUI manageArticles = new ManageArticlesGUI(newRoot); 
						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
						Stage currentStage = (Stage) userPane.getScene().getWindow(); // 
						currentStage.setScene(newScene);
	            	}
            }
        });
        
		backButton.setOnAction(new EventHandler<>()
		{
			public void handle(ActionEvent event) 
			{						
				// Returns user back to previous page
				userPane.getChildren().clear();  // Clear the current root
				
				// Create new pane for next interface
				Pane newRoot = new Pane();
				ManageArticlesGUI manageArticles = new ManageArticlesGUI(newRoot); // Returns user to previous interface
				Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
			    Stage currentStage = (Stage) userPane.getScene().getWindow();
			    currentStage.setScene(newScene); // sets new scene
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
	private boolean ErrorMessage(String headerString, String titleString, String authorString, String descriptionString, String keywordsString, 
			String bodyString, String referencesString) {
		
		boolean filled = true; // Checks of all necessary entries are filled. Starts as false (by default). If any parameters are not filled, stays false.
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
		if(authorString == "") {
			filled = false;
		}
		if(descriptionString == "") {
			filled = false;
		}
		if(keywordsString == "") {
			filled = false;
		}
		if(bodyString == "") {
			filled = false;
		}
		if(referencesString == "") {
			filled = false;
		}
		// Else, filled = true
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
	/*
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}	
	*/
	
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
}


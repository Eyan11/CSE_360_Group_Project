package application;

import database.AccountDatabase;
import database.LoginTracker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * <p> ResetAccountGUI Class </p>
 * 
 * <p> Description: The Java/FX-based user interface for retrieving the one-time use code for
 * 					resetting user account information. This window appears after a username is input in the username box on the reset
 * 					account page and clicking the "Reset" button. Code expires after 10 minutes.
 * Developed for Professor Lynn Robert Carter's CSE 360 Group Project (current version: Phase 4).</p>
 * 
 * <p> Copyright: Evan Espinosa © 2024 </p>
 * 
 * @author Evan Espinosa
 * 
 * @version 1.00		2024-12-04 The JavaFX-based GUI for the implementation of the user's reset account page
 *  
 */

public class ResetAccountGUI {
	
	/**
	 * Variable declaration
	 */
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;
	
	/** Constructors
	 */
	
	/** Constructor for setting up the user's GUI for the update account info page
	 */

	ResetAccountGUI(Pane theRoot, String user) { // user passed in from previous step
		String key = "";
		
		key = AccountDatabase.resetUser(user);
		System.out.println(key);
        
		/** Text to appear as a part of the window (text field indicators, etc. */
		Label keyLabel = new Label("One-time key: " + key);
		Label tenMinsLabel = new Label("Expires in 10 minutes");

		// Key label of format "One time key: (key)"
		setupLabelUI(keyLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.CENTER, 10, 175, Color.GREEN);
		// Label that says the key will expire in 10 minutes
		setupLabelUI(tenMinsLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.CENTER, 10, 215, Color.GREEN);
		
		// Establish the button which will be used to exit the reset account menu and return the user to the relavant page
        Button backButton = new Button("Back");
        setupButtonUI(backButton, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, Color.GREEN);
        

        // Sends all previously established parameters for the pane to the scene for setup (to the new Pane to avoid Pane conflict)
        theRoot.getChildren().addAll(keyLabel, tenMinsLabel, backButton); 
        
        // Establishes the button logic for each press
        backButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	if(LoginTracker.usingAdminRole())
            	{
    	        	theRoot.getChildren().clear();
    	            
    	            Pane newRoot = new Pane();
    	            AdminHome adminHome = new AdminHome(newRoot); 
    	            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
    	    	    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
    	    	    currentStage.setScene(newScene); //
            	}
            	else if(LoginTracker.usingInstructorRole())
            	{
            		theRoot.getChildren().clear();
    	            
    	            Pane newRoot = new Pane();
    	            InstructorHomeGUI instructorHome = new InstructorHomeGUI(newRoot); 
    	            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
    	    	    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
    	    	    currentStage.setScene(newScene); //
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




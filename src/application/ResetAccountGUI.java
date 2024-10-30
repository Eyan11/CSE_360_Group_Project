package application;
import database.AccountDatabase;

import java.sql.SQLException;

import javafx.application.Application;
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
import java.sql.SQLException;	// To catch errors for database


/**
 * <p> ResetAccountGUI Class </p>
 * 
 * <p> Description: The Java/FX-based user interface for updating user account information 
 * in Lynn Robert Carter's CSE 360 Group Project (current version: Phase 2).</p>
 * 
 * <p> Copyright: Evan Espinosa © 2024 </p>
 * 
 * @author Evan Espinosa
 * 
 * @version 1.00		2024-10-30 The JavaFX-based GUI for the implementation of the user's reset account page
 *  
 */


//public class ResetAccountGUI extends Application {

public class ResetAccountGUI {
	
	/**
	 * Variable declaration
	 */
	
	private String user; // Imported from calling class
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;


	
	/** Constructors
	 */
	
	/** Constructor for setting up the user's GUI for the update account info page
	 */
	//ResetAccountGUI(Pane resetPane, String user) extends Application { // user passed in from previous step
	ResetAccountGUI(Pane userPane, String user) { // user passed in from previous step
		Stage updateStage = new Stage();
		updateStage.setTitle("Reset Account");
		
		// Used for testing
		//String thisUser = "user";
		//String thisPass = "pass";
		
		String key = "";
		
		try {
			
			// Used for testing
			//AccountDatabase.createFirstAccount(thisUser, thisPass);
			//key = AccountDatabase.resetUser(thisUser);
			
			
			key = AccountDatabase.resetUser(user);
			System.out.println(key);
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
			key = "Error occured"; // Default message for key if an error occurs
		}
		
		// Create new Pane to prevent conflict with defining a new Pane with an existing Pane object
		Pane resetPane = new Pane();
        
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
        Button back = new Button("Back");
        setupButtonUI(back, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, Color.GREEN);
        

        // Sends all previously established parameters for the pane to the scene for setup (to the new Pane to avoid Pane conflict)
        resetPane.getChildren().addAll(keyLabel, tenMinsLabel, back); 
        
        // Creates new scene using resetPane so as to avoid Pane conflict with userPane
        Scene userScene = new Scene(resetPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        updateStage.setScene(userScene);
        updateStage.show();
        
        // Establishes the button logic for each press
        back.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	
            	resetPane.getChildren().clear();
            	updateStage.close();
            	new AdminHome(userPane);
            	
            	// Not needed here, but kept for reference on previous incorrect implementation methods 
            	//to prevent future errors of the same type
            	/*try {
					new AdminHome(userPane);
				} catch (SQLException e) {
        			System.err.println("Error: " + e.getMessage());

				}*/
            		            	}
        });
        
        
	}

	



/**
 * Methods
 */

	// Main and override kept for possible future use
	/*public static void main(String[] args) {
		launch(args);
	}*/
	
	/*@Override
	public void start(Stage updateStage) throws Exception {

		updateStage.setTitle("Reset Account");
		
		Pane userPane = new Pane();

		String key = "";
		
		try {
			key = AccountDatabase.resetUser(user);
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());

		}
        
		// Text to appear as a part of the window (text field indicators, etc. 
		
		Label keyLabel = new Label("One-time key: " + key);
		Label tenMinsLabel = new Label("Expires in 10 minutes");

		// Label the middle name input field with a title just above it, left aligned
				setupLabelUI(keyLabel, "Arial", 14, WINDOW_WIDTH-10, 
						Pos.BASELINE_LEFT, 10, 175, Color.GREEN);
				// Label the middle name input field with a title just above it, left aligned
				setupLabelUI(tenMinsLabel, "Arial", 14, WINDOW_WIDTH-10, 
						Pos.BASELINE_LEFT, 10, 215, Color.GREEN);
		// Establish the button which will be used to check and send new user info
		// to the respective methods required to update the user info currently in the database
        Button back = new Button("Back");
        setupButtonUI(back, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, Color.GREEN);
        

        // Sends all previously established settings for the pane to the scene for setup
        userPane.getChildren().addAll(back); 
        
        Scene userScene = new Scene(userPane, 800, 500);
        userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        updateStage.setScene(userScene);
        updateStage.show();
        
        // Establishes the button logic for each press
        // DEVELOPER NOTE: Button logic does not refresh or continue after VALID input. If this ever becomes an issue, let Evan know and 
        //                 he will add functionality for repeated valid input.
        back.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	 
            	new AdminHome(userPane);
            	try {
					new AdminHome(userPane);
				} catch (SQLException e) {
        			System.err.println("Error: " + e.getMessage());

				}
            		            	}
        });
	}*/

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
}
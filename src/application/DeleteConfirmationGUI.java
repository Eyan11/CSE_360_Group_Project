package application;
import database.AccountDatabase;

import java.sql.SQLException;
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
 * <p> DeleteConfirmationGUI Class </p>
 * 
 * <p> Description: The Java/FX-based user interface for confirming user account deletion 
 * in Lynn Robert Carter's CSE 360 Group Project (current version: Phase 2).</p>
 * 
 * <p> Copyright: Evan Espinosa © 2024 </p>
 * 
 * @author Evan Espinosa
 * 
 * @version 1.00		2024-10-30 The JavaFX-based GUI for the implementation of the user's delete account page
 *  
 */


// public class DeleteConfirmationGUI extends Application {
public class DeleteConfirmationGUI {
	/**
	 * Variable declaration
	 */
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;

	
	/** Text to appear as a part of the window (text field indicators, etc. */
	private Label sceneLabel = new Label("Are You Sure?"
			+ "\n(once you delete you cannot "
			+ "\nrestore the account)");
	
	// Declaration of SetupUIElements Object
	public SetupUIElements setupUI;
	
	
	/** Constructors
	 */
	
	/** Constructor for setting up the user's GUI for the delete account page	 */
	DeleteConfirmationGUI(Pane userPane, String user) { // user passed in from previous step
		// Utilizes the SetUpElements class for Labels, TextFields, and Buttons
		setupUI = new SetupUIElements();
		
		// Label the email input field with a title just above it, left aligned
		setupLabelUI(sceneLabel, "Arial", 36, WINDOW_WIDTH-10, 
						Pos.CENTER, 10, 25, Color.RED);
		// Establish the button which will be used to confirm user account deletion
		// to the respective methods required to update the user info currently in the database
        Button buttonYes = new Button("Yes");
        setupUI.SetupButtonUI(buttonYes, "Arial", 14, 100, 20, 
        		Pos.BASELINE_LEFT, 125, 175, false, Color.BLACK);

        // Establish the button which will be used to abort user account deletion
		// to the respective methods required to update the user info currently in the database
        Button buttonNo = new Button("No");
        setupUI.SetupButtonUI(buttonNo, "Arial", 14, 100, 20,
        		Pos.BASELINE_LEFT, 275, 175, false, Color.BLACK);
        
        // Sends all previously established parameters for the pane to the scene for setup
        userPane.getChildren().addAll(buttonYes, buttonNo, sceneLabel); 
        
        
        // Used for testing
        /*try {
        	String thisUser = "user";
        	String thisPass = "pass";
        	AccountDatabase.createFirstAccount(thisUser, thisPass);
        }catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());

		}*/
        
        // Establishes the button logic for each press
        // DEVELOPER NOTE: Button logic does not refresh or continue after VALID input. If this ever becomes an issue, let Evan know and 
        //                 he will add functionality for repeated valid input.
        // Both yes and no go back to ModifyAccountsGUI
        buttonYes.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	
            	// Send yes confirmation to database 
            	try {
            		

            		//Clear stuff
					userPane.getChildren().clear();
					
					// Delete user information after yes confirmation
					AccountDatabase.deleteUser(user);
					
					Pane newRoot = new Pane();
					
					// Load new window
					ModifyAccountsGUI modify = new ModifyAccountsGUI(newRoot);
					
					Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //

	                
					Stage currentStage = (Stage) userPane.getScene().getWindow(); //
	                currentStage.setScene(newScene);
					
				} catch (SQLException e) {
        			System.err.println("Error: " + e.getMessage());

				}
            	
            	
            		            	}
        });
        
        buttonNo.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	
            	
            	//Clear stuff
				userPane.getChildren().clear();
				
				// No user deletion after no confirmation
				
				// Setup for loading new page
				Pane newRoot = new Pane();
					
				try {
					
					// Load new window
					ModifyAccountsGUI modify = new ModifyAccountsGUI(newRoot);
					
	            	
	            	 Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
		                
	                 Stage currentStage = (Stage) userPane.getScene().getWindow(); //
	                 currentStage.setScene(newScene);

				}
				catch(SQLException e){
        			System.err.println("Error: " + e.getMessage());
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
}


package application;

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



public class DeleteConfirmationGUI extends Application {
	
	
	/**
	 * Variable declaration
	 */
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;

	/** Text to appear as a part of the window (text field indicators, etc. */
	private Label sceneLabel = new Label("Delete Confirmation");
	
	/** Constructors
	 */
	
	/** Constructor for setting up the user's GUI for the update account info page
	 */
	/*DeleteConfirmationGUI(Pane userPane, String user) { // user passed in from previous step
		Stage updateStage = new Stage();
		updateStage.setTitle("Delete Confirmation");
        
        
		
		
		// Establish the button which will be used to check and send new user info
		// to the respective methods required to update the user info currently in the database
        Button buttonYes = new Button("Yes");
        setupButtonUI(buttonYes, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, Color.GREEN);

		// Establish the button which will be used to check and send new user info
		// to the respective methods required to update the user info currently in the database
        Button buttonNo = new Button("No");
        setupButtonUI(buttonNo, "Arial", 14, WINDOW_WIDTH-20, 
        		Pos.CENTER, 10, 400, Color.GREEN);
        
        // Sends all previously established settings for the pane to the scene for setup
        userPane.getChildren().addAll(buttonYes, buttonNo); 
        
        Scene userScene = new Scene(userPane, 800, 500);
        userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        updateStage.setScene(userScene);
        updateStage.show();
        
        // Establishes the button logic for each press
        // DEVELOPER NOTE: Button logic does not refresh or continue after VALID input. If this ever becomes an issue, let Evan know and 
        //                 he will add functionality for repeated valid input.
        buttonYes.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	
            	// Send yes confirmation to database 
            	try {
					AccountDatabase.deleteUser(user);
					
					// Only explicit error in the entire class; Tried everythign I could think of and still an error so I will
					// address this when I finish I=my other class -Evan
					//UpdateAccountInformationGUI update = new UpdateAccountInformationGUI(userPane, user);
					//update.UpdateAccountInformationGUI(userPane, user);
					new UpdateAccountInformationGUI(userPane, user);
				} catch (SQLException e) {
        			System.err.println("Error: " + e.getMessage());

				}
            		            	}
        });
        
        buttonNo.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	
				UpdateAccountInformationGUI update = new UpdateAccountInformationGUI(userPane, user);
            		            	}
        });
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

public static void main(String[] args) {
	launch(args);
}



public void start(Stage updateStage) throws Exception {
	
	Pane userPane = new Pane();
	String user = "wawa";
	//Stage updateStage = new Stage();
	updateStage.setTitle("Delete Confirmation");
    
    
	
	
	// Establish the button which will be used to check and send new user info
	// to the respective methods required to update the user info currently in the database
    Button buttonYes = new Button("Yes");
    setupButtonUI(buttonYes, "Arial", 14, WINDOW_WIDTH-20, 
    		Pos.CENTER, 10, 400, Color.GREEN);

	// Establish the button which will be used to check and send new user info
	// to the respective methods required to update the user info currently in the database
    Button buttonNo = new Button("No");
    setupButtonUI(buttonNo, "Arial", 14, WINDOW_WIDTH-20, 
    		Pos.CENTER, 10, 400, Color.GREEN);
    
    // Sends all previously established settings for the pane to the scene for setup
    userPane.getChildren().addAll(buttonYes, buttonNo); 
    
    Scene userScene = new Scene(userPane, 800, 500);
    userScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    updateStage.setScene(userScene);
    updateStage.show();
    
    // Establishes the button logic for each press
    // DEVELOPER NOTE: Button logic does not refresh or continue after VALID input. If this ever becomes an issue, let Evan know and 
    //                 he will add functionality for repeated valid input.
    buttonYes.setOnAction(new EventHandler<>() {
        public void handle(ActionEvent event) {
        	
        	// Send yes confirmation to database 
        	try {
				AccountDatabase.deleteUser(user);
				
				// Only explicit error in the entire class; Tried everythign I could think of and still an error so I will
				// address this when I finish I=my other class -Evan
				//UpdateAccountInformationGUI update = new UpdateAccountInformationGUI(userPane, user);
				//update.UpdateAccountInformationGUI(userPane, user);
				/**FIX CONSTRUCTOR
				 * 
				 */
				//new UpdateAccountInformationGUI(userPane, user);
			} catch (SQLException e) {
    			System.err.println("Error: " + e.getMessage());

			}
        		            	}
    });
    
    buttonNo.setOnAction(new EventHandler<>() {
        public void handle(ActionEvent event) {
        	
			//UpdateAccountInformationGUI update = new UpdateAccountInformationGUI(userPane, user);
        	/**FIX CONSTRUCTOR
			 * 
			 */
        	//new UpdateAccountInformationGUI(userPane, user);
        		            	}
    });
    
}

}

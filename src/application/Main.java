package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import database.DatabaseManager;	// To start database connection in different package
import database.AccountDatabase;	// To use the account table in database in different package


public class Main extends Application {
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;
	
	// new start function based on PasswordEvaluationGUITestbed
	@Override
	public void start(Stage theStage) throws Exception {
		
		// Communicates to AccountDatabase when application is opened
		DatabaseManager.connectToDatabase();
		
		theStage.setTitle("Group Project");			// Label the stage (a window)
		
		Pane theRoot = new Pane();							// Create a pane within the window
		
		theStage.show();		// Show the stage to the user
        if (AccountDatabase.isDatabaseEmpty()) {
           //theRoot = switchToCreateAccountInformationGUI(); 
        	CreateAccountInformationGUI createAccountGUI = new CreateAccountInformationGUI(theRoot);
        } else {
        	LoginGUI loginGUI = new LoginGUI(theRoot); 
        }
			Scene mainScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene	
			
			theStage.setScene(mainScene);	// Set the scene on the stage
			theStage.show(); //showing current screen (login or register)
		
		
		// When the stage is shown to the user, the pane within the window is visible.  This means
		// that the labels, fields, and buttons of the Graphical User Interface (GUI) are visible 
		// and it is now possible for the user to select input fields and enter values into them, 
		// click on buttons, and read the labels, the results, and the error messages.
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	/**********
	 * Communicates to the AccountDatabase when application is closed
	 */
	@Override
	public void stop() {
		DatabaseManager.closeConnection();
	}
}

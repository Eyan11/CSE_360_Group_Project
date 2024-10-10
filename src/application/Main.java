package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.sql.SQLException;	// To catch errors for database

public class Main extends Application {
	
	/* This is the default start method generated when class was created, feel free to delete
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	*/
	
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;
	
	// new start function based on PasswordEvaluationGUITestbed
	@Override
	public void start(Stage theStage) throws Exception {
		
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
	
		
	//private Scene mainScene () {
		//if (there are already users) then 
		//Button teacherloginButton = new Button("Teacher Login"); //name isntructor
		//teacherloginButton.setOnAction (event -> LoginGUI(theRoot) );
		//Button studentloginButton = new Button("Student Login");
		//studentloginButton.setOnAction (event -> LoginGUI(theRoot) );
		//Button adminloginButton = new Button("Admin Login");
		//adminloginButton.setOnAction (event -> LoginGUI(theRoot) );
		//else (create new account) {
		//Button registerButton = new Button("Register");
		//registerButton.setOnAction (event -> CreateAccountInformation (theRoot) );
		//VBox buttonsBox = new VBox (10, teacherloginButton, studentloginButton, adminloginButton, registerButton);
		//}
		
	//}
	AccountDatabase accountdatabase = new AccountDatabase(); 
	//make ^^ for loginGUI
	//and for createaccountinfogui
/*	if (accountdatabase.isDatabaseEmpty() == true) {
		go to CreateAccountInformationGUI
	}
	else 
	{
		go to LoginGUI
	}
	*/



	
	public static void main(String[] args) {
		
		// Reference to database
		AccountDatabase database = new AccountDatabase();
		
		// Start database connection
		try {
			database.connectToDatabase();
		}
		// Print error if database couldn't connect
		catch (SQLException e) {
			System.err.println("Database error: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		launch(args);
	}
}
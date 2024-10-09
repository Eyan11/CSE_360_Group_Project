package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


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
		
      
		
	/* ADMIN TESTING
		StackPane root = new StackPane(); 
        AdminHome adminHome = new AdminHome(root);  // Call the AdminHome constructor

        Scene scene = new Scene(root, 400, 400);  // Create the scene
        theStage.setTitle("Admin Home");
        theStage.setScene(scene);
        theStage.show();
        
     */ 
		
		
     /* STUDENT INSTRUCTOR HOMEPAGE TESTING
	   StackPane root = new StackPane(); 
        StudentInstructorHomePage studentInstructorHomePage = new StudentInstructorHomePage(root);  // Call the constructor to set up the GUI

        Scene scene = new Scene(root, 400, 300);  // Create the scene
        theStage.setTitle("Student/Instructor Home");
        theStage.setScene(scene);
        theStage.show();
        
        */
		
		/*	 SELECT ROLE TESTING
	   Pane root = new Pane();  // Create the root Pane
        SelectRole selectRole = new SelectRole(root);  // Call the SelectRole constructor to set up the GUI

        Scene scene = new Scene(root, 300, 200);  // Create the scene
        theStage.setTitle("Select Role");
        theStage.setScene(scene);
        theStage.show();
        
        */

	 
       
				
		AccountDatabase.connectToDatabase();		// Communicates to AccountDatabase when application is opened
		
		theStage.setTitle("Group Project");			// Label the stage (a window)
		
		Pane theRoot = new Pane();							// Create a pane within the window
		
		Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene	
		
		theStage.setScene(theScene);						// Set the scene on the stage
		
		theStage.show();									// Show the stage to the user
		
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
        AccountDatabase.closeConnection();
    }
}

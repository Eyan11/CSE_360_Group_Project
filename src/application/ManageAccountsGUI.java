package application;

import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * <p> ManageAccountsGUI. </p>
 * 
 * <p> Description: A JavaFX class responsible for displaying options to manage accounts.</p>
 * 
 * @author Sriram Nesan
 * 
 * @version 1.00		10/29/2024 Phase 2 implementation and documentation
 *  
 */

public class ManageAccountsGUI {

    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;
    public SetupUIElements setupUI;

    public ManageAccountsGUI(Pane theRoot) {
        setupUI = new SetupUIElements();

        Text title = new Text("Manage Accounts");
        title.setFont(new Font("Arial", 24));
        title.setFill(Color.BLACK);
        title.setLayoutX((WINDOW_WIDTH - title.getLayoutBounds().getWidth()) / 2);
        title.setLayoutY(50);

        Button backButton = new Button("<-");
        Button modifyAccountsButton = new Button("Modify Accounts");
        Button inviteUserButton = new Button("Invite User");

        // Button that returns user to previous interface
 		setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
         		Pos.CENTER, 10, 10, false, Color.BLACK);
        setupUI.SetupButtonUI(modifyAccountsButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        setupUI.SetupButtonUI(inviteUserButton, "Arial", 14, 200, Pos.CENTER, 0, 0, false, Color.BLACK);
        
        modifyAccountsButton.setLayoutX(150);
        modifyAccountsButton.setLayoutY(100);
        inviteUserButton.setLayoutX(150);
        inviteUserButton.setLayoutY(150);

        theRoot.getChildren().addAll(title, backButton, modifyAccountsButton, inviteUserButton);

        handleBack(backButton, theRoot);
        modifyAccountsButton(modifyAccountsButton, theRoot);
        inviteUserButton(inviteUserButton, theRoot);
    }

    private void handleBack(Button backButton, Pane theRoot) {
        backButton.setOnAction(event -> {
        	theRoot.getChildren().clear();
            
            Pane newRoot = new Pane();
            AdminHome adminHome = new AdminHome(newRoot); 
            Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
    	    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
    	    currentStage.setScene(newScene); //
       });
    }
    
    private void modifyAccountsButton(Button modifyAccountsButton, Pane theRoot) {
    	modifyAccountsButton.setOnAction(event -> {
    	theRoot.getChildren().clear(); // clear old root
        
        Pane newRoot = new Pane();
        try {
			ModifyAccountsGUI modifyAccounts = new ModifyAccountsGUI(newRoot);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Navigates to ModifyAccountsGUI
        Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
	    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
	    currentStage.setScene(newScene); //
    	});
    }
    
   
    private void inviteUserButton(Button inviteUserButton, Pane theRoot) {
    	inviteUserButton.setOnAction(event -> {
    	theRoot.getChildren().clear(); // clear old root
        
        Pane newRoot = new Pane();
        InviteUserGUI invUser = new InviteUserGUI(newRoot); // Navigates to InviteUserGUI
        Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
	    Stage currentStage = (Stage) theRoot.getScene().getWindow(); // 
	    currentStage.setScene(newScene); //
    
    	});
    }
}

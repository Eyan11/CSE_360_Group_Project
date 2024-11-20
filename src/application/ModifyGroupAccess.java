package application;

import java.sql.SQLException;

import database.AccountDatabase;
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
import javafx.scene.layout.VBox;     
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


//Import groups from CreateGroupGUI
//List groups from CreateGroupGUI & create new variables (strings, labels, text, etc.) in a for loop that adds them to the GUI for ModifyGroupAccess
// groupString = GroupDatabase.GetAllAuthorizedGroupNames
//String[] GroupsArr = groupString.split("+");
public class ModifyGroupAccess {
	
	/**
	 * Variable declaration
	 */
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 1200;
	
	boolean isViewer;
	
	//String groupString = GroupDatabase.getAllAuthorizedGroupNames(isViewer); // !!!!!!!!!!!! Error !!!!!!!!!!!!
	//String[] GroupsArr = groupString.split("+");
	
	// Calls GroupDatabase method which parses all groups and returns their info in one string that is in the needed format for this GUI
	String allGroups = GroupDatabase.getAllGroupInfo();
	
	
	
	/** String inputs */
	private String user; // Passed in from previous step
	
	/** Text to appear as a part of the window (text field indicators, etc. */
	private Label sceneLabel = new Label("Modify Groups Access");
	private Label errorLabel = new Label("Please fill in the required entries (see red)");
	private Label groupLabel = new Label("Group: ");
	private Label roleLabel = new Label("Role: ");
	private Label userLabel = new Label("User: ");
	private Label groupsLabel = new Label(allGroups);
	
	/** Text fields for user input */
	private TextField userText = new TextField();
	
	// Declaration of SetupUIElements Object
	public SetupUIElements setupUI = new SetupUIElements();

	
	ModifyGroupAccess(Pane userPane) { // user passed in from previous step
		Stage updateStage = new Stage();
		updateStage.setTitle("Modify Groups Access");
		
		// Label the email input field with a title just above it, left aligned
				setupLabelUI(errorLabel, "Arial", 14, WINDOW_WIDTH-10, 
						Pos.BASELINE_LEFT, 10, 30, Color.RED);
		// Label the email input field with a title just above it, left aligned
				setupLabelUI(groupLabel, "Arial", 14, WINDOW_WIDTH-10, 
						Pos.BASELINE_LEFT, 10, 50, Color.BLACK);
				// Label the email input field with a title just above it, left aligned
				setupLabelUI(roleLabel, "Arial", 14, WINDOW_WIDTH-10, 
				Pos.BASELINE_LEFT, 100, 50, Color.BLACK);
				// Label the email input field with a title just above it, left aligned
				setupLabelUI(groupsLabel, "Arial", 14, WINDOW_WIDTH-10, 
						Pos.BASELINE_LEFT, 10, 200, Color.BLACK);
		// Text label for error if error occurs
				setupLabelUI(userLabel, "Arial", 14, WINDOW_WIDTH-10, 
						Pos.BASELINE_LEFT, 10, 90, Color.GREEN);
		
		// Establish the text input operand field and when anything changes in the user inputs,
				// the code will process the entire input to ensure that it is valid or an error.
				setupTextUI(userText, "Arial", 18, WINDOW_WIDTH-20,
						Pos.BASELINE_LEFT, 10, 110, true);
				
		// Establish the buttons which will be used to check and send new user info
				// to the respective methods required to update the user info currently in the database.
				// as well as the button that sends the user to the previous page
				Button addButton = new Button("Add");
		        setupButtonUI(addButton, "Arial", 14, WINDOW_WIDTH-20, 
		        		Pos.CENTER, 10, 150, Color.GREEN);
		        
		        Button removeButton = new Button("Remove");
		        setupButtonUI(removeButton, "Arial", 14, WINDOW_WIDTH-20, 
		        		Pos.CENTER, 10, 180, Color.GREEN);
		        
		        Button backButton = new Button("Back");
		        setupUI.SetupButtonUI(backButton, "Arial", 11, 50, 20,
		        		Pos.CENTER, 10, 20, false, Color.BLACK);
		        
		// Establishes combo boxes for groups and roles, which the input user will be added to and classified under
		        ComboBox groupBox = new ComboBox<>();
		        //groupBox.getItems().addAll(GroupsArr);
		        
		        groupBox.setLayoutX(100);
		        groupBox.setLayoutX(100);

		        
		        ComboBox roleBox = new ComboBox();
		        roleBox.getItems().addAll("Admin", "User");
		        
		        roleBox.setLayoutX(165);
		        roleBox.setLayoutX(165);
		        
		     // Retrieve TextField input
            	String userString = userText.getText();

		        
		        
		       /*\ for(int i = 0; i < GroupsArr.length; i++) {
		    		String groupString = GroupsArr[i];
		    		String addString = "Group: " + groupString + "\n"
		    				+ "Type: " + getGroupType
		    				+ "Admins: " + getAdmins + "\n" 
		    				+ "Users: " + getUsers + "\n";
		    		
		    				
		    	}*/ 
		        

		        // Sends all previously established parameters (other than error label) for the pane to the scene for setup
		        userPane.getChildren().addAll(sceneLabel, groupLabel, roleLabel,  groupsLabel, userLabel,userText, removeButton, addButton, backButton); 
		        
		     // Establishes the button logic for each press
		        // DEVELOPER NOTE: Button logic does not refresh or continue after VALID input. If this ever becomes an issue, let Evan know and 
		        //                 he will add functionality for repeated valid input.
		        addButton.setOnAction(new EventHandler<>() {
		            public void handle(ActionEvent event) {
		            	

			
			                // Do error check, if no errors, update info. If errors, output error message above update button and below info input.
			            	// Repeat process for each button push
			            	boolean pass = ErrorMessage(userString);
			            	
			            	// If there are any unfilled entries, alter text box and output message indicating that entries are incomplete
			            	// and highlight all necessary entry boxes
			            	if(pass == false) {

			            		userPane.getChildren().add(errorLabel);
			            		
			            		setupLabelUI(userLabel, "Arial", 14, WINDOW_WIDTH-10, 
			            				Pos.BASELINE_LEFT, 10, 20, Color.RED);
			            		
			            		setupButtonUI(addButton, "Arial", 14, WINDOW_WIDTH-20, 
			                    		Pos.CENTER, 10, 510, Color.RED);
			            		setupButtonUI(removeButton, "Arial", 14, WINDOW_WIDTH-20, 
			                    		Pos.CENTER, 10, 530, Color.RED);
			
			            	}
			            	
			            	// If all necessary entries are filled, reset scene formatting and send info to next step!
			            	// DEVELOPER NOTE: Please let Evan know what steps need to be incorporated so I can add whatever is necessary to pass 
			            	// 				   onto then next part. Thank you.
			            	else {
			            		
			            		//Eliminate error indicator
			            		userPane.getChildren().remove(errorLabel);
			            					            		
			            		setupLabelUI(userLabel, "Arial", 14, WINDOW_WIDTH-10, 
			            				Pos.BASELINE_LEFT, 10, 20, Color.GREEN);
			            		
			            		setupButtonUI(addButton, "Arial", 14, WINDOW_WIDTH-20, 
			                    		Pos.CENTER, 10, 510, Color.GREEN);
			            		setupButtonUI(removeButton, "Arial", 14, WINDOW_WIDTH-20, 
			                    		Pos.CENTER, 10, 530, Color.GREEN);
			            		
			            		// Retrieve selected role (admin, user) from the role combobox, turn it into string
								String selectedRole = (String) roleBox.getSelectionModel().getSelectedItem();
								
								// Retrieve selected group (any amount of any strings) from the group combobox, turn it into string 
								String selectedGroup = (String) groupBox.getSelectionModel().getSelectedItem();

								// If selected role is "Admin", remove Admin of name userString from the group
								if(selectedRole == "Admin") {
									GroupDatabase.addUserToGroup(userString, selectedGroup, false);

								}
								// Else, if selected role is "User", remove User of name userString from the group

								else {
										GroupDatabase.addUserToGroup(userString, selectedGroup, true);
									}
								}
								
			            	
			            		// DEVELOPER NOTE: Critical step v1
			            		// Pass info onto the next part!
			            		/*	TODO - add author and content level arguments
		            			ArticleDatabase.createArticle(headerString, titleString, descriptionString, keywordsString, 
		            					groupsString, bodyString, referencesString);
		            			*/
		            				
		            			/**
		            			 * Transitions to different home pages
		            			 */
								/*// Returns user back to previous page
		            			userPane.getChildren().clear();  // Clear the current root
		            			
		            			Pane newRoot = new Pane();
		            			// Load next step
		            			ManageArticlesGUI manageArticles = new ManageArticlesGUI(newRoot); 
								Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //
								Stage currentStage = (Stage) userPane.getScene().getWindow(); // 
								currentStage.setScene(newScene);
			            	}*/
		            }
		        });
		        
		        removeButton.setOnAction(new EventHandler<>()
				{
					public void handle(ActionEvent event) 
					{	
						
						// Retrieve selected role (admin, user) from the role combobox, turn it into string
						String selectedRole = (String) roleBox.getSelectionModel().getSelectedItem();
						
						// Retrieve selected group (any amount of any strings) from the group combobox, turn it into string 
						String selectedGroup = (String) groupBox.getSelectionModel().getSelectedItem();

						// If selected role is "Admin", remove Admin of name userString from the group
						if(selectedRole == "Admin") {
							GroupDatabase.removeUserFromGroup(userString, selectedGroup, false);

						}
						// Else, if selected role is "User", remove User of name userString from the group

						else {
								GroupDatabase.removeUserFromGroup(userString, selectedGroup, true);
							}
						
						
						/**
            			 * Transitions to different home pages
            			 */
						/*// Returns user back to previous page
						userPane.getChildren().clear();  // Clear the current root
						// Create new pane for next interface
						Pane newRoot = new Pane();
						ManageArticlesGUI manageArticles = new ManageArticlesGUI(newRoot); // Returns user to previous interface
						Scene newScene = new Scene(newRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // creates new scene
					    Stage currentStage = (Stage) userPane.getScene().getWindow();
					    currentStage.setScene(newScene); // sets new scene*/
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
			private boolean ErrorMessage(String userString) {
				
				boolean filled = true; // Checks of all necessary entries are filled. Starts as false (by default). If any parameters are not filled, stays false.
								// Otherwise, returns as true!
				/*if( (email == "") || (first == "") || (middle == "") || (last == "") ) {
					// Show error above button saying "All necessary text boxes must be filled! (see red)
					// Change relevant titles to red (gonna have to break if statements up)	 
				
				}*/
				// If any entry is empty, filled = false
				if(userString == "") {
					filled = false;
				}
					
				// Else, filled = true
				return filled;
			}

}

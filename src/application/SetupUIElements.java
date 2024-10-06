package application;

import javafx.scene.control.Label; // For Label obj
import javafx.scene.control.TextField; // For TextField obj
import javafx.scene.control.Button; // For Button obj
import javafx.scene.control.CheckBox; // For CheckBox obj
import javafx.scene.text.Font; // To set font and font size
import javafx.geometry.Pos; // For Pos obj (vector2 coordinate used to describe position)
import javafx.scene.paint.Color; // To set color of UI elements

public class SetupUIElements {	
	
	/*
	
	When using this class, don't forget to add all UI elements to root, otherwise they won't appear
	theRoot.getChildren().addAll(label, textField, button, checkBox);
	
	Also don't forget to import the javaFX libraries you need, see above imports for examples
	
	Finally, to use this class, simply create an object of it, then you can reference all the public methods
	public SetupUIElements setupUI = new SetupUIElements;
	
	*/

	/**********
	 * Protected method to initialize the standard fields for a Label
	 */
	public void SetupLabelUI(Label l, String font, double fontSize, double minWidth, Pos pos, double x, double y, Color color) {
		l.setFont(Font.font(font, fontSize));	// Font style and size
		l.setMinWidth(minWidth);	// Minimum width if you want Label to be larger than text inside of it
		// Pos.CENTER is recommended
		l.setAlignment(pos);		// How text within the Label is aligned
		l.setLayoutX(x);			// X coordinate of Label center in window
		l.setLayoutY(y);			// Y coordinate of Label center in window
		l.setTextFill(color);		// Color of label text
		
		/* Example
		Label label = new Label("Test Label");
		setupUI.SetupLabelUI(label, "Arial", 14, WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 10, 50, Color.BLUE);
		*/
	}
	
	/**********
	 * Protected method to initialize the standard fields for a TextField
	 */
	public void SetupTextFieldUI(TextField t, String font, double fontSize, double minWidth, 
		Pos pos, double x, double y, boolean isEditable) {
		
		t.setFont(Font.font(font, fontSize));	// Font style and size
		t.setMinWidth(minWidth);	// Minimum width of TextField
		// Pos.BASELINE_LEFT is recommended
		t.setAlignment(pos);		// How text within the TextField is aligned 
		t.setLayoutX(x);			// X coordinate of TextField center in window
		t.setLayoutY(y);			// Y coordinate of TextField center in window
		// if not editable, then TextField and TextField is NOT grayed out and appears as normal
		t.setEditable(isEditable);	// Allows text box to be typed in or not
		
		/*
		textField textField = new TextField("");
		setupUI.SetupTextFieldUI(textField, "Arial", 14, WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 10, 100, false);
		*/
	}
	
	/**********
	 * Protected method to initialize the standard fields for a Button
	 */
	public void SetupButtonUI(Button b, String font, double fontSize, double minWidth, 
		Pos pos, double x, double y, boolean isDisabled, Color color) {
		
		b.setFont(Font.font(font, fontSize));	// Font style and size
		b.setMinWidth(minWidth);	// Minimum width of Button
		// Pos.CENTER is recommended
		b.setAlignment(pos);		// How text within the Button should is aligned
		b.setLayoutX(x);			// X coordinate of Button center in window
		b.setLayoutY(y);			// Y coordinate of Button center in window
		// if disabled, then Button and Button text is automatically grayed out
		b.setDisable(isDisabled);	// Disabled or enabled the button from being clicked
		b.setTextFill(color);		// Color of Button text
		
		/* Example
		Button button = new Button("Test Buttton");
		setupUI.SetupButtonUI(button, "Arial", 14, WINDOW_WIDTH-10, Pos.CENTER, 10, 150, true, Color.RED);
		*/
	}
	
	/**********
	 * Protected method to initialize the standard fields for a check box
	 */
	public void SetupCheckBoxUI(CheckBox c, String font, double fontSize, double width, double height, 
			Pos pos, double x, double y, boolean isDisabled, Color color) {
		
		c.setFont(Font.font(font, fontSize));	// Font style and size
		c.setPrefSize(width, height); 	// Set width and height of CheckBox
		// Pos.TOP_LEFT is recommended
		c.setAlignment(pos);			// How the text next to CheckBox is aligned
		c.setLayoutX(x);				// X coordinate of CheckBox center in window
		c.setLayoutY(y);				// Y coordinate of CheckBox center in window
		// if disabled, then CheckBox and CheckBox text is automatically grayed out
		c.setDisable(isDisabled);		// Disabled or enabled the CheckBox from being checked
		c.setTextFill(color);			// Color of CheckBox text
		
		/* Example
		CheckBox checkBox = new CheckBox("Test CheckBox");
		setupUI.SetupCheckBoxUI(checkBox, "Arial", 14, WINDOW_WIDTH-10, 20, Pos.TOP_LEFT, 10, 200, true, Color.GREEN);
		*/
	}
}

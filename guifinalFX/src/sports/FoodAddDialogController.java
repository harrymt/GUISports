package sports;

import org.joda.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sports.model.Food;

/**
 * Creates a new Food for the logged in user.
 * 
 * @author hxm02u
 * @version 1.0.0
 */
public class FoodAddDialogController {

	@FXML private TextField nameField;
	@FXML private TextField kjEatenField;
    @FXML private TextField dateField;
    @FXML private TextField carbohydrateField;
    @FXML private TextField fatField;
    @FXML private TextField proteinField;
	private Stage dialogStage;
	private MainApp mainApp;
	
	/**
	 * Gets todays date and places it into the date field.
	 */
	@FXML
	private void initialize() {
		dateField.setText(new LocalDate().toString());
	}
	
	/**
	 * Sets the application so that we can use the same userData here.
	 */
	public void setApp(MainApp application) {
		this.mainApp = application;
	}
	
	/**
	 * @param dialogStage sets the stage to present a dialog window, so the main window
	 * doesn't close
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	

	/**
	 * @param event when the user clicks the save user button.
	 * Requiring the input is valid, it creates a new Food to 
	 * the logged in user
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			Food newfood = new Food((nameField.getText()),(Double.parseDouble(kjEatenField.getText())), (Double.parseDouble(fatField.getText())), (Double.parseDouble(proteinField.getText())), (Double.parseDouble(carbohydrateField.getText())), dateField.getText());
			mainApp.getUserLoggedIn().addNewFood(newfood);	
			UserOverviewController.getFoodData().add(newfood);
			dialogStage.close();
		}
	}

	/**
	 * Closes the window if the user clicks cancel
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (nameField.getText() == null || nameField.getText().length() == 0) {
			errorMessage += "No valid name!\n";
		}

		if (kjEatenField.getText() == null	|| kjEatenField.getText().length() == 0) {
			errorMessage += "No valid kj!\n";
		} else {
			try {
				Double.parseDouble((kjEatenField.getText()));
			} catch (Exception e) {
				errorMessage += "No valid kj!\n";
			}
		}
		if (dateField.getText() == null || dateField.getText().length() == 0 || !validateDate()) {
			errorMessage += "Invalid Date!\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			Dialogs.showErrorDialog(dialogStage, errorMessage,
					"Please correct invalid fields", "Invalid Fields");
			return false;
		}
	}
	
    /**
     * Checks the date to make sure its valid
     * @return true if the date is in the format yyyy-mm-dd
     */
    private boolean validateDate() {
    	String d = dateField.getText();
    	if (d == null || d.isEmpty()) {
    		return false;
    	} else if (d.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
    		return true;
    	} else {
    		return false;
    	}
    }
}

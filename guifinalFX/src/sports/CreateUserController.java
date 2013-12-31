package sports;

import org.joda.time.LocalDate;
import sports.model.User;
import sports.model.Weight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created a new user and passes it into the MainApp userData which is the
 * list of all created and loaded users.
 * 
 * @author hxm02u
 * @version 1.0.0
 */
public class CreateUserController{

	@FXML private Button buttonSaveUser;
	@FXML private TextField heightId;
	@FXML private TextField targetId;
	@FXML private TextField userId;
	@FXML private TextField weightId;

	private Stage dialogStage;
	private MainApp mainApp;

	/**
	 * Sets the application so that we can use the same userData here.
	 */
	public void setApp(MainApp application) {
		this.mainApp = application;
	}
	
	/**
	 * 
	 * @param dialogStage sets the stage to present a dialog window, so the main window
	 * doesn't close
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * @param event when the user clicks the save user button.
	 * Requiring the input is valid, it creates a new user in userData,
	 * the list of all created and loaded users.
	 * 
	 */
	@FXML
	void processSaveUser(ActionEvent event) {
		if (isInputValid()) {
			User u = new User(userId.getText(),Integer.parseInt(targetId.getText()),Integer.parseInt(heightId.getText()));
			u.addNewWeight(new Weight(Integer.parseInt(weightId.getText()), (new LocalDate().toString())));
			mainApp.userData.add(u);
			UserOverviewController.getUserTableList().add(u);
			dialogStage.close();
		}
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (userId.getText() == null || userId.getText().length() == 0) {
			errorMessage += "No valid username!\n";
		}
		if (heightId.getText() == null || heightId.getText().length() == 0) {
			errorMessage += "No valid height!\n";
		} else {
			try {
				Integer.parseInt(heightId.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid height (must be an integer)!\n";
			}
		}
		if (weightId.getText() == null || weightId.getText().length() == 0) {
			errorMessage += "No valid weight!\n";
		} else {
			try {
				Integer.parseInt(weightId.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid weight (must be an integer)!\n";
			}
		}

		if (targetId.getText() == null || targetId.getText().length() == 0) {
			errorMessage += "No valid target!\n";
		} else {
			try {
				Integer.parseInt(targetId.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid target (must be an integer)!\n";
			}
		}
		
		if (errorMessage.length() == 0) {
			return true;
		} else {
			Dialogs.showErrorDialog(dialogStage, errorMessage,
					"Please correct invalid fields", "Invalid Fields");
			return false;
		}
	}

}
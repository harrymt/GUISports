package sports;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.InputStream;
import sports.model.User;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main Application. This class handles navigation and user session.
 * 
 * @author hxm02u
 * @version 1.0.0
 */
public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane layout;
	public User userLoggedIn;
	public ArrayList<User> userData = new ArrayList<User>();
	private ObservableList<User> userDataObservable;

	/**
	 * MainApp constructor.
	 * Creates userData, the list of users for the program and
	 * creates userLoggedIN, the user which holds all the Exercises,
	 * Weights and Food 
	 */
	public MainApp() {
		userLoggedIn = new User();
		userData = new ArrayList<User>();
	}

	/**
	 * @return userData the list users in the system
	 */
	public ArrayList<User> getUserData() {
		return userData;
	}

	/**
	 * @param u the User to be added to the system
	 */
	public void setUserData(ArrayList<User> u) {
		this.userData = u;
	}
	
	/**
	 * @return userDataObservable the data which populates the 
	 * user list table
	 */
	public ObservableList<User> getUserDataObservable() {
		return userDataObservable;
	}

	/**
	 * @param uo the User data to be set on the user list table
	 */
	public void setUserDataObservable(ObservableList<User> uo) {
		this.userDataObservable = uo;
	}
	
	/**
	 * @return a User object with all of the Exercises,
	 *  Weights and Foods in.
	 */
	public User getUserLoggedIn() {
		return userLoggedIn;
	}
	
	/**
	 * @param u a user which is replacing the user,
	 * by logging the previous one out and @param u in
	 */
	public void setUserLoggedIn(User u) {
		this.userLoggedIn = u;
	}
	
	/**
	 * For the other windows to refer back to this one
	 * @return primaryStage the main window as a stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/**
	 * Launches the application and starts the class
	 * @param args prints the arguments back to the java console
	 */
	public static void main(String[] args) {
		Application.launch(MainApp.class, (java.lang.String[]) null);
	}

	/**
	 * Creates the first window, making it not re-sizable
	 * 
	 * @param stage passes in a stage object to create the first window
	 */
	@Override
	public void start(Stage stage) {
		try {
			primaryStage = stage;
			primaryStage.setTitle("Sports App");
			primaryStage.setResizable(false);
			gotoMain();
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Creates an overview controller, a window, which is sized to screen and the 
	 * 	application is set to.
	 */
	public void gotoMain() {
		try {		
			FXMLLoader loader = new FXMLLoader();
			InputStream in = MainApp.class.getResourceAsStream("view/UserOverview.fxml");
			loader.setBuilderFactory(new JavaFXBuilderFactory());
			loader.setLocation(MainApp.class.getResource("view/UserOverview.fxml"));
			layout = (AnchorPane) loader.load(in);
			Scene scene = new Scene(layout);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			UserOverviewController overview = loader.getController();
			overview.setApp(this);		
			} catch (Exception ex) {
				Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	

	/**
	 * Creates and displays a create user dialog, while not removing the main window
	 * 
	 */
	public void showCreateUserDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/CreateUser.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Create User");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			CreateUserController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setApp(this);
			dialogStage.showAndWait();
		} catch (Exception ex) {
			Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	/**
	 * Creates and displays a create exercise dialog, when the user exercise option is selected in the
	 * drop down menu, while not removing the main window
	 * 
	 */
	public void showExerciseAddDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ExerciseNewDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("New Exercise");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			ExerciseAddDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setApp(this);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
  	 * Creates and displays a create food dialog, when the user food option is selected in the
	 * drop down menu, while not removing the main window
	 * 
	 */
	public void showFoodAddDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/FoodNewDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("New Food");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			FoodAddDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setApp(this);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

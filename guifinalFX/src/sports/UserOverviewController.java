package sports;


import util.FileUtil;
import java.io.File;
import java.net.URL;
import sports.model.Food;
import sports.model.User;
import sports.model.Exercise;
import sports.model.Weight;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import com.thoughtworks.xstream.XStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
/**
 * Main Application class. This handles the navigation around each
 * window and the saving and loading of new users.
 * 
 * @author hxm02u
 * @version 1.0.0
 */

public class UserOverviewController extends AnchorPane implements Initializable {
    @FXML private TextField textFieldUserName;
    @FXML private TextField textFieldYouHeight;
    @FXML private TextField textFieldYouWeight;
    @FXML private TextField textFieldYouTarget;
    @FXML private TextField textFieldBMIResult;
    @FXML private MenuButton menuButtonDayCreate;
    @FXML private MenuItem menuItemDayFood;
    @FXML private MenuItem menuItemDayExercise;
    @FXML private TextField dateFrom;
    @FXML private TextField dateTo;
    @FXML private Button saveButton;
    @FXML private TableView<Exercise> exerciseTable;
    @FXML private TableColumn<Exercise, String> exNameColumn;
    @FXML private TableColumn<Exercise, String> exKJColumn;
    @FXML private TableColumn<Exercise, LocalDate> exDateColumn;
    @FXML private TableView<Food> foodTable;
    @FXML private TableColumn<Food, String> foodNameColumn;
    @FXML private TableColumn<Food, Double> kjEatenColumn;
    @FXML private TableColumn<Food, LocalDate> foodDateColumn;
    @FXML private TableColumn<Food, Double> fatColumn;
    @FXML private TableColumn<Food, Double> proteinColumn;
    @FXML private TableColumn<Food, Double> carbohydratesColumn;
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> userColumn;
    @FXML private TextField textFieldWeeklyTarget;
    @FXML private TextField textFieldtodaysWeight;
    @FXML private TabPane tabPaneAll;
    @FXML private Tab tabActivities;
    @FXML private Tab tabStat;
    @FXML private Tab tabYou;    
	private MainApp mainApp;
	private File tempFile;
	
	private static ObservableList<Exercise> exerciseData;
	private static ObservableList<Food> foodData;
	private static ObservableList<User> userTableList;
	
	private ObservableList<Exercise> exerciseFilteredData = FXCollections.observableArrayList();
	private ObservableList<Food> foodFilteredData = FXCollections.observableArrayList();
	private static ObservableList<Weight> weightData = FXCollections.observableArrayList();
	private ObservableList<Tab> tabsHidden = FXCollections.observableArrayList();

	
	/**
	 * Starts the class, by hiding the tabs not in use so the user can focus on the main one,
	 * this directs them towards the login button.
	 * 
	 * Each column points towards there class value.
	 * 
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		removeTabs();
		exNameColumn.setCellValueFactory(new PropertyValueFactory<Exercise, String>("nameExercise"));
		exKJColumn.setCellValueFactory(new PropertyValueFactory<Exercise, String>("kjBurned"));
		exDateColumn.setCellValueFactory(new PropertyValueFactory<Exercise, LocalDate>("date"));
		foodNameColumn.setCellValueFactory(new PropertyValueFactory<Food, String>("nameFood"));
		fatColumn.setCellValueFactory(new PropertyValueFactory<Food, Double>("fat"));
		proteinColumn.setCellValueFactory(new PropertyValueFactory<Food, Double>("protein"));
		carbohydratesColumn.setCellValueFactory(new PropertyValueFactory<Food, Double>("carbohydrate"));
		kjEatenColumn.setCellValueFactory(new PropertyValueFactory<Food, Double>("kjEaten"));
		foodDateColumn.setCellValueFactory(new PropertyValueFactory<Food, LocalDate>("date"));
		exerciseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		foodTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
		dateTo.setText(new LocalDate().toString());
		dateFrom.setText("2013-11-28");
	}

/*   You  Tab  */
	
    /**
     * @param mouse event click button Update, weight.
     * Updates the userLoggedIn weight and the observable list viewing it.
     * 
     */
    @FXML
    void updateTodaysWeight(ActionEvent event) {
    	Weight w = new Weight(Double.parseDouble(textFieldtodaysWeight.getText()), (new LocalDate()).toString());
    	mainApp.getUserLoggedIn().addNewWeight(w); 
    	Weight toBeAddedWeight = mainApp.userLoggedIn.getListofWeights().get(mainApp.userLoggedIn.getListofWeights().size() - 1);
        textFieldtodaysWeight.setText(toBeAddedWeight.getWeight() + "");
        textFieldYouWeight.setText(toBeAddedWeight.getWeight() + "");
        textFieldBMIResult.setText(mainApp.userLoggedIn.calculateBMI() + "");
    }

    /**
     * @param mouse event click button Update, Target
     * Updates the userLoggedIn target and the observable list viewing it.
     */
    @FXML
    void updateWeeklyTarget(ActionEvent event) {
    	mainApp.getUserLoggedIn().setTarget(Integer.parseInt(textFieldWeeklyTarget.getText()));
        textFieldWeeklyTarget.setText(mainApp.userLoggedIn.getTarget() + "");
        textFieldYouTarget.setText(mainApp.userLoggedIn.getTarget() + "");
    }
    /**
     * @param event click button display date, when the user has selected
     * to and from dates they press the button which process the items in
     * the tables and selects the data in those dates.
     */
    @FXML
    void processDisplayDate(ActionEvent event) {
    	exerciseTable.setItems(getExerciseData());
    	foodTable.setItems(getFoodData());
    	updateFilteredData();
    	exerciseTable.setItems(getExerciseFilteredData());
    	foodTable.setItems(getFoodFilteredData());
    }
    
    /**
     * Gets the users which are inbetween the specified dates.
     */
    void updateFilteredData() {
        exerciseFilteredData.clear();
        foodFilteredData.clear();
        if(validateDate()){
        	for (Exercise ex : mainApp.getUserLoggedIn().getListofExercise()) {
            	if (matchesFilter(ex)) {
                	exerciseFilteredData.add(ex);
            	}
        	}
        	for (Food f : mainApp.getUserLoggedIn().getListofFood()) {
        		if (matchesFilter(f)) {
        			foodFilteredData.add(f);
        		}
        	}
        }
        reapplyTableSortOrder();
    }
    
    /**
     * Checks with a regex if the date is valid.
     * @return true if valid date
     */
    private boolean validateDate() {
    	String d = dateTo.getText();
    	if (d == null || d.isEmpty()) {
    		return false;
    	} else if (d.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
    		return true;
    	} else {
    		return false;
    	}
    }

    /**
     * Refreshers the table sort order after filtered data has been applied.
     * 
     */
    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Exercise, ?>> sortOrder = new ArrayList<>(exerciseTable.getSortOrder());
        exerciseTable.getSortOrder().clear();
        exerciseTable.getSortOrder().addAll(sortOrder);
        ArrayList<TableColumn<Food, ?>> sortOrder2 = new ArrayList<>(foodTable.getSortOrder());
        foodTable.getSortOrder().clear();
        foodTable.getSortOrder().addAll(sortOrder2);
    }
    
	/**
	 * @param event mouse click button New User, this creates a new user
	 * dialog and updates the userlists with the data from that dialog.
	 */
	@FXML
	void processCreateUser(ActionEvent event) {
		setUserTableList(mainApp.userData);
		mainApp.showCreateUserDialog();
		userTable.setItems(getUserTableList());
		userColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username")); 
	}
	
	/**
	 * 
	 */
	@FXML
	private void openUser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("User file");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));

		// Show save file dialog
		tempFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

		if (tempFile != null) {
			loadUserDataFromFile(tempFile);
		}
	}
	
	/**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */
	@SuppressWarnings("unchecked")
	public void loadUserDataFromFile(File file) {
		  XStream xstream = new XStream();
		  /* note on macOSX the above method doesnt work */
		  xstream.alias("user", User.class);
		  try {
		    String xml = FileUtil.readFile(file);
		    ArrayList<User> userListInput = (ArrayList<User>) xstream.fromXML(xml);
		    mainApp.userData.clear();
		    mainApp.userData.addAll(userListInput);
			setUserTableList(mainApp.userData);
			userTable.setItems(getUserTableList());
			userColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username")); 
		    setUserFilePath(file);
		  } catch (Exception e) { 
		    Dialogs.showErrorDialog(mainApp.getPrimaryStage(),
		        "Could not load data from file:\n" + file.getPath(),
		        "Could not load data", "Error", e);
		    e.printStackTrace();
		  }
	}
	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	@FXML
	void processUserSave(ActionEvent event) {	
        FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        if (file != null) {
        	if (!file.getPath().endsWith(".xml")) {
        		file = new File(file.getPath() + ".xml");
        	}
        	saveUserDataToFile(file);
        }
	}
	/**
	 * 
	 * 
	 * @param event mouse click button, when the user selects the login button the tabs are enabled, the 
	 * user table will get the userloggedIn and the tables will be populated with 
	 * that user logged in data and the labels will be populated with that data.
	 */
	@FXML
    void processLogin(ActionEvent event) {
    	User u = userTable.getSelectionModel().getSelectedItem();
    	mainApp.setUserLoggedIn(u);
    	if(u==null) { return;}
    	setFoodData(mainApp.getUserLoggedIn().getListofFood());
 		setExerciseData(mainApp.getUserLoggedIn().getListofExercise());
 		setExerciseFilteredData(mainApp.getUserLoggedIn().getListofExercise());
 		setFoodFilteredData(mainApp.getUserLoggedIn().getListofFood());
 		exerciseTable.setItems(exerciseFilteredData);
 		foodTable.setItems(foodFilteredData); 
 		exerciseTable.setItems(getExerciseData()); // Sets the table to the observable list	
 		foodTable.setItems(getFoodData()); // Sets the table to the observable list		
 		
        textFieldUserName.setText(mainApp.userLoggedIn.getUsername().toString());
        textFieldYouHeight.setText(mainApp.userLoggedIn.getHeight() + "");
        textFieldBMIResult.setText(mainApp.userLoggedIn.calculateBMI() + "");
        String w = mainApp.userLoggedIn.getListofWeights().get(mainApp.userLoggedIn.getListofWeights().size() - 1).getWeight() + "";
        textFieldYouWeight.setText(w);
        textFieldYouTarget.setText(mainApp.userLoggedIn.getTarget() + "");
        textFieldWeeklyTarget.setText(mainApp.userLoggedIn.getTarget() + "");
        textFieldtodaysWeight.setText(w);
        enableTabs();

    }
	/**
	 * Removes the tabs that want to be hidden at start.
	 */
	public void removeTabs() {
		for (Tab t: tabPaneAll.getTabs()) {
			if (t.getText().equals("Activities")) {
				this.tabsHidden.add(t);
			}
		}
		for (Tab t: this.tabsHidden) {
			tabPaneAll.getTabs().remove(t);
		}
	}	
	
	/**
	 * Enables the tabs
	 */
	private void enableTabs() {
		for (Tab t: this.tabsHidden) {
			tabPaneAll.getTabs().add(t);
		}	
		tabsHidden = FXCollections.observableArrayList();
	}
	
	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void saveUserDataToFile(File file) {
		XStream xstream = new XStream();
		xstream.alias("user", User.class);

		// Convert ArrayList to a normal ArrayList
		ArrayList<User> userList = new ArrayList<>(mainApp.userData);

		String xml = xstream.toXML(userList);
		try {
			FileUtil.saveFile(xml, file);

			setUserFilePath(file);
		} catch (Exception e) { // catches ANY exception
			Dialogs.showErrorDialog(mainApp.getPrimaryStage(),
					"Could not save data to file:\n" + file.getPath(),
					"Could not save data", "Error", e);
		}
	}
	
	/**
	 * Sets the file path of the currently loaded file.
	 * The path is persisted in the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setUserFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
			mainApp.getPrimaryStage().setTitle("SportsApp - " + file.getName());
		} else {
			prefs.remove("filePath");

			// Update the stage title
			mainApp.getPrimaryStage().setTitle("SportsApp");
		}
	}


/* Activities Tab */
	/**
     * Returns true if the date matches the current filter.
     * @param ex the exercise which contains the dates
     * @return the interval between the dates
     */
    private boolean matchesFilter(Exercise ex) {
        DateTime endD = DateTime.parse(dateTo.getText()).plusDays(1);
        DateTime startD = DateTime.parse(dateFrom.getText());
        Interval interval = new Interval(startD, endD);
        DateTime exDate = DateTime.parse(ex.getDate().toString());
        return (interval.contains(exDate));
    }
	/**
     * Returns true if the date matches the current filter.
     * @param f the food which contains the dates
     * @return the interval between the dates
     */
    private boolean matchesFilter(Food f) {
        DateTime endD = DateTime.parse(dateTo.getText()).plusDays(1);
        DateTime startD = DateTime.parse(dateFrom.getText());
        Interval interval = new Interval(startD, endD);
        DateTime exDate = DateTime.parse(f.getDate().toString());
        return (interval.contains(exDate));
    }
    
	/**
	 * Creates a new exercise when the user selects the dropdown menu item.
	 */
	@FXML
	private void handleNewExercise() {
		mainApp.showExerciseAddDialog();
		setExerciseData(mainApp.getUserLoggedIn().getListofExercise());
 		exerciseTable.setItems(getExerciseData());
	}
	
	/**
	 * Creates a new exercise when the user selects the dropdown menu item.
	 */
	@FXML
	private void handleNewFood() {
		mainApp.showFoodAddDialog();
		setFoodData(mainApp.getUserLoggedIn().getListofFood());
 		foodTable.setItems(getFoodData());
	}
	
	/**  
	 * When the user selects the dropdown menu the dialog is opened
     * @param event
     */
	@FXML
	void displayFoodWindow(ActionEvent event) {
		mainApp.showFoodAddDialog();
	}
	
	/**  
	 * When the user selects the dropdown menu the dialog is opened
     * @param event
     */
	@FXML
	void displayExerciseWindow(ActionEvent event) {
		mainApp.showExerciseAddDialog();
	}

	
	/**
	 * @param exf adds to the exercise filtered data list
	 */
	public void setExerciseFilteredData(ArrayList<Exercise> exf) {
		exerciseFilteredData = FXCollections.observableArrayList(exf);
	}
	/**
	 * @param f adds to the foodData list
	 */
	public void setFoodData(ArrayList<Food> f) {
		foodData = FXCollections.observableArrayList(f);
	}
	/**
	 * @param ex adds to the exerciseData list
	 */
	public void setExerciseData(ArrayList<Exercise> ex) {
		exerciseData = FXCollections.observableArrayList(ex);
	}
	/**
	 * @param w adds to the weightData list
	 */
	public void setWeightData(ArrayList<Weight> w) {
		weightData = FXCollections.observableArrayList(w);
	}
	/**
	 * @param user adds to the user data list
	 */
	public void setUserTableList(ArrayList<User> user) {
		userTableList = FXCollections.observableArrayList(user);
	}
	/**
	 * @return exercise data
	 */
	public static ObservableList<Exercise> getExerciseData() {
		return exerciseData;
	}
	/**
	 * @return food data
	 */
	public static ObservableList<Food> getFoodData() {
		return foodData;
	}
	/**
	 * @return the user table 
	 */
	public static ObservableList<User> getUserTableList() {
		return userTableList;
	}
	/**
	 * @return the weightdata
	 */
	public static ObservableList<Weight> getWeightTableList() {
		return weightData;
	}
	/**
	 * @return the food filtered
	 */
	public ObservableList<Food> getFoodFilteredData() {
		return foodFilteredData;
	}
	/**
	 * @return the exercises filtered
	 */
	public ObservableList<Exercise> getExerciseFilteredData() {
		return exerciseFilteredData;
	}
	/**
	 * @param f the filtered food to be added
	 */
	public void setFoodFilteredData(ArrayList<Food> f) {
		foodFilteredData = FXCollections.observableArrayList(f);
	}
	
	/**
	 * @param application which is set to reference the logged on user
	 */
	public void setApp(MainApp application) {
		this.mainApp = application;
	}

}
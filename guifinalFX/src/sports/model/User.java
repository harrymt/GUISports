package sports.model;

import java.util.ArrayList;

/**
 * Food class for storing data on daily Food eaten
 * 
 * @author hxm02u
 * @version 1.0.0
 */ 
public class User {
	
	private ArrayList<Food> listofFood = new ArrayList<Food>();
	private ArrayList<Exercise> listofExercise = new ArrayList<Exercise>();
	private ArrayList<Weight> listofWeights = new ArrayList<Weight>();
	
	private double height;
	private String username;
	private int target;

	   public User() {
	   
	   }
    /**
     * @param user stores the name of the user
     */
    public User(String user) {
        this.username = user;
    }
    
    /**
     * @param user name of the user
     * @param target that the user reaches
     * @param height of the user
     */
    public User(String user, int target, double height) {
    	this.username = user;
    	this.target = target;
    	this.height = height;
    }

	/**
	 * @return an Array List of Weights
	 */
	public ArrayList<Weight> getListofWeights() {
		return listofWeights;
	}

	/**
	 * @param w weight of the user,
	 * adds to the list of weights
	 */
	public void addNewWeight(Weight w) {
		listofWeights.add(w);
	}
	
    /**
     * Creates a new food.
     * By adding @param f to the list of food
     * 
     */
    public void addNewFood(Food f) {
    	listofFood.add(f);
    }
    
    /**
     * Creates a new exercise.
     * By adding @param ex to the list of exercises
     */
    public void addNewExercise(Exercise ex) {
    	listofExercise.add(ex);
    }

	/**
	 * @return the height of the user
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height, sets the height of the user
	 */
	public void setHeight(long height) {
		this.height = height;
	}

	/**
	 * @return the name of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param name the name to updated
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the target of the user
	 */
	public int getTarget() {
		return target;
	}

	/**
	 * @param target the target for the user to get to
	 */
	public void setTarget(int target) {
		this.target = target;
	}

	/**
	 * BMI = Weight in Kilogrammes / Height in Meters squared
	 * 
	 * @return the BMI of the user calculated using thier weight and height
	 */
	public double calculateBMI() {
		double bmi = listofWeights.get(listofWeights.size() - 1).getWeight() / ((height / 100) * (height / 100));
		return bmi = (Math.round(bmi * 10.0)) / 10.0;
	}

	/**
	 * @return the listofFood
	 */
	public ArrayList<Food> getListofFood() {
		return listofFood;
	}

	/**
	 * @param f the food to add to the list of foods
	 */
	public void setListofFood(Food f) {
		this.listofFood.add(f);
	}

	/**
	 * @return the listofExercise
	 */
	public ArrayList<Exercise> getListofExercise() {
		return listofExercise;
	}

	/**
	 * @param ex the exercise to add to the list of exercises
	 */
	public void setListofExercise(Exercise ex) {
		this.listofExercise.add(ex);
	}


}

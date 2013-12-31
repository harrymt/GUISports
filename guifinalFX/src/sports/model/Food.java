package sports.model;

import org.joda.time.LocalDate;

/**
 * Food class for storing data on daily Food eaten
 * 
 * @author hxm02u
 * @version 1.0.0
 */
public class Food {
	private double kjEaten;
	private String nameFood;
	private LocalDate date = new LocalDate();
	private double fat;
	private double protein;
	private double carbohydrate;

	/**
	 * Food constructor
	 * 
	 * @param n the name of the Food eaten
	 * @param kj the amount to KJ it was worth
	 * @param f the amount of fat in the Food
	 * @param p the amount of protein in the Food
	 * @param c the amount of carbohydrate in the food
	 * @param d the date the food was consumed, stored as a LocalDate valued
	 *            converted from a String()
	 */
	public Food(String name, double kj, double fat, double protein, double carbs, String date) {
		this.nameFood = name;
		this.kjEaten = kj;
		this.fat = fat;
		this.protein = protein;
		this.carbohydrate = carbs;
		this.date = LocalDate.parse(date);
	}

	/**
	 * @return fat the amount of it in the Food
	 */
	public double getFat() {
		return fat;
	}

	/**
	 * @param f the amount of fat in the Food
	 */
	public void setFat(double f) {
		this.fat = f;
	}
	/**
	 * @return protein the amount of it in the Food
	 */
	public double getProtein() {
		return protein;
	}

	/**
	 * @param p the amount of protein in the Food
	 */
	public void setProtein(double p) {
		this.protein = p;
	}
	/**
	 * @return carbohydrate the amount of it in the Food
	 */
	public double getCarbohydrate() {
		return carbohydrate;
	}

	/**
	 * @param carbohydrate the amount of carbohydrate in the Food
	 */
	public void setCarbohydrate(double c) {
		this.carbohydrate = c;
	}

	/**
	 * @return kjEaten the amount consumed in the Food
	 */
	public double getKjEaten() {
		return kjEaten;
	}

	/**
	 * @param kj the amount of kj in the Food
	 */
	public void setKjEaten(double kj) {
		this.kjEaten = kj;
	}

	/**
	 * @return nameFood the name of the food the user ate
	 */
	public String getNameFood() {
		return nameFood;
	}

	/**
	 * @param u the name of the food the user ate
	 */
	public void setNameFood(String u) {
		this.nameFood = u;
	}

	/**
	 * @return the date the Food was eaten
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param d the date the food is eaten
	 */
	public void setDate(LocalDate d) {
		this.date = d;
	}

}

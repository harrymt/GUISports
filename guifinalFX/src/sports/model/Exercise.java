package sports.model;

import org.joda.time.LocalDate;

/**
 * Exercise class for storing data on daily Exercises performed
 * 
 * @author hxm02u
 * @version 1.0.0
 */
public class Exercise {
	private double kjBurned;
	private String nameExercise;
	private LocalDate date = new LocalDate();

	/**
	 * Exercise constructor
	 * 
	 * @param n the name of the exercise performed
	 * @param kj the amount of kj burned
	 * @param d the date the exercise was performed
	 */
	public Exercise(String name, double kj, String date) {
		this.nameExercise = name;
		this.kjBurned = kj;
		this.date = LocalDate.parse(date);
	}

	/**
	 * @return the kj burned in the exercise
	 */
	public double getKjBurned() {
		return kjBurned;
	}

	/**
	 * @param kj the amount of kj burned in the exercise
	 */
	public void setKjBurned(double kj) {
		this.kjBurned = kj;
	}

	/**
	 * @return the name of the exercise performed
	 */
	public String getNameExercise() {
		return nameExercise;
	}

	/**
	 * @param name of the exercise
	 *  */
	public void setNameExercise(String name) {
		this.nameExercise = name;
	}

	/**
	 * @return the date that the exercise was performed
	 */
	public LocalDate getDate() {
		return date;
	}

}

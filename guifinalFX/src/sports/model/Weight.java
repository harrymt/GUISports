package sports.model;

import org.joda.time.LocalDate;

/**
 * Weight class for storing data on daily weight values
 * 
 * @author hxm02u
 * @version 1.0.0
 * 
 */
public class Weight {
	private double weight;
	LocalDate date;

	/**
	 * Mass constructor
	 * 
	 * @param d the date of the measurement
	 * @param w the weight on the given day
	 */
	public Weight(double weight, String date) {
		this.date = LocalDate.parse(date);
		this.weight = weight;
	}

	/**
	 * @return weight value
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight set the weight value of the User
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return date in the format yyyy-mm-dd
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date the weight was recorded
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

}

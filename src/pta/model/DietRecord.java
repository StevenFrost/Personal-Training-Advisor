package pta.model;

import java.util.Date;
import java.text.ParseException;

/**
 * An individual record of a meal the user ate, at the time it was eaten.
 * 
 * @author Steven Frost
 */
public class DietRecord extends Record {
	private Food meal;
	
	/**
	 * Constructor
	 * 
	 * @param meal the meal eaten
	 * @param date the date eaten
	 * @throws ParseException if the date could not be parsed
	 */
	public DietRecord(Food meal, String date) throws ParseException {
		super(date);
		this.meal = meal;
	}
	
	/**
	 * Constructor
	 * 
	 * @param meal the meal eaten
	 * @param date the date eaten
	 */
	public DietRecord(Food meal, Date date) {
		super(date);
		this.meal = meal;
	}

	public Food getMeal() {
		return meal;
	}
}
package pta.model;

import java.text.ParseException;
import java.util.Date;

/**
 * An individual representation of a training routine the user carried out. A
 * record consists of a routine and the time it was carried out for.
 * 
 * @author Steven Frost
 */
public class TrainingRecord extends Record {
	private Exercise routine;
	private int time;

	/**
	 * Constructor
	 * 
	 * @param routine the routine performed
	 * @param time the number of minutes the routine lasted for
	 * @param date the date the routine was performed
	 * @throws ParseException if the date cannot be parsed
	 */
	public TrainingRecord(Exercise routine, int time, String date) throws ParseException {
		super(date);
		this.routine = routine;
		this.time = time;
	}

	/**
	 * Constructor
	 * 
	 * @param routine the routine performed
	 * @param time the number of minutes the routine lasted for
	 * @param date the date the routine was performed
	 */
	public TrainingRecord(Exercise routine, int time, Date date) {
		super(date);
		this.routine = routine;
		this.time = time;
	}

	public Exercise getRoutine() {
		return routine;
	}

	public int getTime() {
		return time;
	}
}
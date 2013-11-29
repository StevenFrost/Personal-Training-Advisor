package pta.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An abstract representation of an individual record. All system records should
 * extend this abstract class.
 * 
 * @author Steven Frost
 */
public abstract class Record {
	private Date date;
	private int id;
	private static int lastID = 0;

	/**
	 * Constructor
	 * 
	 * @param date the date the record was created
	 */
	public Record (Date date) {
		id = ++lastID;
		this.date = date;
	}
	
	/**
	 * Constructor
	 * 
	 * @param date the date the record was created (dd-MM-yyyy HH:mm)
	 * @throws ParseException if the date cannot be parsed
	 */
	public Record (String date) throws ParseException {
		this(new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(date));
	}
	
	public int getID() {
		return id;
	}
	
	public Date getDateTime() {
		return date;
	}
	
	/**
	 * @return the time the record was created in the format HH:mm
	 */
	public String getTimeString() {
		return new SimpleDateFormat("HH:mm").format(date);
	}
	
	/**
	 * @return the date the record was created in the format yyyy-MM-dd
	 */
	public String getDateString() {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
}
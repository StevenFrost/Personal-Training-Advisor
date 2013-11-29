package pta.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A representation of a user in the system. A user is identified by their
 * forename, surname and date of birth.
 * 
 * @author steve
 * 
 */
public class Person {
	private String forename;
	private String surname;
	private Date date;

	/**
	 * Constructor
	 * 
	 * @param forename the user's forename
	 * @param surname the user's surname
	 * @param date the user's date of birth
	 * @throws ParseException if the user's date of birth cannot be parsed
	 */
	public Person(String forename, String surname, String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		this.date = dateFormat.parse(date);
		this.forename = forename;
		this.surname = surname;
	}

	/**
	 * Calculates the user's age from their date of birth
	 * 
	 * @return the user's age
	 */
	public int getAge() {
		Date d = new Date();
		long msNow = d.getTime();
		long msDob = date.getTime();
		return (int) ((msNow - msDob) / 3.15569E10);
	}

	public Date getDOB() {
		return date;
	}

	/**
	 * @return the user's date of birth in dd-MM-yyyy format
	 */
	public String getDOBString() {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}

	public String getForename() {
		return forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
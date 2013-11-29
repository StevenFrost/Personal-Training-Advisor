package pta.model;

import java.util.Date;
import java.text.ParseException;

/**
 * An individual record of a picture inserted by the user for reference. A
 * picture consists of the path to the actual image which can be remote.
 * 
 * @author Steven Frost
 */
public class PictureRecord extends Record {
	private String path;
	
	/**
	 * Constructor
	 * 
	 * @param path the path to the picture
	 * @param date the date the picture was inserted
	 * @throws ParseException if the date cannot be parsed
	 */
	public PictureRecord(String path, String date) throws ParseException {
		super(date);
		this.path = path;
	}
	
	/**
	 * Constructor
	 * 
	 * @param path the path to the picture
	 * @param date the date the picture was inserted
	 */
	public PictureRecord(String path, Date date) {
		super(date);
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
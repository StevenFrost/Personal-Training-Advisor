package pta.model;

import java.text.ParseException;
import java.util.Date;

/**
 * An individual record of a URL created for future reference. A record consists
 * of a name and the url associated with it.
 * 
 * @author Steven Frost
 */
public class LinkRecord extends Record {
	private String name;
	private String url;
	
	/**
	 * Constructor
	 * 
	 * @param name the name of the link
	 * @param url the actual url
	 * @param date the date the record was created
	 * @throws ParseException if the date could not be parsed
	 */
	public LinkRecord(String name, String url, String date) throws ParseException {
		super(date);
		this.name = name;
		this.url = url;
	}
	
	/**
	 * Constructor
	 * 
	 * @param name the name of the link
	 * @param url the actual url
	 * @param date the date the record was created
	 */
	public LinkRecord(String name, String url, Date date) {
		super(date);
		this.name = name;
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	
	public String getURL() {
		return url;
	}
}
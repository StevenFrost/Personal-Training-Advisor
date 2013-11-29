package pta.model;

/**
 * Represents a single exercise routine as a name and the burn rate associated
 * with the routine.
 * 
 * @author Steven Frost
 */
public class Exercise {
	private String name;
	private double burnRate;
	
	/**
	 * Constructor
	 * 
	 * @param name the name of the exercise
	 * @param burnRate the burn rate, in cal/hr
	 */
	public Exercise(String name, double burnRate) {
		this.name = name;
		this.burnRate = burnRate;
	}
	
	public String getName() {
		return name;
	}
	
	public double getBurnRate() {
		return burnRate;
	}
}
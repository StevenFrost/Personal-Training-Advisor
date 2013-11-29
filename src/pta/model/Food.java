package pta.model;

/**
 * Represents a food item by a name, the content of calories, fat and salt.
 * 
 * @author Steven Frost
 */
public class Food {
	private String name;
	private int calories;
	private double fat;
	private double salt;
	
	/**
	 * Constructor
	 * 
	 * @param name the name of the meal
	 * @param calories the number of calories on average (KCal)
	 * @param fat the amount of fat (in g)
	 * @param salt the amount of salt (in g)
	 */
	public Food(String name, int calories, double fat, double salt) {
		this.name = name;
		this.calories = calories;
		this.fat = fat;
		this.salt = salt;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCalories() {
		return calories;
	}
	
	public double getFatContent() {
		return fat;
	}
	
	public double getSaltContent() {
		return salt;
	}
}
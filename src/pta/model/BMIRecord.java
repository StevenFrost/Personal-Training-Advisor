package pta.model;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * An individual record of a user's height, weight and unit preference at the
 * time of creation.
 * 
 * @author Steven Frost
 */
public class BMIRecord extends Record {
	private double height;
	private double weight;
	private Profile.HeightMeasure heightUnit;
	private Profile.WeightMeasure weightUnit;

	/**
	 * Default constructor
	 * 
	 * @param height the height in hUnits
	 * @param weight the weight in wUnits
	 * @param hUnit the height unit to be used
	 * @param wUnit the weight unit to be used
	 * @param date the date of the record creation
	 */
	public BMIRecord(double height, double weight, Profile.HeightMeasure hUnit, Profile.WeightMeasure wUnit, Date date) {
		super(date);
		this.height = height;
		this.weight = weight;
		this.heightUnit = hUnit;
		this.weightUnit = wUnit;
	}

	public BMIRecord(double height, double weight, Profile.HeightMeasure hUnit, Profile.WeightMeasure wUnit, String date) throws ParseException {
		super(date);
		this.height = height;
		this.weight = weight;
		this.heightUnit = hUnit;
		this.weightUnit = wUnit;
	}

	public double getHeight() {
		return height;
	}

	public Profile.HeightMeasure getHeightUnit() {
		return heightUnit;
	}

	public double getWeight() {
		return weight;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Profile.WeightMeasure getWeightUnit() {
		return weightUnit;
	}

	/**
	 * Gets the BMI value for this record, to two decimal places
	 * 
	 * @return the bmi value
	 */
	public double getBMI() {
		double w = weightUnit == Profile.WeightMeasure.KILOGRAMS ? weight : weight * 0.453592;
		double h = heightUnit == Profile.HeightMeasure.METRES ? height : height * 0.3048;
		double b = w / Math.pow(h, 2);
		return Double.valueOf(new DecimalFormat("#.##").format(b));
	}
}
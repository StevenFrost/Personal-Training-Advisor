package pta.tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import pta.model.BMIRecord;
import pta.model.Profile;

public class TestBMIRecord {
	BMIRecord r;
	Date d;

	@Before
	public void setUp() {
		d = new Date();
		r = new BMIRecord(1.8, 80.3, Profile.HeightMeasure.METRES, Profile.WeightMeasure.KILOGRAMS, d);
	}

	@Test
	public void testGetHeight() {
		assertEquals(r.getHeight(), 1.8, 0.01);
	}

	@Test
	public void testGetHeightUnit() {
		assertEquals(r.getHeightUnit(), Profile.HeightMeasure.METRES);
	}

	@Test
	public void testGetWeight() {
		assertEquals(r.getWeight(), 80.3, 0.01);
	}

	@Test
	public void testSetHeight() {
		BMIRecord t = new BMIRecord(1.8, 80.3, Profile.HeightMeasure.METRES, Profile.WeightMeasure.KILOGRAMS, d);
		t.setHeight(1.75);
		assertEquals(t.getHeight(), 1.75, 0.01);
	}

	@Test
	public void testSetWeight() {
		BMIRecord t = new BMIRecord(1.8, 80.3, Profile.HeightMeasure.METRES, Profile.WeightMeasure.KILOGRAMS, d);
		t.setWeight(80.0);
		assertEquals(t.getWeight(), 80.0, 0.01);
	}

	@Test
	public void testGetWeightUnit() {
		assertEquals(r.getWeightUnit(), Profile.WeightMeasure.KILOGRAMS);
	}

	@Test
	public void testGetBMI() {
		assertEquals(r.getBMI(), 24.7, 0.1);
	}

	@Test
	public void testGetDateTime() {
		assertEquals(r.getDateTime(), d);
	}

	@Test
	public void testGetTimeString() {
		assertEquals(r.getTimeString(), new SimpleDateFormat("HH:mm").format(d));
	}

	@Test
	public void testGetDateString() {
		assertEquals(r.getDateString(), new SimpleDateFormat("yyyy-MM-dd").format(d));
	}
}
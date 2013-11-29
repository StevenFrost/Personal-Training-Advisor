package pta.tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import pta.model.DietRecord;
import pta.model.Food;

public class TestDietRecord {
	DietRecord r;
	Food f;
	Date d;

	@Before
	public void setUp() throws Exception {
		d = new Date();
		f = new Food("Chicken", 270, 0.1, 0.1);
		r = new DietRecord(f, d);
	}

	@Test
	public void testGetMeal() {
		assertEquals(r.getMeal(), f);
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
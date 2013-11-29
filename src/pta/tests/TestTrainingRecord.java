package pta.tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import pta.model.Exercise;
import pta.model.TrainingRecord;

public class TestTrainingRecord {
	TrainingRecord r;
	Exercise e;
	Date d;

	@Before
	public void setUp() throws Exception {
		d = new Date();
		e = new Exercise("Running", 430.5);
		r = new TrainingRecord(e, 20, d);
	}

	@Test
	public void testGetRoutine() {
		assertEquals(r.getRoutine(), e);
	}

	@Test
	public void testGetTime() {
		assertEquals(r.getTime(), 20);
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
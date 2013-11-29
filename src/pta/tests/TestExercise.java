package pta.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pta.model.Exercise;

public class TestExercise {
	Exercise e;

	@Before
	public void setUp() throws Exception {
		e = new Exercise("Running", 430.5);
	}

	@Test
	public void testGetName() {
		assertEquals(e.getName(), "Running");
	}

	@Test
	public void testGetBurnRate() {
		assertEquals(e.getBurnRate(), 430.5, 0.01);
	}
}
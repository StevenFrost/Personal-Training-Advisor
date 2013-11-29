package pta.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pta.model.Food;

public class TestFood {
	Food f;
	
	@Before
	public void setUp() throws Exception {
		f = new Food("Chicken", 270, 0.2, 0.1);
	}

	@Test
	public void testGetName() {
		assertEquals(f.getName(), "Chicken");
	}

	@Test
	public void testGetCalories() {
		assertEquals(f.getCalories(), 270);
	}

	@Test
	public void testGetFatContent() {
		assertEquals(f.getFatContent(), 0.2, 0.01);
	}

	@Test
	public void testGetSaltContent() {
		assertEquals(f.getSaltContent(), 0.1, 0.01);
	}
}
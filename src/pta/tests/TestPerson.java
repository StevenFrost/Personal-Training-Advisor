package pta.tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import pta.model.Person;

public class TestPerson {
	Person p;

	@Before
	public void setUp() throws Exception {
		p = new Person("Steven", "Frost", "26-11-1993");
	}

	@Test
	public void testGetAge() {
		// Valid at the time of writing
		assertEquals(p.getAge(), 20);
	}

	@Test
	public void testGetDOB() throws ParseException {
		assertEquals(p.getDOB(), new SimpleDateFormat("dd-MM-yyyy").parse("26-11-1993"));
	}

	@Test
	public void testGetDOBString() {
		assertEquals(p.getDOBString(), "26-11-1993");
	}

	@Test
	public void testGetForename() {
		assertEquals(p.getForename(), "Steven");
	}

	@Test
	public void testGetSurname() {
		assertEquals(p.getSurname(), "Frost");
	}

	@Test
	public void testSetForename() throws ParseException {
		Person t = new Person("Steven", "Frost", "26-11-1993");
		t.setForename("Peter");
		assertEquals(t.getForename(), "Peter");
	}

	@Test
	public void testSetSurname() throws ParseException {
		Person t = new Person("Steven", "Frost", "26-11-1993");
		t.setSurname("Smith");
		assertEquals(t.getSurname(), "Smith");
	}
}
package pta.tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import pta.model.LinkRecord;

public class TestLinkRecord {
	LinkRecord l;
	Date d;
	
	@Before
	public void setUp() throws Exception {
		d = new Date();
		l = new LinkRecord("Google", "http://www.google.co.uk", d);
	}

	@Test
	public void testGetName() {
		assertEquals(l.getName(), "Google");
	}

	@Test
	public void testGetURL() {
		assertEquals(l.getURL(), "http://www.google.co.uk");
	}

	@Test
	public void testGetDateTime() {
		assertEquals(l.getDateTime(), d);
	}

	@Test
	public void testGetTimeString() {
		assertEquals(l.getTimeString(), new SimpleDateFormat("HH:mm").format(d));
	}

	@Test
	public void testGetDateString() {
		assertEquals(l.getDateString(), new SimpleDateFormat("yyyy-MM-dd").format(d));
	}
}
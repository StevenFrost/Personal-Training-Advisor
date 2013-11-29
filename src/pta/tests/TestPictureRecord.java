package pta.tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import pta.model.PictureRecord;

public class TestPictureRecord {
	PictureRecord r;
	Date d;

	@Before
	public void setUp() throws Exception {
		d = new Date();
		r = new PictureRecord("/path/to/picture", d);
	}

	@Test
	public void testGetPath() {
		assertEquals(r.getPath(), "/path/to/picture");
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
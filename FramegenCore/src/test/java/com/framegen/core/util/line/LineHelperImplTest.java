package com.framegen.core.util.line;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class LineHelperImplTest extends FramegenCoreTestCase {
	
	private LineHelperImpl fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new LineHelperImpl();
	}

	@Test
	public void testGetPath_incrementBy2_forwards() {
		LineSettingsVO lineSettings = getTdf().createLineSettings(10, 10, 20, 20, Double.valueOf("2"), null, null, null);
		
		List<Point2D> path = fixture.getPath(lineSettings);
		
		assertNotNull(path);
		assertTrue(path.size() > 0);
		assertEquals(6, path.size());
		
		assertEquals(new Point2D.Double(Double.parseDouble("10.000000"), Double.parseDouble("10.000000")), path.get(0));
		assertEquals(new Point2D.Double(Double.parseDouble("12.000000"), Double.parseDouble("12.000000")), path.get(1));
		assertEquals(new Point2D.Double(Double.parseDouble("14.000000"), Double.parseDouble("14.000000")), path.get(2));
		assertEquals(new Point2D.Double(Double.parseDouble("16.000000"), Double.parseDouble("16.000000")), path.get(3));
		assertEquals(new Point2D.Double(Double.parseDouble("18.000000"), Double.parseDouble("18.000000")), path.get(4));
		assertEquals(new Point2D.Double(Double.parseDouble("20.000000"), Double.parseDouble("20.000000")), path.get(5));
	}
	
	@Test
	public void testGetPath_incrementBy3_backwards() {
		LineSettingsVO lineSettings = getTdf().createLineSettings(90, 90, 60, 60, Double.valueOf("3"), null, null, null);
		
		List<Point2D> path = fixture.getPath(lineSettings);
		
		assertNotNull(path);
		assertTrue(path.size() > 0);
		assertEquals(11, path.size());
		
		assertEquals(new Point2D.Double(Double.parseDouble("90.000000"), Double.parseDouble("90.000000")), path.get(0));
		assertEquals(new Point2D.Double(Double.parseDouble("87.000000"), Double.parseDouble("87.000000")), path.get(1));
		assertEquals(new Point2D.Double(Double.parseDouble("84.000000"), Double.parseDouble("84.000000")), path.get(2));
		assertEquals(new Point2D.Double(Double.parseDouble("81.000000"), Double.parseDouble("81.000000")), path.get(3));
		assertEquals(new Point2D.Double(Double.parseDouble("78.000000"), Double.parseDouble("78.000000")), path.get(4));
		assertEquals(new Point2D.Double(Double.parseDouble("75.000000"), Double.parseDouble("75.000000")), path.get(5));
		assertEquals(new Point2D.Double(Double.parseDouble("72.000000"), Double.parseDouble("72.000000")), path.get(6));
		assertEquals(new Point2D.Double(Double.parseDouble("69.000000"), Double.parseDouble("69.000000")), path.get(7));
		assertEquals(new Point2D.Double(Double.parseDouble("66.000000"), Double.parseDouble("66.000000")), path.get(8));
		assertEquals(new Point2D.Double(Double.parseDouble("63.000000"), Double.parseDouble("63.000000")), path.get(9));
		assertEquals(new Point2D.Double(Double.parseDouble("60.000000"), Double.parseDouble("60.000000")), path.get(10));
	}
	
	protected void generateAsserts(List<Point2D> points) {
		String assertFormat = "assertEquals(new Point2D.Double(Double.parseDouble(\"%f\"), Double.parseDouble(\"%f\")), path.get(%d));";
		for(int i=0; i<points.size(); i++) {
			System.out.println(String.format(assertFormat, points.get(i).getX(), points.get(i).getY(), i));
		}
	}

}

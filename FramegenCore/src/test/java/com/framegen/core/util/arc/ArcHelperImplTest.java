package com.framegen.core.util.arc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.core.FramegenCoreTestCase;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;

public class ArcHelperImplTest extends FramegenCoreTestCase {
	
	private ArcHelperImpl fixture;
	private IArcCalculator arcCalculator;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		arcCalculator = mock(IArcCalculator.class);
		fixture = new ArcHelperImpl(arcCalculator);
	}

	@Test
	public void testGetPointsAlongArc() throws URISyntaxException, IOException {
		BufferedImage baseImage = getTdf().createBufferedImageFromResource("base_map.png");
		
		Integer xStart = 0;
		Integer yStart = 0;
		Integer xEnd = 50;
		Integer yEnd = 50;
		Double moveIncrement = Double.valueOf("5");
		Integer xScale = null;
		Integer yScale = null;
		Integer rotateBy = null;
		Double radius = Double.valueOf("10");
		boolean shortestRoute = true;
		boolean side = false;
		boolean reverseSequence = false;
		ArcSettingsVO arcSettings = getTdf().createArcSettings(xStart, yStart, xEnd, yEnd, moveIncrement, xScale, yScale, 
				rotateBy, radius, shortestRoute, side, reverseSequence);
		
		List<PointF> points = getTdf().createPoints(10);
		
		PointF expectedFrom = new PointF(0f, 2448f);
		PointF expectedTo = new PointF(50f, 2398f);
		when(arcCalculator.generateCurve(expectedFrom, expectedTo, arcSettings.getRadius().floatValue(), arcSettings.getMoveIncrement().floatValue(), 
				arcSettings.isShortestRoute(), arcSettings.isSide())).thenReturn(points);
		
		List<Point2D> pointsAlongArc = fixture.getPointsAlongArc(arcSettings, baseImage);
		
		verify(arcCalculator).generateCurve(expectedFrom, expectedTo, arcSettings.getRadius().floatValue(), arcSettings.getMoveIncrement().floatValue(), 
				arcSettings.isShortestRoute(), arcSettings.isSide());
		
		assertEquals(points.size(), pointsAlongArc.size());
	}

}

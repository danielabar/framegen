package com.framegen.core.util.arc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ArcCalculatorImplTest {
	
	private ArcCalculatorImpl fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new ArcCalculatorImpl();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGenerateCurve_radiusTooSmall() {
		PointF pFrom = new PointF(100f, 100f);
		PointF pTo = new PointF(200f, 200f);
		float pRadius = 10f;
		float pMinDistance = 20f;
		boolean shortest = true;
		boolean side = false;
		
		fixture.generateCurve(pFrom, pTo, pRadius, pMinDistance, shortest, side);
	}

	@Test
	public void testGenerateCurve_shortestTrue_sideTrue() {
		PointF pFrom = new PointF(100f, 100f);
		PointF pTo = new PointF(200f, 200f);
		float pRadius = 100f;
		float pMinDistance = 20f;
		boolean shortest = true;
		boolean side = true;
		
		List<PointF> results = fixture.generateCurve(pFrom, pTo, pRadius, pMinDistance, shortest, side);
		
		assertTrue(results.size() > 0);
		assertEquals(9, results.size());
		
		assertTrue(new PointF(200.000000f, 200.000000f).equals(results.get(0)));
		assertTrue(new PointF(180.133057f, 198.006653f).equals(results.get(1)));
		assertTrue(new PointF(161.058151f, 192.106094f).equals(results.get(2)));
		assertTrue(new PointF(143.535736f, 182.533554f).equals(results.get(3)));
		assertTrue(new PointF(128.264389f, 169.670654f).equals(results.get(4)));
		assertTrue(new PointF(115.852890f, 154.030212f).equals(results.get(5)));
		assertTrue(new PointF(106.796089f, 136.235748f).equals(results.get(6)));
		assertTrue(new PointF(101.455017f, 116.996689f).equals(results.get(7)));
		assertTrue(new PointF(100.000000f, 100.000000f).equals(results.get(8)));
	}
	
	@Test
	public void testGenerateCurve_shortestTrue_sideFalse() {
		PointF pFrom = new PointF(100f, 100f);
		PointF pTo = new PointF(200f, 200f);
		float pRadius = 100f;
		float pMinDistance = 20f;
		boolean shortest = true;
		boolean side = false;
		
		List<PointF> results = fixture.generateCurve(pFrom, pTo, pRadius, pMinDistance, shortest, side);

		assertTrue(results.size() > 0);
		assertEquals(9, results.size());
		
		assertTrue(new PointF(99.999992f, 100.000000f).equals(results.get(0)));
		assertTrue(new PointF(119.866936f, 101.993340f).equals(results.get(1)));
		assertTrue(new PointF(138.941833f, 107.893906f).equals(results.get(2)));
		assertTrue(new PointF(156.464249f, 117.466438f).equals(results.get(3)));
		assertTrue(new PointF(171.735611f, 130.329330f).equals(results.get(4)));
		assertTrue(new PointF(184.147095f, 145.969772f).equals(results.get(5)));
		assertTrue(new PointF(193.203918f, 163.764221f).equals(results.get(6)));
		assertTrue(new PointF(198.544983f, 183.003281f).equals(results.get(7)));
		assertTrue(new PointF(200.000000f, 200.000000f).equals(results.get(8)));
	}
	
	@Test
	public void testGenerateCurve_shortestFalse_sideTrue() {
		PointF pFrom = new PointF(100f, 100f);
		PointF pTo = new PointF(200f, 200f);
		float pRadius = 100f;
		float pMinDistance = 20f;
		boolean shortest = false;
		boolean side = true;
		
		List<PointF> results = fixture.generateCurve(pFrom, pTo, pRadius, pMinDistance, shortest, side);
		
		assertTrue(results.size() > 0);
		assertEquals(25, results.size());
		
		assertTrue(new PointF(100.000000f, 99.999992f).equals(results.get(0)));
		assertTrue(new PointF(101.993347f, 80.133057f).equals(results.get(1)));
		assertTrue(new PointF(107.893906f, 61.058147f).equals(results.get(2)));
		assertTrue(new PointF(117.466446f, 43.535732f).equals(results.get(3)));
		assertTrue(new PointF(130.329346f, 28.264374f).equals(results.get(4)));
		assertTrue(new PointF(145.969788f, 15.852890f).equals(results.get(5)));
		assertTrue(new PointF(163.764236f, 6.796089f).equals(results.get(6)));
		assertTrue(new PointF(183.003281f, 1.455025f).equals(results.get(7)));
		assertTrue(new PointF(202.919922f, 0.042641f).equals(results.get(8)));
		assertTrue(new PointF(222.720169f, 2.615227f).equals(results.get(9)));
		assertTrue(new PointF(241.614624f, 9.070229f).equals(results.get(10)));
		assertTrue(new PointF(258.850037f, 19.150314f).equals(results.get(11)));
		assertTrue(new PointF(273.739319f, 32.453606f).equals(results.get(12)));
		assertTrue(new PointF(285.688812f, 48.449760f).equals(results.get(13)));
		assertTrue(new PointF(294.222198f, 66.501053f).equals(results.get(14)));
		assertTrue(new PointF(298.999237f, 85.887840f).equals(results.get(15)));
		assertTrue(new PointF(299.829498f, 105.837234f).equals(results.get(16)));
		assertTrue(new PointF(296.679871f, 125.553925f).equals(results.get(17)));
		assertTrue(new PointF(289.675934f, 144.251846f).equals(results.get(18)));
		assertTrue(new PointF(279.096924f, 161.185608f).equals(results.get(19)));
		assertTrue(new PointF(265.364563f, 175.680084f).equals(results.get(20)));
		assertTrue(new PointF(249.026321f, 187.157440f).equals(results.get(21)));
		assertTrue(new PointF(230.733566f, 195.160126f).equals(results.get(22)));
		assertTrue(new PointF(211.215561f, 199.369064f).equals(results.get(23)));
		assertTrue(new PointF(200.000000f, 200.000000f).equals(results.get(24)));
	}
	
	@Test
	public void testGenerateCurve_shortestFalse_sideFalse() {
		PointF pFrom = new PointF(100f, 100f);
		PointF pTo = new PointF(200f, 200f);
		float pRadius = 100f;
		float pMinDistance = 20f;
		boolean shortest = false;
		boolean side = false;
		
		List<PointF> results = fixture.generateCurve(pFrom, pTo, pRadius, pMinDistance, shortest, side);
		
		assertTrue(results.size() > 0);
		assertEquals(25, results.size());
		
		assertTrue(new PointF(200.000000f, 200.000000f).equals(results.get(0)));
		assertTrue(new PointF(198.006653f, 219.866928f).equals(results.get(1)));
		assertTrue(new PointF(192.106094f, 238.941833f).equals(results.get(2)));
		assertTrue(new PointF(182.533569f, 256.464233f).equals(results.get(3)));
		assertTrue(new PointF(169.670670f, 271.735596f).equals(results.get(4)));
		assertTrue(new PointF(154.030228f, 284.147095f).equals(results.get(5)));
		assertTrue(new PointF(136.235779f, 293.203918f).equals(results.get(6)));
		assertTrue(new PointF(116.996704f, 298.544983f).equals(results.get(7)));
		assertTrue(new PointF(97.080032f, 299.957367f).equals(results.get(8)));
		assertTrue(new PointF(77.279770f, 297.384766f).equals(results.get(9)));
		assertTrue(new PointF(58.385296f, 290.929749f).equals(results.get(10)));
		assertTrue(new PointF(41.149868f, 280.849609f).equals(results.get(11)));
		assertTrue(new PointF(26.260605f, 267.546295f).equals(results.get(12)));
		assertTrue(new PointF(14.311104f, 251.550110f).equals(results.get(13)));
		assertTrue(new PointF(5.777756f, 233.498779f).equals(results.get(14)));
		assertTrue(new PointF(1.000748f, 214.111954f).equals(results.get(15)));
		assertTrue(new PointF(0.170525f, 194.162537f).equals(results.get(16)));
		assertTrue(new PointF(3.320190f, 174.445831f).equals(results.get(17)));
		assertTrue(new PointF(10.324188f, 155.747894f).equals(results.get(18)));
		assertTrue(new PointF(20.903275f, 138.814148f).equals(results.get(19)));
		assertTrue(new PointF(34.635674f, 124.319717f).equals(results.get(20)));
		assertTrue(new PointF(50.973942f, 112.842407f).equals(results.get(21)));
		assertTrue(new PointF(69.266724f, 104.839790f).equals(results.get(22)));
		assertTrue(new PointF(88.784737f, 100.630905f).equals(results.get(23)));
		assertTrue(new PointF(100.000000f, 100.000000f).equals(results.get(24)));
	}

	protected void generateAsserts(List<PointF> points) {
		for (int i=0; i<points.size(); i++) {
			String assertFormat = "assertTrue(new PointF(%ff, %ff).equals(results.get(%d)));";
			System.out.println(String.format(assertFormat, points.get(i).getX(), points.get(i).getY(), i));
		}
	}
	
}

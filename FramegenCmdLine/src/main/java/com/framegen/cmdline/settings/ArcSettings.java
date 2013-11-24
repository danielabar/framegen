package com.framegen.cmdline.settings;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=", commandDescription = "Settings for ARC frame generation")
public class ArcSettings extends LineSettings {
	
	private static final Double DEFAULT_RADIUS = Double.valueOf(100);

	@Parameter(names = "--radius", description = "radius of the arc")
	private Double radius = DEFAULT_RADIUS;
	
	@Parameter(names = "--shortestRoute", description = "take shortest route between start and end")
	private boolean shortestRoute = false;
	
	@Parameter(names = "--side", description = "true to arc under the points, false to arc over the points")
	private boolean side = false;
	
	@Parameter(names = "--reverseSequence", description = "reverse sequence")
	private boolean reverseSequence = false;

	public Double getRadius() {
		return radius;
	}

	public boolean isShortestRoute() {
		return shortestRoute;
	}

	public boolean isSide() {
		return side;
	}

	public boolean isReverseSequence() {
		return reverseSequence;
	}

}

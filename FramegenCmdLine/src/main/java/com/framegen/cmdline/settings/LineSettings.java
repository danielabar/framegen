package com.framegen.cmdline.settings;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=", commandDescription = "Settings for LINE frame generation")
public class LineSettings {

	public static final Integer DEFAULT_X_START = 0;
	public static final Integer DEFAULT_Y_START = 0;
	public static final Integer DEFAULT_X_END = 100;
	public static final Integer DEFAULT_Y_END = 100;
	public static final Double DEFAULT_MOVE_INCREMENT = Double.valueOf(1L);
	
	public static final Integer DEFAULT_X_SCALE = 0;
	public static final Integer DEFAULT_Y_SCALE = 0;
	private static final Integer DEFAULT_ROTATE_BY = 0;

	@Parameter(names = "--xStart", description = "Start value of x in pixels")
	private Integer xStart = DEFAULT_X_START;

	@Parameter(names = "--yStart", description = "Start value of y in pixels")
	private Integer yStart = DEFAULT_Y_START;

	@Parameter(names = "--xEnd", description = "End value of x in pixels")
	private Integer xEnd = DEFAULT_X_END;
	
	@Parameter(names = "--yEnd", description = "End value of y in pixels")
	private Integer yEnd = DEFAULT_Y_END;
	
	@Parameter(names = "--moveInc", description = "Amount to move each step")
	private Double moveIncrement = DEFAULT_MOVE_INCREMENT;

	@Parameter(names = "--xScale", description = "Amount to increase width of overlay image on each move")
	private Integer xScale = DEFAULT_X_SCALE;
	
	@Parameter(names = "--yScale", description = "Amount to increase height of overlay image on each move")
	private Integer yScale = DEFAULT_Y_SCALE;
	
	@Parameter(names = "--rotateBy", description = "Number of degrees to rotate by on each move")
	private Integer rotateBy = DEFAULT_ROTATE_BY;

	public Integer getxStart() {
		return xStart;
	}

	public Integer getyStart() {
		return yStart;
	}

	public Integer getxEnd() {
		return xEnd;
	}

	public Integer getyEnd() {
		return yEnd;
	}

	public Double getMoveIncrement() {
		return moveIncrement;
	}

	public Integer getxScale() {
		return xScale;
	}

	public Integer getyScale() {
		return yScale;
	}
	
	public Integer getRotateBy() {
		return rotateBy;
	}

	public boolean scalingRequested() {
		return this.xScale > 0 && this.yScale > 0;
	}
	
	public boolean rotationRequested() {
		return this.rotateBy > 0;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}

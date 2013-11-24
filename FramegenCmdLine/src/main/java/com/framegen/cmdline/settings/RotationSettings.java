package com.framegen.cmdline.settings;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=", commandDescription = "Settings for ROTATION frame generation")
public class RotationSettings {
	
	public static final Integer DEFAULT_X_POSITION = Integer.valueOf(0);
	public static final Integer DEFAULT_Y_POSITION = Integer.valueOf(0);
	public static final Integer DEFAULT_DEGREES = Integer.valueOf(360);
	public static final Integer DEFAULT_STEP = Integer.valueOf(15);
	public static final Integer DEFAULT_X_SCALE = Integer.valueOf(0);
	public static final Integer DEFAULT_Y_SCALE = Integer.valueOf(0);

	@Parameter(names = "--xPosition", description = "x position in pixels on base image where overlay will be drawn")
	private Integer xPosition = DEFAULT_X_POSITION;
	
	@Parameter(names = "--yPosition", description = "y position in pixels on base image where overlay will be drawn")
	private Integer yPosition = DEFAULT_Y_POSITION;
	
	@Parameter(names = "--degrees", description = "Number of degrees to rotate the overlay image")
	private Integer degrees = DEFAULT_DEGREES;
	
	@Parameter(names = "--step", description = "Step value in degrees for each rotation")
	private Integer step = DEFAULT_STEP;
	
	@Parameter(names = "--xScale", description = "Amount to increase width of overlay image on each move")
	private Integer xScale = DEFAULT_X_SCALE;
	
	@Parameter(names = "--yScale", description = "Amount to increase height of overlay image on each move")
	private Integer yScale = DEFAULT_Y_SCALE;


	public Integer getxPosition() {
		return xPosition;
	}

	public Integer getyPosition() {
		return yPosition;
	}

	public Integer getDegrees() {
		return degrees;
	}

	public Integer getStep() {
		return step;
	}

	public void setDegrees(Integer degrees) {
		this.degrees = degrees;
	}

	public void setStep(Integer step) {
		this.step = step;
	}
	
	public Integer getxScale() {
		return xScale;
	}

	public Integer getyScale() {
		return yScale;
	}
	
	public boolean scalingRequested() {
		return this.xScale > 0 && this.yScale > 0;
	}

}

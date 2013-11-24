package com.framegen.cmdline.settings;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;
import com.framegen.api.settings.option.TranspSettingsVO.TranspDirection;

@Parameters(separators = "=", commandDescription = "Settings for TRANSP frame generation")
public class TranspSettings {
	
	private static final Double DEFAULT_X_POS = Double.valueOf(0);
	private static final Double DEFAULT_Y_POS = Double.valueOf(0);
	private static final Integer DEFAULT_STEPS = 20;
	private static final TranspDirection DEFAULT_DIRECTION = TranspDirection.FADE_OUT;

	@Parameter(names = "--xPos", description = "x position where transparency will be overlaid on base")
	private Double xPos = DEFAULT_X_POS;

	@Parameter(names = "--yPos", description = "y position where transparency will be overlaid on base")
	private Double yPos = DEFAULT_Y_POS;
	
	@Parameter(names = "--steps", validateWith = PositiveInteger.class, description = "Number of steps to make overlay transparent")
	private Integer steps = DEFAULT_STEPS;

	@Parameter(names = "--direction", description = "Direction of transparency: FADE_OUT or FADE_IN")
	private TranspDirection direction = DEFAULT_DIRECTION;

	public Double getxPos() {
		return xPos;
	}

	public Double getyPos() {
		return yPos;
	}

	public Integer getSteps() {
		return steps;
	}

	public TranspDirection getDirection() {
		return direction;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
	}

	public void setDirection(TranspDirection direction) {
		this.direction = direction;
	}

}

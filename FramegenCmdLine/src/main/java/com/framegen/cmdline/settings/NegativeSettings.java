package com.framegen.cmdline.settings;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;

@Parameters(separators = "=", commandDescription = "Settings for NEGATIVE frame generation")
public class NegativeSettings {

	private static final Integer DEFAULT_STEPS = 10;
	
	@Parameter(names = "--steps", validateWith = PositiveInteger.class, description = "Number of steps to transition from positive to negative")
	private Integer steps = DEFAULT_STEPS;

	public Integer getSteps() {
		return steps;
	}
	
}

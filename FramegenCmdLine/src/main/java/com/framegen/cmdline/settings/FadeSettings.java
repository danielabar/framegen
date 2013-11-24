package com.framegen.cmdline.settings;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;

@Parameters(separators = "=", commandDescription = "Settings for FADE frame generation (overlay is ignored)")
public class FadeSettings {

	private static final Integer DEFAULT_STEPS = 20;
	
	@Parameter(names = "--steps", validateWith = PositiveInteger.class, description = "Number of steps to fade image")
	private Integer steps = DEFAULT_STEPS;

	public Integer getSteps() {
		return steps;
	}
	
}

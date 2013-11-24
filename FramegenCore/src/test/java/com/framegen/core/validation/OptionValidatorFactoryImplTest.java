package com.framegen.core.validation;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.core.FramegenCoreTestCase;
import com.framegen.core.validation.option.ArcOptionValidatorImpl;
import com.framegen.core.validation.option.LineOptionValidatorImpl;

public class OptionValidatorFactoryImplTest extends FramegenCoreTestCase {
	
	OptionValidatorFactoryImpl fixture = new OptionValidatorFactoryImpl();

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new OptionValidatorFactoryImpl();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetOptionValidator_settingsNull() {
		fixture.getOptionValidator(null);
	}
	
	@Test
	public void testGetOptionValidator_line() {
		ProgramSettingsVO programSettings = new ProgramSettingsVO();
		LineSettingsVO lineSettings = new LineSettingsVO();
		SettingsVO settings = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		IOptionValidator optionValidator = fixture.getOptionValidator(settings);
		
		assertTrue(optionValidator instanceof LineOptionValidatorImpl);
	}
	
	@Test
	public void testGetOptionValidator_arc() {
		ProgramSettingsVO programSettings = new ProgramSettingsVO();
		ArcSettingsVO arcSettings = new ArcSettingsVO();
		SettingsVO settings = getTdf().createSettingsWithArc(programSettings, arcSettings);
		
		IOptionValidator optionValidator = fixture.getOptionValidator(settings);
		
		assertTrue(optionValidator instanceof ArcOptionValidatorImpl);
		
	}

}

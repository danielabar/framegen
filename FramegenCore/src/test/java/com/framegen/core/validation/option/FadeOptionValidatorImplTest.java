package com.framegen.core.validation.option;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.FadeSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class FadeOptionValidatorImplTest extends FramegenCoreTestCase {
	
	private FadeOptionValidatorImpl fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new FadeOptionValidatorImpl();
	}

	@Test
	public void testValidate_valid() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Integer steps = 7;
		FadeSettingsVO fadeSettings = getTdf().createFadeSettings(steps );
		
		SettingsVO settingsVO = getTdf().createSettingsWithFade(programSettings, fadeSettings);
		
		List<String> messages = fixture.validate(settingsVO );
		assertEquals(0, messages.size());
	}
	
	@Test
	public void testValidate_missingSteps() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Integer steps = null;
		FadeSettingsVO fadeSettings = getTdf().createFadeSettings(steps );
		
		SettingsVO settingsVO = getTdf().createSettingsWithFade(programSettings, fadeSettings);
		
		List<String> messages = fixture.validate(settingsVO );
		assertEquals(1, messages.size());
		assertEquals("Number of fade steps must be specified", messages.get(0));
	}
	
	@Test
	public void testValidate_missingStepsNegative() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Integer steps = -67;
		FadeSettingsVO fadeSettings = getTdf().createFadeSettings(steps );
		
		SettingsVO settingsVO = getTdf().createSettingsWithFade(programSettings, fadeSettings);
		
		List<String> messages = fixture.validate(settingsVO );
		assertEquals(1, messages.size());
		assertEquals("Number of fade steps must be greater than zero", messages.get(0));
	}

}

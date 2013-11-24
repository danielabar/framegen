package com.framegen.core.validation.option;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class LineOptionValidatorImplTest extends FramegenCoreTestCase {
	
	private LineOptionValidatorImpl fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new LineOptionValidatorImpl();
	}

	@Test
	public void testValidate_valid() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Integer xStart = 10;
		Integer yStart = 20;
		Integer xEnd = 1000;
		Integer yEnd = 2000;
		Double moveIncrement = Double.valueOf("5");
		Integer xScale = null;
		Integer yScale = null;
		Integer rotateBy = null;
		LineSettingsVO lineSettings = getTdf().createLineSettings(xStart, yStart, xEnd, yEnd, moveIncrement, xScale, yScale, rotateBy);
		
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		List<String> messages = fixture.validate(settingsVO );
		assertEquals(0, messages.size());
	}
	
	@Test
	public void testValidate_incrementZero() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Integer xStart = 10;
		Integer yStart = 20;
		Integer xEnd = 1000;
		Integer yEnd = 2000;
		Double moveIncrement = Double.valueOf("0");
		Integer xScale = null;
		Integer yScale = null;
		Integer rotateBy = null;
		LineSettingsVO lineSettings = getTdf().createLineSettings(xStart, yStart, xEnd, yEnd, moveIncrement, xScale, yScale, rotateBy);
		
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		List<String> messages = fixture.validate(settingsVO );
		assertEquals(1, messages.size());
		assertEquals("Move increment must be greater than zero", messages.get(0));
	}
	
	@Test
	public void testValidate_someRequiredNotSpecified() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Integer xStart = 10;
		Integer yStart = null;
		Integer xEnd = null;
		Integer yEnd = 2000;
		Double moveIncrement = null;
		Integer xScale = null;
		Integer yScale = null;
		Integer rotateBy = null;
		LineSettingsVO lineSettings = getTdf().createLineSettings(xStart, yStart, xEnd, yEnd, moveIncrement, xScale, yScale, rotateBy);
		
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		List<String> messages = fixture.validate(settingsVO );
		assertEquals(3, messages.size());
		assertEquals("yStart must be specified", messages.get(0));
		assertEquals("xEnd must be specified", messages.get(1));
		assertEquals("Increment must be specified", messages.get(2));
	}
	
	@Test
	public void testValidate_missingLineSettings() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		LineSettingsVO lineSettings = null;
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		List<String> messages = fixture.validate(settingsVO );
		assertEquals(1, messages.size());
		assertEquals("Line settings must be specified", messages.get(0));
	}

}

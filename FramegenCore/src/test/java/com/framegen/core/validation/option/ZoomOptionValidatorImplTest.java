package com.framegen.core.validation.option;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.ZoomSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class ZoomOptionValidatorImplTest extends FramegenCoreTestCase {
	
	private ZoomOptionValidatorImpl fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new ZoomOptionValidatorImpl();
	}

	@Test
	public void testValidate() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Double xZoom = Double.valueOf("500");
		Double yZoom = Double.valueOf("600");
		Integer numberOfZooms = 10;
		Double zoomFactor = Double.valueOf("5");
		Double zoomIncrement = Double.valueOf("10");
		ZoomSettingsVO zoomSettings = getTdf().createZoomSettings(xZoom, yZoom, numberOfZooms, zoomFactor, zoomIncrement);
		
		SettingsVO settingsVO = getTdf().createSettingsWithZoom(programSettings, zoomSettings);
		
		List<String> messages = fixture.validate(settingsVO );
		assertEquals(0, messages.size());
	}
	
	@Test
	public void testValidate_xyNotProvided() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Double xZoom = null;
		Double yZoom = null;
		Integer numberOfZooms = 10;
		Double zoomFactor = Double.valueOf("5");
		Double zoomIncrement = Double.valueOf("10");
		ZoomSettingsVO zoomSettings = getTdf().createZoomSettings(xZoom, yZoom, numberOfZooms, zoomFactor, zoomIncrement);
		
		SettingsVO settingsVO = getTdf().createSettingsWithZoom(programSettings, zoomSettings);
		
		List<String> messages = fixture.validate(settingsVO );
		assertEquals("xZoom and yZoom are optional", 0, messages.size());
	}
	
	@Test
	public void testValidate_xyNegative() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Double xZoom = Double.valueOf("-500");
		Double yZoom = Double.valueOf("-600");
		Integer numberOfZooms = 10;
		Double zoomFactor = Double.valueOf("5");
		Double zoomIncrement = Double.valueOf("10");
		ZoomSettingsVO zoomSettings = getTdf().createZoomSettings(xZoom, yZoom, numberOfZooms, zoomFactor, zoomIncrement);
		
		SettingsVO settingsVO = getTdf().createSettingsWithZoom(programSettings, zoomSettings);
		
		List<String> messages = fixture.validate(settingsVO );
		assertEquals(2, messages.size());
		assertEquals("X zoom position must be greater than zero", messages.get(0));
		assertEquals("Y zoom position must be greater than zero", messages.get(1));
	}

}

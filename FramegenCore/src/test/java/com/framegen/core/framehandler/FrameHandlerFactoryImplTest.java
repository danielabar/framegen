package com.framegen.core.framehandler;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.service.IFrameHandler;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.api.settings.option.RotationSettingsVO;
import com.framegen.core.framehandler.option.LineFrameHandler;
import com.framegen.core.framehandler.option.RotationFrameHandler;

public class FrameHandlerFactoryImplTest {
	
	private FrameHandlerFactoryImpl fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new FrameHandlerFactoryImpl();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetFrameHandler_settingsNull() {
		SettingsVO settings = null;
		fixture.getFrameHandler(settings);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testGetFrameHandler_unknown() {
		SettingsVO settings = new SettingsVO();
		fixture.getFrameHandler(settings);
	}
	
	@Test
	public void testGetFrameHandler_LINE() {
		SettingsVO settings = new SettingsVO();
		settings.setLineSettings(new LineSettingsVO());
		IFrameHandler result = fixture.getFrameHandler(settings);
		assertTrue(result instanceof LineFrameHandler);
	}
	
	@Test
	public void testGetFrameHandler_ROTATE() {
		SettingsVO settings = new SettingsVO();
		settings.setRotationSettings(new RotationSettingsVO());
		IFrameHandler result = fixture.getFrameHandler(settings);
		assertTrue(result instanceof RotationFrameHandler);
	}
	
	@Test
	public void testGetFrameHandler_LINE_precedes_ROTATE() {
		SettingsVO settings = new SettingsVO();
		settings.setLineSettings(new LineSettingsVO());
		settings.setRotationSettings(new RotationSettingsVO());
		IFrameHandler result = fixture.getFrameHandler(settings);
		assertTrue(result instanceof LineFrameHandler);
	}

}

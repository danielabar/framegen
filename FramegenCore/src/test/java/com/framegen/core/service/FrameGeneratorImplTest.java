package com.framegen.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.FramegenResponseVO;
import com.framegen.api.service.IFrameHandler;
import com.framegen.api.service.IFrameHandlerFactory;
import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.FadeSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class FrameGeneratorImplTest extends FramegenCoreTestCase {
	
	private FrameGeneratorImpl fixture;
	private IFramegenValidator framegenValidator;
	private IDiskSpaceValidator diskSpaceValidator;
	private IFrameHandlerFactory frameHandlerFactory;
	private IFrameHandler frameHandler;
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		framegenValidator = mock(IFramegenValidator.class);
		diskSpaceValidator = mock(IDiskSpaceValidator.class);
		frameHandlerFactory = mock(IFrameHandlerFactory.class);
		frameHandler = mock(IFrameHandler.class);
		fixture = new FrameGeneratorImpl(framegenValidator, diskSpaceValidator, frameHandlerFactory);
	}
	
	@Test
	public void testGenerateFrames() throws Exception {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		FadeSettingsVO fadeSettings = getTdf().createFadeSettings(10);
		SettingsVO settingsVO = getTdf().createSettingsWithFade(programSettings, fadeSettings );
		
		Double allFrameSize = Double.valueOf("50000000");
		List<String> errorMessages = new ArrayList<String>();
		List<String> diskMessages = new ArrayList<String>();
		FrameHandlerVO frameHandlerVO = new FrameHandlerVO();
		
		when(framegenValidator.validate(settingsVO)).thenReturn(errorMessages);
		when(frameHandlerFactory.getFrameHandler(settingsVO)).thenReturn(frameHandler);
		when(frameHandler.getAllFrameSize(settingsVO)).thenReturn(allFrameSize);
		when(diskSpaceValidator.validateDiskSpace(allFrameSize, settingsVO)).thenReturn(diskMessages);
		when(frameHandler.generateFrames(settingsVO)).thenReturn(frameHandlerVO);
		
		FramegenResponseVO framegenResponseVO = fixture.generateFrames(settingsVO);
		
		verify(framegenValidator).validate(settingsVO);
		verify(frameHandlerFactory, times(2)).getFrameHandler(settingsVO);
		verify(frameHandler).getAllFrameSize(settingsVO);
		verify(diskSpaceValidator).validateDiskSpace(allFrameSize, settingsVO);
		verify(frameHandler).generateFrames(settingsVO);
		
		verifyNoMoreInteractions(framegenValidator, diskSpaceValidator, frameHandlerFactory, frameHandler);
		
		assertTrue(framegenResponseVO.isValid());
		assertEquals(frameHandlerVO, framegenResponseVO.getFrameHandlerVO());
	}
	
	@Test
	public void testGenerateFrames_diskSpaceValidationFails() throws Exception {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		FadeSettingsVO fadeSettings = getTdf().createFadeSettings(10);
		SettingsVO settingsVO = getTdf().createSettingsWithFade(programSettings, fadeSettings );
		
		Double allFrameSize = Double.valueOf("50000000");
		List<String> errorMessages = new ArrayList<String>();
		List<String> diskMessages = Arrays.asList("framegen will use too much disk space");
		
		when(framegenValidator.validate(settingsVO)).thenReturn(errorMessages);
		when(frameHandlerFactory.getFrameHandler(settingsVO)).thenReturn(frameHandler);
		when(frameHandler.getAllFrameSize(settingsVO)).thenReturn(allFrameSize);
		when(diskSpaceValidator.validateDiskSpace(allFrameSize, settingsVO)).thenReturn(diskMessages);
		
		FramegenResponseVO framegenResponseVO = fixture.generateFrames(settingsVO);
		
		verify(framegenValidator).validate(settingsVO);
		verify(frameHandlerFactory).getFrameHandler(settingsVO);
		verify(frameHandler).getAllFrameSize(settingsVO);
		verify(diskSpaceValidator).validateDiskSpace(allFrameSize, settingsVO);
		
		verifyNoMoreInteractions(framegenValidator, diskSpaceValidator, frameHandlerFactory, frameHandler);
		
		assertFalse(framegenResponseVO.isValid());
		assertEquals(diskMessages.get(0), framegenResponseVO.getErrorMessages().get(0));
	}

}

package com.framegen.ui.apibridge;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.response.FramegenResponseVO;
import com.framegen.api.response.NumberFramesResponseVO;
import com.framegen.api.service.IFrameGenerator;
import com.framegen.api.settings.SettingsVO;
import com.framegen.ui.FramegenUITestCase;
import com.framegen.ui.mvp.IFramegenModel;

public class FramegenUIServiceTest extends FramegenUITestCase {
	
	private IFramegenUIService fixture;
	private IFrameGenerator frameGenerator;
	private SettingsTransformer settingsTransformer;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		settingsTransformer = mock(SettingsTransformer.class);
		frameGenerator = mock(IFrameGenerator.class);
		fixture = new FramegenUIServiceImpl(settingsTransformer, frameGenerator);
	}
	
	@Test
	public void testGenerateFrames() {
		IFramegenModel model = getTdf().createFramgenModelWithLineSettings();
		SettingsVO settingsVO = new SettingsVO();
		FramegenResponseVO expectedFramegenResponse = new FramegenResponseVO();
		
		when(settingsTransformer.apply(model)).thenReturn(settingsVO);
		when(frameGenerator.generateFrames(settingsVO)).thenReturn(expectedFramegenResponse);
		
		FramegenResponseVO actualFramegenResponse = fixture.generateFrames(model);
		assertEquals(expectedFramegenResponse, actualFramegenResponse);
		
		verify(settingsTransformer).apply(model);
		verify(frameGenerator).generateFrames(settingsVO);
	}
	
	@Test
	public void testGetNumberOfFrames() {
		IFramegenModel model = getTdf().createFramgenModelWithLineSettings();
		SettingsVO settingsVO = new SettingsVO();
		NumberFramesResponseVO expectedNumberFramesResponse = new NumberFramesResponseVO();
		
		when(settingsTransformer.apply(model)).thenReturn(settingsVO);
		when(frameGenerator.getNumberOfFrames(settingsVO)).thenReturn(expectedNumberFramesResponse);
		
		NumberFramesResponseVO actualNumberOfFramesResponse = fixture.getNumberOfFrames(model);
		assertEquals(expectedNumberFramesResponse, actualNumberOfFramesResponse);
		
		verify(settingsTransformer).apply(model);
		verify(frameGenerator).getNumberOfFrames(settingsVO);
	}

}

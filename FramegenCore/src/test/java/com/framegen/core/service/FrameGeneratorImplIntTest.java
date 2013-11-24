package com.framegen.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.response.NumberFramesResponseVO;
import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class FrameGeneratorImplIntTest extends FramegenCoreTestCase {
	
	private FrameGeneratorImpl fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new FrameGeneratorImpl();
	}

	@Test
	public void testGetAllFrameSize() throws Exception {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_dad.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		LineSettingsVO lineSettings = getTdf().createLineSettings(900, 800, 50, 25, Double.valueOf("90"), null, null, null);
		SettingsVO settings = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		Double allFrameSize = fixture.getAllFrameSize(settings );
		assertNotNull(allFrameSize);
		assertTrue(allFrameSize.compareTo(Double.valueOf(0)) > 0);
		
		Double allFrameSizeMB = allFrameSize / 1024 / 2014;
		assertEquals(Double.valueOf("55.102").doubleValue(), allFrameSizeMB.doubleValue(), DELTA);
	}
	
	@Test
	public void testGetNumberOfFrames() throws IOException, URISyntaxException {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_dad.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		LineSettingsVO lineSettings = getTdf().createLineSettings(900, 800, 50, 25, Double.valueOf("90"), null, null, null);
		SettingsVO settings = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		NumberFramesResponseVO numberFramesResponseVO = fixture.getNumberOfFrames(settings);
		assertTrue(numberFramesResponseVO.isValid());
		assertEquals(10, numberFramesResponseVO.getNumberOfFrames().intValue());
	}
	
	@Test
	public void testGetNumberOfFrames_invalid() throws IOException, URISyntaxException {
		File baseImage = null;
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_dad.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		LineSettingsVO lineSettings = getTdf().createLineSettings(900, 800, 50, 25, Double.valueOf("90"), null, null, null);
		SettingsVO settings = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		NumberFramesResponseVO numberFramesResponseVO = fixture.getNumberOfFrames(settings);
		assertFalse(numberFramesResponseVO.isValid());
	}

}

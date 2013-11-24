package com.framegen.core.framehandler.option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class ArcFrameHandlerTest extends FramegenCoreTestCase {
	
	private static final String BASELINE_PATH = "\\baseline\\arc\\";
	private ArcFrameHandler fixture;
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new ArcFrameHandler();
	}

	@Test
	public void testGenerateFrames() throws IOException, URISyntaxException, InterruptedException {
		
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_map.png");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_plane.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		ArcSettingsVO arcSettings = getTdf().createArcSettings(3264, 0, 212, 596, Double.valueOf("300"), null, null, null, Double.valueOf("3000"), true, false, false);
		SettingsVO settings = getTdf().createSettingsWithArc(programSettings, arcSettings);
		
		FrameHandlerVO frameHandlerVO = fixture.generateFrames(settings);
		Integer numTasks = frameHandlerVO.getNumTasks();
	
		verifyTasks(frameHandlerVO, numTasks);
		verifyFrames(programSettings, numTasks);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGenerateFrames_radiusTooSmallForPoints() throws IOException, URISyntaxException, InterruptedException {
		
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_dad.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		ArcSettingsVO arcSettings = getTdf().createArcSettings(0, 0, 500, 500, Double.valueOf("25"), null, null, null, Double.valueOf("100"), true, false, false);
		
		SettingsVO settings = getTdf().createSettingsWithArc(programSettings, arcSettings);
		fixture.generateFrames(settings);
	}
	
	@Test
	public void testGetAllFrameSize() throws Exception {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_map.png");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_plane.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		ArcSettingsVO arcSettings = getTdf().createArcSettings(3264, 0, 212, 596, Double.valueOf("300"), null, null, null, Double.valueOf("3000"), true, false, false);
		SettingsVO settings = getTdf().createSettingsWithArc(programSettings, arcSettings);
		
		Double allFrameSize = fixture.getAllFrameSize(settings);
		assertNotNull(allFrameSize);
		
		Double allFrameSizeMegaBytes = allFrameSize / 1024 / 1024;
		assertEquals(Double.valueOf("106.3546"), allFrameSizeMegaBytes.doubleValue(), DELTA);
	}
	
	private void verifyTasks(FrameHandlerVO frameHandlerVO, Integer numTasks) {
		for (int i=0; i<numTasks; i++) {
			String status = fixture.getStatus(frameHandlerVO);
			assertTrue(StringUtils.isNotEmpty(status));
		}
	}
	
	private void verifyFrames(ProgramSettingsVO programSettings, Integer numTasks) throws URISyntaxException, IOException {
		File[] generatedFiles = getGeneratedFiles(programSettings);
		assertEquals(numTasks.intValue(), generatedFiles.length);
		
		for (File generatedFile : generatedFiles) {
			File resourceFile = getTdf().createImageFileFromResource("test", "png", BASELINE_PATH + generatedFile.getName());
			assertTrue("generated file should have same contents as baseline", FileUtils.contentEquals(resourceFile, generatedFile));
		}
	}

}

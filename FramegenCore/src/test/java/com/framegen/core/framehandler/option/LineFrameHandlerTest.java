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
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class LineFrameHandlerTest extends FramegenCoreTestCase {
	
	private static final String BASELINE_PATH = "\\baseline\\line\\";
	private LineFrameHandler fixture;
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new LineFrameHandler();
	}
	
	@Test
	public void testGetNumberOfThreads() {
		assertTrue(fixture.getNumberOfThreads() > 0);
	}

	@Test
	public void testGenerateFrames() throws IOException, InterruptedException, URISyntaxException {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_dad.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		LineSettingsVO lineSettings = getTdf().createLineSettings(900, 800, 50, 25, Double.valueOf("90"), null, null, null);
		SettingsVO settings = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		FrameHandlerVO frameHandlerVO = fixture.generateFrames(settings);
		Integer numTasks = frameHandlerVO.getNumTasks();
		
		verifyTasks(frameHandlerVO, numTasks);
		verifyFrames(programSettings, numTasks);
	}
	
	@Test
	public void testGetAllFrameSize() throws Exception {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_dad.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		LineSettingsVO lineSettings = getTdf().createLineSettings(900, 800, 50, 25, Double.valueOf("90"), null, null, null);
		SettingsVO settings = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		Double allFrameSize = fixture.getAllFrameSize(settings);
		assertNotNull(allFrameSize);
		
		Double allFrameSizeMegaBytes = allFrameSize / 1024 / 1024;
		assertEquals(Double.valueOf("108.3758"), allFrameSizeMegaBytes.doubleValue(), DELTA);
	}
	
	@Test
	public void testCalculateRotation() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Integer rotateBy = 15;
		LineSettingsVO lineSettings = getTdf().createLineSettings(0, 0, 10, 10, Double.valueOf("1"), 0, 0, rotateBy );
		
		SettingsVO settings = getTdf().createSettingsWithLine(programSettings, lineSettings);
		int imageCount = 3;
		int totalNumberOfImages = 24;
		
		int result = fixture.calculateRotation(settings, imageCount, totalNumberOfImages);
		
		assertEquals(imageCount*rotateBy, result);
	}
	
	@Test
	public void testCalculateRotation_doesNotExceed_360() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		
		Integer rotateBy = 15;
		LineSettingsVO lineSettings = getTdf().createLineSettings(0, 0, 10, 10, Double.valueOf("1"), 0, 0, rotateBy );
		
		SettingsVO settings = getTdf().createSettingsWithLine(programSettings, lineSettings);
		int imageCount = 25;
		int totalNumberOfImages = 25;
		
		int result = fixture.calculateRotation(settings, imageCount, totalNumberOfImages);
		
		assertEquals(rotateBy.intValue(), result);
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

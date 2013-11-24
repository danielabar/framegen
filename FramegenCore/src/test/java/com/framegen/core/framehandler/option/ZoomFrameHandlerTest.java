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
import com.framegen.api.settings.option.ZoomSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class ZoomFrameHandlerTest extends FramegenCoreTestCase {
	
	private static final String BASELINE_PATH = "\\baseline\\zoom\\";
	
	private ZoomFrameHandler fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new ZoomFrameHandler();
	}

	@Test
	public void testGetNumberOfFrames() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		ZoomSettingsVO zoomSettings = getTdf().createZoomSettings(Double.valueOf("50"), Double.valueOf("50"), 10, Double.valueOf("5"), Double.valueOf("10"));
		SettingsVO settings = getTdf().createSettingsWithZoom(programSettings, zoomSettings);
		
		Integer numberOfFrames = fixture.getNumberOfFrames(settings );
		assertEquals(zoomSettings.getNumberOfZooms(), numberOfFrames);
	}

	@Test
	public void testGenerateFrames() throws IOException, URISyntaxException, InterruptedException {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "house.jpg");
		File overlayImage = null;
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		ZoomSettingsVO zoomSettings = getTdf().createZoomSettings(Double.valueOf("1120"), Double.valueOf("1800"), 10, Double.valueOf("1.0"), Double.valueOf("0.08"));
		SettingsVO settings = getTdf().createSettingsWithZoom(programSettings, zoomSettings);
		
		FrameHandlerVO frameHandlerVO = fixture.generateFrames(settings);
		Integer numTasks = frameHandlerVO.getNumTasks();
		
		verifyTasks(frameHandlerVO, numTasks);
		verifyFrames(programSettings, numTasks);
	}
	
	@Test
	public void testGetAllFrameSize() throws Exception {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "house.jpg");
		File overlayImage = null;
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		ZoomSettingsVO zoomSettings = getTdf().createZoomSettings(Double.valueOf("1120"), Double.valueOf("1800"), 10, Double.valueOf("1.0"), Double.valueOf("0.08"));
		SettingsVO settings = getTdf().createSettingsWithZoom(programSettings, zoomSettings);
		
		Double allFrameSize = fixture.getAllFrameSize(settings);
		assertNotNull(allFrameSize);
		
		Double allFrameSizeMegaBytes = allFrameSize / 1024 / 1024;
		assertEquals(Double.valueOf("112.2345"), allFrameSizeMegaBytes.doubleValue(), DELTA);
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

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
import com.framegen.api.settings.option.NegativeSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class NegativeFrameHandlerTest extends FramegenCoreTestCase {
	
	private static final String BASELINE_PATH = "\\baseline\\negative\\";
	private NegativeFrameHandler fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new NegativeFrameHandler();
	}

	@Test
	public void testGetNumberOfFrames() throws IOException, URISyntaxException {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		File overlayImage = null;
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		int negativeSettingSteps = 10;
		NegativeSettingsVO negativeSettings = getTdf().createNegativeSettings(negativeSettingSteps);
		
		SettingsVO settings = getTdf().createSettingsWithNegative(programSettings, negativeSettings);
		assertEquals(negativeSettingSteps+1, fixture.getNumberOfFrames(settings).intValue());
	}

	@Test
	public void testGenerateFrames() throws IOException, URISyntaxException, InterruptedException {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "sunset.jpg");
		File overlayImage = null;
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		int negativeSettingSteps = 5;
		NegativeSettingsVO negativeSettings = getTdf().createNegativeSettings(negativeSettingSteps);
		
		SettingsVO settings = getTdf().createSettingsWithNegative(programSettings, negativeSettings);
		
		FrameHandlerVO frameHandlerVO = fixture.generateFrames(settings);
		Integer numTasks = frameHandlerVO.getNumTasks();
		
		verifyTasks(frameHandlerVO, numTasks);
		verifyFrames(programSettings, numTasks);
	}
	
	@Test
	public void testGetAllFrameSize() throws Exception {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "sunset.jpg");
		File overlayImage = null;
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		int negativeSettingSteps = 5;
		NegativeSettingsVO negativeSettings = getTdf().createNegativeSettings(negativeSettingSteps);
		
		SettingsVO settings = getTdf().createSettingsWithNegative(programSettings, negativeSettings);
		
		Double allFrameSize = fixture.getAllFrameSize(settings);
		assertNotNull(allFrameSize);
		
		Double allFrameSizeMegaBytes = allFrameSize / 1024 / 1024;
		assertEquals(Double.valueOf("39.6594"), allFrameSizeMegaBytes.doubleValue(), DELTA);
		
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

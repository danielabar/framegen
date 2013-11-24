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
import com.framegen.api.settings.option.RotationSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class RotationFrameHandlerTest extends FramegenCoreTestCase {
	
	private static final String BASELINE_PATH = "\\baseline\\rotate\\";
	
	private RotationFrameHandler fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new RotationFrameHandler();
	}
	
	@Test
	public void testGetNumberOfFrames() throws IOException {
		RotationSettingsVO rotationSettings = getTdf().createRotateSettings(0, 0, 360, 15, null, null);
		ProgramSettingsVO programSettings = new ProgramSettingsVO();
		SettingsVO settings = getTdf().createSettingsWithRotate(programSettings , rotationSettings);
		Integer result = fixture.getNumberOfFrames(settings);
		assertEquals(Integer.valueOf(24), result);
	}
	
	@Test
	public void testGetNumberOfFrames_notEvenlyDivisible() throws IOException {
		RotationSettingsVO rotationSettings = getTdf().createRotateSettings(0, 0, 360, 17, null, null);
		ProgramSettingsVO programSettings = new ProgramSettingsVO();
		SettingsVO settings = getTdf().createSettingsWithRotate(programSettings , rotationSettings);
		Integer result = fixture.getNumberOfFrames(settings);
		assertEquals(Integer.valueOf(21), result);
	}

	@Test
	public void testGetTotalNumberOfImages() {
		RotationSettingsVO settings = getTdf().createRotateSettings(0, 0, 360, 15, null, null);
		Integer result = fixture.getTotalNumberOfImages(settings);
		assertEquals(Integer.valueOf(24), result);
	}
	
	@Test
	public void testGetTotalNumberOfImages_notEvenlyDivisible() {
		RotationSettingsVO settings = getTdf().createRotateSettings(0, 0, 360, 17, null, null);
		Integer result = fixture.getTotalNumberOfImages(settings);
		assertEquals(Integer.valueOf(21), result);
	}
	
	@Test
	public void testGenerateFrames() throws IOException, URISyntaxException, InterruptedException {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_dad_large.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		RotationSettingsVO rotateSettings = getTdf().createRotateSettings(300, 615, 360, 45, null, null);
		SettingsVO settings = getTdf().createSettingsWithRotate(programSettings, rotateSettings );
		
		FrameHandlerVO frameHandlerVO = fixture.generateFrames(settings);
		Integer numTasks = frameHandlerVO.getNumTasks();
		
		verifyTasks(frameHandlerVO, numTasks);
		verifyFrames(programSettings, numTasks);
	}
	
	@Test
	public void testGetAlFrameSize() throws Exception {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_dad_large.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, tempOutputDir, "test_");
		
		RotationSettingsVO rotateSettings = getTdf().createRotateSettings(300, 615, 360, 45, null, null);
		SettingsVO settings = getTdf().createSettingsWithRotate(programSettings, rotateSettings );
		
		Double allFrameSize = fixture.getAllFrameSize(settings);
		assertNotNull(allFrameSize);
		
		Double allFrameSizeMegaBytes = allFrameSize / 1024 / 1024;
		assertEquals(Double.valueOf("90.2623"), allFrameSizeMegaBytes.doubleValue(), DELTA);
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

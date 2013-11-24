package com.framegen.cmdline.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.cmdline.FramegenCmdLineTestCase;
import com.framegen.cmdline.tdf.GeneratedImageFilter;

public class FrameGeneratorTest extends FramegenCmdLineTestCase {
	
	private FrameGenerator fixture;
	private ProgramSettingsParser settingsParser;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new FrameGenerator();
		settingsParser = new ProgramSettingsParser();
	}

	@Test
	public void testExecute_line() throws IOException, InterruptedException, URISyntaxException {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base.jpg");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay.png");
		
		String pathToBaseImage = baseImage.getAbsolutePath();
		String pathToOverlayImage = overlayImage.getAbsolutePath();
		String pathToOutputDir = tempOutputDir.getAbsolutePath();
		
		String[] args = { "-baseImage", pathToBaseImage, "-overlayImage", pathToOverlayImage, "-outputDir", pathToOutputDir,
				"LINE", "--xStart=5", "--yStart=5", "--xEnd=25", "--yEnd=25"};
		SettingsVO settings = settingsParser.parseAll(args);
		
		FrameHandlerVO frameHandlerVO = fixture.execute(settings);
		Integer numTasks = frameHandlerVO.getNumTasks();
		
		verifyTasksWereCompleted(settings, frameHandlerVO, numTasks);
		verifyFramesWereGenerated(settings.getProgramSettings(), numTasks);
	}
	
	private void verifyTasksWereCompleted(SettingsVO settingsVO, FrameHandlerVO frameHandlerVO, Integer numTasks) {
		for (int i=0; i<numTasks; i++) {
			String status = fixture.getStatus(settingsVO, frameHandlerVO);
			assertTrue(StringUtils.isNotEmpty(status));
		}
	}
	
	private void verifyFramesWereGenerated(ProgramSettingsVO programSettings, Integer numTasks) {
		File[] actualFiles = tempOutputDir.listFiles(new GeneratedImageFilter(programSettings.getGeneratedImageNamePrefix(), ".png"));
		assertEquals(numTasks.intValue(), actualFiles.length);
	}

}

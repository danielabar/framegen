package com.framegen.core.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.response.TaskResultVO;
import com.framegen.core.FramegenCoreTestCase;
import com.framegen.core.taskvo.RotateTaskVO;

public class RotateOverlayTaskIntTest extends FramegenCoreTestCase {
	
	private RotateOverlayTask<TaskResultVO> fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testOverlay() throws Exception {
		BufferedImage baseImage = getTdf().createBufferedImageFromResource("base_canon.jpg");
		BufferedImage overlay = getTdf().createBufferedImageFromResource("overlay_dad_large.png");
		File outputDir = tempOutputDir;
		
		Integer xCoordinate = 300;
		Integer yCoordinate = 615;
		Integer rotationDegrees = 45;
		
		String combinedFileNamePrefix = "task_test_";
		Integer sequence = 1;
		Integer numberOfPadChars = 4;
		Integer totalNumberOfImages = 5;
		
		RotateTaskVO rotateTaskVO = new RotateTaskVO(baseImage, overlay, outputDir, xCoordinate, yCoordinate, rotationDegrees, 
				combinedFileNamePrefix, sequence, numberOfPadChars, totalNumberOfImages);
		
		fixture = new RotateOverlayTask<TaskResultVO>(rotateTaskVO );
		TaskResultVO taskResultVO = fixture.overlay();
		
		assertNotNull(taskResultVO);
		assertNotNull(taskResultVO.getFrameSize());
		
		File[] generatedFiles = getGeneratedFiles(outputDir, combinedFileNamePrefix);
		assertEquals(1, generatedFiles.length);
		assertEquals(generatedFiles[0].length(), taskResultVO.getFrameSize().longValue());
		
		Double frameSizeMegaBytes = taskResultVO.getFrameSize() / 1024 / 1024;
		assertEquals(Double.valueOf("11.2827"), frameSizeMegaBytes.doubleValue(), DELTA);
	}

}

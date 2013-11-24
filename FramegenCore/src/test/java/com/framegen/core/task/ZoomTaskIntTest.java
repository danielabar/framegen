package com.framegen.core.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.response.TaskResultVO;
import com.framegen.core.FramegenCoreTestCase;
import com.framegen.core.taskvo.ZoomTaskVO;

public class ZoomTaskIntTest extends FramegenCoreTestCase {
	
	private ZoomTask<TaskResultVO> fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testOverlay() throws Exception {
		BufferedImage baseImage = getTdf().createBufferedImageFromResource("house.jpg");
		File outputDir = tempOutputDir;
		
		Double xZoom = Double.valueOf("1120");
		Double yZoom = Double.valueOf("1800");
		Double zoomFactor = Double.valueOf("1.0");
		
		String combinedFileNamePrefix = "task_test_";
		Integer sequence = 1;
		Integer numberOfPadChars = 4;
		Integer totalNumberOfImages = 5;
		
		ZoomTaskVO zoomTaskVO = new ZoomTaskVO(baseImage, outputDir, xZoom, yZoom, zoomFactor, 
				combinedFileNamePrefix, sequence, numberOfPadChars, totalNumberOfImages);
		
		fixture = new ZoomTask<TaskResultVO>(zoomTaskVO );
		TaskResultVO taskResultVO = fixture.overlay();
		
		assertNotNull(taskResultVO);
		assertNotNull(taskResultVO.getFrameSize());
		
		File[] generatedFiles = getGeneratedFiles(outputDir, combinedFileNamePrefix);
		assertEquals(1, generatedFiles.length);
		assertEquals(generatedFiles[0].length(), taskResultVO.getFrameSize().longValue());
		
		Double frameSizeMegaBytes = taskResultVO.getFrameSize() / 1024 / 1024;
		assertEquals(Double.valueOf("14.0417"), frameSizeMegaBytes.doubleValue(), DELTA);
	}

}

package com.framegen.core.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.response.TaskResultVO;
import com.framegen.core.FramegenCoreTestCase;
import com.framegen.core.taskvo.LineTaskVO;

public class LineOverlayTaskIntTest extends FramegenCoreTestCase {
	
	private LineOverlayTask<TaskResultVO> fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testOverlay() throws Exception {
		BufferedImage baseImage = getTdf().createBufferedImageFromResource("base_canon.jpg");
		BufferedImage overlay = getTdf().createBufferedImageFromResource("overlay_dad.png");
		File outputDir = tempOutputDir;
		Double xCoordinate = Double.valueOf("10");
		Double yCoordinate = Double.valueOf("20");
		Integer width = baseImage.getWidth();
		Integer height = baseImage.getHeight();
		String combinedFileNamePrefix = "task_test_";
		Integer sequence = 1;
		Integer numberOfPadChars = 4;
		Integer totalNumberOfImages = 5;
		
		LineTaskVO lineTaskVO = new LineTaskVO(baseImage, overlay, outputDir, xCoordinate, yCoordinate, width, height, combinedFileNamePrefix, 
				sequence, numberOfPadChars, totalNumberOfImages);
		
		fixture = new LineOverlayTask<TaskResultVO>(lineTaskVO );
		TaskResultVO taskResultVO = fixture.overlay();
		
		assertNotNull(taskResultVO);
		assertNotNull(taskResultVO.getFrameSize());
		
		File[] generatedFiles = getGeneratedFiles(outputDir, combinedFileNamePrefix);
		assertEquals(1, generatedFiles.length);
		assertEquals(generatedFiles[0].length(), taskResultVO.getFrameSize().longValue());
		
		Double frameSizeMegaBytes = taskResultVO.getFrameSize() / 1024 / 1024;
		assertEquals(Double.valueOf("10.8465"), frameSizeMegaBytes.doubleValue(), DELTA);
	}

}

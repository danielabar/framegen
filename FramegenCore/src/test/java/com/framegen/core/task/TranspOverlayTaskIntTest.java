package com.framegen.core.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.response.TaskResultVO;
import com.framegen.core.FramegenCoreTestCase;
import com.framegen.core.taskvo.TranspTaskVO;

public class TranspOverlayTaskIntTest extends FramegenCoreTestCase {
	
	private TranspOverlayTask<TaskResultVO> fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testOverlay() throws Exception {
		BufferedImage baseImage = getTdf().createBufferedImageFromResource("condo_hallway.jpg");
		BufferedImage overlay = getTdf().createBufferedImageFromResource("condo_kitchen.jpg");
		File outputDir = tempOutputDir;
		
		Double xPos = Double.valueOf("0");
		Double yPos = Double.valueOf("0");
		float alpha = 0.5f;
		
		Integer width = baseImage.getWidth();
		Integer height = baseImage.getHeight();
		String combinedFileNamePrefix = "task_test_";
		Integer sequence = 1;
		Integer numberOfPadChars = 4;
		Integer totalNumberOfImages = 5;
		
		TranspTaskVO transpTaskVO = new TranspTaskVO(baseImage, overlay, outputDir, xPos, yPos, width, height, 
				combinedFileNamePrefix, sequence, numberOfPadChars, totalNumberOfImages, alpha);
		
		fixture = new TranspOverlayTask<TaskResultVO>(transpTaskVO );
		TaskResultVO taskResultVO = fixture.overlay();
		
		assertNotNull(taskResultVO);
		assertNotNull(taskResultVO.getFrameSize());
		
		File[] generatedFiles = getGeneratedFiles(outputDir, combinedFileNamePrefix);
		assertEquals(1, generatedFiles.length);
		assertEquals(generatedFiles[0].length(), taskResultVO.getFrameSize().longValue());
		
		Double frameSizeMegaBytes = taskResultVO.getFrameSize() / 1024 / 1024;
		assertEquals(Double.valueOf("0.5723"), frameSizeMegaBytes.doubleValue(), DELTA);
	}

}

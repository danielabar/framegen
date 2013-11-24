package com.framegen.core.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.response.TaskResultVO;
import com.framegen.core.FramegenCoreTestCase;
import com.framegen.core.taskvo.OffsetFilterTaskVO;

public class OffsetFilterTaskIntTest extends FramegenCoreTestCase {
	
	private OffsetFilterTask<TaskResultVO> fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testOverlay() throws Exception {
		BufferedImage baseImage = getTdf().createBufferedImageFromResource("miami_beach.jpg");
		File outputDir = tempOutputDir;
		Float multiplier = Float.valueOf("1.0");
		Float offset = Float.valueOf("100");
		String combinedFileNamePrefix = "task_test_";
		Integer sequence = 1;
		Integer numberOfPadChars = 4;
		Integer totalNumberOfImages = 5;
		
		OffsetFilterTaskVO offsetFilterTaskVO = new OffsetFilterTaskVO(baseImage, outputDir, multiplier, offset, combinedFileNamePrefix, 
				sequence, numberOfPadChars, totalNumberOfImages);
		
		fixture = new OffsetFilterTask<TaskResultVO>(offsetFilterTaskVO );
		TaskResultVO taskResultVO = fixture.overlay();
		
		assertNotNull(taskResultVO);
		assertNotNull(taskResultVO.getFrameSize());
		
		File[] generatedFiles = getGeneratedFiles(outputDir, combinedFileNamePrefix);
		assertEquals(1, generatedFiles.length);
		assertEquals(generatedFiles[0].length(), taskResultVO.getFrameSize().longValue());
		
		Double frameSizeMegaBytes = taskResultVO.getFrameSize() / 1024 / 1024;
		assertEquals(Double.valueOf("6.1773"), frameSizeMegaBytes.doubleValue(), DELTA);
	}

}

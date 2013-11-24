package com.framegen.core.task;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.framegen.api.response.TaskResultVO;
import com.framegen.core.taskvo.OffsetFilterTaskVO;
import com.framegen.core.util.IOutputUtil;
import com.framegen.core.util.OutputUtilImpl;
import com.framegen.core.util.offset.IOffsetFilter;
import com.framegen.core.util.offset.OffsetFilterImpl;

public class OffsetFilterTask<V> extends TaskBase<V> implements Callable<TaskResultVO> {
	
	private static final Logger LOGGER = Logger.getLogger(OffsetFilterTask.class);
	
	private final OffsetFilterTaskVO requestVO;
	private final IOutputUtil outputUtil;
	private final IOffsetFilter offsetFilter;
	
	public OffsetFilterTask(OffsetFilterTaskVO requestVO) {
		super();
		this.requestVO = requestVO;
		this.outputUtil = new OutputUtilImpl();
		this.offsetFilter = new OffsetFilterImpl();
	}
	
	@Override
	public TaskResultVO call() throws Exception {
		return overlay();
	}

	public TaskResultVO overlay() throws Exception {
    	LOGGER.info(outputUtil.getStatusMessage(requestVO));
    	BufferedImage filteredImage = offsetFilter.getFilteredImage(requestVO.getImage(), requestVO.getMultiplier(), requestVO.getOffset());
    	String combinedFileName = outputUtil.generateCombinedFileName(requestVO.getSequence(), requestVO.getCombinedFileNamePrefix(), requestVO.getNumberOfPadChars());
    	File output = new File(requestVO.getOutputDir(), combinedFileName);
		ImageIO.write(filteredImage, "PNG", output);
    	
    	return buildTaskResultVO(output, requestVO.getSequence());
	}
	
}

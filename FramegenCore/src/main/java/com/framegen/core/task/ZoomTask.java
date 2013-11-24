package com.framegen.core.task;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.framegen.api.response.TaskResultVO;
import com.framegen.core.taskvo.ZoomTaskVO;
import com.framegen.core.util.IOutputUtil;
import com.framegen.core.util.OutputUtilImpl;
import com.framegen.core.util.zoom.IZoomer;
import com.framegen.core.util.zoom.ZoomerImpl;

public class ZoomTask<V> extends TaskBase<V> implements Callable<TaskResultVO> {
	
	private static final Logger LOGGER = Logger.getLogger(ZoomTask.class);
	
	private final ZoomTaskVO requestVO;
	private final IOutputUtil outputUtil;
	private final IZoomer zoomer;

	public ZoomTask(ZoomTaskVO zoomTaskVO) {
		super();
		this.requestVO = zoomTaskVO;
		this.outputUtil = new OutputUtilImpl();
		this.zoomer = new ZoomerImpl();
	}

	@Override
	public TaskResultVO call() throws Exception {
		return overlay();
	}

	public TaskResultVO overlay() throws Exception {
    	LOGGER.info(outputUtil.getStatusMessage(requestVO));
    	BufferedImage zoomedImage = zoomer.getZoomedImage(requestVO.getImage(), requestVO.getxZoom(), requestVO.getyZoom(), requestVO.getZoomFactor());
    	String combinedFileName = outputUtil.generateCombinedFileName(requestVO.getSequence(), requestVO.getCombinedFileNamePrefix(), requestVO.getNumberOfPadChars());
    	File output = new File(requestVO.getOutputDir(), combinedFileName);
		ImageIO.write(zoomedImage, "PNG", output);
    	
    	return buildTaskResultVO(output, requestVO.getSequence());
	}

}

package com.framegen.core.task;

import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.framegen.api.response.TaskResultVO;
import com.framegen.core.taskvo.RotateTaskVO;
import com.framegen.core.util.*;
import com.framegen.core.util.resize.IResizer;
import com.framegen.core.util.resize.ResizerImpl;
import com.framegen.core.util.rotate.IRotater;
import com.framegen.core.util.rotate.RotaterImpl;

public class RotateOverlayTask<V> extends TaskBase<V> implements Callable<TaskResultVO> {
	
	private static final Logger LOGGER = Logger.getLogger(RotateOverlayTask.class);
	
	private final RotateTaskVO requestVO;
	private final IOutputUtil outputUtil;
	private final IResizer resizer;
	private final IRotater rotater;
	
	public RotateOverlayTask(RotateTaskVO requestVO) {
		super();
		this.requestVO = requestVO;
		this.outputUtil = new OutputUtilImpl();
		this.resizer = new ResizerImpl();
		this.rotater = new RotaterImpl();
	}

	@Override
	public TaskResultVO call() throws Exception {
		return overlay();
	}
	
	public TaskResultVO overlay() throws Exception {
    	
    	LOGGER.info(outputUtil.getStatusMessage(requestVO));
    	
    	BufferedImage combined = new BufferedImage(requestVO.getImage().getWidth(), requestVO.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
    	BufferedImage rotatedImage = rotater.getRotatedImage(getOverlay(requestVO), requestVO.getRotationDegress());
		
		Graphics g = combined.getGraphics();
    	g.drawImage(requestVO.getImage(), 0, 0, null);
    	g.drawImage(rotatedImage, requestVO.getxCoordinate(), requestVO.getyCoordinate(), null);
    	
    	String combinedFileName = outputUtil.generateCombinedFileName(requestVO.getSequence(), requestVO.getCombinedFileNamePrefix(), requestVO.getNumberOfPadChars());
    	File output = new File(requestVO.getOutputDir(), combinedFileName);
		ImageIO.write(combined, "PNG", output);
    	g.dispose();
    	
    	return buildTaskResultVO(output, requestVO.getSequence());
    }
	
	protected BufferedImage getOverlay(RotateTaskVO requestVO) {
		if (requestVO.hasScaleRequest()) {
			return resizer.getScaledInstance(requestVO.getOverlay(), requestVO.getScaleRequest().getTargetWidth(), requestVO.getScaleRequest().getTargetHeight(), 
					RenderingHints.VALUE_INTERPOLATION_BICUBIC, false);
		}
		return requestVO.getOverlay();
	}

}

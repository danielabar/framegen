package com.framegen.core.task;

import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.framegen.api.response.TaskResultVO;
import com.framegen.core.taskvo.LineTaskVO;
import com.framegen.core.util.*;
import com.framegen.core.util.resize.IResizer;
import com.framegen.core.util.resize.ResizerImpl;
import com.framegen.core.util.rotate.IRotater;
import com.framegen.core.util.rotate.RotaterImpl;

public class LineOverlayTask<V> extends TaskBase<V> implements Callable<TaskResultVO> {
	
	private static final Logger LOGGER = Logger.getLogger(LineOverlayTask.class);
	
	private final LineTaskVO requestVO;
	private final IOutputUtil outputUtil;
	private final IResizer resizer;
	private final IRotater rotater;
	
	public LineOverlayTask(LineTaskVO requestVO) {
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
    	
    	BufferedImage combined = new BufferedImage(requestVO.getWidth(), requestVO.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	Graphics g = combined.getGraphics();
    	g.drawImage(requestVO.getImage(), 0, 0, null);
    	g.drawImage(getOverlay(requestVO), requestVO.getxCoordinate().intValue(), requestVO.getyCoordinate().intValue(), null);

    	String combinedFileName = outputUtil.generateCombinedFileName(requestVO.getSequence(), requestVO.getCombinedFileNamePrefix(), requestVO.getNumberOfPadChars());
    	File output = new File(requestVO.getOutputDir(), combinedFileName);
		ImageIO.write(combined, "PNG", output);
    	g.dispose();
    	
    	return buildTaskResultVO(output, this.requestVO.getSequence());
    }

	private BufferedImage getOverlay(LineTaskVO lineTaskVO) {
		BufferedImage scaledImage = getScaledImage(lineTaskVO.getOverlay(), lineTaskVO);
		BufferedImage rotatedImage = getRotatedImage(scaledImage, lineTaskVO);
		return rotatedImage;
	}
	
	private BufferedImage getScaledImage(BufferedImage inputImage, LineTaskVO lineTaskVO) {
		if (lineTaskVO.hasScaleRequest()) {
			return resizer.getScaledInstance(inputImage, lineTaskVO.getScaleRequest().getTargetWidth(), lineTaskVO.getScaleRequest().getTargetHeight(), 
					RenderingHints.VALUE_INTERPOLATION_BICUBIC, false);
		}
		return lineTaskVO.getOverlay();
	}
	
	private BufferedImage getRotatedImage(BufferedImage inputImage, LineTaskVO lineTaskVO) {
		if (lineTaskVO.hasRotationRequest()) {
			return rotater.getRotatedImage(inputImage, lineTaskVO.getRotateBy());
		}
		return lineTaskVO.getOverlay();
	
	}

}

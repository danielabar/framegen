package com.framegen.core.task;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.framegen.api.response.TaskResultVO;
import com.framegen.core.taskvo.TranspTaskVO;
import com.framegen.core.util.IOutputUtil;
import com.framegen.core.util.OutputUtilImpl;
import com.framegen.core.util.transp.ITransparency;
import com.framegen.core.util.transp.TransparencyImpl;

public class TranspOverlayTask<V> extends TaskBase<V> implements Callable<TaskResultVO> {
	
	private static final Logger LOGGER = Logger.getLogger(TranspOverlayTask.class);
	
	private final TranspTaskVO requestVO;
	private final IOutputUtil outputUtil;
	private final ITransparency transparency;

	public TranspOverlayTask(TranspTaskVO requestVO) {
		super();
		this.requestVO = requestVO;
		this.outputUtil = new OutputUtilImpl();
		this.transparency = new TransparencyImpl();
	}

	@Override
	public TaskResultVO call() throws Exception {
		return overlay();
	}

	public TaskResultVO overlay() throws IOException {
		LOGGER.info(outputUtil.getStatusMessage(requestVO));
		
		BufferedImage combined = new BufferedImage(requestVO.getWidth(), requestVO.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	Graphics g = combined.getGraphics();
    	g.drawImage(requestVO.getImage(), 0, 0, null);
    	g.drawImage(getOverlay(requestVO), requestVO.getXPos().intValue(), requestVO.getYPos().intValue(), null);

    	String combinedFileName = outputUtil.generateCombinedFileName(requestVO.getSequence(), requestVO.getCombinedFileNamePrefix(), requestVO.getNumberOfPadChars());
    	File output = new File(requestVO.getOutputDir(), combinedFileName);
		ImageIO.write(combined, "PNG", output);
    	g.dispose();
    	
    	return buildTaskResultVO(output, requestVO.getSequence());
	}

	private BufferedImage getOverlay(TranspTaskVO requestVO) {
		return transparency.getTransparentImage(requestVO.getOverlay(), requestVO.getAlpha());
	}

}

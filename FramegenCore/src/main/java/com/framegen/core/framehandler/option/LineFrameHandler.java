package com.framegen.core.framehandler.option;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.TaskResultVO;
import com.framegen.api.service.IFrameHandler;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.core.framehandler.FrameHandlerBase;
import com.framegen.core.task.LineOverlayTask;
import com.framegen.core.taskvo.LineTaskVO;
import com.framegen.core.taskvo.ScaleVO;
import com.framegen.core.util.line.ILineHelper;
import com.framegen.core.util.line.LineHelperImpl;

public class LineFrameHandler extends FrameHandlerBase implements IFrameHandler {
	
	static final Logger LOGGER = Logger.getLogger(LineFrameHandler.class);
	static final String PROGRESS_MESSAGE = "Task %d of %d is done";
	
	private final ILineHelper lineHelper;
	private final ExecutorService taskExecutor;
	private final CompletionService<TaskResultVO> taskCompletionService;
	
	public LineFrameHandler() {
		super();
		this.lineHelper = new LineHelperImpl();
		this.taskExecutor = Executors.newFixedThreadPool(getNumberOfThreads());
		this.taskCompletionService = new ExecutorCompletionService<TaskResultVO>(taskExecutor);
	}

	@Override
	public Integer getNumberOfFrames(SettingsVO settings) {
		List<Point2D> path = lineHelper.getPath(settings.getLineSettings());
		return path.size();
	}

	@Override
	public FrameHandlerVO generateFrames(SettingsVO settings) throws IOException, InterruptedException {
		
		BufferedImage baseImage = getBaseImage(settings);
    	BufferedImage overlay = getOverlayImage(settings);
    	
    	int combinedWidth = Math.max(baseImage.getWidth(), overlay.getWidth());
    	int combinedHeight = Math.max(baseImage.getHeight(), overlay.getHeight());
    	
    	List<Point2D> path = lineHelper.getPath(settings.getLineSettings());
    	int imageCount = 0;
    	for (Point2D point2d : path) {
    		
    		LineTaskVO lineTaskVO = new LineTaskVO(baseImage, overlay, settings.getProgramSettings().getOutputDir(), 
    				point2d.getX(), point2d.getY(), combinedWidth, combinedHeight, settings.getProgramSettings().getGeneratedImageNamePrefix(), 
    				imageCount, NUMBER_OF_PAD_CHARS, path.size());
    		setAdditionalOptions(settings, overlay, imageCount, lineTaskVO, path.size());
    		
    		LineOverlayTask<TaskResultVO> task = new LineOverlayTask<TaskResultVO>(lineTaskVO);
    		taskCompletionService.submit(task);
    		imageCount++;
		}
    	
    	return buildFrameHandlerResponse(taskCompletionService, imageCount);
	}

	protected void setAdditionalOptions(SettingsVO settings, BufferedImage overlay, int imageCount, LineTaskVO lineTaskVO, int totalNumberOfImages) {
		setScaleOption(settings, overlay, imageCount, lineTaskVO);
		setRotationOption(lineTaskVO, settings, imageCount, totalNumberOfImages);
	}

	protected void setScaleOption(SettingsVO settings, BufferedImage overlay, int imageCount, LineTaskVO lineTaskVO) {
		lineTaskVO.setScaleRequest(getScaleRequest(settings.getLineSettings(), imageCount, overlay));
	}
	
	protected ScaleVO getScaleRequest(LineSettingsVO lineSettings, int imageCount, BufferedImage imageToScale) {
		if (lineSettings.scalingRequested() && isNotFirstInSequence(imageCount)) {
			ScaleVO vo = new ScaleVO();
			vo.setTargetHeight(imageCount * lineSettings.getyScale() + imageToScale.getHeight());
			vo.setTargetWidth(imageCount * lineSettings.getxScale() + imageToScale.getWidth());
			return vo;
		}
		return null;
	}
	
	protected void setRotationOption(LineTaskVO lineTaskVO, SettingsVO settings, int imageCount, int totalNumberOfImages) {
		if (settings.getLineSettings().rotationRequested()) {
			lineTaskVO.setRotateBy(calculateRotation(settings, imageCount, totalNumberOfImages));
		}
	}

	protected int calculateRotation(SettingsVO settings, int imageCount, int totalNumberOfImages) {
		//FIXME make this a setting if user wants last rotation to "straighten out"
		if (imageCount == totalNumberOfImages-1) {
			return 0;
		}
		return (settings.getLineSettings().getRotateBy() * imageCount) % 360;
	}

	@Override
	public Double getAllFrameSize(SettingsVO settings) throws Exception {
		BufferedImage baseImage = getBaseImage(settings);
    	BufferedImage overlay = getOverlayImage(settings);
    	
    	int combinedWidth = Math.max(baseImage.getWidth(), overlay.getWidth());
    	int combinedHeight = Math.max(baseImage.getHeight(), overlay.getHeight());
    	
    	List<Point2D> path = lineHelper.getPath(settings.getLineSettings());
    	
    	LineTaskVO lineTaskVO = new LineTaskVO(baseImage, overlay, settings.getProgramSettings().getOutputDir(), 
				path.get(0).getX(), path.get(0).getY(), combinedWidth, combinedHeight, settings.getProgramSettings().getGeneratedImageNamePrefix(), 
				0, NUMBER_OF_PAD_CHARS, path.size());
		setAdditionalOptions(settings, overlay, 0, lineTaskVO, path.size());
		
		LineOverlayTask<TaskResultVO> task = new LineOverlayTask<TaskResultVO>(lineTaskVO);
		TaskResultVO overlayResultVO = task.overlay();
    	
		return overlayResultVO.getFrameSize() * getNumberOfFrames(settings);
	}

}

package com.framegen.core.framehandler.option;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.TaskResultVO;
import com.framegen.api.service.IFrameHandler;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.core.framehandler.FrameHandlerBase;
import com.framegen.core.task.LineOverlayTask;
import com.framegen.core.taskvo.LineTaskVO;
import com.framegen.core.taskvo.ScaleVO;
import com.framegen.core.util.IOutputUtil;
import com.framegen.core.util.OutputUtilImpl;
import com.framegen.core.util.arc.ArcHelperImpl;
import com.framegen.core.util.arc.IArcHelper;

public class ArcFrameHandler extends FrameHandlerBase implements IFrameHandler {
	
	private final IArcHelper arcHelper;
	private final IOutputUtil outputUtil;
	private final ExecutorService taskExecutor;
	private final CompletionService<TaskResultVO> taskCompletionService;
	
	public ArcFrameHandler() {
		super();
		this.arcHelper = new ArcHelperImpl();
		this.outputUtil = new OutputUtilImpl();
		this.taskExecutor = Executors.newFixedThreadPool(getNumberOfThreads());
		this.taskCompletionService = new ExecutorCompletionService<TaskResultVO>(taskExecutor);
	}

	@Override
	public Integer getNumberOfFrames(SettingsVO settings) throws IOException {
		BufferedImage baseImage = getBaseImage(settings);
		List<Point2D> path = arcHelper.getPointsAlongArc(settings.getArcSettings(), baseImage);
		return path.size();
	}

	@Override
	public FrameHandlerVO generateFrames(SettingsVO settings) throws IOException, InterruptedException {
		
		BufferedImage baseImage = getBaseImage(settings);
    	BufferedImage overlay = getOverlayImage(settings);
    	
    	int combinedWidth = Math.max(baseImage.getWidth(), overlay.getWidth());
    	int combinedHeight = Math.max(baseImage.getHeight(), overlay.getHeight());
    	
    	List<Point2D> path = arcHelper.getPointsAlongArc(settings.getArcSettings(), baseImage);
    	savePoints(settings, baseImage, path);
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

	private void savePoints(SettingsVO settings, BufferedImage baseImage, List<Point2D> path) {
		if (settings.getProgramSettings().isGenPointsCsv()) {
    		outputUtil.generatePointsCsvFile(settings.getProgramSettings(), baseImage, path);
    	}
	}

	private void setAdditionalOptions(SettingsVO settings, BufferedImage overlay, int imageCount, LineTaskVO lineTaskVO, int totalNumberOfImages) {
		setScaleOption(settings, overlay, imageCount, lineTaskVO);
		setRotationOption(lineTaskVO, settings, imageCount, totalNumberOfImages);
	}

	private void setScaleOption(SettingsVO settings, BufferedImage overlay, int imageCount, LineTaskVO lineTaskVO) {
		lineTaskVO.setScaleRequest(getScaleRequest(settings.getArcSettings(), imageCount, overlay));
	}
	
	private ScaleVO getScaleRequest(ArcSettingsVO arcSettings, int imageCount, BufferedImage imageToScale) {
		if (arcSettings.scalingRequested() && isNotFirstInSequence(imageCount)) {
			ScaleVO vo = new ScaleVO();
			vo.setTargetHeight(imageCount * arcSettings.getyScale() + imageToScale.getHeight());
			vo.setTargetWidth(imageCount * arcSettings.getxScale() + imageToScale.getWidth());
			return vo;
		}
		return null;
	}
	
	private void setRotationOption(LineTaskVO lineTaskVO, SettingsVO settings, int imageCount, int totalNumberOfImages) {
		if (settings.getArcSettings().rotationRequested()) {
			lineTaskVO.setRotateBy(calculateRotation(settings, imageCount, totalNumberOfImages));
		}
	}

	private int calculateRotation(SettingsVO settings, int imageCount, int totalNumberOfImages) {
		//FIXME make this a setting if user wants last rotation to "straighten out"
		if (imageCount == totalNumberOfImages-1) {
			return 0;
		}
		return (settings.getArcSettings().getRotateBy() * imageCount) % 360;
	}

	@Override
	public Double getAllFrameSize(SettingsVO settings) throws Exception {
		BufferedImage baseImage = getBaseImage(settings);
    	BufferedImage overlay = getOverlayImage(settings);
    	
    	int combinedWidth = Math.max(baseImage.getWidth(), overlay.getWidth());
    	int combinedHeight = Math.max(baseImage.getHeight(), overlay.getHeight());
    	
    	List<Point2D> path = arcHelper.getPointsAlongArc(settings.getArcSettings(), baseImage);
    	savePoints(settings, baseImage, path);
    	int imageCount = 0;
    	
    	LineTaskVO lineTaskVO = new LineTaskVO(baseImage, overlay, settings.getProgramSettings().getOutputDir(), 
				path.get(0).getX(), path.get(0).getY(), combinedWidth, combinedHeight, settings.getProgramSettings().getGeneratedImageNamePrefix(), 
				imageCount, NUMBER_OF_PAD_CHARS, path.size());
    	setAdditionalOptions(settings, overlay, imageCount, lineTaskVO, path.size());
		
		LineOverlayTask<TaskResultVO> task = new LineOverlayTask<TaskResultVO>(lineTaskVO);
		TaskResultVO overlayResultVO = task.overlay();
    	
		return overlayResultVO.getFrameSize() * getNumberOfFrames(settings);
	}

}

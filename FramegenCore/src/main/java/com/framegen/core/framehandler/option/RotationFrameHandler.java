package com.framegen.core.framehandler.option;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.*;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.TaskResultVO;
import com.framegen.api.service.IFrameHandler;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.RotationSettingsVO;
import com.framegen.core.framehandler.FrameHandlerBase;
import com.framegen.core.task.RotateOverlayTask;
import com.framegen.core.taskvo.RotateTaskVO;
import com.framegen.core.taskvo.ScaleVO;

public class RotationFrameHandler extends FrameHandlerBase implements IFrameHandler {
	
	private final ExecutorService taskExecutor;
	private final CompletionService<TaskResultVO> taskCompletionService;

	public RotationFrameHandler() {
		super();
		this.taskExecutor = Executors.newFixedThreadPool(getNumberOfThreads());
		this.taskCompletionService = new ExecutorCompletionService<TaskResultVO>(taskExecutor);
	}

	@Override
	public Integer getNumberOfFrames(SettingsVO settings) throws IOException {
		return getTotalNumberOfImages(settings.getRotationSettings());
	}

	@Override
	public FrameHandlerVO generateFrames(SettingsVO settings) throws IOException, InterruptedException {
		
		BufferedImage baseImage = getBaseImage(settings);
    	BufferedImage overlay = getOverlayImage(settings);
    	
    	int imageCount = 0;
    	
    	RotationSettingsVO rotationSettings = settings.getRotationSettings();
		for (int rotationDegree = 0; rotationDegree <= rotationSettings.getDegrees(); rotationDegree+=rotationSettings.getStep()) {
    		RotateTaskVO vo = new RotateTaskVO(baseImage, overlay, settings.getProgramSettings().getOutputDir(), 
    				rotationSettings.getxPosition(), rotationSettings.getyPosition(), rotationDegree, 
    				settings.getProgramSettings().getGeneratedImageNamePrefix(), imageCount, NUMBER_OF_PAD_CHARS, getTotalNumberOfImages(rotationSettings));
    		vo.setScaleRequest(getScaleRequest(settings.getRotationSettings(), imageCount, overlay));
    		
    		Callable<TaskResultVO> task = new RotateOverlayTask<TaskResultVO>(vo);
    		taskCompletionService.submit(task);
    		imageCount++;
    	}
		return buildFrameHandlerResponse(taskCompletionService, imageCount);
	}

	protected Integer getTotalNumberOfImages(RotationSettingsVO settings) {
		Integer result = settings.getDegrees() / settings.getStep();
		return result;
	}
	
	protected ScaleVO getScaleRequest(RotationSettingsVO rotationSettings, int imageCount, BufferedImage imageToScale) {
		if (rotationSettings.scalingRequested() && isNotFirstInSequence(imageCount)) {
			ScaleVO vo = new ScaleVO();
			vo.setTargetHeight(imageCount * rotationSettings.getyScale() + imageToScale.getHeight());
			vo.setTargetWidth(imageCount * rotationSettings.getxScale() + imageToScale.getWidth());
			return vo;
		}
		return null;
	}

	@Override
	public Double getAllFrameSize(SettingsVO settings) throws Exception {
		BufferedImage baseImage = getBaseImage(settings);
    	BufferedImage overlay = getOverlayImage(settings);
    	
    	int imageCount = 0;
    	
    	RotationSettingsVO rotationSettings = settings.getRotationSettings();
    	
    	RotateTaskVO rotateTaskVO = new RotateTaskVO(baseImage, overlay, settings.getProgramSettings().getOutputDir(), 
				rotationSettings.getxPosition(), rotationSettings.getyPosition(), rotationSettings.getStep(), 
				settings.getProgramSettings().getGeneratedImageNamePrefix(), imageCount, NUMBER_OF_PAD_CHARS, getTotalNumberOfImages(rotationSettings));
		rotateTaskVO.setScaleRequest(getScaleRequest(settings.getRotationSettings(), imageCount, overlay));
		
		RotateOverlayTask<TaskResultVO> task = new RotateOverlayTask<TaskResultVO>(rotateTaskVO);
		TaskResultVO overlayResultVO = task.overlay();
    	
		return overlayResultVO.getFrameSize() * getNumberOfFrames(settings);
	}

}

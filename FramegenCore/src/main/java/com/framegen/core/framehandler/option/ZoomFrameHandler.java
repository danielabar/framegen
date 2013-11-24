package com.framegen.core.framehandler.option;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.TaskResultVO;
import com.framegen.api.service.IFrameHandler;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.ZoomSettingsVO;
import com.framegen.core.framehandler.FrameHandlerBase;
import com.framegen.core.task.ZoomTask;
import com.framegen.core.taskvo.ZoomTaskVO;

public class ZoomFrameHandler extends FrameHandlerBase implements IFrameHandler {
	
	private final ExecutorService taskExecutor;
	private final CompletionService<TaskResultVO> taskCompletionService;

	public ZoomFrameHandler() {
		super();
		this.taskExecutor = Executors.newFixedThreadPool(getNumberOfThreads());
		this.taskCompletionService = new ExecutorCompletionService<TaskResultVO>(taskExecutor);
	}

	@Override
	public Integer getNumberOfFrames(SettingsVO settings) throws IOException {
		ZoomSettingsVO zoomSettings = settings.getZoomSettings();
    	return zoomSettings.getNumberOfZooms();
	}

	@Override
	public FrameHandlerVO generateFrames(SettingsVO settings) throws IOException, InterruptedException {
		BufferedImage baseImage = getBaseImage(settings);
    	ZoomSettingsVO zoomSettings = settings.getZoomSettings();
    	Integer numberOfZooms = zoomSettings.getNumberOfZooms();
    	
    	for (int i = 0; i<numberOfZooms; i++) {
    		ZoomTaskVO zoomTaskVO = new ZoomTaskVO(baseImage, settings.getProgramSettings().getOutputDir(), 
    				getXZoom(zoomSettings, baseImage), getYZoom(zoomSettings, baseImage), getZoomFactor(zoomSettings, i), 
    				settings.getProgramSettings().getGeneratedImageNamePrefix(), i, NUMBER_OF_PAD_CHARS, zoomSettings.getNumberOfZooms());
    		ZoomTask<TaskResultVO> task = new ZoomTask<TaskResultVO>(zoomTaskVO);
    		taskCompletionService.submit(task);
    	}
    	return buildFrameHandlerResponse(taskCompletionService, numberOfZooms);
	}

	private Double getXZoom(ZoomSettingsVO zoomSettings, BufferedImage baseImage) {
		if (zoomSettings.userSpecifiedZoomCenter()) {
			return zoomSettings.getxZoom();
		}
		return (double) (baseImage.getWidth() / 2);
	}

	private Double getYZoom(ZoomSettingsVO zoomSettings, BufferedImage baseImage) {
		if (zoomSettings.userSpecifiedZoomCenter()) {
			return zoomSettings.getyZoom();
		}
		return (double) (baseImage.getHeight() / 2);
	}

	private Double getZoomFactor(ZoomSettingsVO zoomSettings, int i) {
		Double start = zoomSettings.getZoomFactor();
		Double increment = zoomSettings.getZoomIncrement() * i;
		return start + increment;
	}

	@Override
	public Double getAllFrameSize(SettingsVO settings) throws Exception {
		BufferedImage baseImage = getBaseImage(settings);
    	ZoomSettingsVO zoomSettings = settings.getZoomSettings();
    	
    	ZoomTaskVO zoomTaskVO = new ZoomTaskVO(baseImage, settings.getProgramSettings().getOutputDir(), 
				getXZoom(zoomSettings, baseImage), getYZoom(zoomSettings, baseImage), getZoomFactor(zoomSettings, 1), 
				settings.getProgramSettings().getGeneratedImageNamePrefix(), 1, NUMBER_OF_PAD_CHARS, zoomSettings.getNumberOfZooms());
		ZoomTask<TaskResultVO> task = new ZoomTask<TaskResultVO>(zoomTaskVO);
		TaskResultVO overlayResultVO = task.overlay();
		
		return overlayResultVO.getFrameSize() * getNumberOfFrames(settings);
	}

}

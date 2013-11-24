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
import com.framegen.api.settings.option.NegativeSettingsVO;
import com.framegen.core.framehandler.FrameHandlerBase;
import com.framegen.core.task.OffsetFilterTask;
import com.framegen.core.taskvo.OffsetFilterTaskVO;

public class NegativeFrameHandler extends FrameHandlerBase implements IFrameHandler {
	
	private static final Float MULTIPLIER_START = Float.valueOf("1.0");
	private static final Float MULTIPLIER_END = Float.valueOf("-1.0");

	private static final Float OFFSET_START = Float.valueOf("0");
	private static final Float OFFSET_END = Float.valueOf("255");
	
	private final ExecutorService taskExecutor;
	private final CompletionService<TaskResultVO> taskCompletionService;

	public NegativeFrameHandler() {
		super();
		this.taskExecutor = Executors.newFixedThreadPool(getNumberOfThreads());
		this.taskCompletionService = new ExecutorCompletionService<TaskResultVO>(taskExecutor);
	}

	@Override
	public Integer getNumberOfFrames(SettingsVO settings) throws IOException {
		NegativeSettingsVO negativeSettings = settings.getNegativeSettings();
		return negativeSettings.getSteps() + 1;
	}

	@Override
	public FrameHandlerVO generateFrames(SettingsVO settings) throws IOException, InterruptedException {
		BufferedImage baseImage = getBaseImage(settings);
		NegativeSettingsVO negativeSettings = settings.getNegativeSettings();
		
		Integer steps = negativeSettings.getSteps();
		Float multiplierIncrement = (MULTIPLIER_START - MULTIPLIER_END) / steps;
		Float offsetIncrement = (OFFSET_END - OFFSET_START) / steps;
		
		for (int i=0; i<steps; i++) {
			OffsetFilterTaskVO offsetFilterTaskVO = new OffsetFilterTaskVO(baseImage, settings.getProgramSettings().getOutputDir(), 
					getMultiplier(i, multiplierIncrement), getOffset(i, offsetIncrement), 
					settings.getProgramSettings().getGeneratedImageNamePrefix(), i, NUMBER_OF_PAD_CHARS, steps); 
			OffsetFilterTask<TaskResultVO> task = new OffsetFilterTask<TaskResultVO>(offsetFilterTaskVO);
			taskCompletionService.submit(task);
		}
		OffsetFilterTaskVO vo = new OffsetFilterTaskVO(baseImage, settings.getProgramSettings().getOutputDir(), 
				MULTIPLIER_END, OFFSET_END, 
				settings.getProgramSettings().getGeneratedImageNamePrefix(), steps, NUMBER_OF_PAD_CHARS, steps); 
		OffsetFilterTask<String> task = new OffsetFilterTask<String>(vo);
		taskCompletionService.submit(task);
		
		return buildFrameHandlerResponse(taskCompletionService, steps+1);
	}

	private Float getMultiplier(int i, Float multiplierIncrement) {
		return MULTIPLIER_START - (i * multiplierIncrement);
	}

	private Float getOffset(int i, Float offsetIncrement) {
		return OFFSET_START + (i * offsetIncrement);
	}

	@Override
	public Double getAllFrameSize(SettingsVO settings) throws Exception {
		BufferedImage baseImage = getBaseImage(settings);
		NegativeSettingsVO negativeSettings = settings.getNegativeSettings();
		
		Integer steps = negativeSettings.getSteps();
		Float multiplierIncrement = (MULTIPLIER_START - MULTIPLIER_END) / steps;
		Float offsetIncrement = (OFFSET_END - OFFSET_START) / steps;
		
		OffsetFilterTaskVO offsetFilterTaskVO = new OffsetFilterTaskVO(baseImage, settings.getProgramSettings().getOutputDir(), 
				getMultiplier(1, multiplierIncrement), getOffset(1, offsetIncrement), 
				settings.getProgramSettings().getGeneratedImageNamePrefix(), 1, NUMBER_OF_PAD_CHARS, steps); 
		OffsetFilterTask<TaskResultVO> task = new OffsetFilterTask<TaskResultVO>(offsetFilterTaskVO);
		
		TaskResultVO overlayResultVO = task.overlay();
    	
		return overlayResultVO.getFrameSize() * getNumberOfFrames(settings);
	}

}

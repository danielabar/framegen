package com.framegen.core.framehandler.option;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.*;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.TaskResultVO;
import com.framegen.api.service.IFrameHandler;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.FadeSettingsVO;
import com.framegen.core.framehandler.FrameHandlerBase;
import com.framegen.core.task.OffsetFilterTask;
import com.framegen.core.taskvo.OffsetFilterTaskVO;

public class FadeFrameHandler extends FrameHandlerBase implements IFrameHandler {
	
	private static final Float MULTIPLIER = Float.valueOf("1.0");
	private static final Float OFFSET_START = Float.valueOf("0");
	private static final Float OFFSET_END = Float.valueOf("230");
	
	private final ExecutorService taskExecutor;
	private final CompletionService<TaskResultVO> taskCompletionService;

	public FadeFrameHandler() {
		super();
		this.taskExecutor = Executors.newFixedThreadPool(getNumberOfThreads());
		this.taskCompletionService = new ExecutorCompletionService<TaskResultVO>(taskExecutor);
	}

	@Override
	public Integer getNumberOfFrames(SettingsVO settings) throws IOException {
		FadeSettingsVO fadeSettings = settings.getFadeSettings();
		return fadeSettings.getSteps() + 1;
	}

	@Override
	public FrameHandlerVO generateFrames(SettingsVO settings) throws IOException, InterruptedException {
		BufferedImage baseImage = getBaseImage(settings);
		FadeSettingsVO fadeSettings = settings.getFadeSettings();
		
		Integer steps = fadeSettings.getSteps();
		Float offsetIncrement = (OFFSET_END - OFFSET_START) / steps;
		
		for (int i=0; i<steps; i++) {
			OffsetFilterTaskVO vo = new OffsetFilterTaskVO(baseImage, settings.getProgramSettings().getOutputDir(), 
					MULTIPLIER, getOffset(i, offsetIncrement), 
					settings.getProgramSettings().getGeneratedImageNamePrefix(), i, NUMBER_OF_PAD_CHARS, steps); 
			Callable<TaskResultVO> task = new OffsetFilterTask<TaskResultVO>(vo);
			taskCompletionService.submit(task);
		}
		OffsetFilterTaskVO offsetFilterTaskVO = new OffsetFilterTaskVO(baseImage, settings.getProgramSettings().getOutputDir(), 
				MULTIPLIER, OFFSET_END, 
				settings.getProgramSettings().getGeneratedImageNamePrefix(), steps, NUMBER_OF_PAD_CHARS, steps); 
		Callable<TaskResultVO> task = new OffsetFilterTask<TaskResultVO>(offsetFilterTaskVO);
		taskCompletionService.submit(task);
		
		return buildFrameHandlerResponse(taskCompletionService, steps+1);
	}

	protected Float getOffset(int i, Float offsetIncrement) {
		return OFFSET_START + (i * offsetIncrement);
	}

	@Override
	public Double getAllFrameSize(SettingsVO settings) throws Exception {
		BufferedImage baseImage = getBaseImage(settings);
		FadeSettingsVO negativeSettings = settings.getFadeSettings();
		
		Integer steps = negativeSettings.getSteps();
		Float offsetIncrement = (OFFSET_END - OFFSET_START) / steps;
		OffsetFilterTaskVO offsetFilterTaskVO = new OffsetFilterTaskVO(baseImage, settings.getProgramSettings().getOutputDir(), 
			MULTIPLIER, getOffset(1, offsetIncrement), 
			settings.getProgramSettings().getGeneratedImageNamePrefix(), 1, NUMBER_OF_PAD_CHARS, steps); 
		OffsetFilterTask<TaskResultVO> task = new OffsetFilterTask<TaskResultVO>(offsetFilterTaskVO);
		
		TaskResultVO overlayResultVO = task.overlay();
    	
		return overlayResultVO.getFrameSize() * getNumberOfFrames(settings);
	}

}

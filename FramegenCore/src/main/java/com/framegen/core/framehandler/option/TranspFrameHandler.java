package com.framegen.core.framehandler.option;

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
import com.framegen.core.framehandler.FrameHandlerBase;
import com.framegen.core.task.TranspOverlayTask;
import com.framegen.core.taskvo.TranspTaskVO;
import com.framegen.core.util.alpha.AlphaCalculatorImpl;
import com.framegen.core.util.alpha.IAlphaCalculator;

public class TranspFrameHandler extends FrameHandlerBase implements IFrameHandler {
	
	private final IAlphaCalculator alphaCalculator;
	private final ExecutorService taskExecutor;
	private final CompletionService<TaskResultVO> taskCompletionService;

	public TranspFrameHandler() {
		super();
		this.alphaCalculator = new AlphaCalculatorImpl();
		this.taskExecutor = Executors.newFixedThreadPool(getNumberOfThreads());
		this.taskCompletionService = new ExecutorCompletionService<TaskResultVO>(taskExecutor);
	}
	
	@Override
	public Integer getNumberOfFrames(SettingsVO settings) throws IOException {
		return settings.getTranspSettings().getSteps();
	}

	@Override
	public FrameHandlerVO generateFrames(SettingsVO settings) throws IOException, InterruptedException {
		BufferedImage baseImage = getBaseImage(settings);
		BufferedImage overlay = getOverlayImage(settings);

		int combinedWidth = Math.max(baseImage.getWidth(), overlay.getWidth());
		int combinedHeight = Math.max(baseImage.getHeight(), overlay.getHeight());

		Integer steps = settings.getTranspSettings().getSteps();
		List<Float> alphas = alphaCalculator.getAlphas(settings.getTranspSettings());
		
		for (int i=0; i<steps; i++) {
			TranspTaskVO vo = new TranspTaskVO(baseImage, overlay, settings.getProgramSettings().getOutputDir(), 
					settings.getTranspSettings().getxPos(), settings.getTranspSettings().getyPos(), combinedWidth, combinedHeight, 
					settings.getProgramSettings().getGeneratedImageNamePrefix(), i, NUMBER_OF_PAD_CHARS, steps, 
					alphas.get(i));

			TranspOverlayTask<TaskResultVO> task = new TranspOverlayTask<TaskResultVO>(vo);
			taskCompletionService.submit(task);
		}
		return buildFrameHandlerResponse(taskCompletionService, steps);
	}

	@Override
	public Double getAllFrameSize(SettingsVO settings) throws Exception {
		BufferedImage baseImage = getBaseImage(settings);
		BufferedImage overlay = getOverlayImage(settings);

		int combinedWidth = Math.max(baseImage.getWidth(), overlay.getWidth());
		int combinedHeight = Math.max(baseImage.getHeight(), overlay.getHeight());

		Integer steps = settings.getTranspSettings().getSteps();
		List<Float> alphas = alphaCalculator.getAlphas(settings.getTranspSettings());
		
		TranspTaskVO transpTaskVO = new TranspTaskVO(baseImage, overlay, settings.getProgramSettings().getOutputDir(), 
				settings.getTranspSettings().getxPos(), settings.getTranspSettings().getyPos(), combinedWidth, combinedHeight, 
				settings.getProgramSettings().getGeneratedImageNamePrefix(), 1, NUMBER_OF_PAD_CHARS, steps, 
				alphas.get(1));

		TranspOverlayTask<TaskResultVO> task = new TranspOverlayTask<TaskResultVO>(transpTaskVO);
		TaskResultVO overlayResultVO = task.overlay();
		
		return overlayResultVO.getFrameSize() * getNumberOfFrames(settings);
	}

}

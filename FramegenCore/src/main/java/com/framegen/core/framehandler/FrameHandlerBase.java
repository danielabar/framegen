package com.framegen.core.framehandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.TaskResultVO;
import com.framegen.api.service.IFrameHandler;
import com.framegen.api.settings.SettingsVO;

public abstract class FrameHandlerBase implements IFrameHandler {
	
	protected static final Logger LOGGER = Logger.getLogger(FrameHandlerBase.class);
	
	protected static final String PROGRESS_MESSAGE = "Task %d of %d is done";
	protected static final int NUMBER_OF_PAD_CHARS = 4;
	
	public Integer getNumberOfThreads() {
		return Runtime.getRuntime().availableProcessors();
	}
	
	public BufferedImage getBaseImage(SettingsVO settings) throws IOException {
		return getImage(settings.getProgramSettings().getBaseImage());
	}
	
	public BufferedImage getOverlayImage(SettingsVO settings) throws IOException {
		return getImage(settings.getProgramSettings().getOverlayImage());
	}

	private BufferedImage getImage(File file) throws IOException {
		return ImageIO.read(file);
	}

	protected boolean isNotFirstInSequence(int imageCount) {
		return imageCount > 0;
	}

	@Override
	public String getStatus(FrameHandlerVO vo) {
		CompletionService<TaskResultVO> completionService = vo.getCompletionService();
		Future<TaskResultVO> future;
		try {
			future = completionService.take();
			TaskResultVO taskResultVO = future.get();
			return String.format(PROGRESS_MESSAGE, taskResultVO.getSequence()+1, vo.getNumTasks());
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error(e);
		}
		return StringUtils.EMPTY;
	}

	public FrameHandlerVO buildFrameHandlerResponse(CompletionService<TaskResultVO> taskCompletionService, int imageCount) {
		FrameHandlerVO responseVO = new FrameHandlerVO();
    	responseVO.setCompletionService(taskCompletionService);
    	responseVO.setNumTasks(imageCount);
		return responseVO;
	}
}

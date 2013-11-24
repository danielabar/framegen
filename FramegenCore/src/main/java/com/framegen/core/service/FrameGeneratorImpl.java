package com.framegen.core.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.FramegenResponseVO;
import com.framegen.api.response.NumberFramesResponseVO;
import com.framegen.api.service.IFrameGenerator;
import com.framegen.api.service.IFrameHandler;
import com.framegen.api.service.IFrameHandlerFactory;
import com.framegen.api.settings.SettingsVO;
import com.framegen.core.framehandler.FrameHandlerFactoryImpl;

public class FrameGeneratorImpl implements IFrameGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(FrameGeneratorImpl.class);
	
	private IFramegenValidator framegenValidator;
	private IDiskSpaceValidator diskSpaceValidator;
	private IFrameHandlerFactory frameHandlerFactory;

	public FrameGeneratorImpl() {
		super();
		this.framegenValidator = new FramegenValidatorImpl();
		this.diskSpaceValidator = new DiskSpaceValidatorImpl();
		this.frameHandlerFactory = new FrameHandlerFactoryImpl();
	}
	
	protected FrameGeneratorImpl(IFramegenValidator framegenValidator, IDiskSpaceValidator diskSpaceValidator, IFrameHandlerFactory frameHandlerFactory) {
		super();
		this.framegenValidator = framegenValidator;
		this.diskSpaceValidator = diskSpaceValidator;
		this.frameHandlerFactory = frameHandlerFactory;
	}

	@Override
	public NumberFramesResponseVO getNumberOfFrames(SettingsVO settingsVO) {
		NumberFramesResponseVO responseVO = new NumberFramesResponseVO();
		
		List<String> errorMessages = getFramegenValidator().validate(settingsVO);
		if (CollectionUtils.isNotEmpty(errorMessages)) {
			responseVO.setErrorMessages(errorMessages);
			return responseVO;
		}
		
		IFrameHandler frameHandler = getFrameHandlerFactory().getFrameHandler(settingsVO);
		try {
			Integer numberOfFrames = frameHandler.getNumberOfFrames(settingsVO);
			responseVO.setNumberOfFrames(numberOfFrames);
		} catch (Exception e) {
			responseVO.setException(e);
			LOGGER.error(e);
		}
		
		return responseVO;
	}

	protected Double getAllFrameSize(SettingsVO settings) throws Exception {
		IFrameHandler frameHandler = getFrameHandlerFactory().getFrameHandler(settings);
		return frameHandler.getAllFrameSize(settings);
	}

	@Override
	public FramegenResponseVO generateFrames(SettingsVO settingsVO) {
		FramegenResponseVO responseVO = new FramegenResponseVO();
		
		List<String> errorMessages = getFramegenValidator().validate(settingsVO);
		if (CollectionUtils.isNotEmpty(errorMessages)) {
			responseVO.setErrorMessages(errorMessages);
			return responseVO;
		}
		
		Double allFrameSize;
		try {
			allFrameSize = getAllFrameSize(settingsVO);
		} catch (Exception e) {
			responseVO.setException(e);
			LOGGER.error(e);
			return responseVO;
		}
		
		List<String> validateDiskSpace = getDiskSpaceValidator().validateDiskSpace(allFrameSize, settingsVO);
		if (CollectionUtils.isNotEmpty(validateDiskSpace)) {
			responseVO.setErrorMessages(validateDiskSpace);
			return responseVO;
		}
		
		try {
			IFrameHandler frameHandler = getFrameHandlerFactory().getFrameHandler(settingsVO);
			FrameHandlerVO frameHandlerVO = frameHandler.generateFrames(settingsVO);
			responseVO.setFrameHandlerVO(frameHandlerVO);
			responseVO.setSettingsVO(settingsVO);
		} catch (IOException | InterruptedException e) {
			responseVO.setException(e);
			LOGGER.error(e);
		}
		
		return responseVO;
	}
	
	@Override
	public String getStatus(SettingsVO settings, FrameHandlerVO frameHandlerVO) {
		IFrameHandler frameHandler = getFrameHandlerFactory().getFrameHandler(settings);
		return frameHandler.getStatus(frameHandlerVO);
	}

	public void setFramegenValidator(IFramegenValidator framegenValidator) {
		this.framegenValidator = framegenValidator;
	}

	public IFramegenValidator getFramegenValidator() {
		return framegenValidator;
	}

	public IDiskSpaceValidator getDiskSpaceValidator() {
		return diskSpaceValidator;
	}
	
	public void setDiskSpaceValidator(IDiskSpaceValidator diskSpaceValidator) {
		this.diskSpaceValidator = diskSpaceValidator;
	}

	public IFrameHandlerFactory getFrameHandlerFactory() {
		return frameHandlerFactory;
	}

	public void setFrameHandlerFactory(IFrameHandlerFactory frameHandlerFactory) {
		this.frameHandlerFactory = frameHandlerFactory;
	}

}

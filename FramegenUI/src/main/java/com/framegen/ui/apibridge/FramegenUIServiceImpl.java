package com.framegen.ui.apibridge;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.FramegenResponseVO;
import com.framegen.api.response.NumberFramesResponseVO;
import com.framegen.api.service.IFrameGenerator;
import com.framegen.api.settings.SettingsVO;
import com.framegen.core.service.FrameGeneratorImpl;
import com.framegen.ui.mvp.IFramegenModel;

public class FramegenUIServiceImpl implements IFramegenUIService {
	
	private final IFrameGenerator frameGenerator;
	private final SettingsTransformer settingsTransformer;

	public FramegenUIServiceImpl() {
		super();
		this.frameGenerator = new FrameGeneratorImpl();
		this.settingsTransformer = new SettingsTransformer();
	}

	protected FramegenUIServiceImpl(SettingsTransformer settingsTransformer, IFrameGenerator frameGenerator) {
		super();
		this.frameGenerator = frameGenerator;
		this.settingsTransformer = settingsTransformer;
	}

	@Override
	public NumberFramesResponseVO getNumberOfFrames(IFramegenModel framegenModel) {
		SettingsVO settingsVO = settingsTransformer.apply(framegenModel);
		return frameGenerator.getNumberOfFrames(settingsVO);
	}

	@Override
	public FramegenResponseVO generateFrames(IFramegenModel framegenModel) {
		SettingsVO settingsVO = settingsTransformer.apply(framegenModel);
		return frameGenerator.generateFrames(settingsVO);
	}

	@Override
	public String getStatus(SettingsVO settings, FrameHandlerVO frameHandlerVO) {
		return frameGenerator.getStatus(settings, frameHandlerVO);
	}

}

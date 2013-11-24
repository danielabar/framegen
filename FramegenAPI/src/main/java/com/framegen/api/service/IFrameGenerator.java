package com.framegen.api.service;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.FramegenResponseVO;
import com.framegen.api.response.NumberFramesResponseVO;
import com.framegen.api.settings.SettingsVO;

public interface IFrameGenerator {
	
	public NumberFramesResponseVO getNumberOfFrames(SettingsVO settingsVO);
	
	public FramegenResponseVO generateFrames(SettingsVO settingsVO);
	
	public String getStatus(SettingsVO settings, FrameHandlerVO frameHandlerVO);
}

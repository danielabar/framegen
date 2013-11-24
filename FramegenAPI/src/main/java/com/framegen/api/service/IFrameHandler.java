package com.framegen.api.service;

import java.io.IOException;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.settings.SettingsVO;

public interface IFrameHandler {
	
	public Integer getNumberOfFrames(SettingsVO settings) throws IOException;
	
	public Double getAllFrameSize(SettingsVO settings) throws IOException, Exception;

	public FrameHandlerVO generateFrames(SettingsVO settings) throws IOException, InterruptedException;
	
	public String getStatus(FrameHandlerVO vo);

}

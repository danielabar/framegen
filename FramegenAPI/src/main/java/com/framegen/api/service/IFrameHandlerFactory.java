package com.framegen.api.service;

import com.framegen.api.settings.SettingsVO;

public interface IFrameHandlerFactory {
	IFrameHandler getFrameHandler(SettingsVO settings);
}
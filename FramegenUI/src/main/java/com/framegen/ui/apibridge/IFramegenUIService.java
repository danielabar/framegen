package com.framegen.ui.apibridge;

import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.response.FramegenResponseVO;
import com.framegen.api.response.NumberFramesResponseVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.ui.mvp.IFramegenModel;

public interface IFramegenUIService {

	public NumberFramesResponseVO getNumberOfFrames(IFramegenModel framegenModel);
	
	public FramegenResponseVO generateFrames(IFramegenModel framegenModel);
	
	public String getStatus(SettingsVO settings, FrameHandlerVO frameHandlerVO);

}
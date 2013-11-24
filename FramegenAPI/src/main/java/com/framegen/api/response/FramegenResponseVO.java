package com.framegen.api.response;

import com.framegen.api.settings.SettingsVO;

public class FramegenResponseVO extends ResponseVO {

	private static final long serialVersionUID = -5856729069872246562L;

	private FrameHandlerVO frameHandlerVO;
	private SettingsVO settingsVO;

	public FrameHandlerVO getFrameHandlerVO() {
		return frameHandlerVO;
	}

	public void setFrameHandlerVO(FrameHandlerVO frameHandlerVO) {
		this.frameHandlerVO = frameHandlerVO;
	}

	public SettingsVO getSettingsVO() {
		return settingsVO;
	}

	public void setSettingsVO(SettingsVO settingsVO) {
		this.settingsVO = settingsVO;
	}

}

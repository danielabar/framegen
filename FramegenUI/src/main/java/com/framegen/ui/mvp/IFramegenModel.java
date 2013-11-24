package com.framegen.ui.mvp;

import java.io.File;

import com.framegen.api.settings.option.IOptionSettingsVO;

public interface IFramegenModel {
	
	File getBaseImage();
	void setBaseImage(File baseImage);
	
	File getOverlayImage();
	void setOverlayImage(File overlayImage);
	
	File getOutputDir();
	void setOutputDir(File outputDir);
	
	String getGeneratedImageNamePrefix();
	void setGeneratedImageNamePrefix(String generatedImageNamePrefix);
	
	IOptionSettingsVO getOptionSettingsVO();
	void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO);
}

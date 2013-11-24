package com.framegen.ui.mvp;

import java.io.File;

import com.framegen.api.settings.option.IOptionSettingsVO;

public class FramegenModelImpl implements IFramegenModel {
	
	private File baseImage;
	private File overlayImage;
	private File outputDir;
	private String generatedImageNamePrefix;
	private IOptionSettingsVO optionSettingsVO;
	
	@Override
	public File getBaseImage() {
		return baseImage;
	}
	@Override
	public void setBaseImage(File baseImage) {
		this.baseImage = baseImage;
	}
	@Override
	public File getOverlayImage() {
		return overlayImage;
	}
	@Override
	public void setOverlayImage(File overlayImage) {
		this.overlayImage = overlayImage;
	}
	@Override
	public File getOutputDir() {
		return outputDir;
	}
	@Override
	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}
	@Override
	public String getGeneratedImageNamePrefix() {
		return generatedImageNamePrefix;
	}
	@Override
	public void setGeneratedImageNamePrefix(String generatedImageNamePrefix) {
		this.generatedImageNamePrefix = generatedImageNamePrefix;
	}
	@Override
	public IOptionSettingsVO getOptionSettingsVO() {
		return this.optionSettingsVO;
	}
	@Override
	public void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO) {
		this.optionSettingsVO = optionSettingsVO;
	}

}

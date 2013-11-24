package com.framegen.ui.tdf;

import java.util.Properties;

import com.framegen.ui.mvp.IFramegenModel;

public interface ITestDataFactory {

	public IFramegenModel createFramgenModelWithLineSettings();
	
	public IFramegenModel createFramgenModelWithArcSettings();

	public IFramegenModel createFramegenModelWithNegativeSettings();

	public IFramegenModel createFramegenModel();

	public Properties createProperties(String base, String overlay, String output);

}
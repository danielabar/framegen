package com.framegen.ui.tdf;

import static com.framegen.ui.main.FramegenPropertiesLoader.PROPERTY_DEFAULT_BASE_DIR;
import static com.framegen.ui.main.FramegenPropertiesLoader.PROPERTY_DEFAULT_OUTPUT_DIR;
import static com.framegen.ui.main.FramegenPropertiesLoader.PROPERTY_DEFAULT_OVERLAY_DIR;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.api.settings.option.IOptionSettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.api.settings.option.NegativeSettingsVO;
import com.framegen.ui.mvp.FramegenModelImpl;
import com.framegen.ui.mvp.IFramegenModel;

public class TestDataFactory implements ITestDataFactory {
	
	@Override
	public IFramegenModel createFramgenModelWithLineSettings() {
		IFramegenModel model = createFramegenModel();
		IOptionSettingsVO optionSettingsVO = createLineSettings();
		model.setOptionSettingsVO(optionSettingsVO);
		return model;
	}
	
	@Override
	public IFramegenModel createFramgenModelWithArcSettings() {
		IFramegenModel model = createFramegenModel();
		IOptionSettingsVO optionSettingsVO = createArcSettings();
		model.setOptionSettingsVO(optionSettingsVO);
		return model;
	}

	@Override
	public IFramegenModel createFramegenModelWithNegativeSettings() {
		IFramegenModel model = createFramegenModel();
		IOptionSettingsVO optionSettingsVO = createNegativeSettings();
		model.setOptionSettingsVO(optionSettingsVO);
		return model;
	}
	
	protected IOptionSettingsVO createNegativeSettings() {
		NegativeSettingsVO negVO = new NegativeSettingsVO();
		negVO.setSteps(30);
		return negVO;
	}

	protected IOptionSettingsVO createLineSettings() {
		LineSettingsVO lineVO = new LineSettingsVO();
		lineVO.setxStart(0);
		lineVO.setyStart(10);
		lineVO.setxEnd(500);
		lineVO.setyEnd(600);
		lineVO.setMoveIncrement(Double.valueOf("20"));
		return lineVO;
	}
	
	protected IOptionSettingsVO createArcSettings() {
		ArcSettingsVO arcVO = new ArcSettingsVO();
		arcVO.setxStart(0);
		arcVO.setyStart(10);
		arcVO.setxEnd(500);
		arcVO.setyEnd(600);
		arcVO.setRadius(Double.valueOf("25"));
		arcVO.setMoveIncrement(Double.valueOf("20"));
		arcVO.setShortestRoute(true);
		arcVO.setSide(false);
		arcVO.setReverseSequence(false);
		return arcVO;
	}
	
	@Override
	public IFramegenModel createFramegenModel() {
		IFramegenModel model = new FramegenModelImpl();
		model.setBaseImage(new File("base.png"));
		model.setOverlayImage(new File("overlay.png"));
		model.setOutputDir(new File("out"));
		model.setGeneratedImageNamePrefix("generated_");
		return model;
	}
	
	@Override
	public Properties createProperties(String base, String overlay, String output) {
		Properties prop = new Properties();
		prop.setProperty(PROPERTY_DEFAULT_BASE_DIR, base != null ? base : StringUtils.EMPTY);
		prop.setProperty(PROPERTY_DEFAULT_OVERLAY_DIR, overlay != null ? overlay : StringUtils.EMPTY);
		prop.setProperty(PROPERTY_DEFAULT_OUTPUT_DIR, output != null ? output : StringUtils.EMPTY);
		return prop;
	}
}

package com.framegen.ui.apibridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.api.settings.option.NegativeSettingsVO;
import com.framegen.ui.FramegenUITestCase;
import com.framegen.ui.mvp.IFramegenModel;

public class SettingsTransformerTest extends FramegenUITestCase {
	
	private SettingsTransformer fixture;
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new SettingsTransformer();
	}

	@Test
	public void testApply_lineSettings() {
		IFramegenModel model = getTdf().createFramgenModelWithLineSettings();
		SettingsVO settingsVO = fixture.apply(model);
		
		assertNotNull(settingsVO);
		verifyProgramSettings(model, settingsVO);
		
		assertEquals("frame option", FrameOption.LINE, settingsVO.getFrameOption());
		
		LineSettingsVO expectedSettings = (LineSettingsVO) model.getOptionSettingsVO();
		LineSettingsVO actualSettings = settingsVO.getLineSettings();
		
		assertEquals("x start", expectedSettings.getxStart(), actualSettings.getxStart());
		assertEquals("y start", expectedSettings.getyStart(), actualSettings.getyStart());
		assertEquals("x end", expectedSettings.getxEnd(), actualSettings.getxEnd());
		assertEquals("y end", expectedSettings.getyEnd(), actualSettings.getyEnd());
		assertEquals("increment", expectedSettings.getMoveIncrement(), actualSettings.getMoveIncrement());
	}

	@Test
	public void testApply_arcSettings() {
		IFramegenModel model = getTdf().createFramgenModelWithArcSettings();
		SettingsVO settingsVO = fixture.apply(model);
		
		assertNotNull(settingsVO);
		verifyProgramSettings(model, settingsVO);
		
		assertEquals("frame option", FrameOption.ARC, settingsVO.getFrameOption());
		
		ArcSettingsVO expectedSettings = (ArcSettingsVO) model.getOptionSettingsVO();
		ArcSettingsVO actualSettings = settingsVO.getArcSettings();
		
		assertEquals("x start", expectedSettings.getxStart(), actualSettings.getxStart());
		assertEquals("y start", expectedSettings.getyStart(), actualSettings.getyStart());
		assertEquals("x end", expectedSettings.getxEnd(), actualSettings.getxEnd());
		assertEquals("y end", expectedSettings.getyEnd(), actualSettings.getyEnd());
		assertEquals("increment", expectedSettings.getMoveIncrement(), actualSettings.getMoveIncrement());
		assertEquals("radius", expectedSettings.getRadius(), actualSettings.getRadius());
		assertEquals("shortest route", expectedSettings.isShortestRoute(), actualSettings.isShortestRoute());
		assertEquals("side", expectedSettings.isSide(), actualSettings.isSide());
		assertEquals("reverse sequence", expectedSettings.isReverseSequence(), actualSettings.isReverseSequence());
	}
	
	@Test
	public void testApply_negativeSettings() {
		IFramegenModel model = getTdf().createFramegenModelWithNegativeSettings();
		SettingsVO settingsVO = fixture.apply(model);
		
		assertNotNull(settingsVO);
		verifyProgramSettings(model, settingsVO);
		
		assertEquals("frame option", FrameOption.NEGATIVE, settingsVO.getFrameOption());
		assertEquals("steps", ((NegativeSettingsVO) model.getOptionSettingsVO()).getSteps(), settingsVO.getNegativeSettings().getSteps());
	}
	
	private void verifyProgramSettings(IFramegenModel model, SettingsVO settingsVO) {
		assertEquals("base image", model.getBaseImage(), settingsVO.getProgramSettings().getBaseImage());
		assertEquals("overlay image", model.getOverlayImage(), settingsVO.getProgramSettings().getOverlayImage());
		assertEquals("output dir", model.getOutputDir(), settingsVO.getProgramSettings().getOutputDir());
		assertEquals("generated image prefix", model.getGeneratedImageNamePrefix(), settingsVO.getProgramSettings().getGeneratedImageNamePrefix());
	}

}

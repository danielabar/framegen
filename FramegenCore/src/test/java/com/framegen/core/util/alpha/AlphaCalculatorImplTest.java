package com.framegen.core.util.alpha;

import static com.framegen.core.util.alpha.AlphaCalculatorImpl.OPAQUE_ALPHA;
import static com.framegen.core.util.alpha.AlphaCalculatorImpl.TRANSPARENT_ALPHA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.framegen.api.settings.option.TranspSettingsVO;
import com.framegen.api.settings.option.TranspSettingsVO.TranspDirection;
import com.framegen.core.FramegenCoreTestCase;

public class AlphaCalculatorImplTest extends FramegenCoreTestCase {

	private AlphaCalculatorImpl fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new AlphaCalculatorImpl();
	}

	@Test
	public void testGetAlphas_fadeOut() {
		TranspSettingsVO transpSettings = new TranspSettingsVO();
		transpSettings.setDirection(TranspDirection.FADE_OUT);
		transpSettings.setSteps(10);
		
		List<Float> alphas = fixture.getAlphas(transpSettings);
		
		assertEquals("alphas were generated for each requested step", transpSettings.getSteps().intValue(), alphas.size());
		assertEquals("when fading out, first alpha should be fully opaque", OPAQUE_ALPHA, alphas.get(0).floatValue(), DELTA);
		
		for (int i=0; i<alphas.size()-1; i++) {
			assertTrue("when fading out, alphas generated from larger to smaller values", alphas.get(i).compareTo(alphas.get(i+1)) > 0);
		}
		
	}
	
	@Test
	public void testGetAlphas_fadeIn() {
		TranspSettingsVO transpSettings = new TranspSettingsVO();
		transpSettings.setDirection(TranspDirection.FADE_IN);
		transpSettings.setSteps(10);
		
		List<Float> alphas = fixture.getAlphas(transpSettings);
		
		assertEquals("alphas were generated for each requested step", transpSettings.getSteps().intValue(), alphas.size());
		assertEquals("when fading in, first alpha should be fully transparent", TRANSPARENT_ALPHA, alphas.get(0).floatValue(), DELTA);
		
		for (int i=0; i<alphas.size()-1; i++) {
			assertTrue("when fading in, alphas generated from smaller to larger values", alphas.get(i).compareTo(alphas.get(i+1)) < 0);
		}
		
	}

}

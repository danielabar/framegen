package com.framegen.core.util.alpha;

import java.util.ArrayList;
import java.util.List;

import com.framegen.api.settings.option.TranspSettingsVO;
import com.framegen.api.settings.option.TranspSettingsVO.TranspDirection;

public class AlphaCalculatorImpl implements IAlphaCalculator {
	
	static final float OPAQUE_ALPHA = 1f;
	static final float TRANSPARENT_ALPHA = 0f;
	
	@Override
	public List<Float> getAlphas(TranspSettingsVO transpSettings) {
		Float alphaIncrement = getAlphaIncrement(transpSettings);
		List<Float> results = new ArrayList<Float>();
		for (int i=0; i<transpSettings.getSteps(); i++) {
			results.add(getAlpha(transpSettings, i, alphaIncrement));
		}
		return results;
	}

	protected float getAlphaIncrement(TranspSettingsVO transpSettings) {
		return (OPAQUE_ALPHA -TRANSPARENT_ALPHA) / transpSettings.getSteps();
	}

	protected Float getAlpha(TranspSettingsVO transpSettings, int i, Float alphaIncrement) {
		if (TranspDirection.FADE_OUT.equals(transpSettings.getDirection())) {
			return OPAQUE_ALPHA - (i * alphaIncrement);
		}
		return TRANSPARENT_ALPHA + (i* alphaIncrement);
	}

}

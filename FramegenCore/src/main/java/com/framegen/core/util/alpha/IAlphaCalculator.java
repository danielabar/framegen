package com.framegen.core.util.alpha;

import java.util.List;

import com.framegen.api.settings.option.TranspSettingsVO;

public interface IAlphaCalculator {

	public List<Float> getAlphas(TranspSettingsVO transpSettings);

}
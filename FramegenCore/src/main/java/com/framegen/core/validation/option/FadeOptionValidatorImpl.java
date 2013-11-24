package com.framegen.core.validation.option;

import java.util.ArrayList;
import java.util.List;

import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.FadeSettingsVO;
import com.framegen.core.validation.IOptionValidator;
import com.framegen.core.validation.OptionValidatorBase;

public class FadeOptionValidatorImpl extends OptionValidatorBase implements IOptionValidator {

	@Override
	public List<String> validate(SettingsVO settingsVO) {
		List<String> errors = new ArrayList<String>();
		
		FadeSettingsVO fadeSettings = settingsVO.getFadeSettings();
		if (fadeSettings == null) {
			errors.add("Fade settings must be specified");
			return errors;
		}
		
		validateNumberNotNull(fadeSettings.getSteps(), "Number of fade steps must be specified", errors);
		validateIntegerGreaterThanZero(fadeSettings.getSteps(), "Number of fade steps must be greater than zero", errors);
		
		return errors;
	}

}

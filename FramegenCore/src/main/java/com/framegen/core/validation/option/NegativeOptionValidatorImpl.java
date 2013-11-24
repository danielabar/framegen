package com.framegen.core.validation.option;

import java.util.ArrayList;
import java.util.List;

import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.NegativeSettingsVO;
import com.framegen.core.validation.IOptionValidator;
import com.framegen.core.validation.OptionValidatorBase;

public class NegativeOptionValidatorImpl extends OptionValidatorBase implements
		IOptionValidator {

	@Override
	public List<String> validate(SettingsVO settingsVO) {
		List<String> errors = new ArrayList<String>();
		
		NegativeSettingsVO negativeSettings = settingsVO.getNegativeSettings();
		if (negativeSettings == null) {
			errors.add("Negative settings must be specified");
			return errors;
		}
		
		validateNumberNotNull(negativeSettings.getSteps(), "Number of steps must be specified", errors);
		validateIntegerGreaterThanZero(negativeSettings.getSteps(), "Number of steps must be greater than zero", errors);
		
		return errors;
	}

}

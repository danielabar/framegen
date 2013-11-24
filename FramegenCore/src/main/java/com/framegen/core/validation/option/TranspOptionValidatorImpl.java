package com.framegen.core.validation.option;

import java.util.ArrayList;
import java.util.List;

import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.TranspSettingsVO;
import com.framegen.core.validation.IOptionValidator;
import com.framegen.core.validation.OptionValidatorBase;

public class TranspOptionValidatorImpl extends OptionValidatorBase implements IOptionValidator {

	@Override
	public List<String> validate(SettingsVO settingsVO) {
		List<String> errors = new ArrayList<String>();
		
		TranspSettingsVO transpSettings = settingsVO.getTranspSettings();
		if (transpSettings == null) {
			errors.add("Transparent settings must be specified");
			return errors;
		}
		
		if (transpSettings.getDirection() == null) {
			errors.add("Transparency direction must be specified");
		}
		
		validateNumberNotNull(transpSettings.getxPos(), "x position must be specified", errors);
		validateNumberNotNull(transpSettings.getyPos(), "y position must be specified", errors);
		validateNumberNotNull(transpSettings.getSteps(), "Number of steps must be specified", errors);
		
		validateIntegerGreaterThanZero(transpSettings.getSteps(), "Number of steps must be greater than zero", errors);
		
		return errors;
	}

}

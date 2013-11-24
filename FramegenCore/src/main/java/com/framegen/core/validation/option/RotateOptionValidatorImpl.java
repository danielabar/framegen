package com.framegen.core.validation.option;

import java.util.ArrayList;
import java.util.List;

import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.RotationSettingsVO;
import com.framegen.core.validation.IOptionValidator;
import com.framegen.core.validation.OptionValidatorBase;

public class RotateOptionValidatorImpl extends OptionValidatorBase implements
		IOptionValidator {

	@Override
	public List<String> validate(SettingsVO settingsVO) {
		List<String> errors = new ArrayList<String>();
		
		RotationSettingsVO rotationSettings = settingsVO.getRotationSettings();
		if (rotationSettings == null) {
			errors.add("Rotation settings must be specified");
			return errors;
		}
		
		validateNumberNotNull(rotationSettings.getxPosition(), "xPosition must be specified", errors);
		validateNumberNotNull(rotationSettings.getyPosition(), "yPosition must be specified", errors);
		validateNumberNotNull(rotationSettings.getDegrees(), "degrees must be specified", errors);
		validateNumberNotNull(rotationSettings.getStep(), "step must be specified", errors);
		
		validateIntegerGreaterThanZero(rotationSettings.getDegrees(), "degrees must be greater than zero", errors);
		validateIntegerGreaterThanZero(rotationSettings.getStep(), "step must be greater than zero", errors);
		
		return errors;
	}

}

package com.framegen.core.validation.option;

import java.util.ArrayList;
import java.util.List;

import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.core.validation.IOptionValidator;
import com.framegen.core.validation.OptionValidatorBase;

public class ArcOptionValidatorImpl extends OptionValidatorBase implements IOptionValidator {

	@Override
	public List<String> validate(SettingsVO settingsVO) {
		List<String> errors = new ArrayList<String>();
		
		ArcSettingsVO arcSettings = settingsVO.getArcSettings();
		if (arcSettings == null) {
			errors.add("Arc settings must be specified");
			return errors;
		}
		
		validateNumberNotNull(arcSettings.getxStart(), "xStart must be specified", errors);
		validateNumberNotNull(arcSettings.getyStart(), "yStart must be specified", errors);
		validateNumberNotNull(arcSettings.getxEnd(), "xEnd must be specified", errors);
		validateNumberNotNull(arcSettings.getyEnd(), "yEnd must be specified", errors);
		validateNumberNotNull(arcSettings.getMoveIncrement(), "Increment must be specified", errors);
		validateNumberNotNull(arcSettings.getRadius(), "Radius must be specified", errors);
		
		validateDoubleGreaterThanZero(arcSettings.getMoveIncrement(), "Move increment must be greater than zero", errors);
		validateDoubleGreaterThanZero(arcSettings.getRadius(), "Radius must be greater than zero", errors);
		
		return errors;
	}

}

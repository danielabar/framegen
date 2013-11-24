package com.framegen.core.validation.option;

import java.util.ArrayList;
import java.util.List;

import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.core.validation.IOptionValidator;
import com.framegen.core.validation.OptionValidatorBase;

public class LineOptionValidatorImpl extends OptionValidatorBase implements IOptionValidator {

	@Override
	public List<String> validate(SettingsVO settingsVO) {
		List<String> errors = new ArrayList<String>();
		
		LineSettingsVO lineSettings = settingsVO.getLineSettings();
		if (lineSettings == null) {
			errors.add("Line settings must be specified");
			return errors;
		}
		
		validateNumberNotNull(lineSettings.getxStart(), "xStart must be specified", errors);
		validateNumberNotNull(lineSettings.getyStart(), "yStart must be specified", errors);
		validateNumberNotNull(lineSettings.getxEnd(), "xEnd must be specified", errors);
		validateNumberNotNull(lineSettings.getyEnd(), "yEnd must be specified", errors);
		validateNumberNotNull(lineSettings.getMoveIncrement(), "Increment must be specified", errors);
		
		validateDoubleGreaterThanZero(lineSettings.getMoveIncrement(), "Move increment must be greater than zero", errors);
		
		return errors;
	}

}

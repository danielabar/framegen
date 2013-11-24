package com.framegen.core.validation;

import com.framegen.api.settings.SettingsVO;

public interface IOptionValidatorFactory {

	public IOptionValidator getOptionValidator(SettingsVO settings);
		
}

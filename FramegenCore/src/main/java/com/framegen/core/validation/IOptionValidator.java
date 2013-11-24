package com.framegen.core.validation;

import java.util.List;

import com.framegen.api.settings.SettingsVO;

public interface IOptionValidator {
	
	public List<String> validate(SettingsVO settingsVO);
	
}

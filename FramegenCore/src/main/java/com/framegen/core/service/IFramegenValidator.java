package com.framegen.core.service;

import java.util.List;

import com.framegen.api.settings.SettingsVO;

public interface IFramegenValidator {

	public List<String> validate(SettingsVO settingsVO);

}
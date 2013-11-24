package com.framegen.core.service;

import java.util.List;

import com.framegen.api.settings.SettingsVO;

public interface IDiskSpaceValidator {
	public List<String> validateDiskSpace(Double allFrameSizeBytes, SettingsVO settings);
}

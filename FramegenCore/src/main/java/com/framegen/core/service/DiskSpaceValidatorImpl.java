package com.framegen.core.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.framegen.api.settings.SettingsVO;

public class DiskSpaceValidatorImpl implements IDiskSpaceValidator {
	
	protected static final Double MAX_PERCENT_DISK_USAGE = Double.valueOf("30");
	protected static final String ERROR_MESSAGE_FORMAT = "Frame generation would use approximately %.0f%% of free disk space which exceeds max allowed of %.0f%%";
	
	@Override
	public List<String> validateDiskSpace(Double allFrameSizeBytes, SettingsVO settings) {
		
		List<String> errorMessages = new ArrayList<String>();
		
		if (allFrameSizeBytes == null) {
			errorMessages.add("Unable to determine frame size");
			return errorMessages;
		}
		
		File outputDir = settings.getProgramSettings().getOutputDir();
		Double usableSpace = Double.valueOf(outputDir.getUsableSpace());
		Double percent = (allFrameSizeBytes / usableSpace) * 100; 
		
		if (percent > MAX_PERCENT_DISK_USAGE) {
			errorMessages.add(String.format(ERROR_MESSAGE_FORMAT, percent, MAX_PERCENT_DISK_USAGE));
		}
		
		return errorMessages;
	}

}

package com.framegen.core.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.framegen.api.settings.SettingsVO;
import com.framegen.core.validation.IOptionValidator;
import com.framegen.core.validation.IOptionValidatorFactory;
import com.framegen.core.validation.OptionValidatorFactoryImpl;

public class FramegenValidatorImpl implements IFramegenValidator {
	
	private IOptionValidatorFactory optionValidatorFactory; 
	
	protected static final int MAX_PREFIX_LENGTH = 100;
	protected static final String PREFIX_LENGTH_EXCEEDED_MESSAGE_FORMAT = "Image prefix exceeds max length of %d by %d characters, please enter a shorter prefix";
	
	public FramegenValidatorImpl() {
		super();
		this.optionValidatorFactory = new OptionValidatorFactoryImpl();
	}

	@Override
	public List<String> validate(SettingsVO settingsVO) {
		List<String> errorMessages = new ArrayList<String>();
		
		if (settingsVO == null) {
			errorMessages.add("Settings must be specified");
			return errorMessages;
		}
		
		validateProgramSettings(settingsVO, errorMessages);
		validateOptionSettings(settingsVO, errorMessages);
		
		return errorMessages;
	}

	protected void validateOptionSettings(SettingsVO settingsVO, List<String> errorMessages) {
		IOptionValidator optionValidator = optionValidatorFactory.getOptionValidator(settingsVO);
		List<String> optionErrorMessages = optionValidator.validate(settingsVO);
		if (CollectionUtils.isNotEmpty(optionErrorMessages)) {
			errorMessages.addAll(optionErrorMessages);
		}
	}

	protected void validateProgramSettings(SettingsVO settingsVO, List<String> errorMessges) {
		validateBaseImage(settingsVO, errorMessges);
		validateOverlayImage(settingsVO, errorMessges);
		validateOutputDirectory(settingsVO, errorMessges);
		validatePrefix(settingsVO, errorMessges);
	}

	protected void validatePrefix(SettingsVO settingsVO, List<String> errorMessages) {
		String prefix = settingsVO.getProgramSettings().getGeneratedImageNamePrefix();
		if (StringUtils.isEmpty(prefix)) {
			errorMessages.add("Please specify a prefix for generated images");
		}
		if (StringUtils.isNotEmpty(prefix) &&  prefix.length() > MAX_PREFIX_LENGTH) {
			errorMessages.add(String.format(PREFIX_LENGTH_EXCEEDED_MESSAGE_FORMAT, MAX_PREFIX_LENGTH, prefix.length()-MAX_PREFIX_LENGTH));
		}
	}

	protected void validateOutputDirectory(SettingsVO settingsVO, List<String> errorMessages) {
		if (settingsVO.getProgramSettings().getOutputDir() == null) {
			errorMessages.add("Please specify an output directory for generated images");
		}
		if (settingsVO.getProgramSettings().getOutputDir() != null && !isDirectoryAndExists(settingsVO.getProgramSettings().getOutputDir())) {
			errorMessages.add("Output directory does not refer to a directory or does not exist");
		}
	}

	protected void validateBaseImage(SettingsVO settingsVO, List<String> errorMessages) {
		if (settingsVO.getProgramSettings().getBaseImage() == null) {
			errorMessages.add("Please specify a base image");
		}
		if (settingsVO.getProgramSettings().getBaseImage() != null && !isFileAndExists(settingsVO.getProgramSettings().getBaseImage())) {
			errorMessages.add("Base image does not refer to a file or does not exist");
		} 
		if (settingsVO.getProgramSettings().getBaseImage() != null && isFileAndExists(settingsVO.getProgramSettings().getBaseImage())) {
			validateFileIsImage(settingsVO.getProgramSettings().getBaseImage(), errorMessages, "File specified for base image is not an image");
		}
	}

	protected void validateFileIsImage(File file, List<String> errorMessages, String errorMessage) {
		try {
			String probeContentType = Files.probeContentType(file.toPath());
			if (!StringUtils.contains(probeContentType, "image")) {
				errorMessages.add(errorMessage);
			}
		} catch (IOException e) {
			errorMessages.add(errorMessage);
		}
	}

	protected void validateOverlayImage(SettingsVO settingsVO, List<String> errorMessages) {
		if (settingsVO.getFrameOption().isRequiresOverlay()) {
			if (settingsVO.getProgramSettings().getOverlayImage() == null) {
				errorMessages.add("Please specify an overlay image");
			}
			if (settingsVO.getProgramSettings().getOverlayImage() != null && !isFileAndExists(settingsVO.getProgramSettings().getOverlayImage())) {
				errorMessages.add("Overlay image does not refer to a file or does not exist");
			} 
			if (settingsVO.getProgramSettings().getOverlayImage() != null && isFileAndExists(settingsVO.getProgramSettings().getOverlayImage())) {
				validateFileIsImage(settingsVO.getProgramSettings().getOverlayImage(), errorMessages, "File specified for overlay image is not an image");
			}
		}
	}
	
	boolean isFileAndExists(File file) {
		return file.exists() && file.isFile();
	}
	
	boolean isDirectoryAndExists(File file) {
		return file.exists() && file.isDirectory();
	}

	public IOptionValidatorFactory getOptionValidatorFactory() {
		return optionValidatorFactory;
	}

	public void setOptionValidatorFactory(IOptionValidatorFactory optionValidatorFactory) {
		this.optionValidatorFactory = optionValidatorFactory;
	}
	
}

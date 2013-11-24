package com.framegen.cmdline.settings.validator;

import java.io.File;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class DirectoryExistsValidator implements IParameterValidator {
	
	private static final String ERROR_MESSAGE = "Value of %s provided for %s is not a directory";

	public void validate(String name, String value) throws ParameterException {
		File file = new File(value);
		if (!file.isDirectory()) {
			throw new ParameterException(String.format(ERROR_MESSAGE, value, name));
		}
	}

}

package com.framegen.cmdline.settings.validator;

import java.io.File;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class FileExistsValidator implements IParameterValidator {
	
	private static final String ERROR_MESSAGE = "Value of %s provided for %s could not be found";

	public void validate(String name, String value) throws ParameterException {
		File file = new File(value);
		if (!file.exists()) {
			throw new ParameterException(String.format(ERROR_MESSAGE, value, name));
		}
	}

}

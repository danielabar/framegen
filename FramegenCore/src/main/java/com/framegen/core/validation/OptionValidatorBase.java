package com.framegen.core.validation;

import java.util.List;

public class OptionValidatorBase {

	protected void validateNumberNotNull(Number value, String errorMessage, List<String> errors) {
		if (value == null) {
			errors.add(errorMessage);
		}
	}
	
	protected void validateDoubleGreaterThanZero(Double value, String errorMessage, List<String> errors) {
		if (value != null) {
			if (value.compareTo(Double.valueOf("0")) <= 0) {
				errors.add(errorMessage);
			}
		}
	}
	
	protected void validateIntegerGreaterThanZero(Integer value, String errorMessage, List<String> errors) {
		if (value != null) {
			if (value <= 0) {
				errors.add(errorMessage);
			}
		}
	}
	
}

package com.framegen.core.validation.option;

import java.util.ArrayList;
import java.util.List;

import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.ZoomSettingsVO;
import com.framegen.core.validation.IOptionValidator;
import com.framegen.core.validation.OptionValidatorBase;

public class ZoomOptionValidatorImpl extends OptionValidatorBase implements
		IOptionValidator {

	@Override
	public List<String> validate(SettingsVO settingsVO) {
		List<String> errors = new ArrayList<String>();
		
		ZoomSettingsVO zoomSettings = settingsVO.getZoomSettings();
		if (zoomSettings == null) {
			errors.add("Zoom settings must be specified");
			return errors;
		}
		
		validateNumberNotNull(zoomSettings.getNumberOfZooms(), "Number of zooms must be specified", errors);
		validateNumberNotNull(zoomSettings.getZoomFactor(), "Zoom factor must be specified", errors);
		validateNumberNotNull(zoomSettings.getZoomIncrement(), "Zoom increment must be specified", errors);
		
		validateDoubleGreaterThanZero(zoomSettings.getxZoom(), "X zoom position must be greater than zero", errors);
		validateDoubleGreaterThanZero(zoomSettings.getyZoom(), "Y zoom position must be greater than zero", errors);
		validateIntegerGreaterThanZero(zoomSettings.getNumberOfZooms(), "Number of zooms must be greater than zero", errors);
		validateDoubleGreaterThanZero(zoomSettings.getZoomFactor(), "Zoom factor must be greater than zero", errors);
		validateDoubleGreaterThanZero(zoomSettings.getZoomIncrement(), "Zoom icrement must be greater than zero", errors);
		
		return errors;
	}

}

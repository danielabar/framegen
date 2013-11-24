package com.framegen.core.validation;

import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.SettingsVO;
import com.framegen.core.validation.option.*;

public class OptionValidatorFactoryImpl implements IOptionValidatorFactory {

	@Override
	public IOptionValidator getOptionValidator(SettingsVO settings) {
		
		if (settings == null) {
			throw new UnsupportedOperationException("settings cannot be null");
		}
		
		FrameOption frameOption = settings.getFrameOption();
		switch (frameOption) {
		case LINE:
			return new LineOptionValidatorImpl();
		case ROTATE:
			return new RotateOptionValidatorImpl();
		case ARC:
			return new ArcOptionValidatorImpl();
		case ZOOM:
			return new ZoomOptionValidatorImpl();
		case NEGATIVE:
			return new NegativeOptionValidatorImpl();
		case FADE:
			return new FadeOptionValidatorImpl();
		case TRANSP:
			return new TranspOptionValidatorImpl();
		default:
			throw new UnsupportedOperationException("Unsupported frame option");
		}
	}

}

package com.framegen.core.framehandler;

import com.framegen.api.service.IFrameHandler;
import com.framegen.api.service.IFrameHandlerFactory;
import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.SettingsVO;
import com.framegen.core.framehandler.option.*;

public class FrameHandlerFactoryImpl implements IFrameHandlerFactory {
	
	@Override
	public IFrameHandler getFrameHandler(SettingsVO settings) {
		
		if (settings == null) {
			throw new UnsupportedOperationException("settings cannot be null");
		}
		
		FrameOption frameOption = settings.getFrameOption();
		switch (frameOption) {
		case LINE:
			return new LineFrameHandler();
		case ROTATE:
			return new RotationFrameHandler();
		case ARC:
			return new ArcFrameHandler();
		case ZOOM:
			return new ZoomFrameHandler();
		case NEGATIVE:
			return new NegativeFrameHandler();
		case FADE:
			return new FadeFrameHandler();
		case TRANSP:
			return new TranspFrameHandler();
		default:
			throw new UnsupportedOperationException("Unrecognized frame option");
		}
	}
}

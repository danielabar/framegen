package com.framegen.ui.component;

import javax.swing.JPanel;

import com.framegen.api.settings.FrameOption;
import com.framegen.ui.component.options.*;

public class OptionSettingsPanelFactory {

	JPanel getOptionsSettingsPanel(FrameOption frameOption) {
		switch (frameOption) {
		case LINE:
			return new LineSettingsPanel(FrameOption.LINE);
		case ROTATE:
			return new RotationSettingsPanel(FrameOption.ROTATE);
		case ARC:
			return new ArcSettingsPanel(FrameOption.ARC);
		case ZOOM:
			return new ZoomSettingsPanel(FrameOption.ZOOM);
		case NEGATIVE:
			return new NegativeSettingsPanel(FrameOption.NEGATIVE);
		case FADE:
			return new FadeSettingsPanel(FrameOption.FADE);
		case TRANSP:
			return new TranspSettingsPanel(FrameOption.TRANSP);
		default:
			return new UnimplementedSettingsPanel();
		}
	}

}

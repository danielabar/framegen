package com.framegen.ui.apibridge;

import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.*;
import com.framegen.ui.mvp.IFramegenModel;
import com.google.common.base.Function;

public class SettingsTransformer implements Function<IFramegenModel, SettingsVO> {

	@Override
	public SettingsVO apply(IFramegenModel model) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(new ProgramSettingsTransformer().apply(model));
		
		IOptionSettingsVO optionVO = model.getOptionSettingsVO();
		
		if (optionVO instanceof ArcSettingsVO) {
			vo.setArcSettings((ArcSettingsVO) optionVO);
			return vo;
		}
		
		if (optionVO instanceof LineSettingsVO) {
			vo.setLineSettings((LineSettingsVO) optionVO);
			return vo;
		}
		
		if (optionVO instanceof RotationSettingsVO) {
			vo.setRotationSettings((RotationSettingsVO) optionVO);
			return vo;
		}
		
		if (optionVO instanceof ZoomSettingsVO) {
			vo.setZoomSettings((ZoomSettingsVO) optionVO);
			return vo;
		}
		
		if (optionVO instanceof NegativeSettingsVO) {
			vo.setNegativeSettings((NegativeSettingsVO) optionVO);
			return vo;
		}
		
		if (optionVO instanceof FadeSettingsVO) {
			vo.setFadeSettings((FadeSettingsVO) optionVO);
			return vo;
		}
		
		if (optionVO instanceof TranspSettingsVO) {
			vo.setTranspSettings((TranspSettingsVO) optionVO);
			return vo;
		}
		
		return vo;
	}

}

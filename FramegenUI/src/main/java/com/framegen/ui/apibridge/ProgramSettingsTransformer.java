package com.framegen.ui.apibridge;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.ui.mvp.IFramegenModel;
import com.google.common.base.Function;

public class ProgramSettingsTransformer implements Function<IFramegenModel, ProgramSettingsVO> {

	@Override
	public ProgramSettingsVO apply(IFramegenModel model) {
		ProgramSettingsVO vo = new ProgramSettingsVO();
		vo.setBaseImage(model.getBaseImage());
		vo.setOverlayImage(model.getOverlayImage());
		vo.setOutputDir(model.getOutputDir());
		vo.setGeneratedImageNamePrefix(model.getGeneratedImageNamePrefix());
		return vo;
	}

}

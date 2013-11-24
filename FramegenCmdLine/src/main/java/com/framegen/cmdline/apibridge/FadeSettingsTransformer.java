package com.framegen.cmdline.apibridge;

import com.framegen.api.settings.option.FadeSettingsVO;
import com.framegen.cmdline.settings.FadeSettings;
import com.google.common.base.Function;

public class FadeSettingsTransformer implements Function<FadeSettings, FadeSettingsVO> {

	public FadeSettingsVO apply(FadeSettings input) {
		FadeSettingsVO vo = new FadeSettingsVO();
		vo.setSteps(input.getSteps());
		return vo;
	}

}

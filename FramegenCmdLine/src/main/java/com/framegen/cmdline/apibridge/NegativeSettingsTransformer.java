package com.framegen.cmdline.apibridge;

import com.framegen.api.settings.option.NegativeSettingsVO;
import com.framegen.cmdline.settings.NegativeSettings;
import com.google.common.base.Function;

public class NegativeSettingsTransformer implements Function<NegativeSettings, NegativeSettingsVO> {

	public NegativeSettingsVO apply(NegativeSettings input) {
		NegativeSettingsVO vo = new NegativeSettingsVO();
		vo.setSteps(input.getSteps());
		return vo;
	}

}

package com.framegen.cmdline.apibridge;

import com.framegen.api.settings.option.TranspSettingsVO;
import com.framegen.cmdline.settings.TranspSettings;
import com.google.common.base.Function;

public class TranspSettingsTransformer implements Function<TranspSettings, TranspSettingsVO> {

	public TranspSettingsVO apply(TranspSettings input) {
		TranspSettingsVO vo = new TranspSettingsVO();
		vo.setDirection(input.getDirection());
		vo.setSteps(input.getSteps());
		vo.setxPos(input.getxPos());
		vo.setyPos(input.getyPos());
		return vo;
	}

}

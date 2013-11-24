package com.framegen.cmdline.apibridge;

import com.framegen.api.settings.option.RotationSettingsVO;
import com.framegen.cmdline.settings.RotationSettings;
import com.google.common.base.Function;

public class RotationSettingsTransformer implements Function<RotationSettings, RotationSettingsVO> {

	public RotationSettingsVO apply(RotationSettings input) {
		RotationSettingsVO vo = new RotationSettingsVO();
		vo.setDegrees(input.getDegrees());
		vo.setStep(input.getStep());
		vo.setxPosition(input.getxPosition());
		vo.setxScale(input.getxScale());
		vo.setyPosition(input.getyPosition());
		vo.setyScale(input.getyScale());
		return vo;
	}

}

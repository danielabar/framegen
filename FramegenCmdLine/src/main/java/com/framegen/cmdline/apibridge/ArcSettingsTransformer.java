package com.framegen.cmdline.apibridge;

import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.cmdline.settings.ArcSettings;
import com.google.common.base.Function;

public class ArcSettingsTransformer implements Function<ArcSettings, ArcSettingsVO> {

	public ArcSettingsVO apply(ArcSettings input) {
		ArcSettingsVO vo = new ArcSettingsVO();
		vo.setMoveIncrement(input.getMoveIncrement());
		vo.setRadius(input.getRadius());
		vo.setReverseSequence(input.isReverseSequence());
		vo.setRotateBy(input.getRotateBy());
		vo.setShortestRoute(input.isShortestRoute());
		vo.setSide(input.isSide());
		vo.setxEnd(input.getxEnd());
		vo.setxScale(input.getxScale());
		vo.setxStart(input.getxStart());
		vo.setyEnd(input.getyEnd());
		vo.setyScale(input.getyScale());
		vo.setyStart(input.getyStart());
		return vo;
	}

}

package com.framegen.cmdline.apibridge;

import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.cmdline.settings.LineSettings;
import com.google.common.base.Function;

public class LineSettingsTransformer implements Function<LineSettings, LineSettingsVO> {

	public LineSettingsVO apply(LineSettings input) {
		LineSettingsVO vo = new LineSettingsVO();
		vo.setMoveIncrement(input.getMoveIncrement());
		vo.setRotateBy(input.getRotateBy());
		vo.setxEnd(input.getxEnd());
		vo.setxScale(input.getxScale());
		vo.setxStart(input.getxStart());
		vo.setyEnd(input.getyEnd());
		vo.setyScale(input.getyScale());
		vo.setyStart(input.getyStart());
		return vo;
	}

}

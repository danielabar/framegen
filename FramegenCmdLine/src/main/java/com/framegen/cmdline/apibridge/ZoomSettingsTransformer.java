package com.framegen.cmdline.apibridge;

import com.framegen.api.settings.option.ZoomSettingsVO;
import com.framegen.cmdline.settings.ZoomSettings;
import com.google.common.base.Function;

public class ZoomSettingsTransformer implements Function<ZoomSettings, ZoomSettingsVO> {

	public ZoomSettingsVO apply(ZoomSettings input) {
		ZoomSettingsVO vo = new ZoomSettingsVO();
		vo.setNumberOfZooms(input.getNumberOfZooms());
		vo.setxZoom(input.getxZoom());
		vo.setyZoom(input.getyZoom());
		vo.setZoomFactor(input.getZoomFactor());
		vo.setZoomIncrement(input.getZoomIncremenet());
		return vo;
	}

}

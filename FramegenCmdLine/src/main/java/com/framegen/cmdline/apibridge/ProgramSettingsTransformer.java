package com.framegen.cmdline.apibridge;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.cmdline.settings.ProgramSettings;
import com.google.common.base.Function;

public class ProgramSettingsTransformer implements Function<ProgramSettings, ProgramSettingsVO> {

	public ProgramSettingsVO apply(ProgramSettings input) {
		ProgramSettingsVO vo = new ProgramSettingsVO();
		vo.setHelp(input.isHelp());
		vo.setBaseImage(input.getBaseImage());
		vo.setGeneratedImageNamePrefix(input.getGeneratedImageNamePrefix());
		vo.setGenPointsCsv(input.isGenPointsCsv());
		vo.setOutputDir(input.getOutputDir());
		vo.setOverlayImage(input.getOverlayImage());
		return vo;
	}

}

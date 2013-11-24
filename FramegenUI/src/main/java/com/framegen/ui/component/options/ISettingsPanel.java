package com.framegen.ui.component.options;

import com.framegen.api.settings.option.IOptionSettingsVO;

public interface ISettingsPanel {
	
	public static final int NUMERIC_FIELD_WIDTH = 5;
	
	public IOptionSettingsVO getOptionSettingsVO();
	
	public void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO);
}

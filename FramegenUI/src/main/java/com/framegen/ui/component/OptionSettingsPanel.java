package com.framegen.ui.component;

import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.option.IOptionSettingsVO;
import com.framegen.ui.component.options.ISettingsPanel;
import com.framegen.ui.component.options.SettingsPanelBase;
import com.framegen.ui.mvp.IFramegenView;

public class OptionSettingsPanel extends JTabbedPane implements ChangeListener {

	private static final long serialVersionUID = 441325011868601115L;
	
	private static final int PANEL_WIDTH = 500;
	private static final int PANEL_HEIGHT = 270;
	
	private final IFramegenView framegenView;
	private final OptionSettingsPanelFactory optionSettingsPanelFactory;

	public OptionSettingsPanel(IFramegenView framegenView) {
		super();
		this.framegenView = framegenView;
		this.optionSettingsPanelFactory = new OptionSettingsPanelFactory();
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		addChangeListener(this);
		initComponents();
	}

	private void initComponents() {
		for (FrameOption frameOption : FrameOption.values()) { 
			addTab(frameOption.name(), optionSettingsPanelFactory.getOptionsSettingsPanel(frameOption));
		}
	}
	
	public IOptionSettingsVO getOptionSettingsVO() {
		ISettingsPanel selectedComponent = (ISettingsPanel) getSelectedComponent();
		return selectedComponent.getOptionSettingsVO();
	}
	
	public void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO) {
		ISettingsPanel selectedComponent = (ISettingsPanel) getSelectedComponent();
		selectedComponent.setOptionSettingsVO(optionSettingsVO);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		SettingsPanelBase settingsPanel = (SettingsPanelBase) this.getSelectedComponent();
		if (settingsPanel != null) {
			framegenView.enableDisableOverlaySelection(settingsPanel.getFrameOption());
		}
	}
	
}

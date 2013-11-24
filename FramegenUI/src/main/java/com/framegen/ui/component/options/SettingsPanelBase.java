package com.framegen.ui.component.options;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.framegen.api.settings.FrameOption;
import com.framegen.ui.util.FramegenWidgetFactoryImpl;
import com.framegen.ui.util.IFramegenWidgetFactory;

public class SettingsPanelBase extends JPanel {

	private static final long serialVersionUID = 3487722286070276059L;
	
	protected final FrameOption frameOption;
	
	protected final IFramegenWidgetFactory widgetFactory;
	
	protected JPanel requiredFields;
	protected JPanel optionalFields;
	
	public SettingsPanelBase(FrameOption frameOption) {
		super();
		this.frameOption = frameOption;
		this.widgetFactory = new FramegenWidgetFactoryImpl();
		setLayout(new MigLayout());
	}
	
	public SettingsPanelBase(FrameOption frameOption, String layoutConstraints, String columnConstraints, String rowConstraints) {
		super();
		this.frameOption = frameOption;
		this.widgetFactory = new FramegenWidgetFactoryImpl();
		setLayout(new MigLayout(layoutConstraints, columnConstraints, rowConstraints));
	}
	
	protected void initRequiredFieldsPanel(String layoutConstraints, String columnConstraints, String rowConstraints) {
		requiredFields = new JPanel(new MigLayout(layoutConstraints, columnConstraints, rowConstraints));
		requiredFields.setBorder(widgetFactory.createSectionBorder("Required"));
	}
	
	protected void initOptionalFieldsPanel(String layoutConstraints, String columnConstraints, String rowConstraints) {
		optionalFields = new JPanel(new MigLayout(layoutConstraints, columnConstraints, rowConstraints));
		optionalFields.setBorder(widgetFactory.createSectionBorder("Optional"));
	}

	public FrameOption getFrameOption() {
		return frameOption;
	}

}

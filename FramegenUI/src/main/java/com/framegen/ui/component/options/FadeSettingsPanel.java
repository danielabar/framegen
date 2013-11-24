package com.framegen.ui.component.options;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.option.FadeSettingsVO;
import com.framegen.api.settings.option.IOptionSettingsVO;

public class FadeSettingsPanel extends SettingsPanelBase implements ISettingsPanel, PropertyChangeListener {

	private static final long serialVersionUID = 1782777642138989079L;
	
	private FadeSettingsVO fadeSettingsVO;
	
	private JLabel stepsLabel;
	private JFormattedTextField stepsField;
	
	public FadeSettingsPanel(FrameOption frameOption) {
		super(frameOption);
		this.fadeSettingsVO = new FadeSettingsVO();
		initComponents();
		layoutComponents();
	}
	
	private void initComponents() {
		stepsLabel = new JLabel("Number of steps");
		stepsField = widgetFactory.createIntegerField(this);
	}
	
	private void layoutComponents() {
		initRequiredFieldsPanel("", "[]10[]", "");
		
		requiredFields.add(stepsLabel);
		requiredFields.add(stepsField);
		
		add(requiredFields);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		 
		 if (source == stepsField && stepsField.getValue() != null) {
			 this.fadeSettingsVO.setSteps(((Number)stepsField.getValue()).intValue());
		 }
	}

	@Override
	public IOptionSettingsVO getOptionSettingsVO() {
		return this.fadeSettingsVO;
	}

	@Override
	public void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO) {
		if (optionSettingsVO instanceof FadeSettingsVO) {
			this.fadeSettingsVO = (FadeSettingsVO) optionSettingsVO;
			this.stepsField.setValue(fadeSettingsVO.getSteps());
		}
	}

}

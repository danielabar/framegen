package com.framegen.ui.component.options;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.option.IOptionSettingsVO;
import com.framegen.api.settings.option.NegativeSettingsVO;

public class NegativeSettingsPanel extends SettingsPanelBase implements ISettingsPanel, PropertyChangeListener {

	private static final long serialVersionUID = -4812865582654399358L;
	
	private NegativeSettingsVO negativeSettingsVO;
	
	private JLabel stepsLabel;
	private JFormattedTextField stepsField;
	
	public NegativeSettingsPanel(FrameOption frameOption) {
		super(frameOption);
		this.negativeSettingsVO = new NegativeSettingsVO();
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
			 this.negativeSettingsVO.setSteps(((Number)stepsField.getValue()).intValue());
		 }

	}

	@Override
	public IOptionSettingsVO getOptionSettingsVO() {
		return this.negativeSettingsVO;
	}

	@Override
	public void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO) {
		if (optionSettingsVO instanceof NegativeSettingsVO) {
			this.negativeSettingsVO = (NegativeSettingsVO)optionSettingsVO;
			this.stepsField.setValue(negativeSettingsVO.getSteps()); 
		}

	}

}

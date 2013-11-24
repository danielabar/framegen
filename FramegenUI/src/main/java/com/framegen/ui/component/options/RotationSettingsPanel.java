package com.framegen.ui.component.options;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.option.IOptionSettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.api.settings.option.RotationSettingsVO;

public class RotationSettingsPanel extends SettingsPanelBase implements ISettingsPanel, PropertyChangeListener {

	private static final long serialVersionUID = 7464702541512061167L;
	
	private RotationSettingsVO rotationSettingsVO;
	
	private JLabel xPositionLabel;
	private JFormattedTextField xPositionField;
	
	private JLabel yPositionLabel;
	private JFormattedTextField yPositionField;
	
	private JLabel degreesLabel;
	private JFormattedTextField degreesField;
	
	private JLabel stepLabel;
	private JFormattedTextField stepField;
	
	private JLabel xScaleLabel;
	private JFormattedTextField xScaleField;
	private JLabel yScaleLabel;
	private JFormattedTextField yScaleField;
	
	public RotationSettingsPanel(FrameOption frameOption) {
		super(frameOption, "",  "", "[]15[]");
		this.rotationSettingsVO = new RotationSettingsVO();
		initComponents();
		layoutComponents();
	}

	private void initComponents() {
		initRequiredFields();
		layoutRequiredFields();
		
		initOptionalFields();
		layoutOptionalFields();
	}
	
	private void layoutComponents() {
		add(requiredFields, "wrap");
		add(optionalFields, "wrap");
	}

	private void initRequiredFields() {
		xPositionLabel = new JLabel("X Position");
		xPositionField = widgetFactory.createIntegerField(this);
		
		yPositionLabel = new JLabel("Y Position");
		yPositionField = widgetFactory.createIntegerField(this);
		
		degreesLabel = new JLabel("Degrees");
		degreesField = widgetFactory.createIntegerField(this);
		
		stepLabel = new JLabel("Step");
		stepField = widgetFactory.createIntegerField(this);
	}
	
	private void layoutRequiredFields() {
		initRequiredFieldsPanel("", "[]10[]15[]10[]", "");

		requiredFields.add(xPositionLabel);
		requiredFields.add(xPositionField);
		
		requiredFields.add(yPositionLabel, "gap unrelated");
		requiredFields.add(yPositionField, "wrap");
		
		requiredFields.add(degreesLabel);
		requiredFields.add(degreesField);
		
		requiredFields.add(stepLabel, "gap unrelated");
		requiredFields.add(stepField, "wrap");
	}
	
	private void initOptionalFields() {
		xScaleLabel = new JLabel("X Scale");
		xScaleField = widgetFactory.createIntegerField(this);

		yScaleLabel = new JLabel("Y Scale");
		yScaleField = widgetFactory.createIntegerField(this);
	}
	
	private void layoutOptionalFields() {
		initOptionalFieldsPanel("", "[]10[]15[]10[]", "");
		
		optionalFields.add(xScaleLabel);
		optionalFields.add(xScaleField);
		
		optionalFields.add(yScaleLabel, "gap unrelated");
		optionalFields.add(yScaleField, "wrap");
	}
	
	@Override
	public IOptionSettingsVO getOptionSettingsVO() {
		return this.rotationSettingsVO;
	}

	@Override
	public void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO) {
		if (optionSettingsVO instanceof LineSettingsVO) {
			this.rotationSettingsVO = (RotationSettingsVO) optionSettingsVO;
			this.xPositionField.setValue(rotationSettingsVO.getxPosition());
			this.yPositionField.setValue(rotationSettingsVO.getyPosition());
			this.degreesField.setValue(rotationSettingsVO.getDegrees());
			this.stepField.setValue(rotationSettingsVO.getStep());
			this.xScaleField.setValue(rotationSettingsVO.getxScale());
			this.yScaleField.setValue(rotationSettingsVO.getyScale());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		 Object source = e.getSource();
		 
		 if (source == xPositionField && xPositionField.getValue() != null) {
			 this.rotationSettingsVO.setxPosition(((Number)xPositionField.getValue()).intValue());
		 }
		 
		 if (source == yPositionField && yPositionField.getValue() != null) {
			 this.rotationSettingsVO.setyPosition(((Number)yPositionField.getValue()).intValue());
		 }
		 
		 if (source == degreesField && degreesField.getValue() != null) {
			 this.rotationSettingsVO.setDegrees(((Number)degreesField.getValue()).intValue());
		 }
		 
		 if (source == stepField && stepField.getValue() != null) {
			 this.rotationSettingsVO.setStep(((Number)stepField.getValue()).intValue());
		 }
		 
		 if (source == xScaleField && xScaleField.getValue() != null) {
			 this.rotationSettingsVO.setxScale(((Number)xScaleField.getValue()).intValue());
		 }
		 
		 if (source == yScaleField && yScaleField.getValue() != null) {
			 this.rotationSettingsVO.setyScale(((Number)yScaleField.getValue()).intValue());
		 }
		 
	}
	
}

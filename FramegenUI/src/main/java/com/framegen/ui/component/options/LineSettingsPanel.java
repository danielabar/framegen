package com.framegen.ui.component.options;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.option.IOptionSettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;

public class LineSettingsPanel extends SettingsPanelBase implements ISettingsPanel, PropertyChangeListener {

	private static final long serialVersionUID = 7464702541512061167L;
	
	private LineSettingsVO lineSettingsVO;
	
	private JLabel xStartLabel;
	private JFormattedTextField xStartField;
	private JLabel yStartLabel;
	private JFormattedTextField yStartField;
	private JLabel xEndLabel;
	private JFormattedTextField xEndField;
	private JLabel yEndLabel;
	private JFormattedTextField yEndField;
	private JLabel incrementLabel;
	private JFormattedTextField incrementField;
	
	private JLabel xScaleLabel;
	private JFormattedTextField xScaleField;
	private JLabel yScaleLabel;
	private JFormattedTextField yScaleField;
	private JLabel rotateByLabel;
	private JFormattedTextField rotateByField;
	
	public LineSettingsPanel(FrameOption frameOption) {
		super(frameOption, "",  "", "[]15[]");
		this.lineSettingsVO = new LineSettingsVO();
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
		xStartLabel = new JLabel("X Start");
		xStartField = widgetFactory.createIntegerField(this);
		
		yStartLabel = new JLabel("Y Start");
		yStartField = widgetFactory.createIntegerField(this);
		
		xEndLabel = new JLabel("X End");
		xEndField = widgetFactory.createIntegerField(this);
		
		yEndLabel = new JLabel("Y End");
		yEndField = widgetFactory.createIntegerField(this);
		
		incrementLabel = new JLabel("Increment");
		incrementField = widgetFactory.createDoubleField(this);
	}
	
	private void layoutRequiredFields() {
		initRequiredFieldsPanel("", "[]10[]15[]10[]", "");

		requiredFields.add(xStartLabel);
		requiredFields.add(xStartField);
		
		requiredFields.add(yStartLabel, "gap unrelated");
		requiredFields.add(yStartField, "wrap");
		
		requiredFields.add(xEndLabel);
		requiredFields.add(xEndField);
		
		requiredFields.add(yEndLabel, "gap unrelated");
		requiredFields.add(yEndField, "wrap");
		
		requiredFields.add(incrementLabel);
		requiredFields.add(incrementField);
	}
	
	private void initOptionalFields() {
		xScaleLabel = new JLabel("X Scale");
		xScaleField = widgetFactory.createIntegerField(this);

		yScaleLabel = new JLabel("Y Scale");
		yScaleField = widgetFactory.createIntegerField(this);

		rotateByLabel = new JLabel("Rotate by");
		rotateByField = widgetFactory.createIntegerField(this);
	}
	
	private void layoutOptionalFields() {
		initOptionalFieldsPanel("", "[]10[]15[]10[]", "");
		
		optionalFields.add(xScaleLabel);
		optionalFields.add(xScaleField);
		
		optionalFields.add(yScaleLabel, "gap unrelated");
		optionalFields.add(yScaleField, "wrap");
		
		optionalFields.add(rotateByLabel);
		optionalFields.add(rotateByField);
	}
	
	@Override
	public IOptionSettingsVO getOptionSettingsVO() {
		return this.lineSettingsVO;
	}

	@Override
	public void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO) {
		if (optionSettingsVO instanceof LineSettingsVO) {
			this.lineSettingsVO = (LineSettingsVO) optionSettingsVO;
			this.xStartField.setValue(lineSettingsVO.getxStart());
			this.yStartField.setValue(lineSettingsVO.getyStart());
			this.xEndField.setValue(lineSettingsVO.getxEnd());
			this.yEndField.setValue(lineSettingsVO.getyEnd());
			this.incrementField.setValue(lineSettingsVO.getMoveIncrement());
			this.xScaleField.setValue(lineSettingsVO.getxScale());
			this.yScaleField.setValue(lineSettingsVO.getyScale());
			this.rotateByField.setValue(lineSettingsVO.getRotateBy());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		 Object source = e.getSource();
		 
		 if (source == xStartField && xStartField.getValue() != null) {
			 this.lineSettingsVO.setxStart(((Number)xStartField.getValue()).intValue());
		 }
		 
		 if (source == yStartField && yStartField.getValue() != null) {
			 this.lineSettingsVO.setyStart(((Number)yStartField.getValue()).intValue());
		 }
		 
		 if (source == xEndField && xEndField.getValue() != null) {
			 this.lineSettingsVO.setxEnd(((Number)xEndField.getValue()).intValue());
		 }
		 
		 if (source == yEndField && yEndField.getValue() != null) {
			 this.lineSettingsVO.setyEnd(((Number)yEndField.getValue()).intValue());
		 }
		 
		 if (source == incrementField && incrementField.getValue() != null) {
			 this.lineSettingsVO.setMoveIncrement(((Number)incrementField.getValue()).doubleValue());
		 }
		 
		 if (source == xScaleField && xScaleField.getValue() != null) {
			 this.lineSettingsVO.setxScale(((Number)xScaleField.getValue()).intValue());
		 }
		 
		 if (source == yScaleField && yScaleField.getValue() != null) {
			 this.lineSettingsVO.setyScale(((Number)yScaleField.getValue()).intValue());
		 }
		 
		 if (source == rotateByField && rotateByField.getValue() != null) {
			 this.lineSettingsVO.setRotateBy(((Number)rotateByField.getValue()).intValue());
		 }
		
	}
	
}

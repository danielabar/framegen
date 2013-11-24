package com.framegen.ui.component.options;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.api.settings.option.IOptionSettingsVO;

public class ArcSettingsPanel extends SettingsPanelBase implements ISettingsPanel, PropertyChangeListener, ItemListener {

	private static final long serialVersionUID = 5090731390785385647L;
	
	private ArcSettingsVO arcSettingsVO;
	
	private JLabel xStartLabel;
	private JFormattedTextField xStartField;
	private JLabel yStartLabel;
	private JFormattedTextField yStartField;
	private JLabel xEndLabel;
	private JFormattedTextField xEndField;
	private JLabel yEndLabel;
	private JFormattedTextField yEndField;
	private JLabel radiusLabel;
	private JFormattedTextField radiusField;
	private JLabel incrementLabel;
	private JFormattedTextField incrementField;
	
	private JCheckBox shortestRouteCheckBox;
	private JCheckBox sideCheckBox;
	private JCheckBox reverseSequenceCheckBox;
	
	private JLabel xScaleLabel;
	private JFormattedTextField xScaleField;
	private JLabel yScaleLabel;
	private JFormattedTextField yScaleField;
	private JLabel rotateByLabel;
	private JFormattedTextField rotateByField;
	
	public ArcSettingsPanel(FrameOption frameOption) {
		super(frameOption);
		this.arcSettingsVO = new ArcSettingsVO();
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
		
		radiusLabel = new JLabel("Radius");
		radiusField = widgetFactory.createDoubleField(this);
		
		incrementLabel = new JLabel("Increment");
		incrementField = widgetFactory.createDoubleField(this);
		
		shortestRouteCheckBox = new JCheckBox("Shortest Route");
		shortestRouteCheckBox.addItemListener(this);
		
		sideCheckBox = new JCheckBox("Side");
		sideCheckBox.addItemListener(this);
		
		reverseSequenceCheckBox = new JCheckBox("Reverse Sequence");
		reverseSequenceCheckBox.addItemListener(this);
	}
	
	private void layoutRequiredFields() {
		initRequiredFieldsPanel("", "[]10[]15[]10[]", "[][][]15[][]");

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
		
		requiredFields.add(radiusLabel, "gap unrelated");
		requiredFields.add(radiusField, "wrap");
		
		requiredFields.add(shortestRouteCheckBox, "skip 1, span 2");
		requiredFields.add(sideCheckBox, "span 2, wrap");
		requiredFields.add(reverseSequenceCheckBox, "skip 1, span 2");
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
		return this.arcSettingsVO;
	}

	@Override
	public void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO) {
		if (optionSettingsVO instanceof ArcSettingsVO) {
			this.arcSettingsVO = (ArcSettingsVO) optionSettingsVO;
			this.xStartField.setValue(arcSettingsVO.getxStart());
			this.yStartField.setValue(arcSettingsVO.getyStart());
			this.xEndField.setValue(arcSettingsVO.getxEnd());
			this.yEndField.setValue(arcSettingsVO.getyEnd());
			this.incrementField.setValue(arcSettingsVO.getMoveIncrement());
			this.radiusField.setValue(arcSettingsVO.getRadius());
			this.xScaleField.setValue(arcSettingsVO.getxScale());
			this.yScaleField.setValue(arcSettingsVO.getyScale());
			this.rotateByField.setValue(arcSettingsVO.getRotateBy());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		 Object source = e.getSource();
		 
		 if (source == xStartField && xStartField.getValue() != null) {
			 this.arcSettingsVO.setxStart(((Number)xStartField.getValue()).intValue());
		 }
		 
		 if (source == yStartField && yStartField.getValue() != null) {
			 this.arcSettingsVO.setyStart(((Number)yStartField.getValue()).intValue());
		 }
		 
		 if (source == xEndField && xEndField.getValue() != null) {
			 this.arcSettingsVO.setxEnd(((Number)xEndField.getValue()).intValue());
		 }
		 
		 if (source == yEndField && yEndField.getValue() != null) {
			 this.arcSettingsVO.setyEnd(((Number)yEndField.getValue()).intValue());
		 }
		 
		 if (source == radiusField && radiusField.getValue() != null) {
			 this.arcSettingsVO.setRadius(((Number)radiusField.getValue()).doubleValue());
		 }
		 
		 if (source == incrementField && incrementField.getValue() != null) {
			 this.arcSettingsVO.setMoveIncrement(((Number)incrementField.getValue()).doubleValue());
		 }
		 
		 if (source == xScaleField && xScaleField.getValue() != null) {
			 this.arcSettingsVO.setxScale(((Number)xScaleField.getValue()).intValue());
		 }
		 
		 if (source == yScaleField && yScaleField.getValue() != null) {
			 this.arcSettingsVO.setyScale(((Number)yScaleField.getValue()).intValue());
		 }
		 
		 if (source == rotateByField && rotateByField.getValue() != null) {
			 this.arcSettingsVO.setRotateBy(((Number)rotateByField.getValue()).intValue());
		 }
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		
		if (source == shortestRouteCheckBox && e.getStateChange() == ItemEvent.SELECTED) {
			this.arcSettingsVO.setShortestRoute(true);
		}
		
		if (source == shortestRouteCheckBox && e.getStateChange() == ItemEvent.DESELECTED) {
			this.arcSettingsVO.setShortestRoute(false);
		}
		
		if (source == sideCheckBox && e.getStateChange() == ItemEvent.SELECTED) {
			this.arcSettingsVO.setSide(true);
		}
		
		if (source == sideCheckBox && e.getStateChange() == ItemEvent.DESELECTED) {
			this.arcSettingsVO.setSide(false);
		}
		
		if (source == reverseSequenceCheckBox && e.getStateChange() == ItemEvent.SELECTED) {
			this.arcSettingsVO.setReverseSequence(true);
		}
		
		if (source == reverseSequenceCheckBox && e.getStateChange() == ItemEvent.DESELECTED) {
			this.arcSettingsVO.setReverseSequence(false);
		}
	}
	
}

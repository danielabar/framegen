package com.framegen.ui.component.options;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.option.IOptionSettingsVO;
import com.framegen.api.settings.option.ZoomSettingsVO;

public class ZoomSettingsPanel extends SettingsPanelBase implements ISettingsPanel, PropertyChangeListener {

	private static final long serialVersionUID = -2049964504861327327L;
	
	private ZoomSettingsVO zoomSettingsVO;
	
	private JLabel numberZoomsLabel;
	private JFormattedTextField numberZoomsField;
	
	private JLabel zoomFactorLabel;
	private JFormattedTextField zoomFactorField;
	
	private JLabel zoomIncrementLabel;
	private JFormattedTextField zoomIncrementField;
	
	private JLabel xZoomLabel;
	private JFormattedTextField xZoomField;

	private JLabel yZoomLabel;
	private JFormattedTextField yZoomField;
	
	public ZoomSettingsPanel(FrameOption frameOption) {
		super(frameOption, "", "", "[]15[]");
		this.zoomSettingsVO = new ZoomSettingsVO();
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
		numberZoomsLabel = new JLabel("Number of zooms");
		numberZoomsField = widgetFactory.createIntegerField(this);
		
		zoomFactorLabel = new JLabel("Initial zoom factor");
		zoomFactorField = widgetFactory.createDoubleField(this);
		
		zoomIncrementLabel = new JLabel("Zoom increment");
		zoomIncrementField = widgetFactory.createDoubleField(this);
	}
	
	private void layoutRequiredFields() {
		initRequiredFieldsPanel("", "[]10[]", "");
		
		requiredFields.add(numberZoomsLabel);
		requiredFields.add(numberZoomsField, "wrap");
		
		requiredFields.add(zoomFactorLabel);
		requiredFields.add(zoomFactorField, "wrap");
		
		requiredFields.add(zoomIncrementLabel);
		requiredFields.add(zoomIncrementField);
		
	}
	
	private void initOptionalFields() {
		xZoomLabel = new JLabel("X Position");
		xZoomField = widgetFactory.createDoubleField(this);
		
		yZoomLabel = new JLabel("Y Position");
		yZoomField = widgetFactory.createDoubleField(this);
	}
	
	private void layoutOptionalFields() {
		initOptionalFieldsPanel("", "[]10[]15[]10[]", "");

		optionalFields.add(xZoomLabel);
		optionalFields.add(xZoomField);
		
		optionalFields.add(yZoomLabel, "gap unrelated");
		optionalFields.add(yZoomField, "wrap");
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		 
		 if (source == xZoomField && xZoomField.getValue() != null) {
			 this.zoomSettingsVO.setxZoom(((Number)xZoomField.getValue()).doubleValue());
		 }
		 
		 if (source == yZoomField && yZoomField.getValue() != null) {
			 this.zoomSettingsVO.setyZoom(((Number)yZoomField.getValue()).doubleValue());
		 }
		 
		 if (source == numberZoomsField && numberZoomsField.getValue() != null) {
			 this.zoomSettingsVO.setNumberOfZooms(((Number)numberZoomsField.getValue()).intValue());
		 }
		 
		 if (source == zoomFactorField && zoomFactorField.getValue() != null) {
			 this.zoomSettingsVO.setZoomFactor(((Number)zoomFactorField.getValue()).doubleValue());
		 }
		 
		 if (source == zoomIncrementField && zoomIncrementField.getValue() != null) {
			 this.zoomSettingsVO.setZoomIncrement(((Number)zoomIncrementField.getValue()).doubleValue());
		 }
	}

	@Override
	public IOptionSettingsVO getOptionSettingsVO() {
		return this.zoomSettingsVO;
	}

	@Override
	public void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO) {
		if (optionSettingsVO instanceof ZoomSettingsVO) {
			this.zoomSettingsVO = (ZoomSettingsVO)optionSettingsVO;
			this.xZoomField.setValue(zoomSettingsVO.getxZoom()); 
			this.yZoomField.setValue(zoomSettingsVO.getyZoom()); 
			this.numberZoomsField.setValue(zoomSettingsVO.getNumberOfZooms());
			this.zoomFactorField.setValue(zoomSettingsVO.getZoomFactor());
			this.zoomIncrementField.setValue(zoomSettingsVO.getZoomIncrement());
		}

	}

}

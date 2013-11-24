package com.framegen.ui.component.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang3.StringUtils;

import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.option.IOptionSettingsVO;
import com.framegen.api.settings.option.TranspSettingsVO;
import com.framegen.api.settings.option.TranspSettingsVO.TranspDirection;

public class TranspSettingsPanel extends SettingsPanelBase implements ISettingsPanel, PropertyChangeListener, ActionListener {

	private static final long serialVersionUID = -7744990981056741041L;

	private static final String FADE_OUT_OPTION = "Fade Out";
	private static final String FADE_IN_OPTION = "Fade In";

	private TranspSettingsVO transpSettingsVO;
	
	private JLabel stepsLabel;
	private JFormattedTextField stepsField;
	
	private JLabel xPositionLabel;
	private JFormattedTextField xPositionField;

	private JLabel yPositionLabel;
	private JFormattedTextField yPositionField;
	
	private JRadioButton fadeOutOption;
	private JRadioButton fadeInOption;
	
	public TranspSettingsPanel(FrameOption frameOption) {
		super(frameOption);
		this.transpSettingsVO = new TranspSettingsVO();
		setLayout(new MigLayout());
		initComponents();
		layoutComponents();
	}
	
	private void initComponents() {
		stepsLabel = new JLabel("Number of steps");
		stepsField = widgetFactory.createIntegerField(this);
		
		xPositionLabel = new JLabel("X Position");
		xPositionField = widgetFactory.createDoubleField(this);
		
		yPositionLabel = new JLabel("Y Position");
		yPositionField = widgetFactory.createDoubleField(this);
		
		fadeOutOption = new JRadioButton(FADE_OUT_OPTION);
		fadeOutOption.addActionListener(this);
		fadeInOption = new JRadioButton(FADE_IN_OPTION);
		fadeInOption.addActionListener(this);
		ButtonGroup fadeOptions = new ButtonGroup();
		fadeOptions.add(fadeOutOption);
		fadeOptions.add(fadeInOption);
	}
	
	private void layoutComponents() {
		initRequiredFieldsPanel("",  "[]10[]15[]10[]", "[][]10[]");

		requiredFields.add(stepsLabel);
		requiredFields.add(stepsField, "wrap");
		
		requiredFields.add(xPositionLabel);
		requiredFields.add(xPositionField);
		
		requiredFields.add(yPositionLabel, "gap unrelated");
		requiredFields.add(yPositionField, "wrap");
		
		requiredFields.add(fadeOutOption);
		requiredFields.add(fadeInOption);
		
		add(requiredFields);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		 
		 if (source == stepsField && stepsField.getValue() != null) {
			 this.transpSettingsVO.setSteps(((Number)stepsField.getValue()).intValue());
		 }
		 
		 if (source == xPositionField && xPositionField.getValue() != null) {
			 this.transpSettingsVO.setxPos(((Number)xPositionField.getValue()).doubleValue());
		 }
		 
		 if (source == yPositionField && yPositionField.getValue() != null) {
			 this.transpSettingsVO.setyPos(((Number)yPositionField.getValue()).doubleValue());
		 }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if (StringUtils.equals(FADE_OUT_OPTION, actionCommand)) {
			this.transpSettingsVO.setDirection(TranspDirection.FADE_OUT);
		}
		
		if (StringUtils.equals(FADE_IN_OPTION, actionCommand)) {
			this.transpSettingsVO.setDirection(TranspDirection.FADE_IN);
		}
		
	}
	
	@Override
	public IOptionSettingsVO getOptionSettingsVO() {
		return this.transpSettingsVO;
	}

	@Override
	public void setOptionSettingsVO(IOptionSettingsVO optionSettingsVO) {
		if (optionSettingsVO instanceof TranspSettingsVO) {
			this.transpSettingsVO = (TranspSettingsVO) optionSettingsVO;
			this.stepsField.setValue(transpSettingsVO.getSteps());
			this.xPositionField.setValue(transpSettingsVO.getxPos());
			this.yPositionField.setValue(transpSettingsVO.getyPos());
		}
	}

}

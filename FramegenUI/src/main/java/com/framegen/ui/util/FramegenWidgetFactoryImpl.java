package com.framegen.ui.util;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

public class FramegenWidgetFactoryImpl implements IFramegenWidgetFactory {
	
	public static final int NUMERIC_FIELD_WIDTH = 5;

	@Override
	public JFormattedTextField createIntegerField(PropertyChangeListener propertyChangeListener) {
		JFormattedTextField field = new JFormattedTextField(NumberFormat.getIntegerInstance());
		field.setColumns(NUMERIC_FIELD_WIDTH);
		field.addPropertyChangeListener("value", propertyChangeListener);
		return field;
	}

	@Override
	public JFormattedTextField createDoubleField(PropertyChangeListener propertyChangeListener) {
		JFormattedTextField field = new JFormattedTextField(NumberFormat.getNumberInstance());
		field.setColumns(NUMERIC_FIELD_WIDTH);
		field.addPropertyChangeListener("value", propertyChangeListener);
		return field;
	}

	@Override
	public CompoundBorder createSectionBorder(String title) {
		Color borderColor = new Color(175, 250, 248);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borderColor, 2, true), title);
		return BorderFactory.createCompoundBorder(titledBorder, BorderFactory.createEmptyBorder(15,15,15,15));
	}

}

package com.framegen.ui.util;

import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.border.CompoundBorder;

public interface IFramegenWidgetFactory {

	public JFormattedTextField createIntegerField(PropertyChangeListener propertyChangeListener);
	
	public JFormattedTextField createDoubleField(PropertyChangeListener propertyChangeListener);
	
	public CompoundBorder createSectionBorder(String title);
	
}

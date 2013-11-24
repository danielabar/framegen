package com.framegen.ui.component.options;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class UnimplementedSettingsPanel extends JPanel {

	private static final long serialVersionUID = 7074261203703178574L;

	public UnimplementedSettingsPanel() {
		super();
		setLayout(new FlowLayout());
		add(new JLabel("Not yet implemented"));
	}
	
}

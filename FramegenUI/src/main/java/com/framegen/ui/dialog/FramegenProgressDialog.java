package com.framegen.ui.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.apache.commons.collections.CollectionUtils;

/*
 * http://www.zentut.com/java-swing/simple-login-dialog/
 */
public class FramegenProgressDialog extends JDialog {

	private static final long serialVersionUID = -4937887419313506164L;

	private static final int DIALOG_WIDTH = 300;
	private static final int DIALOG_HEIGHT = 200;
	
	private static final int HGAP = 10;
	private static final int VGAP = 10;

	private JLabel progressLabelFinal;
	private JLabel progressLabel;
	private JProgressBar progressBar;

	public FramegenProgressDialog(Frame parent) {
		super(parent);
		setLocationRelativeTo(parent);
		setTitle("Frame Generator Progress");
		setPreferredSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
		setLayout(new FlowLayout(FlowLayout.CENTER, HGAP, VGAP));
		initComponents();
		pack();
	}

	private void initComponents() {
		progressLabelFinal = new JLabel();
		add(progressLabelFinal);
		
		progressLabel = new JLabel();
		add(progressLabel);
		
		progressBar = new JProgressBar();
		add(progressBar);
	}
	
	public void updateFinalProgressLabel(String update) {
		this.progressLabelFinal.setText(update);
	}

	public void updateProgressLabel(List<String> messages) {
		if (CollectionUtils.isNotEmpty(messages)) {
			for (String message : messages) {
				this.progressLabel.setText(message);
			}
		}
	}
	
	public void initProgressIndicators(Integer max) {
		progressLabelFinal.setText("Generating frames...");
		progressLabel.setText("Status");
		progressBar.setMinimum(0);
		progressBar.setValue(0);
		progressBar.setMaximum(max);
		if (!progressBar.isStringPainted()) {
			progressBar.setStringPainted(true);
		}
	}
	
	public void updateProgressBar(Integer increment) {
		int value = progressBar.getValue();
		if (value+increment <= progressBar.getMaximum()) {
			progressBar.setValue(value+increment);
		}
	}

}

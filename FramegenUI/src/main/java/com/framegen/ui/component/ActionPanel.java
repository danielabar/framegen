package com.framegen.ui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.framegen.ui.mvp.IFramegenPresenter;

public class ActionPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -4137327613453542697L;
	
	
	private IFramegenPresenter framegenPresenter;
	private JButton generateFramesButton;

	public ActionPanel() {
		super();
		setLayout(new MigLayout());
		initComponents();
		layoutComponents();
	}

	private void initComponents() {
		generateFramesButton = new JButton("Generate Frames");
		generateFramesButton.addActionListener(this);
	}

	private void layoutComponents() {
		add(generateFramesButton, "align right");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.framegenPresenter.generateFrames(); 
	}

	public IFramegenPresenter getFramegenPresenter() {
		return framegenPresenter;
	}

	public void setFramegenPresenter(IFramegenPresenter framegenPresenter) {
		this.framegenPresenter = framegenPresenter;
	}
	
}

package com.framegen.ui.mvp;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

import com.ezware.dialog.task.TaskDialogs;
import com.framegen.api.settings.FrameOption;
import com.framegen.ui.component.ActionPanel;
import com.framegen.ui.component.OptionSettingsPanel;
import com.framegen.ui.component.ProgramSettingsPanel;
import com.framegen.ui.dialog.FramegenProgressDialog;

/*
 * http://stackoverflow.com/questions/11367250/concrete-code-example-of-mvp
 * 
 * NICE TO HAVE: i18n
 * 	http://www.java2s.com/Tutorial/Java/0220__I18N/AnInternationalizedSwingApplication.htm
 */
public class FramegenViewImpl extends JFrame implements IFramegenView {

	private static final String ERROR_HEADER = "Frame Generation Error";

	private static final long serialVersionUID = -675088303411262766L;

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 600;
	
	private IFramegenPresenter frameGenPresenter;
	
	private ProgramSettingsPanel programSettingsPanel;
	private OptionSettingsPanel optionSettingsPanel;
	private ActionPanel actionPanel;

	private FramegenProgressDialog progressDialog;
	
	public FramegenViewImpl() {
		setLookAndFeel();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setTitle("Frame Generator");
//		setIconImage(image);	//TODO: Need custom icon image
		getContentPane().setLayout(new MigLayout());
		initComponents();
		layoutComponents();
		pack();
	}
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}

	private void initComponents() {
		programSettingsPanel = new ProgramSettingsPanel();
		optionSettingsPanel = new OptionSettingsPanel(this);
		actionPanel = new ActionPanel();
		progressDialog = new FramegenProgressDialog(this);
	}
	
	private void layoutComponents() {
		add(programSettingsPanel, "wrap");
		add(optionSettingsPanel, "wrap");
		add(actionPanel, "align right");
	}
	

	@Override
	public IFramegenPresenter getPresenter() {
		return this.frameGenPresenter;
	}

	@Override
	public void setPresenter(IFramegenPresenter framegenPresenter) {
		this.frameGenPresenter = framegenPresenter;
		this.actionPanel.setFramegenPresenter(framegenPresenter);
	}

	@Override
	public void updateModelFromView() {
		getPresenter().getModel().setBaseImage(this.programSettingsPanel.getBaseImage());
		getPresenter().getModel().setOverlayImage(this.programSettingsPanel.getOverlayImage());
		getPresenter().getModel().setOutputDir(this.programSettingsPanel.getOutputDir());
		getPresenter().getModel().setGeneratedImageNamePrefix(this.programSettingsPanel.getGenImgPrefix());
		getPresenter().getModel().setOptionSettingsVO(this.optionSettingsPanel.getOptionSettingsVO());
		
		// defaults not really part of model, so this is more updatePresenterFromView
		getPresenter().setFileOpenDefaultsVO(this.programSettingsPanel.getFileOpenDefaults());
	}

	@Override
	public void updateViewFromModel() {
		this.programSettingsPanel.setBaseImage(getPresenter().getModel().getBaseImage());
		this.programSettingsPanel.setOverlayImage(getPresenter().getModel().getOverlayImage());
		this.programSettingsPanel.setOutputDir(getPresenter().getModel().getOutputDir());
		this.programSettingsPanel.setGenImgPrefix(getPresenter().getModel().getGeneratedImageNamePrefix());
		this.optionSettingsPanel.setOptionSettingsVO(getPresenter().getModel().getOptionSettingsVO());
		
		// file open defaults are not really part of the model, so this is really updateViewFromPresenter
		this.programSettingsPanel.setFileOpenDefaults(getPresenter().getFileOpenDefaults());
	}

	@Override
	public void open() {
		setVisible(true);
	}

	@Override
	public void close() {
		dispose();
	}

	@Override
	public void enableDisableOverlaySelection(FrameOption frameOption) {
		if (frameOption.isRequiresOverlay()) { 
			programSettingsPanel.enableOverlaySelection();
		} else {
			programSettingsPanel.disableOverlaySelection();
		}
	}

	/*
	 * http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html(non-Javadoc)
	 *
	 */
	@Override
	public void showError(String errorMessage) {
		progressDialog.setVisible(false);
		JOptionPane.showMessageDialog(this, errorMessage, ERROR_HEADER, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void showException(Exception e) {
		progressDialog.setVisible(false);
		TaskDialogs.showException(e);
	}

	@Override
	public void showProgressDialog() {
		progressDialog.setVisible(true);
	}

	@Override
	public void updateFinalProgressLabel(String updateText) {
		progressDialog.updateFinalProgressLabel(updateText);
	}

	@Override
	public void updateProgressLabel(List<String> messages) {
		progressDialog.updateProgressLabel(messages);
	}

	@Override
	public void initProgressIndicators(Integer max) {
		progressDialog.initProgressIndicators(max);
	}

	@Override
	public void updateProgressBar(Integer increment) {
		progressDialog.updateProgressBar(increment);
	}
	
}

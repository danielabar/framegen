package com.framegen.ui.component;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

import com.framegen.ui.main.FileOpenDefaultsVO;

public class ProgramSettingsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6701593807814611727L;

	private static final int PANEL_WIDTH = 500;
	private static final int PANEL_HEIGHT = 140;
	private static final int TEXT_FIELD_WIDTH = 35;
	
	private FileOpenDefaultsVO fileOpenDefaults;
	
	private JLabel baseImageLabel;
	private JTextField baseImageTextField;
	private JButton baseImageChooseButton;
	private JFileChooser baseImageFileChooser;
	private File baseImage;

	private JLabel overlayImageLabel;
	private JTextField overlayImageTextField;
	private JButton overlayImageChooseButton;
	private JFileChooser overlayImageFileChooser;
	private File overlayImage;
	
	private JLabel outputDirLabel;
	private JTextField outputDirTextField;
	private JButton outputDirChooseButton;
	private JFileChooser outputDirFileChooser;	
	private File outputDir;
	
	private JLabel genImgPrefixLabel;
	private JTextField genImgPrefixTextField;

	public ProgramSettingsPanel() {
		super();
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setLayout(new MigLayout());
		initComponents();
		layoutComponents();
	}

	private void initComponents() {
		initBaseImage();
		initOverlayImage();
		initOutputDir();
		initGenImgPrefix();
	}

	private void initBaseImage() {
		baseImageLabel = new JLabel("Base Image");
		baseImageTextField = new JTextField();
		baseImageTextField.setColumns(TEXT_FIELD_WIDTH);
		baseImageTextField.setEditable(false);
		baseImageFileChooser = new JFileChooser();
		baseImageChooseButton = new JButton(createImageIcon("open.gif"));
		baseImageChooseButton.addActionListener(this);
		baseImageChooseButton.requestFocusInWindow();
	}

	private void initOverlayImage() {
		overlayImageLabel = new JLabel("Overlay Image");
		overlayImageTextField = new JTextField();
		overlayImageTextField.setColumns(TEXT_FIELD_WIDTH);
		overlayImageTextField.setEditable(false);
		overlayImageFileChooser = new JFileChooser();
		overlayImageChooseButton = new JButton(createImageIcon("open.gif"));
		overlayImageChooseButton.addActionListener(this);
	}

	private void initOutputDir() {
		outputDirLabel = new JLabel("Output Directory");
		outputDirTextField = new JTextField();
		outputDirTextField.setEditable(false);
		outputDirTextField.setColumns(TEXT_FIELD_WIDTH);
		outputDirFileChooser = new JFileChooser();
		outputDirFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		outputDirChooseButton = new JButton(createImageIcon("open.gif"));
		outputDirChooseButton.addActionListener(this);
	}

	private void initGenImgPrefix() {
		genImgPrefixLabel = new JLabel("Output Image Prefix");
		genImgPrefixTextField = new JTextField();
		genImgPrefixTextField.setColumns(TEXT_FIELD_WIDTH);
	}
	
	private void layoutComponents() {
		add(baseImageLabel);
		add(baseImageTextField, "gap unrelated");
		add(baseImageChooseButton, "wrap");
		
		add(overlayImageLabel);
		add(overlayImageTextField, "gap unrelated");
		add(overlayImageChooseButton, "wrap");

		add(outputDirLabel);
		add(outputDirTextField, "gap unrelated");
		add(outputDirChooseButton, "wrap");

		add(genImgPrefixLabel);
		add(genImgPrefixTextField, "gap unrelated");
	}
	
	private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = this.getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		handleBaseImageSelection(e);
		handleOverlayImageSelection(e);
		handleOutputDirSelection(e);
	}

	private void handleOutputDirSelection(ActionEvent e) {
		if (e.getSource() == outputDirChooseButton) {
			initDefaultDirOutput();
			int returnVal = outputDirFileChooser.showOpenDialog(this);
			if (JFileChooser.APPROVE_OPTION == returnVal) {
				setOutputDir(outputDirFileChooser.getSelectedFile());
				this.outputDirTextField.setText(getOutputDir().getAbsolutePath());
			}
		}
	}

	private void handleOverlayImageSelection(ActionEvent e) {
		if (e.getSource() == overlayImageChooseButton) {
			initDefaultDirOverlay();
			int returnVal = overlayImageFileChooser.showOpenDialog(this);
			if (JFileChooser.APPROVE_OPTION == returnVal) {
				setOverlayImage(overlayImageFileChooser.getSelectedFile());
				this.overlayImageTextField.setText(getOverlayImage().getAbsolutePath());
			}
		}
	}

	private void handleBaseImageSelection(ActionEvent e) {
		if (e.getSource() == baseImageChooseButton) {
			initDefaultDirBase();
			int returnVal = baseImageFileChooser.showOpenDialog(this);
			if (JFileChooser.APPROVE_OPTION == returnVal) {
				setBaseImage(baseImageFileChooser.getSelectedFile());
				this.baseImageTextField.setText(getBaseImage().getAbsolutePath());
			}
		}
	}

	private void initDefaultDirBase() {
		if (fileOpenDefaults != null && fileOpenDefaults.getBaseImageOpenDir() != null) {
			baseImageFileChooser.setCurrentDirectory(fileOpenDefaults.getBaseImageOpenDir());
		}
	}
	
	private void initDefaultDirOverlay() {
		if (fileOpenDefaults != null && fileOpenDefaults.getOverlayImageOpenDir() != null) {
			overlayImageFileChooser.setCurrentDirectory(fileOpenDefaults.getOverlayImageOpenDir());
		}
	}
	
	private void initDefaultDirOutput() {
		if (fileOpenDefaults != null && fileOpenDefaults.getOutputOpenDir() != null) {
			outputDirFileChooser.setCurrentDirectory(fileOpenDefaults.getOutputOpenDir());
		}
	}

	public File getBaseImage() {
		return baseImage;
	}

	public void setBaseImage(File baseImage) {
		this.baseImage = baseImage;
	}

	public File getOverlayImage() {
		return overlayImage;
	}

	public void setOverlayImage(File overlayImage) {
		this.overlayImage = overlayImage;
	}

	public File getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}

	public String getGenImgPrefix() {
		return this.genImgPrefixTextField.getText();
	}

	public void setGenImgPrefix(String genImgPrefix) {
		this.genImgPrefixTextField.setText(genImgPrefix);
	}

	public FileOpenDefaultsVO getFileOpenDefaults() {
		return fileOpenDefaults;
	}

	public void setFileOpenDefaults(FileOpenDefaultsVO fileOpenDefaults) {
		this.fileOpenDefaults = fileOpenDefaults;
	}
	
	public void disableOverlaySelection() {
		this.overlayImageChooseButton.setEnabled(false);
	}
	
	public void enableOverlaySelection() {
		this.overlayImageChooseButton.setEnabled(true);
	}

}


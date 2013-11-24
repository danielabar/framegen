package com.framegen.cmdline.main;

import com.beust.jcommander.JCommander;
import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.SettingsVO;
import com.framegen.cmdline.apibridge.*;
import com.framegen.cmdline.settings.*;

public class ProgramSettingsParser {

	private ProgramSettings programSettings;
	private LineSettings lineSettings;
	private RotationSettings rotationSettings;
	private ArcSettings arcSettings;
	private ZoomSettings zoomSettings;
	private NegativeSettings negativeSettings;
	private FadeSettings fadeSettings;
	private TranspSettings transpSettings;
	
	public ProgramSettingsParser() {
		super();
		programSettings = new ProgramSettings();
		lineSettings = new LineSettings();
		rotationSettings = new RotationSettings();
		arcSettings = new ArcSettings();
		zoomSettings = new ZoomSettings();
		negativeSettings = new NegativeSettings();
		fadeSettings = new FadeSettings();
		transpSettings = new TranspSettings();
	}

	public ProgramSettings parse(String[] args) {
		ProgramSettings programSettings = new ProgramSettings();
		JCommander jCommander = new JCommander(programSettings, args);
		
		if (programSettings.isHelp()) {
			jCommander.setProgramName("Frame Generator");
			jCommander.usage();
		}
		
		return programSettings;
	}
	
	public SettingsVO parseAll(String[] args) {
		JCommander jCommander = new JCommander(programSettings);
		addCommands(jCommander);
		jCommander.parse(args);
		
		handleHelpOption(jCommander);
		
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(new ProgramSettingsTransformer().apply(programSettings));
		populateOptionSettings(jCommander, vo);
		
		return vo;
	}

	private void handleHelpOption(JCommander jCommander) {
		if (programSettings.isHelp()) {
			jCommander.setProgramName("Frame Generator");
			jCommander.usage();
		}
	}

	private void populateOptionSettings(JCommander jCommander, SettingsVO vo) {
		
		if (FrameOption.LINE.toString().equals(jCommander.getParsedCommand())) {
			vo.setLineSettings(new LineSettingsTransformer().apply(lineSettings));
		}
		
		if (FrameOption.ROTATE.toString().equals(jCommander.getParsedCommand())) {
			vo.setRotationSettings(new RotationSettingsTransformer().apply(rotationSettings));
		}
		
		if (FrameOption.ARC.toString().equals(jCommander.getParsedCommand())) {
			vo.setArcSettings(new ArcSettingsTransformer().apply(arcSettings));
		}
		
		if (FrameOption.ZOOM.toString().equals(jCommander.getParsedCommand())) {
			vo.setZoomSettings(new ZoomSettingsTransformer().apply(zoomSettings));
		}
		
		if (FrameOption.NEGATIVE.toString().equals(jCommander.getParsedCommand())) {
			vo.setNegativeSettings(new NegativeSettingsTransformer().apply(negativeSettings));
		}
		
		if (FrameOption.FADE.toString().equals(jCommander.getParsedCommand())) {
			vo.setFadeSettings(new FadeSettingsTransformer().apply(fadeSettings));
		}
		
		if (FrameOption.TRANSP.toString().equals(jCommander.getParsedCommand())) {
			vo.setTranspSettings(new TranspSettingsTransformer().apply(transpSettings));
		}
	}

	private void addCommands(JCommander jCommander) {
		jCommander.addCommand(FrameOption.LINE.toString(), lineSettings);
		jCommander.addCommand(FrameOption.ROTATE.toString(), rotationSettings);
		jCommander.addCommand(FrameOption.ARC.toString(), arcSettings);
		jCommander.addCommand(FrameOption.ZOOM.toString(), zoomSettings);
		jCommander.addCommand(FrameOption.NEGATIVE.toString(), negativeSettings);
		jCommander.addCommand(FrameOption.FADE.toString(), fadeSettings);
		jCommander.addCommand(FrameOption.TRANSP.toString(), transpSettings);
	}

}

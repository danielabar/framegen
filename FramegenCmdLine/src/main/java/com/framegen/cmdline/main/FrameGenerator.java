package com.framegen.cmdline.main;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.beust.jcommander.ParameterException;
import com.framegen.api.response.FrameHandlerVO;
import com.framegen.api.service.IFrameHandler;
import com.framegen.api.service.IFrameHandlerFactory;
import com.framegen.api.settings.SettingsVO;
import com.framegen.core.framehandler.FrameHandlerFactoryImpl;

public class FrameGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(FrameGenerator.class);
	
	private static final ProgramSettingsParser settingsParser = new ProgramSettingsParser();
	private final IFrameHandlerFactory frameHandlerFactory;
	
	public FrameGenerator() {
		super();
		this.frameHandlerFactory = new FrameHandlerFactoryImpl();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		try {
			SettingsVO settings = settingsParser.parseAll(args);
			if (!settings.getProgramSettings().isHelp()) {
				LOGGER.info("Program Settings: " + settings.toString());
				new FrameGenerator().execute(settings);
				System.exit(0);
			}
		} catch (ParameterException pe) {
			System.err.println(pe.getMessage());
			System.exit(1);
		}
	}

	protected FrameHandlerVO execute(SettingsVO settings) throws IOException, InterruptedException {
		IFrameHandler frameHandler = frameHandlerFactory.getFrameHandler(settings);
		FrameHandlerVO frameHandlerVO = frameHandler.generateFrames(settings);
		return frameHandlerVO;
	}

	public String getStatus(SettingsVO settingsVO, FrameHandlerVO frameHandlerVO) {
		IFrameHandler frameHandler = frameHandlerFactory.getFrameHandler(settingsVO);
		return frameHandler.getStatus(frameHandlerVO);
	}

}

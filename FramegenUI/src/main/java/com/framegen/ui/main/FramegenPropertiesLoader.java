package com.framegen.ui.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class FramegenPropertiesLoader {
	
	private static final Logger LOGGER = Logger.getLogger(FramegenPropertiesLoader.class);

	public static final String PROPERTY_DEFAULT_BASE_DIR = "com.framegen.ui.default.dir.base";
	public static final String PROPERTY_DEFAULT_OVERLAY_DIR = "com.framegen.ui.default.dir.overlay";
	public static final String PROPERTY_DEFAULT_OUTPUT_DIR = "com.framegen.ui.default.dir.output";
	
	private final DirectoryValidator directoryValidator;
	
	public FramegenPropertiesLoader() {
		super();
		this.directoryValidator = new DirectoryValidator();
	}

	// default constructor for unit testing
	FramegenPropertiesLoader(DirectoryValidator directoryValidator) {
		super();
		this.directoryValidator = directoryValidator;
	}

	public Properties initProperties() {
		String propPath = System.getProperty("framegen.properties.file");
		Properties framegenProps = new Properties();
		if (propPath != null) {
			FileInputStream in = null;
			try {
				in = new FileInputStream(propPath);
				framegenProps.load(in);
				in.close();
				return framegenProps;
			} catch (Exception e) {
				LOGGER.error("Unable to read properties file", e);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.error("Unable to read properties file", e);
				}
			}
		}
		return null;
	}
	
	public FileOpenDefaultsVO getFileOpenDefaults(Properties props) {
		FileOpenDefaultsVO vo = new FileOpenDefaultsVO();
		if (props != null) {
			vo.setBaseImageOpenDir(getDirByPropValue(props, PROPERTY_DEFAULT_BASE_DIR));
			vo.setOverlayImageOpenDir(getDirByPropValue(props, PROPERTY_DEFAULT_OVERLAY_DIR));
			vo.setOutputOpenDir(getDirByPropValue(props, PROPERTY_DEFAULT_OUTPUT_DIR));
		}
		return vo;
	}

	private File getDirByPropValue(Properties props, String propKey) {
		String propValue = (String) props.get(propKey);
		if (StringUtils.isNotEmpty(propValue)) {
			File file = directoryValidator.isDirectory(propValue);
			return file;
		}
		return null;
	}
}

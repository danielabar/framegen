package com.framegen.cmdline.settings;

import java.io.File;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import com.framegen.cmdline.settings.validator.DirectoryExistsValidator;
import com.framegen.cmdline.settings.validator.FileExistsValidator;

public class ProgramSettings {
	
	public static final String DEFAULT_GENERATED_IMAGE_NAME_PREFIX = "combined_";

	@Parameter(names = "--help", help = true)
	private boolean help;

	@Parameter(names = "-baseImage", required = true, validateWith = FileExistsValidator.class, converter = FileConverter.class, 
			description = "Full path and file name of base image to be drawn upon")
	private File baseImage;
	
	@Parameter(names = "-overlayImage", required = true, validateWith = FileExistsValidator.class, converter = FileConverter.class, 
			description = "Full path and file name of overlay image that will be drawn on the Base Image")
	private File overlayImage;
	
	@Parameter(names = "-outputDir", required = true, validateWith = DirectoryExistsValidator.class, converter = FileConverter.class, 
			description = "Directory to save generated images")
	private File outputDir;
	
	@Parameter(names = "-genImagePrefix", description = "Generated image name prefix")
	private String generatedImageNamePrefix = DEFAULT_GENERATED_IMAGE_NAME_PREFIX;

	@Parameter(names = "-genPointsCsv", description = "Generate points.csv of x/y co-ordinates")
	private boolean genPointsCsv = false;

	public boolean isHelp() {
		return help;
	}
	
	public File getBaseImage() {
		return baseImage;
	}

	public File getOverlayImage() {
		return overlayImage;
	}

	public File getOutputDir() {
		return outputDir;
	}

	public String getGeneratedImageNamePrefix() {
		return generatedImageNamePrefix;
	}

	public boolean isGenPointsCsv() {
		return genPointsCsv;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}

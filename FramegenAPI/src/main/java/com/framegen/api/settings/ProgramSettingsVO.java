package com.framegen.api.settings;

import java.io.File;

public class ProgramSettingsVO {
	
	private boolean help;
	private File baseImage;
	private File overlayImage;
	private File outputDir;
	private String generatedImageNamePrefix;
	private boolean genPointsCsv;
	
	public boolean isHelp() {
		return help;
	}
	public void setHelp(boolean help) {
		this.help = help;
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
	public String getGeneratedImageNamePrefix() {
		return generatedImageNamePrefix;
	}
	public void setGeneratedImageNamePrefix(String generatedImageNamePrefix) {
		this.generatedImageNamePrefix = generatedImageNamePrefix;
	}
	public boolean isGenPointsCsv() {
		return genPointsCsv;
	}
	public void setGenPointsCsv(boolean genPointsCsv) {
		this.genPointsCsv = genPointsCsv;
	}

}

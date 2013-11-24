package com.framegen.core.taskvo;

import java.awt.image.BufferedImage;
import java.io.File;

public class OffsetFilterTaskVO {

	private BufferedImage image;
	private File outputDir;
	
	private Float multiplier;
	private Float offset;
	
	private String combinedFileNamePrefix;
	private Integer sequence;
	private Integer numberOfPadChars;
	private Integer totalNumberOfImages;
	
	public OffsetFilterTaskVO(BufferedImage image, File outputDir,
			Float multiplier, Float offset, String combinedFileNamePrefix,
			Integer sequence, Integer numberOfPadChars,
			Integer totalNumberOfImages) {
		super();
		this.image = image;
		this.outputDir = outputDir;
		this.multiplier = multiplier;
		this.offset = offset;
		this.combinedFileNamePrefix = combinedFileNamePrefix;
		this.sequence = sequence;
		this.numberOfPadChars = numberOfPadChars;
		this.totalNumberOfImages = totalNumberOfImages;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public File getOutputDir() {
		return outputDir;
	}
	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}
	public Float getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(Float multiplier) {
		this.multiplier = multiplier;
	}
	public Float getOffset() {
		return offset;
	}
	public void setOffset(Float offset) {
		this.offset = offset;
	}
	public String getCombinedFileNamePrefix() {
		return combinedFileNamePrefix;
	}
	public void setCombinedFileNamePrefix(String combinedFileNamePrefix) {
		this.combinedFileNamePrefix = combinedFileNamePrefix;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getNumberOfPadChars() {
		return numberOfPadChars;
	}
	public void setNumberOfPadChars(Integer numberOfPadChars) {
		this.numberOfPadChars = numberOfPadChars;
	}
	public Integer getTotalNumberOfImages() {
		return totalNumberOfImages;
	}
	public void setTotalNumberOfImages(Integer totalNumberOfImages) {
		this.totalNumberOfImages = totalNumberOfImages;
	}
	
}

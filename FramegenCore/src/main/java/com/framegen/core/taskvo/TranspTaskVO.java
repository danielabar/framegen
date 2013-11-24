package com.framegen.core.taskvo;

import java.awt.image.BufferedImage;
import java.io.File;

public class TranspTaskVO {

	private BufferedImage image;
	private BufferedImage overlay;
	private File outputDir;
	
	private Double xPos;
	private Double yPos;
	
	private Integer width;
	private Integer height;
	
	private String combinedFileNamePrefix;
	private Integer sequence;
	private Integer numberOfPadChars;
	private Integer totalNumberOfImages;
	
	float alpha;
	
	public TranspTaskVO(BufferedImage image, BufferedImage overlay, File outputDir, 
			Double xPos, Double yPos, Integer width, Integer height, 
			String combinedFileNamePrefix, Integer sequence, Integer numberOfPadChars,
			Integer totalNumberOfImages, float alpha) {
		super();
		this.image = image;
		this.overlay = overlay;
		this.outputDir = outputDir;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.combinedFileNamePrefix = combinedFileNamePrefix;
		this.sequence = sequence;
		this.numberOfPadChars = numberOfPadChars;
		this.totalNumberOfImages = totalNumberOfImages;
		this.alpha = alpha;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getOverlay() {
		return overlay;
	}

	public void setOverlay(BufferedImage overlay) {
		this.overlay = overlay;
	}

	public File getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}

	public Double getXPos() {
		return xPos;
	}

	public void setXPos(Double xPos) {
		this.xPos = xPos;
	}

	public Double getYPos() {
		return yPos;
	}

	public void setYPos(Double yPos) {
		this.yPos = yPos;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
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

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
}

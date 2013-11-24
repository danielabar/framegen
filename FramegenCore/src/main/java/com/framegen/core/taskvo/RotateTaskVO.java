package com.framegen.core.taskvo;

import java.awt.image.BufferedImage;
import java.io.File;

public class RotateTaskVO {
	
	private BufferedImage image;
	private BufferedImage overlay;
	private File outputDir;
	
	private Integer xCoordinate;
	private Integer yCoordinate;
	
	private Integer rotationDegrees;
	
	private String combinedFileNamePrefix;
	private Integer sequence;
	private Integer numberOfPadChars;
	private Integer totalNumberOfImages;
	
	private ScaleVO scaleRequest;
	
	// FIXME numberOfPadChars should be derived/calculated by caller of this, based on how many images will be required
	public RotateTaskVO(BufferedImage image, BufferedImage overlay, File outputDir,  
			Integer xCoordinate, Integer yCoordinate, Integer rotationDegrees, 
			String combinedFileNamePrefix, Integer sequence, Integer numberOfPadChars, Integer totalNumberOfImages) {
		super();
		this.image = image;
		this.overlay = overlay;
		this.outputDir = outputDir;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.rotationDegrees = rotationDegrees;
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
	public Integer getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(Integer xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public Integer getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(Integer yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public Integer getRotationDegress() {
		return rotationDegrees;
	}
	public void setRotationDegrees(Integer width) {
		this.rotationDegrees = width;
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
	
	public ScaleVO getScaleRequest() {
		return scaleRequest;
	}
	public void setScaleRequest(ScaleVO scaleRequest) {
		this.scaleRequest = scaleRequest;
	}
	
	public boolean hasScaleRequest() {
		return this.scaleRequest != null;
	}
	
	@Override
	public String toString() {
		String displayFormat = "xCoordinate: %d, yCoordinate: %d, sequence: %d";
		return String.format(displayFormat, this.getxCoordinate().intValue(), this.getyCoordinate().intValue(), this.getSequence());
	}
	
}

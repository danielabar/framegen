package com.framegen.core.taskvo;

import java.awt.image.BufferedImage;
import java.io.File;

public class LineTaskVO {
	
	private BufferedImage image;
	private BufferedImage overlay;
	private File outputDir;
	
	private Double xCoordinate;
	private Double yCoordinate;
	
	private Integer width;
	private Integer height;
	
	private String combinedFileNamePrefix;
	private Integer sequence;
	private Integer numberOfPadChars;
	private Integer totalNumberOfImages;
	
	private ScaleVO scaleRequest;
	private Integer rotateBy;
	
	public LineTaskVO(BufferedImage image, BufferedImage overlay, File outputDir,  
			Double xCoordinate, Double yCoordinate, Integer width, Integer height, 
			String combinedFileNamePrefix, Integer sequence, Integer numberOfPadChars, Integer totalNumberOfImages) {
		super();
		this.image = image;
		this.overlay = overlay;
		this.outputDir = outputDir;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.width = width;
		this.height = height;
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
	public Double getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(Double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public Double getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(Double yCoordinate) {
		this.yCoordinate = yCoordinate;
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
	
	public ScaleVO getScaleRequest() {
		return scaleRequest;
	}

	public void setScaleRequest(ScaleVO scaleRequest) {
		this.scaleRequest = scaleRequest;
	}
	
	public boolean hasScaleRequest() {
		return this.scaleRequest != null;
	}

	public Integer getRotateBy() {
		return rotateBy;
	}
	public void setRotateBy(Integer rotateBy) {
		this.rotateBy = rotateBy;
	}
	
	public boolean hasRotationRequest() {
		return this.rotateBy != null;
	}
	
	@Override
	public String toString() {
		String displayFormat = "xCoordinate: %d, yCoordinate: %d, sequence: %d";
		return String.format(displayFormat, this.getxCoordinate().intValue(), this.getyCoordinate().intValue(), this.getSequence());
	}
	
}

package com.framegen.core.taskvo;

import java.awt.image.BufferedImage;
import java.io.File;

public class ZoomTaskVO {
	
	private BufferedImage image;
	private File outputDir;
	
	private Double xZoom;
	private Double yZoom;
	private Double zoomFactor;
	
	private String combinedFileNamePrefix;
	private Integer sequence;
	private Integer numberOfPadChars;
	private Integer totalNumberOfImages;
	
	public ZoomTaskVO(BufferedImage image, File outputDir, Double xZoom,
			Double yZoom, Double zoomFactor, String combinedFileNamePrefix,
			Integer sequence, Integer numberOfPadChars,
			Integer totalNumberOfImages) {
		super();
		this.image = image;
		this.outputDir = outputDir;
		this.xZoom = xZoom;
		this.yZoom = yZoom;
		this.zoomFactor = zoomFactor;
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
	public Double getxZoom() {
		return xZoom;
	}
	public void setxZoom(Double xZoom) {
		this.xZoom = xZoom;
	}
	public Double getyZoom() {
		return yZoom;
	}
	public void setyZoom(Double yZoom) {
		this.yZoom = yZoom;
	}
	public Double getZoomFactor() {
		return zoomFactor;
	}
	public void setZoomFactor(Double zoomFactor) {
		this.zoomFactor = zoomFactor;
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

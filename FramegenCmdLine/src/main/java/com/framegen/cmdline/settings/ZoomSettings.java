package com.framegen.cmdline.settings;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;

@Parameters(separators = "=", commandDescription = "Settings for ZOOM frame generation (overlay is ignored)")
public class ZoomSettings {
	
	private static final Integer DEFAULT_NUMBER_OF_ZOOMS = 10;
	private static final Double DEFAULT_ZOOM_FACTOR = Double.valueOf("1.1");
	private static final Double DEFAULT_ZOOM_INCREMENT = Double.valueOf("0.1");

	@Parameter(names = "--xZoom", description = "x position on which zoom will focus, default is base image x center")
	private Double xZoom;
	
	@Parameter(names = "--yZoom", description = "y position on which zoom will focus, default is base image y center")
	private Double yZoom;
	
	@Parameter(names = "--numberOfZooms", validateWith = PositiveInteger.class, description = "number of times to zoom")
	private Integer numberOfZooms = DEFAULT_NUMBER_OF_ZOOMS;
	
	@Parameter(names = "--zoomFactor", description = "starting zoom factor")
	private Double zoomFactor = DEFAULT_ZOOM_FACTOR;
	
	@Parameter(names = "--zoomIncrement", description = "amount to increment zoom on each iteration")
	private Double zoomIncrement = DEFAULT_ZOOM_INCREMENT;

	public Double getxZoom() {
		return xZoom;
	}

	public Double getyZoom() {
		return yZoom;
	}

	public Integer getNumberOfZooms() {
		return numberOfZooms;
	}

	public Double getZoomFactor() {
		return zoomFactor;
	}

	public Double getZoomIncremenet() {
		return zoomIncrement;
	}
	
	public boolean userSpecifiedZoomCenter() {
		return this.xZoom != null && this.yZoom != null;
	}
	
}

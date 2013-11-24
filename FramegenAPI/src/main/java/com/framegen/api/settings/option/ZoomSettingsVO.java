package com.framegen.api.settings.option;

public class ZoomSettingsVO implements IOptionSettingsVO {
	
	private Double xZoom;
	private Double yZoom;
	private Integer numberOfZooms;
	private Double zoomFactor;
	private Double zoomIncrement;

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

	public Integer getNumberOfZooms() {
		return numberOfZooms;
	}

	public void setNumberOfZooms(Integer numberOfZooms) {
		this.numberOfZooms = numberOfZooms;
	}

	public Double getZoomFactor() {
		return zoomFactor;
	}

	public void setZoomFactor(Double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public Double getZoomIncrement() {
		return zoomIncrement;
	}

	public void setZoomIncrement(Double zoomIncrement) {
		this.zoomIncrement = zoomIncrement;
	}

	public boolean userSpecifiedZoomCenter() {
		return this.xZoom != null && this.yZoom != null;
	}
	
}

package com.framegen.api.settings.option;


public class ArcSettingsVO extends LineSettingsVO implements IOptionSettingsVO {

	private Double radius;
	private boolean shortestRoute;
	private boolean side;
	private boolean reverseSequence;
	
	public Double getRadius() {
		return radius;
	}
	public void setRadius(Double radius) {
		this.radius = radius;
	}
	public boolean isShortestRoute() {
		return shortestRoute;
	}
	public void setShortestRoute(boolean shortestRoute) {
		this.shortestRoute = shortestRoute;
	}
	public boolean isSide() {
		return side;
	}
	public void setSide(boolean side) {
		this.side = side;
	}
	public boolean isReverseSequence() {
		return reverseSequence;
	}
	public void setReverseSequence(boolean reverseSequence) {
		this.reverseSequence = reverseSequence;
	}
	
}

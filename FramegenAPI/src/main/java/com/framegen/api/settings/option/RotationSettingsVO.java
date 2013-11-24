package com.framegen.api.settings.option;

public class RotationSettingsVO implements IOptionSettingsVO {
	
	private Integer xPosition;
	private Integer yPosition;
	private Integer degrees;
	private Integer step;
	private Integer xScale;
	private Integer yScale;

	public Integer getxPosition() {
		return xPosition;
	}

	public void setxPosition(Integer xPosition) {
		this.xPosition = xPosition;
	}

	public Integer getyPosition() {
		return yPosition;
	}

	public void setyPosition(Integer yPosition) {
		this.yPosition = yPosition;
	}

	public Integer getDegrees() {
		return degrees;
	}

	public void setDegrees(Integer degrees) {
		this.degrees = degrees;
	}


	public Integer getStep() {
		return step;
	}


	public void setStep(Integer step) {
		this.step = step;
	}


	public Integer getxScale() {
		return xScale;
	}


	public void setxScale(Integer xScale) {
		this.xScale = xScale;
	}

	public Integer getyScale() {
		return yScale;
	}

	public void setyScale(Integer yScale) {
		this.yScale = yScale;
	}

	public boolean scalingRequested() {
		return this.xScale != null && this.xScale > 0 && 
				this.yScale != null && this.yScale > 0;
	}

}

package com.framegen.api.settings.option;

public class TranspSettingsVO implements IOptionSettingsVO {
	
	public enum TranspDirection { FADE_OUT, FADE_IN };

	private Double xPos;
	private Double yPos;
	private Integer steps;
	private TranspDirection direction;
	
	public Double getxPos() {
		return xPos;
	}
	public void setxPos(Double xPos) {
		this.xPos = xPos;
	}
	public Double getyPos() {
		return yPos;
	}
	public void setyPos(Double yPos) {
		this.yPos = yPos;
	}
	public Integer getSteps() {
		return steps;
	}
	public void setSteps(Integer steps) {
		this.steps = steps;
	}
	public TranspDirection getDirection() {
		return direction;
	}
	public void setDirection(TranspDirection direction) {
		this.direction = direction;
	}

}

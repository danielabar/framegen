package com.framegen.api.settings.option;

public class LineSettingsVO implements IOptionSettingsVO {

	private Integer xStart;
	private Integer yStart;
	private Integer xEnd;
	private Integer yEnd;
	private Double moveIncrement;
	private Integer xScale;
	private Integer yScale;
	private Integer rotateBy;

	public Integer getxStart() {
		return xStart;
	}

	public void setxStart(Integer xStart) {
		this.xStart = xStart;
	}

	public Integer getyStart() {
		return yStart;
	}

	public void setyStart(Integer yStart) {
		this.yStart = yStart;
	}

	public Integer getxEnd() {
		return xEnd;
	}

	public void setxEnd(Integer xEnd) {
		this.xEnd = xEnd;
	}

	public Integer getyEnd() {
		return yEnd;
	}

	public void setyEnd(Integer yEnd) {
		this.yEnd = yEnd;
	}

	public Double getMoveIncrement() {
		return moveIncrement;
	}

	public void setMoveIncrement(Double moveIncrement) {
		this.moveIncrement = moveIncrement;
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

	public Integer getRotateBy() {
		return rotateBy;
	}

	public void setRotateBy(Integer rotateBy) {
		this.rotateBy = rotateBy;
	}

	public boolean scalingRequested() {
		return this.xScale != null && this.xScale > 0 
				&& this.yScale != null && this.yScale > 0;
	}
	
	public boolean rotationRequested() {
		return this.rotateBy != null && this.rotateBy > 0;
	}

}

package com.framegen.core.util.arc;

import org.apache.commons.lang3.builder.HashCodeBuilder;


public class PointF {
	
	protected static final String POINT_CSV_FORMAT = "%f,%f";
	protected static final float EPSILON = 0.001f;

	private float x; 
	private float y;

	public PointF(float x, float y) {
		this.setX(x);
		this.setY(y);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return String.format(POINT_CSV_FORMAT, this.getX(), this.getY());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).
				append(this.getX()).
				append(this.getY()).  
				toHashCode(); 
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		
		PointF castedObj = (PointF) obj;
		return (floatEquals(this.getX(), castedObj.getX()) &&
				floatEquals(this.getY(), castedObj.getY()));
	}
	
	protected boolean floatEquals(float f1, float f2) {
		return Math.abs(f1 - f2) < EPSILON;
	}
	
}
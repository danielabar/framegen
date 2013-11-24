package com.framegen.core.util.transform;

import java.awt.geom.Point2D;

import com.google.common.base.Function;

public class CoordinateTransformer implements Function<Point2D, Point2D> {

	Integer imageHeight;

	public CoordinateTransformer(Integer imageHeight) {
		super();
		this.imageHeight = imageHeight;
	}

	public Point2D apply(Point2D input) {
		Point2D output = new Point2D.Double(input.getX(), getConvertedY(input.getY(), this.imageHeight));
		return output;
	}

	private double getConvertedY(double mathematicalY, Integer imageHeight) {
		return imageHeight - mathematicalY;
	}

}

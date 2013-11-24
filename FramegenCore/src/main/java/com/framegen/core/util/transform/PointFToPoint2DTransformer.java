package com.framegen.core.util.transform;

import java.awt.geom.Point2D;

import com.framegen.core.util.arc.PointF;
import com.google.common.base.Function;

public class PointFToPoint2DTransformer implements Function<PointF, Point2D> {

	@Override
	public Point2D apply(PointF input) {
		Point2D output = new Point2D.Double(input.getX(), input.getY());
		return output;
	}

}

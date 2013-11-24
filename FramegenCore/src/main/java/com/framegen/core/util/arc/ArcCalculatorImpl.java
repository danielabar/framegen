package com.framegen.core.util.arc;

import java.util.ArrayList;
import java.util.List;

/*
 * http://stackoverflow.com/questions/6641977/how-to-create-a-curve-between-2-points-in-2d-and-get-back-points-that-makes-that
 */
public class ArcCalculatorImpl implements IArcCalculator {

	@Override
	public List<PointF> generateCurve(PointF pFrom, PointF pTo, float pRadius, float pMinDistance, boolean shortest, boolean side) {

		List<PointF> results = new ArrayList<PointF>();

		float distanceBetweenPoints = calculateDistanceBetweenPoints(pFrom, pTo);
		rejectRequestIfRadiusTooSmall(pRadius, distanceBetweenPoints);
		PointF circleMiddlePoint = calculateMiddleOfArc(pFrom, pTo, pRadius, side);
		
		float angle1 = calculateReferenceAngle(pFrom, circleMiddlePoint);
		float angle2 = calculateReferenceAngle(pTo, circleMiddlePoint);

		// Swap angles if needed
		if (angle1 > angle2) {
			float temp = angle1;
			angle1 = angle2;
			angle2 = temp;
		}
		boolean flipped = false;
		if (!shortest) {
			if (angle2 - angle1 < Math.PI) {
				float temp = angle1;
				angle1 = angle2;
				angle2 = temp;
				angle2 += Math.PI * 2.0f;
				flipped = true;
			}
		}
		
		float step = calculateStep(pRadius, pMinDistance);
		for (float f = angle1; f < angle2; f += step) {
			PointF p = calculateNextPointAlongArc(pRadius, circleMiddlePoint, f);
			results.add(p);
		}
		addLastPoint(pFrom, pTo, side, results, flipped);

		return results;
	}

	protected void addLastPoint(PointF pFrom, PointF pTo, boolean side, List<PointF> results, boolean flipped) {
		if (flipped ^ side) {
			results.add(pFrom);
		} else {
			results.add(pTo);
		}
	}

	protected PointF calculateNextPointAlongArc(float pRadius, PointF circleMiddlePoint, float f) {
		PointF p = new PointF((float) Math.cos(f) * pRadius
				+ circleMiddlePoint.getX(), (float) Math.sin(f) * pRadius
				+ circleMiddlePoint.getY());
		return p;
	}

	protected float calculateStep(float pRadius, float pMinDistance) {
		float step = pMinDistance / pRadius;
		return step;
	}

	protected float calculateReferenceAngle(PointF point, PointF circleMiddlePoint) {
		return (float) Math.atan2(point.getY() - circleMiddlePoint.getY(), point.getX() - circleMiddlePoint.getX());
	}

	protected void rejectRequestIfRadiusTooSmall(float pRadius, float distance) {
		if (pRadius * 2.0f < distance) {
			throw new IllegalArgumentException("The radius is too small! The given points wont fall on the circle.");
		}
	}

	protected PointF calculateMiddleOfArc(PointF pFrom, PointF pTo, float pRadius, boolean side) {
		float factor = calculateFactor(pFrom, pTo, pRadius);
		PointF circleMiddlePoint = new PointF(0, 0);
		if (side) {
			circleMiddlePoint.setX(0.5f * (pFrom.getX() + pTo.getX()) + factor * (pTo.getY() - pFrom.getY()));
			circleMiddlePoint.setY(0.5f * (pFrom.getY() + pTo.getY()) + factor * (pFrom.getX() - pTo.getX()));
		} else {
			circleMiddlePoint.setX(0.5f * (pFrom.getX() + pTo.getX()) - factor * (pTo.getY() - pFrom.getY()));
			circleMiddlePoint.setY(0.5f * (pFrom.getY() + pTo.getY()) - factor * (pFrom.getX() - pTo.getX()));
		}
		return circleMiddlePoint;
	}

	protected float calculateFactor(PointF pFrom, PointF pTo, float pRadius) {
		float factor = (float) Math.sqrt((pRadius * pRadius)
				/ ((pTo.getX() - pFrom.getX()) * (pTo.getX() - pFrom.getX()) + (pTo.getY() - pFrom.getY())
						* (pTo.getY() - pFrom.getY())) - 0.25f);
		return factor;
	}

	protected float calculateDistanceBetweenPoints(PointF pFrom, PointF pTo) {
		float xDiff = pTo.getX() - pFrom.getX();
		float yDiff = pTo.getY() - pFrom.getY();
		float distance = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		return distance;
	}
	
}

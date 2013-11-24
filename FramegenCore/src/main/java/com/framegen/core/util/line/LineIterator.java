package com.framegen.core.util.line;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Iterator;

/**
 * Bresenham's algorithm to find all pixels on a Line2D.
 * @author nes
 */
public class LineIterator implements Iterator<Point2D> {
	final static double DEFAULT_PRECISION = 1.0;
	final Line2D line;
	final double precision;

	final double stepX, stepY;
	final LineDirection directionX, directionY;
	final double distanceX, distanceY;

	double currentX, currentY, error;

	public LineIterator(Line2D line, double precision) {
		this.line = line;
		this.precision = precision;

		stepX = line.getX1() < line.getX2() ? precision : -1 * precision;
		stepY = line.getY1() < line.getY2() ? precision : -1 * precision;
		
		directionX = line.getX1() < line.getX2() ? LineDirection.FORWARDS : LineDirection.BACKWARDS;
		directionY = line.getY1() < line.getY2() ? LineDirection.FORWARDS : LineDirection.BACKWARDS;

		distanceX = Math.abs(line.getX2() - line.getX1());
		distanceY = Math.abs(line.getY2() - line.getY1());

		error = distanceX - distanceY;

		currentY = line.getY1();
		currentX = line.getX1();
	}

	public LineIterator(Line2D line) {
		this(line, DEFAULT_PRECISION);
	}

	/*
	 * Original hasNext method had bug (or maybe its in the step code that's the bug) where if step interval
	 * put the point past beginning (or end) of line, would keep incrementing in that direction forever, infinite loop
	 */
//	public boolean hasNext() {
//		return  Math.abs(currentX - line.getX2()) > 0.9 
//				|| (Math.abs(currentY - line.getY2()) > 0.9);
//	}
	
	public boolean hasNext() {
		return !(shouldStop());
	}
	
	private boolean shouldStop() {
		
		if (LineDirection.FORWARDS.equals(directionX) && currentX > line.getX2()) {
			return true;
		}
		
		if (LineDirection.BACKWARDS.equals(directionX) && currentX < line.getX2()) {
			return true;
		}
		
		if (LineDirection.FORWARDS.equals(directionY) && currentY > line.getY2()) {
			return true;
		}
		
		if (LineDirection.BACKWARDS.equals(directionY) && currentY < line.getY2()) {
			return true;
		}
		
		return false; 
	}

	public Point2D next() {
		Point2D ret = new Point2D.Double(currentX, currentY);

		double e2 = 2 * error;
		if (e2 > -distanceY) {
			error -= distanceY;
			currentX += stepX;
		}
		if (e2 < distanceX) {
			error += distanceX;
			currentY += stepY;
		}

		return ret;
	}

	public void remove() {
		throw new AssertionError();
	}
}

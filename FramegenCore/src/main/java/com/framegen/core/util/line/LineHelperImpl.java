package com.framegen.core.util.line;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.framegen.api.settings.option.LineSettingsVO;

public class LineHelperImpl implements ILineHelper {
	
	@Override
	public List<Point2D> getPath(LineSettingsVO lineSettings) {
		Point2D p1 = new Point2D.Double(lineSettings.getxStart(), lineSettings.getyStart());
		Point2D p2 = new Point2D.Double(lineSettings.getxEnd(), lineSettings.getyEnd());
		Line2D line2D = new Line2D.Double(p1, p2);
		return getPointsAlongLine(line2D, lineSettings.getMoveIncrement());
	}
	
	protected List<Point2D> getPointsAlongLine(Line2D line2D, Double precision) {
		List<Point2D> points = new ArrayList<Point2D>();
        Point2D current;
        for(Iterator<Point2D> iter = new LineIterator(line2D, precision); iter.hasNext();) {
            current = iter.next();
            points.add(current);
        }
        return points;
	}

}

package com.framegen.core.util.arc;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.core.util.transform.CoordinateTransformer;
import com.framegen.core.util.transform.PointFToPoint2DTransformer;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class ArcHelperImpl implements IArcHelper {
	
	private final IArcCalculator arcCalculator;
	
	public ArcHelperImpl() {
		super();
		this.arcCalculator = new ArcCalculatorImpl();
	}

	protected ArcHelperImpl(IArcCalculator arcCalculator) {
		super();
		this.arcCalculator = arcCalculator;
	}

	@Override
	public List<Point2D> getPointsAlongArc(ArcSettingsVO arcSettings, BufferedImage baseImage) {
		
		PointF pStart = getStartPoint(arcSettings, baseImage);
		PointF pEnd = getEndPoint(arcSettings, baseImage);
		
		List<PointF> mathPointFs = arcCalculator.generateCurve(
				pStart, pEnd, 
				arcSettings.getRadius().floatValue(), 
				arcSettings.getMoveIncrement().floatValue(), 
				arcSettings.isShortestRoute(), arcSettings.isSide());
		
		List<Point2D> imageCoordinates = convertFloatMathToDoubleImageCoordinates(baseImage, mathPointFs);
		if (arcSettings.isReverseSequence()) {
			return Lists.reverse(imageCoordinates);
		}
		return imageCoordinates;
	}

	protected List<Point2D> convertFloatMathToDoubleImageCoordinates(BufferedImage baseImage, List<PointF> mathPointFs) {
		ArrayList<Point2D> mathPoint2Ds = new ArrayList<Point2D>(Collections2.transform(mathPointFs, new PointFToPoint2DTransformer()));
		return new ArrayList<Point2D>(Collections2.transform(mathPoint2Ds, new CoordinateTransformer(baseImage.getHeight())));
	}

	protected PointF getStartPoint(ArcSettingsVO arcSettings, BufferedImage baseImage) {
		Point2D.Double imgStart = new Point2D.Double(arcSettings.getxStart().doubleValue(), arcSettings.getyStart().doubleValue()); 
		Point2D mathStart = new CoordinateTransformer(baseImage.getHeight()).apply(imgStart);
		return new PointF((float) mathStart.getX(), (float) mathStart.getY());
	}
	
	protected PointF getEndPoint(ArcSettingsVO arcSettings, BufferedImage baseImage) {
		Point2D.Double imgEnd = new Point2D.Double(arcSettings.getxEnd().doubleValue(), arcSettings.getyEnd().doubleValue()); 
		Point2D mathEnd = new CoordinateTransformer(baseImage.getHeight()).apply(imgEnd);
		return new PointF((float) mathEnd.getX(), (float) mathEnd.getY());
	}
	
}

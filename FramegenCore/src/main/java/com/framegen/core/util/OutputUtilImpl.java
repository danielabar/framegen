package com.framegen.core.util;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.core.taskvo.*;
import com.framegen.core.util.transform.CoordinateTransformer;
import com.google.common.collect.Collections2;

public class OutputUtilImpl implements IOutputUtil {
	
	private static final Logger LOGGER = Logger.getLogger(OutputUtilImpl.class);
	private static final String POINT_CSV_FORMAT = "%f,%f";
	
	@Override
	public String getStatusMessage(LineTaskVO vo) {
		if (vo.hasScaleRequest()) {
			String message = "Generating line image %d of %d, x = %.0f, y = %.0f, scaleWidth = %d, scaleHeight = %d";
			return String.format(message, vo.getSequence(), vo.getTotalNumberOfImages(), vo.getxCoordinate(), vo.getyCoordinate(), 
					vo.getScaleRequest().getTargetWidth(), vo.getScaleRequest().getTargetHeight()).concat(getRotationMessage(vo));
		} 
		String message = "Generating line image %d of %d, x = %.0f, y = %.0f";
		return String.format(message, vo.getSequence(), vo.getTotalNumberOfImages(), vo.getxCoordinate(), vo.getyCoordinate()).concat(getRotationMessage(vo));
	}
	
	@Override
	public String getStatusMessage(TranspTaskVO vo) {
		String message = "Generating line image %d of %d, alpha = %f";
		return String.format(message, vo.getSequence(), vo.getTotalNumberOfImages(), vo.getAlpha());
	}
	
	@Override
	public String getRotationMessage(LineTaskVO vo) {
		String message = " Rotating by %d degrees";
		if (vo.hasRotationRequest()) {
			return String.format(message, vo.getRotateBy());
		}
		return StringUtils.EMPTY;
	}
	
	@Override
	public String getStatusMessage(RotateTaskVO vo) {
		if (vo.hasScaleRequest()) {
			String message = "Generating rotation image %d of %d, x = %d, y = %d, scaleWidth = %d, scaleHeight = %d";  
			return String.format(message, vo.getSequence(), vo.getTotalNumberOfImages(), vo.getxCoordinate(), vo.getyCoordinate(), 
					vo.getScaleRequest().getTargetWidth(), vo.getScaleRequest().getTargetHeight());
		}
		String message = "Generating rotation image %d of %d, x = %d, y = %d";
		return String.format(message, vo.getSequence(), vo.getTotalNumberOfImages(), vo.getxCoordinate(), vo.getyCoordinate());
	}
	
	@Override
	public String getStatusMessage(ZoomTaskVO vo) {
		String message = "Generating zoom image %d of %d, xZoom = %f, yZoom = %f, zoomFactor = %f";
		return String.format(message, vo.getSequence(), vo.getTotalNumberOfImages(), vo.getxZoom(), vo.getyZoom(), vo.getZoomFactor());
	}
	
	@Override
	public String getStatusMessage(OffsetFilterTaskVO vo) {
		String message = "Generating offset image %d of %d, multiplier = %f, offset = %f";
		return String.format(message, vo.getSequence(), vo.getTotalNumberOfImages(), vo.getMultiplier(), vo.getOffset());
	}
	
	@Override
	public String generateCombinedFileName(int sequence, String combinedFileNamePrefix, Integer numberOfPadChars) {
		
		String strSequence = String.valueOf(sequence);
		int numberOfPadCharsRequired = numberOfPadChars - strSequence.length();
		StringBuffer sb = new StringBuffer();
		sb.append(combinedFileNamePrefix);
		for (int i=0; i<numberOfPadCharsRequired; i++) {
			sb.append("0");
		}
		sb.append(strSequence);
		sb.append(".png");
		return sb.toString();
	}
	
	@Override
	public void generatePointsCsvFile(ProgramSettingsVO settings, BufferedImage baseImage, List<Point2D> points)  {
		ArrayList<Point2D> pointsForOutput = new ArrayList<Point2D>(Collections2.transform(points, new CoordinateTransformer(baseImage.getHeight())));
		BufferedWriter bw;
		try {
			File csvFile = new File(settings.getOutputDir(), "points.csv");
			bw = new BufferedWriter(new FileWriter(csvFile, true));
			for (Point2D point2d : pointsForOutput) {
				bw.write(String.format(POINT_CSV_FORMAT, point2d.getX(), point2d.getY()));
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			LOGGER.error("Unable to generate points.csv file", e);
		}
	}

}

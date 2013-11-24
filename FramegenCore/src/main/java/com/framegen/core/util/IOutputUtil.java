package com.framegen.core.util;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.core.taskvo.*;

public interface IOutputUtil {

	public String getStatusMessage(LineTaskVO vo);

	public String getStatusMessage(TranspTaskVO vo);

	public String getRotationMessage(LineTaskVO vo);

	public String getStatusMessage(RotateTaskVO vo);

	public String getStatusMessage(ZoomTaskVO vo);

	public String getStatusMessage(OffsetFilterTaskVO vo);

	public String generateCombinedFileName(int sequence, String combinedFileNamePrefix, Integer numberOfPadChars);

	public void generatePointsCsvFile(ProgramSettingsVO settings, BufferedImage baseImage, List<Point2D> points);

}
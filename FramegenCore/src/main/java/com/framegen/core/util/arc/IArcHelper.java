package com.framegen.core.util.arc;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

import com.framegen.api.settings.option.ArcSettingsVO;

public interface IArcHelper {

	public List<Point2D> getPointsAlongArc(ArcSettingsVO arcSettings, BufferedImage baseImage);

}
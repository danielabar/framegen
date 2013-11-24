package com.framegen.core.util.line;

import java.awt.geom.Point2D;
import java.util.List;

import com.framegen.api.settings.option.LineSettingsVO;

public interface ILineHelper {

	public List<Point2D> getPath(LineSettingsVO lineSettings);

}
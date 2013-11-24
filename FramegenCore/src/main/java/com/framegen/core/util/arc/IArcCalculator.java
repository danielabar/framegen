package com.framegen.core.util.arc;

import java.util.List;

public interface IArcCalculator {

	public List<PointF> generateCurve(PointF pFrom, PointF pTo, float pRadius, float pMinDistance, boolean shortest, boolean side);

}
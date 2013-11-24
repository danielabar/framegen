package com.framegen.core.tdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.*;
import com.framegen.api.settings.option.TranspSettingsVO.TranspDirection;
import com.framegen.core.util.arc.PointF;

public interface ITestDataFactory {
	
	public SettingsVO createSettingsWithLine(ProgramSettingsVO programSettings, LineSettingsVO lineSettings);

	public SettingsVO createSettingsWithFade(ProgramSettingsVO programSettings, FadeSettingsVO fadeSettings);

	public SettingsVO createSettingsWithNegative(ProgramSettingsVO programSettings, NegativeSettingsVO negativeSettings);

	public SettingsVO createSettingsWithArc(ProgramSettingsVO programSettings, ArcSettingsVO arcSettings);

	public SettingsVO createSettingsWithRotate(ProgramSettingsVO programSettings, RotationSettingsVO rotateSettings);

	public SettingsVO createSettingsWithTransp(ProgramSettingsVO programSettings, TranspSettingsVO transpSettings);

	public SettingsVO createSettingsWithZoom(ProgramSettingsVO programSettings, ZoomSettingsVO zoomSettings);

	public ArcSettingsVO createArcSettings(Integer xStart, Integer yStart, Integer xEnd, Integer yEnd, 
			Double moveIncrement, Integer xScale, Integer yScale, Integer rotateBy, Double radius,
			boolean shortestRoute, boolean side, boolean reverseSequence);

	public LineSettingsVO createLineSettings(Integer xStart, Integer yStart, Integer xEnd, Integer yEnd, 
			Double moveIncrement, Integer xScale, Integer yScale, Integer rotateBy);

	public FadeSettingsVO createFadeSettings(Integer steps);

	public NegativeSettingsVO createNegativeSettings(Integer steps);
	
	public TranspSettingsVO createTranspSettings(Double xPos, Double yPos, Integer steps, TranspDirection direction);
	
	public RotationSettingsVO createRotateSettings(Integer xPosition, Integer yPosition, 
			Integer degrees, Integer step,
			Integer xScale, Integer yScale);
	
	public ZoomSettingsVO createZoomSettings(Double xZoom, Double yZoom, Integer numberOfZooms, Double zoomFactor, Double zoomIncrement);

	public ProgramSettingsVO createValidProgramSettingsVO() throws IOException;

	public ProgramSettingsVO createProgramSettingsVO(File baseImage,
			File overlayImage, File outputDir, String generatedImageNamePrefix);

	public File createTempFile(String prefix, String suffix) throws IOException;

	public File createTempDir();

	public File createNonExistingDir();

	public String createString(String content, int repeat);

	public File createImageFileFromResource(String prefix, String suffix, String resourceName) throws IOException, URISyntaxException;
	
	public BufferedImage createBufferedImageFromResource(String resourceName) throws URISyntaxException, IOException;
	
	public List<PointF> createPoints(int howMany);
	
}
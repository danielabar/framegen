package com.framegen.core.tdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.*;
import com.framegen.api.settings.option.TranspSettingsVO.TranspDirection;
import com.framegen.core.util.arc.PointF;
import com.google.common.io.Files;

public class TestDataFactory implements ITestDataFactory {
	
	@Override
	public SettingsVO createSettingsWithLine(ProgramSettingsVO programSettings, LineSettingsVO lineSettings) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(programSettings);
		vo.setLineSettings(lineSettings);
		return vo;
	}
	
	@Override
	public SettingsVO createSettingsWithFade(ProgramSettingsVO programSettings, FadeSettingsVO fadeSettings) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(programSettings);
		vo.setFadeSettings(fadeSettings);
		return vo;
	}
	
	@Override
	public SettingsVO createSettingsWithNegative(ProgramSettingsVO programSettings, NegativeSettingsVO negativeSettings) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(programSettings);
		vo.setNegativeSettings(negativeSettings);
		return vo;
	}
	
	@Override
	public SettingsVO createSettingsWithArc(ProgramSettingsVO programSettings, ArcSettingsVO arcSettings) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(programSettings);
		vo.setArcSettings(arcSettings);
		return vo;
	}
	
	@Override
	public SettingsVO createSettingsWithRotate(ProgramSettingsVO programSettings, RotationSettingsVO rotateSettings) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(programSettings);
		vo.setRotationSettings(rotateSettings);
		return vo;
	}

	@Override
	public SettingsVO createSettingsWithTransp(ProgramSettingsVO programSettings, TranspSettingsVO transpSettings) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(programSettings);
		vo.setTranspSettings(transpSettings);
		return vo;
	}

	@Override
	public SettingsVO createSettingsWithZoom(ProgramSettingsVO programSettings, ZoomSettingsVO zoomSettings) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(programSettings);
		vo.setZoomSettings(zoomSettings);
		return vo;
	}

	@Override
	public ArcSettingsVO createArcSettings(Integer xStart, Integer yStart, Integer xEnd, Integer yEnd,
			Double moveIncrement, Integer xScale, Integer yScale, Integer rotateBy, 
			Double radius, boolean shortestRoute, boolean side, boolean reverseSequence) {
		ArcSettingsVO vo = new ArcSettingsVO();
		vo.setxStart(xStart);
		vo.setyStart(yStart);
		vo.setxEnd(xEnd);
		vo.setyEnd(yEnd);
		vo.setMoveIncrement(moveIncrement);
		vo.setxScale(xScale);
		vo.setyScale(yScale);
		vo.setRotateBy(rotateBy);
		vo.setRadius(radius);
		vo.setShortestRoute(shortestRoute);
		vo.setSide(side);
		vo.setReverseSequence(reverseSequence);
		return vo;
	}
	
	@Override
	public LineSettingsVO createLineSettings(Integer xStart, Integer yStart, Integer xEnd, Integer yEnd,
			Double moveIncrement, Integer xScale, Integer yScale, Integer rotateBy) {
		LineSettingsVO vo = new LineSettingsVO();
		vo.setxStart(xStart);
		vo.setyStart(yStart);
		vo.setxEnd(xEnd);
		vo.setyEnd(yEnd);
		vo.setMoveIncrement(moveIncrement);
		vo.setxScale(xScale);
		vo.setyScale(yScale);
		vo.setRotateBy(rotateBy);
		return vo;
	}
	
	@Override
	public FadeSettingsVO createFadeSettings(Integer steps) {
		FadeSettingsVO vo = new FadeSettingsVO();
		vo.setSteps(steps);
		return vo;
	}
	
	@Override
	public NegativeSettingsVO createNegativeSettings(Integer steps) {
		NegativeSettingsVO vo = new NegativeSettingsVO();
		vo.setSteps(steps);
		return vo;
	}
	
	@Override
	public TranspSettingsVO createTranspSettings(Double xPos, Double yPos, Integer steps, TranspDirection direction) {
		TranspSettingsVO vo = new TranspSettingsVO();
		vo.setxPos(xPos);
		vo.setyPos(yPos);
		vo.setSteps(steps);
		vo.setDirection(direction);
		return vo;
	}

	@Override
	public RotationSettingsVO createRotateSettings(Integer xPosition, Integer yPosition, 
			Integer degrees, Integer step, 
			Integer xScale, Integer yScale) {
		RotationSettingsVO vo = new RotationSettingsVO();
		vo.setxPosition(xPosition);
		vo.setyPosition(yPosition);
		vo.setDegrees(degrees);
		vo.setStep(step);
		vo.setxScale(xScale);
		vo.setyScale(yScale);
		return vo;
	}

	@Override
	public ZoomSettingsVO createZoomSettings(Double xZoom, Double yZoom, Integer numberOfZooms, Double zoomFactor, Double zoomIncrement) {
		ZoomSettingsVO vo = new ZoomSettingsVO();
		vo.setxZoom(xZoom);
		vo.setyZoom(yZoom);
		vo.setNumberOfZooms(numberOfZooms);
		vo.setZoomFactor(zoomFactor);
		vo.setZoomIncrement(zoomIncrement);
		return vo;
	}

	@Override
	public ProgramSettingsVO createValidProgramSettingsVO() throws IOException {
		File baseImage = createTempFile("base", ".jpg");
		File overlayImage = createTempFile("overlay", ".png");
		File outputDir = createTempDir();
		String generatedImageNamePrefix = "testPrefix_";
		return createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix);
	}

	@Override
	public ProgramSettingsVO createProgramSettingsVO(File baseImage, File overlayImage, File outputDir, String generatedImageNamePrefix) {
		ProgramSettingsVO vo = new ProgramSettingsVO();
		vo.setBaseImage(baseImage);
		vo.setOverlayImage(overlayImage);
		vo.setOutputDir(outputDir);
		vo.setGeneratedImageNamePrefix(generatedImageNamePrefix);
		return vo;
	}
	
	@Override
	public File createTempFile(String prefix, String suffix) throws IOException {
		String actualSuffix = StringUtils.startsWith(suffix, ".") ? suffix : "." + suffix;
		File file = File.createTempFile(prefix, actualSuffix);
		file.deleteOnExit();
		return file;
	}
	
	@Override
	public File createTempDir() {
		File dir = Files.createTempDir();
		dir.deleteOnExit();
		return dir;
	}
	
	@Override
	public File createNonExistingDir() {
		return new File("C:\\TEST" + System.currentTimeMillis());
	}
	
	@Override
	public String createString(String content, int repeat) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<repeat; i++) {
			sb.append(content);
		}
		return sb.toString();
	}
	
	@Override
	public File createImageFileFromResource(String prefix, String suffix, String resourceName) throws IOException, URISyntaxException {
		File imageFile = createTempFile(prefix, suffix);
		File resourceFile = new File(this.getClass().getClassLoader().getResource(resourceName).toURI());
		FileUtils.copyFile(resourceFile, imageFile);
		return imageFile;
	}

	@Override
	public BufferedImage createBufferedImageFromResource(String resourceName) throws URISyntaxException, IOException {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
		try {
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			return bufferedImage;
		} finally {
			inputStream.close();
		}
	}

	@Override
	public List<PointF> createPoints(int howMany) {
		List<PointF> points = new ArrayList<PointF>();
		for (int i=0; i<howMany; i++) {
			PointF point = new PointF(i, i);
			points.add(point);
		}
		return points;
	}

}

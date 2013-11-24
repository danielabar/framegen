package com.framegen.cmdline.tdf;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.ArcSettingsVO;
import com.framegen.api.settings.option.FadeSettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.google.common.io.Files;

public class TestDataFactory {
	
	public SettingsVO createSettingsWithLine(ProgramSettingsVO programSettings, LineSettingsVO lineSettings) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(programSettings);
		vo.setLineSettings(lineSettings);
		return vo;
	}
	
	public SettingsVO createSettingsWithFade(ProgramSettingsVO programSettings, FadeSettingsVO fadeSettings) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(programSettings);
		vo.setFadeSettings(fadeSettings);
		return vo;
	}
	
	public SettingsVO createSettingsWithArc(ProgramSettingsVO programSettings, ArcSettingsVO arcSettings) {
		SettingsVO vo = new SettingsVO();
		vo.setProgramSettings(programSettings);
		vo.setArcSettings(arcSettings);
		return vo;
	}
	
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
	
	public FadeSettingsVO createFadeSettings(Integer steps) {
		FadeSettingsVO vo = new FadeSettingsVO();
		vo.setSteps(steps);
		return vo;
	}
	
	public ProgramSettingsVO createValidProgramSettingsVO() throws IOException {
		File baseImage = createTempFile("base", ".jpg");
		File overlayImage = createTempFile("overlay", ".png");
		File outputDir = createTempDir();
		String generatedImageNamePrefix = "testPrefix_";
		return createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix);
	}

	public ProgramSettingsVO createProgramSettingsVO(File baseImage, File overlayImage, File outputDir, String generatedImageNamePrefix) {
		ProgramSettingsVO vo = new ProgramSettingsVO();
		vo.setBaseImage(baseImage);
		vo.setOverlayImage(overlayImage);
		vo.setOutputDir(outputDir);
		vo.setGeneratedImageNamePrefix(generatedImageNamePrefix);
		return vo;
	}
	
	public File createTempFile(String prefix, String suffix) throws IOException {
		String actualSuffix = StringUtils.startsWith(suffix, ".") ? suffix : "." + suffix;
		File file = File.createTempFile(prefix, actualSuffix);
		file.deleteOnExit();
		return file;
	}
	
	public File createTempDir() {
		File dir = Files.createTempDir();
		dir.deleteOnExit();
		return dir;
	}
	
	public File createNonExistingDir() {
		return new File("C:\\TEST" + System.currentTimeMillis());
	}
	
	public String createString(String content, int repeat) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<repeat; i++) {
			sb.append(content);
		}
		return sb.toString();
	}
	
	public File createImageFileFromResource(String prefix, String suffix, String resourceName) throws IOException, URISyntaxException {
		File imageFile = createTempFile(prefix, suffix);
		File resourceFile = new File(this.getClass().getClassLoader().getResource(resourceName).toURI());
		FileUtils.copyFile(resourceFile, imageFile);
		return imageFile;
	}
}

package com.framegen.core;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.core.tdf.GeneratedImageFilter;
import com.framegen.core.tdf.ITestDataFactory;
import com.framegen.core.tdf.TestDataFactory;

public class FramegenCoreTestCase {

	protected static final Double DELTA = 0.01;
	
	private ITestDataFactory tdf;
	protected File tempOutputDir;
	
	@Before
	public void setUp() throws Exception {
		tdf = new TestDataFactory();
		tempOutputDir = getTdf().createTempDir();
	}
	
	@After
	public void tearDown() throws Exception {
		FileUtils.cleanDirectory(tempOutputDir);
	}

	public ITestDataFactory getTdf() {
		return this.tdf;
	}

	protected File[] getGeneratedFiles(ProgramSettingsVO programSettings) {
		File[] generatedFiles = tempOutputDir.listFiles(new GeneratedImageFilter(programSettings.getGeneratedImageNamePrefix(), ".png"));
		return generatedFiles;
	}
	
	protected File[] getGeneratedFiles(File outputDir, String genImageNamePrefix) {
		File[] generatedFiles = outputDir.listFiles(new GeneratedImageFilter(genImageNamePrefix, ".png"));
		return generatedFiles;
	}
}

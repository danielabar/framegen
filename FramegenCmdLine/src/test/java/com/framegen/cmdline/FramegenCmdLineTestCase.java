package com.framegen.cmdline;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

import com.framegen.cmdline.tdf.TestDataFactory;

public class FramegenCmdLineTestCase {
	
	private TestDataFactory tdf;
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

	public TestDataFactory getTdf() {
		return this.tdf;
	}
}

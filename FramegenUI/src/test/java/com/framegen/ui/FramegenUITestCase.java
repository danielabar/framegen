package com.framegen.ui;

import org.junit.Before;

import com.framegen.ui.tdf.ITestDataFactory;
import com.framegen.ui.tdf.TestDataFactory;

public class FramegenUITestCase {
	
	private ITestDataFactory tdf;

	@Before
	public void setUp() throws Exception {
		tdf = new TestDataFactory();
	}

	public ITestDataFactory getTdf() {
		return this.tdf;
	}

}

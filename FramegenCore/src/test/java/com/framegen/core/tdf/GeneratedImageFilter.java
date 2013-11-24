package com.framegen.core.tdf;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.lang3.StringUtils;

public class GeneratedImageFilter implements FilenameFilter {
	
	private final String expectedPrefix;
	private final String expectedSuffix;
	
	public GeneratedImageFilter(String expectedPrefix, String expectedSuffix) {
		super();
		this.expectedPrefix = expectedPrefix;
		this.expectedSuffix = expectedSuffix;
	}

	@Override
	public boolean accept(File dir, String name) {
		if (matchesExpectedPrefixAndSuffix(name)) {
			return true;
		}
		return false;
	}

	protected boolean matchesExpectedPrefixAndSuffix(String name) {
		return StringUtils.startsWith(name, getExpectedPrefix()) &&
				StringUtils.endsWith(name, getExpectedSuffix());
	}

	public String getExpectedPrefix() {
		return expectedPrefix;
	}

	public String getExpectedSuffix() {
		return expectedSuffix;
	}

}

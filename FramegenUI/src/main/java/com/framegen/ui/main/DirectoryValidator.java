package com.framegen.ui.main;

import java.io.File;

public class DirectoryValidator {

	public File isDirectory(String name) {
		File file = new File(name);
		return file.isDirectory() ? file : null;
	}
}

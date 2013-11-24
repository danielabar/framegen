package com.framegen.ui.util;

import java.util.List;

import com.google.common.base.Function;

public class MessageCreator implements Function<List<String>, String> {
	
	private static final String LINE_SEPARATOR = "\n";

	@Override
	public String apply(List<String> messages) {
		StringBuffer sb = new StringBuffer();
		for (String message : messages) {
			sb.append(message);
			sb.append(LINE_SEPARATOR);
		}
		return sb.toString();
	}

}

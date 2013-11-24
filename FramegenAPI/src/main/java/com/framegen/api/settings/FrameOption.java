package com.framegen.api.settings;


public enum FrameOption {
	LINE(true), 
	ROTATE(true), 
	ARC(true), 
	ZOOM(false), 
	NEGATIVE(false), 
	FADE(false), 
	TRANSP(true); 
	
	private final boolean requiresOverlay;

	FrameOption(boolean requiresOverlay) {
		this.requiresOverlay = requiresOverlay;
	}

	public boolean isRequiresOverlay() {
		return requiresOverlay;
	}
	
}

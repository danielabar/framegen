package com.framegen.api.settings;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.framegen.api.settings.option.*;

public class SettingsVO {
	
	private ProgramSettingsVO programSettings;
	private LineSettingsVO lineSettings;
	private RotationSettingsVO rotationSettings;
	private ArcSettingsVO arcSettings;
	private ZoomSettingsVO zoomSettings;
	private NegativeSettingsVO negativeSettings;
	private FadeSettingsVO fadeSettings;
	private TranspSettingsVO transpSettings;
	
	public ProgramSettingsVO getProgramSettings() {
		return programSettings;
	}
	public void setProgramSettings(ProgramSettingsVO programSettings) {
		this.programSettings = programSettings;
	}
	
	public LineSettingsVO getLineSettings() {
		return lineSettings;
	}
	public void setLineSettings(LineSettingsVO lineSettings) {
		this.lineSettings = lineSettings;
	}
	
	public RotationSettingsVO getRotationSettings() {
		return rotationSettings;
	}
	public void setRotationSettings(RotationSettingsVO rotationSettings) {
		this.rotationSettings = rotationSettings;
	}
	
	public ArcSettingsVO getArcSettings() {
		return arcSettings;
	}
	public void setArcSettings(ArcSettingsVO arcSettings) {
		this.arcSettings = arcSettings;
	}
	
	public ZoomSettingsVO getZoomSettings() {
		return zoomSettings;
	}
	public void setZoomSettings(ZoomSettingsVO zoomSettings) {
		this.zoomSettings = zoomSettings;
	}

	public NegativeSettingsVO getNegativeSettings() {
		return negativeSettings;
	}
	public void setNegativeSettings(NegativeSettingsVO negativeSettings) {
		this.negativeSettings = negativeSettings;
	}
	
	public FadeSettingsVO getFadeSettings() {
		return fadeSettings;
	}
	public void setFadeSettings(FadeSettingsVO fadeSettings) {
		this.fadeSettings = fadeSettings;
	}
	
	public TranspSettingsVO getTranspSettings() {
		return transpSettings;
	}
	public void setTranspSettings(TranspSettingsVO transpSettings) {
		this.transpSettings = transpSettings;
	}
	
	public FrameOption getFrameOption() {
		if (this.lineSettings != null)
			return FrameOption.LINE;

		if (this.rotationSettings != null)
			return FrameOption.ROTATE;
		
		if (this.arcSettings != null)
			return FrameOption.ARC;
		
		if (this.zoomSettings != null)
			return FrameOption.ZOOM;
		
		if (this.negativeSettings != null) 
			return FrameOption.NEGATIVE;
		
		if (this.fadeSettings != null)
			return FrameOption.FADE;
		
		if (this.transpSettings != null)
			return FrameOption.TRANSP;
		
		throw new UnsupportedOperationException("Unable to determine Frame Option from Settings");
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}

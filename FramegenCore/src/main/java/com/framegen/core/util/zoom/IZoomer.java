package com.framegen.core.util.zoom;

import java.awt.image.BufferedImage;

public interface IZoomer {

	public BufferedImage getZoomedImage(BufferedImage sourceImage,
			double zoomX, double zoomY, double zoomFactor);

}
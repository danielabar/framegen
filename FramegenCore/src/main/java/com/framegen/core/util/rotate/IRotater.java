package com.framegen.core.util.rotate;

import java.awt.image.BufferedImage;

public interface IRotater {

	public BufferedImage getRotatedImage(BufferedImage sourceImage,
			Integer rotationDegree);

}
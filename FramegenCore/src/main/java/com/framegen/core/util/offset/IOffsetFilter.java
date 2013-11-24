package com.framegen.core.util.offset;

import java.awt.image.BufferedImage;

public interface IOffsetFilter {

	public BufferedImage getFilteredImage(BufferedImage sourceImage,
			float multiplier, float offset);

}
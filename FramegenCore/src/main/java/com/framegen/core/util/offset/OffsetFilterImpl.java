package com.framegen.core.util.offset;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;

public class OffsetFilterImpl implements IOffsetFilter {

	@Override
	public BufferedImage getFilteredImage(BufferedImage sourceImage, float multiplier, float offset) {
		int imageType = (sourceImage.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

		BufferedImage targetImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), imageType);
		Graphics2D gTarget = targetImage.createGraphics();
		
		BufferedImageOp filter = new RescaleOp(multiplier, offset, null);
		
		gTarget.drawImage(filter.filter(sourceImage, null), 0, 0, null);
		gTarget.dispose();

		return targetImage;
	}
}

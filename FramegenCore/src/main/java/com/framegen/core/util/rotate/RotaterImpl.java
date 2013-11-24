package com.framegen.core.util.rotate;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class RotaterImpl implements IRotater {
	
	@Override
	public BufferedImage getRotatedImage(BufferedImage sourceImage, Integer rotationDegree) {
		int imageType = (sourceImage.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		double anchorX = sourceImage.getWidth() / 2;
		double anchorY = sourceImage.getHeight() / 2;
		
		BufferedImage targetImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), imageType);
		Graphics2D targetGraphics2D = targetImage.createGraphics();
		
		AffineTransform imageTransformation = getRotationTransformation(rotationDegree, anchorX, anchorY);
		
		targetGraphics2D.drawImage(sourceImage, imageTransformation, null);
		targetGraphics2D.dispose();
		
		return targetImage;
	}
	
	protected AffineTransform getRotationTransformation(Integer rotationDegree, Double anchorX, Double anchorY) {
		AffineTransform imageTransformation = new AffineTransform();
		imageTransformation.rotate(Math.toRadians(rotationDegree), anchorX, anchorY);
		return imageTransformation;
	}
	
}

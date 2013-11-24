package com.framegen.core.util.transp;

import java.awt.image.BufferedImage;

public interface ITransparency {

	public BufferedImage getTransparentImage(BufferedImage sourceImage, float alpha);

}
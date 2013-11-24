package com.framegen.core.util.transp;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/*
 * http://www.javalobby.org/articles/ultimate-image/
 */
public class TransparencyImpl implements ITransparency {

	@Override
	public BufferedImage getTransparentImage(BufferedImage sourceImage, float alpha) {

		BufferedImage transpImg = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TRANSLUCENT);
		
        Graphics2D g = transpImg.createGraphics();  
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));  
       
        g.drawImage(sourceImage, null, 0, 0);  
        g.dispose();  
        return transpImg;  
	}
}

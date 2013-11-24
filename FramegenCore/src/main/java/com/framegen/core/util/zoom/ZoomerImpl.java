package com.framegen.core.util.zoom;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/*
 * http://stackoverflow.com/questions/690871/affinetransform-scaling-a-shape-from-its-center
 */
public class ZoomerImpl implements IZoomer {
	
	@Override
	public BufferedImage getZoomedImage(BufferedImage sourceImage, double zoomX, double zoomY, double zoomFactor) {
		int imageType = (sourceImage.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		
		BufferedImage targetImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), imageType);
		Graphics2D gTarget = targetImage.createGraphics();
		
		AffineTransform tr2 = getZoomTransformation(zoomX, zoomY, zoomFactor, gTarget);

        gTarget.setTransform(tr2);
        gTarget.drawImage(sourceImage, tr2, null);
        gTarget.dispose();
        
        return targetImage;
	}

	protected AffineTransform getZoomTransformation(double zoomX, double zoomY, double zoomFactor, Graphics2D gTarget) {
		AffineTransform old= gTarget.getTransform();
		AffineTransform tr2 = AffineTransform.getTranslateInstance(-zoomX, -zoomY);
        AffineTransform tr = AffineTransform.getScaleInstance(zoomFactor, zoomFactor);
        tr.concatenate(tr2); 
        tr2=tr;
        tr = AffineTransform.getTranslateInstance(zoomX, zoomY);
        tr.concatenate(tr2); 
        tr2 = tr;
        tr= new AffineTransform(old);
        tr.concatenate(tr2);  
        tr2=tr;
		return tr2;
	}

}

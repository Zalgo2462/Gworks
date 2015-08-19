package org.zp.gworks.gui.sprites.filter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;

/**
 * Date: 8/23/2014
 * Time: 5:30 PM
 */
public class SquaringFilter extends Filter {
	private double xOffset = 0;
	private double yOffset = 0;

	@Override
	protected Image filter(Image inputImage) {
		double size = Math.sqrt(Math.pow(inputImage.getWidth(null), 2) + Math.pow(inputImage.getHeight(null), 2));
		xOffset = (size - inputImage.getWidth(null)) / 2;
		yOffset = (size - inputImage.getHeight(null)) / 2;
		VolatileImage outputImage = createVolatileImage(Math.round(Math.round(size)), Math.round(Math.round(size)));
		AffineTransform transform = new AffineTransform();
		transform.translate(xOffset, yOffset);
		Graphics2D g = outputImage.createGraphics();
		g.setComposite(AlphaComposite.Clear);
		g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
		g.setComposite(AlphaComposite.Src);
		g.drawImage(inputImage, transform, null);
		g.dispose();
		return outputImage;
	}

	public double getxOffset() {
		return xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}
}

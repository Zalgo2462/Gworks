package org.zp.blockdude.sprites.filter;

import java.awt.*;
import java.awt.image.VolatileImage;
import java.util.HashMap;

/**
 * Date: 8/23/2014
 * Time: 5:13 PM
 */
public abstract class Filter {
	private HashMap<Image, VolatileImage> cache = new HashMap<Image, VolatileImage>();

	protected abstract Image filter(Image inputImage);

	public VolatileImage getFilteredImage(Image inputImage) {
		Image outputImage = filter(inputImage);
		if (!cache.containsKey(inputImage) ||
				outputImage.getHeight(null) != cache.get(inputImage).getHeight(null) ||
				outputImage.getWidth(null) != cache.get(inputImage).getWidth()) {
			cache.put(inputImage, createVolatileImage(outputImage.getWidth(null), outputImage.getHeight(null)));
		}
		VolatileImage filteredImage = cache.get(inputImage);
		Graphics2D graphics = filteredImage.createGraphics();
		graphics.setComposite(AlphaComposite.Clear);
		graphics.fillRect(0, 0, outputImage.getWidth(null), outputImage.getHeight(null));
		graphics.setComposite(AlphaComposite.SrcOver);
		graphics.drawImage(filter(inputImage), 0, 0, null);
		graphics.dispose();
		return filteredImage;
	}

	protected VolatileImage createVolatileImage(int width, int height) {
		return GraphicsEnvironment.
				getLocalGraphicsEnvironment().
				getDefaultScreenDevice().
				getDefaultConfiguration().createCompatibleVolatileImage(
				width, height, VolatileImage.TRANSLUCENT);
	}
}

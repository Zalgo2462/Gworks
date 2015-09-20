package org.zp.gworks.sprites.filter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.awt.image.WritableRaster;

/**
 * Date: 8/29/2014
 * Time: 6:16 PM
 * Link: http://stackoverflow.com/questions/5132015/how-to-convert-image-to-sepia-in-java
 */
public class SepiaFilter extends Filter {
	@Override
	protected Image filter(Image inputImage) {
		BufferedImage img;
		if (inputImage instanceof VolatileImage) {
			img = ((VolatileImage) inputImage).getSnapshot();
		} else if (inputImage instanceof BufferedImage) {
			img = (BufferedImage) inputImage;
		} else {
			return inputImage;
		}
		int sepiaDepth = 20;

		int w = img.getWidth();
		int h = img.getHeight();

		WritableRaster raster = img.getRaster();

		// We need 4 integers (for R,G,B,A color values) per pixel.
		int[] pixels = new int[w * h * 4];
		raster.getPixels(0, 0, w, h, pixels);

		// Process 3 ints at a time for each pixel.
		// Each pixel has 3 RGB colors in array
		for (int i = 0; i < pixels.length; i += 4) {
			int r = pixels[i];
			int g = pixels[i + 1];
			int b = pixels[i + 2];

			int gry = (r + g + b) / 3;
			r = g = b = gry;
			r = r + (sepiaDepth * 2);
			g = g + sepiaDepth;

			if (r > 255) r = 255;
			if (g > 255) g = 255;
			if (b > 255) b = 255;

			// Darken blue color to increase sepia effect
			b -= 20;

			// normalize if out of bounds
			if (b < 0) b = 0;

			pixels[i] = r;
			pixels[i + 1] = g;
			pixels[i + 2] = b;
		}
		raster.setPixels(0, 0, w, h, pixels);
		return img;
	}
}

package org.zp.gworks.gui.sprites.animations;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 8/4/2014
 * Time: 11:09 PM
 */
public abstract class PaintedAnimation extends Animation {
	BufferedImage image;

	public PaintedAnimation(int width, int height) {
		image = GraphicsEnvironment.getLocalGraphicsEnvironment().
				getDefaultScreenDevice().getDefaultConfiguration().
				createCompatibleImage(width, height);
	}

	@Override
	public final BufferedImage getSprite(long delta) {
		paint(image.getGraphics(), delta);
		return image;
	}

	public abstract Shape getBounds(long delta);

	public abstract void paint(Graphics graphics, long delta);
}

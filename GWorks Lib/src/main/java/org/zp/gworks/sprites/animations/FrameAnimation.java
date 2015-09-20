package org.zp.gworks.sprites.animations;

import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 8/4/2014
 * Time: 10:32 PM
 */
public class FrameAnimation extends Animation {
	long elapsedTime = 0;
	int currentFrame = 0;
	BufferedImage[] frames;
	Shape[] bounds;
	long[] frameTimes;


	public FrameAnimation(BufferedImage[] images, Shape[] bounds, long[] frameTimes) {
		this.frames = images;
		this.bounds = bounds;
		this.frameTimes = frameTimes;
	}

	public FrameAnimation(BufferedImage[] images, Shape[] bounds, int FPS) {
		this.frames = images;
		this.bounds = bounds;
		this.frameTimes = new long[frames.length];
		long frameDelay = (60 / FPS) * 1000000000L;
		for (int iii = 0; iii < frameTimes.length; iii++) {
			this.frameTimes[iii] = frameDelay;
		}
	}

	public FrameAnimation(BufferedImage[] images, Shape[] bounds, GCanvas canvas) {
		this(images, canvas.FPS);
	}

	public FrameAnimation(BufferedImage[] images, long[] frameTimes) {
		this.frames = images;
		this.frameTimes = frameTimes;
		this.bounds = new Shape[frames.length];
		for (int i = 0; i < images.length; i++) {
			BufferedImage image = images[i];
			bounds[i] = new Rectangle(image.getWidth(), image.getHeight());
		}
	}

	public FrameAnimation(BufferedImage[] images, int FPS) {
		this.frames = images;
		this.frameTimes = new long[frames.length];
		long frameDelay = (60 / FPS) * 1000000000L;
		for (int iii = 0; iii < frameTimes.length; iii++) {
			this.frameTimes[iii] = frameDelay;
		}
		for (int i = 0; i < images.length; i++) {
			BufferedImage image = images[i];
			bounds[i] = new Rectangle(image.getWidth(), image.getHeight());
		}
	}

	public FrameAnimation(BufferedImage[] images, GCanvas canvas) {
		this(images, canvas.FPS);
	}

	@Override
	public BufferedImage getSprite(long delta) {
		if (currentFrame == frames.length - 1) {
			running = false;
		} else {
			elapsedTime += delta;
			if (elapsedTime > frameTimes[currentFrame]) {
				elapsedTime -= frameTimes[currentFrame];
				currentFrame++;
			}
		}
		return frames[currentFrame];
	}

	@Override
	public Shape getBounds(long delta) {
		return bounds[currentFrame];
	}
}










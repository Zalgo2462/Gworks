package org.zp.gworks.sprites.animations;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 7/31/2014
 * Time: 2:05 PM
 */
public abstract class Animation {
	protected boolean running;

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public abstract BufferedImage getSprite(long delta);

	public abstract Shape getBounds(long delta);
}

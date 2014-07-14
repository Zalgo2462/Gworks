package org.zp.blockdude.sprites;


import org.zp.blockdude.states.playstate.renderstrategies.SpriteRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Sprite {
	private boolean render = false;
	private int speed = 0;
	private Point location = new Point(0,0);
	private double orientation = 0.0D;
	private SpriteRenderer spriteRenderer = new SpriteRenderer(this);

	public void setRendered(final boolean render) {
		this.render = render;
	}

	public boolean shouldRender() {
		return render;
	}

	public void setSpeed(final int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setLocation(final Point location) {
		this.location = location;
	}

	public Point getLocation() {
		return location;
	}

	public void setOrientation(final double orientation) {
		this.orientation = orientation;
	}

	public double getOrientation() {
		return orientation;
	}

	public SpriteRenderer getSpriteRenderer() {
		return spriteRenderer;
	}

	public Rectangle getBounds() {
		return new Rectangle(getLocation().x, getLocation().y,
				getSprite().getWidth(), getSprite().getHeight());
	}

	public abstract BufferedImage getSprite();
}

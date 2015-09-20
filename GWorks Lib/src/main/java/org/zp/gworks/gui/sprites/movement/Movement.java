package org.zp.gworks.gui.sprites.movement;

import org.zp.gworks.gui.sprites.Sprite;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Date: 9/19/2015
 * Time: 5:25 PM
 */
public abstract class Movement {
	protected Sprite sprite;

	protected Movement(Sprite sprite) {
		this.sprite = sprite;
	}

	public abstract Point2D getLocation();

	public abstract void setLocation(double x, double y);

	public abstract void move(double x, double y);

	//velocities
	public abstract double getXVelocity();

	public abstract double getYVelocity();

	public double getVelocity() {
		return Math.hypot(getXVelocity(), getYVelocity());
	}

	//Angles
	public double getForwardAngle() {
		double x = getXVelocity();
		double y = getYVelocity();
		return Math.atan2(y, x);
	}

	public double getBackwardAngle() {
		final double forward = getForwardAngle();
		if (forward + Math.PI < 2 * Math.PI)
			return forward + Math.PI;
		else
			return forward - Math.PI;
	}

	public double angleTo(final double x, final double y) {
		double dx = x - (sprite.getRotation().getRotatedBounds().getBounds().x + sprite.getRotation().getRotatedBounds().getBounds().width / 2);
		double dy = y - (sprite.getRotation().getRotatedBounds().getBounds().y + sprite.getRotation().getRotatedBounds().getBounds().height / 2);
		return Math.atan2(dy, dx);
	}

	public double distanceTo(double x, double y) {
		x = Math.abs(x - getLocation().getX());
		y = Math.abs(y - getLocation().getY());

		return Math.hypot(x, y);
	}

	//Collisions
	public abstract Shape getCollisionArea();

	public abstract void setCollisionArea(Shape collisionArea);

	public abstract Shape getUntranslatedCollisionArea();
}

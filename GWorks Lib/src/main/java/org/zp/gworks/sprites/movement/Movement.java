package org.zp.gworks.sprites.movement;

import org.zp.gworks.sprites.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Date: 9/19/2015
 * Time: 5:25 PM
 */
public abstract class Movement {
	protected Sprite sprite;
	protected Point2D currentLocation;
	protected Shape collisionArea;

	protected Movement(Sprite sprite) {
		this.sprite = sprite;
	}

	public Point2D getLocation() {
		return currentLocation;
	}

	public void setLocation(double x, double y) {
		getLocation().setLocation(x, y);
	}

	public void move(double x, double y) {
		getLocation().setLocation(getLocation().getX() + x, getLocation().getY() + y);
	}

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
	public Shape getCollisionArea() {
		AffineTransform trans = new AffineTransform();
		trans.translate(getLocation().getX(), getLocation().getY());
		return trans.createTransformedShape(collisionArea);
	}

	public void setCollisionArea(Shape collisionArea) {
		this.collisionArea = collisionArea;
	}

	public Shape getUntranslatedCollisionArea() {
		if (collisionArea == null) {
			collisionArea = new Rectangle(sprite.getRenderer().getSprite().getWidth(), sprite.getRenderer().getSprite().getHeight());
		}
		return collisionArea;
	}
}

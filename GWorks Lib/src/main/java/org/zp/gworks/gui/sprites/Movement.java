package org.zp.gworks.gui.sprites;

import org.zp.gworks.gui.sprites.forces.Force;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Date: 9/19/2015
 * Time: 5:12 PM
 */
public class Movement {
	private Sprite sprite;
	private Point2D currentLocation;
	private double xVelocity;
	private double yVelocity;
	private double maxVelocity; //In either x or y direction
	private Shape collisionArea;
	private ArrayList<Force> accelerations;

	//Speed in pixels per second
	Movement(Sprite sprite) {
		this.sprite = sprite;
		this.currentLocation = new Point2D.Double(0, 0);
		this.maxVelocity = 0;
		this.accelerations = new ArrayList<Force>();
	}

	public Point2D getLocation() {
		return currentLocation;
	}

	public void setLocation(final double x, final double y) {
		currentLocation.setLocation(x, y);
	}

	public void move(final double x, final double y) {
		currentLocation.setLocation(currentLocation.getX() + x, currentLocation.getY() + y);
	}

	public void accelerate(final long delta) {
		final double dXVel = getXAcceleration() * delta / 1000000000D;
		final double dYVel = getYAcceleration() * delta / 1000000000D;
		if (xVelocity + dXVel > maxVelocity) {
			xVelocity = maxVelocity;
		} else if (xVelocity + dXVel < -maxVelocity) {
			xVelocity = -maxVelocity;
		} else {
			xVelocity += dXVel;
		}

		if (yVelocity + dYVel > maxVelocity) {
			yVelocity = maxVelocity;
		} else if (yVelocity + dYVel < -maxVelocity) {
			yVelocity = -maxVelocity;
		} else {
			yVelocity += dYVel;
		}
	}

	//Accelerations
	public double getXAcceleration() {
		return accelerations.parallelStream().filter(Force::isActive).mapToDouble(Force::getX).reduce((a, b) -> a + b).orElse(0);
	}

	public double getYAcceleration() {
		return accelerations.parallelStream().filter(Force::isActive).mapToDouble(Force::getY).reduce((a, b) -> a + b).orElse(0);
	}

	//All these functions add forces by reference
	public void addForce(Force force) {
		accelerations.add(force);
	}

	public void addForces(final java.util.List<Force> forces) {
		accelerations.addAll(forces);
	}

	public void setForces(final java.util.List<Force> forces) {
		accelerations.clear();
		accelerations.addAll(forces);
	}

	//velocities
	public double getXVelocity() {
		return xVelocity;
	}

	public void setXVelocity(final double xVel) {
		this.xVelocity = xVel;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public void setYVelocity(final double yVel) {
		this.yVelocity = yVel;
	}

	public double getVelocity() {
		return Math.hypot(getXVelocity(), getYVelocity());
	}

	public void setVelocity(final double magnitude, final double angle) {
		setXVelocity(Math.cos(angle) * magnitude);
		setYVelocity(Math.sin(angle) * magnitude);
	}

	public double getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(final double maxVelocity) {
		this.maxVelocity = maxVelocity;
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
		x = Math.abs(x - currentLocation.getX());
		y = Math.abs(y - currentLocation.getY());

		return Math.hypot(x, y);
	}

	//Collisions
	public Shape getCollisionArea() {

		AffineTransform trans = new AffineTransform();
		trans.translate(currentLocation.getX(), currentLocation.getY());
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

package org.zp.gworks.gui.sprites.movement.movement2d;

import org.zp.gworks.gui.sprites.Sprite;
import org.zp.gworks.gui.sprites.movement.Movement;
import org.zp.gworks.gui.sprites.movement.movement2d.forces.Force;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Date: 9/19/2015
 * Time: 5:12 PM
 */
public class Movement2D extends Movement {
	private Point2D currentLocation;
	private double xVelocity;
	private double yVelocity;
	private double maxVelocity; //In either x or y direction
	private Shape collisionArea;
	private ArrayList<Force> accelerations;

	//Speed in pixels per second
	public Movement2D(Sprite sprite) {
		super(sprite);
		this.currentLocation = new Point2D.Double(0, 0);
		this.maxVelocity = 0;
		this.accelerations = new ArrayList<Force>();
	}

	@Override
	public Point2D getLocation() {
		return currentLocation;
	}

	@Override
	public void setLocation(final double x, final double y) {
		currentLocation.setLocation(x, y);
	}

	@Override
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
	@Override
	public double getXVelocity() {
		return xVelocity;
	}

	public void setXVelocity(final double xVel) {
		this.xVelocity = xVel;
	}

	@Override
	public double getYVelocity() {
		return yVelocity;
	}

	public void setYVelocity(final double yVel) {
		this.yVelocity = yVel;
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

	//Collisions
	@Override
	public Shape getCollisionArea() {

		AffineTransform trans = new AffineTransform();
		trans.translate(currentLocation.getX(), currentLocation.getY());
		return trans.createTransformedShape(collisionArea);
	}

	@Override
	public void setCollisionArea(Shape collisionArea) {
		this.collisionArea = collisionArea;
	}

	@Override
	public Shape getUntranslatedCollisionArea() {
		if (collisionArea == null) {
			collisionArea = new Rectangle(sprite.getRenderer().getSprite().getWidth(), sprite.getRenderer().getSprite().getHeight());
		}
		return collisionArea;
	}
}

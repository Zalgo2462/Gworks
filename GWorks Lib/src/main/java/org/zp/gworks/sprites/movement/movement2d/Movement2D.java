package org.zp.gworks.sprites.movement.movement2d;

import org.zp.gworks.sprites.Sprite;
import org.zp.gworks.sprites.movement.Movement;
import org.zp.gworks.sprites.movement.movement2d.forces.Force2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Date: 9/19/2015
 * Time: 5:12 PM
 */
public class Movement2D extends Movement {
	private double xVelocity;
	private double yVelocity;
	private double maxVelocity; //In either x or y direction
	private ArrayList<Force2D> accelerations;

	//Speed in pixels per second
	public Movement2D(Sprite sprite) {
		super(sprite);
		this.currentLocation = new Point2D.Double(0, 0);
		this.maxVelocity = 0;
		this.accelerations = new ArrayList<Force2D>();
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
		return accelerations.parallelStream().filter(Force2D::isActive).mapToDouble(Force2D::getX).reduce((a, b) -> a + b).orElse(0);
	}

	public double getYAcceleration() {
		return accelerations.parallelStream().filter(Force2D::isActive).mapToDouble(Force2D::getY).reduce((a, b) -> a + b).orElse(0);
	}

	//All these functions add forces by reference
	public void addForce(Force2D force2D) {
		accelerations.add(force2D);
	}

	public void addForces(final java.util.List<Force2D> force2Ds) {
		accelerations.addAll(force2Ds);
	}

	public void setForces(final java.util.List<Force2D> force2Ds) {
		accelerations.clear();
		accelerations.addAll(force2Ds);
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
}

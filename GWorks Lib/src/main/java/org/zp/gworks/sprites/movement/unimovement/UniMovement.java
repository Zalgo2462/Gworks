package org.zp.gworks.sprites.movement.unimovement;

import org.zp.gworks.sprites.Sprite;
import org.zp.gworks.sprites.movement.Movement;
import org.zp.gworks.sprites.movement.unimovement.forces.Force1D;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Date: 9/19/2015
 * Time: 6:15 PM
 */
public class UniMovement extends Movement {
	private double velocity;
	private double maxVelocity;
	private ArrayList<Force1D> accelerations;

	public UniMovement(Sprite sprite) {
		super(sprite);
		this.currentLocation = new Point2D.Double(0, 0);
		this.maxVelocity = 0;
		this.accelerations = new ArrayList<Force1D>();
	}

	public void setVelocity(double velocity)
	{
		if (velocity <= maxVelocity)
			this.velocity = velocity;
		else
			this.velocity = maxVelocity;
	}

	public void accelerate(final long delta) {
		final double dVel = getAcceleration() * delta / 1000000000D;
		if (velocity + dVel > maxVelocity) {
			velocity = maxVelocity;
		} else if (velocity + dVel < -maxVelocity) {
			velocity = -maxVelocity;
		} else {
			velocity += dVel;
		}
	}

	public double getAcceleration() {
		return accelerations.parallelStream().filter(Force1D::isActive).mapToDouble(Force1D::getX).reduce((a, b) -> a + b).orElse(0);
	}

	public void addForce(Force1D force1D) {
		accelerations.add(force1D);
	}

	public void addForces(final java.util.List<Force1D> force1Ds) {
		accelerations.addAll(force1Ds);
	}

	public void setForces(final java.util.List<Force1D> force1Ds) {
		accelerations.clear();
		accelerations.addAll(force1Ds);
	}

	@Override
	public double getXVelocity() {
		return Math.cos(sprite.getRotation().getCurrentAngle()) * velocity;
	}

	@Override
	public double getYVelocity() {
		return Math.sin(sprite.getRotation().getCurrentAngle()) * velocity;
	}

	public double getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(final double maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public boolean isMovingForward() {
		return velocity > 0;
	}
}

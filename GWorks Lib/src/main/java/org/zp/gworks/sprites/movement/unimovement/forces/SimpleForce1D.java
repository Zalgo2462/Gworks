package org.zp.gworks.sprites.movement.unimovement.forces;

/**
 * Date: 9/19/2015
 * Time: 6:19 PM
 */
public class SimpleForce1D implements Force1D{
	private double x;
	private boolean active;

	public SimpleForce1D() {
		this(0);
	}

	public SimpleForce1D(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "SimpleForce1D{" +
				"x=" + x +
				", active=" + active +
				'}';
	}
}

package org.zp.gworks.sprites.movement.movement2d.forces;

/**
 * Date: 9/11/2015
 * Time: 4:45 PM
 */
public class SimpleForce2D implements Force2D {
	private double x;
	private double y;
	private boolean active;

	public SimpleForce2D() {
		this(0, 0);
	}

	public SimpleForce2D(double x, double y) {
		this.x = x;
		this.y = y;
		active = true;
	}

	public void setForce(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	@Override
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Force{" +
				"x=" + x +
				", y=" + y +
				", active=" + active +
				'}';
	}
}

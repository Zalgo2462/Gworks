package org.zp.gworks.sprites;

import org.zp.gworks.sprites.filter.RotationFilter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Date: 9/19/2015
 * Time: 5:15 PM
 */
public class Rotation {
	private Sprite sprite;
	private boolean clockwise;
	private double velocity;
	private boolean moving;
	private RotationFilter rotationFilter;

	//Speed in radians per second
	Rotation(Sprite sprite) {
		this.sprite = sprite;
		this.clockwise = true;
		this.velocity = 0;
		this.moving = false;
		this.rotationFilter = new RotationFilter();
	}

	public double getCurrentAngle() {
		return rotationFilter.getOrientation();
	}

	public double getBackwardAngle() {
		final double forward = getCurrentAngle();
		if (forward + Math.PI < 2 * Math.PI)
			return forward + Math.PI;
		else
			return forward - Math.PI;
	}

	public void setCurrentOrientation(final double currentOrientation) {
		rotationFilter.setOrientation(currentOrientation);
	}

	public boolean isClockwise() {
		return clockwise;
	}

	public void setClockwise(final boolean clockwise) {
		this.clockwise = clockwise;
	}

	public double getVelocity() {
		return velocity;
	}

	public void getVelocity(final double velocity) {
		this.velocity = velocity;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(final boolean moving) {
		this.moving = moving;
	}

	public double angleTo(final double x, final double y) {
		double dTheta = sprite.getMovement().angleTo(x, y) - rotationFilter.getOrientation();
		if (dTheta > 0) {
			while (dTheta > Math.PI * 2) {
				dTheta -= Math.PI * 2;
			}
			if (dTheta > Math.PI) {
				dTheta -= Math.PI * -2;
			}
		} else {
			while (dTheta < -Math.PI * 2) {
				dTheta += Math.PI * 2;
			}
			if (dTheta < -Math.PI) {
				dTheta += Math.PI * 2;
			}
		}
		return dTheta;
	}

	public void rotate(double theta) {
		theta = clockwise ? theta : -theta;
		rotationFilter.setOrientation(rotationFilter.getOrientation() + theta);
	}

	public Point2D rotatePoint(double x, double y, double anchorX, double anchorY, double theta) {
		double dX = x - anchorX;
		double dY = y - anchorY;
		Point2D p = rotatePoint(dX, dY, theta);
		p.setLocation(p.getX() + anchorX, p.getY() + anchorY);
		return p;
	}

	public Point2D rotatePoint(double x, double y, double theta) {
		double[][] pointMatrix = {{x},
				{y}};
		double sin = Math.sin(theta);
		double cos = Math.cos(theta);
		double[][] rotationMatrix = {{cos, -sin},
				{sin, cos}};
		double[][] result = multiplyByMatrix(rotationMatrix, pointMatrix);
		return new Point2D.Double(result[0][0], result[1][0]);
	}

	RotationFilter getRotatedFilter() {
		return rotationFilter;
	}

	private double[][] multiplyByMatrix(double[][] m1, double[][] m2) {
		int m1ColLength = m1[0].length; // m1 columns length
		int m2RowLength = m2.length;    // m2 rows length
		if (m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
		int mRRowLength = m1.length;    // m result rows length
		int mRColLength = m2[0].length; // m result columns length
		double[][] mResult = new double[mRRowLength][mRColLength];
		for (int i = 0; i < mRRowLength; i++) {         // rows from m1
			for (int j = 0; j < mRColLength; j++) {     // columns from m2
				for (int k = 0; k < m1ColLength; k++) { // columns from m1
					mResult[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return mResult;
	}

	private Shape getRotatedShape(Shape input) {
		AffineTransform trans = new AffineTransform();
		trans.rotate(
				rotationFilter.getOrientation(),
				input.getBounds().width / 2,
				input.getBounds().height / 2
		);
		return trans.createTransformedShape(input);
	}

	public Shape getRotatedCollisionArea() {
		Shape collArea = sprite.getMovement().getUntranslatedCollisionArea();
		Shape rotatedArea = getRotatedShape(collArea);
		AffineTransform trans = new AffineTransform();
		trans.translate(sprite.getMovement().getLocation().getX(), sprite.getMovement().getLocation().getY());
		return trans.createTransformedShape(rotatedArea);
	}

	public Shape getRotatedBounds() {
		if (sprite.getRenderer().getSprite() == null) {
			return null;
		}
		Rectangle r = new Rectangle(0, 0,
				sprite.getRenderer().getSprite().getWidth(), sprite.getRenderer().getSprite().getHeight());
		Shape s = getRotatedShape(r);
		AffineTransform trans = new AffineTransform();
		trans.translate(sprite.getMovement().getLocation().getX(), sprite.getMovement().getLocation().getY());
		return trans.createTransformedShape(s);
	}
}

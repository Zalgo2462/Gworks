package org.zp.blockdude.sprites;


import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class Sprite {
	protected Movement movement;
	protected Rotation rotation;
	protected Renderer renderer;

	public Sprite() {
		renderer = new Renderer();
		movement = new Movement();
		rotation = new Rotation();
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Movement getMovement() {
		return movement;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public class Movement {
		private Point currentLocation;
		private double angle;
		private double acceleration;
		private double deceleration;
		private double speed;
		private double maxSpeed;

		//Speed in pixels per second
		public Movement() {
			this.currentLocation = new Point(0, 0);
			this.angle = 0;
			this.acceleration = 0;
			this.deceleration = 0;
			this.speed = 0;
			this.maxSpeed = 0;
		}

		public Point getLocation() {
			return currentLocation;
		}

		public void setCurrentLocation(final double x, final double y) {
			currentLocation.setLocation(x, y);
		}

		public double getAngle() {
			if (angle > 0) {
				while (angle > Math.PI * 2) {
					angle -= Math.PI * 2;
				}
			} else {
				while (angle < -Math.PI * 2) {
					angle += Math.PI * 2;
				}
			}
			return angle;
		}

		public void setAngle(double angle) {
			if (angle > 0) {
				while (angle > Math.PI * 2) {
					angle -= Math.PI * 2;
				}
			} else {
				while (angle < -Math.PI * 2) {
					angle += Math.PI * 2;
				}
			}
			this.angle = angle;
		}

		public double getAcceleration() {
			return acceleration;
		}

		public void setAcceleration(double acceleration) {
			this.acceleration = acceleration;
		}

		public double getDeceleration() {
			return deceleration;
		}

		public void setDeceleration(double deceleration) {
			this.deceleration = deceleration;
		}

		public double getSpeed() {
			return speed;
		}

		public void setSpeed(final double speed) {
			this.speed = speed;
		}

		public double getMaxSpeed() {
			return maxSpeed;
		}

		public void setMaxSpeed(double maxSpeed) {
			this.maxSpeed = maxSpeed;
		}

		public double getXMovement() {
			return Math.cos(angle);
		}

		public double getYMovement() {
			return Math.sin(angle);
		}

		public double getAngleTo(final double x, final double y) {
			double dx = x - (renderer.getBounds().getBounds().x + renderer.getBounds().getBounds().width / 2);
			double dy = y - (renderer.getBounds().getBounds().y + renderer.getBounds().getBounds().height / 2);
			return Math.atan2(dy, dx);
		}

		public void accelerate(final long delta) {
			double dSpeed = acceleration * delta / 1000000000D;
			if (speed + dSpeed <= maxSpeed) {
				speed += dSpeed;
			} else {
				speed = maxSpeed;
			}
		}

		public void decelerate(final long delta) {
			double dSpeed = deceleration * delta / 1000000000D;
			if (speed + dSpeed >= -maxSpeed) {
				speed += dSpeed;
			} else {
				speed = -maxSpeed;
			}
		}

		public void decelerateToZero(final long delta) {
			if (speed > 0) {
				double dSpeed = deceleration * delta / 1000000000D;
				if (speed + dSpeed >= 0) {
					speed += dSpeed;
				} else {
					speed = 0;
				}
			} else if (speed < 0) {
				double dSpeed = acceleration * delta / 1000000000D;
				if (speed + dSpeed <= 0) {
					speed += dSpeed;
				} else {
					speed = 0;
				}
			}
		}

		public void move(final double x, final double y) {
			currentLocation.setLocation(currentLocation.getX() + x, currentLocation.getY() + y);
		}
	}

	public class Rotation {
		private double currentOrientation;
		private boolean clockwise;
		private double speed;
		private boolean moving;

		//Speed in radians per second
		public Rotation() {
			this.currentOrientation = 0;
			this.clockwise = true;
			this.speed = 0;
			this.moving = false;
		}

		public double getCurrentOrientation() {
			return currentOrientation;
		}

		public void setCurrentOrientation(final double currentOrientation) {
			this.currentOrientation = currentOrientation;
		}

		public boolean isClockwise() {
			return clockwise;
		}

		public void setClockwise(final boolean clockwise) {
			this.clockwise = clockwise;
		}

		public double getSpeed() {
			return speed;
		}

		public void setSpeed(final double speed) {
			this.speed = speed;
		}

		public boolean isMoving() {
			return moving;
		}

		public void setMoving(final boolean moving) {
			this.moving = moving;
		}

		public double getAngleTo(final double x, final double y) {
			double dTheta = movement.getAngleTo(x, y) - currentOrientation;
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

		public void rotate(final double theta) {
			currentOrientation += theta;
		}

		public Point rotatePoint(double x, double y, double anchorX, double anchorY, double theta) {
			double dX = x - anchorX;
			double dY = y - anchorY;
			Point p = rotatePoint(dX, dY, theta);
			p.setLocation(p.getX() + anchorX, p.getY() + anchorY);
			return p;
		}

		public Point rotatePoint(double x, double y, double theta) {
			double[][] pointMatrix = {{x},
					{y}};
			double sin = Math.sin(theta);
			double cos = Math.cos(theta);
			double[][] rotationMatrix = {{cos, -sin},
					{sin, cos}};
			double[][] result = multiplyByMatrix(rotationMatrix, pointMatrix);
			Point toReturn = new Point();
			toReturn.setLocation(result[0][0], result[1][0]);
			return toReturn;
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
	}

	public class Renderer implements GRenderListener {
		private double lastRenderedOrientation = 0;
		private int xOffset = 0, yOffset = 0;
		private boolean rendered;
		private BufferedImage sprite;
		private BufferedImage squareSprite;
		private BufferedImage rotatedSprite;
		private Animation animation;

		@Override
		public void paint(GCanvas canvas, Graphics graphics, long delta) {
			if (rotation.getCurrentOrientation() != lastRenderedOrientation) {
				rotateSprite();
			}
			if (rotatedSprite == null) {
				rotatedSprite = squareSprite;
			}
			if (rendered) {
				graphics.drawImage(
						rotatedSprite,
						movement.getLocation().x - xOffset,
						movement.getLocation().y - yOffset,
						null
				);
				//((Graphics2D) graphics).draw(getBounds());
				//((Graphics2D) graphics).draw(getBounds().getBounds());
				/*Area area1 = new Area(getBounds());
				graphics.setColor(Color.BLUE);
				((Graphics2D) graphics).fill(area1);
				Area area2 = SpriteManager.CANVAS_EDGE.RIGHT.getArea();
				graphics.setColor(Color.GREEN);
				((Graphics2D) graphics).fill(area2);
				area1.intersect(area2);
				if(!area1.isEmpty()) {
					double x = area1.getBounds().getX();
					double width = area1.getBounds().getWidth();
					double y = area1.getBounds().getY();
					double height = area1.getBounds().getHeight();
					int x0 = (int) (x + width / 2);
					int y0 = (int) (y + height / 2);

					graphics.setColor(Color.RED);
					((Graphics2D) graphics).drawLine(
							getBounds().getBounds().x + getBounds().getBounds().width / 2,
							getBounds().getBounds().y + getBounds().getBounds().height / 2,
							x0,
							y0
					);
					System.out.println(movement.getAngleTo(x0, y0));
				}
				graphics.setColor(Color.BLACK);  */

			}
		}

		public boolean shouldRender() {
			return rendered;
		}

		public void setRendered(boolean rendered) {
			this.rendered = rendered;
		}

		public BufferedImage getSprite() {
			return sprite;
		}

		public void setSprite(BufferedImage sprite) {
			this.sprite = sprite;
			makeSpriteSquare();
		}

		public Animation getAnimation() {
			return animation;
		}

		public void setAnimation(Animation animation) {
			this.animation = animation;
		}

		private void makeSpriteSquare() {
			double size = Math.sqrt(Math.pow(sprite.getWidth(), 2) + Math.pow(sprite.getHeight(), 2));
			xOffset = (int) Math.ceil(size / 2 - sprite.getWidth() / 2);
			yOffset = (int) Math.ceil(size / 2 - sprite.getHeight() / 2);
			AffineTransform transform = new AffineTransform();
			transform.translate(xOffset, yOffset);
			AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			squareSprite = new BufferedImage((int) Math.ceil(size), (int) Math.ceil(size), sprite.getType());
			transformOp.filter(sprite, squareSprite);
		}

		private void rotateSprite() {
			AffineTransform transform = AffineTransform.getRotateInstance(
					rotation.getCurrentOrientation(), squareSprite.getWidth() / 2, squareSprite.getHeight() / 2
			);
			AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			rotatedSprite = transformOp.createCompatibleDestImage(squareSprite, squareSprite.getColorModel());
			transformOp.filter(squareSprite, rotatedSprite);
			lastRenderedOrientation = rotation.currentOrientation;
		}

		public Shape getBounds() {
			if (getSprite() == null) {
				return null;
			}
			AffineTransform trans = new AffineTransform();
			trans.rotate(
					lastRenderedOrientation,
					sprite.getWidth() / 2,
					sprite.getHeight() / 2
			);
			Rectangle r = new Rectangle(0, 0,
					sprite.getWidth(), sprite.getHeight());
			Shape s = trans.createTransformedShape(r);
			trans = new AffineTransform();
			trans.translate(movement.currentLocation.getX(), movement.currentLocation.getY());
			return trans.createTransformedShape(s);
		}
	}
}

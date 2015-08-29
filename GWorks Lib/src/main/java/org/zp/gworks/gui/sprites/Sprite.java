package org.zp.gworks.gui.sprites;


import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.gui.sprites.animations.Animation;
import org.zp.gworks.gui.sprites.filter.AddAlphaFilter;
import org.zp.gworks.gui.sprites.filter.Filter;
import org.zp.gworks.gui.sprites.filter.RotationFilter;
import org.zp.gworks.gui.sprites.filter.SquaringFilter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.LinkedList;

public abstract class Sprite {
	protected final Movement movement;
	protected final Renderer renderer;
	protected final Rotation rotation;

	public Sprite() {
		renderer = new Renderer();
		movement = new Movement();
		rotation = new Rotation();
	}

	public Movement getMovement() {
		return movement;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public class Movement {
		private Point2D currentLocation;
		private double angle;
		private double acceleration;
		private double deceleration;
		private double naturalDeceleration;
		private double speed;
		private double maxSpeed;
		private Shape collisionArea;

		//Speed in pixels per second
		public Movement() {
			this.currentLocation = new Point2D.Double(0, 0);
			this.angle = 0;
			this.acceleration = 0;
			this.deceleration = 0;
			this.naturalDeceleration = 0;
			this.speed = 0;
			this.maxSpeed = 0;
		}

		public Point2D getLocation() {
			return currentLocation;
		}

		public void setLocation(final double x, final double y) {
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

		public void setDeceleration(int deceleration) {
			this.deceleration = deceleration;
		}

		public double getNaturalDeceleration() {
			return naturalDeceleration;
		}

		public void setNaturalDeceleration(double naturalDeceleration) {
			this.naturalDeceleration = naturalDeceleration;
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

		public double angleTo(final double x, final double y) {
			double dx = x - (rotation.getRotatedBounds().getBounds().x + rotation.getRotatedBounds().getBounds().width / 2);
			double dy = y - (rotation.getRotatedBounds().getBounds().y + rotation.getRotatedBounds().getBounds().height / 2);
			return Math.atan2(dy, dx);
		}

		public double distanceTo(double x, double y) {
			x = Math.abs(x - currentLocation.getX());
			y = Math.abs(y - currentLocation.getY());

			return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
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
				double dSpeed = naturalDeceleration * delta / 1000000000D;
				if (speed + dSpeed >= 0) {
					speed += dSpeed;
				} else {
					speed = 0;
				}
			} else if (speed < 0) {
				double dSpeed = -naturalDeceleration * delta / 1000000000D;
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

		public Shape getCollisionArea() {
			if (collisionArea == null) {
				collisionArea = new Rectangle(renderer.getSprite().getWidth(), renderer.getSprite().getHeight());
			}
			AffineTransform trans = new AffineTransform();
			trans.translate(currentLocation.getX(), currentLocation.getY());
			return trans.createTransformedShape(collisionArea);
		}

		public void setCollisionArea(Shape collisionArea) {
			this.collisionArea = collisionArea;
		}
	}

	public class Rotation {
		private boolean clockwise;
		private double speed;
		private boolean moving;
		private RotationFilter rotationFilter;

		//Speed in radians per second
		public Rotation() {
			this.clockwise = true;
			this.speed = 0;
			this.moving = false;
			this.rotationFilter = new RotationFilter();
		}

		public double getCurrentOrientation() {
			return rotationFilter.getOrientation();
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

		public double angleTo(final double x, final double y) {
			double dTheta = movement.angleTo(x, y) - rotationFilter.getOrientation();
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

		private RotationFilter getRotatedFilter() {
			return rotationFilter;
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
			if (movement.collisionArea == null) {
				movement.setCollisionArea(
						new Rectangle(renderer.getSprite().getWidth(),
								renderer.getSprite().getHeight())
				);
			}
			Shape collArea = movement.collisionArea;
			Shape rotatedArea = getRotatedShape(collArea);
			AffineTransform trans = new AffineTransform();
			trans.translate(movement.getLocation().getX(), movement.getLocation().getY());
			return trans.createTransformedShape(rotatedArea);
		}

		public Shape getRotatedBounds() {
			if (renderer.sprite == null) {
				return null;
			}
			Rectangle r = new Rectangle(0, 0,
					renderer.sprite.getWidth(), renderer.sprite.getHeight());
			Shape s = getRotatedShape(r);
			AffineTransform trans = new AffineTransform();
			trans.translate(movement.getLocation().getX(), movement.getLocation().getY());
			return trans.createTransformedShape(s);
		}
	}

	public class Renderer implements GRenderListener {
		private boolean rendered;
		private float opacity;
		private BufferedImage spriteBacker;
		private VolatileImage sprite;
		private Animation animation;
		private AddAlphaFilter addAlphaFilter;
		private SquaringFilter squaringFilter;
		private LinkedList<Filter> filters;

		public Renderer() {
			this.opacity = 1F;
			this.filters = new LinkedList<Filter>();
			this.squaringFilter = new SquaringFilter();
		}

		@Override
		public void paint(GCanvas canvas, Graphics graphics, long delta) {
			if (animation != null && animation.isRunning()) {
				BufferedImage image = animation.getSprite(delta);
				if (!image.equals(spriteBacker)) {
					setSprite(image);
				}
				Shape collArea = animation.getBounds(delta);
				if (!collArea.equals(movement.getCollisionArea())) {
					movement.setCollisionArea(collArea);
				}
			}

			if (sprite.contentsLost()) {
				makeTranslucentSprite();
			}

			VolatileImage filteredImage = sprite;

			for (int i = 0; i < filters.size(); i++) {
				if (filteredImage.contentsLost()) {
					filteredImage = sprite;
					i = 0;
					continue;
				}
				Filter filter = filters.get(i);
				filteredImage = filter.getFilteredImage(filteredImage);
			}


			filteredImage = squaringFilter.getFilteredImage(filteredImage);
			filteredImage = rotation.getRotatedFilter().getFilteredImage(filteredImage);

			if (rendered) {
				((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
				graphics.drawImage(
						filteredImage,
						Math.round(Math.round(movement.getLocation().getX() - squaringFilter.getxOffset())),
						Math.round(Math.round(movement.getLocation().getY() - squaringFilter.getyOffset())),
						null
				);
			}
		}

		public BufferedImage getSprite() {
			return spriteBacker;
		}

		public void setSprite(BufferedImage sprite) {
			this.spriteBacker = sprite;
			this.addAlphaFilter = new AddAlphaFilter();
			makeTranslucentSprite();
		}

		private void makeTranslucentSprite() {
			this.sprite = addAlphaFilter.getFilteredImage(spriteBacker);
		}

		public Rectangle2D.Double getBounds() {
			if (getSprite() == null) {
				return null;
			}
			return new Rectangle2D.Double(movement.getLocation().getX(), movement.getLocation().getY(),
					sprite.getWidth(), sprite.getHeight());
		}

		public boolean shouldRender() {
			return rendered;
		}

		public void setRendered(boolean rendered) {
			this.rendered = rendered;
		}

		public float getOpacity() {
			return opacity;
		}

		public void setOpacity(float opacity) {
			this.opacity = opacity;
		}

		public Animation getAnimation() {
			return animation;
		}

		public void setAnimation(Animation animation) {
			this.animation = animation;
		}

		public boolean removeFilter(Filter filter) {
			return filters.remove(filter);
		}

		public boolean addFilter(Filter filter) {
			return filters.offer(filter);
		}
	}
}

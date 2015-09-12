package org.zp.gworks.gui.sprites;


import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.gui.sprites.animations.Animation;
import org.zp.gworks.gui.sprites.filter.AddAlphaFilter;
import org.zp.gworks.gui.sprites.filter.Filter;
import org.zp.gworks.gui.sprites.filter.RotationFilter;
import org.zp.gworks.gui.sprites.filter.SquaringFilter;
import org.zp.gworks.gui.sprites.forces.Force;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

	//TODO: separate x and y accelerations
	//TODO: convert from angle & magnitude to x and y
	public class Movement {
		private Point2D currentLocation;
		private double xVelocity;
		private double yVelocity;
		private double maxVelocity; //In either x or y direction
		private Shape collisionArea;
		private ArrayList<Force> accelerations;

		//Speed in pixels per second
		public Movement() {
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

		public void addForces(final List<Force> forces) {
			accelerations.addAll(forces);
		}

		public void setForces(final List<Force> forces) {
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
			double dx = x - (rotation.getRotatedBounds().getBounds().x + rotation.getRotatedBounds().getBounds().width / 2);
			double dy = y - (rotation.getRotatedBounds().getBounds().y + rotation.getRotatedBounds().getBounds().height / 2);
			return Math.atan2(dy, dx);
		}

		public double distanceTo(double x, double y) {
			x = Math.abs(x - currentLocation.getX());
			y = Math.abs(y - currentLocation.getY());

			return Math.hypot(x, y);
		}

		//Collisions
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
		private double velocity;
		private boolean moving;
		private RotationFilter rotationFilter;

		//Speed in radians per second
		public Rotation() {
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
				//TODO: move this into ticklistener
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


			if (rotation.getCurrentAngle() != 0 || rotation.moving) {
				filteredImage = squaringFilter.getFilteredImage(filteredImage);
				filteredImage = rotation.getRotatedFilter().getFilteredImage(filteredImage);
			}

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

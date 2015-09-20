package org.zp.blockdude.sprites;

import org.zp.gworks.sprites.Sprite;
import org.zp.gworks.sprites.movement.movement2d.Movement2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Date: 7/25/2014
 * Time: 12:18 AM
 */
public abstract class Actor extends Sprite<Movement2D> {
	protected final LinkedList<Missile> missiles;
	private final Color color;
	private final double naturalDeceleration;        //todo: move to movement classes
	protected double lastMissileFiredTime;
	protected double missilesPerSecond;
	protected int missileDamage;
	protected int health;
	private double acceleration;
	private double deceleration;
	private BufferedImage image;


	public Actor(int size, Color color) {
		super(Movement2D.class);
		this.color = color;
		createSprite(size);
		movement.setMaxVelocity(250);
		naturalDeceleration = -200;
		acceleration = 500;
		deceleration = -500;
		rotation.getVelocity(Math.PI);
		renderer.setSprite(image);
		missiles = new LinkedList<Missile>();
		lastMissileFiredTime = System.nanoTime();
		missilesPerSecond = 3;
		missileDamage = 25;
		health = 100;
	}

	private void createSprite(int size) {
		image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, size, size);
		g.setColor(Color.BLACK);
		g.drawLine(size / 2, size / 2 - 1, size, size / 2 - 1);
		g.drawLine(size / 2, size / 2, size, size / 2);
		g.drawLine(size / 2, size / 2 + 1, size, size / 2 + 1);
		g.dispose();
	}

	public Collection<Missile> getMissiles() {
		return missiles;
	}

	public Missile fireMissile(double theta) {
		Missile missile = new Missile(color, missileDamage);
		double x0 = (rotation.getRotatedBounds().getBounds().getX() + rotation.getRotatedBounds().getBounds().getWidth() / 2) -
				(missile.getRotation().getRotatedBounds().getBounds().getWidth() / 2);
		double y0 = rotation.getRotatedBounds().getBounds().getY() + rotation.getRotatedBounds().getBounds().getHeight() / 2 -
				(missile.getRotation().getRotatedBounds().getBounds().getHeight() / 2);
		double x1 = x0 + 35;
		double y1 = y0 - (missile.getRotation().getRotatedBounds().getBounds().getHeight() / 2);
		Point2D rotatedPoint = getRotation().rotatePoint(x1, y1, x0, y0, theta);
		missile.getMovement().setLocation(rotatedPoint.getX(), rotatedPoint.getY());
		missile.getRotation().setCurrentOrientation(rotation.getCurrentAngle());
		missile.getMovement().setVelocity(missile.getMovement().getMaxVelocity());
		missiles.add(missile);
		lastMissileFiredTime = System.nanoTime();
		return missile;
	}

	public boolean canFireMissile() {
		return lastMissileFiredTime + (1D / missilesPerSecond * 1000000000D) < System.nanoTime() && health > 0;
	}

	public int getMissileDamage() {
		return missileDamage;
	}

	public void setMissileDamage(int missileDamage) {
		this.missileDamage = missileDamage;
	}

	public double getNaturalDeceleration() {
		return naturalDeceleration;
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void damage(int health) {
		this.health -= health;
	}
}

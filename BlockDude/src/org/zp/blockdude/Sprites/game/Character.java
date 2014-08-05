package org.zp.blockdude.sprites.game;

import org.zp.blockdude.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Date: 7/25/2014
 * Time: 12:18 AM
 */
public abstract class Character extends Sprite {
	protected final LinkedList<Missile> missiles;
	private final Color color;
	protected double lastMissileFiredTime;
	protected double missilesPerSecond;
    protected int missileDamage;
	protected int health;
	private BufferedImage image;

	public Character(int size, Color color) {
		this.color = color;
		createSprite(size);
		movement.setAcceleration(200);
		movement.setMaxSpeed(250);
		movement.setDeceleration(-200);
		rotation.setSpeed(Math.PI);
		renderer.setSprite(image);
		missiles = new LinkedList<Missile>();
		lastMissileFiredTime = System.nanoTime();
		missilesPerSecond = 3;
        missileDamage = 100;
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
		double x0 = (renderer.getBounds().getBounds().getX() + renderer.getBounds().getBounds().getWidth() / 2) -
				(missile.getRenderer().getBounds().getBounds().getWidth() / 2);
		double y0 = renderer.getBounds().getBounds().getY() + renderer.getBounds().getBounds().getHeight() / 2 -
				(missile.getRenderer().getBounds().getBounds().getHeight() / 2);
		double x1 = x0 + 35;
		double y1 = y0 - (missile.getRenderer().getBounds().getBounds().getHeight() / 2);
		Point rotatedPoint = getRotation().rotatePoint(x1, y1, x0, y0, theta);
		missile.getMovement().setLocation(rotatedPoint.getX(), rotatedPoint.getY());
		missile.getRotation().setCurrentOrientation(getRotation().getCurrentOrientation());
		missile.getMovement().setAngle(missile.getRotation().getCurrentOrientation());
		missile.getMovement().setSpeed(missile.getMovement().getMaxSpeed());
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

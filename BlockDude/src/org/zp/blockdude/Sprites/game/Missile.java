package org.zp.blockdude.sprites.game;

import org.zp.blockdude.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 7/25/2014
 * Time: 12:20 AM
 */
public class Missile extends Sprite {
	private final long MAX_AGE = 1250000000L;
	private final Color color;
	private BufferedImage image;
	private long age;
	private double maxDamage;

	public Missile(final Color color, final double maxDamage) {
		this.color = color;
		this.age = 0;
		this.maxDamage = maxDamage;
		createSprite();
		renderer.setSprite(image);
		movement.setAcceleration(200);
		movement.setMaxSpeed(325);
		movement.setNaturalDeceleration(-200);
	}

	private void createSprite() {
		image = new BufferedImage(20, 5, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, 20, 5);
		g.dispose();
	}

	public double getDamage() {
		return (maxDamage * age / MAX_AGE);
	}

	public void age(long delta) {
		age += delta;
		renderer.setOpacity(1 - (1F * age / MAX_AGE));
	}

	public boolean checkDecay() {
		return age > MAX_AGE;
	}
}

package org.zp.blockdude.sprites;

import org.zp.gworks.gui.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 7/25/2014
 * Time: 12:20 AM
 */
public class Missile extends Sprite {
	//TODO: upgradable
	private static final long MAX_AGE = 5 * 100000000L;
	private static final int WIDTH = 20;
	private static final int HEIGHT = 5;
	private final Color color;
	private BufferedImage image;
	private long age;
	private double maxDamage;

	public Missile(final Color color, final double maxDamage) {
		this.color = color;
		this.age = 0;
		this.maxDamage = maxDamage;
		this.getMovement().setMaxVelocity(750);
		createSprite();
		renderer.setSprite(image);
	}

	private void createSprite() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, WIDTH, HEIGHT);
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

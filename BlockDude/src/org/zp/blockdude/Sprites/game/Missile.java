package org.zp.blockdude.sprites.game;

import org.zp.blockdude.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 7/25/2014
 * Time: 12:20 AM
 */
public class Missile extends Sprite {
	private final Color color;
	private BufferedImage image;

	public Missile(final Color color) {
		this.color = color;
		createSprite();
		renderer.setSprite(image);
		movement.setAcceleration(200);
		movement.setMaxSpeed(300);
		movement.setDeceleration(-200);
	}

	private void createSprite() {
		image = new BufferedImage(20, 5, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, 20, 5);
		g.dispose();
	}
}

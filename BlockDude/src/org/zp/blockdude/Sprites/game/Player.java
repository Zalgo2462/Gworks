package org.zp.blockdude.sprites.game;

import org.zp.blockdude.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Sprite {
	private BufferedImage sprite;

	public Player() {
		setSpeed(100);
		createSprite();
	}

	private void createSprite() {
		sprite = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
		Graphics g = sprite.getGraphics();
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 25, 25);
		g.dispose();
	}

	public BufferedImage getSprite() {
		return sprite;
	}
}

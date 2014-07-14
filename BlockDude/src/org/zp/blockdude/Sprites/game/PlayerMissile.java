package org.zp.blockdude.sprites.game;

import org.zp.blockdude.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerMissile extends Sprite {
	private BufferedImage sprite;

	public PlayerMissile() {
		setSpeed(16);
		createSprite();
	}

	private void createSprite() {
		sprite = new BufferedImage(10, 5, BufferedImage.TYPE_INT_ARGB);
		Graphics g = sprite.getGraphics();
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 10, 5);
		g.dispose();
	}

	public BufferedImage getSprite() {
		return sprite;
	}
}

package org.zp.blockdude.sprites.game;

import org.zp.blockdude.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Sprite {
	private BufferedImage sprite;

	public Enemy(final int size, final int speed) {
		createSprite(size);
		setSpeed(speed);
	}

	private void createSprite(final int size) {
		Dimension d = new Dimension(size, size);
		if(d.getWidth() <= 0 || d.getHeight() <= 0)
			d.setSize(5, 5);
		sprite = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = sprite.getGraphics();
		g.setColor(Color.RED);
		g.fillRect(0, 0, d.width, d.height);
		g.dispose();
	}

	public BufferedImage getSprite() {
		return sprite;
	}
}

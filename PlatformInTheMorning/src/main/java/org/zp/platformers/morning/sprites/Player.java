package org.zp.platformers.morning.sprites;

import org.zp.gworks.gui.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 8/29/2015
 * Time: 6:26 PM
 */
public class Player extends Sprite {
	public Player() {
		getRenderer().setSprite(createSprite());
	}

	private BufferedImage createSprite() {
		BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.GREEN);
		g.fillOval(0, 0, 100, 100);
		g.dispose();
		return image;
	}
}

package org.zp.platformers.morning.sprites;

import org.zp.gworks.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Date: 8/30/2015
 * Time: 1:59 PM
 */
public class Platform extends Sprite {

	public Platform(int x, int y, int width, int height) {
		getMovement().setLocation(x, y);
		getRenderer().setSprite(createSprite(width, height));
	}

	private BufferedImage createSprite(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.dispose();
		return image;
	}
}

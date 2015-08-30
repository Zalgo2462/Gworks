package org.zp.platformers.morning.sprites.collisions;

import org.zp.gworks.gui.sprites.Sprite;

import java.awt.*;

/**
 * Date: 8/30/2015
 * Time: 12:25 PM
 */
class TestSprite extends Sprite {
	TestSprite(int x, int y, int width, int height) {
		this.getMovement().setLocation(x, y);
		this.getMovement().setCollisionArea(new Rectangle(width, height));
	}
}

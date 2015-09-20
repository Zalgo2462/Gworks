package org.zp.platformers.morning.sprites.collisions;

import org.zp.gworks.sprites.Sprite;
import org.zp.gworks.sprites.movement.movement2d.Movement2D;

import java.awt.*;

/**
 * Date: 8/30/2015
 * Time: 12:25 PM
 */
class TestSprite extends Sprite<Movement2D> {
	TestSprite(int x, int y, int width, int height) {
		super(Movement2D.class);
		this.getMovement().setLocation(x, y);
		this.getMovement().setCollisionArea(new Rectangle(width, height));
	}
}

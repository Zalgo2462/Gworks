package org.zp.platformers.morning.sprites.collisions;

import org.zp.gworks.gui.sprites.Sprite;

import java.util.LinkedList;

/**
 * Date: 8/29/2015
 * Time: 6:33 PM
 */
public class DynamicSpriteList {
	private LinkedList<Sprite> sprites;

	public void addSprite(Sprite s) {
		sprites.add(s);
	}

	public boolean removeSprite(Sprite s) {
		return sprites.remove(s);
	}

	public Sprite getCollided(Sprite s) {
		return Collider.checkForCollision(s, sprites);
	}

	public Double getCollisionAngle(Sprite s1, Sprite s2) {
		return Collider.getAngleIfCollision(s1, s2);
	}
}

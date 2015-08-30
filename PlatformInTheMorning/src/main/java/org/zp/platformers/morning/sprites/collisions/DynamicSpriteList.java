package org.zp.platformers.morning.sprites.collisions;

import org.zp.gworks.gui.sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Date: 8/29/2015
 * Time: 6:33 PM
 */
public class DynamicSpriteList {
	private LinkedList<Sprite> sprites;

	public DynamicSpriteList() {
		this.sprites = new LinkedList<Sprite>();
	}

	public void addSprite(Sprite s) {
		sprites.add(s);
	}

	public boolean removeSprite(Sprite s) {
		return sprites.remove(s);
	}

	public void removeAllSprites() {
		sprites.clear();
	}


	public Sprite getFirstCollision(Sprite s) {
		return Collider.getFirstCollision(s, sprites);
	}

	public Sprite[] getAllCollisions(Sprite s) {
		ArrayList<Sprite> collisions = new ArrayList<Sprite>(sprites.size() / 4);
		for (Sprite poss : sprites) {
			if (Collider.checkForCollision(s, poss))
				collisions.add(poss);
		}
		return collisions.toArray(new Sprite[collisions.size()]);
	}

	public Sprite[] getAllSprites() {
		return sprites.toArray(new Sprite[sprites.size()]);
	}

	public Sprite[] getAllSpritesInBounds(Shape shape) {
		ArrayList<Sprite> arrayList = new ArrayList<Sprite>();
		for (Sprite s : sprites) {
			if (Collider.testIntersection(shape, s.getRotation().getRotatedCollisionArea())) {
				arrayList.add(s);
			}
		}
		return arrayList.toArray(new Sprite[arrayList.size()]);
	}

	public Double getCollisionAngle(Sprite s1, Sprite s2) {
		return Collider.getAngleIfCollision(s1, s2);
	}
}

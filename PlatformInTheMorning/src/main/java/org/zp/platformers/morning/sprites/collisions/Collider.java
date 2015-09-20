package org.zp.platformers.morning.sprites.collisions;

import org.zp.gworks.sprites.Sprite;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Collection;

/**
 * Date: 8/29/2015
 * Time: 6:48 PM
 */
class Collider {
	public static Double getAngleIfCollision(Sprite sprite, Collection<Sprite> sprites) {
		for (Sprite s : sprites) {
			if (!s.equals(sprite)) {
				Double theta = getAngleIfCollision(sprite, s);
				if (theta != null) {
					return theta;
				}
			}
		}
		return null;
	}

	public static Double getAngleIfCollision(Sprite sprite1, Sprite sprite2) {
		if (sprite1.equals(sprite2)) {
			return null;
		}

		Area area = intersectSprites(sprite1, sprite2);
		if (area.isEmpty()) {
			return null;
		}

		double x = area.getBounds().getX();
		double width = area.getBounds().getWidth();
		double y = area.getBounds().getY();
		double height = area.getBounds().getHeight();

		return sprite1.getMovement().angleTo(x + width / 2, y + height / 2);
	}

	public static Sprite getFirstCollision(Sprite sprite, Collection<Sprite> sprites) {
		for (Sprite s : sprites) {
			if (!s.equals(sprite) && testIntersection(sprite, s)) {
				return s;
			}
		}
		return null;
	}

	public static boolean checkForCollision(Sprite sprite1, Sprite sprite2) {
		return !sprite1.equals(sprite2) && testIntersection(sprite1, sprite2);
	}

	public static boolean testIntersection(Sprite sprite1, Sprite sprite2) {
		return !intersectSprites(sprite1, sprite2).isEmpty();
	}

	public static boolean testIntersection(Shape s, Shape s2) {
		return !intersectShapes(s, s2).isEmpty();
	}

	public static Area intersectShapes(Shape s, Shape s2) {
		Area areaA = new Area(s);
		areaA.intersect(new Area(s2));
		return areaA;
	}

	public static Area intersectSprites(Sprite sprite1, Sprite sprite2) {
		return intersectShapes(
				sprite1.getRotation().getRotatedCollisionArea(),
				sprite2.getRotation().getRotatedCollisionArea());
	}
}

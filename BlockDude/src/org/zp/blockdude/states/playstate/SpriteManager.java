package org.zp.blockdude.states.playstate;

import org.zp.blockdude.sprites.Sprite;
import org.zp.gworks.logic.GState.GMutableState;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Collection;
import java.util.LinkedList;

public class SpriteManager {
	private GMutableState state;
	private LinkedList<Sprite> sprites;

	public SpriteManager(GMutableState state) {
		this.state = state;
		sprites = new LinkedList<Sprite>();
	}

	public boolean registerSprite(Sprite sprite) {
		return sprites.add(sprite) && state.addGRenderListener(sprite.getRenderer());
	}

	public boolean unregisterSprite(Sprite sprite) {
		return sprites.remove(sprite) && state.removeGRenderListener(sprite.getRenderer());
	}

	public enum PLAY_AREA_EDGE {
		TOP(
				PlayState.UI_CONSTANTS.PLAY_AREA_LEFT,
				PlayState.UI_CONSTANTS.PLAY_AREA_TOP - 1,
				PlayState.UI_CONSTANTS.PLAY_AREA_RIGHT,
				1
		),
		RIGHT(
				PlayState.UI_CONSTANTS.PLAY_AREA_RIGHT,
				PlayState.UI_CONSTANTS.PLAY_AREA_TOP,
				1,
				PlayState.UI_CONSTANTS.PLAY_AREA_BOTTOM
		),
		BOTTOM(
				PlayState.UI_CONSTANTS.PLAY_AREA_LEFT,
				PlayState.UI_CONSTANTS.PLAY_AREA_BOTTOM,
				PlayState.UI_CONSTANTS.PLAY_AREA_RIGHT,
				1
		),
		LEFT(
				PlayState.UI_CONSTANTS.PLAY_AREA_LEFT - 1,
				PlayState.UI_CONSTANTS.PLAY_AREA_TOP,
				1,
				PlayState.UI_CONSTANTS.PLAY_AREA_BOTTOM
		),
		NONE(0, 0, 0, 0);
		private Area area;

		PLAY_AREA_EDGE(int x, int y, int w, int h) {
			this.area = new Area(new Rectangle(x, y, w, h));
		}

		public Area getArea() {
			return area;
		}
	}

	public Double getAngleIfCollisionWithEdge(Sprite sprite, PLAY_AREA_EDGE edge) {
		Area area1 = new Area(sprite.getRenderer().getBounds());
		Area area2 = edge.getArea();
		area1.intersect(area2);
		if (area1.isEmpty()) {
			return null;
		}

		double x = area1.getBounds().getX();
		double width = area1.getBounds().getWidth();
		double y = area1.getBounds().getY();
		double height = area1.getBounds().getHeight();

		return sprite.getMovement().getAngleTo(x + width / 2, y + height / 2);
	}

	public PLAY_AREA_EDGE checkForEdgeCollision(Sprite sprite) {
		for (PLAY_AREA_EDGE edge : PLAY_AREA_EDGE.values()) {
			if (getAngleIfCollisionWithEdge(sprite, edge) != null) {
				return edge;
			}
		}
		return PLAY_AREA_EDGE.NONE;
	}

	public Double getAngleIfCollision(Sprite sprite, Collection<Sprite> sprites) {
		for(Sprite s : sprites) {
			if (!s.equals(sprite)) {
				Double theta = getAngleIfCollision(sprite, s);
				if (theta != null) {
					return theta;
				}
			}
		}
		return null;
	}

	public Double getAngleIfCollision(Sprite sprite1, Sprite sprite2) {
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

		return sprite1.getMovement().getAngleTo(x + width / 2, y + height / 2);
	}

	public Sprite checkForCollision(Sprite sprite) {
		return checkForCollision(sprite, sprites);
	}

	public Sprite checkForCollision(Sprite sprite, Collection<Sprite> sprites) {
		for(Sprite s : sprites) {
			if (!s.equals(sprite) && testIntersection(sprite, s)) {
				return s;
			}
		}
		return null;
	}

	public boolean checkForCollision(Sprite sprite1, Sprite sprite2) {
		return !sprite1.equals(sprite2) && testIntersection(sprite1, sprite2);
	}

	private boolean testIntersection(Sprite sprite1, Sprite sprite2) {
		return !intersectSprites(sprite1, sprite2).isEmpty();
	}

	private Area intersectSprites(Sprite sprite1, Sprite sprite2) {
		Area areaA = new Area(sprite1.getRenderer().getBounds());
		areaA.intersect(new Area(sprite2.getRenderer().getBounds()));
		return areaA;
	}
}

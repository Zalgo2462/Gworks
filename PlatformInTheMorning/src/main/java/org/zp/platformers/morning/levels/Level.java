package org.zp.platformers.morning.levels;

import org.zp.gworks.gui.sprites.Sprite;
import org.zp.platformers.morning.sprites.collisions.DynamicSpriteList;
import org.zp.platformers.morning.sprites.collisions.StaticSpriteTree;
import org.zp.platformers.morning.states.PlayState;

import java.awt.*;

/**
 * Date: 8/29/2015
 * Time: 6:25 PM
 */
public abstract class Level {
	protected StaticSpriteTree staticSprites;
	protected DynamicSpriteList dynamicSprites;
	protected PlayState playState;
	private int width;
	private int height;

	protected Level(PlayState playState) {
		this.playState = playState;
		this.staticSprites = new StaticSpriteTree();
		this.dynamicSprites = new DynamicSpriteList();
	}

	public int getWidth() {
		return width;
	}

	protected void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	protected void setHeight(int height) {
		this.height = height;
	}

	public abstract void load();

	public abstract void unload();

	public Sprite[] getSpritesInBounds(Shape s) {
		Sprite[] dynamics = dynamicSprites.getAllSpritesInBounds(s);
		Sprite[] statics = staticSprites.getAllSpritesInBounds(s);
		Sprite[] toReturn = new Sprite[dynamics.length + statics.length];
		System.arraycopy(dynamics, 0, toReturn, 0, dynamics.length);
		System.arraycopy(statics, 0, toReturn, dynamics.length, statics.length);
		return toReturn;
	}
}

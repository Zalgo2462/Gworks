package org.zp.platformers.morning.levels;

import org.zp.platformers.morning.sprites.collisions.DynamicSpriteList;
import org.zp.platformers.morning.sprites.collisions.StaticSpriteTree;
import org.zp.platformers.morning.states.PlayState;

/**
 * Date: 8/29/2015
 * Time: 6:25 PM
 */
public abstract class Level {
	protected StaticSpriteTree staticEntities;
	protected DynamicSpriteList dynamicSprites;
	protected PlayState playState;
	private int width;
	private int height;

	protected Level(PlayState playState) {
		this.playState = playState;
		this.staticEntities = new StaticSpriteTree();
		this.dynamicSprites = new DynamicSpriteList();
	}

	protected void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}

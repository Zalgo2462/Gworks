package org.zp.platformers.morning.levels;

import org.zp.platformers.morning.states.PlayState;

/**
 * Date: 8/29/2015
 * Time: 6:41 PM
 */
public class LevelOne extends Level {
	private static final int WIDTH = 2000;
	private static final int HEIGHT = 2000;

	public LevelOne(PlayState playState) {
		super(playState);
		setWidth(WIDTH);
		setHeight(HEIGHT);
	}
}

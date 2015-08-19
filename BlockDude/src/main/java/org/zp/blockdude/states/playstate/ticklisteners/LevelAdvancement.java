package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.Level;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

/**
 * Date: 8/1/2014
 * Time: 3:44 PM
 */
public class LevelAdvancement implements GTickListener {
	private PlayState playState;

	public LevelAdvancement(PlayState playState) {
		this.playState = playState;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		if (playState.getEnemies().isEmpty()) {
			playState.initLevel(Level.values()[playState.getCurrentLevel().ordinal() + 1]);
		}
	}
}

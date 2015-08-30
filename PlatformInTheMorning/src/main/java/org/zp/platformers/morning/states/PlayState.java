package org.zp.platformers.morning.states;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GState.GMutableState;
import org.zp.platformers.morning.levels.Level;
import org.zp.platformers.morning.sprites.Player;

/**
 * Date: 8/29/2015
 * Time: 6:21 PM
 */
public class PlayState extends GMutableState {
	Player player;
	Level level;

	public PlayState(GCanvas canvas) {
		super(canvas);
	}

	private void loadLevel(Level level) {

	}

	private void unloadLevel() {

	}

	private void loadPlayer() {

	}

	private void unloadPlayer() {

	}

}

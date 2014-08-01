package org.zp.blockdude.states.gameover;

import org.zp.blockdude.states.gameover.renderlisteners.GameOverRenderer;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;

/**
 * Date: 8/1/2014
 * Time: 2:22 PM
 */
public class GameOverState extends GImmutableState {
	private int score;

	public GameOverState(int score) {
		this.score = score;
		setRenderStrategies(new GRenderListener[]{new GameOverRenderer(this)});
	}

	public int getScore() {
		return score;
	}
}
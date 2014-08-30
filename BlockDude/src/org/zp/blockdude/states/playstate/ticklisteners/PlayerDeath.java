package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.Player;
import org.zp.blockdude.states.menus.gameover.GameOverState;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

import static org.zp.blockdude.GameFrame.DIMENSION;

/**
 * Date: 8/1/2014
 * Time: 1:49 PM
 */
public class PlayerDeath implements GTickListener {

	private final PlayState playState;
	private final Player player;

	public PlayerDeath(PlayState playState, Player player) {
		this.playState = playState;
		this.player = player;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		if (player.getHealth() <= 0 && player.getHealthRenderer().getHealth() <= 0) {
			player.setLives(player.getLives() - 1);
			if (player.getLives() > 0) {
				player.setHealth(100);
				player.getMovement().setLocation(
						DIMENSION.width / 2 - player.getRenderer().getSprite().getWidth() / 2,
						DIMENSION.height / 2 - player.getRenderer().getSprite().getHeight() / 2
				);
			} else {
				canvas.removeState(playState);
				canvas.addState(new GameOverState(playState.getCanvas(), playState.getScore()));
			}
		}
	}
}

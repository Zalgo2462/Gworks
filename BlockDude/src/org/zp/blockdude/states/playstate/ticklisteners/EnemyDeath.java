package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.game.Enemy;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

/**
 * Date: 8/1/2014
 * Time: 1:48 PM
 */
public class EnemyDeath implements GTickListener {

	private final PlayState playState;
	private final Enemy enemy;

	public EnemyDeath(PlayState playState, Enemy enemy) {
		this.playState = playState;
		this.enemy = enemy;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		if (enemy.getHealth() <= 0) {
			enemy.getRenderer().setRendered(false);
			playState.removeTickListener(enemy.getEnemyMovement());
			enemy.getEnemyMissiles().notifyRemove();
			playState.removeTickListener(enemy.getEnemyDeath());
			playState.getSpriteManager().unregisterSprite(enemy);
			playState.getEnemies().remove(enemy);
		}
	}
}

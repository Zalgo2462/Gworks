package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.game.Enemy;
import org.zp.blockdude.sprites.game.Missile;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.SpriteManager;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;
import java.util.Iterator;

/**
 * Date: 7/25/2014
 * Time: 1:32 AM
 */
public class EnemyMissiles implements GTickListener {
	private final PlayState playState;
	private final Enemy enemy;
	private final SpriteManager spriteManager;
	private boolean stop;

	public EnemyMissiles(final PlayState playState, final Enemy enemy) {
		this.playState = playState;
		this.enemy = enemy;
		this.spriteManager = playState.getSpriteManager();
		this.stop = false;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		final Point p = playState.getPlayer().getMovement().getLocation();
		final double angleToPlayer = Math.abs(enemy.getRotation().getAngleTo(p.getX(), p.getY()));
		if (angleToPlayer < Math.PI / 16D && enemy.canFireMissile()) {
			Missile missile = enemy.fireMissile();
			spriteManager.registerSprite(missile);
			missile.getRenderer().setRendered(true);
		}
		for (Iterator<Missile> iterator = enemy.getMissiles().iterator(); iterator.hasNext(); ) {
			Missile missile = iterator.next();
			missile.getMovement().accelerate(delta);
			missile.getMovement().move(
					missile.getMovement().getXMovement() * missile.getMovement().getSpeed() * delta / 1000000000F,
					missile.getMovement().getYMovement() * missile.getMovement().getSpeed() * delta / 1000000000F
			);
			SpriteManager.PLAY_AREA_EDGE canvasEdge = spriteManager.checkForEdgeCollision(missile);
			if (canvasEdge != SpriteManager.PLAY_AREA_EDGE.NONE) {
				missile.getRenderer().setRendered(false);
				spriteManager.unregisterSprite(missile);
				iterator.remove();
			}
			if (spriteManager.checkForCollision(missile, playState.getPlayer())) {
				missile.getRenderer().setRendered(false);
				spriteManager.unregisterSprite(missile);
				iterator.remove();
				playState.getPlayer().damage(enemy.getMissileDamage());
			}
		}
		if (stop && enemy.getMissiles().size() == 0) {
			System.out.println("Called");
			playState.removeGTickListener(this);
		}
	}

	public void notifyRemove() {
		stop = true;
	}
}

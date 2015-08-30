package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.Enemy;
import org.zp.blockdude.sprites.Missile;
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
		final Rectangle r = playState.getPlayer().getRotation().getRotatedBounds().getBounds();
		final double angleToPlayer = Math.abs(enemy.getRotation().angleTo(r.getCenterX(), r.getCenterY()));
		if (angleToPlayer < Math.PI / 16D && enemy.canFireMissile()) {
			Missile missile = enemy.fireMissile(enemy.getRotation().getCurrentOrientation());
			spriteManager.registerSprite(missile);
			missile.getRenderer().setRendered(true);
		}
		for (Iterator<Missile> iterator = enemy.getMissiles().iterator(); iterator.hasNext(); ) {
			Missile missile = iterator.next();

			missile.age(delta);
			if (missile.checkDecay()) {
				missile.getRenderer().setRendered(false);
				spriteManager.unregisterSprite(missile);
				iterator.remove();
				continue;
			}


			missile.getMovement().accelerate(delta);
			missile.getMovement().move(
					missile.getMovement().getXMovement() * missile.getMovement().getVelocity() * delta / 1000000000F,
					missile.getMovement().getYMovement() * missile.getMovement().getVelocity() * delta / 1000000000F
			);

			SpriteManager.PlayAreaEdge canvasEdge = spriteManager.checkForEdgeCollision(missile);
			switch (canvasEdge) {
				case TOP:
					missile.getMovement().setLocation(
							missile.getMovement().getLocation().getX(),
							PlayState.UI_CONSTANTS.PLAY_AREA_BOTTOM -
									missile.getRotation().getRotatedBounds().getBounds().getHeight() - 5
					);
					break;
				case BOTTOM:
					missile.getMovement().setLocation(
							missile.getMovement().getLocation().getX(),
							PlayState.UI_CONSTANTS.PLAY_AREA_TOP + 5
					);
					break;
				case RIGHT:
					missile.getMovement().setLocation(
							PlayState.UI_CONSTANTS.PLAY_AREA_LEFT + 5,
							missile.getMovement().getLocation().getY()
					);
					break;
				case LEFT:
					missile.getMovement().setLocation(
							PlayState.UI_CONSTANTS.PLAY_AREA_RIGHT -
									missile.getRotation().getRotatedBounds().getBounds().getWidth() - 5,
							missile.getMovement().getLocation().getY()
					);
					break;
			}

			if (spriteManager.checkForCollision(missile, playState.getPlayer())) {
				missile.getRenderer().setRendered(false);
				spriteManager.unregisterSprite(missile);
				iterator.remove();
				playState.getPlayer().damage(Math.round(Math.round(missile.getDamage())));
			}
		}
		if (stop && enemy.getMissiles().size() == 0) {
			playState.removeTickListener(this);
		}
	}

	public void notifyRemove() {
		stop = true;
	}
}

package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.Sprite;
import org.zp.blockdude.sprites.game.Enemy;
import org.zp.blockdude.sprites.game.Missile;
import org.zp.blockdude.sprites.game.Player;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.SpriteManager;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.input.GKeyListener;
import org.zp.gworks.logic.GTickListener;

import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 * Date: 7/18/2014
 * Time: 5:10 PM
 */
public class PlayerMissiles implements GTickListener {
	private final PlayState playState;
	private final Player player;
	private final SpriteManager spriteManager;
	private final GKeyListener keyListener;

	public PlayerMissiles(final PlayState playState, Player player) {
		this.playState = playState;
		this.player = player;
		this.spriteManager = playState.getSpriteManager();
		this.keyListener = playState.getCanvas().getGKeyListener();
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		if (keyListener.getPressedKeyCodes().contains(KeyEvent.VK_SPACE) && player.canFireMissile()) {
			Missile missile = player.fireMissile(player.getRotation().getCurrentOrientation());
			spriteManager.registerSprite(missile);
			missile.getRenderer().setRendered(true);
		}
		for (Iterator<Missile> iterator = player.getMissiles().iterator(); iterator.hasNext(); ) {
			Missile missile = iterator.next();
			missile.getMovement().accelerate(delta);
			missile.getMovement().move(
					missile.getMovement().getXMovement() * missile.getMovement().getSpeed() * delta / 1000000000F,
					missile.getMovement().getYMovement() * missile.getMovement().getSpeed() * delta / 1000000000F
			);
			SpriteManager.PlayAreaEdge canvasEdge = spriteManager.checkForEdgeCollision(missile);
			if (canvasEdge != SpriteManager.PlayAreaEdge.NONE) {
				missile.getRenderer().setRendered(false);
				spriteManager.unregisterSprite(missile);
				iterator.remove();
				return;
			}
			Sprite collided = spriteManager.checkForCollision(missile, playState.getEnemies());
			if (collided != null) {
				Enemy e = ((Enemy) collided);
				missile.getRenderer().setRendered(false);
				spriteManager.unregisterSprite(missile);
				iterator.remove();
				e.damage(player.getMissileDamage());
				playState.setScore(playState.getScore() + player.getMissileDamage());
			}
		}
	}
}

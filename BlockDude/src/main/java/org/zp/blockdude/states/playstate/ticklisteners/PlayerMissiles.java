package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.Enemy;
import org.zp.blockdude.sprites.Missile;
import org.zp.blockdude.sprites.Player;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.SpriteManager;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.input.GKeyListener;
import org.zp.gworks.gui.sprites.Sprite;
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

			missile.age(delta);
			if (missile.checkDecay()) {
				missile.getRenderer().setRendered(false);
				spriteManager.unregisterSprite(missile);
				iterator.remove();
				continue;
			}

			missile.getMovement().accelerate(delta);
			missile.getMovement().move(
					missile.getMovement().getXMovement() * missile.getMovement().getSpeed() * delta / 1000000000F,
					missile.getMovement().getYMovement() * missile.getMovement().getSpeed() * delta / 1000000000F
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
									missile.getRotation().getRotatedBounds().getBounds().getWidth() + 5,
							missile.getMovement().getLocation().getY()
					);
					break;
			}

			Sprite collided = spriteManager.checkForCollision(missile, playState.getEnemies());
			if (collided != null) {
				Enemy e = ((Enemy) collided);
				missile.getRenderer().setRendered(false);
				spriteManager.unregisterSprite(missile);
				iterator.remove();
				e.damage(Math.round(Math.round(missile.getDamage())));
				playState.setScore(playState.getScore() + Math.round(Math.round(missile.getDamage())));
			}
		}
	}
}

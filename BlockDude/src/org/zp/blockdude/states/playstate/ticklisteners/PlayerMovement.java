package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.Sprite;
import org.zp.blockdude.sprites.game.Enemy;
import org.zp.blockdude.sprites.game.Player;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.SpriteManager;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.input.GKeyListener;
import org.zp.gworks.logic.GTickListener;

import java.awt.event.KeyEvent;

public class PlayerMovement implements GTickListener {
	private final PlayState playState;
	private Player player;
	private GKeyListener keyListener;
	private long blockInputTime;

	public PlayerMovement(PlayState playState, Player player) {
		this.playState = playState;
		this.player = player;
		this.keyListener = playState.getCanvas().getGKeyListener();
		this.blockInputTime = System.currentTimeMillis();
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		if (System.currentTimeMillis() > blockInputTime) {
			for (Integer keyCode : keyListener.getPressedKeyCodes()) {
				switch (keyCode) {
					case KeyEvent.VK_W:
					case KeyEvent.VK_UP:
						player.getMovement().setAngle(player.getRotation().getCurrentOrientation());
						player.getMovement().accelerate(delta);
						break;
					case KeyEvent.VK_D:
					case KeyEvent.VK_RIGHT:
						player.getMovement().setAngle(player.getRotation().getCurrentOrientation());
						player.getRotation().setClockwise(true);
						player.getRotation().setMoving(true);
						break;
					case KeyEvent.VK_S:
					case KeyEvent.VK_DOWN:
						player.getMovement().setAngle(player.getRotation().getCurrentOrientation());
						player.getMovement().decelerate(delta);
						break;
					case KeyEvent.VK_A:
					case KeyEvent.VK_LEFT:
						player.getMovement().setAngle(player.getRotation().getCurrentOrientation());
						player.getRotation().setClockwise(false);
						player.getRotation().setMoving(true);
						break;
				}
			}
		}

		if (!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_UP) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_DOWN) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_W) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_S)) {
			player.getMovement().naturallyDecelerate(delta);
		}

		if (!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_LEFT) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_RIGHT) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_A) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_D)) {
			player.getRotation().setMoving(false);
		}

		player.getMovement().move(
				player.getMovement().getXMovement() * player.getMovement().getSpeed() * delta / 1000000000D,
				player.getMovement().getYMovement() * player.getMovement().getSpeed() * delta / 1000000000D
		);

		if (player.getRotation().isMoving()) {
			double dTheta = player.getRotation().getSpeed() * delta / 1000000000D;
			if (!player.getRotation().isClockwise()) {
				dTheta *= -1;
			}
			player.getRotation().rotate(dTheta);
		}

		Sprite collided = playState.getSpriteManager().checkForCollision(player, playState.getEnemies());
		if (collided != null) {
			Enemy e = (Enemy) collided;
			Double theta = playState.getSpriteManager().getAngleIfCollision(player, e);
			player.getMovement().setAngle(theta + Math.PI);
			e.getMovement().setAngle(theta);
			if (player.getMovement().getSpeed() < 10) {
				player.getMovement().setSpeed(10);
			}
			if (e.getMovement().getSpeed() < 10) {
				player.getMovement().setSpeed(10);
			}
			player.damage(1);
			e.damage(1);
			blockInput(150);
			e.getEnemyMovement().blockRotation(150);
		}

		SpriteManager.PlayAreaEdge canvasEdge = playState.getSpriteManager().checkForEdgeCollision(player);
		switch (canvasEdge) {
			case TOP:
				player.getMovement().setLocation(
						player.getMovement().getLocation().getX(),
						PlayState.UI_CONSTANTS.PLAY_AREA_BOTTOM -
								player.getRenderer().getBounds().getBounds().getHeight()
				);
				break;
			case BOTTOM:
				player.getMovement().setLocation(
						player.getMovement().getLocation().getX(),
						PlayState.UI_CONSTANTS.PLAY_AREA_TOP +
								player.getRenderer().getBounds().getBounds().getHeight()
				);
				break;
			case RIGHT:
				player.getMovement().setLocation(
						PlayState.UI_CONSTANTS.PLAY_AREA_LEFT +
								player.getRenderer().getBounds().getBounds().getWidth(),
						player.getMovement().getLocation().getY()
				);
				break;
			case LEFT:
				player.getMovement().setLocation(
						PlayState.UI_CONSTANTS.PLAY_AREA_RIGHT -
								player.getRenderer().getBounds().getBounds().getWidth(),
						player.getMovement().getLocation().getY()
				);
				break;
		}
	}

	public void blockInput(int milliseconds) {
		player.getRotation().setMoving(false);
		blockInputTime = System.currentTimeMillis() + milliseconds;
	}
}

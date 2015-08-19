package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.Enemy;
import org.zp.blockdude.sprites.Player;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.SpriteManager;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.input.GKeyListener;
import org.zp.gworks.gui.sprites.Sprite;
import org.zp.gworks.logic.GTickListener;

import java.awt.event.KeyEvent;

public class PlayerMovement implements GTickListener {
	private final PlayState playState;
	private Player player;
	private GKeyListener keyListener;

	public PlayerMovement(PlayState playState, Player player) {
		this.playState = playState;
		this.player = player;
		this.keyListener = playState.getCanvas().getGKeyListener();
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		checkInput(delta);

		move(delta);

		rotate(delta);

		checkForWallCollisions();

		checkForEnemyCollisions(delta);
	}

	private void checkInput(long delta) {
		for (Integer keyCode : keyListener.getPressedKeyCodes()) {
			switch (keyCode) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					player.getMovement().accelerate(delta);
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					player.getRotation().setClockwise(true);
					player.getRotation().setMoving(true);
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					player.getMovement().decelerate(delta);
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					player.getRotation().setClockwise(false);
					player.getRotation().setMoving(true);
					break;
			}
		}

		if (!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_UP) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_DOWN) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_W) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_S)) {
			player.getMovement().decelerateToZero(delta);
		}

		if (!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_LEFT) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_RIGHT) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_A) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_D)) {
			player.getRotation().setMoving(false);
		}
	}

	private void move(long delta) {
		player.getMovement().move(
				player.getMovement().getXMovement() * player.getMovement().getSpeed() * delta / 1000000000D,
				player.getMovement().getYMovement() * player.getMovement().getSpeed() * delta / 1000000000D
		);
	}

	private void rotate(long delta) {
		if (player.getRotation().isMoving()) {
			double dTheta = player.getRotation().getSpeed() * delta / 1000000000D;
			player.getRotation().rotate(dTheta);
			player.getMovement().setAngle(player.getRotation().getCurrentOrientation());
		}
	}

	private void checkForEnemyCollisions(long delta) {
		Sprite collided = playState.getSpriteManager().checkForCollision(player, playState.getEnemies());
		if (collided != null) {
			Enemy e = (Enemy) collided;
			Double theta = playState.getSpriteManager().getAngleIfCollision(player, e);
			double oldPlayerAngle = player.getMovement().getAngle();
			double oldEnemyAngle = e.getMovement().getAngle();
			player.damage(1);
			e.damage(1);
			do {
				player.getMovement().setAngle(theta + Math.PI);
				e.getMovement().setAngle(theta);
				player.getMovement().move(
						player.getMovement().getXMovement() * delta / 1000000000D,
						player.getMovement().getYMovement() * delta / 1000000000D
				);
				e.getMovement().move(
						e.getMovement().getXMovement() * delta / 1000000000D,
						e.getMovement().getYMovement() * delta / 1000000000D
				);
				collided = playState.getSpriteManager().checkForCollision(player, playState.getEnemies());
				theta = playState.getSpriteManager().getAngleIfCollision(player, e);
			} while (collided != null);
			player.getMovement().setAngle(oldPlayerAngle);
			e.getMovement().setAngle(oldEnemyAngle);
		}
	}

	private void checkForWallCollisions() {
		SpriteManager.PlayAreaEdge canvasEdge = playState.getSpriteManager().checkForEdgeCollision(player);
		switch (canvasEdge) {
			case TOP:
				player.getMovement().setLocation(
						player.getMovement().getLocation().getX(),
						PlayState.UI_CONSTANTS.PLAY_AREA_BOTTOM -
								player.getRotation().getRotatedBounds().getBounds().getHeight() - 5
				);
				break;
			case BOTTOM:
				player.getMovement().setLocation(
						player.getMovement().getLocation().getX(),
						PlayState.UI_CONSTANTS.PLAY_AREA_TOP + 5
				);
				break;
			case RIGHT:
				player.getMovement().setLocation(
						PlayState.UI_CONSTANTS.PLAY_AREA_LEFT + 5,
						player.getMovement().getLocation().getY()
				);
				break;
			case LEFT:
				player.getMovement().setLocation(
						PlayState.UI_CONSTANTS.PLAY_AREA_RIGHT -
								player.getRotation().getRotatedBounds().getBounds().getWidth() - 5,
						player.getMovement().getLocation().getY()
				);
				break;
		}
	}
}

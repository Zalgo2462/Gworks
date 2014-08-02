package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.GameFrame;
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
		this.keyListener = GameFrame.getCanvas().getGKeyListener();
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
						//player.getMovement().setAngle(player.getRotation().getCurrentOrientation() + Math.PI);
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
			player.getMovement().decelerateToZero(delta);
		}

		if (!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_LEFT) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_RIGHT) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_A) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_D)) {
			player.getRotation().setMoving(false);
		}

		Sprite collided = playState.getSpriteManager().checkForCollision(player, playState.getEnemies());
		if (collided != null) {
			Enemy e = (Enemy) collided;
			Double theta = playState.getSpriteManager().getAngleIfCollision(player, e);
			player.getMovement().setAngle(theta + Math.PI);
			e.getMovement().setAngle(theta);
			player.getMovement().setSpeed(300);
			e.getMovement().setSpeed(300);
			blockInput(100);
			e.getEnemyMovement().blockRotation(100);
		}

		SpriteManager.PLAY_AREA_EDGE canvasEdge = playState.getSpriteManager().checkForEdgeCollision(player);
		switch (canvasEdge) {
			case TOP:
			case BOTTOM:
				player.getMovement().setAngle(
						-playState.getSpriteManager().getAngleIfCollisionWithEdge(player, canvasEdge)
				);
				if (player.getMovement().getSpeed() < 100) {
					player.getMovement().setSpeed(100);
				}
				blockInput(100);
				break;
			case RIGHT:
			case LEFT:
				player.getMovement().setAngle(
						Math.PI - playState.getSpriteManager().getAngleIfCollisionWithEdge(player, canvasEdge)
				);
				if (player.getMovement().getSpeed() < 100) {
					player.getMovement().setSpeed(100);
				}
				blockInput(100);
				break;
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
	}

	public void blockInput(int milliseconds) {
		player.getRotation().setMoving(false);
		blockInputTime = System.currentTimeMillis() + milliseconds;
	}
}

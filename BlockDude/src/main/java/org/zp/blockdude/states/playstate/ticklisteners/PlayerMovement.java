package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.Enemy;
import org.zp.blockdude.sprites.Player;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.SpriteManager;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.input.GKeyListener;
import org.zp.gworks.logic.GTickListener;
import org.zp.gworks.sprites.Sprite;
import org.zp.gworks.sprites.movement.movement2d.forces.SimpleForce;

import java.awt.event.KeyEvent;

public class PlayerMovement implements GTickListener {
	private final PlayState playState;
	private Player player;
	private GKeyListener keyListener;
	private SimpleForce acceleration;
	private SimpleForce deceleration;
	private SimpleForce naturalDeceleration;

	public PlayerMovement(PlayState playState, Player player) {
		this.playState = playState;
		this.player = player;
		this.keyListener = playState.getCanvas().getGKeyListener();
		this.acceleration = new SimpleForce();
		this.deceleration = new SimpleForce();
		this.naturalDeceleration = new SimpleForce();

		player.getMovement().addForce(acceleration);
		player.getMovement().addForce(deceleration);
		player.getMovement().addForce(naturalDeceleration);
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		checkInput();

		rotate(delta);

		updateForces(delta);

		move(delta);

		checkForWallCollisions();

		checkForEnemyCollisions(delta);
	}

	private void checkInput() {
		for (Integer keyCode : keyListener.getPressedKeyCodes()) {
			switch (keyCode) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					acceleration.setActive(true);
					deceleration.setActive(false);
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					player.getRotation().setClockwise(true);
					player.getRotation().setMoving(true);
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					acceleration.setActive(false);
					deceleration.setActive(true);
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
			acceleration.setActive(false);
			deceleration.setActive(false);
		}

		if (!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_LEFT) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_RIGHT) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_A) &&
				!keyListener.getPressedKeyCodes().contains(KeyEvent.VK_D)) {
			player.getRotation().setMoving(false);
		}
	}

	private void updateForces(long delta) {
		acceleration.setForce(
				Math.cos(player.getRotation().getCurrentAngle()) * player.getAcceleration(),
				Math.sin(player.getRotation().getCurrentAngle()) * player.getAcceleration()
		);
		deceleration.setForce(
				Math.cos(player.getRotation().getCurrentAngle()) * player.getDeceleration(),
				Math.sin(player.getRotation().getCurrentAngle()) * player.getDeceleration()
		);
		naturalDeceleration.setForce(
				Math.cos(player.getMovement().getForwardAngle()) * player.getNaturalDeceleration(),
				Math.sin(player.getMovement().getForwardAngle()) * player.getNaturalDeceleration()
		);
		if (Math.abs(player.getMovement().getVelocity()) <= 0.000001) {
			naturalDeceleration.setActive(false);
		} else {
			naturalDeceleration.setActive(true);
		}
	}

	private void move(long delta) {
		player.getMovement().accelerate(delta);
		player.getMovement().move(
				player.getMovement().getXVelocity() * delta / 1000000000D,
				player.getMovement().getYVelocity() * delta / 1000000000D
		);
	}

	private void rotate(long delta) {
		if (player.getRotation().isMoving()) {
			double dTheta = player.getRotation().getVelocity() * delta / 1000000000D;
			player.getRotation().rotate(dTheta);
		}
	}

	private void checkForEnemyCollisions(long delta) {
		Sprite collided = playState.getSpriteManager().checkForCollision(player, playState.getEnemies());
		if (collided != null) {
			Enemy e = (Enemy) collided;
			player.damage(1);
			e.damage(1);
			while ((collided = playState.getSpriteManager().checkForCollision(player, playState.getEnemies())) != null) {
				e = (Enemy) collided;
				Double theta = playState.getSpriteManager().getAngleIfCollision(player, e);
				player.getMovement().move(
						Math.cos(theta + Math.PI) * delta / 1000000000D,
						Math.sin(theta + Math.PI) * delta / 1000000000D
				);
				e.getMovement().move(
						Math.cos(theta) * delta / 1000000000D,
						Math.sin(theta) * delta / 1000000000D
				);
			}
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

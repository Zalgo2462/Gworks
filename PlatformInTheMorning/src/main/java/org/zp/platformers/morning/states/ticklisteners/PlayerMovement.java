package org.zp.platformers.morning.states.ticklisteners;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.input.GKeyListener;
import org.zp.gworks.gui.sprites.Sprite;
import org.zp.gworks.logic.GTickListener;
import org.zp.platformers.morning.sprites.Player;
import org.zp.platformers.morning.sprites.collisions.StaticSpriteTree;
import org.zp.platformers.morning.states.PlayState;

import java.awt.event.KeyEvent;

//TODO:Force compositer
public class PlayerMovement implements GTickListener {
	private final PlayState playState;
	private Player player;
	private GKeyListener keyListener;
	private double x_accel;
	private double y_accel;
	private boolean moving;

	public PlayerMovement(PlayState playState, Player player) {
		this.playState = playState;
		this.player = player;
		this.keyListener = playState.getCanvas().getGKeyListener();
		x_accel = 0;
		y_accel = 0;
		moving = false;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		checkInput(delta);

		move(delta);

		checkForStaticCollisions(delta);

		checkForDynamicCollisions();
	}

	private void checkInput(long delta) {
		x_accel = 0;
		y_accel = 0;
		for (Integer keyCode : keyListener.getPressedKeyCodes()) {
			switch (keyCode) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					y_accel = -player.getMovement().getAcceleration();
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					x_accel = player.getMovement().getAcceleration();
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					y_accel = player.getMovement().getAcceleration();
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					x_accel = -player.getMovement().getAcceleration();
					break;
			}
		}
		moving = !(x_accel == 0 && y_accel == 0);
	}

	private void move(long delta) {
		if (moving) {
			player.getMovement().setAngle(Math.atan2(y_accel, x_accel));
			player.getMovement().accelerate(delta, Math.sqrt(x_accel * x_accel + y_accel * y_accel));
		} else {
			player.getMovement().decelerateToZero(delta);
		}
		player.getMovement().move(
				player.getMovement().getXMovement() * player.getMovement().getVelocity() * delta / 1000000000D,
				player.getMovement().getYMovement() * player.getMovement().getVelocity() * delta / 1000000000D
		);
	}


	private void checkForDynamicCollisions() {

	}

	private void checkForStaticCollisions(double delta) {
		StaticSpriteTree sst = playState.getLevel().getStaticSprites();
		Sprite collided;
		double oldAngle = player.getMovement().getAngle();
		while ((collided = sst.getFirstCollision(player)) != null) {
			Double theta = sst.getCollisionAngle(player, collided);
			player.getMovement().setAngle(theta + Math.PI);
			player.getMovement().move(
					player.getMovement().getXMovement() * delta / 1000000000D,
					player.getMovement().getYMovement() * delta / 1000000000D
			);
		}
		player.getMovement().setAngle(oldAngle);
	}
}

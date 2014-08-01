package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.GameFrame;
import org.zp.blockdude.sprites.Sprite;
import org.zp.blockdude.sprites.game.Character;
import org.zp.blockdude.sprites.game.Enemy;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.SpriteManager;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

import java.util.Random;

/**
 * Date: 7/18/2014
 * Time: 6:55 PM
 */
public class EnemyMovement implements GTickListener {
	private final PlayState playState;
	private final Enemy enemy;
	private final Random random;

	public EnemyMovement(final PlayState playState, final Enemy enemy) {
		this.playState = playState;
		this.enemy = enemy;
		this.random = new Random();
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		double x = 0;
		double y = 0;
		for (Sprite e : playState.getEnemies()) {
			double xDiff = enemy.getMovement().getLocation().getX() - e.getMovement().getLocation().getX();
			double yDiff = enemy.getMovement().getLocation().getY() - e.getMovement().getLocation().getY();
			if (xDiff > 0 && xDiff < 500) {
				x++;
			}
			if (xDiff < 0 && xDiff > -500) {
				x--;
			}
			if (yDiff > 0 && yDiff < 500) {
				y++;
			}
			if (yDiff < 0 && yDiff > -500) {
				y--;
			}

		}
		if (enemy.getMovement().getLocation().getX() < 200) {
			x += 10;
		}
		if (GameFrame.getCanvas().getWidth() - enemy.getMovement().getLocation().getX() < 200) {
			x -= 10;
		}
		if (enemy.getMovement().getLocation().getY() < 200) {
			y += 10;
		}
		if (GameFrame.getCanvas().getHeight() - enemy.getMovement().getLocation().getY() < 200) {
			y -= 10;
		}
		if (playState.getPlayer().getMovement().getLocation().getX() > enemy.getMovement().getLocation().getX()) {
			x += .5;
		} else {
			x -= .5;
		}
		if (playState.getPlayer().getMovement().getLocation().getY() > enemy.getMovement().getLocation().getY()) {
			y += .5;
		} else {
			y -= .5;
		}
		for (int iii = 0; iii < random.nextInt(5); iii++) {
			if (random.nextBoolean()) {
				x++;
			} else {
				x--;
			}
			if (random.nextBoolean()) {
				y++;
			} else {
				y--;
			}
		}
		x *= 100;
		y *= 100;

		turnTo(enemy.getMovement().getLocation().getX() + x, enemy.getMovement().getLocation().getY() + y);
		enemy.getMovement().setAngle(enemy.getRotation().getCurrentOrientation());
		enemy.getRotation().setMoving(true);
		enemy.getMovement().accelerate(delta);

		Sprite collided = playState.getSpriteManager().checkForCollision(enemy, playState.getEnemies());

		if (collided == null) {
			collided = playState.getSpriteManager().checkForCollision(enemy, playState.getPlayer()) ? playState.getPlayer() : null;
		}
		if (collided != null) {
			Character e = (org.zp.blockdude.sprites.game.Character) collided;
			e.damage(1);
			enemy.damage(1);
			Double theta = playState.getSpriteManager().getAngleIfCollision(enemy, e);
			enemy.getMovement().setAngle(theta + Math.PI);
			e.getMovement().setAngle(theta);
			enemy.getMovement().setSpeed(300);
			e.getMovement().setSpeed(300);
		}

		SpriteManager.PLAY_AREA_EDGE canvasEdge = playState.getSpriteManager().checkForEdgeCollision(enemy);
		switch (canvasEdge) {
			case TOP:
			case BOTTOM:
				enemy.getMovement().setAngle(
						-playState.getSpriteManager().getAngleIfCollisionWithEdge(enemy, canvasEdge)
				);
				break;
			case RIGHT:
			case LEFT:
				enemy.getMovement().setAngle(
						Math.PI - playState.getSpriteManager().getAngleIfCollisionWithEdge(enemy, canvasEdge)
				);
		}

		enemy.getMovement().move(
				enemy.getMovement().getXMovement() * enemy.getMovement().getSpeed() * delta / 1000000000F,
				enemy.getMovement().getYMovement() * enemy.getMovement().getSpeed() * delta / 1000000000F
		);

		if (enemy.getRotation().isMoving()) {
			double dTheta = enemy.getRotation().getSpeed() * delta / 1000000000F;
			if (!enemy.getRotation().isClockwise()) {
				dTheta *= -1;
			}
			enemy.getRotation().rotate(dTheta);
		}
		checkDestruct();
	}

	private void checkDestruct() {
		if (enemy.getHealth() <= 0) {
			playState.removeGTickListener(this);
		}
	}

	private void turnTo(double x, double y) {
		double angle = enemy.getRotation().getAngleTo(x, y);
		if (angle > 0) {
			enemy.getRotation().setClockwise(true);
		} else {
			enemy.getRotation().setClockwise(false);
		}
	}
}

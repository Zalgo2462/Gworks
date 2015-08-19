package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.Enemy;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.SpriteManager;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.sprites.Sprite;
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
		double distanceToPlayer = enemy.getMovement().distanceTo(
				playState.getPlayer().getRotation().getRotatedBounds().getBounds().getCenterX(),
				playState.getPlayer().getRotation().getRotatedBounds().getBounds().getCenterY()
		);
		double angleToPlayer = enemy.getRotation().angleTo(
				playState.getPlayer().getRotation().getRotatedBounds().getBounds().getCenterX(),
				playState.getPlayer().getRotation().getRotatedBounds().getBounds().getCenterY()
		);

		if (distanceToPlayer < 75 && Math.abs(angleToPlayer) < Math.PI) {
			enemy.getMovement().decelerate(delta);
		} else if (distanceToPlayer < 400 && Math.abs(angleToPlayer) < Math.PI / 55) {
			lockInOnPlayer(delta);
		} else {
			weightedTurn(delta);
			enemy.getMovement().accelerate(delta);
		}

		moveForward(delta);

		rotate(delta);

		checkWallCollisions();

		checkEnemyCollisions(delta);
	}

	private void lockInOnPlayer(long delta) {
		if (enemy.getMovement().getSpeed() > 0) {
			enemy.getMovement().decelerate(delta);
		} else if (enemy.getMovement().getSpeed() < 0) {
			enemy.getMovement().accelerate(delta);
		}
		enemy.getRotation().setMoving(false);
	}

	private void weightedTurn(long delta) {
		double x = 0;
		double y = 0;
		for (Sprite e : playState.getEnemies()) {
			double xDiff = enemy.getMovement().getLocation().getX() - e.getMovement().getLocation().getX();
			double yDiff = enemy.getMovement().getLocation().getY() - e.getMovement().getLocation().getY();
			if (xDiff > 0 && xDiff < 500) {
				x += enemy.getEnemyRepulsion();
			}
			if (xDiff < 0 && xDiff > -500) {
				x -= enemy.getEnemyRepulsion();
			}
			if (yDiff > 0 && yDiff < 500) {
				y += enemy.getEnemyRepulsion();
			}
			if (yDiff < 0 && yDiff > -500) {
				y -= enemy.getEnemyRepulsion();
			}
		}
		if (playState.getPlayer().getMovement().getLocation().getX() > enemy.getMovement().getLocation().getX()) {
			x += enemy.getPlayerAttraction();
		} else {
			x -= enemy.getPlayerAttraction();
		}
		if (playState.getPlayer().getMovement().getLocation().getY() > enemy.getMovement().getLocation().getY()) {
			y += enemy.getPlayerAttraction();
		} else {
			y -= enemy.getPlayerAttraction();
		}
		for (int iii = 0; iii < random.nextInt(enemy.getRandomness()); iii++) {
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
		enemy.getRotation().setMoving(true);
		turnTo(enemy.getMovement().getLocation().getX() + x, enemy.getMovement().getLocation().getY() + y);
	}

	private void moveForward(long delta) {
		enemy.getMovement().move(
				enemy.getMovement().getXMovement() * enemy.getMovement().getSpeed() * delta / 1000000000D,
				enemy.getMovement().getYMovement() * enemy.getMovement().getSpeed() * delta / 1000000000D
		);
	}

	private void rotate(long delta) {
		if (enemy.getRotation().isMoving()) {
			double dTheta = enemy.getRotation().getSpeed() * delta / 1000000000D;
			enemy.getRotation().rotate(dTheta);
			enemy.getMovement().setAngle(enemy.getRotation().getCurrentOrientation());
		}
	}

	private void checkEnemyCollisions(long delta) {
		Sprite collided = playState.getSpriteManager().checkForCollision(enemy, playState.getEnemies());
		if (collided != null) {
			Enemy e = (Enemy) collided;
			Double theta = playState.getSpriteManager().getAngleIfCollision(enemy, e);
			double oldEnemyAngle = enemy.getMovement().getAngle();
			double oldEAngle = e.getMovement().getAngle();
			enemy.damage(1);
			e.damage(1);
			do {
				enemy.getMovement().setAngle(theta + Math.PI);
				e.getMovement().setAngle(theta);
				enemy.getMovement().move(
						enemy.getMovement().getXMovement() * delta / 1000000000D,
						enemy.getMovement().getYMovement() * delta / 1000000000D
				);
				e.getMovement().move(
						e.getMovement().getXMovement() * delta / 1000000000D,
						e.getMovement().getYMovement() * delta / 1000000000D
				);
				collided = playState.getSpriteManager().checkForCollision(enemy, playState.getEnemies());
				theta = playState.getSpriteManager().getAngleIfCollision(enemy, e);
			} while (collided != null);
			enemy.getMovement().setAngle(oldEnemyAngle);
			e.getMovement().setAngle(oldEAngle);
		}
	}

	private void checkWallCollisions() {
		SpriteManager.PlayAreaEdge canvasEdge = playState.getSpriteManager().checkForEdgeCollision(enemy);
		switch (canvasEdge) {
			case TOP:
				enemy.getMovement().setLocation(
						enemy.getMovement().getLocation().getX(),
						PlayState.UI_CONSTANTS.PLAY_AREA_BOTTOM -
								enemy.getRotation().getRotatedBounds().getBounds().getHeight() - 5
				);
				break;
			case BOTTOM:
				enemy.getMovement().setLocation(
						enemy.getMovement().getLocation().getX(),
						PlayState.UI_CONSTANTS.PLAY_AREA_TOP + 5
				);
				break;
			case RIGHT:
				enemy.getMovement().setLocation(
						PlayState.UI_CONSTANTS.PLAY_AREA_LEFT + 5,
						enemy.getMovement().getLocation().getY()
				);
				break;
			case LEFT:
				enemy.getMovement().setLocation(
						PlayState.UI_CONSTANTS.PLAY_AREA_RIGHT -
								enemy.getRotation().getRotatedBounds().getBounds().getWidth() - 5,
						enemy.getMovement().getLocation().getY()
				);
				break;
		}
	}

	private void turnTo(double x, double y) {
		double angle = enemy.getRotation().angleTo(x, y);
		if (angle > 0) {
			enemy.getRotation().setClockwise(true);
		} else {
			enemy.getRotation().setClockwise(false);
		}
	}
}

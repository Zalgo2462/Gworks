package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.Sprite;
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
	private long blockRotationTime;

	public EnemyMovement(final PlayState playState, final Enemy enemy) {
		this.playState = playState;
		this.enemy = enemy;
		this.random = new Random();
		this.blockRotationTime = System.currentTimeMillis();
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		if (enemy.getMovement().distanceTo(
				playState.getPlayer().getRenderer().getBounds().getBounds().getCenterX(),
				playState.getPlayer().getRenderer().getBounds().getBounds().getCenterY()
		) < 150 && Math.abs(enemy.getRotation().angleTo(
						playState.getPlayer().getRenderer().getBounds().getBounds().getCenterX(),
						playState.getPlayer().getRenderer().getBounds().getBounds().getCenterY())
		) < Math.PI / 32
				) {
			enemy.getMovement().decelerateToZero(delta);
			enemy.getRotation().setMoving(false);
		} else if (System.currentTimeMillis() > blockRotationTime) {
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
			enemy.getMovement().setAngle(enemy.getRotation().getCurrentOrientation());
			enemy.getMovement().accelerate(delta);
		} else {
			enemy.getMovement().decelerateToZero(delta);
		}

		enemy.getMovement().move(
				enemy.getMovement().getXMovement() * enemy.getMovement().getSpeed() * delta / 1000000000F,
				enemy.getMovement().getYMovement() * enemy.getMovement().getSpeed() * delta / 1000000000F
		);

		if (enemy.getRotation().isMoving()) {
			double dTheta = enemy.getRotation().getSpeed() * delta / 1000000000D;
			if (!enemy.getRotation().isClockwise()) {
				dTheta *= -1;
			}
			enemy.getRotation().rotate(dTheta);
		}

		Sprite collided = playState.getSpriteManager().checkForCollision(enemy, playState.getEnemies());
		if (collided != null) {
			Enemy e = (Enemy) collided;
			Double theta = playState.getSpriteManager().getAngleIfCollision(enemy, e);
			enemy.getMovement().setAngle(theta + Math.PI);
			e.getMovement().setAngle(theta);
			if (enemy.getMovement().getSpeed() < 50) {
				enemy.getMovement().setSpeed(50);
			}
			if (e.getMovement().getSpeed() < 50) {
				e.getMovement().setSpeed(50);
			}
			enemy.damage(1);
			e.damage(1);
			blockRotation(150);
			e.getEnemyMovement().blockRotation(150);
		}

		SpriteManager.PlayAreaEdge canvasEdge = playState.getSpriteManager().checkForEdgeCollision(enemy);
		switch (canvasEdge) {
			case TOP:
				enemy.getMovement().setLocation(
						enemy.getMovement().getLocation().getX(),
						PlayState.UI_CONSTANTS.PLAY_AREA_BOTTOM -
								enemy.getRenderer().getBounds().getBounds().getHeight()
				);
				break;
			case BOTTOM:
				enemy.getMovement().setLocation(
						enemy.getMovement().getLocation().getX(),
						PlayState.UI_CONSTANTS.PLAY_AREA_TOP +
								enemy.getRenderer().getBounds().getBounds().getHeight()
				);
				break;
			case RIGHT:
				enemy.getMovement().setLocation(
						PlayState.UI_CONSTANTS.PLAY_AREA_LEFT +
								enemy.getRenderer().getBounds().getBounds().getWidth(),
						enemy.getMovement().getLocation().getY()
				);
				break;
			case LEFT:
				enemy.getMovement().setLocation(
						PlayState.UI_CONSTANTS.PLAY_AREA_RIGHT -
								enemy.getRenderer().getBounds().getBounds().getWidth(),
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

	public void blockRotation(int milliseconds) {
		enemy.getRotation().setMoving(false);
		blockRotationTime = System.currentTimeMillis() + milliseconds;
	}
}

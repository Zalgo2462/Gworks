package org.zp.blockdude.states.playstate.ticklisteners;

import org.zp.blockdude.sprites.Enemy;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.SpriteManager;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;
import org.zp.gworks.sprites.Sprite;
import org.zp.gworks.sprites.movement.movement2d.forces.SimpleForce;

import java.util.Random;

/**
 * Date: 7/18/2014
 * Time: 6:55 PM
 */
public class EnemyMovement implements GTickListener {
	private final PlayState playState;
	private final Enemy enemy;
	private final Random random;
	private SimpleForce acceleration;
	private SimpleForce deceleration;
	private SimpleForce naturalDeceleration;


	public EnemyMovement(final PlayState playState, final Enemy enemy) {
		this.playState = playState;
		this.enemy = enemy;
		this.random = new Random();

		this.acceleration = new SimpleForce();
		acceleration.setActive(false);
		this.deceleration = new SimpleForce();
		acceleration.setActive(false);
		this.naturalDeceleration = new SimpleForce();

		enemy.getMovement().addForce(acceleration);
		enemy.getMovement().addForce(deceleration);
		enemy.getMovement().addForce(naturalDeceleration);
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		setForces();

		rotate(delta);

		updateForces();

		move(delta);

		checkWallCollisions();

		checkEnemyCollisions(delta);
	}

	private void setForces() {
		double distanceToPlayer = enemy.getMovement().distanceTo(
				playState.getPlayer().getRotation().getRotatedBounds().getBounds().getCenterX(),
				playState.getPlayer().getRotation().getRotatedBounds().getBounds().getCenterY()
		);
		double angleToPlayer = enemy.getRotation().angleTo(
				playState.getPlayer().getRotation().getRotatedBounds().getBounds().getCenterX(),
				playState.getPlayer().getRotation().getRotatedBounds().getBounds().getCenterY()
		);

		//decide forces
		if (distanceToPlayer < 75 && Math.abs(angleToPlayer) < Math.PI) { //move back
			acceleration.setActive(false);
			deceleration.setActive(true);
		} else if (distanceToPlayer < 400 && Math.abs(angleToPlayer) < Math.PI / 55) {     //lock in
			if (distanceToPlayer > 200) {
				acceleration.setActive(true);
				deceleration.setActive(false);
			} else if (distanceToPlayer < 200) {
				acceleration.setActive(false);
				deceleration.setActive(true);
			}
			enemy.getRotation().setMoving(false);
		} else {                                    //turn in
			weightedTurn();
			acceleration.setActive(true);
			deceleration.setActive(false);
		}
	}

	private void weightedTurn() {
		double x = 0;
		double y = 0;
		for (Sprite e : playState.getEnemies()) {    //Repulse from enemies
			if (!e.equals(enemy)) {
				double angle = enemy.getMovement().angleTo(
						e.getMovement().getLocation().getX(),
						e.getMovement().getLocation().getY()
				);
				if (angle + Math.PI < Math.PI * 2)
					angle += Math.PI;
				else
					angle -= Math.PI;
				x += Math.cos(angle) * enemy.getEnemyRepulsion();
				y += Math.sin(angle) * enemy.getEnemyRepulsion();
			}
		}
		double angle = enemy.getMovement().angleTo(
				playState.getPlayer().getMovement().getLocation().getX(),
				playState.getPlayer().getMovement().getLocation().getY()
		);
		x += Math.cos(angle) * enemy.getPlayerAttraction();
		y += Math.sin(angle) * enemy.getPlayerAttraction();

		//fuzziness
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

	private void move(long delta) {
		enemy.getMovement().accelerate(delta);
		enemy.getMovement().move(
				enemy.getMovement().getXVelocity() * delta / 1000000000D,
				enemy.getMovement().getYVelocity() * delta / 1000000000D
		);
	}

	private void rotate(long delta) {
		if (enemy.getRotation().isMoving()) {
			double dTheta = enemy.getRotation().getVelocity() * delta / 1000000000D;
			enemy.getRotation().rotate(dTheta);
		}
	}

	private void updateForces() {
		acceleration.setForce(
				Math.cos(enemy.getRotation().getCurrentAngle()) * enemy.getAcceleration(),
				Math.sin(enemy.getRotation().getCurrentAngle()) * enemy.getAcceleration()
		);
		deceleration.setForce(
				Math.cos(enemy.getRotation().getCurrentAngle()) * enemy.getDeceleration(),
				Math.sin(enemy.getRotation().getCurrentAngle()) * enemy.getDeceleration()
		);
		naturalDeceleration.setForce(
				Math.cos(enemy.getMovement().getForwardAngle()) * enemy.getNaturalDeceleration(),
				Math.sin(enemy.getMovement().getForwardAngle()) * enemy.getNaturalDeceleration()
		);
		if (Math.abs(enemy.getMovement().getVelocity()) <= 0.000001) {
			naturalDeceleration.setActive(false);
		} else {
			naturalDeceleration.setActive(true);
		}
	}

	private void checkEnemyCollisions(long delta) {
		Sprite collided = playState.getSpriteManager().checkForCollision(enemy, playState.getEnemies());
		if (collided != null) {
			Enemy e = (Enemy) collided;
			enemy.damage(1);
			e.damage(1);
			while ((collided = playState.getSpriteManager().checkForCollision(enemy, playState.getEnemies())) != null) {
				e = (Enemy) collided;
				Double theta = playState.getSpriteManager().getAngleIfCollision(enemy, e);
				enemy.getMovement().move(
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

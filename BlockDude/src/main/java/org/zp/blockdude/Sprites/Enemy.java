package org.zp.blockdude.sprites;

import org.zp.blockdude.ColorScheme;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.renderlisteners.EnemyHealthRenderer;
import org.zp.blockdude.states.playstate.ticklisteners.EnemyDeath;
import org.zp.blockdude.states.playstate.ticklisteners.EnemyMissiles;
import org.zp.blockdude.states.playstate.ticklisteners.EnemyMovement;

public class Enemy extends Character {
	private PlayState playState;
	private EnemyMissiles enemyMissiles;
	private EnemyMovement enemyMovement;
	private EnemyDeath enemyDeath;
	private EnemyHealthRenderer enemyHealthRenderer;
	private double playerAttraction, enemyRepulsion;
	private int randomness;

	public Enemy(final PlayState playState, final int size, final int speed,
	             final double playerAttraction, final double enemyRepulsion, final int randomness) {
		super(size, ColorScheme.LIGHTER_RED.getColor());
		movement.setMaxSpeed(speed);
		rotation.setSpeed(Math.PI);
		this.playState = playState;
		this.enemyMissiles = new EnemyMissiles(playState, this);
		this.enemyMovement = new EnemyMovement(playState, this);
		this.enemyDeath = new EnemyDeath(playState, this);
		this.enemyHealthRenderer = new EnemyHealthRenderer(this);
		this.playerAttraction = playerAttraction;
		this.enemyRepulsion = enemyRepulsion;
		this.randomness = randomness;
	}

	public EnemyMissiles getEnemyMissiles() {
		return enemyMissiles;
	}

	public EnemyMovement getEnemyMovement() {
		return enemyMovement;
	}

	public EnemyDeath getEnemyDeath() {
		return enemyDeath;
	}

	public EnemyHealthRenderer getEnemyHealthRenderer() {
		return enemyHealthRenderer;
	}

	public double getPlayerAttraction() {
		return playerAttraction;
	}

	public double getEnemyRepulsion() {
		return enemyRepulsion;
	}

	public int getRandomness() {
		return randomness;
	}
}

package org.zp.blockdude.sprites.game;

import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.ticklisteners.EnemyDeath;
import org.zp.blockdude.states.playstate.ticklisteners.EnemyMissiles;
import org.zp.blockdude.states.playstate.ticklisteners.EnemyMovement;

import java.awt.*;

public class Enemy extends Character {
	private PlayState playState;
	private EnemyMissiles enemyMissiles;
	private EnemyMovement enemyMovement;
	private EnemyDeath enemyDeath;

	public Enemy(final PlayState playState, final int size, final int speed) {
		super(size, Color.RED);
		getMovement().setMaxSpeed(speed);
		getRotation().setSpeed(Math.PI);
		this.playState = playState;
		this.enemyMissiles = new EnemyMissiles(playState, this);
		this.enemyMovement = new EnemyMovement(playState, this);
		this.enemyDeath = new EnemyDeath(playState, this);
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
}

package org.zp.blockdude.sprites.game;

import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.renderlisteners.HealthRenderer;
import org.zp.blockdude.states.playstate.ticklisteners.PlayerDeath;
import org.zp.blockdude.states.playstate.ticklisteners.PlayerMissiles;
import org.zp.blockdude.states.playstate.ticklisteners.PlayerMovement;

import java.awt.*;

public class Player extends Character {
	private PlayState playState;
	private PlayerMissiles playerMissiles;
	private PlayerMovement playerMovement;
	private PlayerDeath playerDeath;
	private HealthRenderer healthRenderer;
	private int lives;

	public Player(final PlayState playState) {
		super(25, Color.GREEN);
		this.playState = playState;
		this.playerMissiles = new PlayerMissiles(playState, this);
		this.playerMovement = new PlayerMovement(playState, this);
		this.playerDeath = new PlayerDeath(playState, this);
		this.healthRenderer = new HealthRenderer(this);
		this.lives = 3;
	}

	public PlayerMissiles getPlayerMissiles() {
		return playerMissiles;
	}

	public PlayerMovement getPlayerMovement() {
		return playerMovement;
	}

	public PlayerDeath getPlayerDeath() {
		return playerDeath;
	}

	public HealthRenderer getHealthRenderer() {
		return healthRenderer;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}
package org.zp.blockdude.states.playstate;

import org.zp.blockdude.GameFrame;
import org.zp.blockdude.Level;
import org.zp.blockdude.sprites.game.Enemy;
import org.zp.blockdude.sprites.game.Player;
import org.zp.blockdude.states.playstate.ticklisteners.PlayerMovement;
import org.zp.gworks.logic.GState.GMutableState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayState extends GMutableState {
	private Canvas canvas;
	private SpriteManager spriteManager;
	private Player player;
	private ArrayList<Enemy> enemies;

	public PlayState() {
		canvas = GameFrame.getCanvas();
		spriteManager = new SpriteManager();
		addGPaintStrategy(spriteManager);
	}

	public void initLevel(Level level) {
		initPlayer();
		initEnemies(level);
	}


	private void initPlayer() {
		player = new Player();
		player.setLocation(new Point(
				canvas.getWidth()/2 - player.getSprite().getWidth()/2,
				canvas.getHeight()/2 - player.getSprite().getHeight()/2));
		spriteManager.registerSprite(player);
		addGTickListener(new PlayerMovement(player));
		player.setRendered(true);
	}

	private void initEnemies(Level level) {
		enemies = new ArrayList<Enemy>();
		for(int iii = 0; iii < level.getEnemies(); iii++) {
			Enemy e = new Enemy(level.getEnemySize(), level.getEnemySpeed());
			enemies.add(e);
			spriteManager.registerSprite(e);
			placeEnemy(e);
			e.setRendered(true);
		}
	}

	private void placeEnemy(Enemy enemy) {
		//TODO: Implement better algorithm
		Random rand = new Random();
		int maxX = canvas.getWidth() - enemy.getBounds().width;
		int maxY = canvas.getHeight() - enemy.getBounds().height;
		while(true) {
			enemy.setLocation(new Point(rand.nextInt(maxX),rand.nextInt(maxY)));
			if(!spriteManager.checkForCollision(enemy))
				break;
		}
	}
}

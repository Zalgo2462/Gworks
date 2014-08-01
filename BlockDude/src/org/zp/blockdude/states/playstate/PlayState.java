package org.zp.blockdude.states.playstate;

import org.zp.blockdude.GameFrame;
import org.zp.blockdude.Level;
import org.zp.blockdude.sprites.Sprite;
import org.zp.blockdude.sprites.game.Enemy;
import org.zp.blockdude.sprites.game.Player;
import org.zp.blockdude.states.playstate.renderlisteners.InfoAreaRenderer;
import org.zp.gworks.logic.GState.GMutableState;

import java.util.LinkedList;
import java.util.Random;

import static org.zp.blockdude.GameFrame.DIMENSION;

public class PlayState extends GMutableState {
	private SpriteManager spriteManager;
	private Player player;
	private LinkedList<Sprite> enemies;

	public static class UI_CONSTANTS {
		public static final int PLAY_AREA_TOP = 50;
		public static final int PLAY_AREA_LEFT = 0;
		public static final int PLAY_AREA_RIGHT = GameFrame.DIMENSION.width;
		public static final int PLAY_AREA_BOTTOM = GameFrame.DIMENSION.height;
		public static final int INFO_AREA_TOP = 15;
		public static final int INFO_AREA_HEIGHT = 20;
		public static final int INFO_AREA_LEFT = 15;
		public static final int INFO_AREA_RIGHT = GameFrame.DIMENSION.width - 15;
		public static final int HEALTH_BAR_START = INFO_AREA_RIGHT - 200;
		public static final int LIVES_BAR_START = HEALTH_BAR_START - (((25 + 5) * 5) + 15);
		public static final int LIVES_BAR_END = HEALTH_BAR_START - 15;
	}

	public PlayState() {
		spriteManager = new SpriteManager(this);
		addGRenderListener(new InfoAreaRenderer());
	}

	public void initLevel(Level level) {
		initPlayer();
		initEnemies(level);
	}

	private void initPlayer() {
		player = new Player(this);
		player.getMovement().setCurrentLocation(
				DIMENSION.width / 2 - player.getRenderer().getSprite().getWidth() / 2,
				DIMENSION.height / 2 - player.getRenderer().getSprite().getHeight() / 2);
		spriteManager.registerSprite(player);
		addGRenderListener(player.getHealthRenderer());
		addGTickListener(player.getPlayerMovement());
		addGTickListener(player.getPlayerMissiles());
		addGTickListener(player.getPlayerDeath());
		player.getRenderer().setRendered(true);
	}

	private void initEnemies(Level level) {
		enemies = new LinkedList<Sprite>();
		for(int iii = 0; iii < level.getEnemies(); iii++) {
			Enemy e = new Enemy(this, level.getEnemySize(), level.getEnemySpeed());
			enemies.add(e);
			spriteManager.registerSprite(e);
			placeEnemy(e);
			addGTickListener(e.getEnemyMovement());
			addGTickListener(e.getEnemyMissiles());
			addGTickListener(e.getEnemyDeath());
			e.getRenderer().setRendered(true);
		}
	}

	private void placeEnemy(Enemy enemy) {
		Random rand = new Random();
		int maxX = UI_CONSTANTS.PLAY_AREA_RIGHT - enemy.getRenderer().getBounds().getBounds().width;
		int minY = UI_CONSTANTS.PLAY_AREA_TOP;
		int maxY = UI_CONSTANTS.PLAY_AREA_BOTTOM - enemy.getRenderer().getBounds().getBounds().height;
		while(true) {
			enemy.getMovement().setCurrentLocation(rand.nextInt(maxX), rand.nextInt(maxY - minY + 1) + minY);
			if (spriteManager.checkForCollision(enemy) == null)
				break;
		}
	}

	public LinkedList<Sprite> getEnemies() {
		return enemies;
	}

	public Player getPlayer() {
		return player;
	}

	public SpriteManager getSpriteManager() {
		return spriteManager;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}

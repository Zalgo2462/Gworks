package org.zp.blockdude.states.playstate;

import org.zp.blockdude.GameFrame;
import org.zp.blockdude.Level;
import org.zp.blockdude.sprites.Sprite;
import org.zp.blockdude.sprites.game.Enemy;
import org.zp.blockdude.sprites.game.Player;
import org.zp.blockdude.states.playstate.renderlisteners.BackgroundRenderer;
import org.zp.blockdude.states.playstate.renderlisteners.InfoAreaRenderer;
import org.zp.blockdude.states.playstate.renderlisteners.ScoreRenderer;
import org.zp.blockdude.states.playstate.ticklisteners.LevelAdvancement;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GState.GMutableState;

import java.util.LinkedList;
import java.util.Random;

import static org.zp.blockdude.GameFrame.DIMENSION;

public class PlayState extends GMutableState {
	private Level currentLevel;
	private BackgroundRenderer backgroundRenderer;
	private InfoAreaRenderer infoAreaRenderer;
	private LevelAdvancement levelAdvancer;
	private int score;
	private ScoreRenderer scoreRenderer;
	private SpriteManager spriteManager;
	private Player player;
	private LinkedList<Sprite> enemies;

	public PlayState(GCanvas canvas) {
		super(canvas);
		spriteManager = new SpriteManager(this);

		player = new Player(this);
		enemies = new LinkedList<Sprite>();

		backgroundRenderer = new BackgroundRenderer();

		infoAreaRenderer = new InfoAreaRenderer(this);

		levelAdvancer = new LevelAdvancement(this);

		score = 0;
		scoreRenderer = new ScoreRenderer(this);
	}

	public void onAddGState() {
		initBackground();
		initScoreboard();
	}

	public void onRemoveGState() {
		if (currentLevel == null) {
			uninitLevel();
		}
		uninitBackground();
		uninitScoreboard();
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public void initLevel(Level level) {
		if (currentLevel != null) {
			uninitLevel();
		}
		currentLevel = level;
		initPlayer();
		initEnemies(level);
		addGTickListener(levelAdvancer);
	}

	public void uninitLevel() {
		uninitPlayer();
		uninitEnemies();
		removeGTickListener(levelAdvancer);
		currentLevel = null;
	}

	private void initPlayer() {
		player.getMovement().setCurrentLocation(
				DIMENSION.width / 2 - player.getRenderer().getSprite().getWidth() / 2,
				DIMENSION.height / 2 - player.getRenderer().getSprite().getHeight() / 2);
		player.setHealth(100);
		player.setLives(3);
		addGRenderListener(player.getHealthRenderer());
		addGTickListener(player.getPlayerMovement());
		addGTickListener(player.getPlayerMissiles());
		addGTickListener(player.getPlayerDeath());
		spriteManager.registerSprite(player);
		player.getRenderer().setRendered(true);
	}

	private void uninitPlayer() {
		removeGRenderListener(player.getHealthRenderer());
		removeGTickListener(player.getPlayerMovement());
		removeGTickListener(player.getPlayerMissiles());
		removeGTickListener(player.getPlayerDeath());
		spriteManager.unregisterSprite(player);
		player.getRenderer().setRendered(false);
	}

	private void initEnemies(Level level) {
		for (int iii = 0; iii < level.getEnemies(); iii++) {
			Enemy e = new Enemy(this, level.getEnemySize(), level.getEnemySpeed(),
					level.getPlayerAttraction(), level.getEnemyRepulsion(), level.getRandomness());
			enemies.add(e);
			spriteManager.registerSprite(e);
			placeEnemy(e);
			addGTickListener(e.getEnemyMovement());
			addGTickListener(e.getEnemyMissiles());
			addGTickListener(e.getEnemyDeath());
			e.getRenderer().setRendered(true);
		}
	}

	private void uninitEnemies() {
		for (Sprite e : enemies) {
			Enemy enemy = (Enemy) e;
			spriteManager.unregisterSprite(e);
			removeGTickListener(enemy.getEnemyMovement());
			removeGTickListener(enemy.getEnemyMissiles());
			removeGTickListener(enemy.getEnemyDeath());
			e.getRenderer().setRendered(false);
		}
	}

	private void initBackground() {
		addGRenderListener(backgroundRenderer);
	}

	private void uninitBackground() {
		removeGRenderListener(backgroundRenderer);
	}

	private void initScoreboard() {
		addGRenderListener(infoAreaRenderer);
		addGRenderListener(scoreRenderer);
	}

	private void uninitScoreboard() {
		removeGRenderListener(infoAreaRenderer);
		removeGRenderListener(scoreRenderer);
	}

	private void placeEnemy(Enemy enemy) {
		Random rand = new Random();
		int maxX = UI_CONSTANTS.PLAY_AREA_RIGHT - enemy.getRenderer().getBounds().getBounds().width;
		int minY = UI_CONSTANTS.PLAY_AREA_TOP;
		int maxY = UI_CONSTANTS.PLAY_AREA_BOTTOM - enemy.getRenderer().getBounds().getBounds().height;
		while (true) {
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static class UI_CONSTANTS {
		public static final int PLAY_AREA_TOP = 50;
		public static final int PLAY_AREA_LEFT = 0;
		public static final int PLAY_AREA_RIGHT = GameFrame.DIMENSION.width;
		public static final int PLAY_AREA_BOTTOM = GameFrame.DIMENSION.height;
		public static final int INFO_AREA_TOP = 15;
		public static final int INFO_AREA_HEIGHT = 20;
		public static final int INFO_AREA_LEFT = 15;
		public static final int LEVEL_BAR_START = INFO_AREA_LEFT + 150;
		public static final int INFO_AREA_RIGHT = GameFrame.DIMENSION.width - 15;
		public static final int HEALTH_BAR_START = INFO_AREA_RIGHT - 200;
		public static final int LIVES_BAR_END = HEALTH_BAR_START - 15;
	}
}

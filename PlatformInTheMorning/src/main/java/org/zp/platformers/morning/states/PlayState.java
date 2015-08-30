package org.zp.platformers.morning.states;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GState.GMutableState;
import org.zp.platformers.morning.levels.Level;
import org.zp.platformers.morning.sprites.Player;
import org.zp.platformers.morning.states.renderlisteners.Camera;

/**
 * Date: 8/29/2015
 * Time: 6:21 PM
 */
public class PlayState extends GMutableState {
	private Player player;
	private Level level;
	private Camera camera;

	public PlayState(GCanvas canvas) {
		super(canvas);
		this.player = new Player(this);
		this.camera = new Camera(this);
	}

	@Override
	public void onAddState() {
		addTickListener(camera);
		addRenderListener(camera);

		addTickListener(player.getPlayerMovement());
	}

	@Override
	public void onRemoveState() {
		removeTickListener(camera);
		removeRenderListener(camera);

		removeTickListener(player.getPlayerMovement());
	}

	public Player getPlayer() {
		return player;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		if (this.level != null)
			this.level.unload();

		this.level = level;
		this.level.load();
	}
}

package org.zp.platformers.morning.states;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GState.GMutableState;
import org.zp.platformers.morning.levels.Camera;
import org.zp.platformers.morning.levels.Level;
import org.zp.platformers.morning.sprites.Player;

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
		this.player = new Player();
		this.camera = new Camera(this);
	}

	@Override
	public void onAddState() {
		addTickListener(camera);
		addRenderListener(camera);
	}

	@Override
	public void onRemoveState() {
		removeTickListener(camera);
		removeRenderListener(camera);
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

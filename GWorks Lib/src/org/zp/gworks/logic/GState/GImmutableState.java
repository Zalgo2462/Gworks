package org.zp.gworks.logic.GState;

import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GTickListener;

public abstract class GImmutableState implements GState {
	private GTickListener[] tickListeners = new GTickListener[0];
	private GRenderListener[] renderStrategies = new GRenderListener[0];

	protected void setTickListeners(GTickListener[] tickListeners) {
		this.tickListeners = tickListeners;
	}

	protected void setRenderStrategies(GRenderListener[] renderStrategies) {
		this.renderStrategies = renderStrategies;
	}

	@Override
	public GTickListener[] getTickListeners() {
		return tickListeners;
	}

	@Override
	public GRenderListener[] getRenderStrategies() {
		return renderStrategies;
	}

}

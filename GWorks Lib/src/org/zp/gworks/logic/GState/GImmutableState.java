package org.zp.gworks.logic.GState;

import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;
import org.zp.gworks.logic.GTickListener;

public abstract class GImmutableState implements GState {
	private GTickListener[] tickListeners = new GTickListener[0];
	private GRenderStrategy[] renderStrategies = new GRenderStrategy[0];

	protected void setTickListeners(GTickListener[] tickListeners) {
		this.tickListeners = tickListeners;
	}

	protected void setRenderStrategies(GRenderStrategy[] renderStrategies) {
		this.renderStrategies = renderStrategies;
	}

	@Override
	public GTickListener[] getTickListeners() {
		return tickListeners;
	}

	@Override
	public GRenderStrategy[] getRenderStrategies() {
		return renderStrategies;
	}

}

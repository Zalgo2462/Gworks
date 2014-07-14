package org.zp.gworks.logic.GState;

import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;
import org.zp.gworks.logic.GTickListener;

import java.util.concurrent.CopyOnWriteArrayList;

//Mutable GState
public abstract class GMutableState implements GState {
	private CopyOnWriteArrayList<GTickListener> tickListeners;
	private CopyOnWriteArrayList<GRenderStrategy> renderStrategies;

	protected GMutableState() {
		this.tickListeners = new CopyOnWriteArrayList<GTickListener>();
		this.renderStrategies = new CopyOnWriteArrayList<GRenderStrategy>();
	}

	public GTickListener[] getTickListeners() {
		return tickListeners.toArray(new GTickListener[tickListeners.size()]);
	}

	public GRenderStrategy[] getRenderStrategies() {
		return renderStrategies.toArray(new GRenderStrategy[renderStrategies.size()]);
	}

	protected void addGTickListener(final GTickListener tickListener) {
		tickListeners.add(tickListener);
	}

	protected void addGPaintStrategy(final GRenderStrategy renderStrategy) {
		renderStrategies.add(renderStrategy);
	}

	protected void removeGTickListener(final GTickListener tickListener) {
		tickListeners.remove(tickListener);
	}

	protected void removeGPaintStrategy(final GRenderStrategy renderStrategy) {
		renderStrategies.remove(renderStrategy);
	}
}

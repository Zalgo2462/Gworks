package org.zp.gworks.logic.GState;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GTickListener;

import java.util.concurrent.CopyOnWriteArrayList;

//Mutable GState
public abstract class GMutableState implements GState {
	protected GCanvas canvas;
	private CopyOnWriteArrayList<GTickListener> tickListeners;
	private CopyOnWriteArrayList<GRenderListener> renderStrategies;

	protected GMutableState(GCanvas canvas) {
		this.canvas = canvas;
		this.tickListeners = new CopyOnWriteArrayList<GTickListener>();
		this.renderStrategies = new CopyOnWriteArrayList<GRenderListener>();
	}

	public GTickListener[] getTickListeners() {
		return tickListeners.toArray(new GTickListener[tickListeners.size()]);
	}

	public GRenderListener[] getRenderListeners() {
		return renderStrategies.toArray(new GRenderListener[renderStrategies.size()]);
	}

	public void onAddState() {
	}

	public void onRemoveState() {
	}

	public boolean addTickListener(final GTickListener tickListener) {
		return tickListeners.add(tickListener);
	}

	public boolean addRenderListener(final GRenderListener renderStrategy) {
		return renderStrategies.add(renderStrategy);
	}

	public boolean removeTickListener(final GTickListener tickListener) {
		return tickListeners.remove(tickListener);
	}

	public boolean removeRenderListener(final GRenderListener renderStrategy) {
		return renderStrategies.remove(renderStrategy);
	}

	public GCanvas getCanvas() {
		return canvas;
	}
}

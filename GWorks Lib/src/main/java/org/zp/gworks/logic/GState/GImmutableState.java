package org.zp.gworks.logic.GState;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GTickListener;

public abstract class GImmutableState implements GState {
	protected GCanvas canvas;
	private GTickListener[] tickListeners = new GTickListener[0];
	private GRenderListener[] renderStrategies = new GRenderListener[0];

	protected GImmutableState(GCanvas canvas) {
		this.canvas = canvas;
	}

	public GTickListener[] getTickListeners() {
		return tickListeners;
	}

	protected void setTickListeners(GTickListener[] tickListeners) {
		this.tickListeners = tickListeners;
	}

	public GRenderListener[] getRenderListeners() {
		return renderStrategies;
	}

	protected void setRenderListeners(GRenderListener[] renderStrategies) {
		this.renderStrategies = renderStrategies;
	}

	public void onAddState() {

	}

	public void onRemoveState() {

	}

	public GCanvas getCanvas() {
		return canvas;
	}
}

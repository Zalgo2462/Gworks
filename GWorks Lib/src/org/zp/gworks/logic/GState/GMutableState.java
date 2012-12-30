package org.zp.gworks.logic.GState;

import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;
import org.zp.gworks.logic.GTickListener;

import java.util.concurrent.CopyOnWriteArrayList;

//Mutable GState
public class GMutableState implements GState {
	private CopyOnWriteArrayList<GTickListener> tickListeners;
	private CopyOnWriteArrayList<GPaintStrategy> paintStrategies;

	public GMutableState() {
		this.tickListeners = new CopyOnWriteArrayList<GTickListener>();
		this.paintStrategies = new CopyOnWriteArrayList<GPaintStrategy>();
	}

	public GTickListener[] getTickListeners() {
		return tickListeners.toArray(new GTickListener[tickListeners.size()]);
	}

	public GPaintStrategy[] getPaintStrategies() {
		return paintStrategies.toArray(new GPaintStrategy[paintStrategies.size()]);
	}

	public void addGTickListener(final GTickListener tickListener) {
		tickListeners.add(tickListener);
	}

	public void addGPaintStrategy(final GPaintStrategy paintStrategy) {
		paintStrategies.add(paintStrategy);
	}

	public void removeGTickListener(final GTickListener tickListener) {
		tickListeners.remove(tickListener);
	}

	public void removeGPaintStrategy(final GPaintStrategy paintStrategy) {
		paintStrategies.remove(paintStrategy);
	}
}

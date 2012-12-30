package org.zp.gworks.logic.GState;

import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;
import org.zp.gworks.logic.GTickListener;

public class GImmutableState implements GState {
	private GTickListener[] tickListeners;
	private GPaintStrategy[] paintStrategies;

	public GImmutableState(final GTickListener[] tickListeners, final GPaintStrategy[] paintStrategies) {
		this.tickListeners = tickListeners;
		this.paintStrategies = paintStrategies;
	}

	@Override
	public GTickListener[] getTickListeners() {
		return tickListeners;
	}

	@Override
	public GPaintStrategy[] getPaintStrategies() {
		return paintStrategies;
	}
}

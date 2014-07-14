package org.zp.blockdude.states.fakeloader;

import org.zp.blockdude.states.fakeloader.renderstrategies.FakeLoaderRenderer;
import org.zp.blockdude.states.fakeloader.ticklisteners.TickCounter;
import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

/**
 * Date: 7/14/2014
 * Time: 1:23 AM
 */
public class FakeLoaderState extends GImmutableState {
	private double progress = 0F;

	public FakeLoaderState() {
		setTickListeners(new GTickListener[] {new TickCounter(this)});
		setRenderStrategies(new GRenderStrategy[] {new FakeLoaderRenderer(this)});
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(final double progress) {
		this.progress = progress;
	}
}

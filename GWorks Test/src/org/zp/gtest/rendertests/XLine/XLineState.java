package org.zp.gtest.rendertests.XLine;

import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

public class XLineState extends GImmutableState {
	private int x;
	private boolean backwards;

	public XLineState(final int start) {
		x = start;
		backwards = false;
		setTickListeners(new GTickListener[]{new XLineController(this)});
		setRenderStrategies(new GRenderListener[]{new XLineRenderer(this)});
	}

	public XLineState() {
		this(0);
	}

	public int getX() {
		return x;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public boolean isBackwards() {
		return backwards;
	}

	public void setBackwards(final boolean backwards) {
		this.backwards = backwards;
	}
}

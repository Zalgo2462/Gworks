package org.zp.gtest.rendertests.XLine;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

public class XLineState extends GImmutableState {
	private double x;
	private boolean backwards;

	public XLineState(final GCanvas canvas, final int start) {
		super(canvas);
		x = start;
		backwards = false;
		setTickListeners(new GTickListener[]{new XLineController(this)});
		setRenderListeners(new GRenderListener[]{new XLineRenderer(this)});
	}

	public XLineState(final GCanvas canvas) {
		this(canvas, 0);
	}

	public double getX() {
		return x;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public boolean isBackwards() {
		return backwards;
	}

	public void setBackwards(final boolean backwards) {
		this.backwards = backwards;
	}
}

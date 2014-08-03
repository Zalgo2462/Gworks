package org.zp.gtest.rendertests.Mouse;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;

public class MouseState extends GImmutableState {
	private Point currentPoint = new Point(-1, -1);

	public MouseState(final GCanvas canvas) {
		super(canvas);
		setTickListeners(new GTickListener[]{new MouseController(this)});
		setRenderListeners(new GRenderListener[]{new MouseRenderer(this)});
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(final Point currentPoint) {
		this.currentPoint = currentPoint;
	}
}

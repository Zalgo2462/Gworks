package org.zp.gtest.rendertests.Mouse;

import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;

public class MouseState extends GImmutableState {
	private Point currentPoint = new Point(-1,-1);

	public MouseState() {
		setTickListeners(new GTickListener[] {new MouseController(this)});
		setRenderStrategies(new GRenderStrategy[] {new MouseRenderer(this)});
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(final Point currentPoint) {
		this.currentPoint = currentPoint;
	}
}

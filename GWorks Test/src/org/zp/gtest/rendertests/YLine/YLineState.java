package org.zp.gtest.rendertests.YLine;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;

public class YLineState extends GImmutableState implements GTickListener, GRenderStrategy {
	private int y;
	private boolean backwards;
	private final int SPEED = 50;


	public YLineState(final int start) {
		this.y = start;
		this.backwards = false;
		setTickListeners(new GTickListener[] {this});
		setRenderStrategies(new GRenderStrategy[] {this});
	}

	public YLineState() {
		this(0);
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.drawLine(0, y, canvas.getWidth(), y);
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		if(y >= canvas.getHeight()) {
			backwards = true;
		} else if (y <= 0) {
			backwards = false;
		}
		if(!backwards) {
			y += Math.round(SPEED * delta / 1000000000F);
		} else {
			y -= Math.round(SPEED * delta / 1000000000F);
		}
	}
}

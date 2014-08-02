package org.zp.gtest.rendertests.YLine;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;

public class YLineState extends GImmutableState implements GTickListener, GRenderListener {
	private final int SPEED = 50;
	private double y;
	private boolean backwards;


	public YLineState(final GCanvas canvas, final int start) {
		super(canvas);
		this.y = start;
		this.backwards = false;
		setTickListeners(new GTickListener[]{this});
		setRenderStrategies(new GRenderListener[]{this});
	}

	public YLineState(final GCanvas canvas) {
		this(canvas, 0);
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(Color.BLACK);
		graphics.drawLine(
				0,
				Math.round(Math.round(y)),
				canvas.getWidth(),
				Math.round(Math.round(y))
		);
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		if (y >= canvas.getHeight()) {
			backwards = true;
		} else if (y <= 0) {
			backwards = false;
		}
		if (!backwards) {
			y += SPEED * delta / 1000000000D;
		} else {
			y -= SPEED * delta / 1000000000D;
		}
	}
}

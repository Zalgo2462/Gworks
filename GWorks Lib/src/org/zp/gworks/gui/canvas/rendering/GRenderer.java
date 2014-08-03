package org.zp.gworks.gui.canvas.rendering;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GState.GState;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;

public class GRenderer implements GTickListener {
	private final GRenderListener clearStrategy = new GRenderListener() {
		@Override
		public void paint(GCanvas canvas, Graphics graphics, long delta) {
			graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		}
	};

	private final GCanvas canvas;
	private Graphics graphics;

	public GRenderer(GCanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		graphics = this.canvas.getBufferStrategy().getDrawGraphics();
		((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		drawStrategies(delta, clearStrategy);
		for (GState state : this.canvas.getStates()) {
			drawStrategies(delta, state.getRenderListeners());
		}
		canvas.getBufferStrategy().show();
		Toolkit.getDefaultToolkit().sync();
		if (graphics != null)
			graphics.dispose();
	}

	public void drawStrategies(final long delta, final GRenderListener... renderStrategies) {
		for (GRenderListener renderStrategy : renderStrategies) {
			renderStrategy.paint(canvas, graphics, delta);
		}
	}
}

package org.zp.gworks.gui.canvas.rendering;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GState.GState;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;

public class GRenderer implements GTickListener {
	private final GRenderStrategy clearStrategy = new GRenderStrategy() {
		@Override
		public void paint(GCanvas canvas, Graphics graphics) {
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
		drawStrategies(clearStrategy);
		for(GState state : this.canvas.getGStates()) {
			drawStrategies(state.getRenderStrategies());
		}
		this.canvas.getBufferStrategy().show();
		Toolkit.getDefaultToolkit().sync();
		if(graphics != null)
			graphics.dispose();
	}

	public void drawStrategies(final GRenderStrategy... renderStrategies) {
		for(GRenderStrategy renderStrategy : renderStrategies){
			renderStrategy.paint(canvas, graphics);
		}
	}
}

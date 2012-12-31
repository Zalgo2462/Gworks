package org.zp.gworks.gui.canvas.rendering;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GLoop;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;

public class GRenderer implements GTickListener {
	private final GCanvas canvas;
	private final GPaintStrategy clearStrategy = new GPaintStrategy() {
		@Override
		public void paint(GCanvas canvas, Graphics graphics) {
			graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		}
	};
	public GRenderer(GCanvas canvas) {
		this.canvas = canvas;
	}
	@Override
	public void tick(final GLoop loop) {
		canvas.drawStrategies(clearStrategy);
		canvas.drawStrategies(canvas.getBackgroundState().getPaintStrategies());
		if(canvas.getGState() != null) {
			canvas.drawStrategies(canvas.getGState().getPaintStrategies());

		}
		canvas.showBuffer();
	}
}

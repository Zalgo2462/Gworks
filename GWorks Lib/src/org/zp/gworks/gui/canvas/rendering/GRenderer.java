package org.zp.gworks.gui.canvas.rendering;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GLoop;
import org.zp.gworks.logic.GTickListener;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
		canvas.drawStrategy(clearStrategy);
		for(GPaintStrategy strategy : canvas.getBackgroundState().getPaintStrategies()) {
			canvas.drawStrategy(strategy);
		}
		if(canvas.getGState() != null) {
			for(GPaintStrategy strategy : canvas.getGState().getPaintStrategies()) {
				canvas.drawStrategy(strategy);
			}
		}
		canvas.showBuffer();
	}
}

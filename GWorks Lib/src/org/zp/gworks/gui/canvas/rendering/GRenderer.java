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

	@Override
	public void tick(GCanvas canvas, long delta) {
		Graphics graphics = canvas.getBufferStrategy().getDrawGraphics();
		((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		clearStrategy.paint(canvas, graphics, delta);
		for (GState state : canvas.getStates()) {
			for (GRenderListener renderStrategy : state.getRenderListeners()) {
                if(canvas.getStates().contains(state)) {
                    renderStrategy.paint(canvas, graphics, delta);
                }
            }
		}
		canvas.getBufferStrategy().show();
		Toolkit.getDefaultToolkit().sync();
		graphics.dispose();
	}
}

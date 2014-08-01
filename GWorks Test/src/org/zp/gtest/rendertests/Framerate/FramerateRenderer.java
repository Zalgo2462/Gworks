package org.zp.gtest.rendertests.Framerate;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GLoop;

import java.awt.*;

public class FramerateRenderer implements GRenderListener {
	private GLoop loop;
	private long currentFrame = 0;
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		if (loop == null) {
			loop = canvas.getLoop();
		}

		currentFrame++;
		graphics.setColor(Color.BLACK);
		double framerate = loop.getActualFramerate();
		graphics.drawString(String.valueOf(framerate) + " FPS", 25, 25);
		if (currentFrame > canvas.FPS) {
			if (framerate < min)
				min = framerate;
			if (framerate > max)
				max = framerate;
			final double deviation = max - min;

			graphics.drawString("Min: " + String.valueOf(min) + " FPS", 25, 45);
			graphics.drawString("Max: " + String.valueOf(max) + " FPS", 25, 65);
			graphics.drawString("Dev: " + String.valueOf(deviation) + " FPS", 25, 85);
		}
	}
}

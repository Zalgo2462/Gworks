package org.zp.gtest.rendertests;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;
import org.zp.gworks.logic.GLoop;

import java.awt.*;

public class Framerate implements GPaintStrategy {
	private final GLoop loop;
	private long currentFrame = 0;
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;

	public Framerate(GCanvas canvas) {
		this.loop = canvas.getLoop();
	}
	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		currentFrame++;
		graphics.setColor(Color.WHITE);
		double framerate = loop.getActualFramerate();
		graphics.drawString(String.valueOf(framerate) + " FPS", 25, 25);
		if(currentFrame > canvas.FPS) {
			if(framerate < min)
				min = framerate;
			if(framerate > max)
				max = framerate;
			final double deviation = max - min;

			graphics.drawString("Min: " + String.valueOf(min) + " FPS", 25, 45);
			graphics.drawString("Max: " + String.valueOf(max) + " FPS", 25, 65);
			graphics.drawString("Dev: " + String.valueOf(deviation) + " FPS", 25, 85);
		}
	}
}

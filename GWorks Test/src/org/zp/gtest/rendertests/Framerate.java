package org.zp.gtest.rendertests;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;
import org.zp.gworks.logic.GLoop;

import java.awt.*;

public class Framerate implements GPaintStrategy {
	private final GLoop loop;

	public Framerate(GCanvas canvas) {
		this.loop = canvas.getLoop();
	}
	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.drawString(String.valueOf(loop.getActualFramerate()) + " FPS", 25, 25);
	}
}

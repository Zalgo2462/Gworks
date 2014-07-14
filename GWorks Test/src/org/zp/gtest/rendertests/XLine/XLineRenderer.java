package org.zp.gtest.rendertests.XLine;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;

import java.awt.*;

/**
 * Date: 7/13/2014
 * Time: 5:57 PM
 */
public class XLineRenderer implements GRenderStrategy {
	private final XLineState state;

	public XLineRenderer(final XLineState state) {
		this.state = state;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.drawLine(state.getX(), 0, state.getX(), canvas.getHeight());
	}
}

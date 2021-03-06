package org.zp.gworks.gtest.rendertests.XLine;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Date: 7/13/2014
 * Time: 5:57 PM
 */
public class XLineRenderer implements GRenderListener {
	private final XLineState state;

	public XLineRenderer(final XLineState state) {
		this.state = state;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(Color.BLACK);
		graphics.drawLine(
				Math.round(Math.round(state.getX())),
				0,
				Math.round(Math.round(state.getX())),
				canvas.getHeight()
		);
	}
}

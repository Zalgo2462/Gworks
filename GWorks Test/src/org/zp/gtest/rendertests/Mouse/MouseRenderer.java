package org.zp.gtest.rendertests.Mouse;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;

import java.awt.*;

/**
 * Date: 7/13/2014
 * Time: 5:52 PM
 */
public class MouseRenderer implements GRenderStrategy{
	private final MouseState state;

	public MouseRenderer(MouseState state) {
		this.state = state;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.drawString(state.getCurrentPoint().toString(), 25, 105);
	}
}

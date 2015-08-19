package org.zp.gworks.gtest.rendertests.Mouse;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Date: 7/13/2014
 * Time: 5:52 PM
 */
public class MouseRenderer implements GRenderListener {
	private final MouseState state;

	public MouseRenderer(MouseState state) {
		this.state = state;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(Color.BLACK);
		graphics.drawString(state.getCurrentPoint().toString(), 25, 105);
	}
}

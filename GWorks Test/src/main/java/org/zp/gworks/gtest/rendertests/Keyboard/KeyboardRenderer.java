package org.zp.gworks.gtest.rendertests.Keyboard;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Date: 7/13/2014
 * Time: 5:39 PM
 */
public class KeyboardRenderer implements GRenderListener {
	private final KeyboardState state;

	public KeyboardRenderer(final KeyboardState state) {
		this.state = state;
	}

	@Override
	public void paint(final GCanvas canvas, final Graphics graphics, long delta) {
		graphics.setColor(Color.BLACK);
		graphics.drawString("Keyboard input: " + state.getString(), 25, 125);
	}
}

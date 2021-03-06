package org.zp.gworks.gtest.rendertests.Keyboard;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

/**
 * Date: 7/13/2014
 * Time: 5:39 PM
 */
public class KeyboardController implements GTickListener {
	private final KeyboardState state;

	public KeyboardController(final KeyboardState state) {
		this.state = state;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		StringBuilder s = new StringBuilder();
		for (Integer keyEvent : canvas.getGKeyListener().getPressedKeyCodes()) {
			s.append((char) keyEvent.intValue());
		}
		state.setString(s.toString());
	}
}

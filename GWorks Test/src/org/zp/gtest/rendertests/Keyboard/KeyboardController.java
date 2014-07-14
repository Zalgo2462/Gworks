package org.zp.gtest.rendertests.Keyboard;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

import java.awt.event.KeyEvent;

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
		KeyEvent e = canvas.getGKeyListener().getNextTypedEvent();
		if(e != null)
			state.appendCharacter(e.getKeyChar());
	}
}

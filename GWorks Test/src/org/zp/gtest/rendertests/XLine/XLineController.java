package org.zp.gtest.rendertests.XLine;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

/**
 * Date: 7/13/2014
 * Time: 5:57 PM
 */
public class XLineController implements GTickListener {
	private final XLineState state;
	private final int SPEED = 50; //Pixels per second

	public XLineController(final XLineState state) {
		this.state = state;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		if (state.getX() >= canvas.getWidth()) {
			state.setBackwards(true);
		} else if (state.getX() <= 0) {
			state.setBackwards(false);
		}
		if (!state.isBackwards()) {
			state.setX(state.getX() + SPEED * delta / 1000000000D);
		} else {
			state.setX(state.getX() - SPEED * delta / 1000000000D);
		}
	}
}

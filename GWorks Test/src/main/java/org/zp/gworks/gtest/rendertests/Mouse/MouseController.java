package org.zp.gworks.gtest.rendertests.Mouse;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

import java.awt.event.MouseEvent;

/**
 * Date: 7/13/2014
 * Time: 5:49 PM
 */
public class MouseController implements GTickListener{
	private final MouseState state;

	public MouseController(final MouseState state) {
		this.state = state;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		MouseEvent e = canvas.getGMouseListener().getNextClickedEvent();
		if(e != null)
			state.setCurrentPoint(e.getPoint());
	}
}

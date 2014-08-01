package org.zp.gtest.rendertests.Keyboard;

import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

public class KeyboardState extends GImmutableState {
	private String string = "";

	public KeyboardState() {
		setTickListeners(new GTickListener[]{new KeyboardController(this)});
		setRenderStrategies(new GRenderListener[]{new KeyboardRenderer(this)});
	}

	public void setString(final String s) {
		this.string = s;
	}

	public String getString() {
		return string;
	}
}

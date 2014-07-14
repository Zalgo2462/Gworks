package org.zp.gtest.rendertests.Keyboard;

import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

public class KeyboardState extends GImmutableState {
	private String string = "";

	public KeyboardState() {
		setTickListeners(new GTickListener[] {new KeyboardController(this)});
		setRenderStrategies(new GRenderStrategy[] {new KeyboardRenderer(this)});
	}

	public void appendCharacter(final char c) {
		string += c;
	}

	public String getString() {
		return string;
	}
}

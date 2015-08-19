package org.zp.gworks.gtest.rendertests.Keyboard;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GTickListener;

public class KeyboardState extends GImmutableState {
	private String string = "";

	public KeyboardState(GCanvas canvas) {
		super(canvas);
		setTickListeners(new GTickListener[]{new KeyboardController(this)});
		setRenderListeners(new GRenderListener[]{new KeyboardRenderer(this)});
	}

	public String getString() {
		return string;
	}

	public void setString(final String s) {
		this.string = s;
	}
}

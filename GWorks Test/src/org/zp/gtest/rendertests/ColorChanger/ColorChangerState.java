package org.zp.gtest.rendertests.ColorChanger;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;

/**
 * Date: 7/13/2014
 * Time: 12:25 PM
 */
public class ColorChangerState extends GImmutableState {
	public ColorChangerState(GCanvas canvas) {
		super(canvas);
		setRenderStrategies(new GRenderListener[]{new ColorChangerRenderer()});
	}
}

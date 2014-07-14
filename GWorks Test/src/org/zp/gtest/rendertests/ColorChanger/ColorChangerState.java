package org.zp.gtest.rendertests.ColorChanger;

import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;
import org.zp.gworks.logic.GState.GImmutableState;

/**
 * Date: 7/13/2014
 * Time: 12:25 PM
 */
public class ColorChangerState extends GImmutableState {
	public ColorChangerState() {
		setRenderStrategies(new GRenderStrategy[] {new ColorChangerRenderer()});
	}
}

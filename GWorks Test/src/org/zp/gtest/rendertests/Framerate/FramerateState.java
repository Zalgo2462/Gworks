package org.zp.gtest.rendertests.Framerate;

import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;
import org.zp.gworks.logic.GState.GImmutableState;

/**
 * Date: 7/13/2014
 * Time: 12:28 PM
 */
public class FramerateState extends GImmutableState {
	public FramerateState() {
		setRenderStrategies(new GRenderStrategy[] {new FramerateRenderer()});
	}
}
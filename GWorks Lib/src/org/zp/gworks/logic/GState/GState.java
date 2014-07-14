package org.zp.gworks.logic.GState;

import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;
import org.zp.gworks.logic.GTickListener;

public interface GState {
	public GTickListener[] getTickListeners();
	public GRenderStrategy[] getRenderStrategies();
}

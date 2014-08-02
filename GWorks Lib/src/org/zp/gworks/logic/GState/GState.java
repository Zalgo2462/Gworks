package org.zp.gworks.logic.GState;

import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GTickListener;

public interface GState {
	public GTickListener[] getTickListeners();

	public GRenderListener[] getRenderStrategies();

	public void onAddGState();

	public void onRemoveGState();
}

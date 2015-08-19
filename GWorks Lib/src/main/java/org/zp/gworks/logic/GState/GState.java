package org.zp.gworks.logic.GState;

import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GTickListener;

public interface GState {
	public GTickListener[] getTickListeners();

	public GRenderListener[] getRenderListeners();

	public void onAddState();

	public void onRemoveState();
}

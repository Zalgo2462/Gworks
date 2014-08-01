package org.zp.gworks.gui.canvas.rendering;

import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;

public interface GRenderListener {
	public void paint(GCanvas canvas, Graphics graphics, long delta);
}

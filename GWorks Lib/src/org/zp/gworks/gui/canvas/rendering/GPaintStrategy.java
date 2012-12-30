package org.zp.gworks.gui.canvas.rendering;

import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;

public interface GPaintStrategy {
	public void paint(GCanvas canvas, Graphics graphics);
}

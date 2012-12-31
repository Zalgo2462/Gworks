package org.zp.gtest.rendertests;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;
import org.zp.gworks.gui.canvas.input.GMouseListener;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Mouse implements GPaintStrategy {
	public Point currPoint = new Point(-1,-1);

	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		graphics.setColor(Color.BLACK);
		MouseEvent e = ((GMouseListener)canvas.getMouseListeners()[0]).getNextClickedEvent();
		if(e != null)
			currPoint = e.getPoint();
		graphics.drawString(currPoint.toString(), 25, 105);
	}
}

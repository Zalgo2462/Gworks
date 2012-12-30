package org.zp.gtest.rendertests;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;

import java.awt.*;

public class XLine implements GPaintStrategy{
	int xValue;
	boolean backwards = false;

	public XLine(final int start) {
		xValue = start;
	}

	public XLine() {
		this(0);
	}

	@Override
	public void paint(GCanvas c, Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.drawLine(xValue, 0, xValue, c.getHeight());
		if(xValue >= c.getWidth()) {
			backwards = true;
		} else if (xValue <= 0) {
			backwards = false;
		}
		if(!backwards) {
			xValue++;
		} else {
			xValue--;
		}
	}
}

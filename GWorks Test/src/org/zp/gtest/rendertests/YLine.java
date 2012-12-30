package org.zp.gtest.rendertests;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;

import java.awt.*;

public class YLine implements GPaintStrategy {
	int yValue;
	boolean backwards = false;

	public YLine(final int start) {
		yValue = start;
	}

	public YLine() {
		this(0);
	}

	@Override
	public void paint(GCanvas c, Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.drawLine(0, yValue, c.getWidth(), yValue);
		if(yValue >= c.getHeight()) {
			backwards = true;
		} else if (yValue <= 0) {
			backwards = false;
		}
		if(!backwards) {
			yValue++;
		} else {
			yValue--;
		}
	}
}

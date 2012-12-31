package org.zp.gtest.rendertests;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;
import org.zp.gworks.gui.canvas.input.GKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Keyboard implements GPaintStrategy{
	private String string = "";

	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		graphics.setColor(Color.BLACK);
		KeyEvent e = ((GKeyListener)canvas.getKeyListeners()[0]).getNextTypedEvent();
		if(e != null)
			string += e.getKeyChar();
		graphics.drawString("Keyboard input: " + string, 25, 125);
	}
}

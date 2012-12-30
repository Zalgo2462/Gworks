package org.zp.gtest.rendertests;

import org.zp.gtest.resources.Resources;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.input.GKeyListener;
import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Queue;

public class ImageViewer implements GPaintStrategy{
	private final BufferedImage image;
	private int x1, y1 = 0;

	public ImageViewer(final BufferedImage image) {
		this.image = image;
	}


	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		Queue<Integer> pressedKeys = ((GKeyListener)canvas.getKeyListeners()[0]).getPressedKeyCodes();
		for(Integer code : pressedKeys) {
			switch(code) {
				case KeyEvent.VK_RIGHT:
					++x1;
					break;
				case KeyEvent.VK_UP:
					--y1;
					break;
				case KeyEvent.VK_LEFT:
					--x1;
					break;
				case KeyEvent.VK_DOWN:
					++y1;
					break;
			}
		}

		graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(),
				x1, y1, x1 + canvas.getWidth(), y1 + canvas.getHeight(), null);
	}
}

package org.zp.gtest.rendertests.ImageViewer;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

import java.awt.event.KeyEvent;
import java.util.Collection;

/**
 * Date: 7/13/2014
 * Time: 5:17 PM
 */
public class ImageViewerController implements GTickListener {
	private final ImageViewerState state;
	private final int SPEED = 100;

	public ImageViewerController(ImageViewerState state) {
		this.state = state;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		Collection<Integer> pressedKeys = canvas.getGKeyListener().getPressedKeyCodes();
		for (Integer code : pressedKeys) {
			switch (code) {
				case KeyEvent.VK_RIGHT:
					state.setX(Math.round(state.getX() + SPEED * delta / 1000000000F));
					break;
				case KeyEvent.VK_UP:
					state.setY(Math.round(state.getY() - SPEED * delta / 1000000000F));
					break;
				case KeyEvent.VK_LEFT:
					state.setX(Math.round(state.getX() - SPEED * delta / 1000000000F));
					break;
				case KeyEvent.VK_DOWN:
					state.setY(Math.round(state.getY() + SPEED * delta / 1000000000F));
					break;
			}
		}
	}
}
